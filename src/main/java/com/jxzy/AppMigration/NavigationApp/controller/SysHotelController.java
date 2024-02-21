package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysHotelService;
import com.jxzy.AppMigration.NavigationApp.dao.SysHotelFabulousMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysHotel;
import com.jxzy.AppMigration.NavigationApp.entity.SysHotelFabulous;
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
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 15:27
 * 酒店民宿
 */
@Api(tags = "游娱go酒店民宿")
@CrossOrigin
@RestController
@RequestMapping("sysHotel")
public class SysHotelController {

    @Autowired
    SysHotelService sysHotelService;

    @Autowired
    SysHotelFabulousMapper sysHotelFabulousMapper;

    @ApiOperation("查询酒店民宿列表")
    @GetMapping("getSysHotelList")
    @ResponseBody
    public PageDataResult getSysHotelList(PageDTO pageDTO) {

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

        pageDataResult = sysHotelService.getSysHotelAppList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }

    @ApiOperation("根据酒店id获取酒店详情")
    @GetMapping("getSysHotelIdDetails")
    @ResponseBody
    public ReturnModel getSysHotelIdDetails(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }

        SysHotel sysHotel = sysHotelService.getSysHotelIdDetails(search);
        returnModel.setData(sysHotel);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");

        return returnModel;
    }


    @ApiOperation("酒店民宿点赞")
    @GetMapping("addGiveTheThumbsUp")
    @ResponseBody
    public ReturnModel addGiveTheThumbsUp(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
        int i   = sysHotelFabulousMapper.addGiveTheThumbsUp(baseDataDTO.getId(),baseDataDTO.getType(),baseDataDTO.getUid());

        if (i > 0 ){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("点赞成功");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("点赞失败");
            return returnModel;
        }

    }

    @ApiOperation("取消取消酒店民宿点赞")
    @GetMapping("delGiveTheThumbsUp")
    @ResponseBody
    public ReturnModel delGiveTheThumbsUp(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
        int i  = sysHotelFabulousMapper.delGiveTheThumbsUp(baseDataDTO.getId(),baseDataDTO.getType(),baseDataDTO.getUid());

        if (i > 0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("取消点赞成功");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("取消点赞失败");
            return returnModel;
        }

    }




}
