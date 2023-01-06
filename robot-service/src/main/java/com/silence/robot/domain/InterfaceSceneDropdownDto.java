package com.silence.robot.domain;

import java.util.List;

public class InterfaceSceneDropdownDto {

    private String title;

    private String id;

    private String type;

    private List<InterfaceSceneDropdownDto> child;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<InterfaceSceneDropdownDto> getChild() {
        return child;
    }

    public void setChild(List<InterfaceSceneDropdownDto> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "InterfaceSceneDropdownDto{" + "title='" + title + '\'' + ", id='" + id + '\'' + ", type='" + type + '\''
            + ", child=" + child + '}';
    }
}
