package com.silence.robot.service.mermber;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.CronTaskProcLogDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.member.BusinessInfoDto;
import com.silence.robot.mapper.TBusinessInfoMapper;
import com.silence.robot.model.TBusinessInfo;
import com.silence.robot.model.TCronTaskProcLog;
import com.silence.robot.service.SequenceService;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;

/**
 *
 * @author silence
 * @date 2021/2/3
 */
@Service
public class BusinessInfoService {
    private final Logger logger = LoggerFactory.getLogger(BusinessInfoService.class);

    @Resource
    private TBusinessInfoMapper businessInfoMapper;

    @Resource
    private SequenceService sequenceService;

    public void addBusinessInfo(BusinessInfoDto businessInfoDto) {
        businessInfoDto.setBusinessName("Business"+sequenceService.getSequence("Business"));
        TBusinessInfo businessInfo = BeanUtils.copy(TBusinessInfo.class, businessInfoDto);
        businessInfoMapper.insert(businessInfo);
    }

    public void updateBusinessInfo(BusinessInfoDto businessInfoDto){
        TBusinessInfo businessInfo = BeanUtils.copy(TBusinessInfo.class, businessInfoDto);
        businessInfoMapper.updateByBusinessName(businessInfo);
    }

    public void deleteBusinessInfo(String businessName) {
        businessInfoMapper.deleteByBusinessName(businessName);
    }

    public BusinessInfoDto queryBusinessInfoById(String id) {
        return BeanUtils.copy(BusinessInfoDto.class, businessInfoMapper.selectByPrimaryKey(id));
    }

    public RobotPage<BusinessInfoDto> queryAllBusinessInfo(Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TBusinessInfo> businessInfos = businessInfoMapper.selectAll();
        PageInfo<TBusinessInfo> pageInfo = new PageInfo<>(businessInfos);
        List<BusinessInfoDto> businessInfoDtos = BeanUtils.copyList(BusinessInfoDto.class, businessInfos);
        List<BusinessInfoDto> collect = businessInfoDtos.stream()
            .filter(businessInfoDto -> CommonUtils.isEquals(businessInfoDto.getBusinessOperator(),
                HttpUtils.getLoginUserName()))
            .collect(Collectors.toList());
        return new RobotPage<>(pageInfo.getTotal(), CommonUtils.isEmpty(collect) ? businessInfoDtos : collect);
    }

    public RobotPage<BusinessInfoDto> queryBusinessInfoByBusinessDesc(String businessDesc, Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TBusinessInfo> businessInfos = businessInfoMapper.selectByBusinessDesc(businessDesc);
        PageInfo<TBusinessInfo> pageInfo = new PageInfo<>(businessInfos);
        List<BusinessInfoDto> businessInfoDtos = BeanUtils.copyList(BusinessInfoDto.class, businessInfos);
        return new RobotPage<>(pageInfo.getTotal(), businessInfoDtos);
    }
}
