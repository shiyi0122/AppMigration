package com.jxzy.AppMigration.NavigationApp.controller;


import com.jxzy.AppMigration.NavigationApp.Service.UserRoleService;
import com.jxzy.AppMigration.NavigationApp.entity.UserRole;
import com.jxzy.AppMigration.NavigationApp.entity.dto.UserRoleDTO;
import com.jxzy.AppMigration.NavigationApp.util.Result.PageInfo;
import com.jxzy.AppMigration.NavigationApp.util.Result.PageRequest;
import com.jxzy.AppMigration.NavigationApp.util.Result.Result;
import com.jxzy.AppMigration.NavigationApp.util.Result.mvc.action.BaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "游娱go用户权限相关")
@RestController
@CrossOrigin
@RequestMapping("userRole")
public class UserRoleController extends BaseAction {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/listByclassify")
    @ApiOperation("页面查询")
    public Result<PageInfo<UserRole>> listByclassify(PageRequest pageRequest, @RequestBody UserRoleDTO userRoleDTO) {
        PageInfo<UserRole> userRolePageInfo = userRoleService.listByclassify(this.getPageInfo(pageRequest), userRoleDTO);
        return Result.success("查询成功", userRolePageInfo);
    }

    @ApiOperation("添加")
    @PostMapping("/save")
    public Result saveBatch(@RequestBody List<UserRoleDTO> userRoleDTOs) {
        if (!userRoleDTOs.isEmpty()) {
            List<UserRole> userRoles = new ArrayList<>();
            for (UserRoleDTO userRoleDTO : userRoleDTOs) {
                UserRole userRole = new UserRole();
                BeanUtils.copyProperties(userRoleDTO, userRole);
                userRoleService.save(userRole);
                userRoles.add(userRole);
            }
            return Result.success("添加成功");
        } else {
            return Result.failure("请输入数据");
        }
    }


}
