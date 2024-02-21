package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysSpotDynamicService;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import com.rabbitmq.client.Return;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/12 19:11
 * 动态
 */

@Api(tags = "游娱go动态相关接口")
@CrossOrigin
@RestController
@RequestMapping("sysSpotDynamic")
public class SysSpotDynamicController {

    @Autowired
    SysSpotDynamicService sysSpotDynamicService;

    @ApiOperation("查询动态列表")
    @GetMapping("getSysSpotDynamicList")
    @ResponseBody
    public PageDataResult getSysSpotDynamicList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }
        if (!StringUtils.isEmpty(pageDTO.getContent())){
            search.put("content",pageDTO.getContent());
        }
//        if (!StringUtils.isEmpty(pageDTO.getLng())){
//            search.put("lng",pageDTO.getLng());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getLat())){
//            search.put("lat",pageDTO.getLat());
//        }
        if (!StringUtils.isEmpty(pageDTO.getSpotBindingName())){
            search.put("cityName",pageDTO.getSpotBindingName());
        }
        if (!StringUtils.isEmpty(pageDTO.getUid())){
            search.put("uid", pageDTO.getUid());
        }
        pageDataResult = sysSpotDynamicService.getSysSpotDynamicAppList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }
    @ApiOperation("动态浏览量+1")
    @GetMapping("addSysSpotDynamicBrowse")
    @ResponseBody
    public ReturnModel addSysSpotDynamicBrowse(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotDynamicService.addSysSpotDynamicBrowse(baseDataDTO.getId());

        returnModel.setData(i);
        returnModel.setMsg("添加成功");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;

    }


}
