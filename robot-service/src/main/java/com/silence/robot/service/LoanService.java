package com.silence.robot.service;

import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class LoanService {


    public BigDecimal countMinIntRate(BigDecimal princp, BigDecimal totalAmt, int prids, int points){

        for(int i=1; i<setPoint(points); i++){
            BigDecimal destTotalAmt = countTotalAmt(princp, convertYearIntRate(i,points), prids);
            if(destTotalAmt.subtract(totalAmt).abs().compareTo(new BigDecimal("0.01")) < 0){
                return convertIntRate(i,points);
            }
        }
        throw new BusinessException(ExceptionCode.PRECISION_ERROR);
    }

    public BigDecimal countMaxIntRate(BigDecimal princp, BigDecimal totalAmt, int prids, int points){
        for(int i=setPoint(points)-1; i>0; i--){
            BigDecimal destTotalAmt = countTotalAmt(princp, convertYearIntRate(i,points), prids);
            if(destTotalAmt.subtract(totalAmt).abs().compareTo(new BigDecimal("0.01")) < 0){
                return convertIntRate(i,points);
            }
        }
        throw new BusinessException(ExceptionCode.PRECISION_ERROR);
    }

    public BigDecimal calculateTotalAmt(BigDecimal princp, BigDecimal intRate, int prids){
        intRate = new BigDecimal(""+intRate).divide(new BigDecimal("1200"), MathContext.DECIMAL128);
        return countTotalAmt(princp, intRate, prids).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal countTotalAmt(BigDecimal princp, BigDecimal intRate, int prids){

        BigDecimal pow = intRate.add(BigDecimal.ONE).pow(prids);
        return princp.multiply(intRate).
                multiply(pow).
                divide(pow.
                        subtract(BigDecimal.ONE),MathContext.DECIMAL128);
    }

    private BigDecimal convertYearIntRate(int intRate, int points){
        return new BigDecimal(""+intRate).divide(new BigDecimal(1200*getPoint(points)+""), MathContext.DECIMAL128);
    }

    private BigDecimal convertIntRate(int intRate, int points){
        return new BigDecimal(""+intRate).divide(new BigDecimal(getPoint(points)+""), MathContext.DECIMAL128);
    }

    private int setPoint(int point){
        int result = 100;
        for(int i=0; i<point; i++){
            result = result*10;
        }
        return result;
    }

    private int getPoint(int point){
        int result = 1;
        for(int i=0; i<point; i++){
            result = result*10;
        }
        return result;
    }

}
