package com.silence.robot.service;

import com.silence.robot.domain.EurekaManageDto;
import com.silence.robot.domain.UserInfo;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import org.apache.catalina.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        JSONObject jsonObject = XML.toJSONObject(new InputStreamReader(in));
        logger.info("当前注册在eureka上的信息为{}", jsonObject);
        JSONObject applications = jsonObject.getJSONObject("applications");
        List<EurekaManageDto> instanceList = new ArrayList<>();
        Object object = applications.get("application");
        if (object == null) {
            return instanceList;
        }
        try {

            if(object instanceof JSONArray){
                JSONArray serviceInstance = applications.getJSONArray("application");
                for (int i = 0; i < serviceInstance.length(); i++) {
                    JSONObject application = (JSONObject) serviceInstance.get(i);
                    getInstanceInfo(instanceList, application);
                }
            }else{
                JSONObject application = applications.getJSONObject("application");
                getInstanceInfo(instanceList, application);
            }
        } catch (JSONException e) {
            logger.error("XML转JSON失败", e);
        }

        return instanceList;




    }

    private void getInstanceInfo(List<EurekaManageDto> instanceList, JSONObject application) {
        String appName = application.getString("name");
        Object instanceObj = application.get("instance");
        if(instanceObj instanceof JSONArray){
            JSONArray instances = application.getJSONArray("instance");
            for (int i = 0; i < instances.length(); i++) {
                JSONObject instance = (JSONObject) instances.get(i);
                getServerInfo(instanceList, appName, instance);
            }
        }else{
            JSONObject instance = (JSONObject) instanceObj;
            getServerInfo(instanceList, appName, instance);
        }

    }

    private void getServerInfo(List<EurekaManageDto> instanceList, String appName, JSONObject instance) throws JSONException {
        String ipAddr = instance.getString("ipAddr");
        String instanceId = instance.getString("instanceId");
        String hostName = instance.getString("hostName");
        String status = instance.getString("status");
        JSONObject portObj = instance.getJSONObject("port");
        int port = portObj.getInt("content");
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
