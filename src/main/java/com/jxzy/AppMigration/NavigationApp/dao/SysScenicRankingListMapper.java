package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicRankingList;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/3 18:43
 */
public interface SysScenicRankingListMapper {

    List<SysScenicRankingList> searchRankingList(Map<String, Object> search);


    int insertSelective(SysScenicRankingList sysScenicRankingList);


}
