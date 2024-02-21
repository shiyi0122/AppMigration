package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.WeatherService;
import com.jxzy.AppMigration.NavigationApp.dao.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2022/10/8 17:48
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherMapper weatherMapper;

    /**
     * 根据城市id，获取城市编号
     * @param cityName
     * @return
     */
    @Override
    public String getDistrictId(String cityName) {

       String districtId =  weatherMapper.getDistrictId(cityName);

       return districtId;
    }
}
