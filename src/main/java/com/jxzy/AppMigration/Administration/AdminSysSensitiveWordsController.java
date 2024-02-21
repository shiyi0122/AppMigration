package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysSensitiveWordsService;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUs;
import com.jxzy.AppMigration.NavigationApp.entity.SysSensitiveWords;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhang
 * @Date 2023/1/14 15:55
 */

@Api(tags = "后台管理-敏感词管理")
@RestController
@RequestMapping("adminSysSensitiveWords")
@CrossOrigin
public class AdminSysSensitiveWordsController {

    @Autowired
    SysSensitiveWordsService sysSensitiveWordsService;

    @ApiOperation("敏感词新增")
    @PostMapping("/addSysSensitiveWords")
    @ResponseBody
    public ReturnModel addSysSensitiveWords(@RequestBody SysSensitiveWords sysSensitiveWords) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysSensitiveWordsService.addSysAboutUs(sysSensitiveWords);

        if ( i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
            return returnModel;
        }
    }

    @ApiOperation("敏感词修改")
    @PostMapping("/editSysSensitiveWords")
    @ResponseBody
    public ReturnModel editSysSensitiveWords(@RequestBody SysSensitiveWords sysSensitiveWords) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysSensitiveWordsService.editSysSensitiveWords(sysSensitiveWords);

        if ( i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
            return returnModel;
        }
    }

    @ApiOperation("敏感词删除")
    @GetMapping("/delSysSensitiveWords")
    @ResponseBody
    public ReturnModel delSysSensitiveWords(BaseDataDTO baseDataDTO) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysSensitiveWordsService.delSysSensitiveWords(baseDataDTO.getId());

        if ( i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
            return returnModel;
        }
    }

    @ApiOperation("敏感词列表")
    @GetMapping("/getSysSensitiveWordsList")
    @ResponseBody
    public PageDataResult getSysSensitiveWordsList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysSensitiveWordsService.getSysSensitiveWordsList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getContent());

        return pageDataResult;
    }

}
