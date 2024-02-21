package com.jxzy.AppMigration.Administration;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumCollectionService;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollection;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import cn.hutool.core.util.ObjUtil;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/25 19:20
 */


@Api(tags = "后台管理-博物馆藏品相关接口")
@RestController
@RequestMapping("adminSysMuseumCollection")
@CrossOrigin
public class AdminSysMuseumCollectionController {

    @Autowired
    private SysMuseumCollectionService sysMuseumCollectionService;
    @Autowired
    private SysMuseumService sysMuseumService;

    @ApiOperation("管理后台博物馆藏品列表查询")
    @GetMapping("getSysMuseumCollectionList")
    @ResponseBody
    public ReturnModel getSysMuseumCollectionList(PageDTO pageDTO) {
//        System.out.println(path1);
//        System.out.println(path2);
        ReturnModel returnModel = new ReturnModel();
        Page<SysMuseumCollection> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        LambdaQueryWrapper<SysMuseumCollection> smclqw = Wrappers.lambdaQuery(SysMuseumCollection.class);
        smclqw.eq(StringUtils.hasText(pageDTO.getMuseumId()), SysMuseumCollection::getMuseumId, pageDTO.getMuseumId());
//        smclqw.like(StringUtils.hasText(pageDTO.getMuseumName()),SysMuseumCollection::getMuseumCollectionName)
        smclqw.orderByDesc(SysMuseumCollection::getCreateTime);
        sysMuseumCollectionService.page(page, smclqw);
        List<SysMuseumCollection> collectionList = page.getRecords();
        if (ObjUtil.isNotEmpty(collectionList)) {
            Long museumId;
            LambdaQueryWrapper<SysMuseum> smlqw;
            SysMuseum museum;
            for (SysMuseumCollection museumCollection : collectionList) {
                museumId = museumCollection.getMuseumId();
                if (museumId != null) {
                    smlqw = Wrappers.lambdaQuery(SysMuseum.class);
                    smlqw.eq(ObjUtil.isNotEmpty(museumId), SysMuseum::getId, museumId);
                    museum = sysMuseumService.getOne(smlqw);//添加藏品时必须分配博物馆id，因此可以查询到唯一的博物馆
                    if (ObjUtil.isNotEmpty(museum)) {
                        museumCollection.setMuseumName(museum.getMuseumName());
                    }
                }
            }
        }
        returnModel.setData(collectionList);
        returnModel.setTotal(((int) page.getTotal()));
        return returnModel;
    }


    /**
     * 根据博物馆名称和藏品名称模糊查询
     *
     * @param
     * @return
     */
    @GetMapping("getCollectionByCondition")
    @ApiOperation("搜索查询(博物馆名称,藏品名称)")
    public ReturnModel getCollectionByCondition(String museumName, String museumCollectionName,
                                                @RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize) {
        ReturnModel returnModel = new ReturnModel();
        HashMap<String, Object> map = new HashMap<>();
        map.put("museumName", museumName);
        map.put("museumCollectionName", museumCollectionName);
        PageInfo<SysMuseumCollection> pageInfo = sysMuseumCollectionService.getCollectionByCondition(map, pageNum, pageSize);
        returnModel.setData(pageInfo.getList());
        returnModel.setTotal(((int) pageInfo.getTotal()));
        return returnModel;
    }

    /**
     * 后台管理——添加酒店民宿
     *
     * @param
     * @return
     */
    @ApiOperation("添加博物馆藏品")
    @PostMapping("addSysMuseumCollection")
    @ResponseBody
    public ReturnModel addSysMuseumCollection(@RequestPart("photo") MultipartFile photo,
                                              @RequestPart("video") MultipartFile video,
                                              SysMuseumCollection sysMuseumCollection) {

        ReturnModel returnModel = new ReturnModel();
        try {
            int res = sysMuseumCollectionService.save(photo, video, sysMuseumCollection);
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
            returnModel.setMsg("添加失败");
        }
        return returnModel;
    }

    /**
     * 后台管理——添加酒店民宿（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("添加博物馆藏品信息（无文件）")
    @PostMapping("addSysMuseumCollectionWithoutFile")
    @ResponseBody
    public ReturnModel addSysMuseumCollectionWithoutFile(@RequestBody SysMuseumCollection sysMuseumCollection) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumCollectionService.save(sysMuseumCollection)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("添加成功！");
                return returnModel;
            }
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
            return returnModel;
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
            return returnModel;
        }
    }


    /**
     * 修改博物馆藏品信息
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("修改博物馆藏品信息")
    @PostMapping("editSysMuseumCollection")
    @ResponseBody
    public ReturnModel editSysMuseumCollection(@RequestPart("photo") MultipartFile photo,
                                               @RequestPart("video") MultipartFile video,
                                               SysMuseumCollection sysMuseumCollection) {

        ReturnModel returnModel = new ReturnModel();
        try {
            int res = sysMuseumCollectionService.edit(photo, video, sysMuseumCollection);
            if (res == 1) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("修改成功！");
            } else if (res == 2) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.UN_SUPPORT_PICTURE_TYPE_STR);
            } else if (res == 3) {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg(Constant.PIC_IS_EMPTY_STR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
            return returnModel;
        }
        return returnModel;
    }

    @ApiOperation("修改博物馆信息(无文件)")
    @PostMapping("editSysMuseumCollectionWithoutFile")
    @ResponseBody
    public ReturnModel editSysMuseumCollectionWithoutFile(@RequestBody SysMuseumCollection sysMuseumCollection) {

        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumCollectionService.updateById(sysMuseumCollection)) {
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
     * 后台管理——删除博物馆藏品
     *
     * @param
     * @param
     * @return
     */

    @ApiOperation("删除博物馆藏品")
    @PostMapping("delSysMuseumCollection")
    @ResponseBody
    public ReturnModel delSysMuseumCollection(@RequestParam("id") Long id) {

        ReturnModel returnModel = new ReturnModel();

        try {
            if (sysMuseumCollectionService.removeById(id)) {
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
}