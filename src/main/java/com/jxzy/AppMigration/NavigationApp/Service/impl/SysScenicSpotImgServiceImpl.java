package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotImgService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotImgMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotImg;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2022/11/2 15:56
 */
@Service
public class SysScenicSpotImgServiceImpl implements SysScenicSpotImgService {
    @Autowired
    SysScenicSpotImgMapper sysScenicSpotImgMapper;

    /**
     * 根据景区id，获取img
     * @param scenicSpotId
     * @return
     */
    @Override
    public SysScenicSpotImg getSpotIdBySpotImg(Long scenicSpotId) {


        SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(scenicSpotId);
        return scenicSpotImgByScenicSpotId;

    }
}
