package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFee;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/4 14:17
 */
public interface SysScenicSpotAdmissionFeeMapper {
    List<SysScenicSpotAdmissionFee> selectBySpotId(Long scenicSpotId);

    int insertSelective(SysScenicSpotAdmissionFee sysScenicSpotAdmissionFee);

    List<SysScenicSpotAdmissionFee> selectBySearch(Map<String, Object> search);

    int updateSelective(SysScenicSpotAdmissionFee sysScenicSpotAdmissionFee);

    int deleteByPrimaryKey(Long id);
}
