package com.jxzy.AppMigration.NavigationApp.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/7/23 17:11
 * 根据坐标获取对应城市（百度坐标）
 *
 */
public class HttpClientUtils {

    //传入经纬度, 返回查询的地区, lng: 纬度, lat: 经度
    public static String findByLatAndLng(String lng, String lat) {
        try {
            //移除坐标前后的 空格
            /*lng = lng.trim();
            lat = lat.trim();*/

            CloseableHttpClient httpClient = HttpClients.createDefault();
            // url中的ak值要替换成自己的:
            String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=6RshCqt5hn9u6yAgCEQlIGvczjn2tTna&output=json&coordtype=wgs84ll&location=" + lng + "," + lat;
            //System.out.println(url);
            HttpGet httpGet = new HttpGet(url);

            CloseableHttpResponse response = httpClient.execute(httpGet);

            HttpEntity httpEntity = response.getEntity();

            String json = EntityUtils.toString(httpEntity);

            Map<String, Object> result = JSONObject.parseObject(json, Map.class);
            if (result.get("status").equals(0)) {
                Map<String, Object> resultMap = (Map<String, Object>) result.get("result");
                resultMap = (Map<String, Object>) resultMap.get("addressComponent");
//                String country = (String) resultMap.get("country");
                String province = (String) resultMap.get("province");
//                String city = (String) resultMap.get("city");
//                return country + province + city;

                if (province.length()<=3){
                    String city = StringUtils.substringBefore(province, "市");
                    String provinceN = StringUtils.substringBefore(province, "省");
                    if (city == null || city == ""){

                        return provinceN;
                    }else{
                        return city;
                    }
                }else{//判断是否是内蒙古，新疆，西藏等长度不同的地方（没有省，市的地区）





                }



                StringUtils.substringAfter(province,"省");

                return province;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }









    public static void main(String[] args) {

        String byLatAndLng = HttpClientUtils.findByLatAndLng("29.41", "106.56");
        System.out.println(byLatAndLng);


    }
}
