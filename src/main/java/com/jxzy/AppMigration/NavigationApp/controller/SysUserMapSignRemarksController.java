package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysUserMapSignRemarksService;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserMapSignRemarks;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/10 19:38
 */
@Api(tags = "用户地图标记备注相关接口")
@RestController
@RequestMapping("userMapSignRemarks")
public class SysUserMapSignRemarksController {

    @Autowired
    SysUserMapSignRemarksService sysUserMapSignRemarksService;

    /**
     * zhang
     *
     * @param sysUserMapSignRemarks
     * @return
     */
    @ApiOperation("添加用户地图标记")
    @PostMapping("addUserMapSignRemarks")
    @ResponseBody
    public ReturnModel addUserMapSignRemarks(@RequestBody SysUserMapSignRemarks sysUserMapSignRemarks) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysUserMapSignRemarksService.addUserMapSignRemarks(sysUserMapSignRemarks);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }
        return returnModel;
    }


    /**
     * 查询用户的标记地点
     * 张
     */

    @ApiOperation("查询用户的标记地点")
    @GetMapping("getUserMapSignRemarksList")
    @ResponseBody
    public PageDataResult getUserMapSignRemarksList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();

        if (StringUtils.isEmpty(pageDTO.getSpotId())) {
            pageDataResult.setCode(400);
            return pageDataResult;
        }
        search.put("userId",pageDTO.getUid());
        search.put("spotId", pageDTO.getSpotId());

        pageDataResult = sysUserMapSignRemarksService.getUserMapSignRemarksList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }
}