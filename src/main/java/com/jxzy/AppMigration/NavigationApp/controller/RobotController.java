package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysRobotService;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobot;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PublicUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "机器人端接口")
@RestController
@RequestMapping("robot")
public class RobotController extends PublicUtil {
    @Autowired
    private SysRobotService sysRobotService;

    @ApiOperation("查询机器人列表")
    @GetMapping("/queryRobotList")
    public ReturnModel queryCityAndScenicSpotLists(@ApiParam(name="robotCode",value="机器人编号",required=false)String robotCode){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            search.put("robotCode",robotCode);
           List<SysRobot> robot =  sysRobotService.queryRobotList(search);
            returnModel.setData(robot);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("queryScenicSpotLists",e);
            returnModel.setData("");
            returnModel.setMsg("获取景区列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
}
