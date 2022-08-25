package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotUserStopService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserStop;
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
 * @Date 2022/8/17 10:49
 */
@Api(tags = "驻足时间相关接口")
@RestController
@RequestMapping("spotUserStop")
public class SysScenicSpotUserStopController {

    @Autowired
    SysScenicSpotUserStopService sysScenicSpotUserStopService;


    /**
     * 添加驻足时间
     * 张
     * @param sysScenicSpotUserStop
     * @return
     */
    @ApiOperation("添加驻足时间")
    @PostMapping("addSpotUserStop")
    @ResponseBody
    public ReturnModel addSpotUserStop(@RequestBody SysScenicSpotUserStop sysScenicSpotUserStop){

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(sysScenicSpotUserStop.getSpotId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区id为空，无法添加驻足时间！");
            return returnModel;
        }
        if (StringUtils.isEmpty(sysScenicSpotUserStop.getBroadcastId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景点id为空，无法添加驻足时间！");
            return returnModel;
        }
        if (StringUtils.isEmpty(sysScenicSpotUserStop.getUserId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("用户id为空，无法添加驻足时间！");
            return returnModel;
        }
        int i = sysScenicSpotUserStopService.addSpotUserStop(sysScenicSpotUserStop);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        }else{
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }
        return returnModel;
    }

    /**
     * 查询当天驻足时间
     * zhang
     * @param searchDTO
     * @return
     */
    @ApiOperation("查询当天驻足时间")
    @GetMapping("getSpotUserStop")
    @ResponseBody
    public ReturnModel getSpotUserStop(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(searchDTO.getUid())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("用户id为空，无法查询");
            return returnModel;
        }
        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区id为空，无法查询");
            return returnModel;
        }


        List<SysScenicSpotUserStop> list =  sysScenicSpotUserStopService.getSpotUserStop(searchDTO.getUid(),searchDTO.getSpotId());

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return returnModel;
    }



}
