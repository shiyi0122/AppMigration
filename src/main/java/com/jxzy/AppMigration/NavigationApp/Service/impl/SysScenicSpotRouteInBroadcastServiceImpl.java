package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.alipay.api.domain.DiscountByDayModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRouteInBroadcastService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRecommendedRouteMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRouteInBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;

import com.jxzy.AppMigration.NavigationApp.util.DistanceUtil;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * @Author zhang
 * @Date 2022/8/9 16:31
 * 热门线路相关接口
 */
@Service
public class SysScenicSpotRouteInBroadcastServiceImpl implements SysScenicSpotRouteInBroadcastService {

//    @Autowired
//    SysScenicSpotRouteInBroadcastMapper sysScenicSpotRouteInBroadcastMapper;

    @Autowired
    SysScenicSpotRecommendedRouteMapper sysScenicSpotRecommendedRouteMapper;

    @Autowired
    SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;

    /**
     * 获取热门线路列表
     * @param search
     * @return
     * zhang
     */
    @Override
    public PageDataResult getRouteInBroadcastList(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

//        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRouteInBroadcastMapper.getRouteInBroadcastList(search);
        PageHelper.startPage((int)search.get("pageNum"),(int)search.get("pageSize"));
        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRecommendedRouteMapper.searchRouteList(search);
        Integer  totals = sysScenicSpotRecommendedRouteMapper.selectTotals(search);
        for (SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute : list) {
//            List<SysScenicSpotRouteInBroadcast> spotRouteInBroadcasts = sysScenicSpotRouteInBroadcastMapper.selectSpotRouteIdByList(sysScenicSpotRecommendedRoute.getRouteId());
            //景点数量
            String[] split = sysScenicSpotRecommendedRoute.getRouteGps().split("!");
            List<String> spotBroadcastList = Arrays.asList(split);
            sysScenicSpotRecommendedRoute.setBroadcastCount(String.valueOf(spotBroadcastList.size()));
//            sysScenicSpotRecommendedRoute.setRouteInBroadcastList(spotBroadcastList);
        }

        if (list.size()>0){
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals(totals);
        }
        return pageDataResult;
    }

    /**
     * 全部线路
     * 张
     * @param search
     * @return
     */
    @Override
    public PageDataResult getRouteInBroadcastAll(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

//        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRouteInBroadcastMapper.getRouteInBroadcastList(search);
        PageHelper.startPage((int)search.get("pageNum"),(int)search.get("pageSize"));
        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRecommendedRouteMapper.getRouteInBroadcastAll(search);
        Integer  totals = sysScenicSpotRecommendedRouteMapper.selectTotals(search);
        for (SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute : list) {
//            List<SysScenicSpotRouteInBroadcast> spotRouteInBroadcasts = sysScenicSpotRouteInBroadcastMapper.selectSpotRouteIdByList(sysScenicSpotRecommendedRoute.getRouteId());
            String[] split = sysScenicSpotRecommendedRoute.getRouteGps().split("!");
            List<String> spotBroadcastList = Arrays.asList(split);
            //景点数量
            sysScenicSpotRecommendedRoute.setBroadcastCount(String.valueOf(spotBroadcastList.size()));
//            sysScenicSpotRecommendedRoute.setRouteInBroadcastList(spotBroadcastList);
        }

        if (list.size()>0){
            PageInfo<SysScenicSpotRecommendedRoute> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;


    }

    /**
     * 线路热度加一
     * @param id
     * @return
     */
    @Override
    public int addHotRouteInBroadcast(Long id) {

       int i =  sysScenicSpotRecommendedRouteMapper.addHotRouteInBroadcast(id);

       return i;
    }

    /**
     * 景区全景-当前位置-距离较近的景点列表
     * @param spotId
     * @param lat
     * @param lng
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcast> spotPanorama(String spotId, String lat, String lng) {

        Map<String, Object> search = new HashMap<>();
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();
        search.put("spotId",spotId);

        List<SysScenicSpotBroadcast> list = sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);

        for (SysScenicSpotBroadcast sysScenicSpotBroadcast : list) {
            String[] split = sysScenicSpotBroadcast.getBroadcastGps().split(",");
            if (!StringUtils.isEmpty(split) && split.length>0){

                 from = new Point2D.Double( Double.valueOf(split[0]),Double.valueOf(split[1]));
                 to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//                double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(split[1]), Double.valueOf(split[0]), Double.valueOf(lat), Double.valueOf(lng));
                sysScenicSpotBroadcast.setDistance(distanceOne);
            }else{
                sysScenicSpotBroadcast.setDistance(0d);
            }
        }

        Collections.sort(list, new Comparator<SysScenicSpotBroadcast>() {
            @Override
            public int compare(SysScenicSpotBroadcast o1, SysScenicSpotBroadcast o2) {
                double i =  o1.getDistance() - o2.getDistance();
                if (i>0){
                    return 1;
                }else if (i<0){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        return list;

    }

    /**
     * 伴游线路
     * 张
     * @param spotId
     * @param lat
     * @param lng
     * @return
     */

    @Override
    public List<SysScenicSpotBroadcast> lineDetails(String spotId, String id,String lat, String lng) {

        double high = 0 ;
        int j =0;
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();
        List<SysScenicSpotBroadcast> sysScenicSpotBroadcasts = new ArrayList<>();
        SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute = sysScenicSpotRecommendedRouteMapper.selectByPrimaryKey(Long.parseLong(id));
        String routeGps = sysScenicSpotRecommendedRoute.getRouteGps();
        String[] split = routeGps.split("!");
        for (int i = 0; i < split.length; i++) {
            List<SysScenicSpotBroadcast> sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.getSpotBroadcastGps(split[i]);
            SysScenicSpotBroadcast sysScenicSpotBroadcast1 = sysScenicSpotBroadcast.get(0);
            if (!StringUtils.isEmpty(sysScenicSpotBroadcast1)){
                sysScenicSpotBroadcasts.add(sysScenicSpotBroadcast1);
            }
            String[] split1 = split[i].split(",");

             from = new Point2D.Double(Double.valueOf(split1[0]),Double.valueOf(split1[1]));
             to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
            double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(split1[1]),Double.valueOf(split1[0]), Double.valueOf(lat),Double.valueOf(lng));
            sysScenicSpotBroadcast1.setDistance(distanceOne);
            if (high == 0){
                high = distanceOne;
            }else{
                if (high > distanceOne){
                   high = distanceOne;
                   j = i;
                }
            }
        }

        System.out.println(j);
        return sysScenicSpotBroadcasts;

    }

    /**
     * 伴游线路页面中的线路推荐
     * @param spotId 景点id
     * @return
     */
    @Override
    public List<SysScenicSpotRecommendedRoute> recommendLine(String spotId,String id) {

        SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(Long.parseLong(spotId));
        List<SysScenicSpotRecommendedRoute> list =  sysScenicSpotRecommendedRouteMapper.selectByRouteGps(sysScenicSpotBroadcast.getScenicSpotId(),sysScenicSpotBroadcast.getBroadcastGps());
        int j = 0 ;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRouteId() == Long.parseLong(id)){
                j = i;
            }
        }
        System.out.println(j);
        return list;
    }


}
