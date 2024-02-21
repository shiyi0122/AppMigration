package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFeeNotice;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/8 10:13
 */
public interface SysScenicSpotAdmissionFeeNoticeMapper {

    int insertSelective(SysScenicSpotAdmissionFeeNotice sysScenicSpotAdmissionFeeNotice);


    int updateSelective(SysScenicSpotAdmissionFeeNotice sysScenicSpotAdmissionFeeNotice);


    int deleteByPrimaryKey(Long id);

    List<SysScenicSpotAdmissionFeeNotice> selectBySearch(Map<Object, Object> search);


}
