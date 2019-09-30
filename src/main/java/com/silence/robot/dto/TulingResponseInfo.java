package com.silence.robot.dto;

public class TulingResponseInfo {
    /**
     * 输出类型文本(text);连接(url);音频(voice);视频(video);图片(image);图文(news)
     */
    private String resultType;
    /**
     * 输出文本
     */
    private String text;
    /**
     * 输出链接
     */
    private String url;

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TulingResponseInfo{" +
                "resultType='" + resultType + '\'' +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
