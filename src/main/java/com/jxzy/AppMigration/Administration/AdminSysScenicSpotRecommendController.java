package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRecommendService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommend;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
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
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/3 19:44
 */
@Api(tags = "后台管理-推荐景区接口")
@RestController
@RequestMapping("adminSysScenicSpotRecommendController")
@CrossOrigin
public class AdminSysScenicSpotRecommendController {

    @Autowired
    SysScenicSpotRecommendService sysScenicSpotRecommendService;

    @ApiOperation("查询推荐景区列表")
    @GetMapping("getSpotRecommendList")
    @ResponseBody
    public PageDataResult getSpotRecommendList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
//        if (!StringUtils.isEmpty(pageDTO.getContent())) {
//            search.put("shopsName", pageDTO.getContent());
//        }
//        if (!StringUtils.isEmpty(pageDTO.getSpotId())) {
//            search.put("spotId", pageDTO.getSpotId());
//        }
        pageDataResult = sysScenicSpotRecommendService.getSpotRecommendList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }
    /**
     * 后台管理——添加推荐景区
     *
     * @param
     * @return
     */
    @ApiOperation("添加推荐景区")
    @PostMapping("addSpotRecommend")
    @ResponseBody
    public ReturnModel addSpotRecommend(@RequestBody SysScenicSpotRecommend sysScenicSpotRecommend) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotRecommendService.addSpotRecommend(sysScenicSpotRecommend);
        if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("此排序或景区已有值,无法添加");
        } else if (i == 1){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }
        return returnModel;
    }

    @ApiOperation("编辑推荐景区")
    @PostMapping("editSpotRecommend")
    @ResponseBody
    public ReturnModel editSpotRecommend(@RequestBody SysScenicSpotRecommend sysScenicSpotRecommend) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotRecommendService.editSpotRecommend(sysScenicSpotRecommend);
        if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("此排序已有值,无法编辑！");
        } else if (i == 1){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加失败！");
        }
        return returnModel;
    }

    @ApiOperation("删除推荐景区")
    @PostMapping("delSpotRecommend")
    @ResponseBody
    public ReturnModel delSpotRecommend(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotRecommendService.delSpotRecommend(id);
        if (i >0) {
            returnModel.setData(i);
            returnModel.setMsg("删除成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setMsg("删除失败！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }
    }



}
