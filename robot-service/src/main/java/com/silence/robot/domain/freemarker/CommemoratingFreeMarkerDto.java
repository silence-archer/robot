package com.silence.robot.domain.freemarker;

import java.util.List;

public class CommemoratingFreeMarkerDto {

    private String desc;

    private Integer year;

    private List<CommemoratingFreeMarkerArrayDto> bodys;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<CommemoratingFreeMarkerArrayDto> getBodys() {
        return bodys;
    }

    public void setBodys(List<CommemoratingFreeMarkerArrayDto> bodys) {
        this.bodys = bodys;
    }

    @Override
    public String toString() {
        return "CommemoratingFreeMarkerDto{" + "desc='" + desc + '\'' + ", year=" + year + ", bodys=" + bodys + '}';
    }
}
