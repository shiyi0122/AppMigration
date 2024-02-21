package com.jxzy.AppMigration.NavigationApp.util;

import com.jxzy.AppMigration.NavigationApp.entity.Point;

/**
 * 判断坐标是否在圈内
 * @Title: isPtInPoly
 * @date:   2019年7月16日 下午2:54:18
 * @author: 曲绍备
 * @param: @param ALon
 * @param: @param ALat
 * @param: @param ps
 * @param: @return
 * @return: boolean
 * @throws
 */
public class JudgingCoordinates {
    public static boolean isPtInPoly (double ALon , double ALat , Point[] ps) {
        int iSum, iCount, iIndex;
        double dLon1 = 0, dLon2 = 0, dLat1 = 0, dLat2 = 0, dLon;
        if (ps.length < 3) {
            return false;
        }
        iSum = 0;
        iCount = ps.length;
        for (iIndex = 0; iIndex<iCount;iIndex++) {
            if (iIndex == iCount - 1) {
                dLon1 = ps[iIndex].getX();
                dLat1 = ps[iIndex].getY();
                dLon2 = ps[0].getX();
                dLat2 = ps[0].getY();
            } else {
                dLon1 = ps[iIndex].getX();
                dLat1 = ps[iIndex].getY();
                dLon2 = ps[iIndex + 1].getX();
                dLat2 = ps[iIndex + 1].getY();
            }
            // 以下语句判断A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上
            if (((ALat >= dLat1) && (ALat < dLat2)) || ((ALat >= dLat2) && (ALat < dLat1))) {
                if (Math.abs(dLat1 - dLat2) > 0) {
                    //得到 A点向左射线与边的交点的x坐标：
                    dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - ALat) ) / (dLat1 - dLat2);
                    // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：
                    if (dLon < ALon) {
                        iSum++;
                    }
                }
            }
        }
        if ((iSum % 2) != 0) {
            return true;
        }
        return false;
    }

    /**
     * 计算中心点半径的 四个临界点（暂不使用）
     * @param latitude
     * @param longitude
     * @param dis 半径
     * @return
     */
    public static double[] centerDis(double latitude,double longitude,double dis){
        //地球半径千米
        double r = 6371;
        double dLng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(latitude*Math.PI/180));
        //角度转为弧度
        dLng = dLng*180/Math.PI;
        double dLat = dis/r;
        dLat = dLat*180/Math.PI;
        double minLat =latitude - dLat;
        double maxLat = latitude + dLat;

        double minLng = longitude + dLng;
        double maxLng = longitude - dLng;
        //返回四个边界点
        return new double[]{minLat,maxLat,minLng,maxLng};
    }

    /**
     *
     * 根据经纬度和距离返回一个矩形范围
     * @param lng 经度
     * @param lat 纬度
     * @param distance 距离(单位为米)
     * @return [lng1,lat1, lng2,lat2] 矩形的左下角(lng1,lat1)和右上角(lng2,lat2)
     */
    public static double[] getRectangle(double lng, double lat, long distance)
    {
        float delta = 111000;
        if (lng != 0 && lat != 0)
        {
            double lng1 = lng - distance / Math.abs(Math.cos(Math.toRadians(lat)) * delta);
            double lng2 = lng + distance / Math.abs(Math.cos(Math.toRadians(lat)) * delta);
            double lat1 = lat - (distance / delta);
            double lat2 = lat + (distance / delta);
            return new double[] {lng1, lat1, lng2, lat2};
        }
        else
        {
            double lng1 = lng - distance / delta;
            double lng2 = lng + distance / delta;
            double lat1 = lat - (distance / delta);
            double lat2 = lat + (distance / delta);
            return new double[] {lng1, lat1, lng2, lat2};
        }
    }


    private static double EARTH_RADIUS = 6378138.0;
    private static double rad(double d){

        return d * Math.PI / 180.0;

    }

    /**
     * 判断坐标是否在圆中
     * @param radius 半径
     * @param lat1 纬度
     * @param lng2  经度
     *
     *
     */
    public static boolean isInCircle(double radius,double lat1,double lng1,double lat2,double lng2){

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s =  2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        if (s > radius){
            return false;
        }else{
            return true;
        }
    }


}
