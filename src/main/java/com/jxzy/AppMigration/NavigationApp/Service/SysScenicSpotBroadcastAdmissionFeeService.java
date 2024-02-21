package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/6 10:21
 */
public interface SysScenicSpotBroadcastAdmissionFeeService {

    int addBroadcastAdmissionFee(SysScenicSpotBroadcastAdmissionFee sysScenicSpotBroadcastAdmissionFee);


    PageDataResult spotBroadcastAdmissionFeeList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int editBroadcastAdmissionFee(SysScenicSpotBroadcastAdmissionFee sysScenicSpotBroadcastAdmissionFee);

    int delBroadcastAdmissionFee(Long id);

}
