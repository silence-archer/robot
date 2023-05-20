package com.silence.robot.service.mermber;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.member.MemberInfoDto;
import com.silence.robot.domain.member.MemberTransDetailDto;
import com.silence.robot.mapper.TMemberTransDetailMapper;
import com.silence.robot.model.TMemberInfo;
import com.silence.robot.model.TMemberTransDetail;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;

/**
 *
 * @author silence
 * @date 2021/2/3
 */
@Service
public class MemberTransDetailService {
    private final Logger logger = LoggerFactory.getLogger(MemberTransDetailService.class);

    @Resource
    private TMemberTransDetailMapper memberTransDetailMapper;
    @Resource
    private MemberInfoService memberInfoService;

    public void addMemberTransDetail(MemberTransDetailDto memberTransDetailDto) {
        TMemberTransDetail memberTransDetail = BeanUtils.copy(TMemberTransDetail.class, memberTransDetailDto);
        memberTransDetailMapper.insert(memberTransDetail);
    }

    public void updateMemberTransDetail(MemberTransDetailDto memberTransDetailDto){
        TMemberTransDetail memberTransDetail = BeanUtils.copy(TMemberTransDetail.class, memberTransDetailDto);
        memberTransDetailMapper.updateByPrimaryKey(memberTransDetail);
    }

    public void deleteMemberTransDetail(String memberName) {
        memberTransDetailMapper.deleteByMemberName(memberName);
    }

    public RobotPage<MemberTransDetailDto> queryAllMemberTransDetail(Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TMemberTransDetail> memberTransDetails = memberTransDetailMapper.selectAll();
        PageInfo<TMemberTransDetail> pageInfo = new PageInfo<>(memberTransDetails);
        List<MemberTransDetailDto> memberTransDetailDtos = BeanUtils.copyList(MemberTransDetailDto.class, memberTransDetails);
        fill(memberTransDetailDtos);
        return new RobotPage<>(pageInfo.getTotal(), memberTransDetailDtos);
    }


    public RobotPage<MemberTransDetailDto> queryByCondition(String memberDesc, String memberPhone, Integer page, Integer limit) {
        List<String> collect = memberInfoService.queryByCondition(memberDesc, memberPhone, null, null)
            .getList()
            .stream()
            .map(MemberInfoDto::getMemberName)
            .collect(Collectors.toList());
        if (CommonUtils.isEmpty(collect)) {
            return new RobotPage<>(0L, new ArrayList<>(0));
        }
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TMemberTransDetail> memberTransDetails = memberTransDetailMapper.selectByCondition(collect);
        PageInfo<TMemberTransDetail> pageInfo = new PageInfo<>(memberTransDetails);
        List<MemberTransDetailDto> memberTransDetailDtos = BeanUtils.copyList(MemberTransDetailDto.class, memberTransDetails);
        fill(memberTransDetailDtos);
        return new RobotPage<>(pageInfo.getTotal(), memberTransDetailDtos);
    }

    private void fill(List<MemberTransDetailDto> memberTransDetailDtos) {
        memberTransDetailDtos.forEach(memberTransDetailDto -> {
            MemberInfoDto memberInfoDto = memberInfoService.queryByMemberName(memberTransDetailDto.getMemberName());
            memberTransDetailDto.setMemberDesc(memberInfoDto.getMemberDesc());
            memberTransDetailDto.setMemberPhone(memberInfoDto.getMemberPhone());
        });
    }
}
