package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysUserFansService;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserAlbum;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserFans;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhang
 * @Date 2023/1/12 18:09
 * 用户关注用户
 */
@Api(tags = "游娱go用户关注用户接口")
@RestController
@RequestMapping("SysUserFans")
@CrossOrigin
public class SysUserFansController {

    @Autowired
    SysUserFansService sysUserFansService;

    @ApiOperation("用户关注用户接口")
    @PostMapping("addSysUserFans")
    @ResponseBody
    public ReturnModel addSysUserFans(@RequestBody SysUserFans sysUserFans) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysUserFansService.addSysUserFans(sysUserFans);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("已关注，无需重复关注");
        }


        return returnModel;
    }


    @ApiOperation("取消关注用户接口")
    @PostMapping("cancelSysUserFans")
    @ResponseBody
    public ReturnModel cancelSysUserFans(@RequestBody SysUserFans sysUserFans) {

        ReturnModel returnModel = new ReturnModel();

        int i =  sysUserFansService.cancelSysUserFans(sysUserFans);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("取消成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("取消失败");
        }

        return returnModel;

    }




}
