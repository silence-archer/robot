package com.silence.robot.dto;



import java.math.BigDecimal;


public class LoanDto {

    private BigDecimal principal;

    private BigDecimal intRate;

    private Integer periods;

    private BigDecimal totalAmt;

    private Integer precision;

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getIntRate() {
        return intRate;
    }

    public void setIntRate(BigDecimal intRate) {
        this.intRate = intRate;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    @Override
    public String toString() {
        return "LoanDto{" +
                "principal=" + principal +
                ", intRate=" + intRate +
                ", periods=" + periods +
                ", totalAmt=" + totalAmt +
                ", precision=" + precision +
                '}';
    }
}
