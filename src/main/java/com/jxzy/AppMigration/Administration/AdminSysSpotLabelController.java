package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysSpotLabelService;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLabel;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotNavigation;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/13 10:17
 *  后台景区标签
 */

@Api(tags = "后台管理-景区标签管理")
@RestController
@RequestMapping("adminSysSpotLabel")
@CrossOrigin
public class AdminSysSpotLabelController {

    @Autowired
    SysSpotLabelService sysSpotLabelService;

    /**
     * 后台管理——添加首页导航
     *
     * @param
     * @return
     */
    @ApiOperation("添加景区标签")
    @PostMapping("addSysSpotLabel")
    @ResponseBody
    public ReturnModel addSysSpotLabel(@RequestBody SysSpotLabel sysSpotLabel) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotLabelService.addSysSpotLabel(sysSpotLabel);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }else if (i == 2){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        }else if (i == 3){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 后台管理——修改首页导航
     *
     * @param
     * @return
     */
    @ApiOperation("修改景区标签")
    @PostMapping("editSysSpotLabel")
    @ResponseBody
    public ReturnModel editSysSpotLabel(@RequestBody SysSpotLabel sysSpotLabel) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSpotLabelService.editSysSpotLabel(sysSpotLabel);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        }else if (i == 2){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        }else if (i == 3){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 后台管理——删除商品类型
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除景区标签")
    @PostMapping("delSysSpotLabel")
    @ResponseBody
    public ReturnModel delSysSpotLabel(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysSpotLabelService.delSysSpotLabel(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }


    @ApiOperation("查询景区标签列表")
    @GetMapping("getSysSpotLabelList")
    @ResponseBody
    public PageDataResult getSysSpotLabelList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysSpotLabelService.getSysSpotLabelListList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getContent());

        return pageDataResult;
    }

    @ApiOperation("标签列表下拉选")
    @GetMapping("sysSpotLabelDrop")
    @ResponseBody
    public ReturnModel sysSpotLabelDrop(BaseDataDTO baseDataDTO) {
        ReturnModel returnModel = new ReturnModel();

        List<SysSpotLabel> list =  sysSpotLabelService.sysSpotLabelDrop();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;

    }


}
