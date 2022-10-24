package com.silence.robot.clock;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.constants.RobotConstants;
import com.silence.robot.domain.DataDictDto;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.domain.clock.DingClockDto;
import com.silence.robot.mapper.TDingClockInfoMapper;
import com.silence.robot.model.TDataDict;
import com.silence.robot.model.TDingClockInfo;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;

@Service
public class DingClockService {

    @Resource
    private TDingClockInfoMapper dingClockInfoMapper;
    @Resource
    private SchedulingClickService schedulingClickService;

    public List<DingClockDto> getEffectiveList() {
        return BeanUtils.copyList(DingClockDto.class, dingClockInfoMapper.selectByStatus(RobotConstants.YES));
    }

    public RobotPage<DingClockDto> getDingClockList(Integer page, Integer limit) {
        page = page == null ? 1 : page;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        PageHelper.startPage(page, limit);
        List<TDingClockInfo> dingClockInfos = dingClockInfoMapper.selectAll();
        PageInfo<TDingClockInfo> pageInfo = new PageInfo<>(dingClockInfos);
        List<DingClockDto> dingClockDtos = BeanUtils.copyList(DingClockDto.class, dingClockInfos);
        return new RobotPage<>(pageInfo.getTotal(), dingClockDtos);
    }

    public void addDingClock(DingClockDto dingClockDto) {
        dingClockInfoMapper.insert(BeanUtils.copy(TDingClockInfo.class, dingClockDto));
    }

    public void updateDingClock(DingClockDto dingClockDto) {
        dingClockInfoMapper.updateByPrimaryKey(BeanUtils.copy(TDingClockInfo.class, dingClockDto));
    }

    public void deleteDingClock(String id) {
        dingClockInfoMapper.deleteByPrimaryKey(id);
    }

    public void dingClick(String id) {
        TDingClockInfo dingClockInfo = dingClockInfoMapper.selectByPrimaryKey(id);
        schedulingClickService.onceRun(BeanUtils.copy(DingClockDto.class, dingClockInfo));
    }
}
