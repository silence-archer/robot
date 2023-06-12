/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InstantMsgFileDto
 * Author:   silence
 * Date:     2019/11/7 15:21
 * Description: 文件交互dto
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain.instant.messaging;

/**
 * 〈一句话功能简述〉<br> 
 * 〈文件交互dto〉
 *
 * @author silence
 * @since 2019/11/7
 * 
 */
public class InstantMsgFileDto {

    /**
     * 文件url
     */
    private String src;

    /**
     * 文件名
     */
    private String name;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InstantMsgFileDto{" +
                "src='" + src + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}