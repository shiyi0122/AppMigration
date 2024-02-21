package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotAdmissionFeeService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastAdmissionFeeService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastAdmissionFee;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/6 9:56
 */
@Api(tags = "后台管理-门票管理")
@RestController
@RequestMapping("adminSysScenicSpotAdmissionFee")
@CrossOrigin
public class AdminSysScenicSpotAdmissionFeeController {

    @Autowired
    SysScenicSpotAdmissionFeeService sysScenicSpotAdmissionFeeService;

    @Autowired
    SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    SysScenicSpotBroadcastAdmissionFeeService sysScenicSpotBroadcastAdmissionFeeService;


    @ApiOperation("门票列表")
    @GetMapping("spotAdmissionFeeList")
    @ResponseBody
    public PageDataResult spotAdmissionFeeList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("spotId",pageDTO.getSpotId());
        search.put("broadcastId",pageDTO.getBroadcastId());
        if ("1".equals(pageDTO.getType())){
            pageDataResult = sysScenicSpotAdmissionFeeService.spotAdmissionFeeList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        }else{
            pageDataResult = sysScenicSpotBroadcastAdmissionFeeService.spotBroadcastAdmissionFeeList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        }
        return pageDataResult;
    }


    @ApiOperation("添加景区门票")
    @PostMapping("addSpotAdmissionFee")
    @ResponseBody
    public ReturnModel addSpotAdmissionFee(@RequestBody SysScenicSpotAdmissionFee sysScenicSpotAdmissionFee) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotAdmissionFeeService.addSpotAdmissionFee(sysScenicSpotAdmissionFee);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加景区门票成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加景区门票失败！");
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

    @ApiOperation("修改景区门票")
    @PostMapping("editSpotAdmissionFee")
    @ResponseBody
    public ReturnModel editSpotAdmissionFee(@RequestBody SysScenicSpotAdmissionFee sysScenicSpotAdmissionFee) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotAdmissionFeeService.editSpotAdmissionFee(sysScenicSpotAdmissionFee);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改景区门票成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改景区门票失败！");
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


    @ApiOperation("删除景区门票")
    @GetMapping("delSpotAdmissionFee")
    @ResponseBody
    public ReturnModel delSpotAdmissionFee(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        int i = 0;
        if ("1".equals(baseDataDTO.getType())){//景区门票
            i  = sysScenicSpotAdmissionFeeService.delSpotAdmissionFee(baseDataDTO.getId());

        }else{//景点门票
            i = sysScenicSpotBroadcastAdmissionFeeService.delBroadcastAdmissionFee(baseDataDTO.getId());
        }

        if ( i > 0){
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

    @ApiOperation("添加景点门票")
    @PostMapping("addBroadcastAdmissionFee")
    @ResponseBody
    public ReturnModel addBroadcastAdmissionFee(@RequestBody SysScenicSpotBroadcastAdmissionFee sysScenicSpotBroadcastAdmissionFee) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotBroadcastAdmissionFeeService.addBroadcastAdmissionFee(sysScenicSpotBroadcastAdmissionFee);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加景点门票成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加景点门票失败！");
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

    @ApiOperation("修改景点门票")
    @PostMapping("editBroadcastAdmissionFee")
    @ResponseBody
    public ReturnModel editBroadcastAdmissionFee(@RequestBody SysScenicSpotBroadcastAdmissionFee sysScenicSpotBroadcastAdmissionFee) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotBroadcastAdmissionFeeService.editBroadcastAdmissionFee(sysScenicSpotBroadcastAdmissionFee);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改景点门票成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改景点门票失败！");
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


    @ApiOperation("根据颈景区id，获取景点下拉选")
    @GetMapping("getSpotIdByBroadcastDropDown")
    @ResponseBody
    public ReturnModel getSpotIdByBroadcastDropDown(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(baseDataDTO.getSpotId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区id为空！");
        }
        List<SysScenicSpotBroadcast> list = sysScenicSpotBroadcastService.getSpotIdByBroadcastDropDown(baseDataDTO.getSpotId());

        if ( list.size() > 0){
            returnModel.setData(list);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("查询成功！");
        }else{
            returnModel.setData(list);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("查询失败");
        }
        return returnModel;
    }



}
