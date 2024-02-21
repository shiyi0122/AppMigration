package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysAppSubversionService;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUsType;
import com.jxzy.AppMigration.NavigationApp.entity.SysAppSubversion;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/4/26 9:32
 */
@Api(tags = "后台管理-app类型")
@RestController
@RequestMapping("adminSysAppSubversion")
@CrossOrigin
public class AdminSysAppSubversionController {

    @Autowired
    SysAppSubversionService sysAppSubversionService;

    @ApiOperation("app类型新增")
    @PostMapping("/addSysAppSubversion")
    @ResponseBody
    public ReturnModel addSysAppSubversion(@RequestBody SysAppSubversion sysAppSubversions) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysAppSubversionService.addSysAppSubversion(sysAppSubversions);
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


    @ApiOperation("app类型修改")
    @PostMapping("/editSysAppSubversion")
    @ResponseBody
    public ReturnModel editSysAppSubversion(@RequestBody SysAppSubversion sysAppSubversions) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysAppSubversionService.editSysAppSubversion(sysAppSubversions);
        if ( i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添修改失败！");
            return returnModel;
        }
    }

    @ApiOperation("app类型删除")
    @GetMapping("/delSysAppSubversion")
    @ResponseBody
    public ReturnModel delSysAppSubversion(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysAppSubversionService.delSysAppSubversion(baseDataDTO.getId());

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
    @ApiOperation("app类型列表查询")
    @GetMapping("/getSysAppSubversionList")
    @ResponseBody
    public PageDataResult getSysAppSubversionList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysAppSubversionService.getSysAppSubversionList(pageDTO.getPageNum(),pageDTO.getPageSize());

        return pageDataResult;
    }


    @ApiOperation("app类型下拉选")
    @GetMapping("/sysAppSubversionDropDown")
    @ResponseBody
    public ReturnModel sysAppSubversionDropDown() {
        ReturnModel returnModel = new ReturnModel();

        List<SysAppSubversion>  list = sysAppSubversionService.sysAppSubversionDropDown();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;

    }


}
