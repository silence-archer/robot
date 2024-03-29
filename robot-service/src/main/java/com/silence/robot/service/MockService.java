package com.silence.robot.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silence.robot.domain.MockInfo;
import com.silence.robot.domain.MockRequestInfo;
import com.silence.robot.domain.MockResponseInfo;
import com.silence.robot.domain.RobotPage;
import com.silence.robot.mapper.TMockInfoMapper;
import com.silence.robot.model.TMockInfo;
import com.silence.robot.utils.BeanUtils;
import com.silence.robot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 挡板服务
 *
 * @author silence
 * @since 2020/10/7
 */
@Service
public class MockService {

    private final Logger logger = LoggerFactory.getLogger(MockService.class);

    @Resource
    private TMockInfoMapper mockInfoMapper;

    public List<MockInfo> getMockInfo() {
        List<TMockInfo> tMockInfos = mockInfoMapper.selectAll();
        return BeanUtils.copyList(MockInfo.class, tMockInfos);
    }

    public RobotPage<MockInfo> getMockInfoByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TMockInfo> tMockInfos = mockInfoMapper.selectAll();
        PageInfo<TMockInfo> pageInfo = new PageInfo<>(tMockInfos);
        List<MockInfo> mockInfos = BeanUtils.copyList(MockInfo.class, tMockInfos);

        return new RobotPage<>(pageInfo.getTotal(), mockInfos);
    }

    public RobotPage<MockInfo> getMockInfoByCondition(String mockName, String mockInput, String mockUrl, String mockModule, Integer page, Integer limit) {

        PageHelper.startPage(page, limit);
        List<TMockInfo> tMockInfos = mockInfoMapper.selectByCondition(mockName, mockInput, mockUrl, mockModule);
        PageInfo<TMockInfo> pageInfo = new PageInfo<>(tMockInfos);
        List<MockInfo> mockInfos = BeanUtils.copyList(MockInfo.class, tMockInfos);

        return new RobotPage<>(pageInfo.getTotal(), mockInfos);
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
        String module = mockRequestInfo.getModule();
        logger.info("本次挡板请求的uri为[{}],模块为[{}],请求参数为[{}]", uri, module, mockRequestInfo.getRequest());
        List<TMockInfo> tMockInfos = mockInfoMapper.selectByMockUrlAndModule(uri, module);
        AtomicReference<JSONObject> output = new AtomicReference<>(new JSONObject());
        tMockInfos.forEach(tMockInfo -> {
            logger.info("查询该url-[{}]的挡板入参为[{}]", uri, tMockInfo.getMockInput());
            JSONObject jsonObject = JSONObject.parseObject(tMockInfo.getMockInput());
            if (jsonObject.equals(mockRequestInfo.getRequest())) {
                logger.info("匹配成功-该url-[{}]的挡板返回值为[{}]", uri, jsonObject);
                output.set(JSONObject.parseObject(tMockInfo.getMockOutput()));
            }
        });
        return output.get();
    }

    public MockResponseInfo mockBob(MockRequestInfo mockRequestInfo) {
        String uri = mockRequestInfo.getUri();
        String module = mockRequestInfo.getModule();
        String username = mockRequestInfo.getUsername();
        logger.info("本次挡板请求的uri为[{}],模块为[{}],用户id为[{}]", uri, module, username);
        TMockInfo mockInfo = new TMockInfo();
        mockInfo.setMockModule(module);
        mockInfo.setMockUrl(uri);
        mockInfo.setCreateUser(username);
        List<TMockInfo> tMockInfos = mockInfoMapper.selectByQueryDto(mockInfo);
        MockResponseInfo mockResponseInfo = new MockResponseInfo();
        mockResponseInfo.setMockSuccess(false);
        if (CommonUtils.isNotEmpty(tMockInfos)) {
            mockResponseInfo.setMockSuccess(CommonUtils.isEquals(tMockInfos.get(0).getMockStatus(), "0"));
            mockResponseInfo.setResponseXml(tMockInfos.get(0).getMockOutput());
        }
        return mockResponseInfo;
    }
}
