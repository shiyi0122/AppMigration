package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotFilesExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBanner;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotImg;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.GroovyWebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.ldap.PagedResultsControl;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/29 13:10
 */
@Api(tags = "后台管理-景区管理,相关接口")
@RestController
@RequestMapping("adminSysScenicSpotFiles")
@CrossOrigin
public class AdminSysScenicSpotFilesController  extends PublicUtil {

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @ApiOperation("景区管理查询景区列表")
    @GetMapping("getSpotFilesList")
    @ResponseBody
    public PageDataResult getSpotFilesList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())) {
            search.put("spotId", pageDTO.getSpotId());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotName())){
            search.put("spotName",pageDTO.getSpotName());
        }
        if (!StringUtils.isEmpty(pageDTO.getStartTime())) {
            search.put("startTime", pageDTO.getStartTime());
        }
        if (!StringUtils.isEmpty(pageDTO.getEndTime())) {
            search.put("endTime", pageDTO.getEndTime());
        }
        pageDataResult = sysScenicSpotService.adminSysScenicSpotFilesList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }


    /**
     * 景区管理——添加景区轮播图
     * 张
     * @param file
     * @param sysScenicSpotBanner
     * @return
     */
    @ApiOperation("景区管理——添加景区轮播图")
    @PostMapping("addSpotBanners")
    @ResponseBody
    public ReturnModel addSpotBanners(@RequestPart("file") MultipartFile[] file, SysScenicSpotBanner sysScenicSpotBanner) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.addSpotBanners(file, sysScenicSpotBanner);

        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("景区图片添加成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }else if (i == 2) {
            returnModel.setData("");
            returnModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }else if (i == 3){
            returnModel.setData("");
            returnModel.setMsg("景区图片无法大于2M！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }else {
            returnModel.setData("");
            returnModel.setMsg("景区图片添加失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * 景区管理——修改景区轮播图
     * 张
     * @param file
     * @param sysScenicSpotBanner
     * @return
     */
    @ApiOperation("景区管理——修改景区轮播图")
    @PostMapping("editSpotBanners")
    @ResponseBody
    public ReturnModel editSpotBanners(@RequestPart("file") MultipartFile[] file, SysScenicSpotBanner sysScenicSpotBanner) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.editSpotBanners(file, sysScenicSpotBanner);

        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("景区图片修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }else if (i == 2) {
            returnModel.setData("");
            returnModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }else if (i == 3){
            returnModel.setData("");
            returnModel.setMsg("景区图片无法大于2M！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }else {
            returnModel.setData("");
            returnModel.setMsg("景区图片修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }




    }
        /**
         * 景区管理——景区轮播图列表查询
         * @param pageDTO
         * @return
         */
    @ApiOperation("景区管理——查询景区轮播图列表")
    @GetMapping("getSpotFilesBanners")
    @ResponseBody
    public PageDataResult getSpotFilesBanners(PageDTO pageDTO) {
        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = sysScenicSpotService.getSpotFilesBanners(pageDTO.getSpotId(),pageDTO.getPageNum(),pageDTO.getPageSize());
        return pageDataResult;
    }


    /**
     * 景区管理——删除景区轮播图
     * @param searchDTO
     * @return
     */
    @ApiOperation("景区管理——删除景区轮播图")
    @GetMapping("delSpotFilesBanner")
    @ResponseBody
    public ReturnModel delSpotFilesBanner(SearchDTO searchDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.delSpotFilesBanner(searchDTO.getId());

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }

    /**
     * 景区管理——添加景区坐标以及图片
     * 张
     * @param
     * @param
     * @return
     */
    @ApiOperation("景区管理——添加景区坐标")
    @PostMapping("addSpotFiles")
    @ResponseBody
    public ReturnModel addSpotFiles(@RequestBody SysScenicSpot sysScenicSpot) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.addSpotFiles(sysScenicSpot);
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

    @ApiOperation("景区管理——删除景区坐标以及图片")
    @GetMapping("delSpotFiles")
    @ResponseBody
    public ReturnModel delSpotFiles(String spotId) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotService.delSpotFiles(spotId);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败！");
        }
        return returnModel;
    }

    @ApiOperation("景区管理——修改景区坐标")
    @PostMapping("editSpotFiles")
    @ResponseBody
    public ReturnModel editSpotFiles(@RequestBody SysScenicSpot sysScenicSpot) {

        ReturnModel returnModel = new ReturnModel();
        int i =  sysScenicSpotService.editSpotFiles(sysScenicSpot);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("编辑成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("编辑失败！");
        }
        return returnModel;
    }

    @ApiOperation("景区管理—上传景区图片")
    @PostMapping("addSpotPicture")
    @ResponseBody
    public ReturnModel addSpotPicture(@RequestPart("file") MultipartFile file,Long spotId) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotService.addSpotPicture(file,spotId);
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
         * 后台管理—— 修改轮播图启用禁用状态
         * @param id
         * @param type
         * @return
     * */
    @ApiOperation("修改轮播图启用禁用状态")
    @PostMapping("editSpotFilesState")
    @ResponseBody
    public ReturnModel editSpotFilesState(Long id,Integer type) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.editSpotFilesState(id,type);

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
     * 修改景区启用禁用状态
     * @param spotId
     * @param type
     * @return
     */
    @ApiOperation("修改景区启用禁用状态")
    @GetMapping("editSpotState")
    @ResponseBody
    public ReturnModel editSpotState(Long spotId,Integer type) {
        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.editSpotState(spotId ,type);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("状态修改成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("状态修改失败！");
        }
        return returnModel;
    }

    /**
     * 导出
     * @param response
     * @param
     * @param
     * @throws Exception
     */
    @ApiOperation("导出")
    @RequestMapping(value = "/uploadExcelSpotFiles")
    public void  uploadExcelSpotFiles(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒
            List<SysScenicSpot> orderListByExample = null;
            Map<String,Object> search = new HashMap<>();
            search.put("spotName",dataDTO.getSpotName());

            List<SysScenicSpotFilesExcel> sysScenicSpotList = sysScenicSpotService.getOrderVoExcelPoi(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游小伴景区管理","景区管理",SysScenicSpotFilesExcel.class,sysScenicSpotList),"景区管理"+ dateTime +".xls",response);

        }catch (Exception e){
            logger.info("导出异常",e);
        }
    }

    /**
     * 导入景区档案
     * @param multipartFile
     * @return
     */
    @ApiOperation("导入景区管理")
    @RequestMapping("/importScenicSpot")
    @ResponseBody
    public ReturnModel importScenicSpot(@RequestPart("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysScenicSpotFilesExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotFilesExcel.class, params);
            SysScenicSpot sysScenicSpot = new SysScenicSpot();

            for (SysScenicSpotFilesExcel sysScenicSpotFilesExcel:result){
                //查询景区id是否存在
//                 sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotFilesExcel.getScenicSpotId());
                sysScenicSpot = sysScenicSpotService.selectBySpotName(sysScenicSpotFilesExcel.getScenicSpotName());

                if (ToolUtil.isEmpty(sysScenicSpot)){
                    sysScenicSpot = new SysScenicSpot();
                    sysScenicSpot.setScenicSpotId(IdUtils.getSeqId());
                    sysScenicSpot.setScenicSpotName(sysScenicSpotFilesExcel.getScenicSpotName());
                    sysScenicSpot.setPinYingName(sysScenicSpotFilesExcel.getPinYingName());
                    sysScenicSpot.setScenicSpotSlogan(sysScenicSpotFilesExcel.getScenicSpotSlogan());
                    sysScenicSpot.setIntroduction(sysScenicSpotFilesExcel.getIntroduction());
                    sysScenicSpot.setCoordinateRangeBaiDu(sysScenicSpotFilesExcel.getCoordinateRangeBaiDu());
                    sysScenicSpot.setCoordinateRange(sysScenicSpotFilesExcel.getCoordinateRange());
                    sysScenicSpot.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpot.setCoordinateRangeAdius(sysScenicSpotFilesExcel.getCoordinateRangeAdius());
                    sysScenicSpot.setRobotWakeupWords(sysScenicSpotFilesExcel.getRobotWakeupWords());
                    sysScenicSpot.setScenicSpotContact(sysScenicSpotFilesExcel.getScenicSpotContact());
                    sysScenicSpot.setScenicSpotPhone(sysScenicSpotFilesExcel.getScenicSpotPhone());
                    int i = sysScenicSpotService.importScenicSpot(sysScenicSpot);
                }else{
                    sysScenicSpot.setScenicSpotName(sysScenicSpotFilesExcel.getScenicSpotName());
                    sysScenicSpot.setPinYingName(sysScenicSpotFilesExcel.getPinYingName());
                    sysScenicSpot.setScenicSpotSlogan(sysScenicSpotFilesExcel.getScenicSpotSlogan());
                    sysScenicSpot.setIntroduction(sysScenicSpotFilesExcel.getIntroduction());
                    sysScenicSpot.setCoordinateRangeBaiDu(sysScenicSpotFilesExcel.getCoordinateRangeBaiDu());
                    sysScenicSpot.setCoordinateRange(sysScenicSpotFilesExcel.getCoordinateRange());
                    sysScenicSpot.setCoordinateRangeAdius(sysScenicSpotFilesExcel.getCoordinateRangeAdius());
                    sysScenicSpot.setRobotWakeupWords(sysScenicSpotFilesExcel.getRobotWakeupWords());
                    sysScenicSpot.setScenicSpotContact(sysScenicSpotFilesExcel.getScenicSpotContact());
                    sysScenicSpot.setScenicSpotPhone(sysScenicSpotFilesExcel.getScenicSpotPhone());
                    int i = sysScenicSpotService.editImportScenicSpot(sysScenicSpot);
                }


            }

            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("importBatchNumber", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }



}