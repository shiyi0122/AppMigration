package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRobotService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobot;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotGPS;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/23 10:18
 */
@Api(tags = "游小伴景区机器人相关接口")
@RestController
@RequestMapping("scenicSpotRobot")
public class SysScenicSpotRobotController {

    @Autowired
    SysScenicSpotRobotService sysScenicSpotRobotService;
    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @ApiOperation("景区机器人位置信息")
    @GetMapping("sysScenicSpotRobotList")
    @ResponseBody
    public ReturnModel sysScenicSpotRobotList(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData("");
            returnModel.setMsg("景区id为空！无法查询");
            return returnModel;
        }
        search.put("scenicSpotId",searchDTO.getSpotId());

        SysScenicSpot sysScenicSpot = sysScenicSpotService.selectById(Long.parseLong(searchDTO.getSpotId()));
        search.put("coordinateRange",sysScenicSpot.getCoordinateRange());
        List<SysRobotGPS> list = sysScenicSpotRobotService.sysScenicSpotRobotList(search);

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;
    }





}
