package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysUserDistrictFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserScenicFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理--用户点赞收藏管理
 * @Author zhang
 * @Date 2022/9/23 15:37
 */

@Api(tags = "后台管理-用户点赞收藏管理")
@RestController
@RequestMapping("adminUserFabulousCollection")
@CrossOrigin
public class AdminUserFabulousCollectionController {

    @Autowired
    SysUserDistrictFabulousCollectionService sysUserDistrictFabulousCollectionService;

    @Autowired
    SysUserScenicFabulousCollectionService sysUserScenicFabulousCollectionService;


    @ApiOperation("app用户点赞收藏列表查询")
    @GetMapping("/getUserFabulousCollectionList")
    @ResponseBody
    public PageDataResult getUserFabulousCollectionList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        if (StringUtils.isEmpty(pageDTO.getType())) {
            pageDataResult.setCode(400);
            return pageDataResult;
        }
        Map<String, Object> search = new HashMap<>();
        search.put("userId",pageDTO.getUid());
        search.put("spotId",pageDTO.getSpotId());
        search.put("time",pageDTO.getStartTime());
        search.put("timeEnd",pageDTO.getEndTime());
        search.put("spotName",pageDTO.getSpotName());
        search.put("broadcastName",pageDTO.getBroadcastName());
        if  ("1".equals(pageDTO.getType())){//景区
            pageDataResult  =  sysUserDistrictFabulousCollectionService.getUserFabulousCollectionList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        }else{//景点
            pageDataResult  =  sysUserScenicFabulousCollectionService.getUserScenicFabulousCollectionList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        }
        return  pageDataResult;
    }






}
