package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysHeadMountedService;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/25 11:24
 */
@Api(tags = "游娱go头戴设备相关接口")
@CrossOrigin
@RestController
@RequestMapping("sysHeadMounted")
public class SysHeadMountedController {

    @Autowired
    SysHeadMountedService sysHeadMountedService;

    @ApiOperation("查询头戴设备列表")
    @GetMapping("getSysHeadMountedList")
    @ResponseBody
    public PageDataResult getSysHeadMountedList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("museumId",pageDTO.getMuseumId());
        }

//        if (!StringUtils.isEmpty(pageDTO.getLng())){
//            search.put("lng",pageDTO.getLng());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getLat())){
//            search.put("lat",pageDTO.getLat());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getSpotBindingName())){
//            search.put("cityName",pageDTO.getSpotBindingName());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getUid())){
//            search.put("uid", pageDTO.getUid());
//        }
        pageDataResult = sysHeadMountedService.getSysHeadMountedList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }

    @ApiOperation("修改头戴设备使用状态")
    @GetMapping("editSysHeadMountedState")
    @ResponseBody
    public ReturnModel editSysHeadMountedState(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i  = sysHeadMountedService.editSysHeadMountedState(baseDataDTO.getId(),baseDataDTO.getState());

        if (i > 0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }
        return returnModel;
    }


}
