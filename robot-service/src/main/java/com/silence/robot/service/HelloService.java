package com.silence.robot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.silence.robot.domain.TulingRequestInfo;
import com.silence.robot.domain.TulingResponseInfo;
import com.silence.robot.utils.FileUtils;
import com.silence.robot.utils.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author silence
 */
@Service
public class HelloService {

    @Value("${robot.api.url}")
    private String apiUrl;
    @Value("${robot.api.userId}")
    private String apiUserId;
    @Value("${robot.api.key}")
    private String apiKey;

    private static final Logger log = LoggerFactory.getLogger(HelloService.class);

    public TulingResponseInfo hello(TulingRequestInfo requestInfo) {
        HttpPost httpPost = new HttpPost(apiUrl);
        JSONObject jsonObject = FileUtils.readJsonFile("config/tuling.json", JSONObject.class);
        JSONObject perception = jsonObject.getJSONObject("perception");
        JSONObject userInfo = jsonObject.getJSONObject("userInfo");
        userInfo.put("apiKey", apiKey);
        userInfo.put("userId", apiUserId);
        JSONObject inputText = perception.getJSONObject("inputText");
        inputText.put("text", requestInfo.getTxt());
        String jsonString = jsonObject.toJSONString();
        HttpEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        TulingResponseInfo tulingResponseInfo = new TulingResponseInfo();
        JSONObject jsonMap = HttpUtils.httpClientExecute(httpPost);
        JSONArray results = jsonMap.getJSONArray("results");
        JSONObject values = results.getJSONObject(0).getJSONObject("values");
        String text = values.getString("text");
        tulingResponseInfo.setText(text);
        log.info("text: " + text);


        return tulingResponseInfo;
    }

    public String hello(String txt) {
        TulingRequestInfo requestInfo = new TulingRequestInfo();
        requestInfo.setTxt(txt);
        return hello(requestInfo).getText();

    }



}
