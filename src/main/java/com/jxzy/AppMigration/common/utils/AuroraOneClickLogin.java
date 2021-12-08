package com.jxzy.AppMigration.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuroraOneClickLogin {
    // 私钥
    public static final String prikey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMeB5rEK4bkDshRO/w0xC+1cAHazxP0+zu5dlM37M6fac3xwgG1cENh0GVeNCPT5pVQlhlQl9daCs/BQVk3yXuDJqc2I7fbKusLO1CwrSUAQ1zxJMwchiqHkUqT8awQoxaxVZd0SCGLv+0cSWma3/SvJZaMWlYDzAxvf7ieyOioPAgMBAAECgYEAreJqTqRNWBQAgfLYGYdF/frR7KOCbbbwZJDrwRvQSdv8C9mCFc7IX9/Qtp4V0G5FyrFqAq2TNwN0EV/z2LwGNBlWGF6tlDf97hvI5SJwvvVIV3+mJlg6STPakEGyit21ZOFoEuKeVAfDBp468ZRTBJVY5FboAGd2cTGmjaEFfwECQQD7phAhg/BgE3Hd+3mKgXTtewU+kizsLCaEx2Eb70nxHtbGYsus34sPNgA1sbpiWh9LXii7N89KGiElo8Xmi17tAkEAyvUILLrT6KLXyANJ4e/xsLytd+xDVzZ2Sk0lofDX1Pq4xeH9svVBQzHsN29g+NNE0V2zOQivud/VZyU9eiLRawJBAORRbg9KixxQaB50nHirLjCNTlvP+kwpXqVX1Gao+9h2F32Vg6AMjjm10AjngOjdRcl229PJNhzQikSBS/v5dZ0CQQCv3OscdzcwGgsIUXEvyg6Pqq5Vf/BKHfMaJwc478zVy/tX3J9M9p96bkPVNTm94rN7B8pI3XIHiiEMo4RF1O3JAkBdIItrLyT94IohUzzwPLRSeFX+bzomQxA4MRL5B4rsU6mF0FBxZUyxHqBeyb+VbX7kOlj/rp4SFQso/QGqSdHJ";
    public static String decrypt(String loginToken) throws Exception {
        String host = "https://api.verification.jpush.cn/v1/web/loginTokenVerify";
        String appKey = "cfd9efbf92457fa0132eaccc";
        String Secret = "5ef42c68a76cffa7b37147c8";
        Map<String, Object> map = new HashMap<>();
        map.put("loginToken", loginToken);
        map.put("exID", null);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","Basic ${base64_auth_string}");
        headers.put("Content-Type", "application/json; charset=UTF-8");

        String result = HttpRequest.post(host).header("Authorization", "Basic " + Base64.getUrlEncoder().
                encodeToString((appKey + ":" + Secret).getBytes())).
        header("Content-Type","application/json; charset=UTF-8")
        .body(JSON.toJSONString(map)).execute().body();
        PublicUtil.logger.info("result:[{}]",result);
        JSONObject jsonObject = JSON.parseObject(result);
        PublicUtil.logger.info("获取phone:{}",JSON.toJSON(jsonObject));
        String cryptograph_phone = (String) jsonObject.get("phone");
        if(StrUtil.isBlank(cryptograph_phone)){
            throw new Exception("极光认证出错"+cryptograph_phone);
        }
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(prikey));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] b = Base64.getDecoder().decode(cryptograph_phone);
        return new String(cipher.doFinal(b));
    }
}
