package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtend;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBroadcastExtendMapper {
    int deleteByPrimaryKey(Long broadcastResId);

    int insert(SysScenicSpotBroadcastExtendWithBLOBs record);

    int insertSelective(SysScenicSpotBroadcastExtendWithBLOBs record);

    SysScenicSpotBroadcastExtendWithBLOBs selectByPrimaryKey(Long broadcastResId);

    int updateByPrimaryKeySelective(SysScenicSpotBroadcastExtendWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotBroadcastExtendWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotBroadcastExtend record);
    /**
     * 查询景点详情
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs
     * @author: qushaobei
     * @date: 2022/8/5 0005
     */
    List<SysScenicSpotBroadcastExtendWithBLOBs> queryscenicSpotContent(Map<String, Object> search);

    /**
     * 景点上传图片
     * @param search
     * @return
     */
    List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastBySearch(Map<String, Object> search);


    List<SysScenicSpotBroadcastExtendWithBLOBs> qiliangxiugaichaxun(Long spotId);

    List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastId(Long broadcastId);

    SysScenicSpotBroadcastExtend getBroadcastIdAndTypeByExtend(Long broadcastId, String type);


}