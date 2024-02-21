package com.jxzy.AppMigration.NavigationApp.util.wxpay;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WxConfig implements InitializingBean {
    @Value("${wxpay.appid}")
    private String appId;

    @Value("${wxpay.appsecret}")
    private String appSecret;

    @Value("$(wxpay.redirectUrl}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;


    @Override
    public void afterPropertiesSet(){
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
