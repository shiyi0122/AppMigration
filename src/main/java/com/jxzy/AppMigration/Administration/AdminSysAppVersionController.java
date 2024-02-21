package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysAppVersionService;
import com.jxzy.AppMigration.NavigationApp.entity.SysAppVersion;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import com.jxzy.AppMigration.NavigationApp.util.VersionCompareUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/20 17:32
 */

@Api(tags = "后台管理-app版本管理")
@RestController
@RequestMapping("adminSysAppVersion")
@CrossOrigin
public class AdminSysAppVersionController {

    @Autowired
    SysAppVersionService sysAppVersionService;


    @ApiOperation("管理后台app版本添加")
    @PostMapping("addAdminSysAppVersion")
    @ResponseBody
    public ReturnModel addAdminSysAppVersion(@RequestBody SysAppVersion sysAppVersion) {

        ReturnModel returnModel = new ReturnModel();

        //查询最新版本号
        SysAppVersion sysAppVersionN = sysAppVersionService.getAppNumberNew(sysAppVersion.getPackageType());

        if (!StringUtils.isEmpty(sysAppVersionN)) {
            int i = VersionCompareUtil.compareVersion(sysAppVersion.getAppVersion(), sysAppVersionN.getAppVersion());
            //i == 0 版本相同  i == 1 当前版本小于输入版本  i == -1 当前版本大于输入版本
            if (i == 1) {
                int a = sysAppVersionService.addAdminSysAppVersion(sysAppVersion);
                if (a == 1) {
                    returnModel.setData("");
                    returnModel.setMsg("添加成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                } else {
                    returnModel.setData("");
                    returnModel.setMsg("添加失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            } else {
                returnModel.setData("");
                returnModel.setMsg("输入版本号小于等于当前版本号！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }

        } else {

            int i = sysAppVersionService.addAdminSysAppVersion(sysAppVersion);
            if (i > 0) {
                returnModel.setData(i);
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("添加成功！");
            } else {
                returnModel.setData(i);
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg("添加失败！");
            }
            return returnModel;
        }

    }

    @ApiOperation("查询最新版本的apk")
    @GetMapping("getAppNumberNew")
    @ResponseBody
    public ReturnModel getAppNumberNew(SysAppVersion sysAppVersion) {
        ReturnModel returnModel = new ReturnModel();
        SysAppVersion sysAppVersionN = sysAppVersionService.getAppNumberNew(sysAppVersion.getPackageType());
        returnModel.setData(sysAppVersionN);
        returnModel.setMsg(" 查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }


    @ApiOperation("管理后台app版本添加(新)")
    @PostMapping("addAdminSysAppVersionNew")
    @ResponseBody
    public ReturnModel addAdminSysAppVersionNew(@RequestPart("file") MultipartFile file,SysAppVersion sysAppVersion) {

        ReturnModel returnModel = new ReturnModel();

        //查询最新版本号
        SysAppVersion sysAppVersionN = sysAppVersionService.getAppNumberNew(sysAppVersion.getPackageType());

        if (!StringUtils.isEmpty(sysAppVersionN)) {
            int i = VersionCompareUtil.compareVersion(sysAppVersion.getAppVersion(), sysAppVersionN.getAppVersion());
            //i == 0 版本相同  i == 1 当前版本小于输入版本  i == -1 当前版本大于输入版本
            if (i == 1) {
                int a = sysAppVersionService.addAdminSysAppVersionNew(file, sysAppVersion);
                if (a == 1) {
                    returnModel.setData("");
                    returnModel.setMsg("添加成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                } else if (a==2){
                    returnModel.setData("");
                    returnModel.setMsg("文件格式只支持apk！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else {
                    returnModel.setData("");
                    returnModel.setMsg("添加失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            } else {
                returnModel.setData("");
                returnModel.setMsg("输入版本号小于等于当前版本号！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }

        } else {

            int i = sysAppVersionService.addAdminSysAppVersionNew(file, sysAppVersion);
            if (i > 0) {
                returnModel.setData(i);
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("添加成功！");
            } else {
                returnModel.setData(i);
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg("添加失败！");
            }
            return returnModel;
        }

    }


    @ApiOperation("管理后台app版本编辑")
    @PostMapping("editAdminSysAppVersion")
    @ResponseBody
    public ReturnModel editAdminSysAppVersion(@RequestBody SysAppVersion sysAppVersion) {

        ReturnModel returnModel = new ReturnModel();


        int a = sysAppVersionService.editAdminSysAppVersion(sysAppVersion);
        if (a == 1) {
            returnModel.setData("");
            returnModel.setMsg("修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("管理后台app版本删除")
    @GetMapping("delAdminSysAppVersion")
    @ResponseBody
    public ReturnModel delAdminSysAppVersion(Long id) {

        ReturnModel returnModel = new ReturnModel();


        int a = sysAppVersionService.delAdminSysAppVersion(id);
        if (a == 1) {
            returnModel.setData("");
            returnModel.setMsg("删除成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("管理后台app版本列表")
    @GetMapping("getAdminSysAppVersionList")
    public PageDataResult getAdminSysAppVersionList(PageDTO pageDTO) {

        Map<String, Object> search = new HashMap<>();

        if (!StringUtils.isEmpty(pageDTO.getType())) {
            search.put("type", pageDTO.getType());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotType())) {
            search.put("spotType", pageDTO.getSpotType());
        }

        PageDataResult pageDataResult = sysAppVersionService.getAdminSysAppVersionList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);

        return pageDataResult;
    }

    @ApiOperation("管理后台app版本启用禁用")
    @GetMapping("editAdminSysAppVersionEnableDisable")
    public ReturnModel editAdminSysAppVersionEnableDisable(Long id, String appType) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysAppVersionService.editAdminSysAppVersionEnableDisable(id, appType);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }


}





