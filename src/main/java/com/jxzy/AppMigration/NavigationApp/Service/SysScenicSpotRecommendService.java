package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.Service.impl.SysScenicSpotRecommendServiceImpl;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommend;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/3 19:19
 */

public interface SysScenicSpotRecommendService {
    PageDataResult sysScenicSpotRecommendList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    PageDataResult getSpotRecommendList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int addSpotRecommend(SysScenicSpotRecommend sysScenicSpotRecommend);


    int editSpotRecommend(SysScenicSpotRecommend sysScenicSpotRecommend);


    int delSpotRecommend(Long id);
}
