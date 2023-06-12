/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SvnOperateController
 * Author:   silence
 * Date:     2019/10/23 14:35
 * Description: svn 操作
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.controller;

import com.silence.robot.domain.FileDto;
import com.silence.robot.domain.SvnInfo;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.SvnOperateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈svn 操作〉
 *
 * @author silence
 * @since 2019/10/23
 * 
 */
@RestController
public class SvnOperateController {

    @Resource
    private SvnOperateService svnOperateService;

    @GetMapping("/repoBrowser")
    public DataResponse<List<SvnInfo>> repoBrowser(@RequestParam String url){
        List<SvnInfo> list = svnOperateService.repoBrowser(url);
        return new DataResponse<>(list);
    }

    @GetMapping("/getSvnLogInfo")
    public DataResponse<FileDto> getSvnLogInfo(@RequestParam Integer pos){
        FileDto fileDto = svnOperateService.getSvnLogInfo(pos);
        return new DataResponse<>(fileDto);
    }

    @GetMapping("/getLocalSvnInfo")
    public DataResponse<List<FileDto>> getLocalSvnInfo(@RequestParam String url){
        List<FileDto> list = svnOperateService.getLocalSvnInfo(url);
        return new DataResponse<>(list);
    }

    @PostMapping("/postLocalSvnInfo")
    public DataResponse<List<FileDto>> postLocalSvnInfo(@RequestBody List<FileDto> fileDtos){
        List<FileDto> list = svnOperateService.getLocalSvnInfo(fileDtos);
        return new DataResponse<>(list);
    }

    @GetMapping("/getUrls")
    public DataResponse<List<SvnInfo>> getUrls(){
        List<SvnInfo> list = svnOperateService.getUrls();
        return new DataResponse<>(list);
    }

    @GetMapping("/checkoutStop")
    public DataResponse<?> checkoutStop(){
        svnOperateService.checkoutStop();
        return new DataResponse<>();
    }

    @GetMapping("/updateSvnInfo")
    public DataResponse<?> updateSvnInfo(@RequestParam String url){
        svnOperateService.updateSvnInfo(url);
        return new DataResponse<>();
    }

    @PostMapping("/checkout")
    public DataResponse<?> checkout(@RequestBody SvnInfo svnInfo){
        svnOperateService.checkout(svnInfo);
        return new DataResponse<>();
    }

}