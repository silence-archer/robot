package com.silence.robot.service.mermber;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.clock.MailSendService;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.member.BusinessInfoDto;
import com.silence.robot.domain.member.MemberInfoDto;
import com.silence.robot.domain.member.MemberTransDetailDto;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TMemberInfoMapper;
import com.silence.robot.model.TBusinessInfo;
import com.silence.robot.model.TMemberInfo;
import com.silence.robot.service.SequenceService;
import com.silence.robot.utils.BeanUtils;

/**
 *
 * @author silence
 * @date 2021/2/3
 */
@Service
public class MemberInfoService {
    private final Logger logger = LoggerFactory.getLogger(MemberInfoService.class);

    @Resource
    private SequenceService sequenceService;
    @Resource
    private TMemberInfoMapper memberInfoMapper;
    @Resource
    private MemberTransDetailService memberTransDetailService;
    @Resource
    private MailSendService mailSendService;

    public void addMemberInfo(MemberInfoDto memberInfoDto) {
        memberInfoDto.setMemberBalance(BigDecimal.ZERO);
        memberInfoDto.setMemberName("Member"+sequenceService.getSequence("Member"));
        TMemberInfo memberInfo = BeanUtils.copy(TMemberInfo.class, memberInfoDto);
        memberInfoMapper.insert(memberInfo);
    }

    public void updateMemberInfo(MemberInfoDto memberInfoDto){
        TMemberInfo memberInfo = BeanUtils.copy(TMemberInfo.class, memberInfoDto);
        memberInfoMapper.updateByMemberName(memberInfo);
    }

    public void updateBalance(MemberTransDetailDto memberTransDetailDto){
        TMemberInfo memberInfo = new TMemberInfo();
        memberInfo.setMemberName(memberTransDetailDto.getMemberName());
        if ("input".equals(memberTransDetailDto.getTransType())) {
            memberInfo.setMemberBalance(memberTransDetailDto.getTransAmount());
        }
        if ("output".equals(memberTransDetailDto.getTransType())) {
            BigDecimal memberBalance = memberInfoMapper.selectByMemberName(memberTransDetailDto.getMemberName())
                .getMemberBalance();
            if (memberBalance.compareTo(memberTransDetailDto.getTransAmount()) < 0) {
                throw new BusinessException(ExceptionCode.BALANCE_ERROR);
            }
            memberInfo.setMemberBalance(memberTransDetailDto.getTransAmount().negate());
        }
        memberInfoMapper.updateBalance(memberInfo);
        memberTransDetailDto.setId(null);
        memberTransDetailService.addMemberTransDetail(memberTransDetailDto);
        mailSendService.send("动账通知", memberTransDetailDto.toString(), "659885050@qq.com");
    }

    public void deleteMemberInfo(String memberName) {
        memberInfoMapper.deleteByMemberName(memberName);
        memberTransDetailService.deleteMemberTransDetail(memberName);
    }
    public MemberInfoDto queryByMemberName(String memberName) {

        return BeanUtils.copy(MemberInfoDto.class, memberInfoMapper.selectByMemberName(memberName));
    }


    public RobotPage<MemberInfoDto> queryAllMemberInfo(Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TMemberInfo> memberInfos = memberInfoMapper.selectAll();
        PageInfo<TMemberInfo> pageInfo = new PageInfo<>(memberInfos);
        List<MemberInfoDto> memberInfoDtos = BeanUtils.copyList(MemberInfoDto.class, memberInfos);
        return new RobotPage<>(pageInfo.getTotal(), memberInfoDtos);
    }

    public RobotPage<MemberInfoDto> queryByCondition(String memberDesc, String memberPhone, Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TMemberInfo> memberInfos = memberInfoMapper.selectByCondition(memberDesc, memberPhone);
        PageInfo<TMemberInfo> pageInfo = new PageInfo<>(memberInfos);
        List<MemberInfoDto> memberInfoDtos = BeanUtils.copyList(MemberInfoDto.class, memberInfos);
        return new RobotPage<>(pageInfo.getTotal(), memberInfoDtos);
    }


}
