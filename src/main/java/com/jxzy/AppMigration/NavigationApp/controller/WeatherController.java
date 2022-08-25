package com.jxzy.AppMigration.NavigationApp.controller;


import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import com.jxzy.AppMigration.NavigationApp.util.WeatherUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "天气预报")
@RestController
@CrossOrigin
@RequestMapping("weather")
public class WeatherController extends PublicUtil {

    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;

    @ApiOperation("获取未来五天天气情况")
    @GetMapping("/weatherList")
    @ResponseBody
    public String queryUserHelpList(@ApiParam(name="cityName",value="城市",required=true)String cityName,
                                    @ApiParam(name="baseDTO",value="登录令牌",required=true)BaseDTO baseDTO) {
        ReturnModel returnModel = new ReturnModel();
        try {
//            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
//            if (user == null) {
//                return "false";
//            }
            String cityNames = WeatherUtil.GetWeatherData(cityName);
            return cityNames;
        } catch (Exception e) {
            logger.info("queryUserHelpList", e);
            return null;
        }
    }
}
