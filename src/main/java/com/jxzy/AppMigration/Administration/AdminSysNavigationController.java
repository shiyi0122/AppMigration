package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysNavigationService;
import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsCommodityType;
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
 * @Date 2023/1/9 15:38
 * 首页导航
 */
@Api(tags = "后台管理-首页导航相关接口")
@RestController
@RequestMapping("adminSysNavigation")
@CrossOrigin
public class AdminSysNavigationController {

    @Autowired
    SysNavigationService sysNavigationService;



    /**
     * 后台管理——添加首页导航
     *
     * @param
     * @return
     */
    @ApiOperation("添加首页导航")
    @PostMapping("addSysNavigation")
    @ResponseBody
    public ReturnModel addSysNavigation(@RequestPart("file") MultipartFile file, SysNavigation sysNavigation) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysNavigationService.addSysNavigation(file,sysNavigation);
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
    @ApiOperation("修改首页导航")
    @PostMapping("editSysNavigation")
    @ResponseBody
    public ReturnModel editSysNavigation(@RequestPart("file") MultipartFile file, SysNavigation sysNavigation) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysNavigationService.editSysNavigation(file,sysNavigation);
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
    @ApiOperation("删除首页导航")
    @PostMapping("delSysNavigation")
    @ResponseBody
    public ReturnModel delSysNavigation(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysNavigationService.delSysNavigation(id);
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


    @ApiOperation("查询查询首页导航列表")
    @GetMapping("getSysNavigationList")
    @ResponseBody
    public PageDataResult getSysNavigationList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysNavigationService.getSysNavigationList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getContent());

        return pageDataResult;
    }


}
