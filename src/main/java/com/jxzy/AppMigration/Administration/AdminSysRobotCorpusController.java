package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.jxzy.AppMigration.NavigationApp.Service.SysRobotCorpusService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotExcel;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotShopsExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotCorpus;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author zhang
 * @Date 2022/9/23 16:23
 */
@Api(tags = "后台管理--语音语义管理")
@RestController
@RequestMapping("adminSysRobotCorpus")
@CrossOrigin
public class AdminSysRobotCorpusController extends PublicUtil {

    @Autowired
    SysRobotCorpusService sysRobotCorpusService;

    @ApiOperation("后台管理——语音语义列表查询")
    @GetMapping("/getSysRobotCorpusList")
    @ResponseBody
    public PageDataResult getSysRobotCorpusList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();
        search.put("content", pageDTO.getContent());
        pageDataResult = sysRobotCorpusService.getSysRobotCorpusList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }

    /**
     * 后台管理--添加语音语义
     *
     * @param
     * @return
     */
    @ApiOperation("后台管理--添加语义语义")
    @PostMapping("addSysRobotCorpus")
    @ResponseBody
    public ReturnModel addSysRobotCorpus(@RequestBody SysRobotCorpus sysRobotCorpus) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysRobotCorpusService.addSysRobotCorpus(sysRobotCorpus);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功!");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败!");
        }

        return returnModel;
    }

    @ApiOperation("后台管理--修改语义语义")
    @PostMapping("editSysRobotCorpus")
    @ResponseBody
    public ReturnModel editSysRobotCorpus(@RequestBody SysRobotCorpus sysRobotCorpus) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysRobotCorpusService.editSysRobotCorpus(sysRobotCorpus);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功!");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败!");
        }

        return returnModel;
    }

    @ApiOperation("后台管理--删除语义语义")
    @PostMapping("delSysRobotCorpus")
    @ResponseBody
    public ReturnModel delSysRobotCorpus(Long id) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysRobotCorpusService.delSysRobotCorpus(id);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功!");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败!");
        }
        return returnModel;
    }

    /**
     * 导出问题列表
     *
     * @param response
     * @param dataDTO
     * @throws Exception
     */
    @ApiOperation("导出问题列表")
    @RequestMapping(value = "/uploadExcelShop")
    public void uploadExcelShop(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        Map<String, Object> search = new HashMap<>();
        search.put("content", dataDTO.getContent());
        try {
            List<SysRobotCorpus> list = sysRobotCorpusService.uploadExcelRobotCorpus(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("语音语义管理", "语音语义管理", SysRobotCorpus.class, list), "语音语义管理" + dateTime + ".xls", response);

        } catch (Exception e) {
            logger.info("导出异常", e);
        }
    }

    @ApiOperation("导入语音语义")
    @RequestMapping("/importExcelShop")
    @ResponseBody
    public ReturnModel importExcelShop(@RequestPart("file") MultipartFile multipartFile) {
        ReturnModel returnModel = new ReturnModel();

        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysRobotCorpus> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysRobotCorpus.class, params);
            List<SysRobotCorpus> sysRobotCorpus = new ArrayList<>();
            for (SysRobotCorpus SysRobotCorpus : result) {
                //查询景区id是否存在
                sysRobotCorpus = sysRobotCorpusService.getSysRobotCorpus(SysRobotCorpus.getPinYinProblem());
                if (ToolUtil.isEmpty(sysRobotCorpus)) {

                    int i = sysRobotCorpusService.addSysRobotCorpus(SysRobotCorpus);
                } else {
                   SysRobotCorpus sysRobotCorpus1 = sysRobotCorpus.get(0);
                    sysRobotCorpus1.setPinYinProblem(SysRobotCorpus.getPinYinProblem());
                    sysRobotCorpus1.setCorpusAnswer(SysRobotCorpus.getCorpusAnswer());
                    sysRobotCorpus1.setCreateDate(DateUtil.currentDateTime());
                    sysRobotCorpus1.setCorpusResUrl(SysRobotCorpus.getCorpusResUrl());
                    sysRobotCorpus1.setCorpusResUrlPic(SysRobotCorpus.getCorpusResUrlPic());
                    sysRobotCorpus1.setCorpusType(SysRobotCorpus.getCorpusType());
                    int i = sysRobotCorpusService.editSysRobotCorpus(sysRobotCorpus1);
                }
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("importBatchNumber", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }


    }
}