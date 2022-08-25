package com.jxzy.AppMigration.NavigationApp.controller;

import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.entity.dto.SearchDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/14 17:32
 */

@Api(tags = "游小伴景区资源相关（zhang）")
@RestController
@RequestMapping("scenicSpotNew")
public class SysScenicSpotNewController extends PublicUtil {
    @Autowired
    private SysScenicSpotService sysScenicSpotService;
    @Autowired
    private SysScenicSpotBindingService sysScenicSpotBindingService;
    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;
    @Autowired
    private SysScenicSpotBroadcastService sysScenicSpotBroadcastService;
    @Autowired
    private SysScenicSpotServiceResService sysScenicSpotServiceResService;
    @Autowired
    private SysScenicSpotRecommendedRouteService sysScenicSpotRecommendedRouteService;
    @Autowired
    private SysScenicSpotGpsCoordinateService sysScenicSpotGpsCoordinateService;
    @Autowired
    private SysRobotMapResService sysRobotMapResService;
    @Autowired
    private SysCurrentUserExchangeService sysCurrentUserExchangeService;
    @Autowired
    private SysScenicSpotParkingService sysScenicSpotParkingService;
    @Autowired
    private  SysScenicSpotHeatService sysScenicSpotHeatService;
    @Autowired
    private SysScenicDistrictRankingService sysScenicDistrictRankingService;
    @Autowired
    private SysUserDistrictFabulousCollectionService sysUserDistrictFabulousCollectionService;
    @Autowired
    private SysUserScenicFabulousCollectionService sysUserScenicFabulousCollectionService;

