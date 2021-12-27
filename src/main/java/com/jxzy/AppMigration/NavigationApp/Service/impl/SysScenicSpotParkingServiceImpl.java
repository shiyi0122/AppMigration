package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotParkingService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotParkingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotParkingServiceImpl implements SysScenicSpotParkingService {

    @Autowired
    private SysScenicSpotParkingMapper sysScenicSpotParkingMapper;

    /**
     * 获取景区停靠点列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/27 0027
     */
    public List<SysScenicSpotBroadcast> getScenicSpotParkingList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotParkingMapper.getScenicSpotParkingList(search);
    }
}
