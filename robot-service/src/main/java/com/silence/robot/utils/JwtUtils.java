package com.silence.robot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Verification;
import com.silence.robot.domain.UserInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * JWT工具类
 *
 * @author silence
 * @since 2021/9/12
 */
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static String createToken(UserInfo userInfo) {
        //签发对象
        return JWT.create().withAudience(userInfo.getId())
                //发行时间
                .withIssuedAt(new Date())
                //过期时间
                .withExpiresAt(DateUtils.addMinutes(new Date(), 30))
                //载荷
                .withClaim("userInfo", BeanUtils.beanToMap(userInfo))
                //加密
                .sign(Algorithm.HMAC256(userInfo.getId()+CommonUtils.SLAT));
    }

    public static void verifyToken(String token, String secret) {
        try {
            Verification require = JWT.require(Algorithm.HMAC256(secret + CommonUtils.SLAT));
            require.build().verify(token);
        } catch (Exception e) {
            logger.error("token校验失败", e);
            throw new BusinessException(ExceptionCode.TOKEN_VERIFY_ERROR);
        }

    }

    public static String getAudience(String token) {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            logger.error("token解析失败", e);
            throw new BusinessException(ExceptionCode.TOKEN_PARSE_ERROR);
        }

        return audience;

    }

    public static Date getExpiresAt(String token) {
        Date expiresAt = null;
        try {
            expiresAt = JWT.decode(token).getExpiresAt();
        } catch (Exception e) {
            logger.error("token解析失败", e);
            throw new BusinessException(ExceptionCode.TOKEN_PARSE_ERROR);
        }

        return expiresAt;

    }

    public static UserInfo getUserInfo(String token) {
        Claim claim = null;
        try {
            claim = JWT.decode(token).getClaim("userInfo");
        } catch (Exception e) {
            logger.error("token解析失败", e);
            throw new BusinessException(ExceptionCode.TOKEN_PARSE_ERROR);
        }

        return claim.as(UserInfo.class);
    }

}
