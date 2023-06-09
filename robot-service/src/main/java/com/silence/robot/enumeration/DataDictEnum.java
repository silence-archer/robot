package com.silence.robot.enumeration;

/**
 * 数据字典枚举
 *
 * @author silence
 * @since 2021/5/26
 */
public enum DataDictEnum {
    //
    IP_OWNER_ENUM("ipOwner", "获取用户ip", "ipOwnerService");
    private final String name;

    private final String desc;

    private final String className;

    DataDictEnum(String name, String desc, String className) {
        this.className = className;
        this.desc = desc;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getClassName() {
        return className;
    }
}
