package com.jxzy.AppMigration.NavigationApp.Service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
import com.jxzy.AppMigration.NavigationApp.util.DistanceUtil;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.awt.geom.Point2D;
import java.util.*;

@Service
@Transactional
public class SysScenicSpotServiceImpl implements SysScenicSpotService {

    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;
    @Autowired
    private SysScenicSpotBindingMapper sysScenicSpotBindingMapper;
    @Autowired
    private SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址


    @Override
    public List<SysScenicSpot> queryScenicSpotList() {
        return sysScenicSpotMapper.queryScenicSpotList();
    }

    /**
     * 景区搜索
     * @param pageDTO
     * @return
     */
    @Override
    public PageDataResult searchSpot(PageDTO pageDTO) {
        PageDataResult pageDataResult = new PageDataResult();

        if (!StringUtils.isEmpty(pageDTO.getLng()) && !StringUtils.isEmpty(pageDTO.getLat())){
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();
            String lng = pageDTO.getLng();
            String lat = pageDTO.getLat();
            HashMap<String, Object> search = new HashMap<>();
            search.put("spotName",pageDTO.getSpotName());
            search.put("cityId",pageDTO.getCityId());
            search.put("sort",pageDTO.getSort());
            PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
            List<SysScenicSpot> spotList = sysScenicSpotMapper.selectBySearch(search);
            for (SysScenicSpot sysScenicSpot : spotList) {
                if (StringUtils.isEmpty(sysScenicSpot.getCoordinateRange())){
                    continue;
                }
                String[] split = sysScenicSpot.getCoordinateRange().split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lng),Double.valueOf(lat),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysScenicSpot.setDistance(distanceOne);
            }
            //判断是否是根据距离排序
            if (pageDTO.getSort()==2){
                Collections.sort(spotList, new Comparator<SysScenicSpot>() {
                    @Override
                    public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                        Double i = o2.getDistance() - o1.getDistance();
                        return  i.intValue() ;
                    }
                });
            }
            if (spotList.size()>0){
                PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(spotList);
                pageDataResult.setData(pageInfo.getList());
                pageDataResult.setTotals((int)pageInfo.getTotal());
            }
            return pageDataResult;
        }else{

            HashMap<String, Object> search = new HashMap<>();
            search.put("spotName",pageDTO.getSpotName());
//            search.put("cityId",pageDTO.getCityId());
            search.put("sort",pageDTO.getSort());
            PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
            List<SysScenicSpot> spotList = sysScenicSpotMapper.getScenicSpotAll(search);
//            for (SysScenicSpot sysScenicSpot : spotList) {
//                if (StringUtils.isEmpty(sysScenicSpot.getCoordinateRange())){
//                    continue;
//                }
//                String[] split = sysScenicSpot.getCoordinateRange().split(",");
//                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
//                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
//                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
////            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lng),Double.valueOf(lat),Double.valueOf(split[0]),Double.valueOf(split[1]));
//                sysScenicSpot.setDistance(distanceOne);
//            }

            if (pageDTO.getSort()==2){
                Collections.sort(spotList, new Comparator<SysScenicSpot>() {
                    @Override
                    public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                        if (StringUtils.isEmpty(o2.getDistance()) && StringUtils.isEmpty(o1.getDistance())){
                            return 0;
                        }
                        Double i = o2.getDistance() - o1.getDistance();
                        return  i.intValue() ;
                    }
                });
            }
            if (spotList.size()>0){
                PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(spotList);
                pageDataResult.setData(pageInfo.getList());
                pageDataResult.setTotals((int)pageInfo.getTotal());
            }
            return pageDataResult;


        }

    }

    /**
     * 获取景区详情
     * @param spotId
     * @return
     */
    @Override
    public SysScenicSpot  spotDetails(String spotId, String lat, String lng) {
        SysScenicSpot scenicSpot = sysScenicSpotMapper.spotDetails(spotId);
        if (!StringUtils.isEmpty(lng) && !StringUtils.isEmpty(lat)){
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();
            if (StringUtils.isEmpty(scenicSpot.getCoordinateRange())) { String[] split = scenicSpot.getCoordinateRange().split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat), Double.valueOf(lng), Double.valueOf(split[0]), Double.valueOf(split[1]));
                scenicSpot.setDistance(distanceOne);
            }
        }
        return scenicSpot;
    }

    /**
     * 根据景区ID查询景区数据
     * @param: parseLong
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    public SysScenicSpot queryScenicSpotData(long scenicSpotId) {
        return sysScenicSpotMapper.selectByPrimaryKey(scenicSpotId);
    }

    /**
     * 更新景区热度
     * @param: scenicSpot
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    public int updateScenicSpotHeat(SysScenicSpot scenicSpot) {
        long heat = scenicSpot.getHeat() + 1;//自增热度
        scenicSpot.setHeat(heat);
        return sysScenicSpotMapper.updateByPrimaryKeySelective(scenicSpot);
    }

    /**
     * 查询景区排行
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    public List<SysScenicSpot> queryScenicSpotRankingList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpot> spotList = sysScenicSpotMapper.queryScenicSpotRankingList(search);
        return spotList;
    }

    /**
     * 获取全国景区列表
     * 无当前坐标
     * zhang
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult currentCityAll(Integer sort, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> search = new HashMap<>();
        search.put("sort",sort);
//        List<SysScenicSpot> scenicSpots = sysScenicSpotBindingMapper.selectBySearch(search);
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpot> scenicSpots = sysScenicSpotMapper.getScenicSpotAll(search);
        //计算当前坐标与景点坐标之间的距离
        for (SysScenicSpot scenicSpot : scenicSpots) {
            String coordinateRange = scenicSpot.getCoordinateRange();
            if (StringUtils.isEmpty(coordinateRange)){
                continue;
            }
            //计算景区中景区有多少景点讲解
            Integer spotCount = sysScenicSpotBroadcastMapper.selectSpotByCount(scenicSpot.getScenicSpotId());
            scenicSpot.setScenicSpotBroadcastCount(spotCount);
        }

        //判断是否是根据距离排序
        if (sort==2){

            Collections.sort(scenicSpots, new Comparator<SysScenicSpot>() {
                @Override
                public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                    Double i = o2.getDistance() - o1.getDistance();
                    return  i.intValue() ;
                }
            });
        }
        map.put("scenicSpot",scenicSpots);
        if (scenicSpots.size()>0){
            PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(scenicSpots);
            //pageDataResult.setData(pageInfo.getList());
            pageDataResult.setDataNew(map);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 游小伴pp首页全部景区
     * 有当前坐标
     * @param cityName
     * @return
     */
    @Override
    public PageDataResult currentCity(String lng,String lat,String cityName, Integer sort, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<SysScenicSpot> sysScenicSpots = new ArrayList<>();

        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();

        Map<String, Object> search = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname(cityName);
        if (StringUtils.isEmpty(sysScenicSpotBinding)){
            return pageDataResult;
        }
        PageHelper.startPage(pageNum,pageSize);
        search.put("scenicSpotPid",sysScenicSpotBinding.getScenicSpotFid());
        search.put("sort",sort);
        List<SysScenicSpot> scenicSpots = sysScenicSpotBindingMapper.selectBySearch(search);
        //计算当前坐标与景点坐标之间的距离
        for (SysScenicSpot scenicSpot : scenicSpots) {
            String coordinateRange = scenicSpot.getCoordinateRange();
            if (StringUtils.isEmpty(coordinateRange)){
                continue;
            }
            String[] split = coordinateRange.split(",");
            from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
            to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
            double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
            scenicSpot.setDistance(distanceOne);
            //计算景区中景区有多少景点讲解
            Integer spotCount = sysScenicSpotBroadcastMapper.selectSpotByCount(scenicSpot.getScenicSpotId());
            scenicSpot.setScenicSpotBroadcastCount(spotCount);
        }

        //判断是否是根据距离排序
        if (sort==2){

            Collections.sort(scenicSpots, new Comparator<SysScenicSpot>() {
                @Override
                public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                    Double i = o2.getDistance() - o1.getDistance();
                    return  i.intValue() ;
                }
            });
        }
        map.put("city",sysScenicSpotBinding.getScenicSpotFid());
        map.put("cityName",sysScenicSpotBinding.getScenicSpotFname());
        map.put("scenicSpot",scenicSpots);
        if (scenicSpots.size()>0){
            PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(scenicSpots);
            //pageDataResult.setData(pageInfo.getList());
            pageDataResult.setDataNew(map);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }



}
