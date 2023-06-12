/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ImageMenuReadJob
 * Author:   silence
 * Date:     2020/1/9 9:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.job.task;

import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.job.RobotQuartzTask;
import com.silence.robot.service.SubscribeConfigInfoService;
import com.silence.robot.utils.Excelutils;
import com.silence.robot.utils.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @since 2020/1/9
 * 
 */
@Component
public class ImageMenuReadJob implements RobotQuartzTask {

    private final Logger logger = LoggerFactory.getLogger(ImageMenuReadJob.class);

    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;

    @Override
    public void execute() {
        int nowHour = LocalTime.now().getHour();
        int week = LocalDate.now().getDayOfWeek().getValue();
        logger.info("现在是周{}，{}点", week, nowHour);
        String fileName = FileUtils.getDefaultLocalUrl("1.xlsx");
        Workbook workbook = Excelutils.getWorkbook(fileName);
        String s;
        switch (week){
            case 1:
                s = getHourContent(nowHour, workbook, 2, 2);
                break;
            case 2:
                s = getHourContent(nowHour, workbook, 10, 2);
                break;
            case 3:
                s = getHourContent(nowHour, workbook, 2, 17);
                break;
            case 4:
                s = getHourContent(nowHour, workbook, 10, 17);
                break;
            case 5:
                s = getHourContent(nowHour, workbook, 2, 32);
                break;
            default:
                s = "周末好好休息，慰劳自己吧";
                break;
        }
        subscribeConfigInfoService.setConfigValue(ConfigEnum.DELICACY_ENUM, s, null);


    }

    private String getDayContent(int rowIndex, int cellIndex, Workbook workbook, String head){
        StringBuilder sb = new StringBuilder();
        sb.append(head);
        for (int i = rowIndex; i <13+rowIndex ; i++) {
            String s = Excelutils.readExcel(workbook, 0, i, cellIndex);
            sb.append(s).append(",");
        }
        return sb.toString();
    }

    private String getHourContent(int nowHour, Workbook workbook, int cellIndex, int rowIndex){
        String s;
        if (nowHour < 6){
            s = "现在还是凌晨，安心睡觉吧";
        }else if(nowHour >= 6 && nowHour < 10){
            s = getDayContent(rowIndex,cellIndex, workbook,"今天的早饭是\n");
        }else if(nowHour >= 10 && nowHour < 14){
            s = getDayContent(rowIndex, cellIndex+2, workbook, "今天的中饭是\n");
        }else if(nowHour >= 14){
            s = getDayContent(rowIndex, cellIndex+4, workbook, "今天的晚饭是\n");
        }else{
            s = "读取错误";
        }
        return s;
    }




}