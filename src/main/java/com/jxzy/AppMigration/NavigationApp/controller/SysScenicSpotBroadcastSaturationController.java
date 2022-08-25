package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastSaturationService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastSaturation;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/8/22 16:21
 * 景点饱和度相关接口
 *
 */
@Api(tags = "景点饱和度相关接口")
@RestController
@RequestMapping("broadcastSaturation")
public class SysScenicSpotBroadcastSaturationController {

    @Autowired
    SysScenicSpotBroadcastSaturationService sysScenicSpotBroadcastSaturationService;


    /**
     * 用来统计每个景点的饱和度，添加景点人数
     * @param sysScenicSPotBroadcastSaturation
     * @return
     */
    @ApiOperation("添加景点人数")
    @PostMapping("addSaturation")
    @ResponseBody
    public ReturnModel addSaturation(@RequestBody SysScenicSpotBroadcastSaturation sysScenicSPotBroadcastSaturation){

        ReturnModel returnModel = new ReturnModel();
        int i =  sysScenicSpotBroadcastSaturationService.addSaturation(sysScenicSPotBroadcastSaturation);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }
        return returnModel;
    }

    @ApiOperation("删除景点人数")
    @PostMapping("reduceSaturation")
    @ResponseBody
    public ReturnModel reduceSaturation(@RequestBody SysScenicSpotBroadcastSaturation sysScenicSpotBroadcastSaturation){

        ReturnModel returnModel = new ReturnModel();
        if (StringUtils.isEmpty(sysScenicSpotBroadcastSaturation.getBroadcastId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景点id为空,无法删除");
            return returnModel;
        }
        int i = sysScenicSpotBroadcastSaturationService.reduceSaturation(sysScenicSpotBroadcastSaturation.getBroadcastId());

        if(i>0){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景点人数减一成功！");
        }else{
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景点人数减一失败！");
        }
        return returnModel;
    }

    @ApiOperation("查询景区中各个景点的饱和度")
    @GetMapping("broadcastSaturationList")
    @ResponseBody
    public ReturnModel broadcastSaturationList(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(searchDTO)){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区id为空，无法查询！");
            return returnModel;
        }
        List<SysScenicSpotBroadcastSaturation> list =  sysScenicSpotBroadcastSaturationService.getBroadcastSaturationList(searchDTO.getSpotId());

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功！");
        return returnModel;
    }



}
