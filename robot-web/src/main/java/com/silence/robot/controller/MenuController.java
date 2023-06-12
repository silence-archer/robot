/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MenuController
 * Author:   silence
 * Date:     2019/10/10 9:37
 * Description: 菜单控制器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.controller;

import com.silence.robot.domain.MenuData;
import com.silence.robot.domain.NavigationMenu;
import com.silence.robot.domain.UserInfo;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.exception.BusinessException;
import com.silence.robot.exception.ExceptionCode;
import com.silence.robot.service.MenuService;
import com.silence.robot.utils.HttpUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈菜单控制器〉
 *
 * @author silence
 * @since 2019/10/10
 * 
 */
@RestController
public class MenuController {


    @Resource
    private MenuService menuService;

    @PostMapping("/addMenuData")
    @RequiresPermissions("MENU:ADD")
    public DataResponse<?> addMenuData(@RequestBody MenuData menuData){
        menuService.addMenu(menuData);
        return new DataResponse<>();
    }

    @PostMapping("/deleteMenuData")
    @RequiresPermissions("MENU:DELETE")
    public DataResponse<?> deleteMenuData(@RequestBody MenuData menuData){
        menuService.deleteMenu(menuData);
        return new DataResponse<>();
    }

    @PostMapping("/updateMenuData")
    @RequiresPermissions("MENU:UPDATE")
    public DataResponse<?> updateMenuData(@RequestBody MenuData menuData){
        menuService.updateMenu(menuData);
        return new DataResponse<>();
    }

    @RequestMapping("/getMenuData")
    public DataResponse<List<MenuData>> getMenuData(){
        List<MenuData> dataList = menuService.getMenuData();
        return new DataResponse<>(dataList);
    }

    @RequestMapping("/getNavigationMenu")
    public DataResponse<List<NavigationMenu>> getNavigationMenu(){
        UserInfo userInfo = HttpUtils.getUserInfo();
        if(userInfo == null){
            throw new BusinessException(ExceptionCode.AUTH_ERROR);
        }
        List<NavigationMenu> dataList = menuService.getNavigationMenu(userInfo.getRoleNo());
        return new DataResponse<>(dataList);
    }
}