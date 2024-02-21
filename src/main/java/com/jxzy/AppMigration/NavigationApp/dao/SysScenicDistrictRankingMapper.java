package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicDistrictRanking;

import java.util.Map;

public interface SysScenicDistrictRankingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysScenicDistrictRanking record);

    int insertSelective(SysScenicDistrictRanking record);

    SysScenicDistrictRanking selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysScenicDistrictRanking record);

    int updateByPrimaryKey(SysScenicDistrictRanking record);

    /**
     * 查询景点排行
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysScenicDistrictRanking
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    SysScenicDistrictRanking bestRanking(Map<String, Object> search);

    /**
     * 清空排行数据
     * @return
     */
    int updateRanking();

}