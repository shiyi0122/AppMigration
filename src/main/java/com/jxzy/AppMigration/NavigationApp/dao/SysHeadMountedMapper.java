package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysHeadMounted;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/25 11:19
 */
public interface SysHeadMountedMapper {

    List<SysHeadMounted> getSysHeadMountedList(Map<String, Object> search);


    int editSysHeadMountedState(Long id, String state);

    int insertSelective(SysHeadMounted sysHeadMounted);


    int updateSelective(SysHeadMounted sysHeadMounted);


    int deleteByPrimaryKey(Long id);

}
