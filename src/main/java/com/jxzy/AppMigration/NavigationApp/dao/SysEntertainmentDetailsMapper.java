package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainmentDetails;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/24 11:23
 */
public interface SysEntertainmentDetailsMapper {


    List<SysEntertainmentDetails> getSysEntertainmentIdDetails(Map<String, Object> search);


}
