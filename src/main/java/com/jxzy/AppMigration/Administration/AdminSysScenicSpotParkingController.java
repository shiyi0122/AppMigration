package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotParkingService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParking;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParkingWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/28 16:25
 */

@Api(tags = "后台管理-停放点相关接口")
@RestController
@RequestMapping("adminSysScenicSpotParking")
@CrossOrigin
public class AdminSysScenicSpotParkingController {

    @Autowired
    SysScenicSpotParkingService sysScenicSpotParkingService;


    @ApiOperation("停放点列表")
    @GetMapping("getSpotParkingList")
    @ResponseBody
    public PageDataResult  getSpotParkingList(PageDTO pageDTO){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("parkingName",pageDTO.getContent());
        search.put("spotName",pageDTO.getSpotName());
        search.put("spotId",pageDTO.getSpotId());
        search.put("cType",1);
        pageDataResult =  sysScenicSpotParkingService.selectBySearch(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;
    }

    @ApiOperation("添加停放点")
    @PostMapping("addSpotParking")
    @ResponseBody
    public ReturnModel addSpotParking(@RequestBody SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        ReturnModel returnModel = new ReturnModel();

//        sysScenicSpotParkingWithBLOBs.setCoordinateType("0");
        int i = sysScenicSpotParkingService.addSpotParking(sysScenicSpotParkingWithBLOBs);

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


    @ApiOperation("修改停放点")
    @PostMapping("editSpotParking")
    @ResponseBody
    public ReturnModel editSpotParking(@RequestBody SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotParkingService.editSpotParking(sysScenicSpotParkingWithBLOBs);
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



    @ApiOperation("删除停放点")
    @GetMapping("delSpotParking")
    @ResponseBody
    public ReturnModel delSpotParking(Long parkingId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotParkingService.delSpotParking(parkingId);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }

    @ApiOperation("修改停放点状态")
    @GetMapping("exitSpotParkingType")
    @ResponseBody
    public ReturnModel exitSpotParkingType(Long parkingId, String type) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotParkingService.exitSpotParkingType(parkingId,type);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("状态修改失败！");
        }
        return returnModel;
    }








    }
