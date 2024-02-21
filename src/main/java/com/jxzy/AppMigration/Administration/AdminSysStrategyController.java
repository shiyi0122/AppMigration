package com.jxzy.AppMigration.Administration;

import com.alibaba.fastjson.JSON;
import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyService;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategy;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyContent;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 18:44
 * 游记，攻略，广场，相关接口
 */

@Api(tags = "后台管理-游记，攻略，广场，相关接口")
@RestController
@RequestMapping("adminSysStrategy")
@CrossOrigin
public class AdminSysStrategyController {

    @Autowired
    SysStrategyService sysStrategyService;


    @ApiOperation("游记,攻略，广场列表查询")
    @GetMapping("getSysStrategyList")
    @ResponseBody
    public PageDataResult getSysStrategyList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("spotId",pageDTO.getSpotId());
        search.put("content",pageDTO.getTitle());
        search.put("type",pageDTO.getType());
        search.put("startTime",pageDTO.getStartTime());
        pageDataResult = sysStrategyService.getSysStrategyList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;
    }


    @ApiOperation("游记,攻略,广场___审核")
    @GetMapping("editSysStrategyToExamine")
    @ResponseBody
    public ReturnModel editSysStrategyToExamine(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel  = new ReturnModel();

        Map<String, Object> search = new HashMap<>();

        int i  = sysStrategyService.editSysStrategyToExamine(baseDataDTO.getId(),baseDataDTO.getState());

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("审核成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("审核失败");
        }
        return returnModel;
    }

    @ApiOperation("添加游记,攻略，广场")
    @PostMapping("addSysStrategy")
    @ResponseBody
    public ReturnModel addSysStrategy(@RequestBody SysStrategy sysStrategy) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysStrategyService.addSysStrategyNoFile(sysStrategy);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("审核成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("审核失败");
        }
        return returnModel;

    }

    @ApiOperation("修改游记,攻略，广场")
    @PostMapping("editSysStrategy")
    @ResponseBody
    public ReturnModel editSysStrategy(@RequestBody SysStrategy sysStrategy) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysStrategyService.editSysStrategyNoFile(sysStrategy);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("审核成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("审核失败");
        }

        return returnModel;

    }

    @ApiOperation("删除游记,攻略，广场")
    @GetMapping("delSysStrategy")
    @ResponseBody
    public ReturnModel delSysStrategy(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysStrategyService.delSysStrategy(id);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }

        return returnModel;

    }


    @ApiOperation("添加游记,攻略，广场内容")
    @PostMapping("addSysStrategyContent")
    @ResponseBody
    public ReturnModel addSysStrategyContent(@RequestBody SysStrategyContent sysStrategyContent) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysStrategyService.addSysStrategyContent(sysStrategyContent);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }

        return returnModel;

    }


    @ApiOperation("编辑游记,攻略，广场内容")
    @PostMapping("editSysStrategyContent")
    @ResponseBody
    public ReturnModel editSysStrategyContent(@RequestBody SysStrategyContent sysStrategyContent) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysStrategyService.editSysStrategyContent(sysStrategyContent);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }

        return returnModel;

    }


    @ApiOperation("删除游记,攻略，广场内容")
    @GetMapping("delSysStrategyContent")
    @ResponseBody
    public ReturnModel delSysStrategyContent(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i =   sysStrategyService.delSysStrategyContent(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }
        return returnModel;
    }

    @ApiOperation("删除游记,攻略，广场内容图片")
    @GetMapping("delSysStrategyContentPic")
    @ResponseBody
    public ReturnModel delSysStrategyContentPic(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i =   sysStrategyService.delSysStrategyContentPic(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }
        return returnModel;
    }


    @ApiOperation("游记,攻略，广场内容列表查询")
    @GetMapping("getSysStrategyContentList")
    @ResponseBody
    public PageDataResult getSysStrategyContentList(BaseDataDTO baseDataDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("strategyId",baseDataDTO.getId());
        search.put("content",baseDataDTO.getContent());
        search.put("type",baseDataDTO.getType());
//        pageDataResult = sysStrategyService.getSysStrategyContentList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        pageDataResult = sysStrategyService.getSysStrategyContentList(null,null,search);


        return pageDataResult;
    }



    @ApiOperation("游记,攻略，广场内容列表修改")
    @PostMapping("updateSysStrategyContentList")
    @ResponseBody
    public ReturnModel updateSysStrategyContentList(@RequestBody String data) {

        ReturnModel returnModel = new ReturnModel();
        List<SysStrategyContent> sysStrategyContents = JSON.parseArray(data, SysStrategyContent.class);
        int i = 0;
         i =  sysStrategyService.updateSysStrategyContentList(sysStrategyContents);

        if (i > 0){
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setData(i);
            returnModel.setMsg("修改成功");
        }else{
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData(i);
            returnModel.setMsg("修改失败");
        }
        return returnModel;
    }

}
