package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotGpsCoordinateService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/10/31 15:52
 * 景区围栏
 */
@Api(tags = "后台管理-景区围栏")
@RestController
@RequestMapping("AdminSpotGpsCoordinate")
@CrossOrigin
public class AdminSysScenicSpotGpsCoordinateController {

    @Autowired
    private SysScenicSpotGpsCoordinateService sysScenicSpotGpsCoordinateService;

    @ApiOperation("景区电子围栏")
    @GetMapping("/querySysScenicSpotGpsCoordinate")
    public ReturnModel querySysScenicSpotGpsCoordinate(@ApiParam(name="scenicSpotId",value="景区ID",required=true)String scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
//            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
//            if (user == null) {
//                returnModel.setData("");
//                returnModel.setMsg("令牌失效，请重新登录！");
//                returnModel.setState(Constant.LOGIN_FAILURE);
//                return returnModel;
//            }
            search.put("scenicSpotId",scenicSpotId);
            SysScenicSpotGpsCoordinateWithBLOBs coordinate = sysScenicSpotGpsCoordinateService.queryScenicSpotElectronicFence(search);
            returnModel.setData(coordinate);
            returnModel.setMsg("成功获取景区围栏！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
//            logger.info("queryScenicSpotElectronicFence",e);
            returnModel.setData("");
            returnModel.setMsg("失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }



}
