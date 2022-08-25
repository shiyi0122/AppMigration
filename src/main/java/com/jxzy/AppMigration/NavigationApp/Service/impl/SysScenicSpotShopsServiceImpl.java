package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotShopsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotShopsMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.awt.geom.Point2D;
import java.util.*;

@Service
@Transactional
public class SysScenicSpotShopsServiceImpl implements SysScenicSpotShopsService {

    @Autowired
    private SysScenicSpotShopsMapper sysScenicSpotShopsMapper;

    /**
     * 查询商品店铺详情
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops>
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    public List<SysScenicSpotShops> queryScenicShopsList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotShopsMapper.queryScenicShopsList(search);
    }

    /**
     * 查询最近的店铺信息
     * @param spotId
     * @param lng
     * @param lat
     * @return
     */
    @Override
    public SysScenicSpotShops getLatelyScewnicShops(String spotId, String lng, String lat) {

        Map<String,Object> search = new HashMap<>();

        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();
        search.put("scenicSpotId",spotId);
        List<SysScenicSpotShops> sysScenicSpotShops = sysScenicSpotShopsMapper.searchScenicShopsList(search);

        if (!StringUtils.isEmpty(sysScenicSpotShops)){
            for (SysScenicSpotShops sysScenicSpotShop : sysScenicSpotShops) {
                String[] split = sysScenicSpotShop.getShopsGps().split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                sysScenicSpotShop.setDistance(distanceOne);
            }

            Collections.sort(sysScenicSpotShops, new Comparator<SysScenicSpotShops>() {
                @Override
                public int compare(SysScenicSpotShops o1, SysScenicSpotShops o2) {
                    //降序
                    double i = o2.getDistance() - o1.getDistance();
                    if (i>0){
                        return 1;
                    }else if (i<0){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });

           return sysScenicSpotShops.get(0);
        }

        return null;
    }
}
