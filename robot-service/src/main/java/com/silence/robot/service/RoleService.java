/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: RoleService
 * Author:   silence
 * Date:     2019/12/6 15:24
 * Description: 角色服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.silence.robot.domain.MenuData;
import com.silence.robot.domain.RoleInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TRoleMapper;
import com.silence.robot.model.TRole;
import com.silence.robot.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈角色服务〉
 *
 * @author silence
 * @create 2019/12/6
 * @since 1.0.0
 */
@Service
public class RoleService {

    @Resource
    private TRoleMapper roleMapper;

    @Resource
    private SequenceService sequenceService;

    @Resource
    private MenuService menuService;

    public void addRole(String roleName, List<MenuData> menuDataList) {
        //TODO 暂时忽略掉第一级菜单
        List<MenuData> children = menuDataList.get(0).getChildren();
        String roleNo = String.format("roleNo%04d", sequenceService.getSequence("RoleNo"));
        addRoleMenuInfo(roleNo, roleName, children);
    }

    public void modifyRole(String roleNo, String roleName, List<MenuData> menuDataList){
        //先删除再添加
        roleMapper.deleteByRoleNo(roleNo);
        //TODO 暂时忽略掉第一级菜单
        List<MenuData> children = menuDataList.get(0).getChildren();
        addRoleMenuInfo(roleNo, roleName, children);
    }

    public void deleteRole(String roleNo){
        roleMapper.deleteByRoleNo(roleNo);
    }



    public List<RoleInfo> getRoleInfo() {

        return getRoleInfo(true);
    }

    public String getRoleName(String roleNo) {
        List<TRole> roles = roleMapper.selectByRoleNo(roleNo);
        if(roles.isEmpty()){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        return roles.get(0).getRoleName();
    }

    public List<RoleInfo> getRoleInfo(boolean isQueryList) {
        List<TRole> roles = roleMapper.selectAll();
        List<MenuData> menuNos = menuService.getMenuData();

        List<TRole> collect = roles.stream().filter(CommonUtils.distinctByKey(TRole::getRoleNo)).collect(Collectors.toList());
        if(isQueryList){
            List<RoleInfo> list = new ArrayList<>();
            collect.forEach(role -> {
                //初始化菜单都不勾选 每循环一次创建一个菜单对象
                List<MenuData> results = new ArrayList<>();
                initMenuChecked(menuNos, results);
                RoleInfo roleInfo = new RoleInfo();
                roleInfo.setId(role.getId());
                roleInfo.setRoleName(role.getRoleName());
                roleInfo.setRoleNo(role.getRoleNo());
                roleInfo.setCreateTime(CommonUtils.getStringDate(role.getCreateTime()));
                roleInfo.setMenuNos(results);
                List<TRole> roleList = roles.stream().filter(menuRole -> menuRole.getRoleNo().equals(role.getRoleNo())).collect(Collectors.toList());
                getMenuChecked(results, roleList);
                list.add(roleInfo);

            });
            return list;
        }else{
            List<RoleInfo> list = CommonUtils.copyList(RoleInfo.class, collect);
            return list;
        }

    }


    private void addRoleMenuInfo(String roleNo, String roleName, List<MenuData> menuDataList) {
        menuDataList.forEach(menuData -> {
            TRole role = new TRole();
            role.setRoleNo(roleNo);
            role.setMenuNo(menuData.getId());
            role.setRoleName(roleName);
            roleMapper.insert(role);
            if (!menuData.getChildren().isEmpty()) {
                addRoleMenuInfo(roleNo, roleName, menuData.getChildren());
            }

        });
    }

    /**
     * 匹配上的菜单进行勾选
     *
     * @param menuNos
     * @param roleList
     */
    private void getMenuChecked(List<MenuData> menuNos, List<TRole> roleList) {
        menuNos.forEach(menuData -> {
            boolean anyMatch = roleList.stream().anyMatch(roleData -> menuData.getId().equals(roleData.getMenuNo()));
            //匹配上的菜单进行勾选 只用勾选最末一级
            if (menuData.getChildren().isEmpty()) {
                if (anyMatch) {
                    menuData.setChecked(true);
                }
            }else{
                getMenuChecked(menuData.getChildren(), roleList);
            }
        });
    }

    private void initMenuChecked(List<MenuData> menuNos, List<MenuData> results) {
        menuNos.forEach(menuData -> {
            MenuData result = new MenuData();
            result.setDisabled(menuData.getDisabled());
            result.setField(menuData.getField());
            result.setSpread(menuData.getSpread());
            result.setTitle(menuData.getTitle());
            result.setId(menuData.getId());
            result.setHref(menuData.getHref());
            result.setChecked(false);
            results.add(result);
            List<MenuData> children = new ArrayList<>();
            result.setChildren(children);
            initMenuChecked(menuData.getChildren(), children);
        });
    }


}