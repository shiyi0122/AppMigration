package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.util.JWTUtils;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhang
 * @Date 2022/7/21 14:54
 */

@RestController
@RequestMapping("/api")
public class CommonsController {

    /**
     * 访客token
     *
     * @return token
     */
    @GetMapping("/app/tourists")
    public ReturnModel touristsGetToken() {
        ReturnModel returnModel = new ReturnModel();


        String token = JWTUtils.sign("guest", 0L, System.currentTimeMillis() + "jxzy_yxb");

        returnModel.setData(token);
        return returnModel;
    }


}
