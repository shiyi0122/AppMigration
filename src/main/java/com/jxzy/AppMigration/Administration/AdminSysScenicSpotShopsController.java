package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.google.gson.internal.$Gson$Preconditions;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotShopsService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotBroadcastExcel;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotShopsExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotServiceRes;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsDetails;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/28 17:14
 */
@Api(tags = "后台管理-店铺相关接口")
@RestController
@RequestMapping("adminSysScenicSpotShops")
@CrossOrigin
public class AdminSysScenicSpotShopsController extends PublicUtil {

    @Autowired
    SysScenicSpotShopsService sysScenicSpotShopsService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @ApiOperation("查询店铺列表")
    @GetMapping("getSpotShopsList")
    @ResponseBody
    public PageDataResult getSpotShopsList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getContent())) {
            search.put("shopsName", pageDTO.getContent());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotId())) {
            search.put("spotId", pageDTO.getSpotId());
        }
        pageDataResult = sysScenicSpotShopsService.getSpotShopsList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }


    /**
     * 后台管理——添加店铺
     *
     * @param sysScenicSpotShops
     * @return
     */
    @ApiOperation("添加店铺")
    @PostMapping("addSpotShops")
    @ResponseBody
    public ReturnModel addSpotShops(@RequestBody SysScenicSpotShops sysScenicSpotShops) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotShopsService.addSpotShops(sysScenicSpotShops);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }
        return returnModel;
    }

    /**
     * 后台管理——修改店铺
     *
     * @param sysScenicSpotShops
     * @return
     */
    @ApiOperation("修改店铺")
    @PostMapping("exitSpotShops")
    @ResponseBody
    public ReturnModel exitSpotShops(@RequestBody SysScenicSpotShops sysScenicSpotShops) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotShopsService.exitSpotShops(sysScenicSpotShops);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        }
        return returnModel;

    }

    /**
     * 后台管理——删除店铺
     *
     * @param
     * @return
     */
    @ApiOperation("删除店铺")
    @PostMapping("delSpotShops")
    @ResponseBody
    public ReturnModel delSpotShops(Long shopsId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotShopsService.delSpotShops(shopsId);
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

    /**
     * 后台店铺管理——上传图片
     *
     * @param file
     * @param shopsId
     * @return
     */
    @ApiOperation("店铺上传图片")
    @PostMapping("addSpotShopsPicture")
    @ResponseBody
    public ReturnModel addSpotShopsPicture(@RequestPart("file") MultipartFile file, Long shopsId) {


        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotShopsService.addSpotShopsPicture(file, shopsId);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("上传成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("上传失败！");
        }

        return returnModel;

    }

    /**
     * 后台店铺管理——店铺详情
     *
     * @param
     * @return
     */
    @ApiOperation("查询店铺详情")
    @GetMapping("getSpotShopsDetails")
    @ResponseBody
    public PageDataResult getSpotShopsDetails(PageDTO pageDTO) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("shopsId", pageDTO.getShopsId());
        search.put("state", pageDTO.getState());
        search.put("content", pageDTO.getContent());
        search.put("shopsTypeName", pageDTO.getShopsTypeName());
        pageDataResult = sysScenicSpotShopsService.getSpotShopsDetails(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }

    /**
     * 后台店铺管理——添加店铺详情
     *
     * @param
     * @return
     */
    @ApiOperation("添加店铺详情")
    @PostMapping("addSpotShopsDetails")
    @ResponseBody
    public ReturnModel addSpotShopsDetails(@RequestBody SysScenicSpotShopsDetails sysScenicSpotShopsDetails) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotShopsService.addSpotShopsDetails(sysScenicSpotShopsDetails);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        }
        return returnModel;
    }
    /**
     * 后台管理——上传商品详情图片
     *
     * @param file
     * @param detailsId
     * @return
     */
    @ApiOperation("上传店铺商品详情图片")
    @PostMapping("addSpotShopsDetailsPicture")
    @ResponseBody
    public ReturnModel addSpotShopsDetailsPicture(MultipartFile file, Long detailsId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotShopsService.addSpotShopsDetailsPicture(file, detailsId);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("商铺图片上传成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else if (i == 3) {
            returnModel.setData("");
            returnModel.setMsg("图片文件不能为空!");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        } else if (i == 2) {
            returnModel.setData("");
            returnModel.setMsg("景点播报音频文件上传格式不正确！(支持:jpg,png格式)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("景点内容音频上传失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * 后台店铺管理——修改店铺详情
     *
     * @param
     * @return
     */
    @ApiOperation("修改店铺详情")
    @PostMapping("editSpotShopsDetails")
    @ResponseBody
    public ReturnModel editSpotShopsDetails(@RequestBody SysScenicSpotShopsDetails sysScenicSpotShopsDetails) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotShopsService.editSpotShopsDetails(sysScenicSpotShopsDetails);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改失败！");
            return returnModel;
        }
    }

    /**
     * 后台管理——店铺的启用禁用
     *
     * @param
     * @return
     */
    @ApiOperation("店铺的启用禁用")
    @GetMapping("editSpotShopsState")
    @ResponseBody
    public ReturnModel editSpotShopsState(Long shopsId, String state) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotShopsService.editSpotShopsState(shopsId, state);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改失败！");
            return returnModel;
        }
    }

    /**
     * 后台管理——删除店铺商品
     *
     * @param
     * @return
     */
    @ApiOperation("删除店铺商品")
    @GetMapping("delSpotShopsDetails")
    @ResponseBody
    public ReturnModel delSpotShopsDetails(Long detailsId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotShopsService.delSpotShopsDetails(detailsId);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除失败！");
            return returnModel;
        }

    }

    /**
     * 后台管理——店铺商品上下架
     * @param detailsId
     * @return
     */
    @ApiOperation("店铺商品上下架")
    @GetMapping("editSpotShopsDetailsState")
    @ResponseBody
    public ReturnModel editSpotShopsDetailsState(Long detailsId,String state) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotShopsService.editSpotShopsDetailsState(detailsId,state);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改成功！");
            return returnModel;
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改失败！");
            return returnModel;
        }
    }

    /**
     * 店铺导出
     * @param response
     * @param
     * @param
     * @throws Exception
     */
    @ApiOperation("导出店铺")
    @RequestMapping(value = "/uploadExcelShop")
    public void  uploadExcelShop(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(dataDTO.getContent())) {
            search.put("shopsName", dataDTO.getContent());
        }
        if (!StringUtils.isEmpty(dataDTO.getSpotId())) {
            search.put("spotId", dataDTO.getSpotId());
        }
       try{
        List<SysScenicSpotShopsExcel> list = sysScenicSpotShopsService.uploadExcelShop(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
        FileUtil.exportExcel(FileUtil.getWorkbook("游小伴店铺管理","店铺管理", SysScenicSpotShopsExcel.class,list),"店铺管理"+ dateTime +".xls",response);

        }catch (Exception e){
            logger.info("导出异常",e);
        }
    }




    /**
     * 导入店铺
     * @param multipartFile
     * @return
     */
    @ApiOperation("导入店铺")
    @RequestMapping("/importShop")
    @ResponseBody
    public ReturnModel importShop(@RequestPart("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysScenicSpotShopsExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotShopsExcel.class, params);
            SysScenicSpot sysScenicSpot = new SysScenicSpot();

            for (SysScenicSpotShopsExcel sysScenicSpotServiceResExcel:result){
                //查询景区id是否存在
//                 sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotFilesExcel.getScenicSpotId());
                sysScenicSpot = sysScenicSpotService.selectBySpotName(sysScenicSpotServiceResExcel.getSpotName());
                if (!StringUtils.isEmpty(sysScenicSpot)){
                    sysScenicSpotServiceResExcel.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                }
                sysScenicSpotShopsService.addImportShops(sysScenicSpotServiceResExcel);

            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            e.printStackTrace();
//            logger.info("importBatchNumber", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败 ！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }






}