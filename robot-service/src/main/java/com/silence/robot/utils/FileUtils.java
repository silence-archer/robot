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

import com.silence.robot.domain.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public static FileDto readLine(long pos, int lineNum, String fileName){
        File file = new File(fileName);
        int count = 0;//定义行数
        long posResult = 0;
        String content = "";
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return new FileDto();
        }
        RandomAccessFile fileRead = null;
        try {
            fileRead = new RandomAccessFile(file,"r");
            long length = fileRead.length();
            for (long i = pos; i < length; i++) {
                //开始读取
                fileRead.seek(i);
                ////有换行符，则读取
                if(fileRead.readByte() == '\n'){
                    String readLine = fileRead.readLine();
                    if(readLine == null){
                        readLine = "";
                    }
                    String line = new String(readLine.getBytes("ISO-8859-1"),"UTF-8");
                    content = content + line + "<br/>";
                    count ++;
                    if(count == lineNum){
                        logger.info("当前读取到指定行数{}",lineNum);
                        posResult = i;
                        break;
                    }
                }
            }
            if(count != lineNum){
                posResult = length;
            }
        } catch (IOException e) {
            logger.error("文件读取失败",e);
        }
        FileDto fileDto = new FileDto();
        fileDto.setContent(content);
        fileDto.setLineNum(count);
        fileDto.setPos(posResult+1);
        return fileDto;
    }

    public static String getFileContent(String fileName){
        String content = "";
        if(Files.exists(Paths.get(fileName))){
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(fileName));
                content = new String(bytes);
            } catch (IOException e) {
                logger.error("文件读取失败",e);
            }
        }

        return content;
    }

    public static void main(String[] args){
        FileDto fileDto = readLine(341156, 1000, "cmd.txt");
        System.out.println(fileDto);
    }


}