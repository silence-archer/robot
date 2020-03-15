/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FileUtils
 * Author:   silence
 * Date:     2019/10/29 10:58
 * Description: 文件工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.silence.robot.domain.FileDto;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈文件工具类〉
 *
 * @author silence
 * @create 2019/10/29
 * @since 1.0.0
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static final ThreadPoolExecutor FILE_READ_POOL = new ThreadPoolExecutor(8, 16, 3, TimeUnit.SECONDS, new LinkedBlockingDeque<>(32));

    public static final String DEFAULT_CMD_LOG_URL = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "/robot/";

    /**
     * 读文件根据偏移量一次读多行
     * @param pos
     * @param lineNum
     * @param fileName
     * @return
     */
    public static FileDto readLine(long pos, int lineNum, String fileName) {
        File file = new File(fileName);
        int count = 0;//定义行数
        long posResult = 0;
        String content = "";
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return new FileDto();
        }
        RandomAccessFile fileRead = null;
        try {
            fileRead = new RandomAccessFile(file, "r");
            long length = fileRead.length();
            for (long i = pos; i < length; i++) {
                //开始读取
                fileRead.seek(i);
                ////有换行符，则读取
                if (fileRead.readByte() == '\n') {
                    String readLine = fileRead.readLine();
                    if (readLine == null) {
                        readLine = "";
                    }
                    String line = new String(readLine.getBytes("ISO-8859-1"), "UTF-8");
                    content = content + line + "<br/>";
                    count++;
                    if (count == lineNum) {
                        logger.info("当前读取到指定行数{}", lineNum);
                        posResult = i;
                        break;
                    }
                }
            }
            if (count != lineNum) {
                posResult = length;
            }
        } catch (IOException e) {
            logger.error("文件读取失败", e);
        } finally {
            try {
                fileRead.close();
            } catch (IOException e) {
                logger.error("文件读取失败", e);
            }
        }
        FileDto fileDto = new FileDto();
        fileDto.setContent(content);
        fileDto.setLineNum(count);
        fileDto.setPos(posResult + 1);
        return fileDto;
    }

    /**
     * 根据偏移量读文件
     * @param startPos
     * @param endPos
     * @param file
     * @return
     */
    public static List<String> splitReadFile(long startPos, long endPos, File file) {
        List<String> list = new ArrayList<>();
        RandomAccessFile fileRead = null;
        try {
            fileRead = new RandomAccessFile(file, "r");
            for (long i = startPos; i < endPos; i++) {
                //开始读取
                fileRead.seek(i);
                ////有换行符，则读取
                if (fileRead.readByte() == '\n') {
                    String readLine = fileRead.readLine();
                    if (readLine == null) {
                        readLine = "";
                    }
                    String line = new String(readLine.getBytes("ISO-8859-1"), "UTF-8");
                    list.add(line);

                }
            }
        } catch (IOException e) {
            logger.error("文件读取失败", e);
        } finally {
            try {
                fileRead.close();
            } catch (IOException e) {
                logger.error("文件读取失败", e);
            }
        }
        return list;
    }

    /**
     * 根据文件名读取文件全部内容
     * @param fileName
     * @return
     */
    public static String getFileContent(String fileName) {
        String content = "";
        if (Files.exists(Paths.get(fileName))) {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(fileName));
                content = new String(bytes);
            } catch (IOException e) {
                logger.error("文件读取失败", e);
            }
        }

        return content;
    }

    /**
     * 获取当前目录下所有的文件不包含子目录
     * @param dir
     * @return
     */
    public static List<FileDto> getFileList(String dir) {
        List<FileDto> results = new ArrayList<>();

        File file = new File(dir);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] list = file.listFiles();
                for (File item : list) {
                    FileDto fileDto = new FileDto();
                    fileDto.setDirectory(item.isDirectory());
                    fileDto.setFileName(item.getName());
                    fileDto.setFilePath(item.getPath());
                    results.add(fileDto);
                }
            } else {
                FileDto fileDto = new FileDto();
                fileDto.setFileName(file.getName());
                fileDto.setFilePath(file.getPath());
                results.add(fileDto);
            }

        }
        return results;

    }

    /**
     * 删除文件夹下所有文件
     *
     * @param dir
     */
    public static void delFolder(String dir) {
        delAllFiles(dir);
        File file = new File(dir);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 根据文件路径删除该路径下所有文件包含子目录（递归实现）
     * @param dir
     */
    public static void delAllFiles(String dir) {
        File file = new File(dir);

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File item : files) {
                if (item.isDirectory()) {
                    delAllFiles(item.getPath());
                    delFolder(item.getPath());
                } else {
                    item.delete();
                }
            }
        }


    }

    /**
     * 修改文件后缀名
     * @param path
     * @return
     */
    public static String convertFileName(String path) {
        return convertFileName(path, "txt");
    }

    /**
     * 修改文件后缀名
     * @param path
     * @param type
     * @return
     */
    public static String convertFileName(String path, String type) {
        String regex = "/";
        if (CommonUtils.getOsName().equals("windows")) {
            regex = "\\\\";
        }
        String[] split = path.split(regex);
        String fileName = split[split.length - 1].split("\\.")[0] + "."+type;
        return fileName;
    }

    /**
     * 将线程对象放入线程池执行
     * @param runnable
     */
    public static void threadPoolExecute(Runnable runnable) {
        while (true) {
            if (FileUtils.FILE_READ_POOL.getQueue().isEmpty()) {
                FileUtils.FILE_READ_POOL.execute(runnable);
                break;
            }
        }
    }

    /**
     * 根据文件名和文件内容写文件（追加写）
     * @param fileName
     * @param content
     */
    public static void writeFileAppend(String fileName, String content) {
        BufferedWriter bufferedWriter = null;
        try {
            Path path = Paths.get(getDefaultLocalUrl(fileName));
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("文件写入失败", e);
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error("文件流关闭失败", e);
            }
        }
    }

    /**
     * 写文件文件路径采用系统默认路径
     * @param fileName
     * @param content
     * @param isDefaultLocalUrl
     */
    public static void writeFile(String fileName, String content, boolean isDefaultLocalUrl) {
        BufferedWriter bufferedWriter = null;
        try {
            Path path;
            if(isDefaultLocalUrl){
                path = Paths.get(getDefaultLocalUrl(fileName));
            }else{
                path = Paths.get(fileName);
            }

            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            bufferedWriter = Files.newBufferedWriter(path);
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("文件写入失败", e);
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error("文件流关闭失败", e);
            }
        }
    }

    /**
     * 写文件文件路径采用系统默认路径
     * @param fileName
     * @param content
     */
    public static void writeFile(String fileName, String content) {
        writeFile(fileName, content, true);
    }

    /**
     * 根据文件名和系统默认路径拼接文件全名
     * @param fileName
     * @return
     */
    public static String getDefaultLocalUrl(String fileName) {
        Path path = Paths.get(DEFAULT_CMD_LOG_URL);
        if (Files.notExists(path)) {
            try {
                Files.createDirectory(path);
                logger.info("写文件默认路径为{}",DEFAULT_CMD_LOG_URL);
            } catch (IOException e) {
                logger.error("文件创建失败", e);
            }
        }
        return DEFAULT_CMD_LOG_URL + fileName;
    }

    /**
     * 按行读取文件，文件路径默认
     * @param fileName
     * @return
     */
    public static List<String> readAllLines(String fileName) {
        List<String> list = new ArrayList<>();
        Path path = Paths.get(getDefaultLocalUrl(fileName));
        if (Files.exists(path)) {
            try {
                list = Files.readAllLines(path);
            } catch (IOException e) {
                logger.error("文件读取失败", e);
            }
        }

        return list;
    }

    /**
     * 读取文件全部内容，文件路径默认
     * @param fileName
     * @return
     */
    public static String readAllContents(String fileName) {
        String content = "";
        Path path = Paths.get(getDefaultLocalUrl(fileName));
        if (Files.exists(path)) {
            try {
                content = new String(Files.readAllBytes(path));
            } catch (IOException e) {
                logger.error("文件读取失败", e);
            }
        }

        return content;
    }

    /**
     * 判断文件是否存在，路径默认
     * @param name
     * @return
     */
    public static boolean exists(String name) {
        String fileName = FileUtils.getDefaultLocalUrl(name);
        return Files.exists(Paths.get(fileName));
    }

    /**
     * 判断文件是否存在，路径默认
     * @param name
     * @return
     */
    public static boolean notExists(String name) {
        return !exists(name);
    }

    /**
     * 在类路径下读取json文件
     *
     * @param fileName
     * @return
     */
    public static <T> T readJsonFile(String fileName, Class<T> clazz) {
        InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
        Reader reader = new InputStreamReader(inputStream);
        JSONReader jsonReader = new JSONReader(reader);
        return jsonReader.readObject(clazz);
    }

    /**
     * 根据输入流获取json map
     * @param inputStream
     * @return
     */
    public static Map getJsonMap(InputStream inputStream) {
        Reader reader = new InputStreamReader(inputStream);
        JSONReader jsonReader = new JSONReader(reader);
        Map map = jsonReader.readObject(Map.class);
        return map;
    }

    /**
     * 将xml转化为对象
     * @param xmlStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertXmlStrToObject(String xmlStr, Class<T> clazz) {
        T xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = (T) unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            logger.error("xml字符串{}，转化为对象{}失败", xmlStr, clazz.getName(), e);
        }
        return xmlObject;
    }

    /**
     * 将对象转化为xml
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            logger.error("对象{}转化xml失败", obj.getClass().getName(), e);
        }
        return sw.toString();
    }


    /**
     * 根据图片识别接口识别图片
     * @param uri
     * @param appCode
     * @param type
     * @param fileName
     * @return
     */
    public static String readImage(String uri, String appCode, String type, String fileName) {
        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("format", type);
        configObj.put("finance", false);
        configObj.put("dir_assure", false);
        String configStr = configObj.toString();
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);

        // 对图像进行base64编码
        String imgBase64 = getImageBase64(fileName);

        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        requestObj.put("image", imgBase64);
        requestObj.put("configure", configStr);


        String bodys = requestObj.toString();

        Map map = HttpUtils.doPost(uri, headers, bodys);

        return map.get("tables").toString();


    }

    /**
     * 图片转化为base64编码
     * @param fileName
     * @return
     */
    public static String getImageBase64(String fileName) {
        // 对图像进行base64编码
        String imgBase64 = null;
        try {
            File file = new File(fileName);
            byte[] content = new byte[(int) file.length()];
            FileInputStream inputstream = new FileInputStream(file);
            inputstream.read(content);
            inputstream.close();
            imgBase64 = new String(Base64.encodeBase64(content));
        } catch (IOException e) {
            logger.error("图片{}转base64编码失败", fileName, e);
        }
        return imgBase64;
    }

    /**
     *
     * <合并子文件>
     *
     * @author mazhaohui
     * @date 2020年1月9日 下午6:18:00
     * @param paths
     * @param resultPath
     * @return
     */
    public static void mergeFiles(String[] paths, String resultPath) {
        if (paths == null || paths.length < 1 || CommonUtils.isEmpty(resultPath)) {
            return;
        }
        File[] files = new File[paths.length];
        for (int i = 0; i < paths.length; i++) {
            files[i] = new File(paths[i]);
            if (CommonUtils.isEmpty(paths[i]) || !files[i].exists() || !files[i].isFile()) {
                return;
            }
        }
        try {
            File resultFile = new File(resultPath);
            FileOutputStream fileOutputStream = new FileOutputStream(resultFile, true);
            FileChannel resultFileChannel = fileOutputStream.getChannel();
            for (int i = 0; i < paths.length; i++) {
                FileInputStream fileInputStream = new FileInputStream(files[i]);
                FileChannel blk = fileInputStream.getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
                fileInputStream.close();
            }
            resultFileChannel.close();
            fileOutputStream.close();
        } catch (IOException e) {
            logger.error("{}合并子文件失败", resultPath, e);
            return;
        }

        for (int i = 0; i < paths.length; i++) {
            files[i].delete();
        }
        logger.info("{}合并子文件成功", resultPath);
    }

    /**
     *
     * <按顺序合并子文件>
     *
     * @author mazhaohui
     * @date 2020年1月9日 下午8:07:22
     * @param fileName
     * @param filePath
     * @param size
     * @return
     */
    public static void mergeFilesByOrder(String filePath, String fileName, int size) {
        fileName = filePath + File.separator + fileName;
        logger.info("开始合并文件{}，文件数量为{}", fileName, size);
        // 初始化文件列表数组
        String[] paths = new String[size];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = fileName + "-" + i;
        }

        mergeFiles(paths, fileName);
    }

    /**
     *
     * <写文件>
     *
     * @author mazhaohui
     * @date 2020年3月5日 下午4:40:50
     * @param filePath
     * @param fileName
     * @param content
     * @return
     */
    public static void writeFile(String filePath, String fileName, String content) {
        try {
            if (Files.notExists(Paths.get(filePath))) {
                Files.createDirectories(Paths.get(filePath));
            }
            Files.write(Paths.get(filePath + File.separator + fileName), content.getBytes());
        } catch (IOException e) {
            logger.error("文件路径{}，文件名{}，写文件失败", filePath, fileName, e);
            return;
        }

    }

    /**
     *
     * <删除对应目录下所有文件及子文件-对应并发写文件>
     *
     * @author mazhaohui
     * @date 2020年3月3日 下午2:17:04
     * @param filePath
     * @param fileName
     * @return
     */
    public static void deleteFiles(String filePath, String fileName) {
        try {
            Path dir = Paths.get(filePath);
            if (Files.notExists(dir)) {
                return;
            }

            Files.list(dir).forEach(path -> {
                if (path.getFileName().toString().contains(fileName)) {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        logger.error("文件路径{}，文件名{}，删除失败", filePath, fileName, e);
                    }
                }
            });
        } catch (IOException e) {
            logger.error("文件路径{}，文件名{}，删除失败", filePath, fileName, e);
            return;
        }
    }

    /**
     *
     * <获取文件行数>
     *
     * @author mazhaohui
     * @date 2020年3月5日 下午3:15:25
     * @param filePath
     * @param fileName
     * @return
     */
    public static long getFileLines(String filePath, String fileName) {
        Path path = Paths.get(filePath + File.separator + fileName);
        long count = 0;
        try {
            count = Files.lines(path).count();
        } catch (IOException e) {
            logger.error("{}获取文件{}行数失败", filePath, fileName, e);
        }
        return count;
    }

    /**
     *
     * <获取分割文件处理器>
     *
     * @author mazhaohui
     * @date 2020年3月6日 上午11:15:31
     * @param filePath
     * @param fileName
     * @return
     */
    public static RandomAccessFile getAccessFile(String filePath, String fileName) {
        File file = new File(filePath + File.separator + fileName);
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            logger.error("[{}]目录下[{}]文件不存在", filePath, fileName, e);
        }
        return accessFile;

    }

    /**
     *
     * <切割文件>
     *
     * @author mazhaohui
     * @date 2020年3月6日 下午1:59:37
     * @param accessFile
     * @param maxSize
     * @return
     */
    public static List<Map<String, Long>> splitFile(RandomAccessFile accessFile, long index, long maxSize) {
        List<Map<String, Long>> list = new ArrayList<>();
        long startPos = index;
        long endPos = 0;
        long fileLength = 0;
        try {
            fileLength = accessFile.length();
            while (true) {
                endPos = startPos + maxSize - 1;

                if (endPos >= fileLength - 1) {
                    endPos = fileLength - 1;
                    Map<String, Long> map = new HashMap<>();
                    map.put("startPos", startPos);
                    map.put("endPos", endPos);
                    list.add(map);
                    break;
                }
                accessFile.seek(endPos);
                byte tmp = (byte)accessFile.read();
                while (tmp != '\n' && tmp != '\r') {
                    endPos++;
                    if (endPos >= fileLength - 1) {
                        endPos = fileLength - 1;
                        break;
                    }
                    accessFile.seek(endPos);
                    tmp = (byte)accessFile.read();
                }
                Map<String, Long> map = new HashMap<>();
                map.put("startPos", startPos);
                map.put("endPos", endPos);
                list.add(map);
                if(endPos == fileLength - 1) {
                    break;
                }
                startPos = endPos;

            }
        } catch (IOException e) {
            logger.error("解析文件异常", e);
        }
        logger.info("文件拆分结果为{}", list);
        return list;
    }

    public static Map<String, Object> readFileFirstLine(RandomAccessFile accessFile) {
        long i = 0;
        Map<String, Object> map = new HashMap<>(2);
        try {
            String headLine = accessFile.readLine();
            while (true) {
                accessFile.seek(i);
                byte tmp = (byte)accessFile.read();
                if (tmp == '\n' || tmp == '\r') {
                    break;
                }
                i++;
            }
            map.put("headLine", headLine);
            map.put("index", i);
        } catch (IOException e) {
            logger.error("解析文件异常", e);
        }
        logger.info("文件头信息为{}", map);
        return map;

    }

    /**
     *
     * <根据偏移量读取文件>
     *
     * @author mazhaohui
     * @date 2020年3月6日 下午2:13:39
     * @param accessFile
     * @param startPos
     * @param endPos
     * @return
     */
    public static List<String> getFileContentByPos(RandomAccessFile accessFile, long startPos, long endPos) {
        List<String> list = new ArrayList<>();
        try {
            long sliceSize = endPos - startPos + 1;
            int bufferSize = 1024;
            byte[] readBuff = new byte[bufferSize];
            MappedByteBuffer mapBuffer =
                    accessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, startPos, sliceSize);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            for (int offset = 0; offset < sliceSize; offset += bufferSize) {
                int readLength;
                if (offset + bufferSize <= sliceSize) {
                    readLength = bufferSize;
                } else {
                    readLength = (int)(sliceSize - offset);
                }

                mapBuffer.get(readBuff, 0, readLength);
                for (int i = 0; i < readLength; i++) {
                    byte tmp = readBuff[i];
                    // 碰到换行符
                    if (tmp == '\n' || tmp == '\r') {
                        String line = new String(bos.toByteArray(), "UTF-8");
                        if (CommonUtils.isNotEmpty(line)) {
                            list.add(line);
                        }
                        bos.reset();
                    } else {
                        bos.write(tmp);
                    }
                }
            }
            if (bos.size() > 0) {
                String line = new String(bos.toByteArray(), "UTF-8");
                if (CommonUtils.isNotEmpty(line)) {
                    list.add(line);
                }
            }
        } catch (Exception e) {
            logger.error("文件读取失败", e);
        }
        return list;
    }






}