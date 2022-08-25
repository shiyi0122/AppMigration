package com.jxzy.AppMigration.NavigationApp.util;

import com.alibaba.fastjson.JSONObject;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author zhang
 * @Date 2022/7/28 13:05
 * 计算两点距离
 */

public class DistanceUtil {

    /**
     * 百度地图 Api调用相关的百度AK  服务端ak(密钥)
     */
    public final static String BAIDU_MAP_AK = "6RshCqt5hn9u6yAgCEQlIGvczjn2tTna";

    private static final double EARTH_RADIUS = 6378.137; // 6378.137为地球半径(单位:千米)

    //温馨提示这是2个点A(经度，维度)，B（经度，维度），不是四个别犯迷糊
    //朝阳公园   116.4814281249,39.95656322779
    //莲花池公园  116.31547 ，39.89325
    public static void main(String[] args) {
//        System.out.println("经纬度距离计算结果：" + getDistance(116.4814281249,39.95656322779, 116.47126,40.07432) + "千米");
//            System.out.println("经纬度距离计算结果：" + getDistanceOne(116.38753,40.0146, 116.27455,39.83217) + "千米");
//        getAddressInfoByLngAndLat("116.4814281249","39.95656322779");
    }

    //第一种方法
//    public static double getDistance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
//        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
//        GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);
//
//        return new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();
//    }
//
//    //第二种方法（与百度地图查询出来的距离较为接近）
//    public static double getDistanceOne(double lat1, double lng1, double lat2, double lng2) {
//        double radLat1 = rad(lat1);
//        double radLat2 = rad(lat2);
//        double a = radLat1 - radLat2;      // a 是两坐标点的纬度之差
//        double b = rad(lng1) - rad(lng2);  // b 是两坐标点的经度之差
//
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
//                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
//        s = s * EARTH_RADIUS;
//        System.out.println("s = " + s + "千米"); // 单位:千米
//
//        s = Math.round(s * 1000); // 转为米，用 Math.round() 取整
//        s = s/1000; // 米转千米
//        return s;
//    }
//
//    // 角度转弧度
//    // 把用角度表示的角转换为近似相等的用弧度表示的角 Math.toRadians。
//    // 经纬度是以度数（0-360）表示。如果要把度数转换为弧度（0-2π），要除以360再乘以2ππ（相当于，乘以π/ 180）。
//    private static double rad(double d) {  return d * Math.PI / 180.0; }
//


    /**
     * 根据经纬度调用百度API获取 地理位置信息，根据经纬度
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    public static JSONObject getAddressInfoByLngAndLat(String longitude, String latitude){
        JSONObject obj = new JSONObject();
        String location=latitude+","+longitude;
        //百度url  coordtype :bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度）
        String url ="http://api.map.baidu.com/reverse_geocoding/v3/?ak="+BAIDU_MAP_AK+"&output=json&coordtype=wgs84ll&location="+location;
        try {
            String json = loadJSON(url);
            obj = JSONObject.parseObject(json);
//            System.out.println(obj.toString());
            // status:0 成功
            String success="0";
            String status = String.valueOf(obj.get("status"));
//            if(success.equals(status)){
//                String result = String.valueOf(obj.get("result"));
//                JSONObject resultObj = JSONObject.parseObject(result);
//                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
//               String formattedAddress = String.valueOf(resultObj.get("formatted_address"));
////                 //JSON字符串转换成Java对象
//                // AddressComponent addressComponentInfo = JSONObject.parseObject(addressComponent, AddressComponent.class);
////                System.out.println("addressComponentInfo:"+addressComponent);
////                System.out.println(formattedAddress);
//            }
        } catch (Exception e) {
            System.out.println("未找到相匹配的经纬度，请检查地址！");
        }
        return obj;
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
