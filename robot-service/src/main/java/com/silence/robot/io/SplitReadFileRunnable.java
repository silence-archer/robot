/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SplitReadFileRunnable
 * Author:   silence
 * Date:     2019/10/31 17:15
 * Description: split read
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.io;

import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈split read〉
 *
 * @author silence
 * @create 2019/10/31
 * @since 1.0.0
 */
public class SplitReadFileRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(SplitReadFileRunnable.class);

    private long startPos;
    private long endPos;
    private File file;
    private String content;

    public SplitReadFileRunnable(long startPos, long endPos, File file, String content) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.file = file;
        this.content = content;
    }

    @Override
    public void run() {
        List<String> list = FileUtils.splitReadFile(startPos, endPos, file);
        logger.info("开始字节{},终止字节{},查询内容{},当前读取到的文件片段" + list, startPos, endPos, content);
        List<String> targets = list.stream().filter(str -> str.contains(content)).collect(Collectors.toList());
        if (targets.size() > 0) {
            String target = targets.get(0);
            if (CommonUtils.isNotEmpty(target)) {
                String fileName = FileUtils.convertFileName(content);
                FileUtils.writeFile(fileName, target);

            }

        }
    }
}