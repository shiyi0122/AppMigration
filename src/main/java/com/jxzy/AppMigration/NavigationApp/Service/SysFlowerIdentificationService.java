package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/19 16:40
 */
public interface SysFlowerIdentificationService {
    PageDataResult getSysFlowerIdentificationList(Integer pageNum, Integer pageSize, Map<String, Object> search);

}
