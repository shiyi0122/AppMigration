package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.jxzy.AppMigration.NavigationApp.Service.SysFeaturedFoodService;
import com.jxzy.AppMigration.NavigationApp.Service.SysFeaturedFoodShopService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.*;
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
 * @Date 2023/1/9 17:28
 * 特色美食
 */
@Api(tags = "后台管理-特色美食相关接口")
@RestController
@RequestMapping("adminSysFeaturedFoodShop")
@CrossOrigin
public class AdminSysFeaturedFoodShopController {

    @Autowired
    SysFeaturedFoodShopService sysFeaturedFoodShopService;
    @Autowired
    SysFeaturedFoodService sysFeaturedFoodService;
    @Autowired
    SysScenicSpotService sysScenicSpotService;

    /**
     * 后台管理——添加特色美食店铺(带文件)
     *
     * @param
     * @return
     */
    @ApiOperation("添加特色美食店铺")
    @PostMapping("addSysFeaturedFoodShop")
    @ResponseBody
    public ReturnModel addSysFeaturedFoodShop(@RequestPart("file") MultipartFile file, SysFeaturedFoodShop sysFeaturedFoodShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodShopService.addSysFeaturedFoodShop(file, sysFeaturedFoodShop);
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
    @ApiOperation("添加特色美食店铺(无文件)")
    @PostMapping("addSysFeaturedFoodShopN")
    @ResponseBody
    public ReturnModel addSysFeaturedFoodShopN(@RequestBody SysFeaturedFoodShop sysFeaturedFoodShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodShopService.addSysFeaturedFoodShopN(sysFeaturedFoodShop);
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
     * 后台管理——修改特色美食店铺
     *
     * @param
     * @return
     */
    @ApiOperation("修改特色美食店铺")
    @PostMapping("editSysFeaturedFoodShop")
    @ResponseBody
    public ReturnModel editSysFeaturedFoodShop(@RequestPart("file") MultipartFile file, SysFeaturedFoodShop sysFeaturedFoodShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodShopService.editSysFeaturedFoodShop(file, sysFeaturedFoodShop);
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
     * 后台管理——修改特色美食店铺（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("修改特色美食店铺(无文件)")
    @PostMapping("editSysFeaturedFoodShopN")
    @ResponseBody
    public ReturnModel editSysFeaturedFoodShopN(@RequestBody SysFeaturedFoodShop sysFeaturedFoodShop) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodShopService.editSysFeaturedFoodShopN(sysFeaturedFoodShop);
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
     * 后台管理——删除删除店铺
     * @param
     * @param
     * @return
     */
    @ApiOperation("删除删除店铺")
    @GetMapping("delSysFeaturedFoodShop")
    @ResponseBody
    public ReturnModel delSysFeaturedFoodShop(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysFeaturedFoodShopService.delSysFeaturedFoodShop(id);
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


    @ApiOperation("查询特色美食店铺列表")
    @GetMapping("getSysFeaturedFoodShopList")
    @ResponseBody
    public PageDataResult getSysFeaturedFoodShopList(PageDTO pageDTO) {

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
        pageDataResult = sysFeaturedFoodShopService.getSysFeaturedFoodShopList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }

    /**
     * 后台管理——上传特色美食店铺轮播图
     *
     * @param
     * @return
     */
    @ApiOperation("上传特色美食店铺轮播图")
    @PostMapping("addSysFeaturedFoodShopBanner")
    @ResponseBody
    public ReturnModel addSysFeaturedFoodShopBanner(@RequestPart("file") MultipartFile[] file, HttpServletRequest request) {

        ReturnModel returnModel = new ReturnModel();
        String id = request.getParameter("id");

        int i = sysFeaturedFoodShopService.addSysFeaturedFoodShopBanner(file, id);

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
     * 后台管理——上传特色美食店铺轮播图(无文件)
     *
     * @param
     * @return
     */
    @ApiOperation("上传特色美食店铺轮播图（无文件）")
    @PostMapping("addSysFeaturedFoodShopBannerN")
    @ResponseBody
    public ReturnModel addSysFeaturedFoodShopBannerN(Long id,String url) {

        ReturnModel returnModel = new ReturnModel();


        int i = sysFeaturedFoodShopService.addSysFeaturedFoodShopBannerN(id,url);

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
    @ApiOperation("添加特色美食店铺商品")
    @PostMapping("addSysFeaturedFood")
    @ResponseBody
    public ReturnModel addSysFeaturedFood(@RequestPart("file") MultipartFile file, SysFeaturedFood sysFeaturedFood) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodService.addSysFeaturedFood(file, sysFeaturedFood);
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

    @ApiOperation("添加特色美食店铺商品(无文件)")
    @PostMapping("addSysFeaturedFoodN")
    @ResponseBody
    public ReturnModel addSysFeaturedFoodN(@RequestBody SysFeaturedFood sysFeaturedFood) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodService.addSysFeaturedFoodN( sysFeaturedFood);
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
     * 后台管理——修改特色美食店铺商品
     *
     * @param
     * @return
     */
    @ApiOperation("修改特色美食店铺商品")
    @PostMapping("editSysFeaturedFood")
    @ResponseBody
    public ReturnModel editSysFeaturedFood(@RequestPart("file") MultipartFile file, SysFeaturedFood sysFeaturedFood) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodService.editSysFeaturedFood(file, sysFeaturedFood);
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
     * 后台管理——修改特色美食店铺商品（无文件）
     *
     * @param
     * @return
     */
    @ApiOperation("修改特色美食店铺商品(无文件)")
    @PostMapping("editSysFeaturedFoodN")
    @ResponseBody
    public ReturnModel editSysFeaturedFoodN(@RequestBody SysFeaturedFood sysFeaturedFood) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysFeaturedFoodService.editSysFeaturedFoodN(sysFeaturedFood);
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
    @ApiOperation("删除删除店铺商品")
    @GetMapping("delSysFeaturedFood")
    @ResponseBody
    public ReturnModel delSysFeaturedFood(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysFeaturedFoodService.delSysFeaturedFood(id);
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


    @ApiOperation("查询特色美食店铺商品列表")
    @GetMapping("getSysFeaturedFoodList")
    @ResponseBody
    public PageDataResult getSysFeaturedFoodList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (pageDTO.getSpotId() != null) {
            search.put("shopsId", pageDTO.getSpotId());
        }
        if (pageDTO.getContent() != null) {
            search.put("content", pageDTO.getContent());
        }

        pageDataResult = sysFeaturedFoodService.getSysFeaturedFoodList(pageDTO.getPageNum(), pageDTO.getPageSize(), search);
        return pageDataResult;
    }


    @ApiOperation("导入特色美食店铺")
    @RequestMapping("/importSysFeaturedFood")
    @ResponseBody
    public ReturnModel importSysFeaturedFood(@RequestPart("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        SysScenicSpot sysScenicSpot = new SysScenicSpot();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysFeaturedFoodShop> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysFeaturedFoodShop.class, params);
//            SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs = new SysScenicSpotParkingWithBLOBs();
            for (SysFeaturedFoodShop sysFeaturedFoodShop:result){
                //查询出入口id是否存在
//                sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotExcel.getScenicSpotId());
                if(!StringUtils.isEmpty(sysFeaturedFoodShop.getScenicSpotName())){
                    sysScenicSpot = sysScenicSpotService.selectBySpotName(sysFeaturedFoodShop.getScenicSpotName());
                }

                if (!StringUtils.isEmpty(sysScenicSpot)){
                    sysFeaturedFoodShop.setSpotId(sysScenicSpot.getScenicSpotId());
                }

                sysFeaturedFoodShopService.addSysFeaturedFoodShopN(sysFeaturedFoodShop);
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

    @ApiOperation("导出特色美食店铺")
    @RequestMapping(value = "/uploadExcelFeaturedFood")
    public void  uploadExcelFeaturedFood(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒

            Map<String,Object> search = new HashMap<>();

//            if (dataDTO.getSpotId() != null) {
//                search.put("shopsId", dataDTO.getSpotId());
//            }
            if (dataDTO.getContent() != null) {
                search.put("content", dataDTO.getContent());
            }


            List<SysFeaturedFoodShop> sysFeaturedFoodShopList = sysFeaturedFoodShopService.uploadExcelFeaturedFood(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游娱go美食店铺","游娱go美食店铺",SysFeaturedFoodShop.class,sysFeaturedFoodShopList),"游娱go美食店铺"+ dateTime +".xls",response);

        }catch (Exception e){
//            logger.info("导出异常",e);
            e.printStackTrace();
        }
    }




}