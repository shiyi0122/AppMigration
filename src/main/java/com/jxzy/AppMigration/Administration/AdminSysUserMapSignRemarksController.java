package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysUserMapSignRemarksService;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author zhang
 * @Date 2022/11/1 16:55
 */
@Api(tags = "后台管理-图钉管理")
@RestController
@RequestMapping("adminSysUserMapSignRemarks")
@CrossOrigin
public class AdminSysUserMapSignRemarksController {


    @Autowired
    SysUserMapSignRemarksService sysUserMapSignRemarksService;

    @ApiOperation("图钉列表查询")
    @GetMapping("/getUserMapSignRemarksList")
    @ResponseBody
    public PageDataResult getUserMapSignRemarksList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        HashMap<String, Object> search = new HashMap<>();
        search.put("userId",pageDTO.getUid());

        pageDataResult =  sysUserMapSignRemarksService.adminUserMapSignRemarksList(pageDTO.getPageNum(), pageDTO.getPageSize(),search);

        return pageDataResult;
    }


}
