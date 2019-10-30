/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SvnOperateService
 * Author:   silence
 * Date:     2019/10/22 15:30
 * Description: svn操作
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.silence.robot.domain.FileDto;
import com.silence.robot.domain.SvnInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.io.CmdOperateRunnable;
import com.silence.robot.io.InputStreamRunnable;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈svn操作〉
 *
 * @author silence
 * @create 2019/10/22
 * @since 1.0.0
 */
@Service
public class SvnOperateService {

    private final Logger logger = LoggerFactory.getLogger(SvnOperateService.class);

    private CmdOperateRunnable cmdOperateRunnable;

    private volatile Boolean running = false;

    public List<SvnInfo> repoBrowser(String url){
        List<SvnInfo> results = new ArrayList<>();
        String command = "svn list " + url;
        cmdOperate(command, true);
        List<String> list = new ArrayList<>();
        String errorMsg = "";
        try {
            list = Files.readAllLines(Paths.get("normalCmd.txt"));
            if(Files.exists(Paths.get("errorCmd.txt"))){
                errorMsg = new String(Files.readAllBytes(Paths.get("errorCmd.txt")));
            }

        } catch (IOException e) {
            logger.error("文件读取异常",e);
        }

        if(CommonUtils.isNotEmpty(errorMsg)){
            throw new BusinessException(ExceptionCode.CMD_ERROR);
        }
        String svnUrl = url.charAt(url.length()-1) == '/' ? url : url+"/";
        list.forEach(str -> {
            SvnInfo svnInfo = new SvnInfo();
            svnInfo.setName(str);
            svnInfo.setUrl(svnUrl + str);
            results.add(svnInfo);
        });
        return results;

    }

    public void checkout(SvnInfo svnInfo){
        if(CommonUtils.isEmpty(svnInfo.getLocalUrl())){
            String localUrl = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
            svnInfo.setLocalUrl(localUrl);
        }
        //防止并发
        synchronized (running){
            if(running){
                throw new BusinessException(ExceptionCode.CMD_CHECKOUT_ERROR);
            }
            String command = "svn checkout " + svnInfo.getUrl() + " " + svnInfo.getLocalUrl() + " --username " + svnInfo.getUsername() + " --password " + svnInfo.getPassword();
            cmdOperate(command, false);
            running = true;
        }

    }

    private void cmdOperate(String cmd, boolean isWait){
        cmdOperateRunnable = new CmdOperateRunnable(cmd, isWait);
        Thread thread = new Thread(cmdOperateRunnable,"CmdOperate");
        thread.start();

        if(isWait){
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.error("线程被打断",e);
            }
        }
    }

    public FileDto getSvnLogInfo(long pos){
        String errorContent = FileUtils.getFileContent("errorCmd.txt");
        FileDto fileDto = new FileDto();
        synchronized (running){
            if(CommonUtils.isNotEmpty(errorContent)){
                fileDto.setType("error");
                fileDto.setContent(errorContent);
                if(Files.exists(Paths.get("errorOver.txt"))){
                    running = false;
                    fileDto.setOverFlag(true);
                }
            }else{
                fileDto.setType("normal");
                fileDto = FileUtils.readLine(pos, 100, "normalCmd.txt");
                if(Files.exists(Paths.get("normalOver.txt")) && fileDto.getLineNum() < 100){
                    running = false;
                    fileDto.setOverFlag(true);
                }
            }
        }


        return fileDto;
    }

    public void checkoutStop(){

        synchronized (running){
            if(!running || Files.exists(Paths.get("normalOver.txt"))){
                throw new BusinessException(ExceptionCode.CMD_STATE_ERROR);
            }
            if(running){
                cmdOperateRunnable.destroyProcess();
                running = false;
            }

        }
    }


}