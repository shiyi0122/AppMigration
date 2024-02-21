package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRecommendService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRecommendMapper;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/3 19:33
 */

@Api(tags = "游娱go推荐景区")
@RestController
@RequestMapping("sysScenicSpotRecommend")
public class SysScenicSpotRecommendController extends PublicUtil {

    @Autowired
    SysScenicSpotRecommendService sysScenicSpotRecommendService;

    @ApiOperation("查询游娱go推荐景区列表")
    @GetMapping("sysScenicSpotRecommendList")
    @ResponseBody
    public PageDataResult sysScenicSpotRecommendList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
//        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
//            search.put("spotId",pageDTO.getSpotId());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getContent())){
//            search.put("content",pageDTO.getContent());
//        }
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
//        if (!StringUtils.isEmpty(pageDTO.getType())){
//            search.put("type",pageDTO.getType());
//        }
//
//        if (!StringUtils.isEmpty(pageDTO.getCityType())){
//            search.put("cityType",pageDTO.getCityType());
//        }

        pageDataResult = sysScenicSpotRecommendService.sysScenicSpotRecommendList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }






}
