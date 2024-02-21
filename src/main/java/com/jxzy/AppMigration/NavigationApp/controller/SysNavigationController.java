package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysNavigationService;
import com.jxzy.AppMigration.NavigationApp.Service.SysSpotNavigationService;
import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhang
 * @Date 2023/1/9 17:04
 */
@Api(tags = "游娱go首页导航")
@CrossOrigin
@RestController
@RequestMapping("sysNavigation")
public class SysNavigationController {

    @Autowired
    SysNavigationService sysNavigationService;

    @Autowired
    SysSpotNavigationService sysSpotNavigationService;


    @ApiOperation("查询查询首页导航列表")
    @GetMapping("getSysNavigationList")
    @ResponseBody
    public PageDataResult getSysNavigationList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysNavigationService.getSysNavigationList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getContent());

        return pageDataResult;
    }

    @ApiOperation("查询景区首页导航列表")
    @GetMapping("getSysSpotNavigationList")
    @ResponseBody
    public PageDataResult getSysSpotNavigationList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysSpotNavigationService.getSysSpotNavigationList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getContent());

        return pageDataResult;
    }


}
