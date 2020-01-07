/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ImageOcrService
 * Author:   silence
 * Date:     2020/1/7 17:27
 * Description: 图片试别
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.silence.robot.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈图片试别〉
 *
 * @author silence
 * @create 2020/1/7
 * @since 1.0.0
 */
@Service
public class ImageOcrService {

    private final Logger logger = LoggerFactory.getLogger(ImageOcrService.class);


    @Value("${silence.aliyun.ocr.uri}")
    private String uri;
    @Value("${silence.aliyun.ocr.appCode}")
    private String appCode;

    public void generateXlsx(String fileName){
        String xlsxStr = FileUtils.readImage(uri, appCode, "xlsx", fileName);
        String name = FileUtils.convertFileName(fileName);
        File tmp_base64file = new File(name);
        if(!tmp_base64file.exists()){
            tmp_base64file.getParentFile().mkdirs();
        }
        try{
            tmp_base64file.createNewFile();

            // write
            FileWriter fw = new FileWriter(tmp_base64file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(xlsxStr);
            bw.flush();
            bw.close();
            fw.close();

            String exelFilePath = FileUtils.convertFileName(fileName, "xlsx");
            Runtime.getRuntime().exec("touch "+exelFilePath).destroy();
            Process exec = Runtime.getRuntime().exec("sed -i -e 's/\\\\n/\\n/g' " + name);
            exec.waitFor();
            exec.destroy();

            Process exec1 = null;
            String[] cmd = { "/bin/sh", "-c", "base64 -d " + name + " > " + exelFilePath };
            exec1 = Runtime.getRuntime().exec(cmd);
            exec1.waitFor();
            exec1.destroy();
        }catch (IOException e){
            logger.error("图片{}转excel失败", fileName, e);
        } catch (InterruptedException e) {
            logger.error("图片{}转excel失败", fileName, e);
        }


    }

}