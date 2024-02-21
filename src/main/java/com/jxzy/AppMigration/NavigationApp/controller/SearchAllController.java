package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.dao.SysGameMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysGoodThingsShopMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/15 15:11
 * 首页搜索
 */
@Api(tags = "游娱go首页搜索")
@CrossOrigin
@RestController
@RequestMapping("searchAll")
public class SearchAllController {


    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @Autowired
    SysHotelService sysHotelService;

    @Autowired
    SysGoodThingsShopService sysGoodThingsShopService;

    @Autowired
    SysGameService sysGameService;

    @Autowired
    SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    SysFeaturedFoodService sysFeaturedFoodService;

    @Autowired
    SysStrategyService sysStrategyService;

    @Autowired
    SysSpotDynamicService sysSpotDynamicService;

    @ApiOperation("首页搜索")
    @GetMapping("all")
    @ResponseBody
    public PageDataResult all(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();


        if (!StringUtils.isEmpty(pageDTO.getContent())){
            search.put("content",pageDTO.getContent());
        }
        if (!StringUtils.isEmpty(pageDTO.getLng())){
            search.put("lng",pageDTO.getLng());
        }
        if (!StringUtils.isEmpty(pageDTO.getLat())){
            search.put("lat",pageDTO.getLat());
        }
        if (!StringUtils.isEmpty(pageDTO.getUid())){
            search.put("uid", pageDTO.getUid());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotBindingName())){
            search.put("cityName",pageDTO.getSpotBindingName());
        }
        search.put("pageNum",pageDTO.getPageNum());
        search.put("pageSize",pageDTO.getPageSize());
        if ("1".equals(pageDTO.getType())){//景区

            if (StringUtils.isEmpty(pageDTO.getLng()) || StringUtils.isEmpty(pageDTO.getLat())){
                pageDataResult =  sysScenicSpotService.currentCityAll(pageDTO.getSort(),pageDTO.getContent(),pageDTO.getSpotBindingName(),pageDTO.getPageNum(),pageDTO.getPageSize());
            }else{
                pageDataResult =  sysScenicSpotService.currentCity(pageDTO.getLng(),pageDTO.getLat(), pageDTO.getContent(),pageDTO.getSpotBindingName(),pageDTO.getSort(),pageDTO.getPageNum(),pageDTO.getPageSize());
            }
            return pageDataResult;

        }else if ("2".equals(pageDTO.getType())){//特色民宿

            pageDataResult =   sysHotelService.getAllSysHotel(search);

            return pageDataResult;
        }else if ("3".equals(pageDTO.getType())){//地道好物

            pageDataResult = sysGoodThingsShopService.getAllSysThingsShop(search);

            return pageDataResult;
        }else if ("4".equals(pageDTO.getType())){//娱乐休闲

            pageDataResult =  sysGameService.getAllSysGameShop(search);
            return pageDataResult;
        }else if ("5".equals(pageDTO.getType())){//景点

             pageDataResult = sysScenicSpotBroadcastService.getAllSysBroadcastPage(search);

            return pageDataResult;
        }else if ("6".equals(pageDTO.getType())){//特色美食

             pageDataResult = sysFeaturedFoodService.getAllSysFeaturedFoodShop(search);
             return pageDataResult;
        }else if ("7".equals(pageDTO.getType())){//游娱攻略

            search.put("type",1);
            pageDataResult = sysStrategyService.getAllSysStrategy(search);
            return pageDataResult;
        }else if ("8".equals(pageDTO.getType())){//景区动态

            pageDataResult = sysSpotDynamicService.getAllSysSpotDynamic(search);

            return pageDataResult;
        }else if ("9".equals(pageDTO.getType())){//游记

            search.put("type",2);
            pageDataResult = sysStrategyService.getAllSysStrategy(search);
            return pageDataResult;

        }else{

        }
//         s.sysUserAlbumAppList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }

    @ApiOperation("首页顶部搜索")
    @GetMapping("allTop")
    @ResponseBody
    public ReturnModel allTop(PageDTO pageDTO){

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> map = new HashMap<String,Object>();

        Map<String, Object> search = new HashMap<>();

        if (!StringUtils.isEmpty(pageDTO.getContent())){
            search.put("content",pageDTO.getContent());
        }
        if (!StringUtils.isEmpty(pageDTO.getLng())){
            search.put("lng",pageDTO.getLng());
        }
        if (!StringUtils.isEmpty(pageDTO.getLat())){
            search.put("lat",pageDTO.getLat());
        }
        if (!StringUtils.isEmpty(pageDTO.getUid())){
            search.put("uid", pageDTO.getUid());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotBindingName())){
            search.put("cityName",pageDTO.getSpotBindingName());
        }
        search.put("pageNum",pageDTO.getPageNum());
        search.put("pageSize",pageDTO.getPageSize());

        //搜索景区
        if (StringUtils.isEmpty(pageDTO.getLng()) || StringUtils.isEmpty(pageDTO.getLat())){
            List<SysScenicSpot> list  =  sysScenicSpotService.currentCityAllN(pageDTO.getSort(),pageDTO.getContent(),pageDTO.getSpotBindingName(),pageDTO.getPageNum(),pageDTO.getPageSize());
            map.put("spot",list);
        }else{
            List<SysScenicSpot> list  =  sysScenicSpotService.currentCityN(pageDTO.getLng(),pageDTO.getLat(), pageDTO.getContent(),pageDTO.getSpotBindingName(),pageDTO.getSort(),pageDTO.getPageNum(),pageDTO.getPageSize());
            map.put("spot",list);
        }
        //特色民宿
         List<SysHotel> hotelList =  sysHotelService.getAllSysHotelN(search);
         map.put("hotel",hotelList);
         //地道好物
         List<SysGoodThingsShop> goodThingsShopList = sysGoodThingsShopService.getAllSysThingsShopN(search);
         map.put("goodThingsShop",goodThingsShopList);
         //娱乐休闲
         List<SysGame> gameList = sysGameService.getAllSysGameShopN(search);
         map.put("game",gameList);
         //景点
         List<SysScenicSpotBroadcast> broadcastList = sysScenicSpotBroadcastService.getAllSysBroadcast(search);
         map.put("broadcast",broadcastList);
        //特色美食
        List<SysFeaturedFoodShop> foodList = sysFeaturedFoodService.getAllSysFeaturedFoodShopN(search);
         map.put("featuredFoodShop",foodList);
         //游娱攻略
        search.put("type",1);
        List<SysStrategy> strategyList = sysStrategyService.getAllSysStrategyN(search);
        map.put("strategyG",strategyList);
        //景区动态
        List<SysSpotDynamic> dynamicList = sysSpotDynamicService.getAllSysSpotDynamicN(search);
        map.put("dynamic",dynamicList);
        //游记
        search.put("type",2);
        List<SysStrategy> strategyList1 = sysStrategyService.getAllSysStrategyN(search);
        map.put("strategyY",strategyList1);

        returnModel.setData(map);
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;

    }





}
