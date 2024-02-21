package com.jxzy.AppMigration.NavigationApp.util.wxpay;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


@Slf4j
public class AuthUtil {

    public static final String APPID = "wx3a2cc6945c0b11e8";
    public static final String APPSECRET = "c1f8e7105fe8432b61556189272c9f61";

    public static JSONObject getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID + "&secret=" + AuthUtil.APPSECRET + "&code=" + code
                + "&grant_type=authorization_code";
        System.out.println("url = " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String body = responseEntity.getBody();
        // 返回结果转换为json对象
        JSONObject jObject = JSONObject.parseObject(body);
        return jObject;
    }

    public static JSONObject getUserInfo(String accessToken, String openid) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String body = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        // 返回结果转换为json对象
        JSONObject jObject = JSONObject.parseObject(body);
        return jObject;
    }

}
