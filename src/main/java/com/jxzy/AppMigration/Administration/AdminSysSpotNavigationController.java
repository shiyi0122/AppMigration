package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysSpotNavigationService;
import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotNavigation;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author zhang
 * @Date 2023/1/13 9:44
 */

@Api(tags = "后台管理-景区页面导航相关接口")
@RestController
@RequestMapping("adminSysSpotNavigation")
@CrossOrigin
public class AdminSysSpotNavigationController {

    @Autowired
    SysSpotNavigationService sysSpotNavigationService;


    /**
     * 后台管理——添加首页导航
     *
     * @param
     * @return
     */
    @ApiOperation("添加景区导航")
    @PostMapping("addSysSpotNavigation")
    @ResponseBody
    public ReturnModel addSysSpotNavigation(@RequestPart("file") MultipartFile file, SysSpotNavigation sysSpotNavigation) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotNavigationService.addSysSpotNavigation(file,sysSpotNavigation);
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
     * 后台管理——修改首页导航
     *
     * @param
     * @return
     */
    @ApiOperation("修改景区首页导航")
    @PostMapping("editSysSpotNavigation")
    @ResponseBody
    public ReturnModel editSysSpotNavigation(@RequestPart("file") MultipartFile file, SysSpotNavigation sysSpotNavigation) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotNavigationService.editSysSpotNavigation(file,sysSpotNavigation);
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
     * 后台管理——删除商品类型
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除景区首页导航")
    @PostMapping("delSysSpotNavigation")
    @ResponseBody
    public ReturnModel delSysSpotNavigation(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotNavigationService.delSysSpotNavigation(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }


    @ApiOperation("查询景区首页导航列表")
    @GetMapping("getSysSpotNavigationList")
    @ResponseBody
    public PageDataResult getSysSpotNavigationList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysSpotNavigationService.getSysSpotNavigationList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getContent());

        return pageDataResult;
    }




}
