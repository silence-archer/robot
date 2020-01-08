/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: HttpUtils
 * Author:   silence
 * Date:     2020/1/7 13:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.utils;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author silence
 * @create 2020/1/7
 * @since 1.0.0
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static Map doPost(String uri, Map<String, String> headers, String bodys) {

        HttpPost request = new HttpPost(uri);
        headers.forEach((key, value) -> request.setHeader(key, value));
        HttpEntity httpEntity = new StringEntity(bodys, ContentType.APPLICATION_JSON);
        request.setEntity(httpEntity);
        Map map = httpClientExecute(request);
        return map;
    }




    public static Map httpClientExecute(HttpRequestBase request) {
        Map map = null;
        InputStream content = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpResponse = httpClient.execute(request);
            int stat = httpResponse.getStatusLine().getStatusCode();
            if (stat != 200) {
                logger.error("请求失败 Http code: {}", stat);
                logger.error("http header error msg: {}", httpResponse.getFirstHeader("X-Ca-Error-Message"));
                logger.error("Http body error msg: {}", EntityUtils.toString(httpResponse.getEntity()));
                throw new BusinessException(ExceptionCode.HTTP_REQUEST_ERROR);
            }
            HttpEntity responseEntity = httpResponse.getEntity();
            content = responseEntity.getContent();
            map = FileUtils.getJsonMap(content);
        } catch (IOException e) {
            logger.error("使用流失败", e);

        } finally {
            try {
                content.close();
                httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                logger.error("关闭流失败", e);
            }
        }
        return map;
    }


}