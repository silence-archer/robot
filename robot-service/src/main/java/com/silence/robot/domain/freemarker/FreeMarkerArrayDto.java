package com.silence.robot.domain.freemarker;

import java.util.List;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/5
 */
public class FreeMarkerArrayDto {
    private String name;
    private String desc;
    private List<FreeMarkerBodyDto> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FreeMarkerBodyDto> getList() {
        return list;
    }

    public void setList(List<FreeMarkerBodyDto> list) {
        this.list = list;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
