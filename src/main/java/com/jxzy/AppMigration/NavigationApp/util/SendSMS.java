package com.jxzy.AppMigration.NavigationApp.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.jxzy.AppMigration.common.utils.CacheManagers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendSMS {

    private static int tencentAppid = 1400295952;
    private static String tencentKey = "f4eeb442e910c057d2579c90fa0a3f4d";//key
    private static int tencentTemplate = 500066;//模板
    private static int tencentMinute = 5;//有效期

    public static boolean sendPhoneCode(String key,String phone,String code) throws Exception{
        SmsSingleSender ssender = new SmsSingleSender(tencentAppid, tencentKey);
        String [] params = {code};
        SmsSingleSenderResult result = ssender.sendWithParam("86", phone,
                tencentTemplate,params , "九星智元", "", "");// 签名参数未提供或者为空时，会使用默认签名发送短信
        System.out.println(result);
        if(result!=null && result.result==0){
            CacheManagers.setData(key,code,tencentMinute * 60);//key-value-生命周期 秒数5分钟
            return true;
        }
        return false;
    }
}
