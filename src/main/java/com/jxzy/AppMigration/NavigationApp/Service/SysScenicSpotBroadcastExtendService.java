package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtend;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/11/2 16:29
 */
public interface SysScenicSpotBroadcastExtendService {
    SysScenicSpotBroadcastExtendWithBLOBs getBroadcastIdByBraodcastImg(Long scenicDistrictId);


}
