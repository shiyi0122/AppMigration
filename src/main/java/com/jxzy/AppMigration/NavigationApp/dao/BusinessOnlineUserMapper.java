package com.jxzy.AppMigration.NavigationApp.dao;


import com.jxzy.AppMigration.NavigationApp.entity.BusinessOnLineUser;
import org.apache.ibatis.annotations.Param;
import org.checkerframework.checker.optional.qual.Present;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/5/19 14:05
 */
public interface BusinessOnlineUserMapper {

    int  insertSelective(BusinessOnLineUser businessOnLineUser);

    Long deleteOnlineUser(@Param("robotCode") String robotCode, @Param("userId") Long userId);

    List<BusinessOnLineUser> selectByRobotCode(@Param("robotCode") String robotCode, @Param("type")String type);

}
