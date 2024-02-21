package com.jxzy.AppMigration.NavigationApp.util;



import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhang
 * @Date 2023/2/24 14:29
 */

@Service
public class PolygonOutlineUtil {

    //+
    public  Point2D plus(Point2D left, Point2D right){
        return new Point2D(left.x+right.x, left.y+right.y);
    }
    //-
    public  Point2D reduce(Point2D left,Point2D right) {
        return new Point2D(left.x-right.x, left.y-right.y);
    }
    //value*
    public  Double rideDouble(Point2D left, Point2D right) {
        return left.x*right.x + left.y*right.y;
    }
    //*
    public  Point2D ride(Point2D left, Double value){
        return new Point2D(left.x*value, left.y*value);
    }

    // 自定义的向量差乘运算符号 **
    public  Double operator (Point2D left, Point2D right) {
        return left.x*right.y - left.y*right.x;
    }

//    public   List<Point2D> pList = new ArrayList<>(); // 原始顶点坐标， 在initPList函数当中初始化赋值
//    public  List<Point2D> dpList = new ArrayList<>();// 边向量dpList［i＋1］－ dpLIst［i］ 在 initDPList函数当中计算后赋值
//    public  List<Point2D> ndpList = new ArrayList<>(); // 单位化的边向量， 在initNDPList函数当中计算后肤质，实际使用的时候，完全可以用dpList来保存他们
//    public  List<Point2D> newList = new ArrayList<>();  // 新的折线顶点，在compute函数当中，赋值


    // 初始化顶点队列
    public   List<Point2D> initPList(String str){
        List<Point2D> pList = new ArrayList<>();
        String[] split = str.split("!");
        for(String point:split){
            String[] split1 = point.split(",");
            pList.add(new Point2D(Double.valueOf(split1[1]),  Double.valueOf(split1[0])));
        }

        return pList;
    }


    // 初始化dpList  两顶点间向量差
    public   List<Point2D> initDPList(List<Point2D> pList){
        List<Point2D> dpList = new ArrayList<>();
        System.out.println("计算两顶点间向量差:dpList");
        for (int index=0; index<pList.size();index++){
            int i= index==pList.size()-1 ? 0: index+1;
            dpList.add(reduce(pList.get(index==pList.size()-1 ? 0: index+1),pList.get(index)));
        }
        return dpList;
    }



    // 初始化ndpList，单位化两顶点向量差
    public   List<Point2D> initNDPList(List<Point2D> dpList){
        List<Point2D> ndpList = new ArrayList<>();
        System.out.println("开始计算单位化两顶点向量差:ndpList");
        for(int index =0; index<dpList.size();index++) {
            ndpList.add(ride(dpList.get(index),( 1.0 / Math.sqrt(rideDouble(dpList.get(index),dpList.get(index))))));
        }
        return ndpList;
    }

    // 计算新顶点， 注意参数为负是向内收缩， 为正是向外扩张
    public   List<Point2D> computeLine(List<Point2D> pList,List<Point2D> ndpList , Double dist){
        List<Point2D> newList = new ArrayList<>();
        System.out.println("开始计算新顶点");
        int count = pList.size();
        for (int index = 0 ; index<count; index++) {
            int startIndex = index==0 ? count-1 : index-1;
            int endIndex   = index;
            Double sina = operator(ndpList.get(startIndex),ndpList.get(endIndex));
            Double length = dist / sina;
            Point2D vector = reduce(ndpList.get(endIndex),ndpList.get(startIndex));
            newList.add(plus(pList.get(index),ride(vector,length)));
        }

        return newList;
    }

    public  String computeLine(String coordinateGroup , Double dist){

        List<Point2D> pList = new ArrayList<>();
        List<Point2D> dpList = new ArrayList<>();
        List<Point2D> ndpList = new ArrayList<>();
        List<Point2D> newList = new ArrayList<>();
        String coordinatesss = "";

        pList = initPList(coordinateGroup);
        dpList = initDPList(pList);//0.005
        ndpList = initNDPList(dpList);
        newList = computeLine(pList, ndpList, dist);//正数为外廓，负数为内陷

        for (Point2D point2D : newList) {
            if (StringUtils.isEmpty(coordinatesss)){
                coordinatesss = point2D.y + "," + point2D.x;
            }else{
                coordinatesss = coordinatesss + "!" + point2D.y + "," + point2D.x ;
            }

        }
        return coordinatesss;
    }



    // 整个算法的调用顺序
    public static void main(String[] args) {
//        String str1 = "30.280196656341005,101.70201860368253;31.17443221616588,102.44943223893644;31.612013574947458,105.5988347902894;30.606585065678747,105.95238883048297";

        String str = "116.27371,39.83255!116.27644,39.83271!116.27669,39.83125!116.27372,39.83109";
//        initPList(str);
//        initDPList();//0.005
//        initNDPList();
//        computeLine(0.01D);//正数为外廓，负数为内陷
//        newList.forEach(e->{
//            System.out.println("{lat: "+ e.x +", lng: "+e.y+"},");
//        });
//        String s = computeLine(str, 0.05d);
//        System.out.println(s);
    }



}
