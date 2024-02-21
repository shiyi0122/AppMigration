package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotHeatService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotHeatMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotHeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class SysScenicSpotHeatServiceImpl implements SysScenicSpotHeatService {
    @Autowired
    private SysScenicSpotHeatMapper sysScenicSpotHeatMapper;
    /**
     * 查询最佳人气榜数据
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotHeatService
     * @author: qushaobei
     * @date: 2022/8/2 0002
     */
    public SysScenicSpotHeat querybestPopularity(Map<String, Object> search) {
        return sysScenicSpotHeatMapper.querybestPopularity(search);
    }

    /**
     * 创建景区人气榜数据
     * @param: heat
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/2 0002
     */
    public int insetScenicSpotHeat(SysScenicSpotHeat heat) {
        return sysScenicSpotHeatMapper.insertSelective(heat);
    }

    /**
     * 更新当日和累计人气值
     * @param: heat
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/2 0002
     */
    public int updateScenicSpotHeat(SysScenicSpotHeat heat) {
        return sysScenicSpotHeatMapper.updateByPrimaryKeySelective(heat);
    }

    /**
     *
     * 定时清空热度
     * @return
     */
    @Override
    public int updateHeat() {

       int i = sysScenicSpotHeatMapper.updateHeat();

        return  i;
    }
}
