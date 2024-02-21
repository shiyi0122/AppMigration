package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysFeaturedFoodService;
import com.jxzy.AppMigration.NavigationApp.Service.SysFeaturedFoodShopService;
import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFood;
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

import javax.naming.ldap.PagedResultsControl;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/9 17:34
 */

@Api(tags = "游娱go特色美食")
@CrossOrigin
@RestController
@RequestMapping("sysFeaturedFoodShop")
public class SysFeaturedFoodShopController {

    @Autowired
    SysFeaturedFoodShopService sysFeaturedFoodShopService;

    @Autowired
    SysFeaturedFoodService sysFeaturedFoodService;

    @ApiOperation("查询特色美食店铺列表")
    @GetMapping("getSysFeaturedFoodShopList")
    @ResponseBody
    public PageDataResult getSysNavigationList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())) {
            search.put("spotId", pageDTO.getSpotId());
        }
        if (!StringUtils.isEmpty(pageDTO.getContent())) {
            search.put("content", pageDTO.getContent());
        }
        if (!StringUtils.isEmpty(pageDTO.getLng())) {
            search.put("lng", pageDTO.getLng());
        }
        if (!StringUtils.isEmpty(pageDTO.getLat())) {
            search.put("lat", pageDTO.getLat());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotBindingName())) {
            search.put("cityName", pageDTO.getSpotBindingName());
        }
        if (!StringUtils.isEmpty(pageDTO.getUid())) {
            search.put("uid", pageDTO.getUid());
        }

        pageDataResult = sysFeaturedFoodShopService.getSysFeaturedFoodShopAppList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);

        return pageDataResult;
    }


    @ApiOperation("查询特色美食店铺详情")
    @GetMapping("getSysFeaturedFoodDetails")
    @ResponseBody
    public PageDataResult getSysFeaturedFoodDetails(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getShopsId())) {
            search.put("shopsId", pageDTO.getShopsId());
        }
        if (!StringUtils.isEmpty(pageDTO.getId())) {
            search.put("Id", pageDTO.getId());
        }
        if (!StringUtils.isEmpty(pageDTO.getLng())) {
            search.put("lng", pageDTO.getLng());
        }
        if (!StringUtils.isEmpty(pageDTO.getLat())) {
            search.put("lat", pageDTO.getLat());
        }


        pageDataResult = sysFeaturedFoodService.getSysFeaturedFoodAppList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);

        return pageDataResult;
    }


    @ApiOperation("美食店铺或店铺商品点赞")
    @GetMapping("addGiveTheThumbsUp")
    @ResponseBody
    public ReturnModel addGiveTheThumbsUp(BaseDataDTO baseDataDTO) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodShopService.addGiveTheThumbsUp(baseDataDTO.getId(), baseDataDTO.getType(), baseDataDTO.getUid());
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("点赞成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("点赞失败");
        }
        return returnModel;

    }

    @ApiOperation("取消店铺或店铺商品点赞")
    @GetMapping("delGiveTheThumbsUp")
    @ResponseBody
    public ReturnModel delGiveTheThumbsUp(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysFeaturedFoodShopService.delGiveTheThumbsUp(baseDataDTO.getId(), baseDataDTO.getType(), baseDataDTO.getUid());

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("取消点赞成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("取消点赞失败");
        }
        return returnModel;

    }


}
