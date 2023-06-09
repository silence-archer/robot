package com.silence.robot.encryptor;

import com.silence.robot.controller.FreeMarkerController;
import com.silence.robot.service.FreeMarkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author silence
 * @since 2021/9/5
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("sqlite")
public class FreeMarkerTest {
    @Resource
    private FreeMarkerController freeMarkerController;

    @Resource
    private FreeMarkerService freeMarkerService;

    @Test
    public void test() {
        String content = "baseAcctNo\t账户账号/卡号\tString\t150\tN\n" +
                "acctDesc\t账户(贷款)描述\tString\t240\tN\n" +
                "autoHandle\t是否自动处置\tString\t1\tN\n" +
                "term\t存期\tString\t8\tN\n" +
                "termType\t期限类型\tString\t8\tN\n" +
                "startDate\t起始/开始日期\tString\t8\tY\n" +
                "signDate\t合同签订日期\tString\t8\tN\n" +
                "effectDate\t生效日期\tString\t20\tN\n" +
                "maturityDate\t到期日\tString\t8\tY\n" +
                "allDraInd\t通兑标志\tString\t1\tN\n" +
                "allDepInd\t通存标志\tString\t1\tN\n" +
                "regionFlag\t区域内外标识\tString\t1\tN\n" +
                "ownershipType\t归属种类\tString\t2\tY\n" +
                "crDrInd\t借贷方标志\tString\t1\tN\n" +
                "clientNo\t客户号\tString\t50\tY\n" +
                "cmisloanNo\t借据号\tString\t50\tN\n" +
                "intArray\t利率数组\tArray\t\tY\n" +
                "intClass\t利息类型\tString\t20\tY\n" +
                "realRate\t执行利率\tNumber\t15,8\tY\n" +
                "cycleFreq\t结息频率\tString\t75\tN\n" +
                "nextCycleDate\t下一结息日\tString\t8\tN\n" +
                "intDay\t结息日\tString\t2\tN\n" +
                "yearBasis\t年基准天数\tString\t3\tY\n" +
                "monthBasis\t月基准\tString\t3\tY\n" +
                "minIntRate\t分户最低利率\tNumber\t15,8\tN\n" +
                "maxIntRate\t分户最高利率\tNumber\t15,8\tN\n" +
                "intApplType\t利率启用方式\tString\t1\tY\n" +
                "rateEffectType\t利率生效方式\tString\t1\tY\n" +
                "intCap\t是否资本化标志\tString\t150\tN\n" +
                "intType\t利率类型\tString\t3\tY\n" +
                "penaltyOdiRateType\t罚息利率使用方式\tString\t1\tN\n" +
                "calcBeginDate\t利息计算起始日\tString\t8\tN\n" +
                "calcEndDate\t利息计算截止日\tString\t8\tN\n" +
                "rollDate\t利率变更日期\tString\t8\tN\n" +
                "rollFreq\t利率变更频率\tString\t2\tN\n" +
                "rollDay\t利率变更日\tString\t2\tN\n" +
                "actualRate\t行内利率\tNumber\t15,8\tN\n" +
                "floatRate\t浮动利率\tNumber\t15,8\tN\n" +
                "spreadRate\t浮动点数\tNumber\t15,8\tN\n" +
                "spreadPercent\t浮动百分比\tNumber\t5,2\tN\n" +
                "acctSpreadRate\t分户级浮动百分点\tNumber\t15,8\tN\n" +
                "acctPercentRate\t分户级浮动百分比\tNumber\t5,2\tN\n" +
                "acctFixedRate\t分户级固定利率\tNumber\t15,8\tN\n" +
                "intInd\t是否计息标志\tString\t1\tY\n" +
                "calcByInt\t是否随/按正常利率浮动\tString\t1\tN\n" +
                "intArray\t利率数组\tArray\t\tY\n" +
                "huntingStatus\t持续扣款标志\tString\t1\tN\n" +
                "tfLoanType\tTF贷款类型\tString\t50\tN\n" +
                "creditNo\t项目号\tString\t50\tN\n" +
                "ccy\t币种\tString\t50\tY\n" +
                "origLoanAmt\t初始合同金额\tNumber\t17,2\tY\n" +
                "sofCountry\t资金来源国家\tString\t3\tN\n" +
                "sofState\t资金来源省\tString\t10\tN\n" +
                "ddEndDate\t发放截止日\tString\t8\tY\n" +
                "priPenalty\t是否收取罚息\tString\t1\tN\n" +
                "prodType\t产品类型\tString\t50\tY\n" +
                "marketingProd\t营销产品类型\tString\t20\tY\n" +
                "marketingProdDesc\t营销产品名称\tString\t300\tY\n" +
                "intPenalty\t是否收取利息复利\tString\t1\tN\n" +
                "prePayRate\t提前还本的补偿金率\tNumber\t15,8\tN\n" +
                "uiPrepayment\t折扣贷款利息罚息率\tNumber\t15,8\tN\n" +
                "prMinAmt\t提前还款的最低补偿金\tNumber\t17,2\tN\n" +
                "uiMinAmt\t折扣贷款最低罚金\tNumber\t17,2\tN\n" +
                "gracePeriod\t宽限期的天数\tString\t5\tN\n" +
                "graceEndMonth\t是否宽限到月末\tString\t3\tN\n" +
                "forceGrace\t宽限期遇节假日是否顺延\tString\t1\tN\n" +
                "graceChargeInt\t到期本金在宽限期内是否计收利息\tString\t1\tN\n" +
                "autoSettle\t是否自动回收\tString\t1\tY\n" +
                "odPriPenalty\t是否收取罚息的复利\tString\t1\tN\n" +
                "odIntPenalty\t是否收取复利的复利\tString\t1\tN\n" +
                "homeBranch\t营销机构\tString\t20\tY\n" +
                "tfRefNo\tTF模块业务编号\tString\t50\tN\n" +
                "preRepayDeal\t还款计划变更方式（变额不变期或者变期不变额标志）\tString\t1\tN\n" +
                "syncFinalBilling\t是否利随本清\tString\t1\tN\n" +
                "purpose\t贷款目的\tString\t6\tN\n" +
                "guarantyStyle\t担保方式\tString\t6\tN\n" +
                "analysis1\t房屋类型\tString\t6\tN\n" +
                "analysis2\t统计标志2\tString\t6\tN\n" +
                "analysis3\t统计标志3\tString\t6\tN\n" +
                "contractNo\t合同号\tString\t50\tY\n" +
                "openBranch\t开户机构\tString\t20\tY\n" +
                "agreementOpenDate\t签约开户时间\tString\t8\tN\n" +
                "endDate\t结束日期\tString\t20\tN\n" +
                "autoLoanClassify\t自动更改状态标记\tString\t1\tN\n" +
                "clSettle\t结算信息\tArray\t\tY\n" +
                "settleBranch\t结算分行\tString\t20\tY\n" +
                "settleAcctCcy\t结算账户币种\tString\t3\tY\n" +
                "bankInOut\t是否行内行外\tString\t1\tN\n" +
                "settleAcctSeqNo\t结算账户序列号\tLong\t8\tN\n" +
                "settleCcy\t结算币种\tString\t3\tY\n" +
                "settleAmt\t结算金额\tNumber\t17,2\tN\n" +
                "settleXrate\t结算汇率\tNumber\t15,8\tN\n" +
                "settleXrateId\t汇兑方式1\tString\t1\tN\n" +
                "autoBlocking\t自动锁定标记\tString\t1\tN\n" +
                "priority\t优先级\tString\t5\tN\n" +
                "settleWeight\t结算权重\tNumber\t5,2\tN\n" +
                "trustedPayNo\t受托支付编号\tString\t50\tN\n" +
                "settleClient\t结算客户号\tString\t20\tY\n" +
                "settleNo\t结算编号\tString\t50\tN\n" +
                "settleAcctClass\t结算账户分类\tString\t3\tY\n" +
                "settleMethod\t结算方式\tString\t3\tY\n" +
                "payRecInd\t收付款标志\tString\t3\tY\n" +
                "amtType\t金额类型\tString\t3\tY\n" +
                "tranType\t交易类型\tString\t30\tN\n" +
                "settleBaseAcctNo\t结算账号\tString\t150\tN\n" +
                "settleProdType\t结算产品类型\tString\t50\tN\n" +
                "profitRatio\t分润/出资比例\tString\t17\tN\n" +
                "contributiveRatio\t出资比例\tString\t17\tN\n" +
                "isSelfSupport\t是否自营\tString\t17\tN\n" +
                "settleAcctName\t结算账户名称\tString\t200\tN\n" +
                "payeeBankName\t收款行行名\tString\t60\tN\n" +
                "payeeBankCode\t收款行行号\tString\t12\tN\n" +
                "freezeType\t冻结支付方式\tString\t1\tN\n" +
                "clSettle\t结算信息\tArray\t\tY\n" +
                "managerBank\t银团贷款管理行\tString\t20\tN\n" +
                "arrBank\t银团贷款安排行\tString\t20\tN\n" +
                "ssiEndDate\t贴息截止日期\tString\t8\tN\n" +
                "schedMode\t还款方式\tString\t3\tY\n" +
                "calcTimes\t气球贷计算期次\tString\t5\tN\n" +
                "lender\t贷款人\tString\t20\tN\n" +
                "applyBranch\t管理行(LOAN_MANAGER)\tString\t20\tN\n" +
                "acctExec\t客户经理\tString\t30\tN\n" +
                "profitCentre\t利润中心\tString\t20\tN\n" +
                "anytimeRec\t随借随还标志\tString\t1\tN\n" +
                "firPeriod\t首段期数\tString\t5\tN\n" +
                "midPeriod\t累进间隔期数\tString\t5\tN\n" +
                "addAmt\t累进金额\tNumber\t17,2\tN\n" +
                "addRatio\t累进比例\tNumber\t5,2\tN\n" +
                "fiveCategory\t贷款五级分类\tString\t6\tN\n" +
                "isStampTax\t是否收取印花税\tString\t1\tY\n" +
                "contriButeAmt\t参与行出资金额\tNumber\t17,2\tN\n" +
                "taxableInd\t税收标识\tString\t1\tN";
        freeMarkerService.getInterfaceFreeMarker("11", "22", 9030, content);
    }



}
