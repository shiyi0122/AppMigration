package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBindingService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.ContentHandler;
import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/17 16:16
 */
@Api(tags = "城市切换")
@RestController
@RequestMapping("scenicSpotBinding")
public class SysScenicSpotBindingController {

    @Autowired
    SysScenicSpotBindingService sysScenicSpotBindingService;

    @ApiOperation("热门城市")
    @GetMapping("getHotCity")
    @ResponseBody
    public ReturnModel getHotCity() {
        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotBinding> list =  sysScenicSpotBindingService.getHotCity();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;

    }

    @ApiOperation("全部市")
    @GetMapping("getCityAll")
    @ResponseBody
    public ReturnModel getCityAll() {
        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotBinding> list =  sysScenicSpotBindingService.getCityAll();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;

    }
    @ApiOperation("全部县区")
    @GetMapping("getAreaAll")
    @ResponseBody
    public ReturnModel getAreaAll() {
        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotBinding> list =  sysScenicSpotBindingService.getAreaAll();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;

    }

    @ApiOperation("搜索城市区县")
    @GetMapping("getCityArea")
    @ResponseBody
    public ReturnModel getCityArea(BaseDataDTO baseDataDTO) {
        ReturnModel returnModel = new ReturnModel();

        String content = baseDataDTO.getContent();

        List<SysScenicSpotBinding> list =  sysScenicSpotBindingService.getCityArea(content);

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;

    }


    @ApiOperation("市查询")
    @GetMapping("getProvinceCity")
    @ResponseBody
    public ReturnModel getProvinceCity() {
        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotBinding> list =  sysScenicSpotBindingService.getProvinceCity();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;

    }




}
