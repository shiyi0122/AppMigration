package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysAppVersionService;
import com.jxzy.AppMigration.NavigationApp.entity.SysAppVersion;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/21 14:06
 */
@Api(tags = "游小伴App版本更新接口")
@CrossOrigin
@RestController
@RequestMapping("appVersion")
public class SysAppVersionController {

    @Autowired
    SysAppVersionService sysAppVersionService;

    @ApiOperation("获取最新版本")
    @GetMapping("getSysAppVersionUpToDate")
    public ReturnModel getSysAppVersionUpToDate() {

        ReturnModel returnModel = new ReturnModel();

        SysAppVersion sysAppVersion = sysAppVersionService.getSysAppVersionUpToDate();

        returnModel.setData(sysAppVersion);
        returnModel.setMsg("获取成功");
        returnModel.setState(Constant.STATE_SUCCESS);

        return returnModel;
    }


    @ApiOperation("获取最新版本(奥森和全国分开)")
    @GetMapping("getSysAppVersionUpToDateNew")
    public ReturnModel getSysAppVersionUpToDateNew(String spotType) {

        ReturnModel returnModel = new ReturnModel();

        SysAppVersion sysAppVersion = sysAppVersionService.getSysAppVersionUpToDateNew(spotType);

        returnModel.setData(sysAppVersion);
        returnModel.setMsg("获取成功");
        returnModel.setState(Constant.STATE_SUCCESS);

        return returnModel;
    }








}
