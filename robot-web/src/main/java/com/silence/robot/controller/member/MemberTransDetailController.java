package com.silence.robot.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.member.MemberInfoDto;
import com.silence.robot.domain.member.MemberTransDetailDto;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.mermber.MemberTransDetailService;

/**
 * 文件配置处理
 *
 * @author silence
 * @since 2021/2/5
 */
@RestController
public class MemberTransDetailController {
    @Resource
    private MemberTransDetailService memberTransDetailService;

    @GetMapping("getMemberTransDetail")
    public DataResponse<List<MemberTransDetailDto>> getMemberTransDetail(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<MemberTransDetailDto> robotPage = memberTransDetailService.queryAllMemberTransDetail(page, limit);
        DataResponse<List<MemberTransDetailDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }

    @GetMapping("/getMemberTransDetailByCondition")
    public DataResponse<List<MemberTransDetailDto>> getMemberTransDetailByCondition(@RequestParam(required = false) String memberDesc, @RequestParam(required = false) String memberPhone, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        RobotPage<MemberTransDetailDto> robotPage = memberTransDetailService.queryByCondition(memberDesc, memberPhone, page, limit);
        DataResponse<List<MemberTransDetailDto>> response = new DataResponse<>(robotPage.getList());
        response.setCount(robotPage.getTotal());
        return response;
    }
}
