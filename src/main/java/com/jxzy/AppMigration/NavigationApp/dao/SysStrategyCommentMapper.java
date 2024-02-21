package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyComment;

/**
 * @Author zhang
 * @Date 2023/1/12 16:52
 */
public interface SysStrategyCommentMapper {
    int insert(SysStrategyComment strategyComment);


    int delete(Long id, Long userId);

    Integer selectStrategyNumber(Long id, String type);


}
