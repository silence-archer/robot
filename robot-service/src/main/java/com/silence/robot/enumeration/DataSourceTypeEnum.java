package com.silence.robot.enumeration;
/**
 * 数据源类型
 * @author silence
 * @date 2020/5/29 23:02
 */
public enum DataSourceTypeEnum {

    READ("read","读库"),
    WRITE("write","写库"),
    ;


    private final String name;

    private final String desc;


    DataSourceTypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
