package com.silence.robot.service;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.MockInfo;
import com.silence.robot.domain.MockRequestInfo;
import com.silence.robot.mapper.TMockInfoMapper;
import com.silence.robot.model.TMockInfo;
import com.silence.robot.utils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 挡板服务
 *
 * @author silence
 * @date 2020/10/7
 */
@Service
public class MockService {

    @Resource
    private TMockInfoMapper mockInfoMapper;

    public List<MockInfo> getMockInfo() {
        List<TMockInfo> tMockInfos = mockInfoMapper.selectAll();
        return BeanUtils.copyList(MockInfo.class, tMockInfos);
    }
    public void addMock(MockInfo mockInfo) {
        TMockInfo tMockInfo = BeanUtils.copy(TMockInfo.class, mockInfo);
        mockInfoMapper.insert(tMockInfo);
    }
    public void updateMock(MockInfo mockInfo) {
        TMockInfo tMockInfo = BeanUtils.copy(TMockInfo.class, mockInfo);
        mockInfoMapper.updateByPrimaryKey(tMockInfo);
    }
    public void deleteMock(String id) {
        mockInfoMapper.deleteByPrimaryKey(id);
    }

    public JSONObject mock(MockRequestInfo mockRequestInfo) {
        String uri = mockRequestInfo.getUri();
        List<TMockInfo> tMockInfos = mockInfoMapper.selectByMockUrl(uri);
        AtomicReference<JSONObject> output = new AtomicReference<>(new JSONObject());
        tMockInfos.forEach(tMockInfo -> {
            JSONObject jsonObject = JSONObject.parseObject(tMockInfo.getMockInput());
            if (jsonObject.toJSONString().equals(mockRequestInfo.getRequest().toJSONString())) {
                output.set(JSONObject.parseObject(tMockInfo.getMockOutput()));
            }
        });
        return output.get();
    }
}
