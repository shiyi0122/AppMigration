package com.jxzy.AppMigration.NavigationApp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumTagRelationService;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumTagService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumTag;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumTagRelation;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author libofan
 * @since 2023-10-30
 */
@Api(tags = "后台管理-博物馆标签")
@CrossOrigin
@RestController
@RequestMapping("/sysMuseumTag")
public class SysMuseumTagController {
    @Autowired
    private SysMuseumTagService sysMuseumTagService;
    @Autowired
    private SysMuseumTagRelationService sysMuseumTagRelationService;

    @GetMapping("getMuseumTag")
    @ApiOperation("标签列表")
    public ReturnModel getMuseumTag(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize,
                                    SysMuseumTag museumTag) {
        ReturnModel returnModel = new ReturnModel();
        Page<SysMuseumTag> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysMuseumTag> smtlqw = Wrappers.lambdaQuery(SysMuseumTag.class);
        String content = museumTag.getContent();
        smtlqw.like(StringUtils.hasText(content), SysMuseumTag::getContent, content);
        sysMuseumTagService.page(page, smtlqw);
        returnModel.setData(page.getRecords());
        returnModel.setTotal(((int) page.getTotal()));
        return returnModel;
    }

    @PostMapping("addMuseumTag")
    @ApiOperation("添加标签")
    public ReturnModel addMuseumTag(@RequestBody SysMuseumTag museumTag) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumTagService.save(museumTag)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("添加成功");
            } else {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
            return returnModel;
        }
        return returnModel;
    }

    @PostMapping("addMuseumTagForMuseum")
    @ApiOperation("为博物馆添加标签")
    public ReturnModel addMuseumTagForMuseum(@RequestParam("museumId") Long museumId,
                                             @RequestParam("tagIds[]") Long[] tagIds) {
        ReturnModel returnModel = new ReturnModel();
        ArrayList<SysMuseumTagRelation> relations = new ArrayList<>();
        SysMuseumTagRelation museumTagRelation;
        for (Long tagId : tagIds) {
            museumTagRelation = new SysMuseumTagRelation();
            museumTagRelation.setMuseumId(museumId);
            museumTagRelation.setTagId(tagId);
            relations.add(museumTagRelation);
        }
        try {
            if (sysMuseumTagRelationService.saveBatch(relations)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("添加成功");
            } else {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
            return returnModel;
        }
        return returnModel;
    }

    @PostMapping("editMuseumTag")
    @ApiOperation("编辑标签")
    public ReturnModel editMuseumTag(@RequestBody SysMuseumTag museumTag) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumTagService.updateById(museumTag)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("编辑成功");
            } else {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg("编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("编辑失败");
            return returnModel;
        }
        return returnModel;
    }
    @PostMapping("delMuseumTagForMuseum")
    @ApiOperation("删除博物馆标签")
    public ReturnModel delMuseumTagForMuseum(@RequestParam("museumId") Long museumId,
                                             @RequestParam("tagIds[]") Long[] tagIds) {
        ReturnModel returnModel = new ReturnModel();
        ArrayList<SysMuseumTagRelation> relations = new ArrayList<>();
        SysMuseumTagRelation sysMuseumTagRelation;
        for (Long tagId : tagIds) {
            sysMuseumTagRelation = new SysMuseumTagRelation();
            sysMuseumTagRelation.setMuseumId(museumId);
            sysMuseumTagRelation.setTagId(tagId);
            relations.add(sysMuseumTagRelation);
        }
        try {
            if (sysMuseumTagRelationService.delMuseumTagForMuseum(relations)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("删除成功");
            } else {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
            return returnModel;
        }
        return returnModel;
    }
    @PostMapping("delMuseumTag")
    @ApiOperation("删除标签")
    public ReturnModel delMuseumTag(@RequestBody SysMuseumTag museumTag) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysMuseumTagService.removeById(museumTag)) {
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("删除成功");
            } else {
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setMsg("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
            return returnModel;
        }
        return returnModel;
    }
}
