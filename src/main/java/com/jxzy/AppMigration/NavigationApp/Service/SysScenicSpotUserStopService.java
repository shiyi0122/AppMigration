package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserStop;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/17 11:08
 */
public interface SysScenicSpotUserStopService {

    int addSpotUserStop(SysScenicSpotUserStop sysScenicSpotUserStop);

    List<SysScenicSpotUserStop> getSpotUserStop(String uid, String spotId);

    PageDataResult getSysScenicSpotUserStopList(Integer pageNum,Integer pageSize,Map<String, Object> search);

    List<SysScenicSpotUserStop> getSysScenicSpotUserStopFootprint(Long spotId, Long userId);

    int delSysScenicSpotUserStopFootprint(Long id);


}
