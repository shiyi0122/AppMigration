package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFeeNotice;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/8 10:11
 */
public interface SysScenicSpotAdmissionFeeNoticeService {


    int addSpotAdmissionFeeNotice(SysScenicSpotAdmissionFeeNotice sysScenicSpotAdmissionFeeNotice);


    int editSpotAdmissionFeeNotice(SysScenicSpotAdmissionFeeNotice sysScenicSpotAdmissionFeeNotice);


    int delSpotAdmissionFeeNotice(Long id);

    PageDataResult getSpotAdmissionFeeNoticeList(Integer pageNum, Integer pageSize, Map<Object, Object> search);

}
