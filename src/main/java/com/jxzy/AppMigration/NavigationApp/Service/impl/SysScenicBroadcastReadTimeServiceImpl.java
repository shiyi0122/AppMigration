package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicBroadcastReadTimeService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicBroadcastReadTimeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicBroadcastReadTime;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2022/10/1 8:53
 */
@Service
public class SysScenicBroadcastReadTimeServiceImpl implements SysScenicBroadcastReadTimeService {

    @Autowired
    SysScenicBroadcastReadTimeMapper sysScenicBroadcastReadTimeMapper;


    @Override
    public int addBroadcastReadTime(SysScenicBroadcastReadTime sysScenicBroadcastReadTime) {

        sysScenicBroadcastReadTime.setId(IdUtils.getSeqId());
        sysScenicBroadcastReadTime.setCreateDate(DateUtil.currentDateTime());
        int i = sysScenicBroadcastReadTimeMapper.insertSelective(sysScenicBroadcastReadTime);
        return i;

    }


    @Override
    public int delBroadcastReadTime(Long userId, Long broadcastId) {

       int i = sysScenicBroadcastReadTimeMapper.delBroadcastReadTime(userId,broadcastId);

       return i;
    }


    public int getBroadcastRealTimePeople(Long broadcastId){

        int i =  sysScenicBroadcastReadTimeMapper.getBroadcastRealTimePeople(broadcastId);

        return i;

    }



}
