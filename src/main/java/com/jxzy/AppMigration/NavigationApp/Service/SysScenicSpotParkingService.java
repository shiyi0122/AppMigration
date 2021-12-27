package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotParkingService {
    /**
     * 获取景区停靠点列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/27 0027
     */
    List<SysScenicSpotBroadcast> getScenicSpotParkingList(int pageNum, int pageSize, Map<String, Object> search);
}
