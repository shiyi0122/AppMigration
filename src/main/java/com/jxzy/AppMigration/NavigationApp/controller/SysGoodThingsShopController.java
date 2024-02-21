package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysGoodThingsService;
import com.jxzy.AppMigration.NavigationApp.Service.SysGoodThingsShopService;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 17:55
 * 地道好物店铺
 */
@Api(tags = "游娱go地道好物店铺")
@CrossOrigin
@RestController
@RequestMapping("sysGoodThingsShop")
public class SysGoodThingsShopController {

    @Autowired
    SysGoodThingsShopService sysGoodThingsShopService;

    @Autowired
    SysGoodThingsService sysGoodThingsService;

    @ApiOperation("查询好物店铺列表")
    @GetMapping("getSysGoodThingsShopList")
    @ResponseBody
    public PageDataResult getSysFeaturedFoodShopList(PageDTO pageDTO) {

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

        pageDataResult = sysGoodThingsShopService.getSysGoodThingsShopAppList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }

    @ApiOperation("查询好物店铺详情")
    @GetMapping("getDetails")
    @ResponseBody
    public PageDataResult getSysFeaturedFoodShopDetails(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getShopsId())){
            search.put("shopsId",pageDTO.getShopsId());
        }
        if (!StringUtils.isEmpty(pageDTO.getLng())){
            search.put("lng",pageDTO.getLng());
        }
        if (!StringUtils.isEmpty(pageDTO.getLat())){
            search.put("lat",pageDTO.getLat());
        }


        pageDataResult = sysGoodThingsService.getSysGoodThingsAppList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);

        return pageDataResult;
    }


    @ApiOperation("好物店铺或店铺商品点赞")
    @GetMapping("addGiveTheThumbsUp")
    @ResponseBody
    public ReturnModel addGiveTheThumbsUp(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGoodThingsShopService.addGiveTheThumbsUp(baseDataDTO.getId(),baseDataDTO.getType(),baseDataDTO.getUid());

        if (i > 0 ) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("点赞成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("点赞失败");
        }
        return returnModel;

    }

    @ApiOperation("取消好物店铺或店铺商品点赞")
    @GetMapping("delGiveTheThumbsUp")
    @ResponseBody
    public ReturnModel delGiveTheThumbsUp(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGoodThingsShopService.delGiveTheThumbsUp(baseDataDTO.getId(),baseDataDTO.getType(),baseDataDTO.getUid());

        if (i > 0 ) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("取消点赞成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("取消点赞失败");
        }
        return returnModel;

    }




}
