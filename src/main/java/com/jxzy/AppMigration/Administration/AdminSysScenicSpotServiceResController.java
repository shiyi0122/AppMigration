package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotServiceResService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotFilesExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotServiceRes;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import com.rabbitmq.client.Return;
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
 * @Date 2022/9/2 10:55
 */
@Api(tags = "后台管理-卫生间相关接口")
@RestController
@RequestMapping("adminSysScenicSpotServiceRes")
@CrossOrigin
public class AdminSysScenicSpotServiceResController {

    @Autowired
    SysScenicSpotServiceResService sysScenicSpotServiceResService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    /**
     * 后台管理——查询景区卫生间
     * @param pageDTO
     * @return
     */
    @ApiOperation("查询景区卫生间列表")
    @GetMapping("getSpotServiceResList")
    @ResponseBody
    public PageDataResult getSpotServiceResList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("scenicSpotId",pageDTO.getSpotId());
        search.put("serviceName",pageDTO.getContent());
        search.put("spotName",pageDTO.getSpotName());
        pageDataResult = sysScenicSpotServiceResService.getSpotServiceResList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;
    }

    /**
     * 后台管理——添加景区卫生间
     * @param
     * @return
     */
    @ApiOperation("添加景区卫生间")
    @PostMapping("addSpotServiceRes")
    @ResponseBody
    public ReturnModel addSpotServiceRes(@RequestBody SysScenicSpotServiceRes sysScenicSpotServiceRes) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotServiceResService.addSpotServiceRes(sysScenicSpotServiceRes);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功!");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加失败!");
            return returnModel;
        }


    }

    /**
     * 后台管理——修改景区卫生间
     * @param sysScenicSpotServiceRes
     * @return
     */
    @ApiOperation("修改景区卫生间")
    @PostMapping("editSpotServiceRes")
    @ResponseBody
    public ReturnModel editSpotServiceRes(@RequestBody SysScenicSpotServiceRes sysScenicSpotServiceRes) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotServiceResService.editSpotServiceRes(sysScenicSpotServiceRes);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功!");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改失败!");
            return returnModel;
        }


    }

    @ApiOperation("删除景区卫生间")
    @GetMapping("delSpotServiceRes")
    @ResponseBody
    public ReturnModel delSpotServiceRes(Long serviceId) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotServiceResService.delSpotServiceRes(serviceId);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功!");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除失败!");
            return returnModel;
        }
    }


    /**
     * 修改卫生间启用禁用状态
     * @param serviceId
     * @return
     */
    @ApiOperation("修改卫生间启用禁用状态")
    @GetMapping("editSpotServiceResState")
    @ResponseBody
    public ReturnModel editSpotServiceResState(Long serviceId,String  serviceState ) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotServiceResService.editSpotServiceResState(serviceId,serviceState);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改状态成功!");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改状态失败!");
            return returnModel;
        }

    }


    /**
     * 导入卫生间
     * @param multipartFile
     * @return
     */
    @ApiOperation("导入景区卫生间")
    @RequestMapping("/importServiceResState")
    @ResponseBody
    public ReturnModel importServiceResState(@RequestPart("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysScenicSpotServiceRes> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotServiceRes.class, params);
            SysScenicSpot sysScenicSpot = new SysScenicSpot();

            for (SysScenicSpotServiceRes sysScenicSpotServiceRes:result){
                //查询景区id是否存在
//                 sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotFilesExcel.getScenicSpotId());
                sysScenicSpot = sysScenicSpotService.selectBySpotName(sysScenicSpotServiceRes.getScenicSpotName());
                if (!StringUtils.isEmpty(sysScenicSpot)){
                    sysScenicSpotServiceRes.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                }
                sysScenicSpotServiceRes.setServiceType("1");
                sysScenicSpotServiceRes.setServiceId(IdUtils.getSeqId());
                sysScenicSpotServiceResService.insert(sysScenicSpotServiceRes);

            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
//            logger.info("importBatchNumber", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败 ！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 导出
     * @param response
     * @param
     * @param
     * @throws Exception
     */
    @ApiOperation("导出")
    @RequestMapping(value = "/uploadExcelServiceRes")
    public void  uploadExcelServiceRes(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒
            List<SysScenicSpot> orderListByExample = null;
            Map<String,Object> search = new HashMap<>();
            search.put("spotName",dataDTO.getSpotName());

            List<SysScenicSpotServiceRes> sysScenicSpotServiceResList = sysScenicSpotServiceResService.getServiceResExcelPoi(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游娱go卫生间管理","卫生间管理",SysScenicSpotServiceRes.class,sysScenicSpotServiceResList),"卫生间管理"+ dateTime +".xls",response);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
