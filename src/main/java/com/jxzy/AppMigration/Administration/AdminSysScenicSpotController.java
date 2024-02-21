package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotExcel;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotFilesExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
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
import org.springframework.data.domain.jaxb.SpringDataJaxb;
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
 * @Date 2022/8/27 16:15
 */
@Api(tags = "后台管理-景区档案,相关接口")
@RestController
@RequestMapping("adminSysScenicSpot")
@CrossOrigin
public class AdminSysScenicSpotController extends PublicUtil {

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    /**
     * 后台管理——景区列表
     *
     * @param pageDTO
     * @return
     */

    @ApiOperation("管理后台景区档案查询景区列表")
    @GetMapping("adminSysScenicSpotList")
    @ResponseBody
    public PageDataResult adminSysScenicSpotList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        if (StringUtils.isEmpty(pageDTO.getSort())) {
            pageDTO.setSort(6);
        }
        Map<String, Object> search = new HashMap<>();
//        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
//            search.put("spotId",pageDTO.getSpotId());
//        }
        if (!StringUtils.isEmpty(pageDTO.getSpotName())){
            search.put("spotName",pageDTO.getSpotName());
        }
        if (!StringUtils.isEmpty(pageDTO.getType())){
            search.put("robotWakeupWords",pageDTO.getType());
        }
        if (!StringUtils.isEmpty(pageDTO.getScenicSpotFid())){
            search.put("spotFid",pageDTO.getScenicSpotFid());
        }
        if (!StringUtils.isEmpty(pageDTO.getScenicSpotSid())){
            search.put("spotSid", pageDTO.getScenicSpotSid());
        }
        if (!StringUtils.isEmpty(pageDTO.getScenicSpotQid())){
            search.put("spotQid",pageDTO.getScenicSpotQid());
        }
        search.put("sort",pageDTO.getSort());
        pageDataResult = sysScenicSpotService.adminSysScenicSpotList(pageDTO.getPageNum(),pageDTO.getPageSize(),search);
        return pageDataResult;
    }

    @ApiOperation("管理后台景区档案添加景区")
    @PostMapping("adminAddSysScenicSpot")
    @ResponseBody
    public ReturnModel adminAddSysScenicSpot(@RequestBody SysScenicSpot sysScenicSpot) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotService.adminAddSysScenicSpot(sysScenicSpot);
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
     * 管理后台修改景区
     *
     * @param sysScenicSpot
     * @return
     */
    @ApiOperation("管理后台景区档案修改景区")
    @PostMapping("adminEditSysScenicSpot")
    @ResponseBody
    public ReturnModel adminEditSysScenicSpot(@RequestBody SysScenicSpot sysScenicSpot) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.adminEditSysScenicSpot(sysScenicSpot);

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

    @ApiOperation("管理后台景区档案删除景区")
    @PostMapping("adminDelSysScenicSpot")
    @ResponseBody
    public ReturnModel adminDelSysScenicSpot(@RequestBody SearchDTO searchDTO) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.adminDelSysScenicSpot(searchDTO.getSpotId());
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
     * 景区下拉选
     */
    @ApiOperation("景区下拉选")
    @GetMapping("scenicSpotDrop")
    @ResponseBody
    public ReturnModel scenicSpotDrop() {

        ReturnModel returnModel = new ReturnModel();
        List<SysScenicSpot> list = sysScenicSpotService.scenicSpotDrop();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功！");
        return returnModel;
    }

    /**
     * 归属地下拉选
     */
    @ApiOperation("归属地下拉选")
    @GetMapping("placeDrop")
    @ResponseBody
    public ReturnModel placeDrop() {

        ReturnModel returnModel = new ReturnModel();
        List<SysScenicSpotBinding> list = sysScenicSpotService.placeDrop();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功！");
        return returnModel;
    }


    /**
     * 后台管理——上传景区语音讲解
     * @param file
     * @param scenicSpotId
     * @return
     */
    @ApiOperation("上传景区语音讲解（暂时不用）")
    @PostMapping("adminAddSysScenicSpotAudio")
    @ResponseBody
    public ReturnModel adminAddSysScenicSpotAudio(@RequestPart("file")MultipartFile file, Long scenicSpotId) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotService.adminAddSysScenicSpotAudio(file,scenicSpotId);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("上传成功！");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("上传失败！");
            return returnModel;
        }
    }

    /**
     * 景区档案导出
     * @param response
     * @param
     * @param
     * @throws Exception
     */
    @ApiOperation("导出景区档案")
    @RequestMapping(value = "/uploadExcelSpot")
    public void  uploadExcelSpot(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒

            Map<String,Object> search = new HashMap<>();

            if (!StringUtils.isEmpty(dataDTO.getSpotName())){
                search.put("spotName",dataDTO.getSpotName());
            }
            if (!StringUtils.isEmpty(dataDTO.getType())){
                search.put("robotWakeupWords",dataDTO.getType());
            }
            if (!StringUtils.isEmpty(dataDTO.getSpotBindingId())){
                search.put("spotFid",dataDTO.getSpotBindingId());
            }
            search.put("sort",6);
            List<SysScenicSpotExcel> sysScenicSpotList = sysScenicSpotService.uploadExcelSpot(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游小伴景区档案","景区档案",SysScenicSpotExcel.class,sysScenicSpotList),"景区档案"+ dateTime +".xls",response);

        }catch (Exception e){
            logger.info("导出异常",e);
        }
    }

    /**
     * 景区档案导入景区档案
     * @param
     * @param
     * @param
     * @throws Exception
     */
    @ApiOperation("导入景区档案")
    @RequestMapping("/importSpot")
    @ResponseBody
    public ReturnModel importSpot(@RequestPart("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysScenicSpotExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotExcel.class, params);
            SysScenicSpot sysScenicSpot = new SysScenicSpot();
            for (SysScenicSpotExcel sysScenicSpotExcel:result){
                //查询景区id是否存在
//                sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotExcel.getScenicSpotId());

                sysScenicSpot = sysScenicSpotService.selectBySpotName(sysScenicSpotExcel.getScenicSpotName());

                if (ToolUtil.isEmpty(sysScenicSpot)){
                    sysScenicSpot = new SysScenicSpot();
                    sysScenicSpot.setScenicSpotId(IdUtils.getSeqId());
                    sysScenicSpot.setScenicSpotName(sysScenicSpotExcel.getScenicSpotName());
                    sysScenicSpot.setStartLevel(sysScenicSpotExcel.getStartLevel());
                    sysScenicSpot.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpot.setRobotWakeupWords(sysScenicSpotExcel.getRobotWakeupWords());
                    sysScenicSpot.setScenicSpotContact(sysScenicSpotExcel.getScenicSpotContact());
                    sysScenicSpot.setScenicSpotPhone(sysScenicSpotExcel.getScenicSpotPhone());
                    sysScenicSpot.setScenicSpotEmail(sysScenicSpotExcel.getScenicSpotEmail());
                    sysScenicSpot.setScenicSpotAddres(sysScenicSpotExcel.getScenicSpotAddres());
                    sysScenicSpot.setScenicSpotPostalCode(sysScenicSpotExcel.getScenicSpotPostalCode());
                    sysScenicSpot.setScenicSpotFname(sysScenicSpotExcel.getScenicSpotFname());
                    sysScenicSpot.setRobotWakeupWords(sysScenicSpotExcel.getRobotWakeupWords());
                    sysScenicSpot.setScenicSpotCompany(sysScenicSpotExcel.getScenicSpotCompany());
                    int i = sysScenicSpotService.importScenicSpot(sysScenicSpot);
                }else{
                    sysScenicSpot.setScenicSpotName(sysScenicSpotExcel.getScenicSpotName());
                    sysScenicSpot.setStartLevel(sysScenicSpotExcel.getStartLevel());
                    sysScenicSpot.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpot.setRobotWakeupWords(sysScenicSpotExcel.getRobotWakeupWords());
                    sysScenicSpot.setScenicSpotContact(sysScenicSpotExcel.getScenicSpotContact());
                    sysScenicSpot.setScenicSpotPhone(sysScenicSpotExcel.getScenicSpotPhone());
                    sysScenicSpot.setScenicSpotEmail(sysScenicSpotExcel.getScenicSpotEmail());
                    sysScenicSpot.setScenicSpotAddres(sysScenicSpotExcel.getScenicSpotAddres());
                    sysScenicSpot.setScenicSpotPostalCode(sysScenicSpotExcel.getScenicSpotPostalCode());
                    sysScenicSpot.setScenicSpotFname(sysScenicSpotExcel.getScenicSpotFname());
                    sysScenicSpot.setRobotWakeupWords(sysScenicSpotExcel.getRobotWakeupWords());
                    sysScenicSpot.setScenicSpotCompany(sysScenicSpotExcel.getScenicSpotCompany());
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


//    /**
//     * 景区录入拼音
//     */
//    @ApiOperation("景区录入拼音(只使用一次)")
//    @GetMapping("adminPinyin")
//    @ResponseBody
//    public void adminPinyin() {
//
//        ReturnModel returnModel = new ReturnModel();
//        sysScenicSpotService.adminPinyin();
//
//    }


}