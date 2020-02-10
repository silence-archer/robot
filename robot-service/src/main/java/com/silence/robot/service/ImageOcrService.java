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

import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private SvnOperateService svnOperateService;

    public void generateXlsx(String fileName) {
        String xlsxStr = FileUtils.readImage(uri, appCode, "xlsx", FileUtils.getDefaultLocalUrl(fileName));
        String tmpFileName = FileUtils.convertFileName(fileName);
        FileUtils.writeFile(tmpFileName, xlsxStr);

        String excelFileName = FileUtils.convertFileName(fileName, "xlsx");
        String cmd = "touch " + FileUtils.getDefaultLocalUrl(excelFileName);
        svnOperateService.cmdOperate(cmd, true);
        cmd = "sed -i -e 's/\\\\n/\\n/g' " + tmpFileName;
        svnOperateService.cmdOperate(cmd, true);

        String[] cmdStr = {"/bin/sh", "-c", "base64 -d " + FileUtils.getDefaultLocalUrl(tmpFileName) + " > " + FileUtils.getDefaultLocalUrl(excelFileName)};
        svnOperateService.cmdOperate(cmdStr, true);


    }

}