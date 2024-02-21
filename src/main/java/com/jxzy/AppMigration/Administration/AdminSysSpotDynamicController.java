package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysSpotDynamicService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamic;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamicBanner;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamicContent;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/12 19:33
 * 后台动态
 */

@Api(tags = "后台管理-动态相关接口")
@RestController
@RequestMapping("adminSysSpotDynamic")
@CrossOrigin
public class AdminSysSpotDynamicController {

    @Autowired
    SysSpotDynamicService sysSpotDynamicService;


    /**
     * 后台管理——添加动态
     *
     * @param
     * @return
     */
    @ApiOperation("添加动态")
    @PostMapping("addSysSpotDynamic")
    @ResponseBody
    public ReturnModel addSysSpotDynamic(@RequestPart("file") MultipartFile file, SysSpotDynamic sysSpotDynamic) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotDynamicService.addSysSpotDynamic(file, sysSpotDynamic);
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


    /**
     * 后台管理——添加动态
     *
     * @param
     * @return
     */
    @ApiOperation("添加动态(无文件)")
    @PostMapping("addSysSpotDynamicN")
    @ResponseBody
    public ReturnModel addSysSpotDynamicN(@RequestBody SysSpotDynamic sysSpotDynamic) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotDynamicService.addSysSpotDynamicN( sysSpotDynamic);
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

    /**
     * 后台管理——修改动态
     *
     * @param
     * @return
     */
    @ApiOperation("修改动态")
    @PostMapping("editSysSpotDynamic")
    @ResponseBody
    public ReturnModel editSysSpotDynamic(@RequestPart("file") MultipartFile file, SysSpotDynamic sysSpotDynamic) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotDynamicService.editSysSpotDynamic(file, sysSpotDynamic);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
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

    /**
     * 后台管理——修改动态
     *
     * @param
     * @return
     */
    @ApiOperation("修改动态(无文件)")
    @PostMapping("editSysSpotDynamicN")
    @ResponseBody
    public ReturnModel editSysSpotDynamicN(@RequestBody SysSpotDynamic sysSpotDynamic) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotDynamicService.editSysSpotDynamicN( sysSpotDynamic);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
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

    /**
     * 后台管理——删除好物店铺
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除动态")
    @GetMapping("delSysSpotDynamic")
    @ResponseBody
    public ReturnModel delSysSpotDynamic(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotDynamicService.delSysSpotDynamic(id);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }


    @ApiOperation("查询动态列表")
    @GetMapping("getSysSpotDynamicList")
    @ResponseBody
    public PageDataResult getSysSpotDynamicList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (pageDTO.getSpotId() != null) {
            search.put("spotId", pageDTO.getSpotId());
        }
        if (pageDTO.getContent() != null) {
            search.put("content", pageDTO.getContent());
        }
        if (pageDTO.getTitle() != null) {
            search.put("title", pageDTO.getTitle());
        }
        if (pageDTO.getStartTime() != null){
            search.put("startTime",pageDTO.getStartTime());
        }
        if (pageDTO.getSpotName() != null){
            search.put("spotName",pageDTO.getSpotName());
        }
        pageDataResult = sysSpotDynamicService.getSysSpotDynamicList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }

    @ApiOperation("添加动态轮播图")
    @PostMapping("addSysSpotDynamicBanner")
    @ResponseBody
    public ReturnModel addSysSpotDynamicBanner(@RequestBody SysSpotDynamicBanner sysSpotDynamicBanner) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotDynamicService.addSysSpotDynamicBanner(sysSpotDynamicBanner);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("上传成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("上传失败");
        }

        return returnModel;

    }


    @ApiOperation("编辑动态轮播图")
    @PostMapping("editSysSpotDynamicBanner")
    @ResponseBody
    public ReturnModel editSysSpotDynamicBanner(@RequestBody SysSpotDynamicBanner sysSpotDynamicBanner) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotDynamicService.editSysSpotDynamicBanner(sysSpotDynamicBanner);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }
        return returnModel;

    }


    @ApiOperation("删除动态轮播图")
    @GetMapping("delSysSpotDynamicBanner")
    @ResponseBody
    public ReturnModel delSysSpotDynamicBanner(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotDynamicService.delSysSpotDynamicBanner(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }

        return returnModel;
    }

    @ApiOperation("查询动态轮播图")
    @GetMapping("getSysSpotDynamicBannerList")
    @ResponseBody
    public PageDataResult getSysSpotDynamicBannerList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysSpotDynamicService.getSysSpotDynamicBannerList(pageDTO.getId(),pageDTO.getPageNum(),pageDTO.getPageSize());

        return pageDataResult;
    }


    @ApiOperation("添加动态内容")
    @PostMapping("addSysSpotDynamicContent")
    @ResponseBody
    public ReturnModel addSysSpotDynamicContent(@RequestBody SysSpotDynamicContent sysSpotDynamicContent) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotDynamicService.addSysSpotDynamicContent(sysSpotDynamicContent);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }

        return returnModel;

    }


    @ApiOperation("修改动态内容")
    @PostMapping("editSysSpotDynamicContent")
    @ResponseBody
    public ReturnModel editSysSpotDynamicContent(@RequestBody SysSpotDynamicContent sysSpotDynamicContent) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotDynamicService.editSysSpotDynamicContent(sysSpotDynamicContent);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }

        return returnModel;

    }



    @ApiOperation("删除动态内容")
    @GetMapping("delSysSpotDynamicContent")
    @ResponseBody
    public ReturnModel delSysSpotDynamicContent(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i =  sysSpotDynamicService.delSysSpotDynamicContent(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }
        return returnModel;
    }

    @ApiOperation("删除动态内容图片")
    @GetMapping("delSysSpotDynamicContentPic")
    @ResponseBody
    public ReturnModel delSysSpotDynamicContentPic(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i =  sysSpotDynamicService.delSysSpotDynamicContentPic(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }
        return returnModel;
    }

    @ApiOperation("查询动态内容")
    @GetMapping("getSysSpotDynamicContentList")
    @ResponseBody
    public PageDataResult getSysSpotDynamicContentList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysSpotDynamicService.getSysSpotDynamicContentList(pageDTO.getId(), pageDTO.getPageNum(), pageDTO.getPageSize());
        return pageDataResult;

    }



}
