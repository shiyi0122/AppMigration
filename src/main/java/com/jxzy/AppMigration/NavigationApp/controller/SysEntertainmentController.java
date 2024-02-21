package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysEntertainmentService;
import com.jxzy.AppMigration.NavigationApp.Service.SysGameService;
import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainment;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 17:17
 * 娱乐项目
 */
@Api(tags = "游娱go娱乐设施新")
@CrossOrigin
@RestController
@RequestMapping("sysEntertainment")
public class SysEntertainmentController {

    @Autowired
    SysEntertainmentService sysEntertainmentService;


    @ApiOperation("查询娱乐设施列表")
    @GetMapping("getSysEntertainmentList")
    @ResponseBody
    public PageDataResult getSysEntertainmentList(PageDTO pageDTO) {

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

        pageDataResult = sysEntertainmentService.getSysEntertainmentAppList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }


    @ApiOperation("根据娱乐设施id获取详情")
    @GetMapping("getSysEntertainmentIdDetails")
    @ResponseBody
    public ReturnModel getSysEntertainmentIdDetails(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
        SysEntertainment sysEntertainment  = sysEntertainmentService.getSysEntertainmentIdDetails(search);
        returnModel.setData(sysEntertainment);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }


    @ApiOperation("娱乐设施点赞")
    @GetMapping("addGiveTheThumbsUp")
    @ResponseBody
    public ReturnModel addGiveTheThumbsUp(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
       int i  = sysEntertainmentService.addGiveTheThumbsUp(baseDataDTO.getId(),baseDataDTO.getType(),baseDataDTO.getUid());

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

    @ApiOperation("取消娱乐设施点赞")
    @GetMapping("delGiveTheThumbsUp")
    @ResponseBody
    public ReturnModel delGiveTheThumbsUp(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
        int i  = sysEntertainmentService.delGiveTheThumbsUp(baseDataDTO.getId(),baseDataDTO.getType(),baseDataDTO.getUid());

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
