package com.silence.robot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.EurekaManageDto;
import com.silence.robot.domain.UserInfo;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import com.silence.robot.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * eureka注册列表管理
 *
 * @author silence
 * @date 2020/12/12
 */
@Service
public class EurekaManageService {

    private final Logger logger = LoggerFactory.getLogger(EurekaManageService.class);

    @Resource
    private UserService userService;

    public List<EurekaManageDto> getEurekaInstanceList(String eurekaUrl) {
        logger.info("当前eureka地址为{}", eurekaUrl);
        InputStream in = HttpUtils.getStreamByHttp(eurekaUrl);
        JSONObject jsonObject = XmlUtils.xmlToJsonByInputStream(in);
        logger.info("当前注册在eureka上的信息为{}", jsonObject);
        JSONObject applications = jsonObject.getJSONObject("applications");
        List<EurekaManageDto> instanceList = new ArrayList<>();
        Object object = applications.get("application");
        if (object == null) {
            return instanceList;
        }


        if (object instanceof List) {
            JSONArray serviceInstance = applications.getJSONArray("application");
            for (Object o : serviceInstance) {
                JSONObject application = new JSONObject((LinkedHashMap) o);
                getInstanceInfo(instanceList, application);
            }
        } else {
            JSONObject application = applications.getJSONObject("application");
            getInstanceInfo(instanceList, application);
        }


        return instanceList;


    }

    private void getInstanceInfo(List<EurekaManageDto> instanceList, JSONObject application) {
        String appName = application.getString("name");
        Object instanceObj = application.get("instance");
        if (instanceObj instanceof List) {
            JSONArray instances = application.getJSONArray("instance");
            for (Object o : instances) {
                JSONObject instance = new JSONObject((LinkedHashMap) o);
                getServerInfo(instanceList, appName, instance);
            }
        } else {
            JSONObject instance = new JSONObject((LinkedHashMap) instanceObj);
            getServerInfo(instanceList, appName, instance);
        }

    }

    private void getServerInfo(List<EurekaManageDto> instanceList, String appName, JSONObject instance) throws JSONException {
        String ipAddr = instance.getString("ipAddr");
        String instanceId = instance.getString("instanceId");
        String hostName = instance.getString("hostName");
        String status = instance.getString("status");
        JSONObject portObj = instance.getJSONObject("port");
        int port = portObj.getIntValue("#text");
        String homePageUrl = instance.getString("homePageUrl");

        EurekaManageDto eurekaManageDto = new EurekaManageDto();
        eurekaManageDto.setName(appName);
        eurekaManageDto.setIpAddr(ipAddr);
        eurekaManageDto.setInstanceId(instanceId);
        eurekaManageDto.setHostName(hostName);
        eurekaManageDto.setStatus(status);
        eurekaManageDto.setPort(port);
        eurekaManageDto.setHomePageUrl(homePageUrl);
        UserInfo userInfo = new UserInfo();
        userInfo.setIpAddr(ipAddr);
        List<UserInfo> userInfos = userService.getUserInfoByCondition(userInfo);
        if (CommonUtils.isNotEmpty(userInfos)) {
            eurekaManageDto.setOwner(userInfos.get(0).getNickname());
        }

        instanceList.add(eurekaManageDto);
    }
}
