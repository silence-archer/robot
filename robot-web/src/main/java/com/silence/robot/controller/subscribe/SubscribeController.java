/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SubscribeController
 * Author:   silence
 * Date:     2019/12/23 19:18
 * Description: 订阅号controller
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.controller.subscribe;

import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈订阅号controller〉
 *
 * @author silence
 * @create 2019/12/23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/signature")
public class SubscribeController {

    private final Logger logger = LoggerFactory.getLogger(SubscribeController.class);

    @Value("${silence.subscribe.token}")
    private String token;

    @GetMapping("/checkSignature")
    public String checkSignature(@RequestParam String signature,@RequestParam String timestamp,@RequestParam String nonce,@RequestParam String echostr){
        logger.info("请求的signature为{},timestamp为{},nonce为{},echostr为{},token为{}",signature,timestamp,nonce,echostr,token);
        List<String> list = Arrays.asList(token, timestamp, nonce);
        //对三个入参进行字典序排序
        Collections.sort(list);
        String sha1 = CommonUtils.sha1(list.get(0)+list.get(1)+list.get(2));
        logger.info("加密后的sha1为{}",sha1);
        if(sha1.equals(signature)){
            return echostr;
        }
        throw new BusinessException(ExceptionCode.ILLEGAL_REQUEST);
    }

    @PostMapping("/checkSignature")
    public String getMessage(@RequestBody String xmlMsg){
        logger.info("接收到用户发送的消息为{}",xmlMsg);
        return "更多信息请关注蓓婴佑小程序";
    }


}