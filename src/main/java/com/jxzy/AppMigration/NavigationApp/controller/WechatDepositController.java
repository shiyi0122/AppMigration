package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.WechatDepositService;
import com.jxzy.AppMigration.NavigationApp.entity.APIReturnResult;
import com.jxzy.AppMigration.NavigationApp.entity.WechatDeposit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/8/7 11:20
 */


@Api(tags = "游娱go支付机器人押金相关接口")
@RestController
@CrossOrigin
@RequestMapping("wechatDeposit")
public class WechatDepositController {

    @Autowired
    WechatDepositService wechatDepositService;


//    @ApiOperation("生成押金订单")
//    @GetMapping("addWechatDeposit")
//    @ResponseBody
//    public APIReturnResult addWechatDeposit( String userId, String scenicSpotId){
//
//        Map<String, Object> map  = wechatDepositService.addWechatDeposit(userId,scenicSpotId);
//        Map<String,Object> dataMap = new HashMap<>();
//        dataMap.put("data",map);
//        return APIReturnResult.ok("获取成功",dataMap);
//
//    }

}
