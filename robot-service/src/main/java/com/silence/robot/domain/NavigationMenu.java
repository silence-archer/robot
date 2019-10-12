/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: NavigationMenu
 * Author:   silence
 * Date:     2019/10/10 18:10
 * Description: 导航菜单
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈导航菜单〉
 *
 * @author silence
 * @create 2019/10/10
 * @since 1.0.0
 */
public class NavigationMenu {

    private String name;

    private String url;

    private List<NavigationMenu> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NavigationMenu> getList() {
        return list;
    }

    public void setList(List<NavigationMenu> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NavigationMenu{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", list=" + list +
                '}';
    }
}