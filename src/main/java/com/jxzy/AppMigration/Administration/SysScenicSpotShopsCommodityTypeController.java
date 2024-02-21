package com.jxzy.AppMigration.Administration;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotShopsCommodityTypeService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsCommodityType;
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
 * @Date 2022/9/6 17:44
 */

@Api(tags = "后台管理-商品类型相关接口")
@RestController
@RequestMapping("adminSpotShopsCommodityType")
@CrossOrigin
public class SysScenicSpotShopsCommodityTypeController {

    @Autowired
    SysScenicSpotShopsCommodityTypeService sysScenicSpotShopsCommodityTypeService;


    @ApiOperation("查询商品类型列表")
    @GetMapping("getShopsCommodityTypeList")
    @ResponseBody
    public PageDataResult getShopsCommodityTypeList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysScenicSpotShopsCommodityTypeService.getShopsCommodityTypeList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getContent());

        return pageDataResult;
    }

    /**
     * 后台管理——添加商品类型
     *
     * @param
     * @return
     */
    @ApiOperation("添加商品类型")
    @PostMapping("addShopsCommodityType")
    @ResponseBody
    public ReturnModel addShopsCommodityType(@RequestPart("file") MultipartFile file, SysScenicSpotShopsCommodityType sysScenicSpotShopsCommodityType) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotShopsCommodityTypeService.addShopsCommodityType(file,sysScenicSpotShopsCommodityType);
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
     * 后台管理——修改商品类型
     *
     * @param
     * @return
     */
    @ApiOperation("修改商品类型")
    @PostMapping("editShopsCommodityType")
    @ResponseBody
    public ReturnModel editShopsCommodityType(@RequestPart("file") MultipartFile file, SysScenicSpotShopsCommodityType sysScenicSpotShopsCommodityType) {

        ReturnModel returnModel = new ReturnModel();

        int i =  sysScenicSpotShopsCommodityTypeService.editShopsCommodityType(file,sysScenicSpotShopsCommodityType);
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
     * 后台管理——删除商品类型
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除商品类型")
    @PostMapping("delShopsCommodityType")
    @ResponseBody
    public ReturnModel delShopsCommodityType(Long typeId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotShopsCommodityTypeService.delShopsCommodityType(typeId);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }

    @ApiOperation("商品类型下拉选")
    @GetMapping("shopsCommodityTypeDrop")
    @ResponseBody
    public ReturnModel shopsCommodityTypeDrop() {

        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotShopsCommodityType> list = sysScenicSpotShopsCommodityTypeService.shopsCommodityTypeDrop();

        returnModel.setData(list);
        returnModel.setMsg("获取成功");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

}
