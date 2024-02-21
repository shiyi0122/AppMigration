package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumCollectionBroadcastService;
import com.jxzy.AppMigration.NavigationApp.dao.SysMuseumCollectionBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollectionBroadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2023/6/21 13:42
 */
@Service
public class SysMuseumCollectionBroadcastServiceImpl extends ServiceImpl<SysMuseumCollectionBroadcastMapper, SysMuseumCollectionBroadcast> implements SysMuseumCollectionBroadcastService {


   @Autowired
    SysMuseumCollectionBroadcastMapper sysMuseumCollectionBroadcastMapper;


}
