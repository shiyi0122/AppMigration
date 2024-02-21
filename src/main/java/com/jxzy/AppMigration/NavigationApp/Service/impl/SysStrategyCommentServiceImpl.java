package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyCollectionService;
import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyCommentService;
import com.jxzy.AppMigration.NavigationApp.dao.SysStrategyCommentMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyComment;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2023/1/12 16:51
 * 游记，攻略，广场，，评论
 */
@Service
public class SysStrategyCommentServiceImpl implements SysStrategyCommentService {

    @Autowired
    SysStrategyCommentMapper sysStrategyCommentMapper;

    @Override
    public int insert(SysStrategyComment strategyComment) {

        strategyComment.setId(IdUtils.getSeqId());
        strategyComment.setCreateTime(DateUtil.currentDateTime());
        strategyComment.setUpdateTime(DateUtil.currentDateTime());

        int i = sysStrategyCommentMapper.insert(strategyComment);

        return i;
    }

    @Override
    public int delete(Long id, Long userId) {

        int i = sysStrategyCommentMapper.delete(id,userId);
        return i;
    }
}