    /**
     * 根据城市和景区名查询列表以及热度
     * @param: longinTokenId 登录令牌
     * @param: scenicSpotName景区名称
     * @param: city          省
     * @param: type          类型
     * @param: pageNum       当前页
     * @param: pageSize      当前页条数
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    @ApiOperation("根据省市和景区名查询列表以及热度")
    @GetMapping("/queryCityAndScenicSpotLists")
    public ReturnModel queryCityAndScenicSpotLists(@ApiParam(name="baseDTO",value="登录令牌",required=true) BaseDTO baseDTO,
                                                   @ApiParam(name="scenicSpotName",value="景区名称",required=false)String scenicSpotName,
                                                   @ApiParam(name="city",value="省市名称",required=false)String city,
                                                   @ApiParam(name="type",value="空代表查询全部，1代表热度查询",required=false)String type,
                                                   @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                                   @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize
                                                   ){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            String objectProvinces = FromCityToProvince.findObjectProvinces(city);//截取“市”
            search.put("scenicSpotName",scenicSpotName);
            search.put("scenicSpotFname",objectProvinces);
            search.put("type",type);
            List<SysScenicSpotBinding> sysScenicSpot = sysScenicSpotBindingService.queryCityAndScenicSpotLists(pageNum,pageSize,search);
            //PageInfo就是一个分页Bean
            PageInfo pageInfo = new PageInfo(sysScenicSpot);
            returnModel.setData(pageInfo);
            returnModel.setMsg("成功获取景区列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("queryScenicSpotLists",e);
            returnModel.setData("");
            returnModel.setMsg("获取景区列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 更新景区热度
     * @param: longinTokenId
     * @param: scenicSpotId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    @ApiOperation("更新景区热度")
    @PostMapping("/addScenicSpotHeat")
    @ApiImplicitParams({
            @ApiImplicitParam(name="baseDTO", value="登录令牌", dataType="string", required = true),
            @ApiImplicitParam(name="scenicSpotId",value="景区ID",dataType="string",required = true)})
    public ReturnModel addScenicSpotHeat(BaseDTO baseDTO,String scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysScenicSpot scenicSpot = sysScenicSpotService.queryScenicSpotData(Long.parseLong(scenicSpotId));
            if (scenicSpot == null) {
                returnModel.setData("");
                returnModel.setMsg("为查询到此景区或景区ID有误！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
            int update = sysScenicSpotService.updateScenicSpotHeat(scenicSpot);
            if (update > 0) {
                returnModel.setData(scenicSpot);
                returnModel.setMsg("更新成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("更新失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("addScenicSpotHeat", e);
            returnModel.setData("");
            returnModel.setMsg("增加景区热度！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 获取景点播报列表
     * @param: longinTokenId 登录令牌
     * @param: scenicSpotId  景区ID
     * @param: pageNum       当前页数
     * @param: pageSize      当前页条数
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    @ApiOperation("查询景区资源")
    @GetMapping("/queryScenicResourcesLists")
    public ReturnModel queryScenicResourcesLists(@ApiParam(name="baseDTO",value="登录令牌",required=true)BaseDTO baseDTO,
                                                 @ApiParam(name="scenicSpotId",value="景区ID",required=true)String scenicSpotId,
                                                 @ApiParam(name="type",value="type=1查询景点列表,type=2查询停靠点,type=3卫生间列表,type=4经典路线列表",required=true)String type,
                                                 @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                                 @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {

            search.put("scenicSpotId",Long.parseLong(scenicSpotId));
            if (type.equals("1")) {
                List<SysScenicSpotBroadcast> broadcast = sysScenicSpotBroadcastService.queryWordsScenicSpotBroadcast(pageNum,pageSize,search);
                //PageInfo就是一个分页Bean
                PageInfo pageInfo = new PageInfo(broadcast);
                returnModel.setData(pageInfo);
                returnModel.setMsg("成功获取景点列表！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (type.equals("2")) {
                List<SysScenicSpotBroadcast> parking = sysScenicSpotParkingService.getScenicSpotParkingList(pageNum,pageSize,search);
                //List<SysScenicSpotBroadcast> parking = sysScenicSpotBroadcastService.queryScenicSpotStop(pageNum,pageSize,search);
                //PageInfo就是一个分页Bean
                PageInfo pageInfo = new PageInfo(parking);
                returnModel.setData(pageInfo);
                returnModel.setMsg("成功获取停靠点列表！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (type.equals("3")) {
                List<SysScenicSpotServiceRes> res = sysScenicSpotServiceResService.queryToiletList(pageNum,pageSize,search);
                //PageInfo就是一个分页Bean
                PageInfo pageInfo = new PageInfo(res);
                returnModel.setData(pageInfo);
                returnModel.setMsg("成功获取洗手间列表！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (type.equals("4")) {
                List<SysScenicSpotRecommendedRoute> route = sysScenicSpotRecommendedRouteService.queryScenicSpotRecommendedRouteList(pageNum,pageSize,search);
                //PageInfo就是一个分页Bean
                PageInfo pageInfo = new PageInfo(route);
                returnModel.setData(pageInfo);
                returnModel.setMsg("成功获取洗手间列表！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("您输入的类型有误，请重新输入！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("queryScenicSpotLists",e);
            returnModel.setData("");
            returnModel.setMsg("获取景点列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 获取景区电子围栏
     * @param: longinTokenId 登录令牌
     * @param: scenicSpotId  景区ID
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    @ApiOperation("景区电子围栏")
    @GetMapping("/queryScenicSpotElectronicFence")
    public ReturnModel queryScenicSpotElectronicFence(@ApiParam(name="baseDTO",value="登录令牌",required=true)BaseDTO baseDTO,
                                                      @ApiParam(name="scenicSpotId",value="景区ID",required=true)String scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            search.put("scenicSpotId",scenicSpotId);
            SysScenicSpotGpsCoordinateWithBLOBs coordinate = sysScenicSpotGpsCoordinateService.queryScenicSpotElectronicFence(search);
            returnModel.setData(coordinate);
            returnModel.setMsg("成功获取景区围栏！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("queryScenicSpotElectronicFence",e);
            returnModel.setData("");
            returnModel.setMsg("失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 查询地图资源
     * @param: longinTokenId 登录令牌
     * @param: scenicSpotId 景区ID
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    @ApiOperation("查询地图资源")
    @GetMapping("/queryMapRes")
    public ReturnModel queryMapRes(@ApiParam(name="baseDTO",value="登录令牌",required=true)BaseDTO baseDTO,
                                   @ApiParam(name="scenicSpotId",value="景区名称",required=true)String scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            search.put("scenicSpotId",scenicSpotId);
            List<SysRobotMapRes> sysScenicSpot = sysRobotMapResService.queryMapRes(search);
            returnModel.setData(sysScenicSpot);
            returnModel.setMsg("成功获取景区列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("queryScenicSpotLists",e);
            returnModel.setData("");
            returnModel.setMsg("获取景区列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 扫码兑奖
     * @param: exchangeNumber 兑奖编号
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    @ApiOperation("兑换奖品")
    @PostMapping("/exchangePrize")
    @ApiImplicitParams({@ApiImplicitParam(name ="exchangeNumber", value = "兑奖编号",dataType="string", required = true)})
    public ReturnModel exchangePrize(String exchangeNumber){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {

            search.put("exchangeNumber",exchangeNumber);
            SysCurrentUserExchange exchange = sysCurrentUserExchangeService.exchangePrize(search);
            if (exchange.getExchangeState().equals("0") && DateUtil.isEffectiveDates(exchange.getStartValidity(), exchange.getEndValidity())){
                sysCurrentUserExchangeService.updateExchangePrizeState(exchange);
                returnModel.setData(exchange);
                returnModel.setMsg("兑奖成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
            }else {
                returnModel.setData("");
                returnModel.setMsg("此奖品已兑换或已过期！");
                returnModel.setState(Constant.STATE_FAILURE);
            }
            return returnModel;
        }catch (Exception e){
            logger.info("exchangePrize",e);
            returnModel.setData("");
            returnModel.setMsg("兑奖失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 查询奖品列表
     * @param: exchangeState
     * @param: scenicSpotId
     * @param: pageNum
     * @param: pageSize
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    @ApiOperation("奖品列表")
    @GetMapping("/queryExchangePrizeList")
    public ReturnModel queryExchangePrizeList(@ApiParam(name="exchangeState",value="兑换状态exchangeState=1已兑换奖品，exchangeState=0为兑换奖品，不传查询所有",required=false)String exchangeState,
                                              @ApiParam(name="scenicSpotId",value="景区名称",required=true)String scenicSpotId,
                                              @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                              @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize,
                                              @ApiParam(name="baseDTO",value="登录令牌",required=true) BaseDTO baseDTO){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            search.put("scenicSpotId",scenicSpotId);
            search.put("exchangeState",exchangeState);
            List<SysCurrentUserExchange> exchangeList = sysCurrentUserExchangeService.queryExchangePrizeList(pageNum,pageSize,search);
            //PageInfo就是一个分页Bean
            PageInfo pageInfo = new PageInfo(exchangeList);
            returnModel.setData(pageInfo);
            returnModel.setMsg("成功获取奖品列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("queryExchangePrizeList",e);
            returnModel.setData("");
            returnModel.setMsg("获取奖品列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 张
     * 根据坐标获取当前所在城市(首页全部景区)
     */
    @ApiOperation("根据坐标获取当前所在城市，获取下面的全部景区")
    @GetMapping("/currentCity")
    @ResponseBody
    public PageDataResult currentCity(PageDTO pageDTO){

        PageDataResult pageDataResult = new PageDataResult();

        if (StringUtils.isEmpty(pageDTO.getLng()) &&StringUtils.isEmpty(pageDTO.getLat())){
            Integer sort = pageDTO.getSort();
            Integer pageNum = pageDTO.getPageNum();
            Integer pageSize = pageDTO.getPageSize();
            pageDataResult =  sysScenicSpotService.currentCityAll(sort,pageNum,pageSize);
            return pageDataResult;
        }else{
            String lng = pageDTO.getLng();
            String lat = pageDTO.getLat();
            Integer sort = pageDTO.getSort();
            Integer pageNum = pageDTO.getPageNum();
            Integer pageSize = pageDTO.getPageSize();
            String cityName = HttpClientUtils.findByLatAndLng(lat,lng);
            if (!StringUtils.isEmpty(cityName)){
                pageDataResult =  sysScenicSpotService.currentCity(lng,lat,cityName,sort,pageNum,pageSize);
                return pageDataResult;
            }else{

                pageDataResult.setCode(400);
                return pageDataResult;
            }
        }
    }
    /**
     * zhang
     * 景区搜索
     */

