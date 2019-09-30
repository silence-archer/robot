package com.silence.robot.dto;

public class TulingRequestInfo {

    /**
     * 文本信息
     */
    private String txt;
    /**
     * 图片信息
     */
    private String imageUrl;
    /**
     * 音频信息
     */
    private String mediaUrl;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 	省份
     */
    private String province;
    /**
     * 	街道
     */
    private String street;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "TulingRequestInfo{" +
                "txt='" + txt + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
