package com.silence.robot.model;

import java.io.Serializable;

/**
 * t_data_dict
 * @author 
 */
public class TDataDict implements Serializable {
    /**
     * 物理主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 枚举值
     */
    private String enumName;

    /**
     * 枚举值描述
     */
    private String enumDesc;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TDataDict other = (TDataDict) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getEnumName() == null ? other.getEnumName() == null : this.getEnumName().equals(other.getEnumName()))
            && (this.getEnumDesc() == null ? other.getEnumDesc() == null : this.getEnumDesc().equals(other.getEnumDesc()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getEnumName() == null) ? 0 : getEnumName().hashCode());
        result = prime * result + ((getEnumDesc() == null) ? 0 : getEnumDesc().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
        sb.append(", enumName=").append(enumName);
        sb.append(", enumDesc=").append(enumDesc);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}