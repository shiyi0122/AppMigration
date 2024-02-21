package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastExtendService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastExtendMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtend;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/11/2 16:29
 */
@Service
public class SysScenicSpotBroadcastExtendServiceImpl implements SysScenicSpotBroadcastExtendService {

    @Autowired
    SysScenicSpotBroadcastExtendMapper sysScenicSpotBroadcastExtendMapper;


    @Override
    public SysScenicSpotBroadcastExtendWithBLOBs getBroadcastIdByBraodcastImg(Long scenicDistrictId) {

        SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs1 = new SysScenicSpotBroadcastExtendWithBLOBs();
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastId = sysScenicSpotBroadcastExtendMapper.getBroadcastId(scenicDistrictId);

        for (SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs : broadcastId) {

            if (!StringUtils.isEmpty(sysScenicSpotBroadcastExtendWithBLOBs.getPictureUrl())){

                sysScenicSpotBroadcastExtendWithBLOBs1 = sysScenicSpotBroadcastExtendWithBLOBs;
                break;

            }
        }

        return sysScenicSpotBroadcastExtendWithBLOBs1;


    }
}
