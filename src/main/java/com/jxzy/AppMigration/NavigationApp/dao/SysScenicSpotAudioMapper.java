package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysRobotMapRes;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAudio;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/9/3 16:49
 */
public interface SysScenicSpotAudioMapper {


    int insertSelective(SysScenicSpotAudio record);

    List<SysScenicSpotAudio> selectBySpotId(Long scenicSpotId);


    int updateSelective(SysScenicSpotAudio sysScenicSpotAudio);


}
