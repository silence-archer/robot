package com.silence.robot.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.silence.robot.context.GlobalContext;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.utils.CommonUtils;
import com.silence.robot.utils.HttpUtils;
import com.silence.robot.utils.TraceUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
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
     * @param request
     * @return com.alibaba.fastjson.JSONObject
     * @author silence
     * @since 2021/5/22 21:07
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
     * @since 2021/5/22 21:07
     */
    public JSONObject executeLoanVersion2(String uri, JSONObject request) {
        CommonUtils.deleteJsonEmptyStr(request);
        CommonUtils.updateJsonArray(request.getJSONObject("BODY"), "execute");
        JSONObject sysHead = request.getJSONObject("SYS_HEAD");
        sysHead.put("SEQ_NO", CommonUtils.getUuid());
        sysHead.put("TRAN_TIMESTAMP", LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")));
        sysHead.put("TRAN_MODE", "ONLINE");
        sysHead.put("USER_LANG", "CHINESE");
        if ("1400".equals(sysHead.getString("MESSAGE_TYPE"))) {
            JSONObject appHead = new JSONObject();
            appHead.put("PGUP_OR_PGDN", "1");
            appHead.put("TOTAL_NUM", Integer.MAX_VALUE+"");
            appHead.put("CURRENT_NUM", "1");
            appHead.put("TOTAL_FLAG", "E");
            request.put("APP_HEAD", appHead);
        }
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

    public JSONObject executeLoanVersion306(JSONObject request) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        String apiCd = servletRequestAttributes.getRequest()
            .getHeader("apiCd");
        String ipAddr = servletRequestAttributes.getRequest()
            .getHeader("ipAddr");
        String port = servletRequestAttributes.getRequest()
            .getHeader("port");
        Map<String, String> headers = new HashMap<>();
        headers.put("thread_tag", "thread_tag");
        JSONObject jsonObject = new JSONObject();
        JSONObject requestObject = new JSONObject();
        requestObject.put("ask","ASSET");
        requestObject.put("answer","ASSET");
        requestObject.put("serialNo",System.currentTimeMillis()+"");
        requestObject.put("serviceScene","UNKNOWN");
        requestObject.put("idemSerialNo",System.currentTimeMillis()+"");
        requestObject.put("transDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        requestObject.put("inboundIdemSerialNo",System.currentTimeMillis()+"");
        requestObject.put("inboundTransDateTime",LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        requestObject.put("tenantId","000");
        requestObject.put("channelNo","001");
        requestObject.put("sign",System.currentTimeMillis()+"");
        String loan306Header = subscribeConfigInfoService.getConfigValue("loan306_header", null);
        jsonObject.put(loan306Header, requestObject.toJSONString());
        JSONObject userObject = new JSONObject();
        userObject.put("authId","123");
        userObject.put("token",System.currentTimeMillis()+"");
        userObject.put("organId","10");
        userObject.put("organName","根机构");
        userObject.put("operatorId","123");
        userObject.put("operatorName","测试柜员");
        String loan306UserHeader = subscribeConfigInfoService.getConfigValue("loan306_user_header", null);
        jsonObject.put(loan306UserHeader, userObject.toJSONString());
        headers.put("thread_context", Base64.getEncoder().encodeToString(jsonObject.toJSONString().getBytes(
            StandardCharsets.UTF_8)));
        JSONObject args0 = new JSONObject();
        args0.put("args0", request.toJSONString());
        return HttpUtils.doPost("http://" + ipAddr + ":" + port + apiCd , headers, args0.toJSONString());
    }

    public JSONObject executeLoan217(String remoteIp, Integer remotePort, String tranCode, String sceneValue) {
        String loan306Header = subscribeConfigInfoService.getConfigValue("loan306_header", null);
        Map<String, String> headers = new HashMap<>();
        String loanHeader = "ask=923" + "&" + "answer=923" + "&" + "serialNo=I" + GlobalContext.getHttpClientContext(
            "serial_no") + "&" + "idemSerialNo=" + GlobalContext.getHttpClientContext("serial_no") + "&"
            + "serviceScene=10" + "&" + "transDateTime=" + GlobalContext.getHttpClientContext("date") + "&"
            + "tenantId=000" + "&" + "channelNo=001" + "&" + "sign=" + "S" + GlobalContext.getHttpClientContext(
            "serial_no");
        headers.put(loan306Header, loanHeader);
        String loan306UserHeader = subscribeConfigInfoService.getConfigValue("loan306_user_header", null);
        String loanUserHeader = "authId=927" + "&" + "token=T" + GlobalContext.getHttpClientContext("serial_no") + "&"
            + "operatorId=927" + "&" + "operatorName=archer" + "&" + "organId=" + GlobalContext.getHttpClientContext(
            "organ_id") + "&" + "organName=silence";
        headers.put(loan306UserHeader, loanUserHeader);

        return HttpUtils.doPost("http://" + remoteIp + ":" + remotePort + tranCode , headers, sceneValue);

    }
}
