package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicDistrictRanking;

import java.util.Map;

public interface SysScenicDistrictRankingService {

    /**
     * 查询景点排行
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysScenicDistrictRanking
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    SysScenicDistrictRanking bestRanking(Map<String, Object> search);

    /**
     * 插入排行数据
     * @param: ranking
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    int insetbestRanking(SysScenicDistrictRanking ranking);

    /**
     * 更新排行数据
     * @param: rankings
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    int updatebestRanking(SysScenicDistrictRanking rankings);

    /**
     * 清空排行数据
     * @return
     */
    int updateRanking();


}
