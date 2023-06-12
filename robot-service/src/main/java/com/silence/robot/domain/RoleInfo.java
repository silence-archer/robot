/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: RoleInfo
 * Author:   silence
 * Date:     2019/12/6 15:26
 * Description: 角色信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.domain;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色信息〉
 *
 * @author silence
 * @since 2019/12/6
 * 
 */
public class RoleInfo {

    private String id;

    private String roleNo;

    private String roleName;

    private List<MenuData> menuNos;

    private String createTime;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<MenuData> getMenuNos() {
        return menuNos;
    }

    public void setMenuNos(List<MenuData> menuNos) {
        this.menuNos = menuNos;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(String roleNo) {
        this.roleNo = roleNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RoleInfo{" +
                "id='" + id + '\'' +
                ", roleNo='" + roleNo + '\'' +
                ", roleName='" + roleName + '\'' +
                ", menuNos=" + menuNos +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}