package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumBannerService;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumBanner;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollection;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollectionBroadcast;
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
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/20 11:11
 */
@Api(tags = "游娱go博物馆相关接口")
@CrossOrigin
@RestController
@RequestMapping("sysMuseum")
public class SysMuseumController {

    @Autowired
    SysMuseumService sysMuseumService;

    @Autowired
    SysMuseumBannerService sysMuseumBannerService;

    @ApiOperation("查询博物馆列表")
    @GetMapping("getSysMuseumList")
    @ResponseBody
    public PageDataResult getSysMuseumList(PageDTO pageDTO) {

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

        pageDataResult = sysMuseumService.getSysMuseumList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }


    @ApiOperation("根据博物馆id获取详情")
    @GetMapping("getSysMuseumIdDetails")
    @ResponseBody
    public ReturnModel getSysMuseumIdDetails(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
        SysMuseum sysMuseum  = sysMuseumService.getSysMuseumIdDetails(search);
        returnModel.setData(sysMuseum);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }

    @ApiOperation("根据博物馆藏品id获取播报内容")
    @GetMapping("getCollectionIdBroadcast")
    @ResponseBody
    public ReturnModel getCollectionIdBroadcast(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
        SysMuseumCollectionBroadcast sysMuseumCollectionBroadcast = sysMuseumService.getCollectionIdBroadcast(search);
        returnModel.setData(sysMuseumCollectionBroadcast);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }


    @ApiOperation("根据坐标获取是否在博物馆内,未找到则返回null")
    @GetMapping("getGpsMuseum")
    @ResponseBody
    public ReturnModel getGpsMuseum(String lng, String lat) {

        ReturnModel returnModel = new ReturnModel();
        if (StringUtils.isEmpty(lng) || StringUtils.isEmpty(lat)){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("未上传坐标值,请上传后查询!");
        }
        Map<String,Object> map  = sysMuseumService.getGpsMuseum(lng,lat);
        returnModel.setData(map);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;

    }


    @ApiOperation("根据藏品id,或者名称获取藏品详情")
    @GetMapping("getMuseumCollectionIdDetails")
    @ResponseBody
    public ReturnModel getMuseumCollectionIdDetails(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
        if (!StringUtils.isEmpty(baseDataDTO.getMuseumId())){
            search.put("museumId",baseDataDTO.getMuseumId());
        }
        if (!StringUtils.isEmpty(baseDataDTO.getCollectionId())){
            search.put("collectionId",baseDataDTO.getCollectionId());
        }
        if (!StringUtils.isEmpty(baseDataDTO.getContent())){
            search.put("museumCollectionName",baseDataDTO.getContent());
        }
        if (!StringUtils.isEmpty(baseDataDTO.getPinyin())){
            search.put("pinyin",baseDataDTO. getPinyin());
        }

        List<SysMuseumCollection> sysMuseumCollection  = sysMuseumService.getMuseumCollectionIdDetails(search);
        returnModel.setData(sysMuseumCollection);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }


    @ApiOperation("根据博物馆id,获取轮播图")
    @GetMapping("getMuseumBannerList")
    @ResponseBody
    public ReturnModel getMuseumBannerList(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();

        if (!StringUtils.isEmpty(baseDataDTO.getMuseumId())){
            search.put("museumId",baseDataDTO.getMuseumId());
        }else{
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("博物馆id为null,无法查询!");
        }
        List<SysMuseumBanner> sysMuseumCollection  = sysMuseumBannerService.getMuseumBannerList(search);
        returnModel.setData(sysMuseumCollection);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }




}
