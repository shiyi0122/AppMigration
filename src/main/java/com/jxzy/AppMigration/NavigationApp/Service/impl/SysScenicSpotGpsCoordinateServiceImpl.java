package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotGpsCoordinateService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotGpsCoordinateMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinate;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs;
import com.jxzy.AppMigration.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    /**
     * 根据景区id查询围栏
     * @param coordinateScenicSpotId
     * @return
     */
    @Override
    public SysScenicSpotGpsCoordinateWithBLOBs getSpotIdByGpsCoordinate(Long coordinateScenicSpotId) {

        SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs = sysScenicSpotGpsCoordinateMapper.selectBySpotId(coordinateScenicSpotId);
        return sysScenicSpotGpsCoordinateWithBLOBs;

    }

    /**
     * 定时任务中添加围栏
     * @param sysScenicSpotGpsCoordinateWithBLOBs
     * @return
     */
    @Override
    public int insert(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs) {

        sysScenicSpotGpsCoordinateWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotGpsCoordinateWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotGpsCoordinateMapper.insertSelective(sysScenicSpotGpsCoordinateWithBLOBs);
        return i;
    }

    /**
     * 根据id查询
     * @param coordinateId
     * @return
     */
    @Override
    public SysScenicSpotGpsCoordinateWithBLOBs getSpotGpsCoordinateId(Long coordinateId) {

        SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs = sysScenicSpotGpsCoordinateMapper.selectByPrimaryKey(coordinateId);
        return sysScenicSpotGpsCoordinateWithBLOBs;
    }

    /**
     * 编辑围栏信息
     * @param sysScenicSpotGpsCoordinateWithBLOBs
     * @return
     */
    @Override
    public int edit(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs) {

        int i = sysScenicSpotGpsCoordinateMapper.updateByPrimaryKeySelective(sysScenicSpotGpsCoordinateWithBLOBs);
        return i;
    }
}
