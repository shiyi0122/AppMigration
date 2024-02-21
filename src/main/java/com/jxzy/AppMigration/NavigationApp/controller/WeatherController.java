package com.jxzy.AppMigration.NavigationApp.controller;


import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.Service.WeatherService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "天气预报")
@RestController
@CrossOrigin
@RequestMapping("weather")
public class WeatherController extends PublicUtil {

    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;
    @Autowired
    private WeatherService weatherService;

    @ApiOperation("获取未来五天天气情况")
    @GetMapping("/weatherList")
    @ResponseBody
    public ReturnModel queryUserHelpList(@ApiParam(name="cityName",value="城市",required=true)String cityName,
                                    @ApiParam(name="baseDTO",value="登录令牌",required=true)BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();

//        boolean shi  = cityName.contains("市");
//        if (shi){
//            String[] city = cityName.split("市");
//            cityName = city[0];
//        }else{
//            String[] province = cityName.split("省");
//            cityName = province[0];
//        }

        String  districtId =  weatherService.getDistrictId(cityName);

        Map<String, Object> regionWeather = WeatherBDUtil.getRegionWeather(districtId);

        returnModel.setData(regionWeather);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return  returnModel;

    }
}
