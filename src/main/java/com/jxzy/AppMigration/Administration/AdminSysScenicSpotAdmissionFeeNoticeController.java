package com.jxzy.AppMigration.Administration;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotAdmissionFeeNoticeService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFeeNotice;
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
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/8 10:10
 */
@Api(tags = "后台管理-购票须知管理")
@RestController
@RequestMapping("adminSysScenicSpotAdmissionFeeNotice")
@CrossOrigin
public class AdminSysScenicSpotAdmissionFeeNoticeController {

    @Autowired
    SysScenicSpotAdmissionFeeNoticeService sysScenicSpotAdmissionFeeNoticeService;

    @ApiOperation("添加购票须知")
    @PostMapping("addSpotAdmissionFeeNotice")
    @ResponseBody
    public ReturnModel addSpotAdmissionFeeNotice(@RequestBody SysScenicSpotAdmissionFeeNotice sysScenicSpotAdmissionFeeNotice) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotAdmissionFeeNoticeService.addSpotAdmissionFeeNotice(sysScenicSpotAdmissionFeeNotice);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加购票须知成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加购票须知失败！");
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


    @ApiOperation("修改购票须知")
    @PostMapping("editSpotAdmissionFeeNotice")
    @ResponseBody
    public ReturnModel editSpotAdmissionFeeNotice(@RequestBody SysScenicSpotAdmissionFeeNotice sysScenicSpotAdmissionFeeNotice) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotAdmissionFeeNoticeService.editSpotAdmissionFeeNotice(sysScenicSpotAdmissionFeeNotice);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改购票须知成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改购票须知失败！");
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


    @ApiOperation("删除购票须知")
    @GetMapping("delSpotAdmissionFeeNotice")
    @ResponseBody
    public ReturnModel delSpotAdmissionFeeNotice(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotAdmissionFeeNoticeService.delSpotAdmissionFeeNotice(baseDataDTO.getId());
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除购票须知成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除购票须知失败！");
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


    @ApiOperation("购票须知列表")
    @GetMapping("getSpotAdmissionFeeNoticeList")
    @ResponseBody
    public PageDataResult getSpotAdmissionFeeNoticeList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<Object, Object> search = new HashMap<>();

        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }
        pageDataResult = sysScenicSpotAdmissionFeeNoticeService.getSpotAdmissionFeeNoticeList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }

}

