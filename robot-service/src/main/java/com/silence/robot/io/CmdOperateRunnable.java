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

import com.silence.robot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br> 
 * 〈process执行线程〉
 *
 * @author silence
 * @create 2019/10/28
 * @since 1.0.0
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
                process = Runtime.getRuntime().exec(cmd[0]);
            }else {
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