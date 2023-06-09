package com.silence.robot.domain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 数据字典对象
 *
 * @author silence
 * @since 2021/5/4
 */
public class DataDictDto {

    private String id;

    @JSONField(ordinal = 1)
    private String name;
    @JSONField(ordinal = 2)
    private String desc;
    @JSONField(ordinal = 3)
    private String enumName;
    @JSONField(ordinal = 4)
    private String enumDesc;
    @JSONField(ordinal = 5)
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getEnumDesc() {
        return enumDesc;
    }

    public void setEnumDesc(String enumDesc) {
        this.enumDesc = enumDesc;
    }
}
