package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppAgreementService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppAgreement;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNews;
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
 * @Date 2023/1/13 14:38
 * 后台管理，协议管理
 */
@Api(tags = "后台管理-后台管理，协议管理")
@RestController
@RequestMapping("adminSysGuideAppAgreement")
@CrossOrigin
public class AdminSysGuideAppAgreementController {

    @Autowired
    SysGuideAppAgreementService sysGuideAppAgreementService;


    @ApiOperation("协议新增")
    @PostMapping("/addSysGuideAppAgreement")
    @ResponseBody
    public ReturnModel addSysGuideAppAgreement(@RequestBody SysGuideAppAgreement sysGuideAppAgreement) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysGuideAppAgreementService.addSysGuideAppAgreement(sysGuideAppAgreement);

        if ( i>0){
            returnModel.setData(Constant.STATE_SUCCESS);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
            return returnModel;
        }else{
            returnModel.setData(Constant.STATE_FAILURE);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
            return returnModel;
        }
    }

    @ApiOperation("协议修改")
    @PostMapping("/editSysGuideAppAgreement")
    @ResponseBody
    public ReturnModel editSysGuideAppAgreement(@RequestBody SysGuideAppAgreement sysGuideAppAgreement) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysGuideAppAgreementService.editSysGuideAppAgreement(sysGuideAppAgreement);

        if ( i>0){
            returnModel.setData(Constant.STATE_SUCCESS);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
            return returnModel;
        }else{
            returnModel.setData(Constant.STATE_FAILURE);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
            return returnModel;
        }
    }

    @ApiOperation("协议列表查询")
    @GetMapping("/getSysGuideAppAgreementList")
    @ResponseBody
    public PageDataResult getSysGuideAppAgreementList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysGuideAppAgreementService.getSysGuideAppAgreementList(pageDTO.getPageNum(),pageDTO.getPageSize());

        return pageDataResult;
    }




}
