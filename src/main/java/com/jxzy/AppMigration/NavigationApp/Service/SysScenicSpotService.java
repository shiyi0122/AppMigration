package com.jxzy.AppMigration.NavigationApp.Service;


import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;

public interface SysScenicSpotService {


    List<SysScenicSpot> queryScenicSpotList();

    PageDataResult currentCity(String lng,String lat,String cityName, Integer sort, Integer pageNum, Integer pageSize);

    PageDataResult searchSpot(PageDTO pageDTO);

    SysScenicSpot spotDetails(String spotId, String lat, String lng);

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
