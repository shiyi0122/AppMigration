package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

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
    List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcastList(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询景点详情
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs
     * @author: qushaobei
     * @date: 2022/8/5 0005
     */
    SysScenicSpotBroadcastExtendWithBLOBs queryscenicSpotContent(Map<String, Object> search);

    /**
     * 获取景点列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSpotBroadcastList(Integer pageNum, Integer pageSize, Integer sort, Map<String, Object> search);

    /**
     * 景点热度加一
     * @param id
     * @return
     */
    int addHotSpotBroadcast(Long id);

}
