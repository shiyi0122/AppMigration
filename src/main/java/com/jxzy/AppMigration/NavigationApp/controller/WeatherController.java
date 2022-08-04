package com.jxzy.AppMigration.NavigationApp.controller;


import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import com.jxzy.AppMigration.NavigationApp.util.WeatherUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@Api(tags = "天气预报")
@RestController
@RequestMapping("weather")
public class WeatherController extends PublicUtil {

    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;

    @ApiOperation("获取未来五天天气情况")
    @GetMapping("/weatherList")
    @ResponseBody
    public ReturnModel queryUserHelpList(@ApiParam(name="longinTokenId",value="登录令牌,状态码202为登录失效",required=true)String longinTokenId,
                                         @ApiParam(name="cityName",value="城市",required=true)String cityName) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
            if (user == null) {
                returnModel.setData("");
                returnModel.setMsg("令牌失效，请重新登录！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
            String cityNames = WeatherUtil.GetWeatherData(cityName);
            returnModel.setData(cityNames);
            returnModel.setMsg("成功获取使用帮助列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("queryUserHelpList", e);
            returnModel.setData("");
            returnModel.setMsg("获取失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
}
