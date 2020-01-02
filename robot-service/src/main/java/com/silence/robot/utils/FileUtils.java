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

import com.alibaba.fastjson.JSONReader;
import com.silence.robot.domain.FileDto;
import com.silence.robot.domain.subscribe.SubscribeMsgInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static String convertFileName(String path) {
        String regex = "/";
        if(CommonUtils.getOsName().equals("windows")){
            regex = "\\\\";
        }
        String[] split = path.split(regex);
        String fileName = split[split.length - 1].split("\\.")[0] + ".txt";
        return fileName;
    }

    public static void threadPoolExecute(Runnable runnable) {
        while (true) {
            if (FileUtils.FILE_READ_POOL.getQueue().isEmpty()) {
                FileUtils.FILE_READ_POOL.execute(runnable);
                break;
            }
        }
    }

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
        }finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error("文件流关闭失败", e);
            }
        }
    }

    public static void writeFile(String fileName, String content) {
        BufferedWriter bufferedWriter = null;
        try {
            Path path = Paths.get(getDefaultLocalUrl(fileName));
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            bufferedWriter = Files.newBufferedWriter(path);
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("文件写入失败", e);
        }finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error("文件流关闭失败", e);
            }
        }
    }

    public static String getDefaultLocalUrl(String fileName){
        String s = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "/robot/";
        Path path = Paths.get(s);
        if(Files.notExists(path)){
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                logger.error("文件创建失败", e);
            }
        }
        return s + fileName;
    }

    public static List<String> readAllLines(String fileName){
        List<String> list = new ArrayList<>();
        Path path = Paths.get(getDefaultLocalUrl(fileName));
        if(Files.exists(path)){
            try {
                list = Files.readAllLines(path);
            } catch (IOException e) {
                logger.error("文件读取失败", e);
            }
        }

        return list;
    }

    public static String readAllContents(String fileName){
        String content = "";
        Path path = Paths.get(getDefaultLocalUrl(fileName));
        if(Files.exists(path)){
            try {
                content = new String(Files.readAllBytes(path));
            } catch (IOException e) {
                logger.error("文件读取失败", e);
            }
        }

        return content;
    }

    public static boolean exists(String name){
        String fileName = FileUtils.getDefaultLocalUrl(name);
        return Files.exists(Paths.get(fileName));
    }

    public static boolean notExists(String name){
        return !exists(name);
    }

    /**
     * 在类路径下读取json文件
     * @param fileName
     * @return
     */
    public static <T> T readJsonFile(String fileName, Class<T> clazz){
        InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
        Reader reader = new InputStreamReader(inputStream);
        JSONReader jsonReader = new JSONReader(reader);
        return jsonReader.readObject(clazz);
    }

    public static Map getJsonMap(InputStream inputStream) {
        Reader reader = new InputStreamReader(inputStream);
        JSONReader jsonReader = new JSONReader(reader);
        Map map = jsonReader.readObject(Map.class);
        return map;
    }

    public static <T> T readXml(String xmlStr, Class<T> clazz){
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            T instance = clazz.newInstance();
            elements.forEach(element ->{
                BeanUtils.setProperty(instance,element.getName(),element.getStringValue());
            });

            return instance;
        } catch (Exception e) {
            logger.error("xml字符串{}，转化为对象{}失败",xmlStr,clazz.getName(), e);
        }
        return null;

    }

    public static <T> T convertXmlStrToObject(String xmlStr, Class<T> clazz){
        T xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = (T) unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            logger.error("xml字符串{}，转化为对象{}失败",xmlStr,clazz.getName(), e);
        }
        return xmlObject;
    }

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
            logger.error("对象{}转化xml失败",obj.getClass().getName(), e);
        }
        return sw.toString();
    }


    public static void main(String[] args) {
        SubscribeMsgInfo subscribeMsgInfo = convertXmlStrToObject("<xml><ToUserName><![CDATA[gh_e46a5a4fc246]]></ToUserName>\n" +
                "<FromUserName><![CDATA[o9tSfjg9jM_JuvU8gv1dbpmNNAdo]]></FromUserName>\n" +
                "<CreateTime>1577934196</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[你好]]></Content>\n" +
                "<MsgId>22590585821471954</MsgId>\n" +
                "</xml>\n", SubscribeMsgInfo.class);
        subscribeMsgInfo.setMsgId(null);
        System.out.println(convertToXml(subscribeMsgInfo));
    }


}