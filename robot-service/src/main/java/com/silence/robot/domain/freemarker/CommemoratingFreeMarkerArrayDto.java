package com.silence.robot.domain.freemarker;

import java.util.List;

public class CommemoratingFreeMarkerArrayDto {

    private String date;

    private String imageName;

    private boolean now;



    private List<String> list;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public boolean isNow() {
        return now;
    }

    public void setNow(boolean now) {
        this.now = now;
    }

    @Override
    public String toString() {
        return "CommemoratingFreeMarkerArrayDto{" + "date='" + date + '\'' + ", imageName='" + imageName + '\''
            + ", list=" + list + '}';
    }
}
