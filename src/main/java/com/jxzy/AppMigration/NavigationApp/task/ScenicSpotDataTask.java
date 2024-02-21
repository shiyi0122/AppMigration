package com.jxzy.AppMigration.NavigationApp.task;

import com.alibaba.fastjson.JSONObject;
import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/12/6 15:23
 * 定时任务
 */

@Component
public class ScenicSpotDataTask {


    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @Autowired
    SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    SysScenicSpotRecommendedRouteService sysScenicSpotRecommendedRouteService;

    @Autowired
    SysScenicSpotBindingService sysScenicSpotBindingService;

    @Autowired
    SysScenicSpotGpsCoordinateService sysScenicSpotGpsCoordinateService;

    @Autowired
    SysScenicSpotServiceResService sysScenicSpotServiceResService;

    @Autowired
    SysScenicSpotParkingService sysScenicSpotParkingService;

    @Autowired
    BusinessOrderUserInformationService businessOrderUserInformationService;

//    @Scheduled(cron="0 0 1 * * ?")//每天凌晨一点执行
//    @Scheduled(cron="0 25 09 * * ?")
    public  void  getScenicSpotData() throws Exception {
        Map map = this.IUseOthers();
        List<SysScenicSpot> sysScenicSpotList =(List<SysScenicSpot>) map.get("sysScenicSpotList");
        List<SysScenicSpotBroadcast> sysScenicSpotBroadcastList =(List<SysScenicSpotBroadcast>) map.get("sysScenicSpotBroadcastList");
        List<SysScenicSpotRecommendedRoute> sysScenicSpotRecommendedRouteList = (List<SysScenicSpotRecommendedRoute>) map.get("sysScenicSpotRecommendedRouteList");
        List<SysScenicSpotBinding> sysScenicSpotBindingList = (List<SysScenicSpotBinding>) map.get("scenicSpotBindingList");
        List<SysScenicSpotGpsCoordinateWithBLOBs> sysScenicSpotGpsCoordinateList = (List<SysScenicSpotGpsCoordinateWithBLOBs>) map.get("scenicSpotGpsCoordinateList");
        List<SysScenicSpotServiceRes> sysScenicSpotServiceResList = (List<SysScenicSpotServiceRes>) map.get("sysScenicSpotServiceResList");
        List<SysScenicSpotParkingWithBLOBs> sysScenicSpotParkingWithBLOBsList =(List<SysScenicSpotParkingWithBLOBs>) map.get("sysScenicSpotParkingList");

        SysScenicSpot sysScenicSpotN = new SysScenicSpot();
        SysScenicSpotBroadcast sysScenicSpotBroadcastN = new SysScenicSpotBroadcast();
        SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRouteN = new SysScenicSpotRecommendedRoute();
        SysScenicSpotBinding sysScenicSpotBindingN = new SysScenicSpotBinding();
        SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBsN = new SysScenicSpotGpsCoordinateWithBLOBs();
        SysScenicSpotServiceRes sysScenicSpotServiceResN = new SysScenicSpotServiceRes();
        SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBsN = new SysScenicSpotParkingWithBLOBs();
        //景区列表
        for (SysScenicSpot sysScenicSpot : sysScenicSpotList) {
            sysScenicSpotN = new SysScenicSpot();
//            sysScenicSpotN = sysScenicSpotService.selectBySpotName(sysScenicSpot.getScenicSpotName());
            sysScenicSpotN = sysScenicSpotService.selectByIdAndName(sysScenicSpot.getScenicSpotId(),sysScenicSpot.getScenicSpotName());

            if (StringUtils.isEmpty(sysScenicSpotN)){
                sysScenicSpot.setPinYingName(Tinypinyin.tinypinyin(sysScenicSpot.getScenicSpotName()));
                sysScenicSpot.setRobotWakeupWords("2");
                int i = sysScenicSpotService.insert(sysScenicSpot);
            }else{
                sysScenicSpotN.setPinYingName(Tinypinyin.tinypinyin(sysScenicSpot.getScenicSpotName()));
                int i = sysScenicSpotService.adminEditSysScenicSpot(sysScenicSpotN);
            }
        }

        //景点列表
        for (SysScenicSpotBroadcast sysScenicSpotBroadcast : sysScenicSpotBroadcastList) {
            sysScenicSpotBroadcastN = new SysScenicSpotBroadcast();
//            sysScenicSpotBroadcastN =  sysScenicSpotBroadcastService.getSpotIdAndBroadcastName(sysScenicSpotBroadcast.getScenicSpotId(),sysScenicSpotBroadcast.getBroadcastName());
            sysScenicSpotBroadcastN = sysScenicSpotBroadcastService.getSpotBroadcastId(sysScenicSpotBroadcast.getBroadcastId());
            if (StringUtils.isEmpty(sysScenicSpotBroadcastN)){
               int i =  sysScenicSpotBroadcastService.insert(sysScenicSpotBroadcast);
            }else{
                int i = sysScenicSpotBroadcastService.editBroadcast(sysScenicSpotBroadcastN);
            }
        }
        //线路列表
//        for (SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute : sysScenicSpotRecommendedRouteList) {
//            sysScenicSpotRecommendedRouteN = new SysScenicSpotRecommendedRoute();
////            sysScenicSpotRecommendedRouteN = sysScenicSpotRecommendedRouteService.getSpotIdAndSpotRecommendedName(sysScenicSpotRecommendedRoute.getScenicSpotId(),sysScenicSpotRecommendedRoute.getRouteName());
//            sysScenicSpotRecommendedRouteN = sysScenicSpotRecommendedRouteService.getRecommendedRouteId(sysScenicSpotRecommendedRoute.getRouteId());
//            if (StringUtils.isEmpty(sysScenicSpotRecommendedRouteN)){
//               int i = sysScenicSpotRecommendedRouteService.insert(sysScenicSpotRecommendedRoute);
//            }else{
//               int i = sysScenicSpotRecommendedRouteService.exitRecommendedRoute(sysScenicSpotRecommendedRouteN);
//            }
//        }

        //景区归属地列表
        for (SysScenicSpotBinding sysScenicSpotBinding : sysScenicSpotBindingList) {
            sysScenicSpotBindingN = new SysScenicSpotBinding();
//            sysScenicSpotBindingN = sysScenicSpotBindingService.getSpotIdAndSpotFid(sysScenicSpotBinding.getScenicSpotFid(),sysScenicSpotBinding.getScenicSpotPid());
            sysScenicSpotBindingN = sysScenicSpotBindingService.getSpotBindingId(sysScenicSpotBinding.getScenicSpotFid());
            if (StringUtils.isEmpty(sysScenicSpotBindingN)){
              int i = sysScenicSpotBindingService.insert(sysScenicSpotBinding);
            }else{
              int i = sysScenicSpotBindingService.edit(sysScenicSpotBinding);
            }
        }

        //景区围栏列表
        for (SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs : sysScenicSpotGpsCoordinateList) {
           sysScenicSpotGpsCoordinateWithBLOBsN = new SysScenicSpotGpsCoordinateWithBLOBs();
//           sysScenicSpotGpsCoordinateWithBLOBsN = sysScenicSpotGpsCoordinateService.getSpotIdByGpsCoordinate(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateScenicSpotId());
            sysScenicSpotGpsCoordinateWithBLOBsN = sysScenicSpotGpsCoordinateService.getSpotGpsCoordinateId(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateId());
            if (StringUtils.isEmpty(sysScenicSpotGpsCoordinateWithBLOBs)){
              int i = sysScenicSpotGpsCoordinateService.insert( sysScenicSpotGpsCoordinateWithBLOBs);
           }else{
              int i = sysScenicSpotGpsCoordinateService.edit(sysScenicSpotGpsCoordinateWithBLOBs);
            }
        }
        //景区服务项列表
        for (SysScenicSpotServiceRes sysScenicSpotServiceRes : sysScenicSpotServiceResList) {
            sysScenicSpotServiceResN = new SysScenicSpotServiceRes();
//            sysScenicSpotServiceResN = sysScenicSpotServiceResService.getSpotIdAndServiceResId(sysScenicSpotServiceRes.getScenicSpotId(),sysScenicSpotServiceRes.getServiceId());
            sysScenicSpotServiceResN = sysScenicSpotServiceResService.getSpotServiceResId(sysScenicSpotServiceRes.getServiceId());
            if (StringUtils.isEmpty(sysScenicSpotServiceResN)){
                sysScenicSpotServiceResService.insert(sysScenicSpotServiceRes);
            }else{
                sysScenicSpotServiceResService.editSpotServiceRes(sysScenicSpotServiceRes);
            }
        }
        //停放点列表
        for (SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs : sysScenicSpotParkingWithBLOBsList) {
            sysScenicSpotParkingWithBLOBsN =  new SysScenicSpotParkingWithBLOBs();
//            sysScenicSpotParkingWithBLOBs = sysScenicSpotParkingService.getSpotIdAndParkingName(sysScenicSpotParkingWithBLOBs.getParkingScenicSpotId(),sysScenicSpotParkingWithBLOBs.getParkingName());
            sysScenicSpotParkingWithBLOBsN = sysScenicSpotParkingService.getSpotParkingId(sysScenicSpotParkingWithBLOBs.getParkingId());
            if (StringUtils.isEmpty(sysScenicSpotParkingWithBLOBsN)){
                sysScenicSpotParkingWithBLOBs.setParkingPinyinName(Tinypinyin.tinypinyin(sysScenicSpotParkingWithBLOBs.getParkingName()));
                sysScenicSpotParkingService.insert(sysScenicSpotParkingWithBLOBs);
            }else{
                sysScenicSpotParkingWithBLOBs.setParkingPinyinName(Tinypinyin.tinypinyin(sysScenicSpotParkingWithBLOBs.getParkingName()));
                sysScenicSpotParkingService.editSpotParking(sysScenicSpotParkingWithBLOBs);
            }
        }
    }
    public Map IUseOthers(){
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("https://topsroboteer.ac.cn/appSystem/YXBScenicSpotData/getScenicSpotData.do");//写网址

        postMethod.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        try{

            int status = client.executeMethod(postMethod);

            //获取返回信息

//            JSONObject jsonObject = JSONObject.fromObject(postMethod.getResponseBodyAsString());
            JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(postMethod.getResponseBodyAsString());
            String s = postMethod.getResponseBodyAsString();
            String code = jsonObject.getString("code");

            if("200".equals(code)){
                //景区列表
                String sysScenicSpot = jsonObject.get("scenicSpotList") == null ? "[]" : jsonObject.get("scenicSpotList").toString();
                List<SysScenicSpot> sysScenicSpotList = JSONObject.parseArray(sysScenicSpot, SysScenicSpot.class);

                //景点列表
                String sysScenicSpotBroadcast = jsonObject.get("sysScenicSpotBroadcastList") == null ? "[]" : jsonObject.get("sysScenicSpotBroadcastList").toString();
                List<SysScenicSpotBroadcast> sysScenicSpotBroadcastList = JSONObject.parseArray(sysScenicSpotBroadcast, SysScenicSpotBroadcast.class);

                //线路列表
                String sysScenicSpotRecommendedRout = jsonObject.get("sysScenicSpotRecommendedRouteList") == null ? "[]" : jsonObject.get("sysScenicSpotRecommendedRouteList").toString();
                List<SysScenicSpotRecommendedRoute> sysScenicSpotRecommendedRouteList = JSONObject.parseArray(sysScenicSpotRecommendedRout, SysScenicSpotRecommendedRoute.class);
                //景区归属地列表
                String sysScenicSpotBinding = jsonObject.get("scenicSpotBindingList") == null ? "[]" : jsonObject.get("scenicSpotBindingList").toString();
                List<SysScenicSpotBinding> scenicSpotBindingList = JSONObject.parseArray(sysScenicSpotBinding, SysScenicSpotBinding.class);

//              //景区电子围栏列表
                String sysScenicSpotGpsCoordinateWithBLOBs = jsonObject.get("scenicSpotGpsCoordinateList") == null ? "[]" : jsonObject.get("scenicSpotGpsCoordinateList").toString();
                List<SysScenicSpotGpsCoordinateWithBLOBs> scenicSpotGpsCoordinateList = JSONObject.parseArray(sysScenicSpotGpsCoordinateWithBLOBs, SysScenicSpotGpsCoordinateWithBLOBs.class);
                //卫生间列表
                String sysScenicSpotServiceRes = jsonObject.get("sysScenicSpotServiceResList") == null ? "[]" : jsonObject.get("sysScenicSpotServiceResList").toString();
                List<SysScenicSpotServiceRes> sysScenicSpotServiceResList = JSONObject.parseArray(sysScenicSpotServiceRes, SysScenicSpotServiceRes.class);

                //停放点列表
                String sysScenicSpotParkingWithBLOBs = jsonObject.get("sysScenicSpotParkingList") == null ? "[]" : jsonObject.get("sysScenicSpotParkingList").toString();
                List<SysScenicSpotParkingWithBLOBs> sysScenicSpotParkingList = JSONObject.parseArray(sysScenicSpotParkingWithBLOBs, SysScenicSpotParkingWithBLOBs.class);

                map.put("sysScenicSpotList",sysScenicSpotList);
                map.put("sysScenicSpotBroadcastList",sysScenicSpotBroadcastList);
                map.put("sysScenicSpotRecommendedRouteList",sysScenicSpotRecommendedRouteList);
                map.put("scenicSpotBindingList",scenicSpotBindingList);
                map.put("scenicSpotGpsCoordinateList",scenicSpotGpsCoordinateList);
                map.put("sysScenicSpotServiceResList",sysScenicSpotServiceResList);
                map.put("sysScenicSpotParkingList",sysScenicSpotParkingList);
            }
        } catch(IOException e){
            e.printStackTrace();

        }finally{
            if(postMethod != null){
                postMethod.releaseConnection();
            }
        }
        return map;
    }


    @Scheduled(cron="0 0 0 */1 * * ")//每天凌晨12点执行
    public  void  getAdmissionfeeTime() throws Exception {

        businessOrderUserInformationService.getAdmissionfeeTime();

    }


}
