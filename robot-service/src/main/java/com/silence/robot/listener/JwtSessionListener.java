package com.silence.robot.listener;

import com.silence.robot.domain.UserInfo;
import com.silence.robot.utils.HttpUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/13
 */
public class JwtSessionListener {
    private static final ConcurrentHashMap<String, String> IMAGE_CODE_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, UserInfo> USERNAME_MAP = new ConcurrentHashMap<>();

    public static void putImageCode(String imageCode) {
        IMAGE_CODE_MAP.put(HttpUtils.getIpAddress(), imageCode+"-"+System.currentTimeMillis());
    }

    public static String getImageCode() {
        return IMAGE_CODE_MAP.get(HttpUtils.getIpAddress());
    }

    public static void removeImageCode() {
        IMAGE_CODE_MAP.remove(HttpUtils.getIpAddress());
    }

    public static void putUserInfo(UserInfo userInfo) {
        USERNAME_MAP.put(HttpUtils.getIpAddress(), userInfo);
    }

    public static UserInfo getUserInfo() {
        return USERNAME_MAP.get(HttpUtils.getIpAddress());
    }

    public static void removeUserInfo() {
        USERNAME_MAP.remove(HttpUtils.getIpAddress());
    }

}
