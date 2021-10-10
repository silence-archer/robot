package com.silence.robot.listener;

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
    private static final ConcurrentHashMap<String, String> TOKEN_MAP = new ConcurrentHashMap<>();


    public static void putImageCode(String imageCode) {
        IMAGE_CODE_MAP.put(HttpUtils.getIpAddress(), imageCode+"-"+System.currentTimeMillis());
    }

    public static String getImageCode() {
        return IMAGE_CODE_MAP.get(HttpUtils.getIpAddress());
    }

    public static void removeImageCode() {
        IMAGE_CODE_MAP.remove(HttpUtils.getIpAddress());
    }

    public static void putToken(String userId, String token) {
        TOKEN_MAP.put(userId, token);
    }

    public static String getToken(String userId) {
        return TOKEN_MAP.get(userId);
    }

    public static void removeUserInfo(String userId) {
        TOKEN_MAP.remove(userId);
    }

}
