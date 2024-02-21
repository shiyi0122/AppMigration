package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotExcel;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotFilesExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotWithBlogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysScenicSpotMapper {
    int deleteByPrimaryKey(Long scenicSpotId);

    int insert(SysScenicSpot record);

    int insertSelective(SysScenicSpot record);

    SysScenicSpot selectByPrimaryKey(Long scenicSpotId);

    int updateByPrimaryKeySelective(SysScenicSpot record);

    int updateByPrimaryKey(SysScenicSpot record);

    List<SysScenicSpot> queryScenicSpotList();

    List<SysScenicSpot> selectBySearch(HashMap<String, Object> search);

    SysScenicSpot spotDetails(String spotId);

    /**
     * 查询景区排行
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    List<SysScenicSpot> queryScenicSpotRankingList(Map<String, Object> search);

    List<SysScenicSpot> getScenicSpotAll(HashMap<String, Object> search);

    List<SysScenicSpotWithBlogs> adminSysScenicSpotList(Map<String, Object> search);


    List<SysScenicSpot> adminSysScenicSpotFilesList(Map<String, Object> search);

    /**
     * 景区下拉选
     * @return
     */
    List<SysScenicSpot> scenicSpotDrop();


    /**
     * 导出
     * @param search
     * @return
     */
    List<SysScenicSpotFilesExcel> getOrderVoExcelPoi(Map<String, Object> search);


    /**
     * 景区档案导出
     * @param search
     * @return
     */
    List<SysScenicSpotExcel> uploadExcelSpot(Map<String, Object> search);

    /**
     * 景区搜索用
     * @param search
     * @return
     */
    List<SysScenicSpot> selectBySearchNew(HashMap<String, Object> search);

    SysScenicSpot selectBySpotName(String scenicSpotName);

    List<SysScenicSpot> getRecommendSpot(String type);

    SysScenicSpot selectBySpotLikeName(String placeOfOwnership);

    SysScenicSpot selectByIdAndName(Long scenicSpotId, String scenicSpotName);

    List<SysScenicSpot> getRecommendSpotNew();


}