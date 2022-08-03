package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotWifiDataService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotWifiDataMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotWifiData;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysScenicSpotWifiDataServiceImpl implements SysScenicSpotWifiDataService {

    @Autowired
    private SysScenicSpotWifiDataMapper sysScenicSpotWifiDataMapper;


    /**
     * 插入WIFI探针数据
     * @param: data
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/3/18 0018
     */
    public int wifiData(SysScenicSpotWifiData data) {
        data.setWifiId(IdUtils.getSeqId());
        data.setCreateDate(DateUtil.currentDateTime());
        data.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotWifiDataMapper.insertSelective(data);
    }
}
