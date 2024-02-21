package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotHeat;

import java.util.Map;

public interface SysScenicSpotHeatService {
    /**
     * 查询最佳人气榜数据
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotHeatService
     * @author: qushaobei
     * @date: 2022/8/2 0002
     */
    SysScenicSpotHeat querybestPopularity(Map<String, Object> search);

    /**
     * 创建景区人气榜数据
     * @param: heat
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/2 0002
     */
    int insetScenicSpotHeat(SysScenicSpotHeat heat);

    /**
     * 更新当日和累计人气值
     * @param: heat
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/2 0002
     */
    int updateScenicSpotHeat(SysScenicSpotHeat heat);


    /**
     * 定时清空热度
     *
     * @return
     */
    int updateHeat();



}
