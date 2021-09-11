package com.silence.robot.encryptor;

import com.silence.robot.controller.FreeMarkerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/5
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("sqlite")
public class FreeMarkerTest {
    @Resource
    private FreeMarkerController freeMarkerController;

    @Test
    public void test() {
    }



}
