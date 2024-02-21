package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysStrategy;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyLike;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/12 16:54
 */
public interface SysStrategyLikeMapper {
    int insert(SysStrategyLike strategyLike);

    int delete(Long id, Long userId);

    int selectStrategyNumber(Long id, String type);


    Integer selectUidByLikeNumber(String uid);

    List<SysStrategy> myLike(String uid, String type);

    Integer getUserIsLike(String uid, Long id);

}
