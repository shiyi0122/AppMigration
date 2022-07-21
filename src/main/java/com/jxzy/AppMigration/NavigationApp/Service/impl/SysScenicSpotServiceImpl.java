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

    /**
     * 根据景区ID查询景区数据
     * @param: parseLong
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    public SysScenicSpot queryScenicSpotData(long scenicSpotId) {
        return sysScenicSpotMapper.selectByPrimaryKey(scenicSpotId);
    }

    /**
     * 更新景区热度
     * @param: scenicSpot
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    public int updateScenicSpotHeat(SysScenicSpot scenicSpot) {
        long heat = scenicSpot.getHeat() + 1;//自增热度
        scenicSpot.setHeat(heat);
        return sysScenicSpotMapper.updateByPrimaryKeySelective(scenicSpot);
    }
}