    @ApiOperation("景区搜索")
    @GetMapping("searchSpots")
    @ResponseBody
    public PageDataResult searchSpots(PageDTO pageDTO){
        PageDataResult pageDataResult = new PageDataResult();
        pageDataResult = sysScenicSpotService.searchSpot(pageDTO);

        return pageDataResult;
    }


    /**
     * zhang
     * 获取景区详情
     */

    @ApiOperation("获取景区详情")
    @GetMapping("spotDetails")
    @ResponseBody
    public ReturnModel spotDetails(SearchDTO searchDTO){

        ReturnModel returnModel = new ReturnModel();

        if (StringUtils.isEmpty(searchDTO.getSpotId())){
            returnModel.setData(0);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("景区id为空，无法获取详情!");
        }
//        if (StringUtils.isEmpty(searchDTO.getLat()) || StringUtils.isEmpty(searchDTO.getLat())){
//            returnModel.setData(0);
//            returnModel.setState(Constant.STATE_FAILURE);
//            returnModel.setMsg("当前坐标数据为空，无法获取数据!");
//        }
        SysScenicSpot sysScenicSpot = sysScenicSpotService.spotDetails(searchDTO.getSpotId(),searchDTO.getLat(),searchDTO.getLng());

        returnModel.setData(sysScenicSpot);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取景区详情成功!");
        return returnModel;
    }








}
