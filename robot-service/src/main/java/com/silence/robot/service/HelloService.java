package com.silence.robot.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.silence.robot.domain.TulingRequestInfo;
import com.silence.robot.domain.TulingResponseInfo;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

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
        Map map = FileUtils.readJsonFile("config/tuling.json", Map.class);
        Map perception = (Map) map.get("perception");
        Map userInfo = (Map) map.get("userInfo");
        userInfo.put("apiKey", apiKey);
        userInfo.put("userId", apiUserId);
        Map inputText = (Map) perception.get("inputText");
        inputText.put("text", requestInfo.getTxt());
        String jsonString = JSONObject.toJSONString(map);
        HttpEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        TulingResponseInfo tulingResponseInfo = new TulingResponseInfo();
        Map jsonMap = CommonUtils.httpClientExecute(httpPost);
        List<Map> results = (List<Map>) jsonMap.get("results");
        Map values = (Map) results.get(0).get("values");
        String text = (String) values.get("text");
        tulingResponseInfo.setText(text);
        log.info("text: " + text);


        return tulingResponseInfo;
    }



}
