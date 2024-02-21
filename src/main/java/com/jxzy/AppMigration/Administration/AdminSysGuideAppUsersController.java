package com.jxzy.AppMigration.Administration;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/2 18:50
 */

@Api(tags = "后台管理-游小伴app用户管理相关接口")
@RestController
@RequestMapping("adminSysGuideAppUsers")
@CrossOrigin
public class AdminSysGuideAppUsersController {

    @Autowired
    SysGuideAppUsersService sysGuideAppUsersService;

    @ApiOperation("app用户列表查询")
    @GetMapping("/getSysGuideAppUsersList")
    @ResponseBody
    public PageDataResult getSysGuideAppUsersList(PageDTO pageDTO) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("phone",pageDTO.getPhone());
        pageDataResult = sysGuideAppUsersService.getSysGuideAppUsersList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getPhone(),search);
        return pageDataResult;
    }


    @ApiOperation("app用户列表下拉选")
    @GetMapping("/getSysGuideAppUsersDrop")
    @ResponseBody
    public ReturnModel getSysGuideAppUsersDrop() {

        ReturnModel returnModel = new ReturnModel();
        List<SysGuideAppUsers> list = sysGuideAppUsersService.getSysGuideAppUsersDrop();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return returnModel;

    }

    @ApiOperation("app用户删除")
    @GetMapping("/delSysGuideAppUsers")
    @ResponseBody
    public ReturnModel delSysGuideAppUsers(SysGuideAppUsers sysGuideAppUsers) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGuideAppUsersService.delSysGuideAppusers(sysGuideAppUsers.getUserId());

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
            return  returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
            return  returnModel;
        }

    }


}
