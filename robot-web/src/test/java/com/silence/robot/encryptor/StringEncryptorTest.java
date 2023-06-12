/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: StringEncryptorTest
 * Author:   silence
 * Date:     2019/12/5 14:50
 * Description: 数据库密码加密测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.encryptor;

import com.silence.robot.clock.MailSendService;
import com.silence.robot.mapper.TUserMapper;
import com.silence.robot.model.TUser;
import com.silence.robot.service.UserService;
import com.silence.robot.utils.FileUtils;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数据库密码加密测试〉
 *
 * @author silence
 * @since 2019/12/5
 * 
 */
//我们在测试使用 websocket的时候需要启动一个完整的服务器，而使用这个注解就是说每次测试都会选用一个随即可用的端口模拟启动一个完整的服务器
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("sqlite")
public class StringEncryptorTest {

    @Resource
    private StringEncryptor jasyptStringEncryptor;

    @Resource
    private UserService userService;

    @Value("${spring.datasource.username:mzh}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    @Value("${silence.subscribe.token}")
    private String token;
    @Resource
    private MailSendService mailSendService;

    @Test
    public void encry(){

        String mzh = jasyptStringEncryptor.encrypt("mzh");
        System.out.println(mzh);
        System.out.println(username);
        System.out.println(password);
        System.out.println(token);
    }

    @Test
    public void sendMail() {


        mailSendService.send("测试", "", "@139.com");
        System.out.println("111111111");
    }
}