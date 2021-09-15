package com.silence.robot.controller;

import com.silence.robot.domain.TulingRequestInfo;
import com.silence.robot.domain.TulingResponseInfo;
import com.silence.robot.service.HelloService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author silence
 */
@RestController
public class HelloController {

    @Resource
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello(@RequestParam("txt") String txt){

        return helloService.hello(txt);
    }

    @PostMapping("/say")
    public TulingResponseInfo say(@RequestBody TulingRequestInfo requestInfo){
        return helloService.hello(requestInfo);
    }

}
