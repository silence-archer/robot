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

    /**
     * 文件偏移量
     */
    private long pos;

    /**
     * 文件内容
     */
    private String content;

    /**
     * 文件类型normal error
     */
    private String type;

    /**
     * 结束标志
     */
    private boolean overFlag;

    /**
     * 每次读取行数
     */
    private int lineNum;

    /**
     * 是否为目录
     */
    private boolean directory;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 带路径的文件名称
     */
    private String filePath;

    /**
     * 文件版本状态
     */
    private String fileSvnStatus;

    public String getFileSvnStatus() {
        return fileSvnStatus;
    }

    public void setFileSvnStatus(String fileSvnStatus) {
        this.fileSvnStatus = fileSvnStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
                ", directory=" + directory +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSvnStatus='" + fileSvnStatus + '\'' +
                '}';
    }
}