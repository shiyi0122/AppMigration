package com.jxzy.AppMigration.NavigationApp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxzy.AppMigration.NavigationApp.entity.WeatherInfoBD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/10/8 14:11
 * 获取天气（百度地图）
 *
 */
public class WeatherBDUtil {

//        public final static String BAIDU_MAP_AK = "SCXoi0LVSISMOku9c1SAC4kDCHOjeoGp";
        public final static String BAIDU_MAP_AK = "LLkO2epjwP0shUuF4RoSvkOaMZ53EFCo";

        public static void main(String[] args) {

            Map<String, Object> regionWeather = getRegionWeather("110100");
            System.out.println(regionWeather);

        }


        /**
     * 根据城市获取天气预报
     * @return
     */
    public static Map<String, Object> getRegionWeather(String districtId){
        HashMap<String, Object> map = new HashMap<>();
        JSONObject obj = new JSONObject();
        String url ="https://api.map.baidu.com/weather/v1/?data_type=all&district_id="+ districtId +"&ak="+BAIDU_MAP_AK;
        try {
            String json = loadJSON(url);
            obj = JSONObject.parseObject(json);
//            System.out.println(obj.toString());
            // status:0 成功
            String success="0";
            String status = String.valueOf(obj.get("status"));
            if(success.equals(status)){
                String result = String.valueOf(obj.get("result"));
                JSONObject resultObj = JSONObject.parseObject(result);
                String forecasts = String.valueOf(resultObj.get("forecasts"));
                String now = String.valueOf(resultObj.get("now"));
                Map nowMap = JSONObject.parseObject(now, Map.class);

                List<Map> mapList = JSON.parseArray(forecasts, Map.class);



                map.put("nowMap",nowMap);
                map.put("mapList",mapList);
                return map;

            }
        } catch (Exception e) {
            System.out.println("未找到相匹配的经纬度，请检查地址！");
        }
        return map;
    }

        public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }


}
