package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicRankingListService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicRankingList;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/8/3 18:26
 */
@Api(tags = "榜单种类")
@RestController
@RequestMapping("rankingList")
public class SysScenicRankingListController {

    @Autowired
    SysScenicRankingListService sysScenicRankingListService;

    @ApiOperation("获取榜单标签")
    @GetMapping("getRankingList")
    public ReturnModel getRankingList(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();

        List<SysScenicRankingList> list = sysScenicRankingListService.getRankingList(searchDTO);

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功!");

        return returnModel;
    }

    @ApiOperation("添加榜单标签")
    @PostMapping("addRankingList")
    public ReturnModel addRankingList(@RequestPart("file") MultipartFile file, SysScenicRankingList sysScenicRankingList){

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicRankingListService.addRankingList(file, sysScenicRankingList);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功!");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败!");
        }
        return returnModel;
    }








}
