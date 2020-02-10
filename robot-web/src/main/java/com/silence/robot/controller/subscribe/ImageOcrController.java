/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ImageOcrController
 * Author:   silence
 * Date:     2020/1/7 17:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.controller.subscribe;

import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.ImageOcrService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @create 2020/1/7
 * @since 1.0.0
 */
@RestController
@RequestMapping("/signature")
public class ImageOcrController {

    @Resource
    private ImageOcrService imageOcrService;

    @GetMapping("/generateXlsx")
    public DataResponse<?> generateXlsx(@RequestParam String fileName){
        imageOcrService.generateXlsx(fileName);

        return new DataResponse<>();

    }

}