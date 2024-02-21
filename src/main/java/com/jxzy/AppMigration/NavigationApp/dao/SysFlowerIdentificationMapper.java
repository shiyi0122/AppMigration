package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysFlowerIdentification;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/19 16:41
 */


public interface SysFlowerIdentificationMapper {


    List<SysFlowerIdentification> list(Map<String, Object> search);


}
