package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysScenicSpotServiceImpl implements SysScenicSpotService {

    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;


    @Override
    public List<SysScenicSpot> queryScenicSpotList() {
        return sysScenicSpotMapper.queryScenicSpotList();
    }
}
