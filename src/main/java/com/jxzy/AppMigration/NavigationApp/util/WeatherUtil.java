package com.jxzy.AppMigration.NavigationApp.util;

import com.google.gson.Gson;
import com.jxzy.AppMigration.NavigationApp.entity.WeatherInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.GZIPInputStream;

public class WeatherUtil {

    private static String weatherUrl = "http://wthrcdn.etouch.cn/weather_mini?city=";
    /**
     * 通过城市名称获取该城市的天气信息
     */
    public static String GetWeatherData(String cityName) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        String sbs = "";
        HttpGet httpGet = new HttpGet(weatherUrl + cityName);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String result = getJsonStringFromGZIP(httpResponse);// 获取到解压缩之后的字符串
            JSONObject dataOfJson = JSONObject.fromObject(result);
            sbs = dataOfJson.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbs;
    }

    /**
     * 将JSON格式数据进行解析 ，返回一个weather对象
     */
    public static WeatherInfo GetWeather(String weatherInfobyJson){
        JSONObject dataOfJson = JSONObject.fromObject(weatherInfobyJson);   // json天气数据
        if(dataOfJson.getInt("status") != 1000){
            return null;
        }
        // 创建WeatherInfo对象，提取所需的天气信息
        WeatherInfo weatherInfo = new WeatherInfo();

        // 获取当前日期：日期、星期
        Calendar cal = Calendar.getInstance();    							     // Calendar类的实例化
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");  // 时间的格式化
        weatherInfo.setDate(sdf1.format(cal.getTime()));                // 时间
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        weatherInfo.setWeek(sdf2.format(cal.getTime()));                // 星期

        // 从json数据中提取数据：城市、温度、小提醒
        dataOfJson = JSONObject.fromObject(dataOfJson.getString("data"));
        weatherInfo.setCityname(dataOfJson.getString("city"));            // 城市
        weatherInfo.setTemp(dataOfJson.getString("wendu"));               // 温度
        weatherInfo.setTips(dataOfJson.getString("ganmao"));              // 小提示

        // 获取今天的天气预报信息：最高温度、最低温度、天气
        JSONArray forecast = dataOfJson.getJSONArray("forecast");
        JSONObject result = forecast.getJSONObject(0);
        weatherInfo.setHighTemp(result.getString("high").substring(2));   // 最高气温
        weatherInfo.setLowTemp(result.getString("low").substring(2));     // 最低气温
        weatherInfo.setWeather(result.getString("type"));                 // 天气

        return weatherInfo;
    }

    public static void main(String[] args){
        String info = WeatherUtil.GetWeatherData("北京");
        System.out.println("1.预测结果：" + info);                    // 全部天气数据，含预测
        WeatherInfo weatherinfo = WeatherUtil.GetWeather(info);
        System.out.println("2.今天天气：" + weatherinfo.toString());  // 当天天气数据
    }


    /**
     * 解压
     * @param: response
     * @description: TODO
     * @return: java.lang.String
     * @author: qushaobei
     * @date: 2022/7/27 0027
     */
    private static String getJsonStringFromGZIP(HttpResponse response) {
        String jsonString = null;
        try {
            InputStream is = response.getEntity().getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            bis.mark(2);
            // 取前两个字节
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset输入流到开始位置
            bis.reset();
            // 判断是否是GZIP格式
            int headerData = WeatherUtil.getShort(header);
            if (result != -1 && headerData == 0x1f8b) {
                is = new GZIPInputStream(bis);
            } else {
                is = bis;
            }
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            StringBuffer sb = new StringBuffer();
            while ((readSize = reader.read(data)) > 0) {
                sb.append(data, 0, readSize);
            }
            jsonString = sb.toString();
            bis.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private static int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    /**
     * 处理调用接口回参中的特殊字符
     * @param str
     * @return
     */
    public static String replaceAll(String str){
        String replaceAll3 = null;
        if(!StringUtils.isBlank(str)){
            String replaceAll = str.replaceAll("\\\\", "");
            System.out.println(replaceAll);
            String replaceAll2 = replaceAll.replaceAll("\"[{]", "{");
            replaceAll3 = replaceAll2.replaceAll("[}]\"", "}");
        }

        return replaceAll3;
    }

}
