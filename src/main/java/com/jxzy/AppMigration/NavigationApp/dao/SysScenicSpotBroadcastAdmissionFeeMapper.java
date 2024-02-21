package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastAdmissionFee;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/4 15:11
 */
public interface SysScenicSpotBroadcastAdmissionFeeMapper {

    List<SysScenicSpotBroadcastAdmissionFee> selectByBroadcastId(Long broadcastId);

    int insertSelective(SysScenicSpotBroadcastAdmissionFee sysScenicSpotBroadcastAdmissionFee);


    List<SysScenicSpotBroadcastAdmissionFee> selectBySearch(Map<String, Object> search);

    int updateSelective(SysScenicSpotBroadcastAdmissionFee sysScenicSpotBroadcastAdmissionFee);

    int deleteByPrimaryKey(Long id);


}
