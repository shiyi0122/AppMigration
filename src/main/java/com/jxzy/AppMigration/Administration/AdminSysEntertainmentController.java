package com.jxzy.AppMigration.Administration;




import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.jxzy.AppMigration.NavigationApp.Service.SysEntertainmentService;
import com.jxzy.AppMigration.NavigationApp.Service.SysGameService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotParkingExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainment;
import com.jxzy.AppMigration.NavigationApp.entity.SysGame;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
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
 * @Date 2023/1/11 17:19
 * 娱乐设施(新)
 */

@Api(tags = "后台管理-娱乐设施（新）")
@RestController
@RequestMapping("adminSysEntertainment")
@CrossOrigin
public class    AdminSysEntertainmentController {


//    @Autowired
//    SysGameService sysGameService;

    @Autowired
    SysEntertainmentService sysEntertainmentService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    /**
     * 添加娱乐设施
     * @param file
     * @param
     * @return
     */

    @ApiOperation("添加娱乐设施")
    @PostMapping("addSysEntertainment")
    @ResponseBody
    public ReturnModel addSysEntertainment(@RequestPart("file") MultipartFile file, SysEntertainment sysEntertainment) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysEntertainmentService.addSysEntertainment(file, sysEntertainment);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 添加娱乐设施（无文件）
     * @param
     * @param
     * @return
     */

    @ApiOperation("添加娱乐设施(无文件)")
    @PostMapping("addSysEntertainmentN")
    @ResponseBody
    public ReturnModel addSysEntertainmentN(@RequestBody SysEntertainment sysEntertainment) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysEntertainmentService.addSysEntertainmentN(sysEntertainment);
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
     * 修改娱乐设施
     * @param file
     * @param
     * @return
     */
    @ApiOperation("修改娱乐设施")
    @PostMapping("editSysEntertainment")
    @ResponseBody
    public ReturnModel editSysEntertainment(@RequestPart("file") MultipartFile file, SysEntertainment sysEntertainment) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysEntertainmentService.editSysEntertainment(file, sysEntertainment);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }

    /**
     * 修改娱乐设施（无文件）
     * @param
     * @param
     * @return
     */
    @ApiOperation("修改娱乐设施(无文件)")
    @PostMapping("editSysEntertainmentN")
    @ResponseBody
    public ReturnModel editSysEntertainmentN(@RequestBody SysEntertainment sysEntertainment) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysEntertainmentService.editSysEntertainmentN(sysEntertainment);
        if (i == 1) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功！");
        } else if (i == 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败！");
        } else if (i == 2) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件类型不对！");
        } else if (i == 3) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("文件为空！");
        }
        return returnModel;
    }


    /**
     * 后台管理——删除娱乐设施
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除娱乐设施")
    @GetMapping("delSysEntertainment")
    @ResponseBody
    public ReturnModel delSysEntertainment(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysEntertainmentService.delSysEntertainment(id);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }

    /**
     * 查询娱乐设施列表
     * @param pageDTO
     * @return
     */
    @ApiOperation("查询娱乐设施列表")
    @GetMapping("getSysEntertainmentList")
    @ResponseBody
    public PageDataResult getSysEntertainmentList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (pageDTO.getSpotId() != null) {
            search.put("spotId", pageDTO.getSpotId());
        }
        if (pageDTO.getContent() != null) {
            search.put("content", pageDTO.getContent());
        }
        if (pageDTO.getProvince() != null){
            search.put("province",pageDTO.getProvince());
        }
        if (pageDTO.getCity() != null){
            search.put("city",pageDTO.getCity());
        }
        if (pageDTO.getArea() != null){
            search.put("area",pageDTO.getArea());
        }
        if (pageDTO.getIsPeriphery() != null){
            search.put("isPeriphery",pageDTO.getIsPeriphery());
        }
        pageDataResult = sysEntertainmentService.getSysEntertainmentList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }

    @ApiOperation("导入景区娱乐设施（周边）")
    @RequestMapping("/importEntertainment")
    @ResponseBody
    public ReturnModel importEntertainment(@RequestPart("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        SysScenicSpot sysScenicSpot = new SysScenicSpot();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysEntertainment> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysEntertainment.class, params);
//            SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs = new SysScenicSpotParkingWithBLOBs();
            for (SysEntertainment sysEntertainment:result){
                //查询出入口id是否存在
//                sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotExcel.getScenicSpotId());
                sysScenicSpot = sysScenicSpotService.selectBySpotName(sysEntertainment.getScenicSpotName());

                if (!StringUtils.isEmpty(sysScenicSpot)){
                    sysEntertainment.setAscriptionSpotId(sysScenicSpot.getScenicSpotId());
                }
                sysEntertainment.setIsPeriphery("1");
                sysEntertainmentService.addSysEntertainmentN(sysEntertainment);
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

    @ApiOperation("导出娱乐设施(周边)")
    @RequestMapping(value = "/uploadExcelEntertainment")
    public void  uploadExcelEntertainment(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒

            Map<String,Object> search = new HashMap<>();

            if (dataDTO.getSpotId() != null) {
                search.put("spotId", dataDTO.getSpotId());
            }
            if (dataDTO.getContent() != null) {
                search.put("content", dataDTO.getContent());
            }
            if (dataDTO.getIsPeriphery() != null){
                search.put("isPeriphery",dataDTO.getIsPeriphery());
            }

            List<SysEntertainment> sysScenicSpotEntranceList = sysEntertainmentService.uploadExcelEntertainment(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游娱go娱乐设施(周边)","游娱go娱乐设施(周边)",SysEntertainment.class,sysScenicSpotEntranceList),"游娱go娱乐设施(周边)"+ dateTime +".xls",response);

        }catch (Exception e){
//            logger.info("导出异常",e);
            e.printStackTrace();
        }
    }

}
