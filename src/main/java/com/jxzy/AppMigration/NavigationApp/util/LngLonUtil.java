package com.jxzy.AppMigration.NavigationApp.util;

import org.gavaghan.geodesy.*;

import java.awt.geom.Point2D;

/**
 * @Author zhang
 * @Date 2022/2/16 10:15
 * 坐标系转换,距离计算
 */
public class LngLonUtil {

    public static double pi = 3.1415926535897932384626;
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
    public static double[] transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat,mgLon};
    }
    /**
     * 判断是否在中国
     * @param lat
     * @param lon
     * @return
     */
    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }
    /**
     * 84 ==》 高德
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    /**
     * 高德 ==》 84
     * @param lon * @param lat * @return
     * */
    public static double[] gcj02_To_Gps84(double lat, double lon) {
        double[] gps = transform(lat, lon);
        double lontitude = lon * 2 - gps[1];
        double latitude = lat * 2 - gps[0];
        return new double[]{latitude, lontitude};
    }
    /**
     * 高德 == 》 百度
     * @param lat
     * @param lon
     */
    public static double[] gcj02_To_Bd09(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat,tempLon};
        return gps;
    }

    /**
     * 百度 == 》 高德
     * @param lat
     * @param lon
     */
    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat,tempLon};
        return gps;
    }

    /**
     * 84 == 》 百度
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_bd09(double lat,double lon){
        double[] gcj02 = gps84_To_Gcj02(lat,lon);
        double[] bd09 = gcj02_To_Bd09(gcj02[0],gcj02[1]);
        return bd09;
    }

    /**
     * 百度 == 》 84
     * @param lat
     * @param lon
     * @return
     */
    public static double[] bd09_To_gps84(double lat,double lon){
        double[] gcj02 = bd09_To_Gcj02(lat, lon);
        double[] gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1]);
        //保留小数点后六位
        gps84[0] = retain6(gps84[0]);
        gps84[1] = retain6(gps84[1]);
        return gps84;
    }


    @Deprecated
    public static double calculateWithSdk(Point2D.Double fromPoint, Point2D.Double toPoint) {
        GlobalCoordinates from = new GlobalCoordinates(fromPoint.getX(), fromPoint.getY());
        GlobalCoordinates to = new GlobalCoordinates(toPoint.getX(), toPoint.getY());
        return  retain6(new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, from, to).getEllipsoidalDistance() / 1000.0);
    }
    /**
     * 计算距离
     * @param
     * @return
     */
    public static   GeodeticCurve calculateGeodeticCurve(Ellipsoid ellipsoid, GlobalCoordinates start, GlobalCoordinates end) {
        double a = ellipsoid.getSemiMajorAxis();
        double b = ellipsoid.getSemiMinorAxis();
        double f = ellipsoid.getFlattening();
        double phi1 = Angle.toRadians(start.getLatitude());
        double lambda1 = Angle.toRadians(start.getLongitude());
        double phi2 = Angle.toRadians(end.getLatitude());
        double lambda2 = Angle.toRadians(end.getLongitude());
        double a2 = a * a;
        double b2 = b * b;
        double a2b2b2 = (a2 - b2) / b2;
        double omega = lambda2 - lambda1;
        double tanphi1 = Math.tan(phi1);
        double tanU1 = (1.0D - f) * tanphi1;
        double U1 = Math.atan(tanU1);
        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double tanphi2 = Math.tan(phi2);
        double tanU2 = (1.0D - f) * tanphi2;
        double U2 = Math.atan(tanU2);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);
        double sinU1sinU2 = sinU1 * sinU2;
        double cosU1sinU2 = cosU1 * sinU2;
        double sinU1cosU2 = sinU1 * cosU2;
        double cosU1cosU2 = cosU1 * cosU2;
        double lambda = omega;
        double A = 0.0D;
        double B = 0.0D;
        double sigma = 0.0D;
        double deltasigma = 0.0D;
        boolean converged = false;

        for(int i = 0; i < 20; ++i) {
            double lambda0 = lambda;
            double sinlambda = Math.sin(lambda);
            double coslambda = Math.cos(lambda);
            double sin2sigma = cosU2 * sinlambda * cosU2 * sinlambda + (cosU1sinU2 - sinU1cosU2 * coslambda) * (cosU1sinU2 - sinU1cosU2 * coslambda);
            double sinsigma = Math.sqrt(sin2sigma);
            double cossigma = sinU1sinU2 + cosU1cosU2 * coslambda;
            sigma = Math.atan2(sinsigma, cossigma);
            double sinalpha = sin2sigma == 0.0D ? 0.0D : cosU1cosU2 * sinlambda / sinsigma;
            double alpha = Math.asin(sinalpha);
            double cosalpha = Math.cos(alpha);
            double cos2alpha = cosalpha * cosalpha;
            double cos2sigmam = cos2alpha == 0.0D ? 0.0D : cossigma - 2.0D * sinU1sinU2 / cos2alpha;
            double u2 = cos2alpha * a2b2b2;
            double cos2sigmam2 = cos2sigmam * cos2sigmam;
            A = 1.0D + u2 / 16384.0D * (4096.0D + u2 * (-768.0D + u2 * (320.0D - 175.0D * u2)));
            B = u2 / 1024.0D * (256.0D + u2 * (-128.0D + u2 * (74.0D - 47.0D * u2)));
            deltasigma = B * sinsigma * (cos2sigmam + B / 4.0D * (cossigma * (-1.0D + 2.0D * cos2sigmam2) - B / 6.0D * cos2sigmam * (-3.0D + 4.0D * sin2sigma) * (-3.0D + 4.0D * cos2sigmam2)));
            double C = f / 16.0D * cos2alpha * (4.0D + f * (4.0D - 3.0D * cos2alpha));
            lambda = omega + (1.0D - C) * f * sinalpha * (sigma + C * sinsigma * (cos2sigmam + C * cossigma * (-1.0D + 2.0D * cos2sigmam2)));
            double change = Math.abs((lambda - lambda0) / lambda);
            if (i > 1 && change < 1.0E-13D) {
                converged = true;
                break;
            }
        }

        double s = b * A * (sigma - deltasigma);
        double alpha1;
        double alpha2;
        if (!converged) {
            if (phi1 > phi2) {
                alpha1 = 180.0D;
                alpha2 = 0.0D;
            } else if (phi1 < phi2) {
                alpha1 = 0.0D;
                alpha2 = 180.0D;
            } else {
                alpha1 = 0.0D / 0.0;
                alpha2 = 0.0D / 0.0;
            }
        } else {
            double radians = Math.atan2(cosU2 * Math.sin(lambda), cosU1sinU2 - sinU1cosU2 * Math.cos(lambda));
            if (radians < 0.0D) {
                radians += 6.283185307179586D;
            }

            alpha1 = Angle.toDegrees(radians);
            radians = Math.atan2(cosU1 * Math.sin(lambda), -sinU1cosU2 + cosU1sinU2 * Math.cos(lambda)) + 3.141592653589793D;
            if (radians < 0.0D) {
                radians += 6.283185307179586D;
            }

            alpha2 = Angle.toDegrees(radians);
        }

        if (alpha1 >= 360.0D) {
            alpha1 -= 360.0D;
        }

        if (alpha2 >= 360.0D) {
            alpha2 -= 360.0D;
        }

        return new GeodeticCurve(s, alpha1, alpha2);
    }







    /*
     * 保留小数点后六位
     * @param num
     * @return
     */
    private static double retain6(double num){
        String result = String.format("%.2f", num);
        return Double.valueOf(result);
    }

    public static void main(String[] args){
        //todo  84 --> 高德
        //double[] gps = gps84_To_Gcj02(纬度,经度);
        //todo 84 -- > 百度
        //double[] gps = gps84_To_bd09(纬度,经度);
        //todo 百度 --> 84
//        double[] gps = bd09_To_gps84("纬度","经度");

        Point2D.Double from=new Point2D.Double(116.38056903541,40.02473076103);
        Point2D.Double to=new Point2D.Double(116.402078,40.029286);
        double v = LngLonUtil.calculateWithSdk(from, to);
        System.out.println(v);
    }



}
