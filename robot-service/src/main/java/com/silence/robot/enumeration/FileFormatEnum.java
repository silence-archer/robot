package com.silence.robot.enumeration;

/**
 * 文件格式枚举
 *
 * @author silence
 * @since 2020/10/6
 */
public enum FileFormatEnum {

    //
    JSON("json","json格式字符串","json"),
    VERTICAL_SEPARATOR_SKIP("|","竖线分隔字符串","\\|"),
    ASCII_SEPARATOR("0x01","竖线分隔字符串", new String(new byte[]{0x01})),
    VERTICAL_SEPARATOR(",","逗号分隔", ",");

    private final String name;

    private final String desc;
    private final String value;

    FileFormatEnum(String name, String desc, String value){
        this.name = name;
        this.desc = desc;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public String getValue(){
        return value;
    }

    public static FileFormatEnum getEnumByName(String name) {
        for (FileFormatEnum fileFormatEnum : values()) {
            if (fileFormatEnum.getName().equals(name)) {
                return fileFormatEnum;
            }
        }
        return null;
    }
}
