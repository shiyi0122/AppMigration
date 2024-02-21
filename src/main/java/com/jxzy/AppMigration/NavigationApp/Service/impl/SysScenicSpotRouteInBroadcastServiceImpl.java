package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.alipay.api.domain.DiscountByDayModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRouteInBroadcastService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRecommendedRouteMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRouteInBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.entity.Point;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRouteInBroadcast;
import com.jxzy.AppMigration.NavigationApp.util.JudgingCoordinates;
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

    @Autowired
    SysScenicSpotRouteInBroadcastMapper sysScenicSpotRouteInBroadcastMapper;

    @Autowired
    SysScenicSpotRecommendedRouteMapper sysScenicSpotRecommendedRouteMapper;

    @Autowired
    SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;

    /**
     * 获取热门线路列表
     *
     * @param search
     * @return zhang
     */
    @Override
    public PageDataResult getRouteInBroadcastList(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

//        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRouteInBroadcastMapper.getRouteInBroadcastList(search);
        PageHelper.startPage((int) search.get("pageNum"), (int) search.get("pageSize"));
        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRecommendedRouteMapper.searchRouteList(search);
        Integer totals = sysScenicSpotRecommendedRouteMapper.selectTotals(search);
        for (SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute : list) {
//            List<SysScenicSpotRouteInBroadcast> spotRouteInBroadcasts = sysScenicSpotRouteInBroadcastMapper.selectSpotRouteIdByList(sysScenicSpotRecommendedRoute.getRouteId());
            //景点数量
            String[] split = sysScenicSpotRecommendedRoute.getRouteGps().split("!");
            List<String> spotBroadcastList = Arrays.asList(split);
            sysScenicSpotRecommendedRoute.setBroadcastCount(String.valueOf(spotBroadcastList.size()));
//            sysScenicSpotRecommendedRoute.setRouteInBroadcastList(spotBroadcastList);
        }

        if (list.size() > 0) {
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals(totals);
        }
        return pageDataResult;
    }

    /**
     * 全部线路
     * 张
     *
     * @param search
     * @return
     */
    @Override
    public PageDataResult getRouteInBroadcastAll(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

//        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRouteInBroadcastMapper.getRouteInBroadcastList(search);
        PageHelper.startPage((int) search.get("pageNum"), (int) search.get("pageSize"));
        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRecommendedRouteMapper.getRouteInBroadcastAll(search);
        Integer totals = sysScenicSpotRecommendedRouteMapper.selectTotals(search);
        for (SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute : list) {
//            List<SysScenicSpotRouteInBroadcast> spotRouteInBroadcasts = sysScenicSpotRouteInBroadcastMapper.selectSpotRouteIdByList(sysScenicSpotRecommendedRoute.getRouteId());
            String[] split = sysScenicSpotRecommendedRoute.getRouteGps().split("!");
            List<String> spotBroadcastList = Arrays.asList(split);
            //景点数量
            sysScenicSpotRecommendedRoute.setBroadcastCount(String.valueOf(spotBroadcastList.size()));
//            sysScenicSpotRecommendedRoute.setRouteInBroadcastList(spotBroadcastList);
        }

        if (list.size() > 0) {
            PageInfo<SysScenicSpotRecommendedRoute> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;


    }

    /**
     * 线路热度加一
     *
     * @param id
     * @return
     */
    @Override
    public int addHotRouteInBroadcast(Long id) {

        int i = sysScenicSpotRecommendedRouteMapper.addHotRouteInBroadcast(id);

        return i;
    }

    /**
     * 景区全景-当前位置-距离较近的景点列表
     *
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
        search.put("spotId", spotId);

        List<SysScenicSpotBroadcast> list = sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);

        for (SysScenicSpotBroadcast sysScenicSpotBroadcast : list) {
            String[] split = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
            if (!StringUtils.isEmpty(split) && split.length > 0) {

                from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng), Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//                double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(split[1]), Double.valueOf(split[0]), Double.valueOf(lat), Double.valueOf(lng));
                sysScenicSpotBroadcast.setDistance(distanceOne);
            } else {
                sysScenicSpotBroadcast.setDistance(0d);
            }
        }

        Collections.sort(list, new Comparator<SysScenicSpotBroadcast>() {
            @Override
            public int compare(SysScenicSpotBroadcast o1, SysScenicSpotBroadcast o2) {
                double i = o1.getDistance() - o2.getDistance();
                if (i > 0) {
                    return 1;
                } else if (i < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return list;

    }

    /**
     * 伴游线路
     * 张
     *
     * @param spotId
     * @param lat
     * @param lng
     * @return
     */

    @Override
    public List<SysScenicSpotBroadcast> lineDetails(String spotId, String id, String lat, String lng) {

        double high = 0;
        int j = 0;
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();
        List<SysScenicSpotBroadcast> sysScenicSpotBroadcasts = new ArrayList<>();
        SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute = sysScenicSpotRecommendedRouteMapper.selectByPrimaryKey(Long.parseLong(id));
        String routeGps = sysScenicSpotRecommendedRoute.getRouteGpsBaiDu();
        String[] split = routeGps.split("!");
        for (int i = 0; i < split.length; i++) {
            List<SysScenicSpotBroadcast> sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.getSpotBroadcastGps(split[i]);
            SysScenicSpotBroadcast sysScenicSpotBroadcast1 = sysScenicSpotBroadcast.get(0);
            if (!StringUtils.isEmpty(sysScenicSpotBroadcast1)) {
                sysScenicSpotBroadcasts.add(sysScenicSpotBroadcast1);
            }
            String[] split1 = split[i].split(",");

            from = new Point2D.Double(Double.valueOf(split1[0]), Double.valueOf(split1[1]));
            to = new Point2D.Double(Double.valueOf(lng), Double.valueOf(lat));
            double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(split1[1]),Double.valueOf(split1[0]), Double.valueOf(lat),Double.valueOf(lng));
            sysScenicSpotBroadcast1.setDistance(distanceOne);
            if (high == 0) {
                high = distanceOne;
            } else {
                if (high > distanceOne) {
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
     *
     * @param spotId 景点id
     * @return
     */
    @Override
    public List<SysScenicSpotRecommendedRoute> recommendLine(String spotId, String id) {

        SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(Long.parseLong(spotId));
        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRecommendedRouteMapper.selectByRouteGps(sysScenicSpotBroadcast.getScenicSpotId(), sysScenicSpotBroadcast.getBroadcastGps());
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRouteId() == Long.parseLong(id)) {
                j = i;
            }
        }
        System.out.println(j);
        return list;
    }

    /**
     * 伴游线路(方法2)
     * 张
     *
     * @param spotId
     * @param lat
     * @param lng
     * @return
     */
    @Override
    public List<SysScenicSpotRecommendedRoute> lineDetailsTwo(String spotId, String id, String lat, String lng) {

        Map<String, Object> search = new HashMap<>();
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();

        search.put("spotId", spotId);
        search.put("routeId", id);

        List<SysScenicSpotRecommendedRoute> routeInBroadcastAll = sysScenicSpotRecommendedRouteMapper.getRouteInBroadcastAll(search);

        if (!StringUtils.isEmpty(routeInBroadcastAll)) {
            for (SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute : routeInBroadcastAll) {

                search = new HashMap<String, Object>();
                double sum = 0;
                //j景点列表中距离当前位置最近的景点
                int j = 0;
                search.put("routeId", sysScenicSpotRecommendedRoute.getRouteId());

                List<SysScenicSpotRouteInBroadcast> routeInBroadcastList = sysScenicSpotRouteInBroadcastMapper.getSpotIdAndRouteIdByList(search);

                for (int i = 0; i < routeInBroadcastList.size(); i++) {

                    String[] split = routeInBroadcastList.get(i).getBroadcastGpsBaiDu().split(",");
                    if (split.length > 2) {
                        split = routeInBroadcastList.get(i).getBroadcastGpsBaiDu().split("!");
                        split = split[0].split(",");
                    }

                    //景点坐标半径
                    String scenicSpotRange = routeInBroadcastList.get(i).getScenicSpotRange();
                    //判断用户坐标是否在景点坐标半径内
//                    double[] rectangle = JudgingCoordinates.getRectangle(Double.valueOf(split[0]), Double.valueOf(split[1]), Long.valueOf(scenicSpotRange));
//                    Point[] ps = new Point[rectangle.length/2];
//                    ps[0] = new Point(Double.valueOf(rectangle[0]), Double.valueOf(rectangle[1]));
//                    ps[1] = new Point(Double.valueOf(rectangle[2]),Double.valueOf(rectangle[3]));
//                    boolean ptInPoly = JudgingCoordinates.isPtInPoly(Double.valueOf(lng), Double.valueOf(lat), ps);
                    boolean inCircle = JudgingCoordinates.isInCircle(Double.valueOf(scenicSpotRange), Double.valueOf(split[1]), Double.valueOf(split[0]), Double.valueOf(lat), Double.valueOf(lng));
                    if (inCircle) {
                        j = i;
                        break;
                    }
                    from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf(lng), Double.valueOf(lat));
                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                    if (sum == 0) {
                        sum = distanceOne;
                        j = i;
                    } else {
                        if (sum > distanceOne) {
                            sum = distanceOne;
                            j = i;
                        }
                    }
                }
                System.out.println("****************---------" + j);
                sysScenicSpotRecommendedRoute.setRouteInBroadcastList(routeInBroadcastList);
                sysScenicSpotRecommendedRoute.setCurrentSpot(j);
            }
        }
        return routeInBroadcastAll;
    }

    /**
     * 伴游线路页面中的推荐线路(方法2)
     * 张
     */
    @Override
    public List<SysScenicSpotRecommendedRoute> recommendLineTwo(String spotId, String id, String broadcastId) {

        Map<String, Object> search = new HashMap<>();
        search.put("spotId", spotId);
        List<SysScenicSpotRecommendedRoute> routeInBroadcastAll = sysScenicSpotRecommendedRouteMapper.getRouteInBroadcastAll(search);

        List<SysScenicSpotRecommendedRoute> r = new ArrayList<>();
        int t = 0;
        for (int i = 0; i < routeInBroadcastAll.size(); i++) {
            SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute = routeInBroadcastAll.get(i);
            search = new HashMap<String, Object>();
            search.put("routeId", sysScenicSpotRecommendedRoute.getRouteId());
            List<SysScenicSpotRouteInBroadcast> spotIdAndRouteIdByList = sysScenicSpotRouteInBroadcastMapper.getSpotIdAndRouteIdByList(search);
            for (int i1 = 0; i1 < spotIdAndRouteIdByList.size(); i1++) {
                if (spotIdAndRouteIdByList.get(i1).getSysScenicSpotBroadcastId() == Long.parseLong(broadcastId)) {
                    r.add(routeInBroadcastAll.get(i));
                    break;
                }
            }
            if (sysScenicSpotRecommendedRoute.getRouteId() == Long.parseLong(id)) {
                t = i;
            }
        }
        return r;
    }

    /**
     * 点击景点进入地图页（暂时写死）
     *
     * @param
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcast> broadcastEnterMap(Map<String, Object> search, String lng, String lat) {

        List<SysScenicSpotBroadcast> spotBroadcastList = sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);
        List<SysScenicSpotBroadcast> spotBroadcastListNew = new ArrayList<>();
        SysScenicSpotBroadcast sysScenicSpotBroadcast = new SysScenicSpotBroadcast();
        List<SysScenicSpotBroadcast> sysScenicSpotBroadcasts = spotBroadcastList.subList(0, 5);
        int t = 0;
//        for (SysScenicSpotBroadcast sysScenicSpotBroadcast : spotBroadcastList) {
//            String broadcastGps = sysScenicSpotBroadcast.getBroadcastGps();
//            String[] split = broadcastGps.split(",");
//            if (split.length>2){
//                split = broadcastGps.split("!");
//                split = split[0].split(",");
//            }
//            String scenicSpotRange = sysScenicSpotBroadcast.getScenicSpotRange();
//            boolean inCircle = JudgingCoordinates.isInCircle(Double.valueOf(scenicSpotRange), Double.valueOf(split[1]), Double.valueOf(split[0]), Double.valueOf(lat), Double.valueOf(lng));
//            if (inCircle){
//                spotBroadcastListNew.add()
//
//            }
//
//
//        }
        for (int i = 0; i < spotBroadcastList.size(); i++) {
            String broadcastGpsBaiDu = spotBroadcastList.get(i).getBroadcastGpsBaiDu();
            String[] split = broadcastGpsBaiDu.split(",");
            if (split.length > 2) {
                split = broadcastGpsBaiDu.split("!");
                split = split[0].split(",");
            }

            String scenicSpotRange = spotBroadcastList.get(i).getScenicSpotRange();
            boolean inCircle = JudgingCoordinates.isInCircle(Double.valueOf(scenicSpotRange), Double.valueOf(split[1]), Double.valueOf(split[0]), Double.valueOf(lat), Double.valueOf(lng));
            if (inCircle) {
                t = 1;
                sysScenicSpotBroadcast = spotBroadcastList.get(i);
                break;
            }
        }
        if (t == 1) {

            for (int i = 0; i < 5; i++) {
                if (i == 2) {
                    spotBroadcastListNew.add(sysScenicSpotBroadcast);
                } else {
                    for (SysScenicSpotBroadcast scenicSpotBroadcast : spotBroadcastList) {
                        if (scenicSpotBroadcast.getBroadcastId() != sysScenicSpotBroadcast.getBroadcastId()) {
                            spotBroadcastListNew.add(scenicSpotBroadcast);
                            break;
                        }
                    }
                }
            }

            return spotBroadcastListNew;
        } else {
            return sysScenicSpotBroadcasts;
        }

    }

    /**
     * 伴游线路页面中的推荐线路(方法3)
     * 张
     */
    @Override
    public List<SysScenicSpotRecommendedRoute> recommendLineThree(String spotId, String id, String broadcastId) {
        Map<String, Object> search = new HashMap<>();
        search.put("spotId", spotId);
        //线路列表
        List<SysScenicSpotRecommendedRoute> routeInBroadcastAll = sysScenicSpotRecommendedRouteMapper.getRouteInBroadcastAll(search);

        //景点列表
//        List<SysScenicSpotBroadcast> spotBroadcastList = sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);

        List<SysScenicSpotBroadcast> sysScenicSpotBroadcasts = new ArrayList<>();
        List<SysScenicSpotBroadcast> sysScenicSpotBroadcastGpss = new ArrayList<>();
        List<SysScenicSpotRecommendedRoute> r = new ArrayList<>();
        int t = 0;
        for (int i = 0; i < routeInBroadcastAll.size(); i++) {
            sysScenicSpotBroadcasts = new ArrayList<>();
//            sysScenicSpotBroadcastGpss = new ArrayList<>();
            SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute = routeInBroadcastAll.get(i);
            search = new HashMap<String, Object>();
            search.put("routeId", sysScenicSpotRecommendedRoute.getRouteId());
            List<SysScenicSpotRouteInBroadcast> spotIdAndRouteIdByList = sysScenicSpotRouteInBroadcastMapper.getSpotIdAndRouteIdByList(search);
            for (int i1 = 0; i1 < spotIdAndRouteIdByList.size(); i1++) {

                SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(spotIdAndRouteIdByList.get(i1).getSysScenicSpotBroadcastId());
                if (!StringUtils.isEmpty(sysScenicSpotBroadcast)) {
                    sysScenicSpotBroadcast.setPictureUrl(spotIdAndRouteIdByList.get(i1).getPictureUrl());
                    sysScenicSpotBroadcast.setAudioUrl(spotIdAndRouteIdByList.get(i1).getAudioUrl());
                    sysScenicSpotBroadcast.setVideoUrl(spotIdAndRouteIdByList.get(i1).getVideoUrl());
                    sysScenicSpotBroadcast.setBroadcastContent(spotIdAndRouteIdByList.get(i1).getBroadcastContent());
                    sysScenicSpotBroadcasts.add(sysScenicSpotBroadcast);
                }
            }
//            if (sysScenicSpotRecommendedRoute.getRouteId()== Long.parseLong(id)){
//                t = i;
//            }
            //手动计算线路中相近的景点
//            String[] split = sysScenicSpotRecommendedRoute.getRouteGpsBaiDu().split("!");
//            for (String s : split) {
//
//                String[] BaiDuGps = s.split(",");
//                for (SysScenicSpotBroadcast sysScenicSpotBroadcast : spotBroadcastList) {
//                    t = 0;
//                    String[]  broadcastBaiDuGpss = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split("!");
//                    String[] broadcastBaiDuGps = broadcastBaiDuGpss[0].split(",");
//
//                    String scenicSpotRange = sysScenicSpotBroadcast.getScenicSpotRange();
//
//                    boolean inCircle = JudgingCoordinates.isInCircle(Double.valueOf(80), Double.valueOf(broadcastBaiDuGps[1]), Double.valueOf(broadcastBaiDuGps[0]), Double.valueOf(BaiDuGps[1]), Double.valueOf(BaiDuGps[0]));
//                    if (inCircle){
//                        sysScenicSpotBroadcasts.add(sysScenicSpotBroadcast);
//                        t = 1;
//                        sysScenicSpotBroadcastGpss.add(sysScenicSpotBroadcast);
//                        break;
//                    }
//                }
//                if (t == 0 ){
//                    sysScenicSpotBroadcastGpss.add(new SysScenicSpotBroadcast());
//                }
//            }
            routeInBroadcastAll.get(i).setSysScenicSpotBroadcastList(sysScenicSpotBroadcasts);
//            routeInBroadcastAll.get(i).setSysScenicSpotBroadcastGpss(sysScenicSpotBroadcastGpss);

        }


        return routeInBroadcastAll;
    }


}
