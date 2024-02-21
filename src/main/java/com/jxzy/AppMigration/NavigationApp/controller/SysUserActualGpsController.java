package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBindingService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRobotService;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserActualGpsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserActualGpsMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/11 10:44
 * 游娱go用户实时坐标定位
 */
@Api(tags = "游娱go用户实时坐标定位接口")
@CrossOrigin
@RestController
@RequestMapping("sysUserActualGps")
public class SysUserActualGpsController {

    @Autowired
    SysUserActualGpsService sysUserActualGpsService;
    @Autowired
    SysScenicSpotRobotService sysScenicSpotRobotService;
    @Autowired
    SysScenicSpotBindingService sysScenicSpotBindingService;

    @ApiOperation("获取用户坐标列表")
    @GetMapping("/getUserGpsList")
    public ReturnModel getUserGpsList(@ApiParam(name = "token", value = "token", required = true) BaseDTO baseDTO,
                                      @ApiParam(name = "type", value = "type", required = true) Long type,
                                      @ApiParam(name = "id", value = "id", required = false) Long id,
                                      @ApiParam(name = "cityName", value = "cityName", required = false) String cityName) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();

        List<SysUserGps> list = sysUserActualGpsService.getUserGpsList(id, type, cityName);

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;


    }

    @ApiOperation("查询用户身份（临时）")
    @GetMapping("/listByphone")
    public ReturnModel listByphone(@ApiParam(name = "userPhone", value = "userPhone", required = false) String userPhone) {
        ReturnModel returnModel = new ReturnModel();
        List<UserRoleText> userRoleTexts = sysUserActualGpsService.listByPhone(userPhone);
        returnModel.setData(userRoleTexts);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;
    }

    @ApiOperation("年会查询锁开关（临时）")
    @GetMapping("/getLock")
    public ReturnModel getLock() {
        ReturnModel returnModel = new ReturnModel();
        LockText lock = sysUserActualGpsService.getLock();
        returnModel.setData(lock);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;
    }

    @ApiOperation("查询锁开关（活动）")
    @GetMapping("/getLockNew")
    public ReturnModel getLockNew() {
        ReturnModel returnModel = new ReturnModel();
        LockText lock = sysUserActualGpsService.getLockNew();
        returnModel.setData(lock);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;
    }

    @ApiOperation("修改锁开关（活动）")
    @GetMapping("/editLock")
    public ReturnModel editLock(String onOff) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysUserActualGpsService.editLock(onOff);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改失败！");
            return returnModel;
        }
    }


    @ApiOperation("添加,更新用户实时坐标")
    @PostMapping("/editUserGps")
    public ReturnModel editUserGps(@RequestBody SysUserActualGps sysUserActualGps) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysUserActualGpsService.editUserGps(sysUserActualGps);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("上传成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("上传失败！");
            return returnModel;
        }


    }


}
