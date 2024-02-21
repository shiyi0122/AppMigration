package com.jxzy.AppMigration.NavigationApp.dao;


import com.jxzy.AppMigration.NavigationApp.entity.BusinessUserLiveDeviceLog;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/16 19:27
 */
public interface BusinessUserLiveDeviceLogMapper {
    int  insertSelective(BusinessUserLiveDeviceLog businessUserLiveDeviceLogN);


    BusinessUserLiveDeviceLog selectBySearchOne(Map<String, Object> search);


    int updateSelective(BusinessUserLiveDeviceLog businessUserLiveDeviceLog);


    /**
     * 支付成功，修改支付状态
     * @param liveId
     * @return
     */
    int  editIfPayState(String liveId);

    BusinessUserLiveDeviceLog selectByPrimaryKey(String liveId);

    List<BusinessUserLiveDeviceLog> selectBySearchList(Map<String, String> search);


}
