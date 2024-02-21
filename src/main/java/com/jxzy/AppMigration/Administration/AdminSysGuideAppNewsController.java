package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppNewsService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNews;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/4 13:10
 */
@Api(tags = "后台管理-游小伴app消息管理相关接口")
@RestController
@RequestMapping("adminSysGuideAppNews")
@CrossOrigin
public class AdminSysGuideAppNewsController {

    @Autowired
    SysGuideAppNewsService sysGuideAppNewsService;

    @ApiOperation("app消息管理列表查询")
    @GetMapping("/getSysGuideAppNewsList")
    @ResponseBody
    public PageDataResult getSysGuideAppNewsList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("content",pageDTO.getContent());
        pageDataResult = sysGuideAppNewsService.getSysGuideAppNewsList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;

    }

    @ApiOperation("app消息管理-添加消息")
    @PostMapping("/addSysGuideAppNews")
    @ResponseBody
    public ReturnModel addSysGuideAppNews(@RequestBody SysGuideAppNews sysGuideAppNews) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysGuideAppNewsService.addSysGuideAppNews(sysGuideAppNews);

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

    @ApiOperation("app消息管理-编辑消息")
    @PostMapping("/editSysGuideAppNews")
    @ResponseBody
    public ReturnModel editSysGuideAppNews(@RequestBody SysGuideAppNews sysGuideAppNews) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGuideAppNewsService.editSysGuideAppNews(sysGuideAppNews);

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

    @ApiOperation("app消息管理-删除消息")
    @GetMapping("/delSysGuideAppNews")
    @ResponseBody
    public ReturnModel delSysGuideAppNews(Long guideId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGuideAppNewsService.delSysGuideAppNews(guideId);

        if ( i>0){
            returnModel.setData(Constant.STATE_SUCCESS);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
            return returnModel;
        }else{
            returnModel.setData(Constant.STATE_FAILURE);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
            return returnModel;
        }

    }




}
