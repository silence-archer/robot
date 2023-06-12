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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色管理〉
 *
 * @author silence
 * @since 2019/12/6
 * 
 */
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/getRoleInfo")
    public DataResponse<List<RoleInfo>> getRoleInfo(@RequestParam Integer page, @RequestParam Integer limit){
        return new DataResponse<>(roleService.getRoleInfo());
    }

    @GetMapping("/queryRoleSelectInfo")
    public DataResponse<List<RoleInfo>> queryRoleSelectInfo(){
        return new DataResponse<>(roleService.getRoleInfo(false));
    }

    @GetMapping("/deleteRole")
    @RequiresPermissions("ROLE:DELETE")
    public DataResponse<?> deleteRole(@RequestParam String roleNo){
        roleService.deleteRole(roleNo);
        return new DataResponse<>();
    }

    @PostMapping("/addRole")
    @RequiresPermissions("ROLE:ADD")
    public DataResponse<?> addRole(@RequestBody DataRequest<List<MenuData>> request){
        roleService.addRole(request.getType(), request.getData());
        return new DataResponse<>();
    }

    @PostMapping("/modifyRole")
    @RequiresPermissions("ROLE:UPDATE")
    public DataResponse<?> modifyRole(@RequestBody DataRequest<List<MenuData>> request){
        roleService.modifyRole(request.getApiCd(), request.getType(), request.getData());
        return new DataResponse<>();
    }


}