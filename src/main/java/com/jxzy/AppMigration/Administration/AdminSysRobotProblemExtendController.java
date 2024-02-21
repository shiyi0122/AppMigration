package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysRobotProblemExtendService;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotMapRes;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotProblemExtend;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/11/3 9:03
 */
@Api(tags = "后台管理--游小伴扩展问题")
@RestController
@RequestMapping("adminSysRobotProblemExtend")
@CrossOrigin
public class AdminSysRobotProblemExtendController {

    @Autowired
    SysRobotProblemExtendService sysRobotProblemExtendService;

    @ApiOperation("后台管理--添加扩展问题")
    @PostMapping("/addSysRobotProblemExtend")
    @ResponseBody
    public ReturnModel addSysRobotProblemExtend(@RequestBody SysRobotProblemExtend sysRobotProblemExtend) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysRobotProblemExtendService.addSysRobotProblemExtend(sysRobotProblemExtend);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }
        return returnModel;
    }

    /**
     * 资源详情修改
     *
     * @param sysRobotProblemExtend
     * @return
     */
    @ApiOperation("后台管理--修改扩展问题")
    @PostMapping("/editSysRobotProblemExtend")
    @ResponseBody
    public ReturnModel editSysRobotProblemExtend(@RequestBody SysRobotProblemExtend sysRobotProblemExtend) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotProblemExtendService.editSysRobotProblemExtend(sysRobotProblemExtend);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("资源详情修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("资源详情修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
//            logger.error("editSemanticsDetails", e);
            returnModel.setData("");
            returnModel.setMsg("资源详情修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 资源详情删除
     *
     * @param sysRobotProblemExtend
     * @return
     */
    @ApiOperation("后台管理--删除扩展问题")
    @GetMapping("/delSysRobotProblemExtend")
    @ResponseBody
    public ReturnModel delSysRobotProblemExtend(SysRobotProblemExtend sysRobotProblemExtend) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysRobotProblemExtendService.delSysRobotProblemExtend(sysRobotProblemExtend);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }

        return returnModel;
    }
    @ApiOperation("后台管理--查询扩展问题")
    @GetMapping("/getSysRobotProblemExtendList")
    @ResponseBody
    public PageDataResult getSysRobotProblemExtendList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();
        pageDataResult = sysRobotProblemExtendService.getSysRobotProblemExtendList(pageDTO);
        return pageDataResult;
    }


}
