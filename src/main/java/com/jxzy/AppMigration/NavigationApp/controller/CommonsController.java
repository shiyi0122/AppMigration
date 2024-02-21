package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicDistrictRankingService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotHeatService;
import com.jxzy.AppMigration.NavigationApp.util.JWTUtils;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhang
 * @Date 2022/7/21 14:54
 */

@Api(tags = "游客token")
@RestController
@RequestMapping("/api")
public class CommonsController extends PublicUtil {

    @Autowired
    private SysScenicSpotHeatService sysScenicSpotHeatService;
    @Autowired
    private SysScenicDistrictRankingService sysScenicDistrictRankingService;
    /**
     * 访客token
     *
     * @return token
     */
    @GetMapping("/touristsGetToken")
    public ReturnModel touristsGetToken() {
        ReturnModel returnModel = new ReturnModel();


        String token = JWTUtils.sign("guest", 0L, System.currentTimeMillis() + "jxzy_yxb");

        returnModel.setData(token);
        return returnModel;
    }


    @Scheduled(cron="0 1 0 * * ?") //凌晨12.01执行
    public void statusCheck() {
        logger.info("凌晨12.01执行");
        int a = sysScenicSpotHeatService.updateHeat();
        int b =sysScenicDistrictRankingService.updateRanking();
        //statusTask.healthCheck();
        logger.info("凌晨12.01执行");
    }



}
