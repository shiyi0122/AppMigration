package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.BusinessOrderUserInformationService;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrder;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrderUserInformation;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
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
 * @Date 2023/5/5 14:35
 */
@Api(tags = "游娱go预约门票相关接口")
@RestController
@RequestMapping("businessBookingTickets")
public class BusinessOrderUserInformationController {

    @Autowired
    BusinessOrderUserInformationService businessOrderUserInformationService;

    @ApiOperation("预约门票")
    @PostMapping("addBusinessBookingTickets")
    @ResponseBody
    public ReturnModel addBusinessBookingTickets(@RequestBody BusinessOrderUserInformation businessOrderUserInformation) {

        ReturnModel returnModel = new ReturnModel();
        int i = businessOrderUserInformationService.addBusinessBookingTickets(businessOrderUserInformation);
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

    @ApiOperation("我的预约门票列表")
    @GetMapping("getBusinessBookingTicketsList")
    @ResponseBody
    public PageDataResult getBusinessBookingTicketsList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (StringUtils.isEmpty(pageDTO.getUid())){
           pageDataResult.setCode(400);
           return pageDataResult;
        }
        search.put("uid",pageDTO.getUid());
        if ("0".equals(pageDTO.getType())){
            search.put("type",null);
        }else{
            search.put("type",pageDTO.getType());
        }


        pageDataResult = businessOrderUserInformationService.getBusinessBookingTicketsList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }

    @ApiOperation("门票id获取门票详情")
    @GetMapping("getBusinessBookingTicketsDetails")
    @ResponseBody
    public ReturnModel getBusinessBookingTicketsDetails(SearchDTO searchDTO) {

        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData(0);
            returnModel.setMsg("门票id为空！");
        }
        search.put("id",searchDTO.getId());//门票id

        BusinessOrderUserInformation businessOrderUserInformation = businessOrderUserInformationService.getBusinessBookingTicketsDetails(search);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setData(businessOrderUserInformation);
        returnModel.setMsg("查询成功");

        return returnModel;
    }


    @ApiOperation("取消预约")
    @GetMapping("cancellationReservation")
    @ResponseBody
    public ReturnModel cancellationReservation(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i = businessOrderUserInformationService.cancellationReservation(baseDataDTO.getId().toString());
        if (i > 0) {
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setData(i);
            returnModel.setMsg("取消成功");
        } else {
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData(i);
            returnModel.setMsg("取消失败");
        }
        return returnModel;
    }


    @ApiOperation("测试定时")
    @GetMapping("test")
    @ResponseBody
    public void testTime() {

        businessOrderUserInformationService.getAdmissionfeeTime();


    }

}
