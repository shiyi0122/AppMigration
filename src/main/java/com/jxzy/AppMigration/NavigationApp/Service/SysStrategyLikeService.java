package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyLike;

/**
 * @Author zhang
 * @Date 2023/1/12 16:52
 * 游记，攻略，广场 ， 喜欢
 */
public interface SysStrategyLikeService {
    int insert(SysStrategyLike strategyLike);

    int delete(Long id, Long userId);

}
