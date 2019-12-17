/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MenuService
 * Author:   silence
 * Date:     2019/10/10 9:15
 * Description: 菜单服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.silence.robot.domain.MenuData;
import com.silence.robot.domain.NavigationMenu;
import com.silence.robot.domain.RoleInfo;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.mapper.TMenuMapper;
import com.silence.robot.mapper.TRoleMapper;
import com.silence.robot.model.TMenu;
import com.silence.robot.model.TRole;
import com.silence.robot.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br> 
 * 〈菜单服务〉
 *
 * @author silence
 * @create 2019/10/10
 * @since 1.0.0
 */
@Service
public class MenuService {

    @Resource
    private TMenuMapper menuMapper;

    @Resource
    private SequenceService sequenceService;

    @Resource
    private TRoleMapper roleMapper;

    public void addMenu(MenuData menuData){
        TMenu menu = new TMenu();
        menu.setId(CommonUtils.getUuid());
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        menu.setMenuName("未命名");
        if(menuData.getId().equals("1")){
            menu.setMenuLevel(1);
            menu.setMenuNo(getMenuNo(menu.getMenuLevel(), ""));
        }else{
            //TODO 菜单级别扩展
            menu.setParentMenuNo(menuData.getId());
            menu.setMenuLevel(2);
            menu.setMenuNo(getMenuNo(menu.getMenuLevel(), menuData.getId()));
        }
        menuMapper.insert(menu);
    }

    public void updateMenu(MenuData menuData){
        TMenu menu = menuMapper.selectByMenuNo(menuData.getId());
        if(menu != null){
            menu.setUpdateTime(new Date());
            menu.setMenuPath(menuData.getField());
            menu.setMenuName(menuData.getTitle());
            menuMapper.updateByPrimaryKey(menu);
        }else{
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
    }


    public void deleteMenu(MenuData menuData){

        menuMapper.deleteByMenuNo(menuData.getId());
        menuMapper.deleteByParentMenuNo(menuData.getId());
    }

    public List<MenuData> getMenuData(){
        List<TMenu> menus = menuMapper.selectAll();
        List<MenuData> list = new ArrayList<>();
        MenuData menuData = new MenuData();
        menuData.setId("1");
        menuData.setTitle("后台管理系统");
        menuData.setSpread(true);
        menuData.setChecked(false);
        menuData.setDisabled(false);
        menuData.setChildren(new ArrayList<>());
        List<TMenu> list1 = menus.stream().filter(menu -> menu.getMenuLevel() == 1).collect(Collectors.toList());
        List<TMenu> list2 = menus.stream().filter(menu -> menu.getMenuLevel() == 2).collect(Collectors.toList());
        for (TMenu menuLevel1: list1) {
            MenuData children = addChildren(menuLevel1);
            menuData.getChildren().add(children);
            List<TMenu> menuLevel1SubList = list2.stream().filter(menuLevel2 -> menuLevel2.getParentMenuNo().equals(menuLevel1.getMenuNo())).collect(Collectors.toList());
            for(TMenu menuLevel2: menuLevel1SubList){
                children.getChildren().add(addChildren(menuLevel2));
            }
        }


        list.add(menuData);
        return list;
    }

    public List<NavigationMenu> getNavigationMenu(String roleNo){
        if(CommonUtils.isEmpty(roleNo)){
            throw new BusinessException(ExceptionCode.ROLE_NO_EXIST);
        }
        List<TRole> roles = roleMapper.selectByRoleNo(roleNo);
        if(roles.isEmpty()){
            throw new BusinessException(ExceptionCode.NO_EXIST);
        }
        List<String> collect = roles.stream().map(TRole::getMenuNo).collect(Collectors.toList());
        List<NavigationMenu> list = new ArrayList<>();
        List<TMenu> menus = menuMapper.selectByMenuNos(collect);
        List<TMenu> list1 = menus.stream().filter(menu -> menu.getMenuLevel() == 1).collect(Collectors.toList());
        getLevelMenu(list1, list, menus);
        return list;
    }

    private void getLevelMenu(List<TMenu> list1, List<NavigationMenu> list, List<TMenu> menus){
        for (TMenu menu1 : list1) {
            NavigationMenu menuNav = new NavigationMenu();
            menuNav.setName(menu1.getMenuName());
            menuNav.setUrl(menu1.getMenuPath());
            menuNav.setList(new ArrayList<>());
            list.add(menuNav);
            List<TMenu> list2 = menus.stream().filter(menuL1 -> menu1.getMenuNo().equals(menuL1.getParentMenuNo())).collect(Collectors.toList());
            getLevelMenu(list2, menuNav.getList(), menus);
        }
    }



    private String getMenuNo(int level , String parentMenuNo){
        int sequence = sequenceService.getSequence("MenuLevel" + level);
        if(parentMenuNo.length() > 8){
            throw new BusinessException(ExceptionCode.MENU_ERROR);
        }
        String menuNo;
        if(CommonUtils.isEmpty(parentMenuNo)){
            menuNo = "Menu"+(1000+sequence);
        }else{
            menuNo = parentMenuNo + (1000+sequence);
        }

        return menuNo;
    }

    private MenuData addChildren(TMenu menu){
        MenuData menuItem = new MenuData();
        menuItem.setId(menu.getMenuNo());
        menuItem.setTitle(menu.getMenuName());
        menuItem.setField(menu.getMenuPath());
        menuItem.setSpread(true);
        menuItem.setChildren(new ArrayList<>());
        return menuItem;
    }



}