package com.jxzy.AppMigration.Administration;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotBroadcastExcel;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import com.rabbitmq.client.Return;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/27 16:15
 */
@Api(tags = "后台管理-景点相关接口")
@RestController
@RequestMapping("adminSysScenicSpotBroadcast")
@CrossOrigin
public class AdminSysScenicSpotBroadcastController extends PublicUtil {

    @Autowired
    SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;


    /**
     * 后台管理景点搜索列表
     *
     * @param pageDTO
     * @return
     */
    @ApiOperation("景点列表查询")
    @GetMapping("/getBroadcastList")
    @ResponseBody
    public PageDataResult getBroadcastList(PageDTO pageDTO) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if (null == pageDTO.getPageNum()) {
                pageDTO.setPageNum(1);
            }
            if (null == pageDTO.getPageSize()) {

                pageDTO.setPageSize(10);
            }
            pageDataResult = sysScenicSpotBroadcastService.getBroadcastList(pageDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageDataResult;
    }

    @ApiOperation("资源详情查询")
    @GetMapping("/getBroadcastDetails")
    @ResponseBody
    public PageDataResult getBroadcastDetails(SearchDTO searchDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysScenicSpotBroadcastService.getBroadcastDetails(searchDTO.getBroadcastId());
        return pageDataResult;
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author
     * @Description 新增景点
     * @Date 10:52 2020/6/8
     * @Param [broadcast, session]
     **/
    @ApiOperation("后台添加景点")
    @PostMapping("/addBroadcast")
    @ResponseBody
    public ReturnModel addBroadcast(@RequestBody SysScenicSpotBroadcast broadcast) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastService.addBroadcast(broadcast);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景点新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景点新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

//    @ApiOperation("景点上传图片(未使用)")
//    @PostMapping("/addBroadcastPicture")
//    @ResponseBody
//    public ReturnModel addBroadcastPicture( @RequestPart("file") MultipartFile file ,Long broadcastId) {
//        ReturnModel returnModel = new ReturnModel();
//        int i = sysScenicSpotBroadcastService.addBroadcastPicture(file , broadcastId);
//        if (i > 0){
//            returnModel.setData("");
//            returnModel.setMsg("景点图片上传成功！");
//            returnModel.setState(Constant.STATE_SUCCESS);
//            return returnModel;
//        }else{
//            returnModel.setData("");
//            returnModel.setMsg("景点图片上传失败！");
//            returnModel.setState(Constant.STATE_FAILURE);
//            return returnModel;
//        }
//
//    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author
     * @Description 删除景点
     * @Date 11:22 2020/6/8
     * @Param [broadcastId]
     **/
    @ApiOperation("后台管理删除景点")
    @GetMapping("/delBroadcast")
    @ResponseBody
    public ReturnModel delBroadcast(@NotBlank(message = "景点ID不能为空") Long broadcastId, @NotBlank(message = "景区ID不能为空") Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastService.delBroadcast(broadcastId, scenicSpotId);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("景点删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景点删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景点删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author
     * @Description 编辑景点
     * @Date 13:12 2020/6/8
     * @Param [broadcast]
     **/
    @ApiOperation("编辑景点")
    @PostMapping("/editBroadcast")
    @ResponseBody
    public ReturnModel editBroadcast(@RequestBody SysScenicSpotBroadcast broadcast) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastService.editBroadcast(broadcast);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景点修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {

            returnModel.setData("");
            returnModel.setMsg("景点修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author
     * @Description 景点内容新增（文字 图片）
     * @Date 13:21 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @ApiOperation("景点内容新增（文字图片）")
    @PostMapping("/addBroadcastContentExtendImage")
    @ResponseBody
    public ReturnModel addBroadcastContentExtendImage(@RequestPart("file") MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {

            int i = sysScenicSpotBroadcastService.addBroadcastContentExtendImage(file, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("景点播报图片文件上传格式不正确！(支持:png，jpg格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("图片大小不可大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景点内容新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author
     * @Description 景点播报内容新增
     * @Date 13:37 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @ApiOperation("景点播报内容新增")
    @PostMapping("/addBroadcastContentExtendAudio")
    @ResponseBody
    public ReturnModel addBroadcastContentExtendAudio(SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastService.addBroadcastContentExtendAudio( sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("音频文件不能为空");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("景点播报音频文件上传格式不正确！(支持:mp3格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 4) {
                returnModel.setData("");
                returnModel.setMsg("景点播报图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 5) {
                returnModel.setData("");
                returnModel.setMsg("请上传文件");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景点内容音频上传失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {

            returnModel.setData("");
            returnModel.setMsg("景点内容音频上传失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("景点内容修改（文字图片）")
    @PostMapping("/exitBroadcastContentExtendImage")
    @ResponseBody
    public ReturnModel exitBroadcastContentExtendImage(@RequestPart("file") MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {

            int i = sysScenicSpotBroadcastService.exitBroadcastContentExtendImage(file, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点内容修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("景点播报图片文件上传格式不正确！(支持:png，jpg格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("图片大小不可大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景点内容修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景点内容修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }


    }


    @ApiOperation("景点播报内容修改")
    @PostMapping("/exitBroadcastContentExtendAudio")
    @ResponseBody
    public ReturnModel exitBroadcastContentExtendAudio(SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
//        try {
//            if (!file.isEmpty()) {
//                int i = sysScenicSpotBroadcastService.exitBroadcastContentExtendAudio(sysScenicSpotBroadcastExtendWithBLOBs);
//                if (i == 1) {
//                    returnModel.setData("");
//                    returnModel.setMsg("景点内容编辑成功！");
//                    returnModel.setState(Constant.STATE_SUCCESS);
//                    return returnModel;
//                } else if (i == 2) {
//                    returnModel.setData("");
//                    returnModel.setMsg("景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                } else {
//                    returnModel.setData("");
//                    returnModel.setMsg("景点内容编辑失败！");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                }
//            } else {
//                returnModel.setData("");
//                returnModel.setMsg("请选择要上传的视频文件！");
//                returnModel.setState(Constant.STATE_FAILURE);
//                return returnModel;
//            }
//        } catch (Exception e) {
//       }
        int i = sysScenicSpotBroadcastService.exitBroadcastContentExtendAudio( sysScenicSpotBroadcastExtendWithBLOBs);

        if (i>0){
            returnModel.setData(i);
            returnModel.setMsg("景点内容编辑成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setMsg("景点内容编辑失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }


    }


    @ApiOperation("景点内容音频删除")
    @PostMapping("/delBroadcastContentExtendAudio")
    @ResponseBody
    public ReturnModel delBroadcastContentExtendAudio(String broadcastResId) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotBroadcastService.delBroadcastContentExtendAudio(broadcastResId);

        if (i>0){
            returnModel.setData("");
            returnModel.setMsg("景点内容音频删除成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }else{
            returnModel.setData("");
            returnModel.setMsg("景点内容音频删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 景点管理导出
     * @param response
     * @param
     * @param
     * @throws Exception
     */
    @ApiOperation("导出景点管理")
    @RequestMapping(value = "/uploadExcelSpotBroadcast")
    public void  uploadExcelSpotBroadcast(HttpServletResponse response, BaseDataDTO dataDTO) throws Exception {
        try {
            Map<String,Object> search = new HashMap<>();
            search.put("startTime",dataDTO.getStartTime());
            search.put("endTime",dataDTO.getEndTime());
            search.put("spotId", dataDTO.getSpotId());
            search.put("broadcastName", dataDTO.getBroadcastName());
            search.put("sort",6);
            List<SysScenicSpotBroadcastExcel> SysScenicSpotBroadcastExcelList = sysScenicSpotBroadcastService.uploadExcelSpotBroadcast(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游小伴景点管理","景点管理",SysScenicSpotBroadcastExcel.class,SysScenicSpotBroadcastExcelList),"景点管理"+ dateTime +".xls",response);

        }catch (Exception e){
            logger.info("导出异常",e);
        }
    }

    /**
     * 景点管理导入
     * @param
     * @param
     * @param
     * @throws Exception
     */
    @ApiOperation("导入景点管理")
    @RequestMapping(value = "/importSpotBroadcast")
    public ReturnModel  importSpotBroadcast(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<SysScenicSpotBroadcastExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotBroadcastExcel.class, params);
            SysScenicSpotBroadcast sysScenicSpotBroadcast = new SysScenicSpotBroadcast();
            for (SysScenicSpotBroadcastExcel sysScenicSpotBroadcastExcel:result){
                //查询景点id是否存在
//                sysScenicSpotBroadcast =  sysScenicSpotBroadcastService.getSpotBroadcastId(sysScenicSpotBroadcastExcel.getBroadcastId());
                sysScenicSpotBroadcast = sysScenicSpotBroadcastService.getSpotBroadcastName(sysScenicSpotBroadcastExcel.getBroadcastName());
                if (ToolUtil.isEmpty(sysScenicSpotBroadcast)){

                    SysScenicSpot sysScenicSpot = sysScenicSpotService.selectBySpotName(sysScenicSpotBroadcastExcel.getScenicSpotName());

                    if (!StringUtils.isEmpty(sysScenicSpot)){

                        sysScenicSpotBroadcast = new SysScenicSpotBroadcast();
                        sysScenicSpotBroadcast.setBroadcastId(IdUtils.getSeqId());
                        sysScenicSpotBroadcast.setScenicSpotName(sysScenicSpotBroadcastExcel.getScenicSpotName());
                        sysScenicSpotBroadcast.setBroadcastName(sysScenicSpotBroadcastExcel.getBroadcastName());
                        sysScenicSpotBroadcast.setCreateDate(com.jxzy.AppMigration.common.utils.DateUtil.currentDateTime());
                        sysScenicSpotBroadcast.setPinYinName(sysScenicSpotBroadcastExcel.getPinYinName());
                        sysScenicSpotBroadcast.setBroadcastGps(sysScenicSpotBroadcastExcel.getBroadcastGps());
                        sysScenicSpotBroadcast.setBroadcastGpsBaiDu(sysScenicSpotBroadcastExcel.getBroadcastGpsBaiDu());
                        sysScenicSpotBroadcast.setScenicSpotRange(sysScenicSpotBroadcastExcel.getScenicSpotRange());
                        sysScenicSpotBroadcast.setIsFeature(sysScenicSpotBroadcastExcel.getIsFeature());
                        sysScenicSpotBroadcast.setFeatureIntroduce(sysScenicSpotBroadcastExcel.getFeatureIntroduce());
                        sysScenicSpotBroadcast.setPictureUrl(sysScenicSpotBroadcastExcel.getPictureUrl());
                        sysScenicSpotBroadcast.setTourDuration(sysScenicSpotBroadcastExcel.getTourDuration());
                        sysScenicSpotBroadcast.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                        sysScenicSpotBroadcast.setIntroduce(sysScenicSpotBroadcastExcel.getIntroduce());
                        int i = sysScenicSpotBroadcastService.importScenicSpotBroadcast(sysScenicSpotBroadcast);

                    }else{
                        continue;
                    }


                }else{
                    sysScenicSpotBroadcast.setScenicSpotName(sysScenicSpotBroadcastExcel.getScenicSpotName());
                    sysScenicSpotBroadcast.setBroadcastName(sysScenicSpotBroadcastExcel.getBroadcastName());
                    sysScenicSpotBroadcast.setCreateDate(com.jxzy.AppMigration.common.utils.DateUtil.currentDateTime());
                    sysScenicSpotBroadcast.setPinYinName(sysScenicSpotBroadcastExcel.getPinYinName());
                    sysScenicSpotBroadcast.setBroadcastGps(sysScenicSpotBroadcastExcel.getBroadcastGps());
                    sysScenicSpotBroadcast.setBroadcastGpsBaiDu(sysScenicSpotBroadcastExcel.getBroadcastGpsBaiDu());
                    sysScenicSpotBroadcast.setScenicSpotRange(sysScenicSpotBroadcastExcel.getScenicSpotRange());
                    sysScenicSpotBroadcast.setIsFeature(sysScenicSpotBroadcastExcel.getIsFeature());
                    sysScenicSpotBroadcast.setFeatureIntroduce(sysScenicSpotBroadcastExcel.getFeatureIntroduce());
                    sysScenicSpotBroadcast.setPictureUrl(sysScenicSpotBroadcastExcel.getPictureUrl());
                    sysScenicSpotBroadcast.setTourDuration(sysScenicSpotBroadcastExcel.getTourDuration());
                    sysScenicSpotBroadcast.setIntroduce(sysScenicSpotBroadcastExcel.getIntroduce());
                    int i = sysScenicSpotBroadcastService.editImportScenicSpot(sysScenicSpotBroadcast);
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


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author
     * @Description 景点音频新增（音频）
     * @Date 13:21 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @ApiOperation("景点音频新增（音频）")
    @PostMapping("/addBroadcastAudio")
    @ResponseBody
    public ReturnModel addBroadcastAudio(@RequestPart("file") MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {

            int i = sysScenicSpotBroadcastService.addBroadcastAudio(file, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点音频新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("景点播报音频文件上传格式不正确！(支持:mp3，wav格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("景点音频新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("景点音频修改")
    @PostMapping("/editBroadcastAudio")
    @ResponseBody
    public ReturnModel editBroadcastAudio(@RequestPart("file") MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {

            int i = sysScenicSpotBroadcastService.editBroadcastAudio(file, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点音频修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("景点音频文件上传格式不正确！(支持:mp3，wav格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景点内容修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景点内容修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author
     * @Description 景点视频新增（视频）
     * @Date 13:21 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @ApiOperation("景点视频新增（视频）")
    @PostMapping("/addBroadcastVideo")
    @ResponseBody
    public ReturnModel addBroadcastVideo(@RequestPart("file") MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {

            int i = sysScenicSpotBroadcastService.addBroadcastVideo(file, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点视频新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景点视频新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景点视频新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 景点视频修改
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @ApiOperation("景点视频修改")
    @PostMapping("/editBroadcastVideo")
    @ResponseBody
    public ReturnModel editBroadcastVideo(@RequestPart("file") MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {

            int i = sysScenicSpotBroadcastService.editBroadcastVideo(file, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点视频修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景点视频修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景点视频修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }


    /**
     * 景点音视频删除

     * @return
     */
    @ApiOperation("景点音视频删除")
    @GetMapping("/delBroadcastType")
    @ResponseBody
    public ReturnModel delBroadcastType(Long broadcastId,String type) {
        ReturnModel returnModel = new ReturnModel();
        try {

            int i = sysScenicSpotBroadcastService.delBroadcastType(broadcastId, type);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("资源删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("资源删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("资源删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("资源删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }









//
//    @ApiOperation("修改景点表的图片路径（请勿乱用）")
//    @PostMapping("/upText")
//    @ResponseBody
//    public void upText() {
//
//       sysScenicSpotBroadcastService.upText();
//
//    }
//
//    @ApiOperation("批量修改景点图片，(只使用一次，请勿使用)")
//    @GetMapping("editspotpicture")
//    @ResponseBody
//    public void editspotpicture(Long spotId) {
//
//        sysScenicSpotBroadcastService.editspotpicture(spotId);
//
//    }


}