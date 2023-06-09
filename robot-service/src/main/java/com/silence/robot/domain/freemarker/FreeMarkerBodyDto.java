package com.silence.robot.domain.freemarker;

/**
 * TODO
 *
 * @author silence
 * @since 2021/9/5
 */
public class FreeMarkerBodyDto {
    private String name;
    private String desc;
    private String type;
    private Boolean required;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
