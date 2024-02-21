package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysAboutUsTypeService;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUs;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUsType;
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
 * @Date 2023/1/14 17:19
 */

@Api(tags = "后台管理-服务项类型")
@RestController
@RequestMapping("adminSysAboutUsType")
@CrossOrigin
public class AdminSysAboutUsTypeController {

    @Autowired
    SysAboutUsTypeService sysAboutUsTypeService;

    @ApiOperation("服务类型新增")
    @PostMapping("/addSysAboutUsType")
    @ResponseBody
    public ReturnModel addSysAboutUsType(@RequestBody SysAboutUsType sysAboutUsType) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysAboutUsTypeService.addSysAboutUsType(sysAboutUsType);

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

    @ApiOperation("服务项类型修改")
    @PostMapping("/editSysAboutUsType")
    @ResponseBody
    public ReturnModel editSysAboutUsType(@RequestBody SysAboutUsType sysAboutUsType) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysAboutUsTypeService.editSysAboutUsType(sysAboutUsType);

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

    @ApiOperation("服务器类型删除")
    @GetMapping("/delSysAboutUsType")
    @ResponseBody
    public ReturnModel delSysAboutUsType(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysAboutUsTypeService.delSysAboutUsType(baseDataDTO.getId());

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

    @ApiOperation("服务项类型列表查询")
    @GetMapping("/getSysAboutUsTypeList")
    @ResponseBody
    public PageDataResult getSysAboutUsTypeList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysAboutUsTypeService.getSysAboutUsTypeList(pageDTO.getPageNum(),pageDTO.getPageSize());

        return pageDataResult;
    }

    @ApiOperation("服务项类型下拉选")
    @GetMapping("/sysAboutUsTypeDropDown")
    @ResponseBody
    public ReturnModel sysAboutUsTypeDropDown(PageDTO pageDTO) {
        ReturnModel returnModel = new ReturnModel();

        List<SysAboutUsType> list = sysAboutUsTypeService.sysAboutUsTypeDropDown();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("添加成功");
        return returnModel;

    }


}
