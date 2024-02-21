package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotMapResService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotMapResMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotMapRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2022/8/29 18:36
 */
@Service
public class SysScenicSpotMapResServiceImpl implements SysScenicSpotMapResService {

    @Autowired
    SysScenicSpotMapResMapper sysScenicSpotMapResMapper;

    @Override
    public SysScenicSpotMapRes getScenicSpotMapRes(Long spotId) {

        SysScenicSpotMapRes sysScenicSpotMapRes = sysScenicSpotMapResMapper.getScenicSpotMapRes(spotId);

        return sysScenicSpotMapRes;
    }
}
