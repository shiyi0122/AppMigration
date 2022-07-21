package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;

import java.util.List;

public interface SysScenicSpotService {


    List<SysScenicSpot> queryScenicSpotList();

    /**
     * 根据景区ID查询景区数据
     * @param: parseLong
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    SysScenicSpot queryScenicSpotData(long scenicSpotId);

    /**
     * 更新景区热度
     * @param: scenicSpot
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    int updateScenicSpotHeat(SysScenicSpot scenicSpot);
}
