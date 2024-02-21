package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyCollectionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysStrategyCollectionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyCollection;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

/**
 * @Author zhang
 * @Date 2023/1/12 16:49
 * 游记，攻略，广场收藏
 */
@Service
public class SysStrategyCollectionServiceImpl implements SysStrategyCollectionService {

    @Autowired
    SysStrategyCollectionMapper sysStrategyCollectionMapper;

    @Override
    public int insert(SysStrategyCollection strategyCollection) {

        Integer userIsCollection = sysStrategyCollectionMapper.getUserIsCollection(strategyCollection.getUserId().toString(), strategyCollection.getStrategyId());
        if (userIsCollection>0){
            return 0;
        }

        strategyCollection.setId(IdUtils.getSeqId());
        strategyCollection.setCreateTime(DateUtil.currentDateTime());
        strategyCollection.setUpdateTime(DateUtil.currentDateTime());
        int i =  sysStrategyCollectionMapper.insert(strategyCollection);
        return i;
    }

    @Override
    public int delete(Long id,Long userId) {

        int i = sysStrategyCollectionMapper.delete(id,userId);

        return i;
    }
}
