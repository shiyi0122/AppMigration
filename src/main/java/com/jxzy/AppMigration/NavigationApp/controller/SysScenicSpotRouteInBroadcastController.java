package com.jxzy.AppMigration.NavigationApp.controller;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastService;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRouteInBroadcastService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;
import com.jxzy.AppMigration.NavigationApp.entity.dto.BaseDataDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/9 16:28
 */
@Api(tags = "热门线路,景区全景等相关接口")
@RestController
@RequestMapping("routeInBroadcast")
public class SysScenicSpotRouteInBroadcastController {

    @Autowired
    SysScenicSpotRouteInBroadcastService sysScenicSpotRouteInBroadcastService;

    @Autowired
    SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    /**
     * 获取热门线路
     * 张
     * @param pageDTO
     * @return
     */
    @ApiOperation("获取热门线路")
    @GetMapping("getHotRouteInBroadcastList")
    @ResponseBody
    public PageDataResult getHotRouteInBroadcastList(PageDTO pageDTO){

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }
        search.put("pageNum",pageDTO.getPageNum());
        search.put("pageSize",pageDTO.getPageSize());

        pageDataResult = sysScenicSpotRouteInBroadcastService.getRouteInBroadcastList(search);

        return pageDataResult;
    }

    /**
     * 全部线路
     * 张
     */
    @ApiOperation("获取全部线路")
    @GetMapping("getRouteInBroadcastAll")
    @ResponseBody
    public PageDataResult getRouteInBroadcastAll(PageDTO pageDTO){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }
        search.put("pageNum",pageDTO.getPageNum());
        search.put("pageSize",pageDTO.getPageSize());

        pageDataResult = sysScenicSpotRouteInBroadcastService.getRouteInBroadcastAll(search);

        return pageDataResult;
    }

    /**
     * 点击线路热度加一
     *
     * 张
     */
    @ApiOperation("点击线路热度加一")
    @GetMapping("addHotRouteInBroadcast")
    @ResponseBody
    public ReturnModel addHotRouteInBroadcast(BaseDataDTO baseDataDTO){

        ReturnModel returnModel = new ReturnModel();
        Long id = baseDataDTO.getId();
        int i = sysScenicSpotRouteInBroadcastService.addHotRouteInBroadcast(id);
        if (i>0){
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setData(i);
            returnModel.setMsg("加一成功");
        }else{
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData(i);
            returnModel.setMsg("加一失败");
        }
        return returnModel;

    }

    /**
     * 获取查询景点列表
     *张
     */
    @ApiOperation("获取查询景点列表")
    @GetMapping("getSpotBroadcastList")
    @ResponseBody
    public PageDataResult getSpotBroadcastList(PageDTO pageDTO){

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("lat",pageDTO.getLat());
        search.put("lng",pageDTO.getLng());
        if (!StringUtils.isEmpty(pageDTO.getSpotId())){
            search.put("spotId",pageDTO.getSpotId());
        }
        if (!StringUtils.isEmpty(pageDTO.getSpotName()))        {
            search.put("spotName",pageDTO.getSpotName());
        }
        pageDataResult =  sysScenicSpotBroadcastService.getSpotBroadcastList(pageDTO.getPageNum(),pageDTO.getPageSize(),pageDTO.getSort(),search);
        return pageDataResult;
    }

    @ApiOperation("景点热度加一")
    @GetMapping("addSpotBroadcast")
    @ResponseBody
    public ReturnModel addHotSpotBroadcast(BaseDataDTO  baseDataDTO){

        ReturnModel returnModel = new ReturnModel();
        Long id = baseDataDTO.getId();
        int i = sysScenicSpotBroadcastService.addHotSpotBroadcast(id);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("加一成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("加一失败");
        }
        return returnModel;
    }


    @ApiOperation("景区全景-当前位置-距离较近的景点")
    @GetMapping("spotPanorama")
    @ResponseBody
    public  ReturnModel spotPanorama(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区id为空！");
            return returnModel;
        }
        if (StringUtils.isEmpty(searchDTO.getLat()) && StringUtils.isEmpty(searchDTO.getLng())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("当前坐标为空！");
            return returnModel;
        }
        List<SysScenicSpotBroadcast> list  = sysScenicSpotRouteInBroadcastService.spotPanorama(searchDTO.getSpotId(),searchDTO.getLat(),searchDTO.getLng());

        returnModel.setData(list);
        returnModel.setMsg("查询成功");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

    /**
     * 根据两个坐标点，计算相距距离
     * 涨
     */
    @ApiOperation("计算两个坐标点之间的距离")
    @GetMapping("coordinateDistance")
    @ResponseBody
    public ReturnModel coordinateDistance(String lat,String log,String latTwo,String logTwo){

        ReturnModel returnModel = new ReturnModel();


        Point2D.Double from=new Point2D.Double(  Double.valueOf(log) ,Double.valueOf(lat));
        Point2D.Double to=new Point2D.Double( Double.valueOf(logTwo),Double.valueOf(latTwo));
        double distanceOne = LngLonUtil.calculateWithSdk(from,to);
//
        returnModel.setData(distanceOne);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }

    /**
     * 伴游线路
     * 张
     */
    @ApiOperation("伴游线路(暂不使用)")
    @GetMapping("lineDetails")
    @ResponseBody
    public ReturnModel lineDetails(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区id为空，无法查询");
            return  returnModel;
        }
        if(StringUtils.isEmpty(searchDTO.getId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("热门线路id为空，无法查询");
            return  returnModel;
        }

        if(StringUtils.isEmpty(searchDTO.getLat()) && StringUtils.isEmpty(searchDTO.getLng())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("当前坐标为空，无法查询");
            return  returnModel;
        }

        List<SysScenicSpotBroadcast>  list =  sysScenicSpotRouteInBroadcastService.lineDetails(searchDTO.getSpotId(),searchDTO.getId(),searchDTO.getLat(),searchDTO.getLng());

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }


    /**
     * 伴游线路页面中的推荐线路
     * 张
     */
    @ApiOperation("伴游线路页面中的推荐线路(暂不使用)")
    @GetMapping("recommendLine")
    @ResponseBody
    public ReturnModel recommendLine(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();

        //判断景点id是否为空
        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景点spotId为空，无法查询推荐线路");
            return returnModel;
        }
        if (StringUtils.isEmpty(searchDTO.getId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("线路id为空，无法查询推荐线路");
            return returnModel;
        }

        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRouteInBroadcastService.recommendLine(searchDTO.getSpotId(),searchDTO.getId());

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }



    /**
     * 伴游线路（方法2）
     * 张
     */
    @ApiOperation("伴游线路（方法2）")
    @GetMapping("lineDetailsTwo")
    @ResponseBody
    public ReturnModel lineDetailsTwo(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区id为空，无法查询");
            return  returnModel;
        }


        if(StringUtils.isEmpty(searchDTO.getLat()) && StringUtils.isEmpty(searchDTO.getLng())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("当前坐标为空，无法查询");
            return  returnModel;
        }

        //线路id为空时返回数据
        if(StringUtils.isEmpty(searchDTO.getId())){
            Map<String, Object> search = new HashMap<>();
            search.put("spotId",searchDTO.getSpotId());
//            search.put("broadcastId",searchDTO.getBroadcastId());
            List<SysScenicSpotBroadcast> list = sysScenicSpotRouteInBroadcastService.broadcastEnterMap(search,searchDTO.getLng(),searchDTO.getLat());
            returnModel.setData(list);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("线路id为空，随机获取5个景点");
            return  returnModel;
        }

        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRouteInBroadcastService.lineDetailsTwo(searchDTO.getSpotId(),searchDTO.getId(),searchDTO.getLat(),searchDTO.getLng());

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }

    /**
     * 伴游线路页面中的推荐线路(方法2)
     * 张
     */
    @ApiOperation("伴游线路页面中的推荐线路(方法2)")
    @GetMapping("recommendLineTwo")
    @ResponseBody
    public ReturnModel recommendLineTwo(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();
        //判断景点id是否为空
        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区spotId为空，无法查询推荐线路");
            return returnModel;
        }
        if (StringUtils.isEmpty(searchDTO.getId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("线路id为空，无法查询推荐线路");
            return returnModel;
        }
        if (StringUtils.isEmpty(searchDTO.getBroadcastId())){
            returnModel.setData("");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景点id为空，无法查询推荐线路");
            return returnModel;
        }

        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRouteInBroadcastService.recommendLineTwo(searchDTO.getSpotId(),searchDTO.getId(),searchDTO.getBroadcastId());

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }


    /**
     * 点击景点进入地图页（暂时写死）
     * @param searchDTO
     * @return
     */
    @ApiOperation("景点进入地图页")
    @GetMapping("broadcastEnterMap")
    @ResponseBody
    public ReturnModel broadcastEnterMap(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        search.put("spotId",searchDTO.getSpotId());
        search.put("broadcastId",searchDTO.getBroadcastId());
        List<SysScenicSpotBroadcast> list = sysScenicSpotRouteInBroadcastService.broadcastEnterMap(search,searchDTO.getLng(),searchDTO.getLat());

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功！");
        return returnModel;

    }


    /**
     * 根据两个84坐标点，从百度地图中得到对应的线路坐标(有问题)
     * 涨
     */
    @ApiOperation("根据两个84坐标点，从百度地图中得到两个点中线路的坐标(骑行的线路)")
    @GetMapping("coordinateLineBD")
    @ResponseBody
    public ReturnModel coordinateLineBD(String lat,String lng,String latTwo,String lngTwo) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> returnModelMap = HttpClientUtils.findByNavigationCoordinatePoint(lat, lng, latTwo, lngTwo);

        returnModel.setData(returnModelMap);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功！");
        return returnModel;
    }


    /**
     * 伴游线路页面中的推荐线路(方法3)
     * 张
     */
    @ApiOperation("伴游线路页面中的推荐线路(方法3)")
    @GetMapping("recommendLineThree")
    @ResponseBody
    public ReturnModel recommendLineThree(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();
        //判断景点id是否为空
//        if (StringUtils.isEmpty(searchDTO.getSpotId())){
//            returnModel.setData("");
//            returnModel.setState(Constant.STATE_FAILURE);
//            returnModel.setMsg("景区spotId为空，无法查询推荐线路");
//            return returnModel;
//        }
//        if (StringUtils.isEmpty(searchDTO.getId())){
//            returnModel.setData("");
//            returnModel.setState(Constant.STATE_FAILURE);
//            returnModel.setMsg("线路id为空，无法查询推荐线路");
//            return returnModel;
//        }
//        if (StringUtils.isEmpty(searchDTO.getBroadcastId())){
//            returnModel.setData("");
//            returnModel.setState(Constant.STATE_FAILURE);
//            returnModel.setMsg("景点id为空，无法查询推荐线路");
//            return returnModel;
//        }

        List<SysScenicSpotRecommendedRoute> list = sysScenicSpotRouteInBroadcastService.recommendLineThree(searchDTO.getSpotId(),searchDTO.getId(),searchDTO.getBroadcastId());

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }



}
