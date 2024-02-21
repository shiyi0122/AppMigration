package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersFeedbacksService;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserDistrictFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserScenicFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/5 11:01
 */

//@Api(tags = "后台管理-用户景区点赞收藏相关接口")
@RestController
@RequestMapping("adminSysUserDistrictFabulous")
@CrossOrigin
public class AdminSysUserDistrictFabulousController {

    @Autowired
    SysUserDistrictFabulousCollectionService sysUserDistrictFabulousCollectionService;



    /**
     * 后台管理——用户景区点赞收藏列表搜索
     *
     * @param pageDTO
     * @return
     */
//    @ApiOperation("用户景区点赞收藏列表搜索")
    @GetMapping("/getSysUserDistrictFabulousList")
    @ResponseBody
    public PageDataResult getSysUserDistrictFabulousList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("userId",pageDTO.getUid());
        search.put("userName",pageDTO.getName());
        pageDataResult = sysUserDistrictFabulousCollectionService.getSysUserDistrictFabulousList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;

    }

}
