package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastExtendMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.util.DistanceUtil;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.awt.geom.Point2D;
import java.util.*;

@Service
@Transactional
public class SysScenicSpotBroadcastServiceImpl implements SysScenicSpotBroadcastService {
    @Autowired
    private SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;
    @Autowired
    private SysScenicSpotBroadcastExtendMapper sysScenicSpotBroadcastExtendMapper;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址
    /**
     *
     * @param: pageNum 当前页
     * @param: pageSize当前页总条数
     * @param: search  map对象
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcast(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBroadcast> broadcast = sysScenicSpotBroadcastMapper.queryWordsScenicSpotBroadcast(search);
        for (int i =0;i<broadcast.size();i++) {
            List<SysScenicSpotBroadcastExtendWithBLOBs> sysScenicSpotBroadcastExtend = broadcast.get(i).getSysScenicSpotBroadcastExtend();
            if (sysScenicSpotBroadcastExtend.size() > 0) {
                for (int j = 0; j < sysScenicSpotBroadcastExtend.size(); j++) {
                    sysScenicSpotBroadcastExtend.get(j).setPictureUrl(DOMAIN_NAME+sysScenicSpotBroadcastExtend.get(j).getPictureUrl());
                }
            }
        }
        return sysScenicSpotBroadcastMapper.queryWordsScenicSpotBroadcast(search);
    }

    /**
     * 查询景区停靠点
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotBroadcast> queryScenicSpotStop(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotBroadcastMapper.queryScenicSpotStop(search);
    }

    /**
     * 查询景点排行
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    public List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcastList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotBroadcastMapper.queryWordsScenicSpotBroadcastList(search);
    }


    /**
     * 查询景点详情
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs
     * @author: qushaobei
     * @date: 2022/8/5 0005
     */
    public SysScenicSpotBroadcastExtendWithBLOBs queryscenicSpotContent(Map<String, Object> search) {
        SysScenicSpotBroadcastExtendWithBLOBs Broadcast = sysScenicSpotBroadcastExtendMapper.queryscenicSpotContent(search);
        if (Broadcast != null) {
            if (Broadcast.getPictureUrl() != null && !"".equals(Broadcast.getPictureUrl())) {
                Broadcast.setPictureUrl(DOMAIN_NAME+Broadcast.getPictureUrl());
            }
        }
        return Broadcast;
//        return sysScenicSpotBroadcastExtendMapper.queryscenicSpotContent(search);

    }

    /**
     * 获取景点列表
     * 张
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSpotBroadcastList(Integer pageNum, Integer pageSize, Integer sort, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        List<SysScenicSpotBroadcast> list = new ArrayList<>();
        if (!StringUtils.isEmpty(search.get("lat")) && !StringUtils.isEmpty(search.get("lat"))){
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();
            search.put("sort",sort);
            PageHelper.startPage(pageNum,pageSize);
             list =  sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);
            JSONObject obj = new JSONObject();
            for (SysScenicSpotBroadcast sysScenicSpotBroadcast : list) {

                String[] split = sysScenicSpotBroadcast.getBroadcastGps().split(",");

                if (split.length>2){
                    String[] split1 = sysScenicSpotBroadcast.getBroadcastGps().split("!");
                    split = split1[0].split(",");
                }
                String[] baiDusplit = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (!StringUtils.isEmpty(split) && split.length>0){

                    from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//                double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf((String)search.get("lat")),Double.valueOf((String)search.get("lng")), Double.valueOf(split[1]), Double.valueOf(split[0]));
                    sysScenicSpotBroadcast.setDistance(distanceOne);
                    obj = DistanceUtil.getAddressInfoByLngAndLat(baiDusplit[0], baiDusplit[1]);
                    String status = String.valueOf(obj.get("status"));
                    if("0".equals(status)){
                        String result = String.valueOf(obj.get("result"));
                        JSONObject resultObj = JSONObject.parseObject(result);
//                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
                        String formattedAddress = String.valueOf(resultObj.get("formatted_address"));

                        sysScenicSpotBroadcast.setSpotBroadcastAddress(formattedAddress);
                    }
                }
            }
        }else{

            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();
            search.put("sort",sort);
            PageHelper.startPage(pageNum,pageSize);
            list =  sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);
            JSONObject obj = new JSONObject();
            for (SysScenicSpotBroadcast sysScenicSpotBroadcast : list) {
                String[] split = sysScenicSpotBroadcast.getBroadcastGps().split(",");
                String[] baiDusplit = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (!StringUtils.isEmpty(split) && split.length>0){
//                    from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
//                    to = new Point2D.Double(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
//                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
////                double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf((String)search.get("lat")),Double.valueOf((String)search.get("lng")), Double.valueOf(split[1]), Double.valueOf(split[0]));
//                    sysScenicSpotBroadcast.setDistance(distanceOne);
                    obj = DistanceUtil.getAddressInfoByLngAndLat(baiDusplit[0], baiDusplit[1]);
                    String status = String.valueOf(obj.get("status"));
                    if("0".equals(status)){
                        String result = String.valueOf(obj.get("result"));
                        JSONObject resultObj = JSONObject.parseObject(result);
//                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
                        String formattedAddress = String.valueOf(resultObj.get("formatted_address"));
                        sysScenicSpotBroadcast.setSpotBroadcastAddress(formattedAddress);
                    }
                }
            }
        }
        if (sort == 2){
            Collections.sort(list, new Comparator<SysScenicSpotBroadcast>() {
                @Override
                public int compare(SysScenicSpotBroadcast o1, SysScenicSpotBroadcast o2) {
                    double i = o1.getDistance() - o2.getDistance();
                    if (i>0){
                        return 1;
                    }else if (i<0){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
        }
        if (list.size() > 0){
            PageInfo<SysScenicSpotBroadcast> pageInfo = new PageInfo<>(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 景区热度加一
     * @param id
     * @return
     */
    @Override
    public int addHotSpotBroadcast(Long id) {
       int i = sysScenicSpotBroadcastMapper.addHotSpotBroadcast(id);
       return i;
    }

}
