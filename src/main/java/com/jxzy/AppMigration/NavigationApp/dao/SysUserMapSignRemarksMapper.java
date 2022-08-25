package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserMapSignRemarks;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/10 19:44
 */

public interface SysUserMapSignRemarksMapper {
    int addUserMapSignRemarks(SysUserMapSignRemarks sysUserMapSignRemarks);

    List<SysUserMapSignRemarks> getUserMapSignRemarksList(Map<String, Object> search);

}
