/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SchedulerJobTest
 * Author:   silence
 * Date:     2019/12/26 10:41
 * Description: scheduler test
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.encryptor;

import com.silence.robot.utils.SpringContextHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈scheduler test〉
 *
 * @author silence
 * @create 2019/12/26
 * @since 1.0.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("h2")
public class SchedulerJobTest {

    @Resource
    private Scheduler scheduler;

    @Test
    public void schedulerTest() throws Exception {

    }
}