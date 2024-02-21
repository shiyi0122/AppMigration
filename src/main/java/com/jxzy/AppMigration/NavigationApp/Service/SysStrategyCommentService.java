package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyComment;

/**
 * @Author zhang
 * @Date 2023/1/12 16:50
 * 游记，攻略，广场，，评论
 */
public interface SysStrategyCommentService {

    int insert(SysStrategyComment strategyComment);


    int delete(Long id, Long userId);

}
