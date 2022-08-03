package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysGuideAppNewsMapper {
    int deleteByPrimaryKey(Long guideId);

    int insert(SysGuideAppNews record);

    int insertSelective(SysGuideAppNews record);

    SysGuideAppNews selectByPrimaryKey(Long guideId);

    int updateByPrimaryKeySelective(SysGuideAppNews record);

    int updateByPrimaryKeyWithBLOBs(SysGuideAppNews record);

    int updateByPrimaryKey(SysGuideAppNews record);

    /**
     * 查询消息列表
     * @param: 
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    List<SysGuideAppNews> queryGuideAppNewsListsLimit(Map<String, Object> search);
    /**
     * 批量更新消息列表
     * @param: type
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/3 0003
     */
    int updateByBatchSelective(SysGuideAppNews record);

    List<SysGuideAppNews> selectBySearch(HashMap<String, Object> search);


    List<String> selectIdBySearch(HashMap<String, Object> search);

}