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
import com.silence.robot.io.SplitReadFileRunnable;
import com.silence.robot.mapper.TSvnInfoMapper;
import com.silence.robot.model.TSvnInfo;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @Resource
    private TSvnInfoMapper tSvnInfoMapper;

    private volatile Boolean running = false;

    /**
     * 浏览svn信息
     *
     * @param url
     * @return
     */
    public List<SvnInfo> repoBrowser(String url) {
        List<SvnInfo> results = new ArrayList<>();
        String command = "svn list " + url;
        cmdOperate(command, true);
        List<String> list = FileUtils.readAllLines("normalCmd.txt");
        String errorMsg = FileUtils.readAllContents("errorCmd.txt");

        if (CommonUtils.isNotEmpty(errorMsg)) {
            throw new BusinessException(ExceptionCode.CMD_ERROR);
        }
        String svnUrl = url.charAt(url.length() - 1) == '/' ? url : url + "/";
        String[] split = svnUrl.split("/");
        String svnName = split[split.length - 1];
        list.forEach(str -> {
            SvnInfo svnInfo = new SvnInfo();
            svnInfo.setSvnName(svnName);
            svnInfo.setName(str);
            svnInfo.setUrl(svnUrl + str);
            results.add(svnInfo);
        });
        return results;

    }

    /**
     * 检出svn
     *
     * @param svnInfo
     */
    public void checkout(SvnInfo svnInfo) {
        if (CommonUtils.isEmpty(svnInfo.getUrl())) {
            throw new BusinessException(ExceptionCode.CHECK_NULL_ERROR);
        }

        if (CommonUtils.isEmpty(svnInfo.getLocalUrl())) {
            String localUrl = FileUtils.getDefaultLocalUrl(svnInfo.getSvnName());
            svnInfo.setLocalUrl(localUrl);
        }
        //防止并发
        synchronized (running) {
            if (running) {
                throw new BusinessException(ExceptionCode.CMD_CHECKOUT_ERROR);
            }
            String command = "svn checkout " + svnInfo.getUrl() + " " + svnInfo.getLocalUrl() + " --username " + svnInfo.getUsername() + " --password " + svnInfo.getPassword();
            cmdOperate(command, false);

        }
        TSvnInfo info = tSvnInfoMapper.selectByUrl(svnInfo.getUrl());
        if (info == null) {
            TSvnInfo tSvnInfo = new TSvnInfo();
            tSvnInfo.setUrl(svnInfo.getUrl());
            tSvnInfo.setLocalUrl(svnInfo.getLocalUrl());
            tSvnInfo.setRemark(svnInfo.getSvnName());
            //检出未完成
            tSvnInfo.setOverFlag("0");
            tSvnInfoMapper.insert(tSvnInfo);
        }else{
            info.setOverFlag("0");
            tSvnInfoMapper.updateByPrimaryKey(info);
        }

    }

    /**
     * 执行cmd命令
     *
     * @param cmd
     * @param isWait
     */
    public void cmdOperate(String cmd, boolean isWait) {
        String[] cmdStrs = {cmd};
        cmdOperate(cmdStrs, isWait);
    }

    /**
     * 执行cmd命令
     *
     * @param cmd
     * @param isWait
     */
    public void cmdOperate(String[] cmd, boolean isWait) {
        cmdOperateRunnable = new CmdOperateRunnable(cmd, isWait);
        Thread thread = new Thread(cmdOperateRunnable, "CmdOperate");
        thread.start();
        running = true;
        if (isWait) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.error("线程被打断", e);
            }
            running = false;
        }
    }

    /**
     * 获取svn检出日志信息
     *
     * @param pos
     * @return
     */
    public FileDto getSvnLogInfo(long pos) {
        String errorContent = FileUtils.readAllContents("errorCmd.txt");
        FileDto fileDto = new FileDto();
        synchronized (running) {
            if (CommonUtils.isNotEmpty(errorContent)) {
                fileDto.setType("error");
                fileDto.setContent(errorContent);
                if (FileUtils.exists("errorOver.txt")) {
                    fileDto.setOverFlag(true);
                    //导出出错，删除重新导入
                    List<TSvnInfo> infos = tSvnInfoMapper.selectByOverFlag("0");
                    if (infos.size() != 1) {
                        throw new BusinessException(ExceptionCode.QUERY_ERROR);
                    }
                    //暂时手动删除避免误删
                    tSvnInfoMapper.deleteByPrimaryKey(infos.get(0).getId());
                }
            } else {
                fileDto.setType("normal");
                String fileName = FileUtils.getDefaultLocalUrl("normalCmd.txt");
                fileDto = FileUtils.readLine(pos, 10, fileName);
                if (FileUtils.exists("normalOver.txt") && fileDto.getLineNum() < 10) {
                    fileDto.setOverFlag(true);
                    //检出完毕
                    List<TSvnInfo> infos = tSvnInfoMapper.selectByOverFlag("0");
                    if (infos.size() != 1) {
                        throw new BusinessException(ExceptionCode.QUERY_ERROR);
                    }
                    infos.get(0).setOverFlag("1");
                    tSvnInfoMapper.updateByPrimaryKey(infos.get(0));

                }
            }
        }


        return fileDto;
    }

    /**
     * 终止正在检出的任务
     */
    public void checkoutStop() {

        synchronized (running) {
            if (!running || FileUtils.exists("normalOver.txt")) {
                throw new BusinessException(ExceptionCode.CMD_STATE_ERROR);
            }
            if (running) {
                cmdOperateRunnable.destroyProcess();
                running = false;
            }

        }
    }

    /**
     * 获取本地已检出的svn地址
     *
     * @return
     */
    public List<SvnInfo> getUrls() {
        List<SvnInfo> svnInfoList = new ArrayList<>();
        List<TSvnInfo> list = tSvnInfoMapper.selectByOverFlag("1");
        list.forEach(tSvnInfo -> {
            SvnInfo svnInfo = new SvnInfo();
            svnInfo.setUrl(tSvnInfo.getUrl());
            svnInfo.setLocalUrl(tSvnInfo.getLocalUrl());
            svnInfo.setSvnName(tSvnInfo.getRemark());
            svnInfoList.add(svnInfo);
        });
        return svnInfoList;
    }

    /**
     * 获取本地svn信息
     *
     * @param url
     * @return
     */
    public List<FileDto> getLocalSvnInfo(String url) {
        if (CommonUtils.isEmpty(url)) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        String localUrl = tSvnInfoMapper.selectByUrl(url).getLocalUrl();
        List<FileDto> fileList = FileUtils.getFileList(localUrl);
        String cmd = "svn status -u -v " + localUrl;
        //防止并发
        synchronized (running) {
            if (running) {
                throw new BusinessException(ExceptionCode.CMD_CHECKOUT_ERROR);
            }
            cmdOperate(cmd, false);
        }

        return fileList;
    }

    /**
     * 获取本地svn信息
     *
     * @param fileDtos
     * @return
     */
    public List<FileDto> getLocalSvnInfo(List<FileDto> fileDtos) {
        synchronized (running) {
            if (running) {
                boolean notExistsError = FileUtils.notExists("errorOver.txt");
                boolean notExists = FileUtils.notExists("normalOver.txt");
                if (notExistsError || notExists) {
                    throw new BusinessException(ExceptionCode.RUNNING_ERROR);
                }
                String errorContent = FileUtils.readAllContents("errorCmd.txt");
                if (CommonUtils.isNotEmpty(errorContent)) {
                    throw new BusinessException(errorContent, new RuntimeException("命令执行出错"));
                }
                if (!notExists) {
                    fileDtos.forEach(fileDto -> {
                        if (!fileDto.isDirectory()) {
                            String fileName = FileUtils.getDefaultLocalUrl("normalCmd.txt");
                            searchContent(fileName, fileDto.getFilePath());

                        }
                    });
                }

            }
            //获取查询结果并处理
            fileDtos.forEach(fileDto -> {
                if (!fileDto.isDirectory()) {
                    String fileName = FileUtils.convertFileName(fileDto.getFilePath());
                    fileName = FileUtils.getDefaultLocalUrl(fileName);
                    String svnStatus = getFileSvnInfo(fileName);
                    fileDto.setFileSvnStatus(svnStatus);

                }
            });
            //删除中间文件 TODO
        }
        return fileDtos;
    }

    /**
     * 获取本地svn信息
     *
     * @param localUrl
     * @param fileName
     * @return
     */
    public List<FileDto> getLocalSvnInfo(String localUrl, String fileName) {
        return FileUtils.getFileList(localUrl + "\\" + fileName);
    }

    /**
     * 更新svn
     *
     * @param url
     */
    public void updateSvnInfo(String url) {
        if (CommonUtils.isEmpty(url)) {
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        TSvnInfo tSvnInfo = tSvnInfoMapper.selectByUrl(url);
        tSvnInfo.setOverFlag("0");
        tSvnInfoMapper.updateByPrimaryKey(tSvnInfo);
        String localUrl = tSvnInfo.getLocalUrl();
        String cmd = "svn update " + localUrl;
        cmdOperate(cmd, false);
    }


    private String getFileSvnInfo(String fileName) {
        String content = "";
        try {
            Path path = Paths.get(fileName);
            if (Files.notExists(path)) {
                throw new BusinessException(ExceptionCode.RUNNING_ERROR);
            }
            List<String> list = Files.readAllLines(path);
            if (list.size() != 1) {
                throw new BusinessException(ExceptionCode.QUERY_ERROR);
            }
            content = list.get(0);

            content = content.substring(0, 1).trim();
        } catch (IOException e) {
            logger.error("读取文件失败", e);
        }
        return content;

    }

    /**
     * 并发查询
     *
     * @param fileName
     * @param str
     */
    private void searchContent(String fileName, String str) {
        File file = new File(fileName);
        long sum = file.length();
        long total = sum;
        long startPos = 0L;
        long endPos = 0L;
        //改为一次读10000字节
        long taskCounts = 10000L;
        while (true) {
            SplitReadFileRunnable splitReadFileRunnable;
            if (taskCounts > total) {
                splitReadFileRunnable = new SplitReadFileRunnable(startPos, sum, file, str);
                FileUtils.threadPoolExecute(splitReadFileRunnable);
                break;
            } else {
                total = total - taskCounts;
                endPos = endPos + taskCounts;
                splitReadFileRunnable = new SplitReadFileRunnable(startPos, endPos, file, str);
                FileUtils.threadPoolExecute(splitReadFileRunnable);
                startPos = endPos;
            }

        }

    }

    public void setRunning(boolean running) {
        synchronized (this.running) {
            this.running = running;
        }

    }


}