package com.silence.robot.service.mermber;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.member.BusinessInfoDto;
import com.silence.robot.domain.member.MerchantInfoDto;
import com.silence.robot.mapper.TMerchantInfoMapper;
import com.silence.robot.model.TBusinessInfo;
import com.silence.robot.model.TMerchantInfo;
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
public class MerchantInfoService {
    private final Logger logger = LoggerFactory.getLogger(MerchantInfoService.class);

    @Resource
    private TMerchantInfoMapper merchantInfoMapper;

    @Resource
    private SequenceService sequenceService;

    public void addMerchantInfo(MerchantInfoDto merchantInfoDto) {
        merchantInfoDto.setMerchantName("Merchant"+sequenceService.getSequence("Merchant"));
        TMerchantInfo merchantInfo = BeanUtils.copy(TMerchantInfo.class, merchantInfoDto);
        merchantInfoMapper.insert(merchantInfo);
    }

    public void updateMerchantInfo(MerchantInfoDto merchantInfoDto){
        TMerchantInfo merchantInfo = BeanUtils.copy(TMerchantInfo.class, merchantInfoDto);
        merchantInfoMapper.updateByMerchantName(merchantInfo);
    }

    public void deleteMerchantInfo(String merchantName) {
        merchantInfoMapper.deleteByMerchantName(merchantName);
    }

    public RobotPage<MerchantInfoDto> queryAllMerchantInfo(Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TMerchantInfo> merchantInfos = merchantInfoMapper.selectAll();
        PageInfo<TMerchantInfo> pageInfo = new PageInfo<>(merchantInfos);
        List<MerchantInfoDto> merchantInfoDtos = BeanUtils.copyList(MerchantInfoDto.class, merchantInfos);
        List<MerchantInfoDto> collect = merchantInfoDtos.stream()
            .filter(merchantInfoDto -> CommonUtils.isEquals(merchantInfoDto.getMerchantOperator(),
                HttpUtils.getLoginUserName()))
            .collect(Collectors.toList());
        return new RobotPage<>(pageInfo.getTotal(), CommonUtils.isEmpty(collect) ? merchantInfoDtos : collect);
    }

    public RobotPage<MerchantInfoDto> queryByCondition(String merchantDesc, Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TMerchantInfo> merchantInfos = merchantInfoMapper.selectByCondition(merchantDesc);
        PageInfo<TMerchantInfo> pageInfo = new PageInfo<>(merchantInfos);
        List<MerchantInfoDto> merchantInfoDtos = BeanUtils.copyList(MerchantInfoDto.class, merchantInfos);
        return new RobotPage<>(pageInfo.getTotal(), merchantInfoDtos);
    }


}
