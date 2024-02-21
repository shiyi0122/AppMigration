package com.jxzy.AppMigration.NavigationApp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumBannerService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumBanner;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 博物馆轮播图 前端控制器
 * </p>
 *
 * @author libofan
 * @since 2023-10-29
 */
@Api(tags = "后台管理-博物馆轮播图")
@CrossOrigin
@RestController
@RequestMapping("/sysMuseumBanner")
public class SysMuseumBannerController {
    @Autowired
    private SysMuseumBannerService sysMuseumBannerService;

    @ApiOperation("上传博物馆轮播图")
    @PostMapping("addSysMuseumBanner")
    public ReturnModel addMuseumBanner(@RequestPart("photo") MultipartFile photo, SysMuseumBanner museumBanner) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int res = sysMuseumBannerService.save(photo, museumBanner);
            if (res == -4) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PIC_SIZE_STR);
                return returnModel;
            } else if (res == -2) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PICTURE_TYPE_STR);
                return returnModel;
            }
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
            return returnModel;
        }
    }

    @ApiOperation("编辑博物馆轮播图")
    @PostMapping("editMuseumBanner")
    public ReturnModel editMuseumBanner(@RequestPart("photo") MultipartFile photo, SysMuseumBanner museumBanner) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int res = sysMuseumBannerService.edit(photo, museumBanner);
            if (res == -4) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PIC_SIZE_STR);
                return returnModel;
            } else if (res == -2) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PICTURE_TYPE_STR);
                return returnModel;
            }
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
            return returnModel;
        }
    }

    @ApiOperation("删除博物馆轮播图")
    @PostMapping("delMuseumBanner")
    public ReturnModel delMuseumBanner(@RequestParam("id") Long id) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumBannerService.removeById(id)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("删除成功");
                return returnModel;
            }
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
            return returnModel;
        }
    }
    @ApiOperation("查询博物馆轮播图")
    @GetMapping("getMuseumBanner")
    public ReturnModel getMuseumBanner(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       SysMuseumBanner sysMuseumBanner){
        ReturnModel returnModel = new ReturnModel();
        Page<SysMuseumBanner> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysMuseumBanner> smblqw = Wrappers.lambdaQuery(SysMuseumBanner.class);
        sysMuseumBannerService.page(page,smblqw,sysMuseumBanner);
        returnModel.setData(page.getRecords());
        returnModel.setTotal(((int) page.getTotal()));
        return returnModel;
    }
}
