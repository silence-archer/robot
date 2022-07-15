package com.silence.robot.controller;

import com.alibaba.fastjson.JSONObject;
import com.silence.robot.dto.DataRequest;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.dto.LoanDto;
import com.silence.robot.enumeration.ConfigEnum;
import com.silence.robot.service.LoanService;
import com.silence.robot.service.SubscribeConfigInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author silence
 */
@RestController
public class LoanController {

    @Resource
    private LoanService loanService;

    @Resource
    private SubscribeConfigInfoService subscribeConfigInfoService;

    @PostMapping("/loanTry")
    public DataResponse<BigDecimal> loanTryService(@RequestBody DataRequest<LoanDto> request){
        LoanDto loanDto = request.getData();
        BigDecimal intRate = loanService.countMinIntRate(loanDto.getPrincipal(), loanDto.getTotalAmt(), loanDto.getPeriods(), loanDto.getPrecision());
        DataResponse<BigDecimal> dataResponse = new DataResponse<>();
        dataResponse.setData(intRate);
        return dataResponse;
    }

    @PostMapping("/loan")
    public DataResponse<JSONObject> loanService(@RequestBody DataRequest<JSONObject> request){
        JSONObject data = request.getData();
        String configValue = subscribeConfigInfoService.getConfigValue(ConfigEnum.FREE_MARKER_VERSION_ENUM);
        JSONObject body = null;
        if ("2.0".equals(configValue)) {
            body = loanService.executeLoanVersion2(request.getApiCd(), data);
        }else {
            body = loanService.executeLoan(request.getApiCd(), data);
        }
        return new DataResponse<>(body);
    }

    @PostMapping("/loan306")
    public DataResponse<JSONObject> loan306Service(@RequestBody JSONObject request){
        return new DataResponse<>(loanService.executeLoanVersion306(request));
    }
}
