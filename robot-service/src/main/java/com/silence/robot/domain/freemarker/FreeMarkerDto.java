package com.silence.robot.domain.freemarker;

import java.util.List;

/**
 * TODO
 *
 * @author silence
 * @date 2021/9/5
 */
public class FreeMarkerDto {
    private String name;
    private Integer port;
    private String uri;

    private List<FreeMarkerBodyDto> bodys;

    private List<FreeMarkerArrayDto> arrays;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<FreeMarkerBodyDto> getBodys() {
        return bodys;
    }

    public void setBodys(List<FreeMarkerBodyDto> bodys) {
        this.bodys = bodys;
    }

    public List<FreeMarkerArrayDto> getArrays() {
        return arrays;
    }

    public void setArrays(List<FreeMarkerArrayDto> arrays) {
        this.arrays = arrays;
    }


}
