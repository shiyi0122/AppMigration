package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicBroadcastReadTimeService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicBroadcastReadTime;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhang
 * @Date 2022/10/1 8:51
 */
@Api(tags = "景点实时信息相关接口")
@RestController
@RequestMapping("broadcastReadTime")
public class SysScenicBroadcastReadTimeController {

    @Autowired
    SysScenicBroadcastReadTimeService   sysScenicBroadcastReadTimeService;

    @ApiOperation("添加景点实时信息")
    @PostMapping("addBroadcastReadTime")
    @ResponseBody
    public ReturnModel addBroadcastReadTime(@RequestBody SysScenicBroadcastReadTime sysScenicBroadcastReadTime){

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicBroadcastReadTimeService.addBroadcastReadTime(sysScenicBroadcastReadTime);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }
        return returnModel;

    }
    @ApiOperation("删除景点实时信息")
    @GetMapping("delBroadcastReadTime")
    @ResponseBody
    public ReturnModel delBroadcastReadTime(Long userId,Long broadcastId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicBroadcastReadTimeService.delBroadcastReadTime(userId,broadcastId);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }
        return returnModel;
    }
}
