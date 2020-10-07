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
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalTime;

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
        userInfo.setRoleNo("1111");
        userInfo.setSign("321");
        StringBuilder stringBuilder = new StringBuilder(1000);
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(123);
        }
        userInfo.setAvatar(stringBuilder.toString());
        userInfo.setUsername("321");
        userInfo.setPassword("321");
        userInfo.setNickname("321");
        userInfo.setRoleName("321");

        userService.addUser(userInfo);
    }

    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        robot.delay(5000);
        long start = System.currentTimeMillis();
        while (true) {
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_F1);
            robot.keyRelease(KeyEvent.VK_F1);
            robot.delay(100);
            long end = System.currentTimeMillis();
            if (end - start > 1000*60*15) {
                robot.keyPress(KeyEvent.VK_F2);
                robot.keyRelease(KeyEvent.VK_F2);
                robot.delay(100);
                robot.keyPress(KeyEvent.VK_F3);
                robot.keyRelease(KeyEvent.VK_F3);
                robot.delay(100);
                start = end;
            }

        }
    }
}