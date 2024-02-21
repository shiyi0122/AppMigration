package com.jxzy.AppMigration.NavigationApp.util.wxpay;

import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Description:微信配置
*/
@Data
//@Configuration
//@PropertySource(value="classpath:application.yml")
//@ConfigurationProperties(prefix = "wxpay") //与配置文件中开头相同
public class MyConfig extends WXPayConfig {

    private byte[] certData;

    private static MyConfig INSTANCE;

    private String apiSecretkey;

    private String parterSecrekey;

    private String appid;

    private String mchid;

//    public MyConfig() throws Exception {
//        //获取证书
//        File file = new ClassPathResource("apiclient_cert.p12").getFile();
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
//    }

    public static MyConfig getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (MyConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyConfig();
                }
            }
        }
        return INSTANCE;
    }
    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    protected IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    public String getAppID() {
        return this.appid;
    }

    public String getMchID() {
        return this.mchid;
    }

    public String getKey() {
        return this.apiSecretkey;
    }


  

}
