package com.jxzy.AppMigration.NavigationApp.controller;


import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Api(tags = "游小伴景区资源相关")
@CrossOrigin
@RestController
@RequestMapping("scenicSpot")
public class ScenicSpotController extends PublicUtil {
    private static final UUID UUID = null;
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
    private SysScenicSpotHeatService sysScenicSpotHeatService;
    @Autowired
    private SysScenicDistrictRankingService sysScenicDistrictRankingService;
    @Autowired
    private SysUserDistrictFabulousCollectionService sysUserDistrictFabulousCollectionService;
    @Autowired
    private SysUserScenicFabulousCollectionService sysUserScenicFabulousCollectionService;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址
    @Value("${UPLOAD_PIC}")
    private String UPLOAD_PIC;//后台管系统域名地址

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
        public ReturnModel queryCityAndScenicSpotLists(@ApiParam(name="longinTokenId",value="登录令牌,状态码202为登录失效",required=true)String longinTokenId,
                                                @ApiParam(name="scenicSpotName",value="景区名称",required=false)String scenicSpotName,
                                                @ApiParam(name="city",value="省市名称",required=false)String city,
                                                @ApiParam(name="type",value="空代表查询全部，1代表热度查询",required=false)String type,
                                                @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                                @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize){
            ReturnModel returnModel = new ReturnModel();
            Map<String,Object> search = new HashMap<>();
            try {
                SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
                if (user == null) {
                    returnModel.setData("");
                    returnModel.setMsg("令牌失效，请重新登录！");
                    returnModel.setState(Constant.LOGIN_FAILURE);
                    return returnModel;
                }
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
                @ApiImplicitParam(name="longinTokenId", value="登录令牌,状态码202为登录失效", dataType="string", required = true),
                @ApiImplicitParam(name="scenicSpotId",value="景区ID",dataType="string",required = true)})
        public ReturnModel addScenicSpotHeat(String longinTokenId,String scenicSpotId) {
            ReturnModel returnModel = new ReturnModel();
            try {
                SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
                if (user == null) {
                    returnModel.setData("");
                    returnModel.setMsg("令牌失效，请重新登录！");
                    returnModel.setState(Constant.LOGIN_FAILURE);
                    return returnModel;
                }
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
    public ReturnModel queryScenicResourcesLists(@ApiParam(name="longinTokenId",value="登录令牌,状态码202为登录失效",required=true)String longinTokenId,
                                            @ApiParam(name="scenicSpotId",value="景区ID",required=true)String scenicSpotId,
                                            @ApiParam(name="type",value="type=1查询景点列表,type=2查询停靠点,type=3卫生间列表,type=4经典路线列表",required=true)String type,
                                            @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                            @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
            if (user == null) {
                returnModel.setData("");
                returnModel.setMsg("令牌失效，请重新登录！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
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
    public ReturnModel queryScenicSpotElectronicFence(@ApiParam(name="longinTokenId",value="登录令牌,状态码202为登录失效",required=true)String longinTokenId,
                                                   @ApiParam(name="scenicSpotId",value="景区ID",required=true)String scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
            if (user == null) {
                returnModel.setData("");
                returnModel.setMsg("令牌失效，请重新登录！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
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
    public ReturnModel queryMapRes(@ApiParam(name="longinTokenId",value="登录令牌,状态码202为登录失效",required=true)String longinTokenId,
                                                   @ApiParam(name="scenicSpotId",value="景区名称",required=true)String scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            SysGuideAppUsers user = sysGuideAppUsersService.getToken(longinTokenId);
            if (user == null) {
                returnModel.setData("");
                returnModel.setMsg("令牌失效，请重新登录！");
                returnModel.setState(Constant.LOGIN_FAILURE);
                return returnModel;
            }
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
                                              @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize){
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
     * 更新景区排名
     * @param: scenicSpotId
     * @param: userCoordinates
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/2 0002
     */
    @ApiOperation("更新景区排名")
    @GetMapping("/bestPopularity")
    public ReturnModel bestPopularity(@ApiParam(name="scenicSpotId",value="景区ID",required=true)String scenicSpotId,
                                      @ApiParam(name="userCoordinates",value="用户坐标(type=1的时候不传此参数)",required=false)String userCoordinates,
                                      @ApiParam(name="type",value="1热搜榜、2人气榜、3欢迎榜、4收藏榜、5点赞榜",required=true)String type,
                                      @ApiParam(name="sameDay",value="当日数据",required=true)int sameDay,
                                      @ApiParam(name="total",value="累计数据",required=true)int total) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        SysScenicSpotGpsCoordinateWithBLOBs coordinate = null;//初始化电子围栏对象
        SysScenicSpotHeat heat = new SysScenicSpotHeat();//查询最佳人气榜数据
        boolean flag = false;
        // 当前机器人的GPS 小程序获取的GPS从数据库中查找
        Point n1 = null;
        try {
            if (userCoordinates != null) {
                String[] split = userCoordinates.split(",");//获取机器人经纬度 并根据逗号截取
                n1 = new Point(Double.valueOf(split[0]), Double.valueOf(split[1]));//
                search.put("scenicSpotId", scenicSpotId);
                coordinate = sysScenicSpotGpsCoordinateService.queryScenicSpotElectronicFence(search);
                if (coordinate != null) {//判断是否在围栏内
                    String[] coordinateOuterring = coordinate.getCoordinateOuterring().split("!");//获取WGS84围栏坐标组
                    if (coordinateOuterring != null && coordinateOuterring.length > 0) {
                        Point[] ps = new Point[coordinateOuterring.length];
                        for (int i = 0; i < coordinateOuterring.length; i++) {
                            String[] str = coordinateOuterring[i].split(",");
                            ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                        }
                        flag = JudgingCoordinates.isPtInPoly(n1.getX(), n1.getY(), ps);
                    }
                }else {
                    returnModel.setData("");
                    returnModel.setMsg("未查询到景区围栏数据！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
                if (flag == false) {
                    search.put("type",type);
                    search.put("scenicSpotId",scenicSpotId);
                    SysScenicSpotHeat heats = sysScenicSpotHeatService.querybestPopularity(search);
                    if (heats == null) {
                        heat.setId(IdUtils.getSeqId());//ID
                        heat.setSameDay(sameDay);//当日统计
                        heat.setTotal(total);//累计统计
                        heat.setScenicSpotId(Long.parseLong(scenicSpotId));//景区ID
                        heat.setType(type);//类型 2代表人气榜
                        heat.setCreateTime(DateUtil.currentDateTime());
                        heat.setUpdateTime(DateUtil.currentDateTime());
                        sysScenicSpotHeatService.insetScenicSpotHeat(heat);
                        returnModel.setData("");
                        returnModel.setMsg("创建人气榜数据成功！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = simpleDateFormat.parse(heats.getUpdateTime());
                        if (DateUtil.isToday(date)) {//判断是否是当天时间
                            heats.setSameDay(heats.getSameDay()+sameDay);
                            heats.setTotal(heats.getTotal()+total);
                            sysScenicSpotHeatService.updateScenicSpotHeat(heats);
                            returnModel.setData("");
                            returnModel.setMsg("更新人气榜数据成功！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }else {
                            heats.setSameDay(sameDay);
                            heats.setTotal(heats.getTotal()+total);
                            heats.setUpdateTime(DateUtil.currentDateTime());
                            sysScenicSpotHeatService.updateScenicSpotHeat(heats);
                            returnModel.setData("");
                            returnModel.setMsg("更新人气榜数据成功！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }
                    }
                }else {
                    returnModel.setData("");
                    returnModel.setMsg("您已经在此景区无需进行导航！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }
            search.put("type",type);
            search.put("scenicSpotId",scenicSpotId);
            SysScenicSpotHeat heats = sysScenicSpotHeatService.querybestPopularity(search);
            if (heats == null) {
                heat.setId(IdUtils.getSeqId());//ID
                heat.setSameDay(sameDay);//当日统计
                heat.setTotal(total);//累计统计
                heat.setScenicSpotId(Long.parseLong(scenicSpotId));//景区ID
                heat.setType(type);//类型 2代表人气榜
                heat.setCreateTime(DateUtil.currentDateTime());
                heat.setUpdateTime(DateUtil.currentDateTime());
                sysScenicSpotHeatService.insetScenicSpotHeat(heat);
                returnModel.setData("");
                returnModel.setMsg("创建人气榜数据成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(heats.getUpdateTime());
                if (DateUtil.isToday(date)) {//判断是否是当天时间
                    heats.setSameDay(heats.getSameDay()+sameDay);
                    heats.setTotal(heats.getTotal()+total);
                    sysScenicSpotHeatService.updateScenicSpotHeat(heats);
                    returnModel.setData("");
                    returnModel.setMsg("更新人气榜数据成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else {
                    heats.setSameDay(sameDay);
                    heats.setTotal(heats.getTotal()+total);
                    heats.setUpdateTime(DateUtil.currentDateTime());
                    sysScenicSpotHeatService.updateScenicSpotHeat(heats);
                    returnModel.setData("");
                    returnModel.setMsg("更新人气榜数据成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }
            }
        }catch(Exception e){
            logger.info("queryExchangePrizeList", e);
            returnModel.setData("");
            returnModel.setMsg("更新景区排名失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 查询用户是否在景区范围内
     * @param: userCoordinates
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    @ApiOperation("查询用户是否在景区范围内")
    @GetMapping("/queryLocationScenicSpot")
    public ReturnModel queryLocationScenicSpot (@ApiParam(name="userCoordinates",value="当前页,输入0不分页",required=true)String userCoordinates){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        boolean flag = false;
        // 当前机器人的GPS 小程序获取的GPS从数据库中查找
        Point n1 =  null;
        try {
            String[] splits = userCoordinates.split(",");//获取机器人经纬度 并根据逗号截取
            n1 = new Point(Double.valueOf(splits[0]),Double.valueOf(splits[1]));
            List<SysScenicSpotGpsCoordinateWithBLOBs> coordinate = sysScenicSpotGpsCoordinateService.queryLocationScenicSpot();
            for (SysScenicSpotGpsCoordinateWithBLOBs GpsCoordinate : coordinate) {
            String[] split = GpsCoordinate.getCoordinateOuterring().split("!");
            if(split != null && split.length>0){
                Point[] ps = new Point[split.length];
                for (int i = 0; i < split.length; i++) {
                    String[] str = split[i].split(",");
                    ps[i] = new Point(Double.valueOf(str[0]),Double.valueOf(str[1]));
                }
                flag = JudgingCoordinates.isPtInPoly(n1.getX() , n1.getY() , ps);
            }
            if(flag == true){
                returnModel.setData(GpsCoordinate.getCoordinateScenicSpotId());
                returnModel.setMsg("您已在景区内！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }
            }
            returnModel.setData("");
            returnModel.setMsg("此用户不在景区范围内");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }catch (Exception e){
            logger.info("bestPopularity",e);
            returnModel.setData("");
            returnModel.setMsg("询用户是否在景区范围内失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 更新景点排名
     * @param: scenicSpotId
     * @param: scenicDistrictId
     * @param: userCoordinates
     * @param: type
     * @param: sameDay
     * @param: total
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    @ApiOperation("更新景点排名")
    @GetMapping("/bestRanking")
    public ReturnModel bestRanking(@ApiParam(name="scenicSpotId",value="景区ID",required=true)String scenicSpotId,
                                      @ApiParam(name="scenicDistrictId",value="景点ID",required=true)String scenicDistrictId,
                                      @ApiParam(name="userCoordinates",value="用户坐标",required=false)String userCoordinates,
                                      @ApiParam(name="type",value="1热搜榜、2人气榜、3欢迎榜、4收藏榜、5点赞榜",required=true)String type,
                                      @ApiParam(name="sameDay",value="当日数据",required=true)int sameDay,
                                      @ApiParam(name="total",value="累计数据",required=true)int total) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        SysScenicSpotGpsCoordinateWithBLOBs coordinate = null;//初始化电子围栏对象
        SysScenicDistrictRanking ranking = new SysScenicDistrictRanking();//查询最佳人气榜数据
        boolean flag = false;
        // 当前机器人的GPS 小程序获取的GPS从数据库中查找
        Point n1 = null;
        try {
            if (userCoordinates != null) {
                String[] split = userCoordinates.split(",");//获取机器人经纬度 并根据逗号截取
                n1 = new Point(Double.valueOf(split[0]), Double.valueOf(split[1]));//
                search.put("scenicSpotId", scenicSpotId);
                coordinate = sysScenicSpotGpsCoordinateService.queryScenicSpotElectronicFence(search);
                if (coordinate != null) {//判断是否在围栏内
                    String[] coordinateOuterring = coordinate.getCoordinateOuterring().split("!");//获取WGS84围栏坐标组
                    if (coordinateOuterring != null && coordinateOuterring.length > 0) {
                        Point[] ps = new Point[coordinateOuterring.length];
                        for (int i = 0; i < coordinateOuterring.length; i++) {
                            String[] str = coordinateOuterring[i].split(",");
                            ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                        }
                        flag = JudgingCoordinates.isPtInPoly(n1.getX(), n1.getY(), ps);
                    }
                }else {
                    returnModel.setData("");
                    returnModel.setMsg("未查询到景区围栏数据！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
                if (flag == false) {
                    returnModel.setData("");
                    returnModel.setMsg("您不在此景区无法为您导航此景点！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else {
                    search.put("type",type);
                    search.put("scenicDistrictId",scenicDistrictId);
                    SysScenicDistrictRanking rankings = sysScenicDistrictRankingService.bestRanking(search);
                    if (rankings == null) {
                        ranking.setId(IdUtils.getSeqId());//ID
                        ranking.setSameDay(sameDay);//当日统计
                        ranking.setTotal(total);//累计统计
                        ranking.setBroadcastId(Long.parseLong(scenicDistrictId));//景区ID
                        ranking.setType(type);//类型 2代表人气榜
                        ranking.setCreateTime(DateUtil.currentDateTime());
                        ranking.setUpdateTime(DateUtil.currentDateTime());
                        sysScenicDistrictRankingService.insetbestRanking(ranking);
                        returnModel.setData("");
                        returnModel.setMsg("创建人气榜数据成功！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = simpleDateFormat.parse(rankings.getUpdateTime());
                        if (DateUtil.isToday(date)) {//判断是否是当天时间
                            rankings.setSameDay(rankings.getSameDay()+sameDay);
                            rankings.setTotal(rankings.getTotal()+total);
                            sysScenicDistrictRankingService.updatebestRanking(rankings);
                            returnModel.setData("");
                            returnModel.setMsg("更新人气榜数据成功！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }else {
                            rankings.setSameDay(sameDay);
                            rankings.setTotal(rankings.getTotal()+total);
                            rankings.setUpdateTime(DateUtil.currentDateTime());
                            sysScenicDistrictRankingService.updatebestRanking(rankings);
                            returnModel.setData("");
                            returnModel.setMsg("更新人气榜数据成功！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }
                    }
                }
            }
            search.put("type",type);
            search.put("scenicDistrictId",scenicDistrictId);
            SysScenicDistrictRanking rankings = sysScenicDistrictRankingService.bestRanking(search);
            if (rankings == null) {
                ranking.setId(IdUtils.getSeqId());//ID
                ranking.setSameDay(sameDay);//当日统计
                ranking.setTotal(total);//累计统计
                ranking.setBroadcastId(Long.parseLong(scenicDistrictId));//景区ID
                ranking.setType(type);//类型 2代表人气榜
                ranking.setCreateTime(DateUtil.currentDateTime());
                ranking.setUpdateTime(DateUtil.currentDateTime());
                sysScenicDistrictRankingService.insetbestRanking(ranking);
                returnModel.setData("");
                returnModel.setMsg("创建排名数据成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(rankings.getUpdateTime());
                if (DateUtil.isToday(date)) {//判断是否是当天时间
                    rankings.setSameDay(rankings.getSameDay()+sameDay);
                    rankings.setTotal(rankings.getTotal()+total);
                    sysScenicDistrictRankingService.updatebestRanking(rankings);
                    returnModel.setData("");
                    returnModel.setMsg("更新排名数据成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else {
                    rankings.setSameDay(sameDay);
                    rankings.setTotal(rankings.getTotal()+total);
                    rankings.setUpdateTime(DateUtil.currentDateTime());
                    sysScenicDistrictRankingService.updatebestRanking(rankings);
                    returnModel.setData("");
                    returnModel.setMsg("更新排名数据成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }
            }
        }catch(Exception e){
            logger.info("bestRanking", e);
            returnModel.setData("");
            returnModel.setMsg("更新景点排名失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 更新用户景区或景点收藏和点赞
     * @param: userId
     * @param: type
     * @param: part
     * @param: scenicSpotId
     * @param: scenicDistrictId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    @ApiOperation("更新用户景区或景点收藏和点赞")
    @GetMapping("/updateUserFabulousCollection")
    public ReturnModel updateUserFabulousCollection(@ApiParam(name="userId",value="用户ID",required=true)String userId,
                                              @ApiParam(name="type",value="区分景区点赞收藏还是景点点赞收藏：1景区，2景点",required=true)String type,
                                              @ApiParam(name="part",value="区分点赞还是收藏：1点赞，2收藏,3取消点赞,4取消收藏",required=true)String part,
                                              @ApiParam(name="scenicSpotId",value="景区ID",required=false)String scenicSpotId,
                                              @ApiParam(name="scenicDistrictId",value="景点ID",required=false)String scenicDistrictId){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        SysUserDistrictFabulousCollection users = new SysUserDistrictFabulousCollection();
        SysUserScenicFabulousCollection sciences =new SysUserScenicFabulousCollection();
        try {
            if ("1".equals(type)) {
                search.put("userId",userId);
                search.put("scenicSpotId",scenicSpotId);
                SysUserDistrictFabulousCollection user = sysUserDistrictFabulousCollectionService.queryUserFabulousCollection(search);
                if (user == null) {
                    if ("1".equals(part)) {
                        users.setId(IdUtils.getSeqId());
                        users.setUserId(Long.parseLong(userId));
                        users.setScenicSpotId(Long.parseLong(scenicSpotId));
                        users.setFabulous("1");//点赞
                        users.setCreateTime(DateUtil.currentDateTime());
                        users.setUpdateTime(DateUtil.currentDateTime());
                        sysUserDistrictFabulousCollectionService.insetUserFabulousCollection(users);
                        returnModel.setData("");
                        returnModel.setMsg("已成功为景区点赞！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else if ("2".equals(part)) {
                        users.setId(IdUtils.getSeqId());
                        users.setUserId(Long.parseLong(userId));
                        users.setScenicSpotId(Long.parseLong(scenicSpotId));
                        users.setCollection("1");//收藏
                        users.setCreateTime(DateUtil.currentDateTime());
                        users.setUpdateTime(DateUtil.currentDateTime());
                        sysUserDistrictFabulousCollectionService.insetUserFabulousCollection(users);
                        returnModel.setData("");
                        returnModel.setMsg("已成功关注此景区！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }
                }else {
                    if ("1".equals(part)) {
                        user.setFabulous("1");
                        sysUserDistrictFabulousCollectionService.updateUserFabulousCollection(user);
                        returnModel.setData("");
                        returnModel.setMsg("已成功为景区点赞！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else if ("2".equals(part)) {
                        user.setCollection("1");
                        sysUserDistrictFabulousCollectionService.updateUserFabulousCollection(user);
                        returnModel.setData("");
                        returnModel.setMsg("已成功关注此景区！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else if ("3".equals(part)) {
                        search.put("type","5");//点赞
                        search.put("scenicSpotId", scenicSpotId);
                        SysScenicSpotHeat heats = sysScenicSpotHeatService.querybestPopularity(search);
                        heats.setTotal(heats.getTotal()-1);
                        heats.setSameDay(heats.getSameDay()-1);
                        sysScenicSpotHeatService.updateScenicSpotHeat(heats);
                        user.setFabulous("0");
                        sysUserDistrictFabulousCollectionService.updateUserFabulousCollection(user);
                        returnModel.setData("");
                        returnModel.setMsg("已取消点赞！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else if ("4".equals(part)) {
                        search.put("type","4");//收藏
                        search.put("scenicSpotId", scenicSpotId);
                        SysScenicSpotHeat heats = sysScenicSpotHeatService.querybestPopularity(search);
                        heats.setTotal(heats.getTotal()-1);
                        heats.setSameDay(heats.getSameDay()-1);
                        sysScenicSpotHeatService.updateScenicSpotHeat(heats);
                        user.setCollection("0");
                        sysUserDistrictFabulousCollectionService.updateUserFabulousCollection(user);
                        returnModel.setData("");
                        returnModel.setMsg("已取消关注！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }
                }

            }else if ("2".equals(type)) {
                search.put("userId",userId);
                search.put("scenicDistrictId",scenicDistrictId);
                SysUserScenicFabulousCollection scenic = sysUserScenicFabulousCollectionService.queryUserFabulousCollection(search);
                if (scenic == null) {
                    if ("1".equals(part)) {
                        sciences.setId(IdUtils.getSeqId());
                        sciences.setUserId(Long.parseLong(userId));
                        sciences.setScenicDistrictId(Long.parseLong(scenicDistrictId));
                        sciences.setFabulous("1");//点赞
                        sciences.setCreateTime(DateUtil.currentDateTime());
                        sciences.setUpdateTime(DateUtil.currentDateTime());
                        sysUserScenicFabulousCollectionService.insetUserFabulousCollection(sciences);
                        returnModel.setData("");
                        returnModel.setMsg("已成功为景点点赞！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else if ("2".equals(part)) {
                        sciences.setId(IdUtils.getSeqId());
                        sciences.setUserId(Long.parseLong(userId));
                        sciences.setScenicDistrictId(Long.parseLong(scenicDistrictId));
                        sciences.setCollection("1");//收藏
                        sciences.setCreateTime(DateUtil.currentDateTime());
                        sciences.setUpdateTime(DateUtil.currentDateTime());
                        sysUserScenicFabulousCollectionService.insetUserFabulousCollection(sciences);
                        returnModel.setData("");
                        returnModel.setMsg("已成功关注此景点！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }
                }else {
                    if ("1".equals(part)) {
                        scenic.setFabulous("1");
                        sysUserScenicFabulousCollectionService.updateUserFabulousCollection(scenic);
                        returnModel.setData("");
                        returnModel.setMsg("已成功为景点点赞！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else if ("2".equals(part)) {
                        scenic.setCollection("1");//收藏
                        sysUserScenicFabulousCollectionService.updateUserFabulousCollection(scenic);
                        returnModel.setData("");
                        returnModel.setMsg("已成功关注此景点！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else if ("3".equals(part)) {
                        search.put("type","5");
                        search.put("scenicDistrictId",scenicDistrictId);
                        SysScenicDistrictRanking rankings = sysScenicDistrictRankingService.bestRanking(search);
                        if (rankings != null) {
                            rankings.setSameDay(rankings.getSameDay()-1);
                            rankings.setTotal(rankings.getTotal()-1);
                            sysScenicDistrictRankingService.updatebestRanking(rankings);
                        }
                        scenic.setFabulous("0");
                        sysUserScenicFabulousCollectionService.updateUserFabulousCollection(scenic);
                        returnModel.setData("");
                        returnModel.setMsg("已取消点赞！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }else if ("4".equals(part)) {
                        search.put("type","4");
                        search.put("scenicDistrictId",scenicDistrictId);
                        SysScenicDistrictRanking rankings = sysScenicDistrictRankingService.bestRanking(search);
                        if (rankings != null) {
                            rankings.setSameDay(rankings.getSameDay()-1);
                            rankings.setTotal(rankings.getTotal()-1);
                            sysScenicDistrictRankingService.updatebestRanking(rankings);
                        }
                        scenic.setCollection("0");
                        sysUserScenicFabulousCollectionService.updateUserFabulousCollection(scenic);
                        returnModel.setData("");
                        returnModel.setMsg("已取消关注！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    }
                }
            }
            return returnModel;
        }catch (Exception e){
            logger.info("updateUserFabulousCollection",e);
            returnModel.setData("");
            returnModel.setMsg("更新景区景点点赞收藏失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 查询景区排名列表以及检索景区
     * @param: type
     * @param: pageNum
     * @param: pageSize
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    @ApiOperation("查询景区排名列表以及检索景区")
    @GetMapping("/queryScenicSpotRankingList")
    public ReturnModel queryScenicSpotRankingList(@ApiParam(name="type",value="1热搜榜、2人气榜、3欢迎榜、4收藏榜、5点赞榜",required=false)String type,
                                                  @ApiParam(name="types",value="判定type条件，类型和type类型一样(1热搜榜、2人气榜、3欢迎榜、4收藏榜、5点赞榜)",required=false)String types,
                                                  @ApiParam(name="scenicSpotName",value="景区名称",required=false)String scenicSpotName,
                                                  @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                                  @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            if (type != null) {
                search.put("type",type);
            }
            if (scenicSpotName != null && types != null) {
                search.put("scenicSpotName",scenicSpotName);
                search.put("types",types);
            }
            List<SysScenicSpot> spotList = sysScenicSpotService.queryScenicSpotRankingList(pageNum,pageSize,search);
            //PageInfo就是一个分页Bean
            PageInfo pageInfo = new PageInfo(spotList);
            returnModel.setData(pageInfo);
            returnModel.setMsg("成功获取景区排行列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("queryScenicSpotRankingList",e);
            returnModel.setData("");
            returnModel.setMsg("获取景区排行列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 查询景点排名列表以及景点详情和检索景点
     * @param: type
     * @param: scenicSpotId
     * @param: broadcastId
     * @param: pageNum
     * @param: pageSize
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/8 0008
     */
    @ApiOperation("查询景点排名列表以及景点详情和检索景点")
    @GetMapping("/queryScenicDistrictRankingList")
    public ReturnModel queryScenicDistrictRankingList(@ApiParam(name="type",value="1热搜榜、2人气榜、3欢迎榜、4收藏榜、5点赞榜",required=false)String type,
                                                      @ApiParam(name="types",value="判定type条件，类型和type类型一样(1热搜榜、2人气榜、3欢迎榜、4收藏榜、5点赞榜)",required=false)String types,
                                                      @ApiParam(name="scenicSpotId",value="景区ID",required=false)String scenicSpotId,
                                                      @ApiParam(name="userId",value="用户ID",required=false)String userId,
                                                      @ApiParam(name="broadcastId",value="景点ID",required=false)String broadcastId,
                                                      @ApiParam(name="broadcastName",value="景点名称",required=false)String broadcastName,
                                                      @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                                      @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            if (scenicSpotId != null) {
                search.put("type",type);
                search.put("types",types);
                search.put("scenicSpotId",scenicSpotId);
                search.put("broadcastName",broadcastName);
                List<SysScenicSpotBroadcast> broadcast = sysScenicSpotBroadcastService.queryWordsScenicSpotBroadcastList(pageNum,pageSize,search);
                for (int i = 0; i < broadcast.size(); i++) {
                    String pictureUrl = broadcast.get(i).getPictureUrl();
                    if (pictureUrl != null && !"".equals(pictureUrl)) {
                        broadcast.get(i).setPictureUrl(DOMAIN_NAME+pictureUrl);
                    }
                }
                //PageInfo就是一个分页Bean
                PageInfo pageInfo = new PageInfo(broadcast);
                returnModel.setData(pageInfo);
                returnModel.setMsg("成功获取景区排行列表！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (broadcastId != null) {
                search.put("broadcastId",broadcastId);
                search.put("userId",userId);
                SysScenicSpotBroadcastExtendWithBLOBs extend = sysScenicSpotBroadcastService.queryscenicSpotContent(search);
                returnModel.setData(extend);
                returnModel.setMsg("成功获取景区排行列表！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }
            return returnModel;
        }catch (Exception e){
            logger.info("queryScenicSpotRankingList",e);
            returnModel.setData("");
            returnModel.setMsg("获取景区排行列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 查询景区和景点点赞和收藏
     * @param: type
     * @param: scenicType
     * @param: userId
     * @param: baseDto
     * @param: pageNum
     * @param: pageSize
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    @ApiOperation("查询景区和景点点赞和收藏")
    @GetMapping("/queryUserLikeCollection")
    public ReturnModel queryUserLikeCollection(@ApiParam(name="type",value="4收藏榜、5点赞榜",required=true)String type,
                                               @ApiParam(name="scenicType",value="1景区、2景点",required=true)String scenicType,
                                               @ApiParam(name="userId",value="用户ID",required=true)String userId,
                                               @ApiParam(name="baseDto",value="登录令牌(测试阶段暂时可以随便传参)",required=true) BaseDTO baseDto,
                                               @ApiParam(name="pageNum",value="当前页,输入0不分页",required=true)int pageNum,
                                               @ApiParam(name="pageSize",value="总条数,输入0不分页",required=true)int pageSize){
        ReturnModel returnModel = new ReturnModel();
        Map<String,Object> search = new HashMap<>();
        try {
            search.put("userId",userId);
            if ("1".equals(scenicType)) {//景区
                if ("4".equals(type)) {//收藏
                    List<SysUserDistrictFabulousCollection> broadcast = sysUserDistrictFabulousCollectionService.queryUserCollection(pageNum,pageSize,search);
                    //PageInfo就是一个分页Bean
                    PageInfo pageInfo = new PageInfo(broadcast);
                    returnModel.setData(pageInfo);
                    returnModel.setMsg("成功获取景区排行列表！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if ("5".equals(type)) {//点赞
                    List<SysUserDistrictFabulousCollection> broadcast = sysUserDistrictFabulousCollectionService.queryUserLike(pageNum,pageSize,search);
                    //PageInfo就是一个分页Bean
                    PageInfo pageInfo = new PageInfo(broadcast);
                    returnModel.setData(pageInfo);
                    returnModel.setMsg("成功获取景区排行列表！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }
            }else if ("2".equals(scenicType)) {//景点
                if ("4".equals(type)) {//收藏
                    List<SysUserScenicFabulousCollection> broadcast = sysUserScenicFabulousCollectionService.queryUserScenicCollection(pageNum,pageSize,search);
                    for (int i = 0; i < broadcast.size(); i++) {
                        if (broadcast.size() > 0) {
                            for (int j = 0; j < broadcast.get(i).getSysScenicSpotBroadcasts().size(); j++) {
                                String pictureUrl = broadcast.get(j).getSysScenicSpotBroadcasts().get(j).getPictureUrl()=="" ?"":broadcast.get(j).getSysScenicSpotBroadcasts().get(j).getPictureUrl();
                                if (pictureUrl != null && !"".equals(pictureUrl)) {
                                    broadcast.get(i).getSysScenicSpotBroadcasts().get(i).setPictureUrl(DOMAIN_NAME+pictureUrl);
                                }
                            }
                        }
                    }
                    //PageInfo就是一个分页Bean
                    PageInfo pageInfo = new PageInfo(broadcast);
                    returnModel.setData(pageInfo);
                    returnModel.setMsg("成功获取景区排行列表！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if ("5".equals(type)) {//点赞
                    List<SysUserScenicFabulousCollection> broadcast = sysUserScenicFabulousCollectionService.queryUserScenicLike(pageNum,pageSize,search);
                    for (int i = 0; i < broadcast.size(); i++) {
                        if (broadcast.size() > 0) {
                            for (int j = 0; j < broadcast.get(i).getSysScenicSpotBroadcasts().size(); j++) {
                                String pictureUrl = broadcast.get(j).getSysScenicSpotBroadcasts().get(j).getPictureUrl()==null ?"":broadcast.get(j).getSysScenicSpotBroadcasts().get(j).getPictureUrl();
                                if (pictureUrl != null && !"".equals(pictureUrl)) {
                                    broadcast.get(i).getSysScenicSpotBroadcasts().get(i).setPictureUrl(DOMAIN_NAME+pictureUrl);
                                }
                            }
                        }
                    }
                    //PageInfo就是一个分页Bean
                    PageInfo pageInfo = new PageInfo(broadcast);
                    returnModel.setData(pageInfo);
                    returnModel.setMsg("成功获取景区排行列表！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }
            }
            return returnModel;
        }catch (Exception e){
            logger.info("queryScenicSpotRankingList",e);
            returnModel.setData("");
            returnModel.setMsg("获取景区排行列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }



}
