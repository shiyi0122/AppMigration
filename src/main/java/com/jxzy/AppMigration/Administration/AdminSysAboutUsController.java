package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysAboutUsService;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUs;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppAgreement;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhang
 * @Date 2023/1/13 15:03
 * 关于我们
 */
@Api(tags = "后台管理-关于我们")
@RestController
@RequestMapping("adminSysAboutUs")
@CrossOrigin
public class AdminSysAboutUsController {

    @Autowired
    SysAboutUsService  sysAboutUsService;


    @ApiOperation("关于我们新增以及修改")
    @PostMapping("/addSysAboutUs")
    @ResponseBody
    public ReturnModel addSysAboutUs(@RequestBody SysAboutUs sysAboutUs) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysAboutUsService.addSysAboutUs(sysAboutUs);

        if ( i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
            return returnModel;
        }
    }

    @ApiOperation("关于我们修改")
    @PostMapping("/editSysAboutUs")
    @ResponseBody
    public ReturnModel editSysAboutUs(@RequestBody SysAboutUs sysAboutUs) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysAboutUsService.editSysAboutUs(sysAboutUs);

        if ( i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
            return returnModel;
        }
    }

    @ApiOperation("关于我们列表查询")
    @GetMapping("/getSysAboutUsList")
    @ResponseBody
    public PageDataResult getSysAboutUsList(BaseDataDTO baseDataDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysAboutUsService.getSysAboutUsList(baseDataDTO.getType(),baseDataDTO.getAppSubversionId());

        return pageDataResult;
    }

    @ApiOperation("关于我们删除")
    @GetMapping("/delSysAboutUs")
    @ResponseBody
    public ReturnModel delSysAboutUs(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysAboutUsService.delSysAboutUs(baseDataDTO.getId());

        if ( i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
            return returnModel;
        }

    }
}
