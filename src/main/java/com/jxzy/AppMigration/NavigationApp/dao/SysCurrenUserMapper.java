package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysCurrenUser;

/**
 * @Author zhang
 * @Date 2023/8/8 15:40
 */
public interface SysCurrenUserMapper {


    SysCurrenUser selectPhoneByUser(String phone);

    int updateByPrimaryKeySelective(SysCurrenUser sysCurrenUser);

    SysCurrenUser selectUserIdByUser(Long depositUserId);


}
