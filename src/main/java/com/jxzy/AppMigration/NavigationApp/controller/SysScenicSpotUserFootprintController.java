package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotUserFootprintService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserFootprint;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserMapSignRemarks;
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
 * 景区足迹i相关接口
 * @Author zhang
 * @Date 2022/8/16 16:28
 */
@Api(tags = "景区足迹i相关接口")
@RestController
@RequestMapping("spotUserFootPrint")
public class SysScenicSpotUserFootprintController {

    @Autowired
    SysScenicSpotUserFootprintService sysScenicSpotUserFootprintService;

    /**
     * 添加用户足迹
     * 张
     * @param
     * @return
     */
    @ApiOperation("添加,更新用户足迹")
    @PostMapping("addSpotUserFootPrint")
    @ResponseBody
    public ReturnModel addSpotUserFootPrint(@RequestBody SysScenicSpotUserFootprint sysScenicSpotUserFootprint) {

        ReturnModel returnModel = new ReturnModel();

        int i =  sysScenicSpotUserFootprintService.addSpotUserFootPrint(sysScenicSpotUserFootprint);

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

    /**
     * 查询用户最近一次足迹
     * 张
     * @param searchDTO
     * @return
     */
    @ApiOperation("查询用户最近一次的足迹")
    @GetMapping("getSpotUserFootPrint")
    @ResponseBody
    public ReturnModel getSpotUserFootPrint(SearchDTO searchDTO) {

        ReturnModel returnModel = new ReturnModel();
        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData("");
            returnModel.setMsg("景区id为空无法查询");
            return returnModel;
        }
        if (StringUtils.isEmpty(searchDTO.getUid())){
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData("");
            returnModel.setMsg("用户id为空无法查询");
            return returnModel;
        }
        List<SysScenicSpotUserFootprint> list = sysScenicSpotUserFootprintService.getSpotUserFootPrint(searchDTO.getSpotId(),searchDTO.getUid());
        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }


    }
