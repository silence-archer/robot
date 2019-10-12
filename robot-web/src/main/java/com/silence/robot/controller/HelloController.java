package com.silence.robot.controller;

import com.silence.robot.domain.TulingRequestInfo;
import com.silence.robot.domain.TulingResponseInfo;
import com.silence.robot.service.HelloService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello(@RequestParam("txt") String txt){
        TulingRequestInfo requestInfo = new TulingRequestInfo();
        requestInfo.setTxt(txt);
        TulingResponseInfo tulingResponseInfo = helloService.hello(requestInfo);
        return tulingResponseInfo.getText();
    }

    @PostMapping("/say")
    public TulingResponseInfo say(@RequestBody TulingRequestInfo requestInfo){
        TulingResponseInfo tulingResponseInfo = helloService.hello(requestInfo);
        return tulingResponseInfo;
    }

}
