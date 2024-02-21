package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.jxzy.AppMigration.NavigationApp.Service.SysGoodThingsService;
import com.jxzy.AppMigration.NavigationApp.Service.SysGoodThingsShopService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFood;
import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFoodShop;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 18:02
 * 地道好物
 */

@Api(tags = "后台管理-地道好物")
@RestController
@RequestMapping("adminSysGoodThingsShop")
@CrossOrigin
public class AdminSysGoodThingsShopController {

    @Autowired
    SysGoodThingsShopService sysGoodThingsShopService;
    @Autowired
    SysGoodThingsService sysGoodThingsService;
    @Autowired
    SysScenicSpotService sysScenicSpotService;

    /**
     * 后台管理——添加特色美食店铺
     *
     * @param
     * @return
     */
    @ApiOperation("添加特色美食店铺")
    @PostMapping("addSysGoodThingsShop")
    @ResponseBody
    public ReturnModel addSysGoodThingsShop(@RequestPart("file") MultipartFile file, SysGoodThingsShop sysGoodThingsShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGoodThingsShopService.addSysGoodThingsShop(file, sysGoodThingsShop);
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
     * 后台管理——添加特色美食店铺(无文件)
     *
     * @param
     * @return
     */
    @ApiOperation("添加特色美食店铺（无文件）")
    @PostMapping("addSysGoodThingsShopN")
    @ResponseBody
    public ReturnModel addSysGoodThingsShopN(@RequestBody SysGoodThingsShop sysGoodThingsShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGoodThingsShopService.addSysGoodThingsShopN(sysGoodThingsShop);
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
     * 后台管理——修改地道好物
     *
     * @param
     * @return
     */
    @ApiOperation("修改地道好物")
    @PostMapping("editSysGoodThingsShop")
    @ResponseBody
    public ReturnModel editSysGoodThingsShop(@RequestPart("file") MultipartFile file, SysGoodThingsShop sysGoodThingsShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGoodThingsShopService.editSysGoodThingsShop(file, sysGoodThingsShop);
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
     * 后台管理——修改地道好物
     *
     * @param
     * @return
     */
    @ApiOperation("修改地道好物(无文件)")
    @PostMapping("editSysGoodThingsShopN")
    @ResponseBody
    public ReturnModel editSysGoodThingsShopN(@RequestBody SysGoodThingsShop sysGoodThingsShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGoodThingsShopService.editSysGoodThingsShopN(sysGoodThingsShop);
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
     * 后台管理——删除好物店铺
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除好物店铺")
    @GetMapping("delSysGoodThingsShop")
    @ResponseBody
    public ReturnModel delSysGoodThingsShop(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGoodThingsShopService.delSysGoodThingsShop(id);
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


    @ApiOperation("查询好物店铺列表")
    @GetMapping("getSysGoodThingsShopList")
    @ResponseBody
    public PageDataResult getSysGoodThingsShopList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (pageDTO.getSpotId() != null) {
            search.put("spotId", pageDTO.getSpotId());
        }
        if (pageDTO.getContent() != null) {
            search.put("content", pageDTO.getContent());
        }
        if (pageDTO.getProvince() != null) {
            search.put("province", pageDTO.getProvince());
        }
        if (pageDTO.getCity() != null) {
            search.put("city", pageDTO.getCity());
        }
        if (pageDTO.getArea() != null) {
            search.put("area", pageDTO.getArea());
        }
        pageDataResult = sysGoodThingsShopService.getSysGoodThingsShopList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }


    /**
     * 后台管理——上传好物店铺轮播图
     *
     * @param
     * @return
     */
    @ApiOperation("上传好物店铺轮播图")
    @PostMapping("addSysGoodThingsShopBanner")
    @ResponseBody
    public ReturnModel addSysGoodThingsShopBanner(@RequestPart("file") MultipartFile[] file, HttpServletRequest request) {

        ReturnModel returnModel = new ReturnModel();
        String id = request.getParameter("id");

        int i = sysGoodThingsShopService.addSysGoodThingsShopBanner(file, id);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("上传成功！");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("上传失败！");
        }
        return returnModel;
    }


    /**
     * 后台管理——添加特色美食店铺商品
     *
     * @param
     * @return
     */
    @ApiOperation("添加好物店铺商品")
    @PostMapping("addSysGoodThings")
    @ResponseBody
    public ReturnModel addSysGoodThings(@RequestPart("file") MultipartFile file, SysGoodThingsShop sysGoodThingsShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGoodThingsService.addSysGoodThings(file, sysGoodThingsShop);
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
     * 后台管理——修改好物店铺商品
     *
     * @param
     * @return
     */
    @ApiOperation("修改好物店铺商品")
    @PostMapping("editSysGoodThings")
    @ResponseBody
    public ReturnModel editSysGoodThings(@RequestPart("file") MultipartFile file, SysGoodThingsShop sysGoodThingsShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysGoodThingsService.editSysGoodThings(file, sysGoodThingsShop);
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
     * 后台管理——删除删除店铺商品
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除好物店铺商品")
    @GetMapping("delSysGoodThings")
    @ResponseBody
    public ReturnModel delSysGoodThings(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysGoodThingsService.delSysGoodThings(id);
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


    @ApiOperation("查询好物店铺商品列表")
    @GetMapping("getSysGoodThingsList")
    @ResponseBody
    public PageDataResult getSysGoodThingsList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (pageDTO.getSpotId() != null) {
            search.put("foodShopId", pageDTO.getSpotId());
        }
        if (pageDTO.getContent() != null) {
            search.put("content", pageDTO.getContent());
        }
        pageDataResult = sysGoodThingsService.getSysGoodThingsList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }


    @ApiOperation("导入地道好物店铺")
    @RequestMapping("/importGoodThings")
    @ResponseBody
    public ReturnModel importGoodThings(@RequestPart("file") MultipartFile multipartFile) {
        ReturnModel returnModel = new ReturnModel();
        SysScenicSpot sysScenicSpot = new SysScenicSpot();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysGoodThingsShop> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysGoodThingsShop.class, params);
//            SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs = new SysScenicSpotParkingWithBLOBs();
            for (SysGoodThingsShop sysGoodThingsShop : result) {
                //查询出入口id是否存在
//                sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotExcel.getScenicSpotId());
                if (!StringUtils.isEmpty(sysGoodThingsShop.getScenicSpotName())) {
                    sysScenicSpot = sysScenicSpotService.selectBySpotName(sysGoodThingsShop.getScenicSpotName());
                }

                if (!StringUtils.isEmpty(sysScenicSpot)) {
                    sysGoodThingsShop.setSpotId(sysScenicSpot.getScenicSpotId());
                }

                sysGoodThingsShopService.addSysGoodThingsShopN(sysGoodThingsShop);
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
//            logger.info("importBatchNumber", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("导出地道好物店铺")
    @RequestMapping(value = "/uploadExcelGoodThings")
    public void uploadExcelGoodThings(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒

            Map<String, Object> search = new HashMap<>();

            if (dataDTO.getSpotId() != null) {
                search.put("foodShopId", dataDTO.getShopsId());
            }
            if (dataDTO.getContent() != null) {
                search.put("content", dataDTO.getContent());
            }

            List<SysGoodThingsShop> sysGoodThingsShopList = sysGoodThingsShopService.uploadExcelGoodThings(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游娱go地道好物店铺", "游娱go地道好物店铺", SysGoodThingsShop.class, sysGoodThingsShopList), "游娱go地道好物店铺" + dateTime + ".xls", response);

        } catch (Exception e) {
//            logger.info("导出异常",e);
            e.printStackTrace();
        }
    }


}
