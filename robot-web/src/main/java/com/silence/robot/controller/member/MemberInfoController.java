package com.silence.robot.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.member.BusinessInfoDto;
import com.silence.robot.domain.member.MemberInfoDto;
import com.silence.robot.domain.member.MemberTransDetailDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.mermber.MemberInfoService;

/**
 * 文件配置处理
 *
 * @author silence
 * @date 2021/2/5
 */
@RestController
public class MemberInfoController {
    @Resource
    private MemberInfoService memberInfoService;

    @PostMapping("/addMemberInfo")
    public DataResponse<?> addMemberInfo(@RequestBody MemberInfoDto memberInfoDto) {
        memberInfoService.addMemberInfo(memberInfoDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateMemberInfo")
    public DataResponse<?> updateMemberInfo(@RequestBody MemberInfoDto memberInfoDto){
        memberInfoService.updateMemberInfo(memberInfoDto);
        return new DataResponse<>();
    }

    @PostMapping("/updateBalance")
    public DataResponse<?> updateBalance(@RequestBody MemberTransDetailDto memberTransDetailDto){
        memberInfoService.updateBalance(memberTransDetailDto);
        return new DataResponse<>();
    }

    @GetMapping("/deleteMemberInfo")
    public DataResponse<?> deleteMemberInfo(@RequestParam String memberName) {
        memberInfoService.deleteMemberInfo(memberName);
        return new DataResponse<>();
    }

    @GetMapping("getMemberInfo")
    public DataResponse<List<MemberInfoDto>> getMemberInfo(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<MemberInfoDto> robotPage = memberInfoService.queryAllMemberInfo(page, limit);
        DataResponse<List<MemberInfoDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }

    @GetMapping("/getMemberInfoByCondition")
    public DataResponse<List<MemberInfoDto>> getMemberInfoByCondition(@RequestParam(required = false) String memberDesc, @RequestParam(required = false) String memberPhone, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<MemberInfoDto> robotPage = memberInfoService.queryByCondition(memberDesc, memberPhone, page, limit);
        DataResponse<List<MemberInfoDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }
}
