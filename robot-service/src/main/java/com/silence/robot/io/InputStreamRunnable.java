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

import com.silence.robot.service.SvnOperateService;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import com.silence.robot.utils.SpringContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private InputStream inputStream;

    private String type;

    public InputStreamRunnable(InputStream inputStream, String type) {
        try {
            bReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream), CommonUtils.getCmdCharset()));
            this.type = type;
            this.inputStream = inputStream;
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
                    FileUtils.writeFileAppend(type + "Cmd.txt",sb.toString());
                    sb.delete(0, sb.length());
                    i = 0;
                }
            }
            if (sb.length() > 0) {
                FileUtils.writeFileAppend(type + "Cmd.txt",sb.toString());
            }
            logger.info("命令读取结束");
            String fileName = FileUtils.getDefaultLocalUrl(type + "Over.txt");
            Path overPath = Paths.get(fileName);
            Files.createFile(overPath);
            SvnOperateService svnOperateService = SpringContextHelper.getBean(SvnOperateService.class);
            svnOperateService.setRunning(false);
            bReader.close();
            inputStream.close();
        } catch (IOException e) {
            logger.error("命令输入流读取异常", e);
        }

    }


    private void deleteFile() throws IOException {
        String fileName = FileUtils.getDefaultLocalUrl(type + "Cmd.txt");
        String fileOver = FileUtils.getDefaultLocalUrl(type + "Over.txt");
        Path path = Paths.get(fileName);
        Path overPath = Paths.get(fileOver);


        if (Files.exists(path)) {
            Files.delete(path);
        }
        if (Files.exists(overPath)) {
            Files.delete(overPath);
        }


    }

}