package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBroadcastService {
    /**
     * 获取景点播报列表
     * @param: pageNum 当前页
     * @param: pageSize当前页总条数 
     * @param: search  map对象
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcast(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询景区停靠点
     * @param: search map对象
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotBroadcast> queryScenicSpotStop(int pageNum, int pageSize, Map<String, Object> search);
}
