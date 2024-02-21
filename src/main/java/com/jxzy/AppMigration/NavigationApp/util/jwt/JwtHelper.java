package com.jxzy.AppMigration.NavigationApp.util.jwt;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author vic
 *
 */
@Slf4j
@SuppressWarnings("restriction")
public class JwtHelper {
    // 签名秘钥
    public static final String BASE64SECRET = "ZW]4l5JH[m6Lm)LaQEjpb!4E0lRaG(";

    // 超时毫秒数（默认30分钟）
    public static final int EXPIRESSECOND = 1800000;

    // 用于JWT加密的密匙
    public static final String DATAKEY = "u^3y6SPER41jm*fn";

    /**
     * 生成JWT字符串 格式：A.B.C A-header头信息 B-payload 有效负荷 C-signature 签名信息
     * 是将header和payload进行加密生成的
     *
     * @param userId     用户编号
     * @param userName   用户名
     * @param identities 客户端信息（变长参数），目前包含浏览器信息，用于客户端拦截器校验，防止跨域非法访问
     * @return
     */
    public static String generateJWT(String userId, String userName, String... identities) {
        // 签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        // 将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BASE64SECRET);
        // 使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        // 添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<String, Object>();
        // Header { "alg": "HS256", "typ": "JWT" }
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");
        JwtBuilder builder = Jwts.builder().setHeader(headMap)
                // Payload { "userId": "1234567890", "userName": "vic", }
                // 加密后的客户编号
                .claim("userId", AESSecretUtil.encryptToStr(userId, DATAKEY))
                // 客户名称
                .claim("userName", userName)
                // 客户端浏览器信息
                .claim("userAgent", identities[0])
                // Signature
                .signWith(signatureAlgorithm, signingKey);
        // 添加Token过期时间
        if (EXPIRESSECOND >= 0) {
            long expMillis = nowTimeMillis + EXPIRESSECOND;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate).setNotBefore(now);
        }
        return builder.compact();
    }

    /**
     * 解析JWT 返回Claims对象
     *
     * @param jsonWebToken
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        Claims claims = null;
        try {
            if (StringUtils.isNotEmpty(jsonWebToken)) {
                // 解析jwt
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(BASE64SECRET))
                        .parseClaimsJws(jsonWebToken).getBody();
            } else {
                log.warn("[JWTHelper]-json web token 为空");
            }
        } catch (Exception e) {
            log.error("[JWTHelper]-JWT解析异常：可能因为token已经超时或非法token");
        }
        return claims;
    }

    /**
     * 校验JWT是否有效 返回json字符串的demo:
     * {"freshToken":"A.B.C","userName":"vic","userId":"123", "userAgent":"xxxx"}
     * freshToken-刷新后的jwt userName-客户名称 userId-客户编号 userAgent-客户端浏览器信息
     *
     * @param jsonWebToken
     * @return
     */
    public static String validateLogin(String jsonWebToken) {
        Map<String, Object> retMap = null;
        Claims claims = parseJWT(jsonWebToken);
        if (claims != null) {
            // 解密客户编号
            String decryptUserId = AESSecretUtil.decryptToStr((String) claims.get("userId"), DATAKEY);
            retMap = new HashMap<String, Object>();
            // 加密后的客户编号
            retMap.put("userId", decryptUserId);
            // 客户名称
            retMap.put("userName", claims.get("userName"));
            // 客户端浏览器信息
            retMap.put("userAgent", claims.get("userAgent"));
            // 刷新JWT
            retMap.put("freshToken", generateJWT(decryptUserId, (String) claims.get("userName"),
                    (String) claims.get("userAgent"), (String) claims.get("domainName")));
        } else {
            log.warn("[JWTHelper]-JWT解析出claims为空");
        }
        return retMap != null ? JSONObject.toJSONString(retMap) : null;
    }
}

