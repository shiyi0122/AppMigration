package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserFans;

/**
 * @Author zhang
 * @Date 2023/1/12 18:10
 */
public interface SysUserFansMapper {


    Integer selectIsFans(String uid, Long userId);


    int insertSelective(SysUserFans sysUserFans);


    Integer selectByFansNumber(String uid);

    Integer selectByFollowNumber(String uid);

    int cancelSysUserFans(Long fansUserId, Long coverFansUserId);

}
