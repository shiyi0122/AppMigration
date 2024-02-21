package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyCollectionService;
import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyCommentService;
import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyLikeService;
import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyService;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 18:39
 * 攻略，游记，广场，相关接口
 */

@Api(tags = "游娱go攻略，游记，广场，相关接口")
@CrossOrigin
@RestController
@RequestMapping("sysStrategy")
public class SysStrategyController {


    @Autowired
    SysStrategyService sysStrategyService;

    @Autowired
    SysStrategyLikeService sysStrategyLikeService;

    @Autowired
    SysStrategyCommentService sysStrategyCommentService;

    @Autowired
    SysStrategyCollectionService sysStrategyCollectionService;


    @ApiOperation("添加攻略,游记,广场")
    @PostMapping("addSysStrategy")
    @ResponseBody
    public ReturnModel addSysStrategy(@RequestBody SysStrategy sysStrategy) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysStrategyService.addAppSysStrategyNoFile(sysStrategy);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }

    @ApiOperation("添加攻略,游记,广场（新）")
    @PostMapping("addSysStrategyNew")
    @ResponseBody
    public ReturnModel addSysStrategyNew(@RequestBody SysStrategy sysStrategy) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysStrategyService.addAppSysStrategyNoFileNew(sysStrategy);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    @ApiOperation("查询游娱攻,游记，广场列表")
    @GetMapping("getSysStrategyAppList")
    @ResponseBody
    public PageDataResult getSysStrategyAppList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }
        if (!StringUtils.isEmpty(pageDTO.getContent())){
            search.put("content",pageDTO.getContent());
        }
//        if (!StringUtils.isEmpty(pageDTO.getLng())){
//            search.put("lng",pageDTO.getLng());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getLat())){
//            search.put("lat",pageDTO.getLat());
//        }
        if (!StringUtils.isEmpty(pageDTO.getSpotBindingName())){
            search.put("cityName",pageDTO.getSpotBindingName());
        }
        if (!StringUtils.isEmpty(pageDTO.getUid())){
            search.put("uid", pageDTO.getUid());
        }
        if (!StringUtils.isEmpty(pageDTO.getType())){
            search.put("type",pageDTO.getType());
        }

        if (!StringUtils.isEmpty(pageDTO.getCityType())){
            search.put("cityType",pageDTO.getCityType());
        }

        pageDataResult = sysStrategyService.getSysStrategyAppList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }

    @ApiOperation("查询游娱攻略，游记，广场详情")
    @GetMapping("getSysStrategyAppDetails")
    @ResponseBody
    public ReturnModel getSysStrategyAppDetails(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(baseDataDTO.getId())){
            search.put("id",baseDataDTO.getId());
        }
        if (!StringUtils.isEmpty(baseDataDTO.getUid())){
            search.put("uid",baseDataDTO.getUid());
        }
        search.put("type",baseDataDTO.getType());
//        if (!StringUtils.isEmpty(baseDataDTO.getContent())){
//            search.put("content",baseDataDTO.getContent());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getLng())){
//            search.put("lng",pageDTO.getLng());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getLat())){
//            search.put("lat",pageDTO.getLat());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getSpotBindingName())){
//            search.put("cityName",pageDTO.getSpotBindingName());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getUid())){
//            search.put("uid", pageDTO.getUid());
//        }

        SysStrategy sysStrategy =  sysStrategyService.getSysStrategyAppDetails(search);
        returnModel.setData(sysStrategy);
        returnModel.setMsg("查询成功");
        returnModel.setState(Constant.STATE_SUCCESS);
       return returnModel;
    }

    @ApiOperation("游娱攻略，游记，广场浏览次数加一")
    @GetMapping("addSysStrategyNumber")
    @ResponseBody
    public ReturnModel addSysStrategyNumber(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysStrategyService.addSysStrategyNumber(baseDataDTO.getId());
        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("添加成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("添加失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }


    @ApiOperation("收藏 攻略,游记,广场")
    @GetMapping("addSysStrategyCollection")
    @ResponseBody
    public ReturnModel addSysStrategyCollection(SysStrategyCollection strategyCollection) {

        ReturnModel returnModel = new ReturnModel();
        if ( StringUtils.isEmpty(strategyCollection.getUserId())){
            returnModel.setData(0);
            returnModel.setMsg("添加失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        int i = sysStrategyCollectionService.insert(strategyCollection);

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("添加成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("添加失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;

    }

    @ApiOperation("取消收藏__攻略,游记,广场")
    @GetMapping("delSysStrategyCollection")
    @ResponseBody
    public ReturnModel delSysStrategyCollection(SysStrategyCollection strategyCollection) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysStrategyCollectionService.delete(strategyCollection.getStrategyId(),strategyCollection.getUserId());

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("取消成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("取消失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;

    }

    @ApiOperation("喜欢 攻略,游记,广场")
    @GetMapping("addSysStrategyLike")
    @ResponseBody
    public ReturnModel addSysStrategyLike(SysStrategyLike strategyLike) {

        ReturnModel returnModel = new ReturnModel();
        if (StringUtils.isEmpty(strategyLike.getUserId())){
            returnModel.setData(0);
            returnModel.setMsg("添加失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        int i = sysStrategyLikeService.insert(strategyLike);

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("添加成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("添加失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;

    }


    @ApiOperation("取消喜欢__攻略,游记,广场")
    @GetMapping("delSysStrategyLike")
    @ResponseBody
    public ReturnModel delSysStrategyLike(SysStrategyLike strategyLike) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysStrategyLikeService.delete(strategyLike.getStrategyId(),strategyLike.getUserId());

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("取消成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("取消失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("评论——攻略,游记,广场")
    @GetMapping("addSysStrategyComment")
    @ResponseBody
    public ReturnModel addSysStrategyComment(SysStrategyComment strategyComment) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysStrategyCommentService.insert(strategyComment);

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("添加成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("添加失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;

    }

    @ApiOperation("删除评论——攻略,游记,广场")
    @GetMapping("delSysStrategyComment")
    @ResponseBody
    public ReturnModel delSysStrategyComment(SysStrategyComment strategyComment) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysStrategyCommentService.delete(strategyComment.getId(),strategyComment.getUserId());

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("删除成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("删除失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }


    @ApiOperation("删除攻略，游记，广场")
    @GetMapping("delAppSysStrategy")
    @ResponseBody
    public ReturnModel delAppSysStrategy(SysStrategy sysStrategy) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysStrategyService.delSysStrategy(sysStrategy.getId());

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("删除成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("删除失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }





}
