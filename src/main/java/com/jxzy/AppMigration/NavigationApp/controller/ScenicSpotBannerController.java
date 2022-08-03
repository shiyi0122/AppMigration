package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBannerService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBanner;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/7/30 13:34
 */


@Api(tags = "景区轮播图")
@RestController
@RequestMapping("scenicSpotBanner")
public class ScenicSpotBannerController {

    @Autowired
    SysScenicSpotBannerService scenicSpotBannerService;

    @ApiOperation("获取首页轮播图")
    @GetMapping("getScenicSpotBanner")
    public ReturnModel getScenicSpotBanner(SearchDTO searchDTO){
        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(searchDTO.getSpotId())){
            search.put("spotId",searchDTO.getSpotId());
        }
        search.put("sort",searchDTO.getSort());
        List<SysScenicSpotBanner> list = scenicSpotBannerService.getScenicSpotBanner(search);
        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取轮播图成功!");

        return returnModel;
    }

    @ApiOperation("添加首页轮播图")
    @PostMapping("/addBroadcastHuntRule")
    public ReturnModel addScenicSpotBanner(@RequestPart("file") MultipartFile file,SysScenicSpotBanner sysScenicSpotBanner){

        ReturnModel returnModel = new ReturnModel();

        int i = scenicSpotBannerService.addScenicSpotBanner(file,sysScenicSpotBanner);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加轮播图成功!");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加轮播图失败!");
        }
        return returnModel;
    }

    @ApiOperation("修改轮播图首页轮播图")
    @PostMapping("/editBroadcastHuntRule")
    public ReturnModel editScenicSpotBanner(@RequestPart("file") MultipartFile file,SysScenicSpotBanner sysScenicSpotBanner){

        ReturnModel returnModel = new ReturnModel();

        int i = scenicSpotBannerService.editScenicSpotBanner(file,sysScenicSpotBanner);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改轮播图成功!");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改轮播图失败!");
        }
        return returnModel;
    }






}
