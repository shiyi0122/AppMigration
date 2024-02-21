package com.jxzy.AppMigration.NavigationApp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumMapResService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumMapRes;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 地图资源维护 前端控制器
 * </p>
 *
 * @author libofan
 * @since 2023-10-29
 */
@Api(tags = "后台管理-博物馆地图")
@CrossOrigin
@RestController
@RequestMapping("/sysMuseumMapRes")
public class SysMuseumMapResController {
    @Autowired
    private SysMuseumMapResService sysMuseumMapResService;

    @ApiOperation("上传博物馆地图")
    @PostMapping("addMuseumMapRes")
    public ReturnModel addMuseumMapRes(@RequestPart(value = "$resPic",required = false) MultipartFile resPic,
                                       @RequestPart(value = "$resMarkPic",required = false) MultipartFile resMarkPic,
                                       @RequestPart(value = "$resMarkPicFile",required = false) MultipartFile resMarkPicFile,
                                       SysMuseumMapRes museumMapRes) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int res = sysMuseumMapResService.save(resPic, resMarkPic, resMarkPicFile, museumMapRes);
            if (res == -2) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PICTURE_TYPE_STR);
            }  else if (res == -4) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PIC_SIZE_STR);
            } else {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("上传成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("上传失败");
            return returnModel;
        }
        return returnModel;
    }

    @ApiOperation("编辑博物馆地图")
    @PostMapping("editMuseumMapRes")
    public ReturnModel editMuseumMapRes(@RequestPart(value = "$resPic",required = false) MultipartFile resPic,
                                        @RequestPart(value = "$resMarkPic",required = false) MultipartFile resMarkPic,
                                        @RequestPart(value = "$resMarkPicFile",required = false) MultipartFile resMarkPicFile,
                                        SysMuseumMapRes museumMapRes) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int res = sysMuseumMapResService.edit(resPic, resMarkPic, resMarkPicFile, museumMapRes);
            if (res == 2) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PICTURE_TYPE_STR);
            } else if (res == 3) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.PIC_IS_EMPTY_STR);
            } else if (res == 4) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PIC_SIZE_STR);
            } else {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("编辑成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("编辑失败");
            return returnModel;
        }
        return returnModel;
    }

    @ApiOperation("删除博物馆地图")
    @PostMapping("delMuseumMapRes")
    public ReturnModel delMuseumMapRes(@RequestParam("resId") Long resId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumMapResService.removeById(resId)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("删除成功");
                return returnModel;
            }
            returnModel.setData(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
            return returnModel;
        }
    }

    @ApiOperation("博物馆地图列表")
    @GetMapping("getMuseumMapRes")
    public ReturnModel getMuseumMapRes(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       SysMuseumMapRes museumMapRes) {
        ReturnModel returnModel = new ReturnModel();
        Page<SysMuseumMapRes> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysMuseumMapRes> smmrlqw = Wrappers.lambdaQuery(SysMuseumMapRes.class);
        sysMuseumMapResService.page(page, smmrlqw,museumMapRes);
        returnModel.setData(page.getRecords());
        returnModel.setTotal(((int) page.getTotal()));
        return returnModel;
    }

    @ApiOperation("搜索博物馆地图（博物馆名称）")
    @GetMapping("getMuseumMapResByCondition")
    public ReturnModel getMuseumMapResByCondition(String museumName) {
        ReturnModel returnModel = new ReturnModel();
        List<SysMuseumMapRes> museumMapResList = sysMuseumMapResService.getMuseumMapResByCondition(museumName);
        returnModel.setData(museumMapResList);
        returnModel.setTotal(museumMapResList.size());
        return returnModel;
    }
}
