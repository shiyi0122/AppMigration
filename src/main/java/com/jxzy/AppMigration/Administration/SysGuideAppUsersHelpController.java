package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersHelpService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/27 13:16
 */
@Api(tags = "后台管理-使用说明相关接口")
@RestController
@RequestMapping("adminAppUsersHelp")
@CrossOrigin
public class SysGuideAppUsersHelpController {

    @Autowired
    SysGuideAppUsersHelpService sysGuideAppUsersHelpService;

    @ApiOperation("使用说明列表")
    @GetMapping("appUsersHelpList")
    @ResponseBody
    public PageDataResult appUsersHelpList(PageDTO pageDTO){
        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getTitle())){
            search.put("title",pageDTO.getTitle());
        }
        if (!StringUtils.isEmpty(pageDTO.getContent())){
            search.put("content",pageDTO.getContent());
        }
        pageDataResult = sysGuideAppUsersHelpService.getUsersHelpBySearch(search,pageDTO.getPageNum(),pageDTO.getPageSize());
        return pageDataResult;
    }

    @ApiOperation("添加使用规则")
    @PostMapping
    @ResponseBody
    public ReturnModel addAppUsersHelp(@RequestBody SysGuideAppUsersHelp sysGuideAppUsersHelp){

        ReturnModel returnModel = new ReturnModel();

        int i = sysGuideAppUsersHelpService.addAppUsersHelp(sysGuideAppUsersHelp);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }
        return returnModel;
    }


    @ApiOperation("修改使用规则")
    @PostMapping("editAppUsersHelp")
    @ResponseBody
    public ReturnModel editAppUsersHelp(@RequestBody SysGuideAppUsersHelp sysGuideAppUsersHelp){

        ReturnModel returnModel = new ReturnModel();

        int i = sysGuideAppUsersHelpService.editAppUsersHelp(sysGuideAppUsersHelp);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        }
        return returnModel;
    }

    @ApiOperation("删除使用规则")
    @GetMapping("delAppUsersHelp")
    @ResponseBody
    public ReturnModel delAppUsersHelp(SearchDTO searchDTO){
        ReturnModel returnModel = new ReturnModel();
        if (StringUtils.isEmpty(searchDTO.getId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("使用规则id为空，无法查询！");
        }
        int i = sysGuideAppUsersHelpService.delAppUsersHelp(searchDTO.getId());
        if (i>0){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        }else{
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }



}
