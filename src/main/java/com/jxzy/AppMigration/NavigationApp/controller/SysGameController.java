package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysGameService;
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
 * @Date 2023/1/11 17:17
 * 娱乐项目
 */
@Api(tags = "游娱go娱乐设施")
@CrossOrigin
@RestController
@RequestMapping("sysGame")
public class SysGameController {

    @Autowired
    SysGameService sysGameService;


    @ApiOperation("查询娱乐设施列表")
    @GetMapping("getSysGameList")
    @ResponseBody
    public PageDataResult getSysGameList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }
        if (!StringUtils.isEmpty(pageDTO.getContent())){
            search.put("content",pageDTO.getContent());
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
        if (!StringUtils.isEmpty(pageDTO.getUid())){
            search.put("uid", pageDTO.getUid());
        }

        pageDataResult = sysGameService.getSysGameAppList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }





}
