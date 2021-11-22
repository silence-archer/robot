package com.silence.robot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
     * @param request
     * @return com.alibaba.fastjson.JSONObject
     * @author silence
     * @date 2021/5/22 21:07
     */
    public JSONObject executeLoan(String uri, JSONObject request) {
        CommonUtils.deleteJsonEmptyStr(request);
        JSONObject sysHead = request.getJSONObject("sysHead");
        sysHead.put("seqNo", System.currentTimeMillis());
        sysHead.put("subSeqNo", System.currentTimeMillis());
        JSONObject jsonObject = HttpUtils.doPost("http://"+uri, request.toJSONString());
        JSONObject sysHeadResult = jsonObject.getJSONObject("sysHead");
        JSONArray rets = sysHeadResult.getJSONArray("ret");
        JSONObject ret = rets.getJSONObject(0);
        String retCode = ret.getString("retCode");
        String retMsg = ret.getString("retMsg");
        if ("000000".equals(retCode)) {
            return jsonObject.getJSONObject("body");
        }
        throw new BusinessException(retCode+ "-" + retMsg);

    }

    /**
     * 模拟接口发送
     *
     * @param request
     * @return com.alibaba.fastjson.JSONObject
     * @author silence
     * @date 2021/5/22 21:07
     */
    public JSONObject executeLoanVersion2(String uri, JSONObject request) {
        CommonUtils.deleteJsonEmptyStr(request);
        CommonUtils.updateJsonArray(request.getJSONObject("BODY"), "execute");
        JSONObject sysHead = request.getJSONObject("SYS_HEAD");
        sysHead.put("SEQ_NO", CommonUtils.getUuid());
        sysHead.put("TRAN_TIMESTAMP", LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")));
        sysHead.put("TRAN_MODE", "ONLINE");
        sysHead.put("USER_LANG", "CHINESE");
        JSONObject jsonObject = HttpUtils.doPost("http://"+uri, request.toJSONString());
        JSONObject sysHeadResult = jsonObject.getJSONObject("SYS_HEAD");
        JSONArray rets = sysHeadResult.getJSONArray("RET");
        JSONObject ret = rets.getJSONObject(0);
        String retCode = ret.getString("RET_CODE");
        String retMsg = ret.getString("RET_MSG");
        if ("000000".equals(retCode)) {
            return jsonObject.getJSONObject("BODY");
        }
        throw new BusinessException(retMsg);

    }

}
