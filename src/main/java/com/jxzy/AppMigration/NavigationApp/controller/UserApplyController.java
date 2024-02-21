package com.jxzy.AppMigration.NavigationApp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jxzy.AppMigration.NavigationApp.Service.UserApplyService;
import com.jxzy.AppMigration.NavigationApp.entity.UserApply;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user-apply")
@Api(tags = "用户报名信息")
public class UserApplyController {
    @Autowired
    private UserApplyService userApplyService;

    @ApiOperation("新增用户报名信息")
    @PostMapping("/save")
    public ReturnModel save(@RequestBody UserApply userApply) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = userApplyService.addUserApply(userApply);
            if (i > 0) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("新增成功");
                return returnModel;
            }
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("新增失败");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("新增失败");
            return returnModel;
        }
    }

    @ApiOperation("用户报名信息列表")
    @GetMapping("/list")
    public ReturnModel list() {
        ReturnModel returnModel = new ReturnModel();
        List<UserApply> userApplies = userApplyService.getByUser();
        returnModel.setData(userApplies);
        int total = userApplies != null ? userApplies.size() : 0;
        returnModel.setTotal(total);
        returnModel.setMsg("查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }
}
