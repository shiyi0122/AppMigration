package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserMapSignRemarks;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/10 19:43
 */
public interface SysUserMapSignRemarksService {

    int addUserMapSignRemarks(SysUserMapSignRemarks sysUserMapSignRemarks);


    PageDataResult getUserMapSignRemarksList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    /**
     * 后台查询图钉
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult adminUserMapSignRemarksList(Integer pageNum, Integer pageSize, HashMap<String, Object> search);


}
