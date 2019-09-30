package com.silence.robot.controller;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.service.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class ImageController {

    @Resource
    private ImageService imageService;

    @GetMapping("/verifyCode")
    public JSONObject getImageCode(HttpSession httpSession){
        String imageWithVerifyCode = imageService.createImageWithVerifyCode(100, 40, 4, httpSession);
        imageWithVerifyCode = "data:image/png;base64,"+imageWithVerifyCode;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imageCode",imageWithVerifyCode);
        return jsonObject;
    }

}
