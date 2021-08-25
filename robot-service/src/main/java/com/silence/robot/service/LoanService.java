package com.silence.robot.service;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * @author silence
 */
@Service
public class LoanService {

    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;


    public BigDecimal countMinIntRate(BigDecimal princp, BigDecimal totalAmt, int prids, int points) {

        for (int i = 1; i < setPoint(points); i++) {
            BigDecimal destTotalAmt = countTotalAmt(princp, convertYearIntRate(i, points), prids);
            if (destTotalAmt.subtract(totalAmt).abs().compareTo(new BigDecimal("0.01")) < 0) {
                return convertIntRate(i, points);
            }
        }
        throw new BusinessException(ExceptionCode.PRECISION_ERROR);
    }

    public BigDecimal countMaxIntRate(BigDecimal princp, BigDecimal totalAmt, int prids, int points) {
        for (int i = setPoint(points) - 1; i > 0; i--) {
            BigDecimal destTotalAmt = countTotalAmt(princp, convertYearIntRate(i, points), prids);
            if (destTotalAmt.subtract(totalAmt).abs().compareTo(new BigDecimal("0.01")) < 0) {
                return convertIntRate(i, points);
            }
        }
        throw new BusinessException(ExceptionCode.PRECISION_ERROR);
    }

    public BigDecimal calculateTotalAmt(BigDecimal princp, BigDecimal intRate, int prids) {
        intRate = new BigDecimal("" + intRate).divide(new BigDecimal("1200"), MathContext.DECIMAL128);
        return countTotalAmt(princp, intRate, prids).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal countTotalAmt(BigDecimal princp, BigDecimal intRate, int prids) {

        BigDecimal pow = intRate.add(BigDecimal.ONE).pow(prids);
        return princp.multiply(intRate).
                multiply(pow).
                divide(pow.
                        subtract(BigDecimal.ONE), MathContext.DECIMAL128);
    }

    private BigDecimal convertYearIntRate(int intRate, int points) {
        return new BigDecimal("" + intRate).divide(new BigDecimal(1200 * getPoint(points) + ""), MathContext.DECIMAL128);
    }

    private BigDecimal convertIntRate(int intRate, int points) {
        return new BigDecimal("" + intRate).divide(new BigDecimal(getPoint(points) + ""), MathContext.DECIMAL128);
    }

    private int setPoint(int point) {
        int result = 100;
        for (int i = 0; i < point; i++) {
            result = result * 10;
        }
        return result;
    }

    private int getPoint(int point) {
        int result = 1;
        for (int i = 0; i < point; i++) {
            result = result * 10;
        }
        return result;
    }

    /**
     * 模拟接口发送
     *
     * @param data
     * @return com.alibaba.fastjson.JSONObject
     * @author silence
     * @date 2021/5/22 21:07
     */
    public JSONObject executeLoan(JSONObject data) {
        JSONObject request = new JSONObject();
        request.put("body", data);
        JSONObject sysHead = new JSONObject();
        String tranDate = subscribeConfigInfoService.getConfigValue(ConfigEnum.LOAN_DATE_ENUM);
        sysHead.put("tranDate", tranDate);
        sysHead.put("seqNo", System.currentTimeMillis());
        sysHead.put("subSeqNo", System.currentTimeMillis());
        request.put("sysHead", sysHead);
        Map map = HttpUtils.doPost("/cl/inq/trial/schedule", request.toJSONString());
        List<Map> rets = (List<Map>) map.get("ret");
        String retCode = rets.get(0).get("retCode").toString();
        String retMsg = rets.get(0).get("retMsg").toString();
        if ("000000".equals(retCode)) {
            return new JSONObject((Map) map.get("body"));
        }
        throw new BusinessException(retCode+ "-" + retMsg);

    }

}
