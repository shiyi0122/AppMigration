package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.google.gson.internal.$Gson$Preconditions;
import com.jxzy.AppMigration.NavigationApp.Service.SysHotelService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainment;
import com.jxzy.AppMigration.NavigationApp.entity.SysHotel;
import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.FileUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
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
 * @Date 2023/1/11 15:41
 * 酒店民宿
 */

@Api(tags = "后台管理-酒店民宿")
@RestController
@RequestMapping("adminSysHotel")
@CrossOrigin
public class AdminSysHotelController {

    @Autowired
    SysHotelService sysHotelService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    /**
     * 后台管理——添加酒店民宿
     *
     * @param
     * @return
     */
    @ApiOperation("添加酒店民宿")
    @PostMapping("addSysHotel")
    @ResponseBody
    public ReturnModel addSysHotel(@RequestPart("file") MultipartFile file, SysHotel sysHotel) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysHotelService.addSysHotel(file,sysHotel);
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
     * 后台管理——添加酒店民宿（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("添加酒店民宿（无文件）")
    @PostMapping("addSysHotelN")
    @ResponseBody
    public ReturnModel addSysHotelN(@RequestBody SysHotel sysHotel) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysHotelService.addSysHotelN(sysHotel);
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
     * 后台管理——修改酒店民宿
     *
     * @param
     * @return
     */
    @ApiOperation("修改酒店民宿")
    @PostMapping("editSysHotel")
    @ResponseBody
    public ReturnModel editSysHotel(@RequestPart("file") MultipartFile file, SysHotel sysHotel) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysHotelService.editSysHotel(file,sysHotel);
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
     * 后台管理——修改酒店民宿（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("修改酒店民宿(无文件)")
    @PostMapping("editSysHotelN")
    @ResponseBody
    public ReturnModel editSysHotelN(@RequestBody SysHotel sysHotel) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysHotelService.editSysHotelN(sysHotel);
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
     * 后台管理——删除酒店民宿
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除酒店民宿")
    @GetMapping("delSysHotel")
    @ResponseBody
    public ReturnModel delSysHotel(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysHotelService.delSysHotel(id);
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


    /**
     * 后台管理——删除酒店民宿
     * @param
     * @param
     * @return
     */
    @ApiOperation("查询酒店民宿")
    @GetMapping("getSysHotelList")
    @ResponseBody
    public PageDataResult getSysHotelList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();

        search.put("content", pageDTO.getContent());
        search.put("province",pageDTO.getProvince());
        search.put("city",pageDTO.getCity());
        search.put("area",pageDTO.getArea());

        pageDataResult = sysHotelService.getSysHotelList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;
    }



    @ApiOperation("导入特色民宿")
    @RequestMapping("/importSysHotel")
    @ResponseBody
    public ReturnModel importSysHotel(@RequestPart("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        SysScenicSpot sysScenicSpot = new SysScenicSpot();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysHotel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysHotel.class, params);
//            SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs = new SysScenicSpotParkingWithBLOBs();
            for (SysHotel sysHotel:result){
                //查询出入口id是否存在
//                sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotExcel.getScenicSpotId());
                if(!StringUtils.isEmpty(sysHotel.getScenicSpotName())){
                    sysScenicSpot = sysScenicSpotService.selectBySpotName(sysHotel.getScenicSpotName());
                }

                if (!StringUtils.isEmpty(sysScenicSpot)){
                    sysHotel.setSpotId(sysScenicSpot.getScenicSpotId().toString());
                }

                sysHotelService.addSysHotelN(sysHotel);
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

    @ApiOperation("导出酒店民宿")
    @RequestMapping(value = "/uploadExcelHotel")
    public void  uploadExcelHotel(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒

            Map<String,Object> search = new HashMap<>();

            search.put("content", dataDTO.getContent());
            search.put("province",dataDTO.getProvince());
            search.put("city",dataDTO.getCity());
            search.put("area",dataDTO.getArea());
            List<SysHotel> sysHotelList = sysHotelService.uploadExcelHotel(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游娱go酒店民宿","游娱go酒店民宿",SysHotel.class,sysHotelList),"游娱go酒店民宿"+ dateTime +".xls",response);

        }catch (Exception e){
//            logger.info("导出异常",e);
            e.printStackTrace();
        }
    }

}
