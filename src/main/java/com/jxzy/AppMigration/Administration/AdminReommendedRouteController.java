package com.jxzy.AppMigration.Administration;

import com.google.gson.internal.$Gson$Preconditions;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRecommendedRouteService;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicReommendedRoute;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotBroadcastExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRouteInBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
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
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @Author zhang
 * @Date 2022/8/28 14:29
 */
@Api(tags = "后台管理-伴游线路相关接口")
@RestController
@RequestMapping("adminSysScenicSpot")
@CrossOrigin
public class AdminReommendedRouteController extends PublicUtil {


    @Autowired
    SysScenicSpotRecommendedRouteService sysScenicSpotRecommendedRouteService;

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author
     * @Description 经典路线列表查询
     * @Date 9:38 2020/6/22
     * @Param [pageNum, pageSize, sysScenicSpotRecommendedRoute]
     **/
    @ApiOperation("经典路线列表查询")
    @GetMapping("/getRecommendedRouteList")
    @ResponseBody
    public PageDataResult getRecommendedRouteList(PageDTO pageDTO) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, String> search = new HashMap<>();
        try {
            if (null == pageDTO.getPageNum()) {
                pageDTO.setPageNum(1);
            }
            if (null == pageDTO.getPageSize()) {
                pageDTO.setPageSize(10);
            }
            if (!StringUtils.isEmpty(pageDTO.getContent())) {
                search.put("routeName", pageDTO.getContent());
            }
            if (!StringUtils.isEmpty(pageDTO.getSpotId())) {
                search.put("scenicSpotId", pageDTO.getSpotId().toString());
            }
            if (!StringUtils.isEmpty(pageDTO.getBroadcastName())) {
                search.put("broadcastName", pageDTO.getBroadcastName());
            }
            pageDataResult = sysScenicSpotRecommendedRouteService.getRecommendedRouteList(search, pageDTO.getPageNum(), pageDTO.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageDataResult;
    }

    /**
     * 后台管理——经典线路添加
     *
     * @param sysScenicSpotRecommendedRoute
     * @return
     */
    @ApiOperation("经典路线列表添加")
    @PostMapping("/addRecommendedRoute")
    @ResponseBody
    public ReturnModel addRecommendedRoute(@RequestBody SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotRecommendedRouteService.addRecommendedRoute(sysScenicSpotRecommendedRoute);

        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("经典路线新增成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("经典路线新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * 后台管理经典线路修改
     *
     * @param sysScenicSpotRecommendedRoute
     * @return
     */
    @ApiOperation("经典路线列表修改")
    @PostMapping("/exitRecommendedRoute")
    @ResponseBody
    public ReturnModel exitRecommendedRoute(@RequestBody SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotRecommendedRouteService.exitRecommendedRoute(sysScenicSpotRecommendedRoute);

        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("经典路线修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("经典路线修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 经典线路删除
     *
     * @param routeId
     * @return
     */
    @ApiOperation("经典路线列表删除")
    @GetMapping("/delRecommendedRoute")
    @ResponseBody
    public ReturnModel delRecommendedRoute(Long routeId) {


        ReturnModel returnModel = new ReturnModel();


        int i = sysScenicSpotRecommendedRouteService.delRecommendedRoute(routeId);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("经典路线删除成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("经典路线删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

//    @ApiOperation("经典路线添加线路中的景点(暂不使用)")
//    @PostMapping("/addRouteInBroadcast")
//    @ResponseBody
//    public ReturnModel addRouteInBroadcast(String routeId, String broadcastId) {
//
//        ReturnModel returnModel = new ReturnModel();
//
//        int i = sysScenicSpotRecommendedRouteService.addRouteInBroadcast(routeId, broadcastId);
//        if (i == 1) {
//            returnModel.setData("");
//            returnModel.setMsg("经典路线中景点添加成功！");
//            returnModel.setState(Constant.STATE_SUCCESS);
//            return returnModel;
//        } else {
//            returnModel.setData("");
//            returnModel.setMsg("经典路线中景点添加失败！");
//            returnModel.setState(Constant.STATE_FAILURE);
//            return returnModel;
//        }
//    }

    @ApiOperation("查询经典路线中的景点列表")
    @GetMapping("/getRouteInBroadcastList")
    @ResponseBody
    public PageDataResult getRouteInBroadcast(PageDTO pageDTO) {
        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysScenicSpotRecommendedRouteService.getRouteInBroadcast(pageDTO.getId(), pageDTO.getPageNum(), pageDTO.getPageSize());
        return pageDataResult;
    }

    @ApiOperation("删除经典路线中的景点列表")
    @GetMapping("/delRouteInBroadcast")
    @ResponseBody
    public ReturnModel delRouteInBroadcast(Long id) {

        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotRecommendedRouteService.delRouteInBroadcast(id);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("经典路线中景点删除成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("经典路线中景点删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("获取景区下的所有景点")
    @GetMapping("/getSpotLowerBroadcast")
    @ResponseBody
    public ReturnModel getSpotLowerBroadcast(Long spotId) {

        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotBroadcast> list = sysScenicSpotRecommendedRouteService.getSpotLowerBroadcast(spotId);

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;
    }

    /**
     * 后台管理——经典线路状态修改
     *
     * @return
     */
    @ApiOperation("经典路线状态启用禁用")
    @PostMapping("/exitRecommendedRouteState")
    @ResponseBody
    public ReturnModel exitRecommendedRouteState(String routeId, String routeState) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotRecommendedRouteService.exitRecommendedRouteState(routeId, routeState);
        if (i > 0) {
            returnModel.setData(i);
            returnModel.setMsg("状态修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("状态修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }


    }

    /**
     * 后台管理——经典线路导出
     *
     * @return
     */
    @ApiOperation("经典线路导出")
    @PostMapping("/uploadExcelRecommendedRoute")
    @ResponseBody
    public void uploadExcelRecommendedRoute(HttpServletResponse response, BaseDataDTO dataDTO) {

        try {
            ReturnModel returnModel = new ReturnModel();

            Map<String, String> search = new HashMap<>();
            if (!StringUtils.isEmpty(dataDTO.getContent())) {
                search.put("routeName", dataDTO.getContent());
            }
            if (!StringUtils.isEmpty(dataDTO.getSpotId())) {
                search.put("scenicSpotId", dataDTO.getSpotId().toString());
            }
            if (!StringUtils.isEmpty(dataDTO.getBroadcastName())) {
                search.put("broadcastName", dataDTO.getBroadcastName());
            }

            List<SysScenicReommendedRoute> list = sysScenicSpotRecommendedRouteService.uploadExcelRecommendedRoute(search);

            String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
            FileUtil.exportExcel(FileUtil.getWorkbook("游小伴线路管理", "线路管理", SysScenicReommendedRoute.class, list), "线路管理" + dateTime + ".xls", response);
        } catch (Exception e) {
            logger.info("导出异常", e);
        }

    }

    @ApiOperation("导入景点线路(未完成)")
    @RequestMapping("/importRecommendedRoute")
    @ResponseBody
    public ReturnModel importRecommendedRoute(@RequestPart("file") MultipartFile multipartFile) {

        ReturnModel returnModel = new ReturnModel();

        return returnModel;
    }

}