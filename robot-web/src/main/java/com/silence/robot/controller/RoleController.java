/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: RoleController
 * Author:   silence
 * Date:     2019/12/6 17:30
 * Description: 角色管理
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.controller;

import com.silence.robot.domain.MenuData;
import com.silence.robot.domain.RoleInfo;
import com.silence.robot.dto.DataRequest;
import com.silence.robot.dto.DataResponse;
import com.silence.robot.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色管理〉
 *
 * @author silence
 * @create 2019/12/6
 * @since 1.0.0
 */
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/getRoleInfo")
    public DataResponse<List<RoleInfo>> getRoleInfo(){
        return new DataResponse<>(roleService.getRoleInfo());
    }

    @PostMapping("/addRole")
    public DataResponse<?> addRole(@RequestBody DataRequest<List<MenuData>> request){
        roleService.addRole(request.getType(), request.getData());
        return new DataResponse<>();
    }


}