/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InputStreamRunnable
 * Author:   silence
 * Date:     2019/10/22 16:34
 * Description: 正常输入流处理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.io;

import com.silence.robot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 〈一句话功能简述〉<br>
 * 〈正常输入流处理〉
 *
 * @author silence
 * @create 2019/10/22
 * @since 1.0.0
 */
public class InputStreamRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(InputStreamRunnable.class);
    private BufferedReader bReader;

    private String type;

    public InputStreamRunnable(InputStream inputStream, String type) {
        try {
            bReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream), CommonUtils.getCmdCharset()));
            this.type = type;
        } catch (Exception ex) {
            logger.error("输入流读取异常", ex);
        }
    }

    @Override
    public void run() {

        String line;
        StringBuilder sb = new StringBuilder();
        try {
            int i = 0;

            deleteFile();
            while ((line = bReader.readLine()) != null) {
                logger.info("当前读取到的命令返回:" + line);
                sb.append(line + "\n");
                i++;
                if (i == 100) {
                    writeFile(sb);
                    sb.delete(0, sb.length());
                    i = 0;
                }
            }
            if (sb.length() > 0) {
                writeFile(sb);
            }
            logger.info("命令读取结束");
            Path overPath = Paths.get(type + "Over.txt");
            Files.createFile(overPath);
            bReader.close();
        } catch (IOException e) {
            logger.error("命令输入流读取异常", e);
        }

    }

    private void writeFile(StringBuilder sb) throws IOException {
        Path path = Paths.get(type + "Cmd.txt");

        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Files.write(path, sb.toString().getBytes(), StandardOpenOption.APPEND);
    }

    private void deleteFile() throws IOException {
        Path path = Paths.get(type + "Cmd.txt");
        Path overPath = Paths.get(type + "Over.txt");


        if (Files.exists(path)) {
            Files.delete(path);
        }
        if (Files.exists(overPath)) {
            Files.delete(overPath);
        }


    }

}