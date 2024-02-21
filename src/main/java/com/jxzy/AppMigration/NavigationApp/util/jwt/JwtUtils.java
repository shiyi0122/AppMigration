package com.jxzy.AppMigration.NavigationApp.util.jwt;


import com.jxzy.AppMigration.NavigationApp.entity.JWTUser;
import com.jxzy.AppMigration.NavigationApp.util.Des;
import com.jxzy.AppMigration.NavigationApp.util.JsonUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils {
    /**
     * 有效期 7天
     */
    private static long expiration  = 604800000L;
    /***
     * 长期有效（暂定1年有效）用于小程序
     */
    private static long longExpiration  = 31536000000L;
    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey(String salt) {

        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(salt);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    public static String createJWT(String uid, String accessToken, int ttlMillis){
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", uid);
        claims.put("accessToken", accessToken);
        SecretKey key = generalKey(JwtConstant.JWT_SECRET);
        String subject =  JsonUtils.toJSONString(claims);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(uid)                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(new Date())           // iat: jwt的签发时间
                .setIssuer(uid)          // issuer：jwt签发人
                .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥

        if (ttlMillis == -1) {//默认七天
            Date exp = generateExpirationDate();
            builder.setExpiration(exp);
        }else {
            Date exp = longExpirationDate();
            builder.setExpiration(exp);
        }
        return builder.compact();
    }
    /**
     * 创建jwt
     * @param id
     * @param jwtUser
     * @param ttlMillis
     * @return
     */
    public static String createJWT(Long id, JWTUser jwtUser, String salt, int ttlMillis) {
        String subject = JsonUtils.toJSONString(jwtUser);
        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", jwtUser.getId());
        claims.put("userName", jwtUser.getUserName());
        claims.put("phone", jwtUser.getPhone());
//        claims.put("email", jwtUser.getEmail());
//        claims.put("nickname", jwtUser.getNickname());
        String issuer =  jwtUser.getCompanyName();

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey(salt);
        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id.toString())                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           // iat: jwt的签发时间
                .setIssuer(issuer)          // issuer：jwt签发人
                .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥

        if (ttlMillis == -1) {//默认七天
            Date exp = generateExpirationDate();
            builder.setExpiration(exp);
        }else{
            Date exp = longExpirationDate();
            builder.setExpiration(exp);
        }
        return builder.compact();
    }




    /**
     * 生成token的过期时间
     */
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration);
    }
    private static Date longExpirationDate() {
        return new Date(System.currentTimeMillis() + longExpiration);
    }





    public static JWTUser getUserByToken(String accessToken, String salt) throws Exception {
        String str = JwtUtils.getUserStrByJWT(accessToken,salt);
        JWTUser user = null ;
        if(StringUtils.isNotEmpty(str)){
            user = JsonUtils.toBean(str,JWTUser.class);
        }
        return user;
    }
    public static Map<String, Object> getAccessTokenByToken(String token) throws Exception {
        String str = JwtUtils.getUserStrByJWT(token, JwtConstant.JWT_SECRET);
        Map<String, Object> data = null;
        if(StringUtils.isNotEmpty(str)){
            data = JsonUtils.parseJSON2Map(str);

        }
        return data;
    }


    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static JwtResult parseJWT(String jwt, String salt) throws Exception {
        JwtResult result = new JwtResult();
        try {
        	SecretKey key = generalKey(salt);  //签名秘钥，和生成的签名的秘钥一模一样
        	Claims claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)                 //设置签名的秘钥
                    .parseClaimsJws(jwt).getBody();     //设置需要解析的jwt
            result.setSuccess(true);
            result.setClaims(claims);
            result.setCode("0");

        } catch (ExpiredJwtException e) {
            result.setSuccess(true);
            result.setClaims(null);
            result.setCode("1");
            log.info("jwt超时啦");
        	System.out.println("超时啦");
        } catch (SignatureException e) {
            result.setSuccess(true);
            result.setClaims(null);
            result.setCode("2");
            log.info("jwt解析报错啦");
        } catch (Exception e) {
            result.setSuccess(true);
            result.setClaims(null);
            result.setCode("2");
            log.info("jwt解析报错啦");
        }
        return result;
    }


    /**
     * 从token中获取过期时间
     */
    public static Date getExpiredDateFromToken(String token, String salt) {
        Claims claims = getClaimsFromToken(token,salt);
        if ( claims != null && claims.getExpiration() != null){
            return claims.getExpiration();
        }else{
            return new Date();
        }
    }


    /**
     * 从token中获取JWT中的负载
     */
    private static Claims getClaimsFromToken(String token, String salt) {
        Claims claims = null;
        try {
            SecretKey key = generalKey(salt);  //签名秘钥，和生成的签名的秘钥一模一样
            claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)                 //设置签名的秘钥
                    .parseClaimsJws(token).getBody();     //设置需要解析的jwt
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}",token);
        }
        return claims;
    }


    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static String getUserStrByJWT(String jwt, String salt) throws Exception {
        try {
            SecretKey key = generalKey(salt);  //签名秘钥，和生成的签名的秘钥一模一样
            Claims claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)                 //设置签名的秘钥
                    .parseClaimsJws(jwt).getBody();     //设置需要解析的jwt
            if(claims != null && StringUtils.isNotEmpty(claims.getSubject())) {
               return claims.getSubject();
            }
        } catch (ExpiredJwtException e) {
            log.info("jwt超时啦");
        } catch (SignatureException e) {
            log.info("jwt解析报错啦");
        } catch (Exception e) {
            log.info("jwt解析报错啦");
        }
        return "";
    }
    /***
     * 根据token获取userId
     * @param token
     * @return
     */
    public static Long getUserIdByToken(String token){
        try{
            token  = Des.decryptString(token);
            Map<String, Object> accessTokenMap = JsonUtils.parseJSON2Map(token);
            if(accessTokenMap!=null){
                Long id  = (Long)accessTokenMap.get("id");
                return id;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
//   @Test
//    public  void  test() {
////       JWTUser user = new JWTUser();
//       Date time = generateExpirationDate();
//       System.out.println(DateUtil.date2String(time));
//       System.out.println((DateUtil.addDay(new Date(),365).getTime())- System.currentTimeMillis());
//       Date time1 = longExpirationDate();
//       System.out.println(DateUtil.date2String(time1));
//
////        String subject = JsonUtils.toJSONString(user);
////        String  uuid = UUID.randomUUID().toString();
////        System.out.println("uuid"+uuid);
//        try {
//            //String jwt = util.createJWT(Constant.JWT_ID, "Anson", subject, Constant.JWT_TTL);
//
//        //	String jwt =  JwtUtils.createJWT("Anson", "111", subject,10000,"112");
////            String jwt = JwtUtils.createJWT(1,user,uuid,10000);
////        	System.out.println("JWT：" + jwt);
//////        	Thread.sleep(15000);
////            System.out.println("\n解密\n");
////            JwtResult result = parseJWT(jwt,uuid);
////            Claims c = result.getClaims();
////            if(c != null ) {
////            	 System.out.println(c.getId());
////                 System.out.println(c.getIssuedAt());
////                 System.out.println(c.getSubject());
////                 System.out.println(c.getIssuer());
////                 System.out.println(c.get("uid", String.class));
////            }
//        } catch (Exception e) {
//            log.error("Exception!", e);
//        }
//    }
}
