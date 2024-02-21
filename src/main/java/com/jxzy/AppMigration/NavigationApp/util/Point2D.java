package com.jxzy.AppMigration.NavigationApp.util;

/**
 * @Author zhang
 * @Date 2023/2/24 14:36
 */
public class Point2D {

    public double x=0.0;
    public double y=0.0;
    Point2D(double x,double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
