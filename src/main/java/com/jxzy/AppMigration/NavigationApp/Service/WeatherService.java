package com.jxzy.AppMigration.NavigationApp.Service;

/**
 * @Author zhang
 * @Date 2022/10/8 17:48
 */
public interface WeatherService {
    /**
     * 根据城市名称查询区县编号
     * @param cityName
     * @return
     */
    String getDistrictId(String cityName);


}
