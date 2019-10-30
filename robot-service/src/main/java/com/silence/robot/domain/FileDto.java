/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FileDto
 * Author:   silence
 * Date:     2019/10/29 11:25
 * Description: file dto
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

/**
 * 〈一句话功能简述〉<br> 
 * 〈file dto〉
 *
 * @author silence
 * @create 2019/10/29
 * @since 1.0.0
 */
public class FileDto {

    private long pos;

    private String content;

    private String type;

    private boolean overFlag;

    private int lineNum;

    public FileDto(){
        this.content = "";
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public boolean isOverFlag() {
        return overFlag;
    }

    public void setOverFlag(boolean overFlag) {
        this.overFlag = overFlag;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "pos=" + pos +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", overFlag=" + overFlag +
                ", lineNum=" + lineNum +
                '}';
    }
}