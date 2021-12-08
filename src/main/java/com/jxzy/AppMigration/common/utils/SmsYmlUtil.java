package com.jxzy.AppMigration.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SmsYmlUtil {
    public static String TENCENT_APPID;
    public static String TENCENT_KEY;
    public static String TENCENT_TEMPLATE;
    public static String TENCENT_MINUTE;

    public static String getTencentAppid() {
        return TENCENT_APPID;
    }

    public static void setTencentAppid(String tencentAppid) {
        TENCENT_APPID = tencentAppid;
    }

    public static String getTencentKey() {
        return TENCENT_KEY;
    }

    public static void setTencentKey(String tencentKey) {
        TENCENT_KEY = tencentKey;
    }

    public static String getTencentTemplate() {
        return TENCENT_TEMPLATE;
    }

    public static void setTencentTemplate(String tencentTemplate) {
        TENCENT_TEMPLATE = tencentTemplate;
    }

    public static String getTencentMinute() {
        return TENCENT_MINUTE;
    }

    public static void setTencentMinute(String tencentMinute) {
        TENCENT_MINUTE = tencentMinute;
    }
}
