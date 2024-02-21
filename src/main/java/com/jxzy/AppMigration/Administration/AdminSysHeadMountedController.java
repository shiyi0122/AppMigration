package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysHeadMountedService;
import com.jxzy.AppMigration.NavigationApp.entity.SysHeadMounted;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/27 13:28
 */

@Api(tags = "后台管理-博物馆头戴设备相关接口")
@RestController
@RequestMapping("adminSysHeadMounted")
@CrossOrigin
public class AdminSysHeadMountedController {


    @Autowired
    SysHeadMountedService sysHeadMountedService;


    /**
     * 后台管理——添加头戴式设备
     *
     * @param
     * @return
     */
    @ApiOperation("添加头戴设备")
    @PostMapping("addSysHeadMounted")
    @ResponseBody
    public ReturnModel addSysHeadMounted(@RequestPart("file") MultipartFile file, SysHeadMounted sysHeadMounted) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysHeadMountedService.addSysHeadMounted(file,sysHeadMounted);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }else if (i == 2){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        }else if (i == 3){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 后台管理——添加头戴式设备（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("添加头戴设备（无文件）")
    @PostMapping("addSysHeadMountedN")
    @ResponseBody
    public ReturnModel addSysHeadMountedN(SysHeadMounted sysHeadMounted) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysHeadMountedService.addSysHeadMountedN(sysHeadMounted);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }else if (i == 2){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        }else if (i == 3){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 修改头戴式设备信息
     * @param file
     * @param
     * @return
     */
    @ApiOperation("修改头戴式设备信息")
    @PostMapping("editSysHeadMounted")
    @ResponseBody
    public ReturnModel editSysHeadMounted(@RequestPart("file") MultipartFile file, SysHeadMounted sysHeadMounted) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysHeadMountedService.editSysHeadMounted(file,sysHeadMounted);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        }else if (i == 2){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        }else if (i == 3){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }

    /**
     * 修改头戴式设备信息(无文件)
     * @param
     * @param
     * @return
     */
    @ApiOperation("修改头戴式设备信息(无文件)")
    @PostMapping("editSysHeadMountedN")
    @ResponseBody
    public ReturnModel editSysHeadMountedN(SysHeadMounted sysHeadMounted) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysHeadMountedService.editSysHeadMountedN(sysHeadMounted);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        }else if (i == 2){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        }else if (i == 3){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 后台管理——删除头戴式设备
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除头戴式设备")
    @GetMapping("delSysHeadMounted")
    @ResponseBody
    public ReturnModel delSysHeadMounted(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysHeadMountedService.delSysHeadMounted(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;

    }


    /**
     * 管理后台-头戴式设备列表查询
     * @param pageDTO
     * @return
     */
    @ApiOperation("管理后台头戴式设备列表查询")
    @GetMapping("getSysHeadMountedList")
    @ResponseBody
    public PageDataResult getSysHeadMountedList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        if (StringUtils.isEmpty(pageDTO.getSort())) {
            pageDTO.setSort(6);
        }
        Map<String, Object> search = new HashMap<>();
//        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
//            search.put("spotId",pageDTO.getSpotId());
//        }
        if (!StringUtils.isEmpty(pageDTO.getMuseumId())){
            search.put("museumId",pageDTO.getMuseumId());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotName())){
            search.put("museumName",pageDTO.getMuseumName());
        }
        if (!StringUtils.isEmpty(pageDTO.getScenicSpotFid())){
            search.put("spotFid",pageDTO.getScenicSpotFid());
        }
        if (!StringUtils.isEmpty(pageDTO.getScenicSpotSid())){
            search.put("spotSid", pageDTO.getScenicSpotSid());
        }
        if (!StringUtils.isEmpty(pageDTO.getScenicSpotQid())){
            search.put("spotQid",pageDTO.getScenicSpotQid());
        }
        search.put("sort",pageDTO.getSort());
        pageDataResult = sysHeadMountedService.adminSysHeadMountedList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;
    }




}
