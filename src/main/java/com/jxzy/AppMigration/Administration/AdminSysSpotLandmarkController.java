package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysSpotLandmarkService;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLandmark;
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
 * @Date 2023/1/16 17:02
 * 后台地标管理
 */

@Api(tags = "后台管理-地标管理")
@RestController
@RequestMapping("adminSysSpotLandmark")
@CrossOrigin
public class AdminSysSpotLandmarkController {

    @Autowired
    SysSpotLandmarkService sysSpotLandmarkService;

    /**
     * 后台管理——添加酒店民宿（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("添加地标（无文件）")
    @PostMapping("addSpotLandmark")
    @ResponseBody
    public ReturnModel addSpotLandmark(@RequestBody SysSpotLandmark sysSpotLandmark) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotLandmarkService.addSpotLandmark(sysSpotLandmark);
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
     * 后台管理——修改地标（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("修改地标（无文件）")
    @PostMapping("editSpotLandmark")
    @ResponseBody
    public ReturnModel editSpotLandmark(@RequestBody SysSpotLandmark sysSpotLandmark) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotLandmarkService.editSpotLandmark(sysSpotLandmark);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("编辑成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("编辑失败！");
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
     * 后台管理——删除地标
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除地标")
    @GetMapping("delSpotLandmark")
    @ResponseBody
    public ReturnModel delSpotLandmark(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotLandmarkService.delSpotLandmark(id);
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


    /**
     * 查询地标列表
     * @param pageDTO
     * @return
     */
    @ApiOperation("查询地标列表")
    @GetMapping("getSpotLandmarkList")
    @ResponseBody
    public PageDataResult getSpotLandmarkList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getContent())){
            search.put("content",pageDTO.getContent());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }
        pageDataResult =  sysSpotLandmarkService.getAdminSpotLandmarkList(pageDTO.getPageNum(), pageDTO.getPageSize(),search);

       return pageDataResult;

    }



}
