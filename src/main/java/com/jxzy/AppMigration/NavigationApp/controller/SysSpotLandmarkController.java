package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysSpotLandmarkService;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/19 16:54
 */
@Api(tags = "游娱go地标")
@CrossOrigin
@RestController
@RequestMapping("sysSpotLandmark")
public class SysSpotLandmarkController {

    @Autowired
    SysSpotLandmarkService sysSpotLandmarkService;

    @ApiOperation("查询地标列表")
    @GetMapping("getSysSpotLandmarkList")
    @ResponseBody
    public PageDataResult getSysSpotLandmarkList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }

        if (!StringUtils.isEmpty(pageDTO.getLng())){
            search.put("lng",pageDTO.getLng());
        }
        if (!StringUtils.isEmpty(pageDTO.getLat())){
            search.put("lat",pageDTO.getLat());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotBindingName())){
            search.put("cityName",pageDTO.getSpotBindingName());
        }

        pageDataResult = sysSpotLandmarkService.getSpotLandmarkList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }






}
