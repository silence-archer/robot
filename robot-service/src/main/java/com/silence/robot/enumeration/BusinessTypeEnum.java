package com.silence.robot.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BusinessTypeEnum{
    /** 描述 */
    ASSET_MYSQL("asset-mysql","mysql", "asset"),
    COMMON_MYSQL("common-mysql","mysql", "common"),
    JOB_ADMIN_MYSQL("jobAdmin-mysql","mysql", "jobAdmin"),
    ACCT_CENTER_MYSQL("acctcenter-mysql","mysql", "acctcenter"),
    ACCT_DAY_END_MYSQL("acctdayend-mysql","mysql", "acctdayend"),
    CONVERT_MYSQL("convert-mysql","mysql", "convert"),
    ASSET_ORACLE("asset-oracle","oracle", "asset"),
    COMMON_ORACLE("common-oracle","oracle", "common"),
    JOB_ADMIN_ORACLE("jobAdmin-oracle","oracle", "jobAdmin"),
    ACCT_CENTER_ORACLE("acctcenter-oracle","oracle", "acctcenter"),
    ACCT_DAY_END_ORACLE("acctdayend-oracle","oracle", "acctdayend"),
    CONVERT_ORACLE("convert-oracle","oracle", "convert"),
    ;

    /** 状态码 */
    private final String code;

    private final String dbType;

    private final String businessKind;

    BusinessTypeEnum(String code, String dbType, String businessKind) {
        this.code = code;
        this.dbType = dbType;
        this.businessKind = businessKind;
    }

    public String getCode() {
        return code;
    }

    public String getDbType() {
        return dbType;
    }

    public String getBusinessKind() {
        return businessKind;
    }

    public static List<BusinessTypeEnum> getByDbType(String dbType) {
        return Arrays.stream(values()).filter(businessTypeEnum -> businessTypeEnum.dbType.equals(dbType)).collect(Collectors.toList());
    }
}