package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumService;
import com.jxzy.AppMigration.NavigationApp.entity.SysHotel;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/21 15:06
 */

@Api(tags = "后台管理-博物馆相关接口")
@RestController
@RequestMapping("adminSysMuseum")
@CrossOrigin
public class AdminSysMuseumController {

    @Autowired
    SysMuseumService sysMuseumService;


    /**
     * 后台管理——添加博物馆
     *
     * @param
     * @return
     */
    @ApiOperation("添加博物馆")
    @PostMapping("addSysMuseum")
    @ResponseBody
    public ReturnModel addSysMuseum(@RequestPart("photo") MultipartFile photo, SysMuseum sysMuseum) {

        ReturnModel returnModel = new ReturnModel();
        try {
            int res = sysMuseumService.save(photo, sysMuseum);
            if (res == 1) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("添加成功！");
            } else if (res == -2) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PICTURE_TYPE_STR);
            } else if (res == -3) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.PIC_IS_EMPTY_STR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
            return returnModel;
        }
        return returnModel;
    }

    /**
     * 后台管理——添加酒店民宿（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("添加博物馆信息（无文件）")
    @PostMapping("addSysMuseumN")
    public ReturnModel addSysMuseum(@RequestBody SysMuseum sysMuseum) {

        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumService.save(sysMuseum)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("添加成功！");
                return returnModel;
            }
            returnModel.setMsg(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setMsg(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
            return returnModel;
        }
    }


    @ApiOperation("修改博物馆信息")
    @PostMapping("editSysMuseum")
    public ReturnModel editSysMuseum(@RequestPart("photo") MultipartFile photo, SysMuseum sysMuseum) {

        ReturnModel returnModel = new ReturnModel();
        int res = sysMuseumService.update(photo, sysMuseum);
        if (res == 1) {
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (res == 2) {
            returnModel.setData(res);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg(Constant.UN_SUPPORT_PICTURE_TYPE_STR);
        } else if (res == 3) {
            returnModel.setData(res);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg(Constant.PIC_IS_EMPTY_STR);
        }
        return returnModel;
    }


    @ApiOperation("修改博物馆信息(无文件)")
    @PostMapping("editSysMuseumN")
    public ReturnModel editSysMuseum(@RequestBody SysMuseum sysMuseum) {

        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumService.updateById(sysMuseum)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("修改成功！");
                return returnModel;
            }
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
            return returnModel;
        }
    }


    /**
     * 后台管理——删除博物馆
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除博物馆")
    @PostMapping("delSysMuseum")
    public ReturnModel delSysMuseum(@RequestBody SysMuseum museum) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumService.removeById(museum.getId())){
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("删除成功！");
                return returnModel;
            }
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
            return returnModel;
        }
    }


    /**
     * 管理后台-博物馆列表查询
     *
     * @param pageDTO
     * @return
     */
    /*@ApiOperation("管理后台博物馆列表查询")
    @GetMapping("getSysMuseumList")
    public PageDataResult adminSysMuseumList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        if (StringUtils.isEmpty(pageDTO.getSort())) {
            pageDTO.setSort(6);
        }
        Page<SysMuseum> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        LambdaQueryWrapper<SysMuseum> smlqw = Wrappers.lambdaQuery(SysMuseum.class);
        smlqw.like(StringUtils.hasText(pageDTO.getMuseumName()), SysMuseum::getMuseumName, pageDTO.getMuseumName());
        smlqw.orderByDesc(SysMuseum::getCreateTime);
        sysMuseumService.page(page, smlqw);
        pageDataResult.setData(page.getRecords());
        pageDataResult.setTotals(((int) page.getTotal()));
        return pageDataResult;
    }*/


    /**
     * 管理后台--博物馆列表下拉选
     *
     * @param
     * @return
     */
   /* @ApiOperation("后台管理-博物馆列表下拉选")
    @GetMapping("getSysMuseumDrop")
    public ReturnModel getSysMuseumDrop() {
        ReturnModel returnModel = new ReturnModel();
        LambdaQueryWrapper<SysMuseum> smlqw = Wrappers.lambdaQuery(SysMuseum.class);
        smlqw.select(SysMuseum::getId, SysMuseum::getMuseumName);
        List<SysMuseum> sysMuseumList = sysMuseumService.list(smlqw);
        returnModel.setData(sysMuseumList);
        returnModel.setTotal(sysMuseumList.size());
        return returnModel;
    }*/
    @ApiOperation("管理后台博物馆列表详情查询")
    @GetMapping("/getSysMuseumInfo")
    public PageDataResult getMuseumInfo(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,
                                        SysMuseum sysMuseum){
        List<SysMuseum> sysMuseumList = sysMuseumService.getMuseumInfo(pageNum, pageSize, sysMuseum);
        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult.setData(sysMuseumList);
        pageDataResult.setTotals(sysMuseumList.size());
        return pageDataResult;
    }
    @ApiOperation("上线")
    @PostMapping("online")
    public ReturnModel online(@RequestBody SysMuseum museum) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumService.updateById(museum)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("博物馆已上线！");
                return returnModel;
            }
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("操作失败！");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("操作失败！");
            return returnModel;
        }
    }
    @ApiOperation("下线")
    @PostMapping("offline")
    public ReturnModel offline(@RequestBody SysMuseum museum) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumService.updateById(museum)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("博物馆已下线！");
                return returnModel;
            }
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("操作失败！");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("操作失败！");
            return returnModel;
        }
    }
}