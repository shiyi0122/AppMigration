package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersFeedbacksService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedbacks;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * 后台管理-意见反馈相关接口
 * @Date 2022/9/4 14:31
 */

@Api(tags = "后台管理-意见反馈相关接口")
@RestController
@RequestMapping("AdminGuideAppUsersFeedbacks")
@CrossOrigin
public class SysGuideAppUsersFeedbacksController {

    @Autowired
    SysGuideAppUsersFeedbacksService sysGuideAppUsersFeedbacksService;


    @ApiOperation("app用户意见反馈列表查询")
    @GetMapping("/getSysGuideAppUsersFeedbacksList")
    @ResponseBody
    public PageDataResult getSysGuideAppNewsList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("phone", pageDTO.getPhone());
        search.put("content", pageDTO.getContent());
        search.put("userName", pageDTO.getName());
        pageDataResult = sysGuideAppUsersFeedbacksService.getSysGuideAppNewsList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }

    /**
     * 后台管理——意见反馈状态修改
     *
     * @param id
     * @param states
     * @return
     */
    @ApiOperation("app用户意见反馈状态修改")
    @GetMapping("/exitSysGuideAppUsersFeedbacksStates")
    @ResponseBody
    public ReturnModel exitSysGuideAppUsersFeedbacksStates(Long id, String states) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGuideAppUsersFeedbacksService.exitSysGuideAppUsersFeedbacksStates(id, states);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("状态修改失败！");
            return returnModel;
        }
    }


    @ApiOperation("app用户意见反馈回复修改")
    @PostMapping("/exitSysGuideAppUsersFeedbacksReply")
    @ResponseBody
    public ReturnModel exitSysGuideAppUsersFeedbacksReply(@RequestBody SysGuideAppUsersFeedbacks sysGuideAppUsersFeedbacks) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGuideAppUsersFeedbacksService.exitSysGuideAppUsersFeedbacksReply(sysGuideAppUsersFeedbacks);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("状态修改失败！");
            return returnModel;
        }
    }


    /**
     * 后台管理删除用户反馈
     * @param id
     * @param
     * @return
     */
    @ApiOperation("后台删除用户反馈")
    @GetMapping("/delSysGuideAppUsersFeedbacks")
    @ResponseBody
    public ReturnModel delSysGuideAppUsersFeedbacks(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGuideAppUsersFeedbacksService.delSysGuideAppUsersFeedbacks(id);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
            return returnModel;
        }
    }


    @ApiOperation("根据反馈id，获取图片列表")
    @GetMapping("/getUsersFeedbacksPicturl")
    @ResponseBody
    public ReturnModel getUsersFeedbacksPicturl(Long id) {

        ReturnModel returnModel = new ReturnModel();

        List<SysGuideAppUsersFeedbacks> list = sysGuideAppUsersFeedbacksService.getUsersFeedbacksPicturl(id);

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功！");
        return returnModel;
    }


}