package com.jxzy.AppMigration.NavigationApp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 通用物体和场景识别
 */

@Service
public class AdvancedGeneral {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    AuthService authService;

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public  String advancedGeneral(MultipartFile file) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
        try {
            // 本地文件路径
//            String filePath = "http://47.93.220.232:8081/sysLandmark/53f2f629-8909-4ef3-b40f-85745a7c08ea.png";
//            byte[] imgData = image2Byte(filePath);
//            byte[] imgData = FileUtilBD.readFileByBytes(filePath);
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam + "&baike_num=5";
//            String param1 = "baike_num = 5";
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = AuthService.getAuth();
            boolean authserviceBAIDU = redisUtil.exists("authServiceBAIDU");
            String accessToken= null;
            if (authserviceBAIDU){
                accessToken = (String)redisUtil.get("authServiceBAIDU");
            }else{
                accessToken = authService.getAuth();
                redisUtil.set("authServiceBAIDU",accessToken);
            }
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String error_code = (String)jsonObject.get("error_code");
            if (!StringUtils.isEmpty(error_code)){
                String accessTokenN = authService.getAuth();
                redisUtil.set("authServiceBAIDU",accessToken);
                String resultN = HttpUtil.post(url, accessTokenN, param);
                return resultN;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通用识别
     * @param imgData
     * @return
     */
    public  String advancedGeneral( byte[] imgData ) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
        try {
            // 本地文件路径
//            String filePath = "http://47.93.220.232:8081/sysLandmark/53f2f629-8909-4ef3-b40f-85745a7c08ea.png";
//            byte[] imgData = image2Byte(filePath);
//            byte[] imgData = FileUtilBD.readFileByBytes(filePath);
//            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam + "&baike_num=5";
//            String param1 = "baike_num = 5";
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = AuthService.getAuth();
            boolean authserviceBAIDU = redisUtil.exists("authServiceBAIDU");
            String accessToken= null;
            if (authserviceBAIDU){
                accessToken = (String)redisUtil.get("authServiceBAIDU");
            }else{
                accessToken = authService.getAuth();
                redisUtil.set("authServiceBAIDU",accessToken);
            }
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String error_code = (String)jsonObject.get("error_code");
            if (!StringUtils.isEmpty(error_code)){
                String accessTokenN = authService.getAuth();
                redisUtil.set("authServiceBAIDU",accessToken);
                String resultN = HttpUtil.post(url, accessTokenN, param);
                return resultN;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public  String advancedGeneral(String  filePath) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
        try {
            // 本地文件路径
//            String filePath = "http://47.93.220.232:8081/sysLandmark/53f2f629-8909-4ef3-b40f-85745a7c08ea.png";

//            filePath = java.net.URLDecoder.decode(filePath, "utf-8");
            byte[] imgData = image2Byte(filePath);
//            byte[] imgData = FileUtilBD.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = AuthService.getAuth();
            boolean authserviceBAIDU = redisUtil.exists("authServiceBAIDU");
            String accessToken= null;
            if (authserviceBAIDU){
                accessToken = (String)redisUtil.get("authServiceBAIDU");
            }else{
                accessToken = authService.getAuth();
                redisUtil.set("authServiceBAIDU",accessToken);
            }
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String error_code = (String)jsonObject.get("error_code");
            if (!StringUtils.isEmpty(error_code)){
                String accessTokenN = authService.getAuth();
                String resultN = HttpUtil.post(url, accessToken, param);
                return resultN;
            }
//            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 植物识别
     * @param imgData
     * @return
     */
    public  String botanYadvancedGeneral( byte[] imgData ) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant";
        try {
            // 本地文件路径
//            String filePath = "http://47.93.220.232:8081/sysLandmark/53f2f629-8909-4ef3-b40f-85745a7c08ea.png";
//            byte[] imgData = image2Byte(filePath);
//            byte[] imgData = FileUtilBD.readFileByBytes(filePath);
//            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam + "&baike_num=5";
//            String param1 = "baike_num = 5";
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = AuthService.getAuth();
            boolean authserviceBAIDU = redisUtil.exists("authServiceBAIDU");
            String accessToken= null;
            if (authserviceBAIDU){
                accessToken = (String)redisUtil.get("authServiceBAIDU");
            }else{
                accessToken = authService.getAuth();
                redisUtil.set("authServiceBAIDU",accessToken);
            }
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String error_code = (String)jsonObject.get("error_code");
            if (!StringUtils.isEmpty(error_code)){
                String accessTokenN = authService.getAuth();
                redisUtil.set("authServiceBAIDU",accessToken);
                String resultN = HttpUtil.post(url, accessTokenN, param);
                return resultN;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }






    public static void main(String[] args) {
//        AdvancedGeneral.advancedGeneral();
    }

    public static byte[] image2Byte(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try{
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while( (len=is.read(buffer)) != -1 ){
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return outStream.toByteArray();
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outStream != null)
            {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpUrl != null)
            {
                httpUrl.disconnect();
            }
        }
        return null;
    }


}