package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyLikeService;
import com.jxzy.AppMigration.NavigationApp.dao.SysStrategyLikeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyLike;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2023/1/12 16:53
 * 游记，攻略，广场 ， 喜欢
 */
@Service
public class SysStrategyLikeServiceImpl implements SysStrategyLikeService {

    @Autowired
    SysStrategyLikeMapper sysStrategyLikeMapper;

    @Override
    public int insert(SysStrategyLike strategyLike) {

        Integer userIsLike = sysStrategyLikeMapper.getUserIsLike(strategyLike.getUserId().toString(), strategyLike.getStrategyId());
        if (userIsLike>0){
            return 0;
        }
        strategyLike.setId(IdUtils.getSeqId());
        strategyLike.setCreateTime(DateUtil.currentDateTime());
        strategyLike.setUpdateTime(DateUtil.currentDateTime());

        int i = sysStrategyLikeMapper.insert(strategyLike);
        return i ;
    }

    @Override
    public int delete(Long id, Long userId) {

       int i = sysStrategyLikeMapper.delete(id,userId);
       return i;
    }
}
