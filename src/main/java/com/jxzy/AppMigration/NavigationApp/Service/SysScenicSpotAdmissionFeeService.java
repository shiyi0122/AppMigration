package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/6 10:02
 */
public interface SysScenicSpotAdmissionFeeService {
    int addSpotAdmissionFee(SysScenicSpotAdmissionFee sysScenicSpotAdmissionFee);


    PageDataResult spotAdmissionFeeList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int editSpotAdmissionFee(SysScenicSpotAdmissionFee sysScenicSpotAdmissionFee);


    int delSpotAdmissionFee(Long id);


}
