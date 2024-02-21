package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotParkingService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotExcel;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotParkingExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParking;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParkingWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
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
 * @Date 2022/10/15 10:49
 */
@Api(tags = "后台管理-景区出入口相关接口")
@RestController
@RequestMapping("adminSysScenicSpotEntrance")
@CrossOrigin
public class AdminSysScenicSpotEntranceController {

    @Autowired
    SysScenicSpotParkingService sysScenicSpotParkingService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @ApiOperation("出入口列表")
    @GetMapping("getSpotEntranceList")
    @ResponseBody
    public PageDataResult getSpotEntranceList(PageDTO pageDTO){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("parkingName",pageDTO.getParkingName());
        search.put("spotName",pageDTO.getSpotName());
        search.put("type",3);
        pageDataResult =  sysScenicSpotParkingService.selectBySearch(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;
    }


    @ApiOperation("添加出入口")
    @PostMapping("addSpotEntrance")
    @ResponseBody
    public ReturnModel addSpotEntrance(@RequestBody SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        ReturnModel returnModel = new ReturnModel();

        sysScenicSpotParkingWithBLOBs.setCoordinateType("3");
        int i = sysScenicSpotParkingService.addSpotParking(sysScenicSpotParkingWithBLOBs);

        if (i>0){
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

    @ApiOperation("修改出入口")
    @PostMapping("editSpotEntrance")
    @ResponseBody
    public ReturnModel editSpotEntrance(@RequestBody SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotParkingService.editSpotParking(sysScenicSpotParkingWithBLOBs);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        }
        return returnModel;
    }

    @ApiOperation("删除出入口")
    @GetMapping("delSpotEntrance")
    @ResponseBody
    public ReturnModel delSpotEntrance(Long parkingId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotParkingService.delSpotParking(parkingId);

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

    @ApiOperation("修改出入口状态")
    @GetMapping("exitSpotEntranceType")
    @ResponseBody
    public ReturnModel exitSpotEntranceType(Long parkingId, String type) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotParkingService.exitSpotParkingType(parkingId,type);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改成功！");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("状态修改失败！");
        }
        return returnModel;
    }

    @ApiOperation("导出景区出入口")
    @RequestMapping(value = "/uploadExcelSpotEntrance")
    public void  uploadExcelSpotEntrance(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒

            Map<String,Object> search = new HashMap<>();

            search.put("parkingName",dataDTO.getParkingName());
            search.put("spotName",dataDTO.getSpotName());
            search.put("type",3);
            List<SysScenicSpotParkingExcel> sysScenicSpotEntranceList = sysScenicSpotParkingService.uploadExcelSpotEntrance(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游小伴景区出入口","景区出入口",SysScenicSpotParkingExcel.class,sysScenicSpotEntranceList),"景区出入口"+ dateTime +".xls",response);

        }catch (Exception e){
//            logger.info("导出异常",e);
            e.printStackTrace();
        }
    }


    @ApiOperation("导入景区出入口")
    @RequestMapping("/importSpotEntrance")
    @ResponseBody
    public ReturnModel importSpotEntrance(@RequestPart("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        SysScenicSpot sysScenicSpot = new SysScenicSpot();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysScenicSpotParkingExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotParkingExcel.class, params);
            SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs = new SysScenicSpotParkingWithBLOBs();
            for (SysScenicSpotParkingExcel sysScenicSpotParkingExcel:result){
                //查询出入口id是否存在
//                sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotExcel.getScenicSpotId());
                 sysScenicSpot = sysScenicSpotService.selectBySpotName(sysScenicSpotParkingExcel.getSpotName());
                    sysScenicSpotParkingWithBLOBs = new SysScenicSpotParkingWithBLOBs();
                    sysScenicSpotParkingWithBLOBs.setParkingId(IdUtils.getSeqId());
                    sysScenicSpotParkingWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpotParkingWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                    sysScenicSpotParkingWithBLOBs.setParkingContent(sysScenicSpotParkingExcel.getParkingContent());
                    sysScenicSpotParkingWithBLOBs.setCoordinateType("3");
                    sysScenicSpotParkingWithBLOBs.setParkingScenicSpotId(sysScenicSpot.getScenicSpotId());
                    sysScenicSpotParkingWithBLOBs.setParkingName(sysScenicSpotParkingExcel.getParkingName());
                    sysScenicSpotParkingWithBLOBs.setParkingPinyinName(Tinypinyin.tinypinyin(sysScenicSpotParkingExcel.getParkingName()));
                    sysScenicSpotParkingWithBLOBs.setParkingRange(sysScenicSpotParkingExcel.getParkingRange());
                    sysScenicSpotParkingWithBLOBs.setParkingType(sysScenicSpotParkingExcel.getParkingType());
                    sysScenicSpotParkingWithBLOBs.setParkingCoordinateGroup(sysScenicSpotParkingExcel.getParkingCoordinateGroup());
                    sysScenicSpotParkingWithBLOBs.setParkingCoordinateGroupBaidu(sysScenicSpotParkingExcel.getParkingCoordinateGroupBaidu());
                    sysScenicSpotParkingWithBLOBs.setParkingCoordinateGroupGaode(sysScenicSpotParkingExcel.getParkingCoordinateGroupGaode());
                    int i = sysScenicSpotParkingService.importScenicSpot(sysScenicSpotParkingWithBLOBs);
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
//            logger.info("importBatchNumber", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


}
