/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MenuData
 * Author:   silence
 * Date:     2019/10/10 9:27
 * Description: 菜单JSON对象
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈菜单JSON对象〉
 *
 * @author silence
 * @since 2019/10/10
 * 
 */
public class MenuData {

    /**
     * 节点标题
     */
    private String title;
    /**
     * 节点唯一索引值，用于对指定节点进行各类操作
     */
    private String id;
    /**
     * 节点字段名/一般对应表字段名
     */
    private String field;
    /**
     * 子节点。支持设定选项同父节点
     */
    private List<MenuData> children;
    /**
     * 点击节点弹出新窗口对应的 url。需开启 isJump 参数
     */
    private String href;
    /**
     * 节点是否初始展开，默认 false
     */
    private Boolean spread;
    /**
     * 节点是否初始为选中状态（如果开启复选框的话），默认 false
     */
    private Boolean checked;
    /**
     * 节点是否为禁用状态。默认 false
     */
    private Boolean disabled;

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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<MenuData> getChildren() {
        return children;
    }

    public void setChildren(List<MenuData> children) {
        this.children = children;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "MenuData{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", field='" + field + '\'' +
                ", children=" + children +
                ", href='" + href + '\'' +
                ", spread='" + spread + '\'' +
                ", checked='" + checked + '\'' +
                ", disabled='" + disabled + '\'' +
                '}';
    }
}