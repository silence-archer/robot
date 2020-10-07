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

import com.silence.robot.mapper.TUserMapper;
import com.silence.robot.model.TUser;
import com.silence.robot.service.UserService;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数据库密码加密测试〉
 *
 * @author silence
 * @create 2019/12/5
 * @since 1.0.0
 */
//我们在测试使用 websocket的时候需要启动一个完整的服务器，而使用这个注解就是说每次测试都会选用一个随即可用的端口模拟启动一个完整的服务器
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("h2")
public class StringEncryptorTest {

    @Resource
    private StringEncryptor jasyptStringEncryptor;

    @Resource
    private UserService userService;

    @Value("${spring.datasource.username:mzh}")
    private String username;

    @Value("${spring.datasource.password:19930927}")
    private String password;
    @Value("${silence.subscribe.token}")
    private String token;

    @Test
    public void encry(){

        String mzh = jasyptStringEncryptor.encrypt("silence");
        System.out.println(mzh);
        System.out.println(username);
        System.out.println(password);
        System.out.println(token);
    }



}