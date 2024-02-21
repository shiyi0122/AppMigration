package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.BusinessOrderService;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrder;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrderY;
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
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/24 17:03
 */
@Api(tags = "游娱go订单相关接口")
@RestController
@RequestMapping("businessOrder")
public class BusinessOrderController {

    @Autowired
    BusinessOrderService businessOrderService;

    @ApiOperation("添加订单")
    @PostMapping("addBusinessOrder")
    @ResponseBody
    public ReturnModel addBusinessOrder(BusinessOrderY businessOrderY) {

        ReturnModel returnModel = new ReturnModel();
        int i = businessOrderService.addBusinessOrderY(businessOrderY);
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


    @ApiOperation("根据id查询订单详情")
    @GetMapping("getBusinessOrderId")
    @ResponseBody
    public ReturnModel getBusinessOrderId(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        search.put("id",baseDataDTO.getId());
        BusinessOrderY businessOrderY  = businessOrderService.getBusinessOrderYId(search);

            returnModel.setData(businessOrderY);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");

        return returnModel;
    }

    @ApiOperation("根据用户id获取门票订单列表")
    @GetMapping("getBusinessOrderByUserIdList")
    @ResponseBody
    public PageDataResult getBusinessOrderByUserIdList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("uid",pageDTO.getUid());
        search.put("type",pageDTO.getType());
        pageDataResult = businessOrderService.getBusinessOrderYByUserIdList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);

        return pageDataResult;
    }


}
