package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotShopsService {
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
    List<SysScenicSpotShops> queryScenicShopsList(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询最近的店铺信息
     * @param spotId
     * @param lng
     * @param lat
     * @return
     */
    SysScenicSpotShops getLatelyScewnicShops(String spotId, String lng, String lat);

}
