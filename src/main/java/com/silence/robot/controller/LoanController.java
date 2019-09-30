package com.silence.robot.controller;

import com.silence.robot.dto.DataRequest;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.dto.LoanDto;
import com.silence.robot.service.LoanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class LoanController {

    @Resource
    private LoanService loanService;

    @PostMapping("/loan")
    public DataResponse<BigDecimal> loanService(@RequestBody DataRequest<LoanDto> request){
        LoanDto loanDto = request.getData();
        BigDecimal intRate = loanService.countMinIntRate(loanDto.getPrincipal(), loanDto.getTotalAmt(), loanDto.getPeriods(), loanDto.getPrecision());
        DataResponse<BigDecimal> dataResponse = new DataResponse<>();
        dataResponse.setData(intRate);
        return dataResponse;
    }
}
