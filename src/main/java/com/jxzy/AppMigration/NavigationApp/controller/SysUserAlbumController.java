package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysUserAlbumService;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategy;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserAlbum;
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
 * @Date 2023/1/15 14:14
 *
 * 我的相册
 */
@Api(tags = "游娱go用户相册接口")
@CrossOrigin
@RestController
@RequestMapping("sysUserAlbum")
public class SysUserAlbumController {

    @Autowired
    SysUserAlbumService sysUserAlbumService;

    @ApiOperation("添加攻略,游记,广场")
    @PostMapping("addSysStrategy")
    @ResponseBody
    public ReturnModel addSysUserAlbum(SysUserAlbum sysUserAlbum) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysUserAlbumService.addSysUserAlbum(sysUserAlbum);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }

    @ApiOperation("查询我的相册列表")
    @GetMapping("sysUserAlbumAppList")
    @ResponseBody
    public PageDataResult sysUserAlbumAppList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
//        if (!StringUtils.isEmpty(pageDTO.getContent())){
//            search.put("content",pageDTO.getContent());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getLng())){
//            search.put("lng",pageDTO.getLng());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getLat())){
//            search.put("lat",pageDTO.getLat());
//        }

        if (!StringUtils.isEmpty(pageDTO.getUid())){
            search.put("uid", pageDTO.getUid());
        }
        pageDataResult = sysUserAlbumService.sysUserAlbumAppList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }





}
