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
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.MenuService;
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
 * @create 2019/10/10
 * @since 1.0.0
 */
@RestController
public class MenuController {


    @Resource
    private MenuService menuService;

    @PostMapping("/addMenuData")
    public DataResponse<?> addMenuData(@RequestBody MenuData menuData){
        menuService.addMenu(menuData);
        return new DataResponse<>();
    }

    @PostMapping("/deleteMenuData")
    public DataResponse<?> deleteMenuData(@RequestBody MenuData menuData){
        menuService.deleteMenu(menuData);
        return new DataResponse<>();
    }

    @PostMapping("/updateMenuData")
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
        List<NavigationMenu> dataList = menuService.getNavigationMenu();
        return new DataResponse<>(dataList);
    }
}