package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotGpsCoordinateService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotGpsCoordinateMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotGpsCoordinateServiceImpl implements SysScenicSpotGpsCoordinateService {
    @Autowired
    private SysScenicSpotGpsCoordinateMapper sysScenicSpotGpsCoordinateMapper;

    /**
     * 查询景区电子围栏
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    public SysScenicSpotGpsCoordinateWithBLOBs queryScenicSpotElectronicFence(Map<String, Object> search) {
        return sysScenicSpotGpsCoordinateMapper.queryScenicSpotElectronicFence(search);
    }

    /**
     * 查询景区所有电子围栏
     * @param:
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs>
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public List<SysScenicSpotGpsCoordinateWithBLOBs> queryLocationScenicSpot() {
        return sysScenicSpotGpsCoordinateMapper.queryLocationScenicSpot();
    }
}
