package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotUserStopService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserStop;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/21 10:08
 */
@Api(tags = "后台管理-足迹管理")
@RestController
@RequestMapping("adminSysScenicSpotUserStop")
@CrossOrigin
public class AdminSysScenicSpotUserStopController {

    @Autowired
    SysScenicSpotUserStopService sysScenicSpotUserStopService;

    /**
     * 后台管理——用户足迹列表查询
     * @param pageDTO
     * @return
     */

    @ApiOperation("后台管理--用户足迹列表查询")
    @GetMapping("/getSysScenicSpotUserStopList")
    @ResponseBody
    public PageDataResult getSysScenicSpotUserStopList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getUid())){
            search.put("uid",pageDTO.getUid());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotName())){
            search.put("spotName",pageDTO.getSpotName());
        }
        if (!StringUtils.isEmpty(pageDTO.getStartTime())){
            search.put("time",pageDTO.getStartTime());
        }
        pageDataResult = sysScenicSpotUserStopService.getSysScenicSpotUserStopList(pageDTO.getPageNum(), pageDTO.getPageSize(),search);
        return pageDataResult;
    }

    /**
     * 后台管理——用户足迹查看足迹
     * @param
     * @return
     */

    @ApiOperation("后台管理——用户足迹--查看足迹")
    @GetMapping("/getSysScenicSpotUserStopFootprint")
    @ResponseBody
    public ReturnModel getSysScenicSpotUserStopFootprint(Long spotId,Long userId) {
        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotUserStop> list = sysScenicSpotUserStopService.getSysScenicSpotUserStopFootprint(spotId,userId);

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }

    /**
     * 删除足迹景点
     * @param id
     * @return
     */
    @ApiOperation("后台管理——用户足迹--删除")
    @GetMapping("/delSysScenicSpotUserStopFootprint")
    @ResponseBody
    public ReturnModel delSysScenicSpotUserStopFootprint(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotUserStopService.delSysScenicSpotUserStopFootprint(id);

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("删除成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }else{
            returnModel.setData(i);
            returnModel.setMsg("删除失败");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }






    }
