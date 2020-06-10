/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TransactionTest
 * Author:   silence
 * Date:     2020/4/16 22:06
 * Description: 事务传播机制测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.encryptor;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈事务传播机制测试〉
 *
 * @author silence
 * @create 2020/4/16
 * @since 1.0.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("h2")
public class TransactionTest {

    @Resource
    private UserService userService;

    @Test
    public void test() {
        UserInfo userInfo = new UserInfo();
        userInfo.setRoleNo("123");
        userInfo.setSign("321");
        userInfo.setAvatar("321");
        userInfo.setCreateTime("321");
        userInfo.setId("321");
        userInfo.setImageCode("321");
        userInfo.setUsername("321");
        userInfo.setPassword("321");
        userInfo.setNickname("321");
        userInfo.setImageWithVerifyCode("321");
        userInfo.setRoleName("321");

        userService.addUser(userInfo);
    }
}