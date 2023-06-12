/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CmdOperateRunnable
 * Author:   silence
 * Date:     2019/10/28 19:21
 * Description: process执行线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silence.robot.utils.CommonUtils;

/**
 * 〈一句话功能简述〉<br> 
 * 〈process执行线程〉
 *
 * @author silence
 * @since 2019/10/28
 * 
 */
public class CmdOperateRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(CmdOperateRunnable.class);


    private String[] cmd;

    private boolean isWait;

    private Process process;


    public CmdOperateRunnable(String[] cmd, boolean isWait){
        this.cmd = cmd;
        this.isWait = isWait;
    }

    public void destroyProcess(){
        process.destroy();
    }

    @Override
    public void run() {
        try{
            if(cmd.length == 1){
                logger.info("待执行的命令为{}",cmd[0]);
                process = Runtime.getRuntime().exec(cmd[0]);
            }else {
                logger.info("待执行的命令为{}",CommonUtils.arrayToString(cmd));
                process = Runtime.getRuntime().exec(cmd);
            }

            Thread t1 = new Thread(new InputStreamRunnable(process.getErrorStream(),"error"),"ErrorStream");
            t1.start();
            Thread t2 = new Thread(new InputStreamRunnable(process.getInputStream(),"normal"),"InputStream");
            t2.start();
            process.waitFor();
            if(isWait){
                t1.join();
                t2.join();
            }
        }catch (Exception e){
            logger.error("命令读取异常",e);
        }

    }
}