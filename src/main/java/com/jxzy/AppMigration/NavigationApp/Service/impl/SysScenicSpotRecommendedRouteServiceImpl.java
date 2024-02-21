package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRecommendedRouteService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRecommendedRouteMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRouteInBroadcastMapper;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicReommendedRoute;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastWithBlogs;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRouteInBroadcast;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotRecommendedRouteServiceImpl implements SysScenicSpotRecommendedRouteService {
    @Autowired
    private SysScenicSpotRecommendedRouteMapper sysScenicSpotRecommendedRouteMapper;
    @Autowired
    private SysScenicSpotRouteInBroadcastMapper sysScenicSpotRouteInBroadcastMapper;
    @Autowired
    private SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;
    /**
     * 查询景区景点路线列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotRecommendedRoute> queryScenicSpotRecommendedRouteList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotRecommendedRouteMapper.queryScenicSpotRecommendedRouteList(search);
    }

    /**
     * 后台管理——伴游线路列表
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getRecommendedRouteList(Map<String, String> search, Integer pageNum, Integer pageSize) {

        Map<String, Object> searchN = new HashMap<>();
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotRecommendedRoute> sysScenicSpotRecommendedRouteList = sysScenicSpotRecommendedRouteMapper.getRecommendedRouteList(search);
        for (SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute : sysScenicSpotRecommendedRouteList) {
            searchN = new HashMap<>();
            searchN.put("routeId", sysScenicSpotRecommendedRoute.getRouteId());
            List<SysScenicSpotRouteInBroadcast> sysScenicSpotRouteInBroadcasts = sysScenicSpotRouteInBroadcastMapper.getSpotIdAndRouteIdByList(searchN);
//            sysScenicSpotRecommendedRoute.setRouteInBroadcastList(sysScenicSpotRouteInBroadcasts);
            List<Long> longs = new ArrayList<>();
            for (SysScenicSpotRouteInBroadcast sysScenicSpotRouteInBroadcast : sysScenicSpotRouteInBroadcasts) {
                longs.add(sysScenicSpotRouteInBroadcast.getSysScenicSpotBroadcastId());
            }
            sysScenicSpotRecommendedRoute.setBroadcastIds(longs.toArray(new Long[longs.size()]));
            sysScenicSpotRecommendedRoute.setBroadcastCount(String.valueOf(sysScenicSpotRouteInBroadcasts.size()));
        }
        if (sysScenicSpotRecommendedRouteList.size() != 0) {
            PageInfo<SysScenicSpotRecommendedRoute> pageInfo = new PageInfo<>(sysScenicSpotRecommendedRouteList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;


    }

    /**
     * 后台管理——添加伴游线路
     * @param sysScenicSpotRecommendedRoute
     * @return
     */
    @Override
    public int addRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {

        SysScenicSpotRouteInBroadcast sysScenicSpotRouteInBroadcast = new SysScenicSpotRouteInBroadcast();
        Long[] broadcastIds = sysScenicSpotRecommendedRoute.getBroadcastIds();
        if (broadcastIds.length > 0) {
            //添加经典线路
            sysScenicSpotRecommendedRoute.setRouteId(IdUtils.getSeqId());
            sysScenicSpotRecommendedRoute.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotRecommendedRoute.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotRecommendedRouteMapper.insertSelective(sysScenicSpotRecommendedRoute);
            //添加经典线路中的景点
            Integer j = 1;
            for (Long broadcastId : broadcastIds) {

//                SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(broadcastId);sysScenicSpotRouteInBroadcast = new SysScenicSpotRouteInBroadcast();
                sysScenicSpotRouteInBroadcast.setId(IdUtils.getSeqId());
                sysScenicSpotRouteInBroadcast.setSysScenicSpotBroadcastId(broadcastId);
                sysScenicSpotRouteInBroadcast.setSysScenicSpotRouteId(sysScenicSpotRecommendedRoute.getRouteId());
                sysScenicSpotRouteInBroadcast.setSort(j.toString());
                sysScenicSpotRouteInBroadcast.setCreateTime(DateUtil.currentDateTime());
                sysScenicSpotRouteInBroadcast.setUpdateTime(DateUtil.currentDateTime());
                i = sysScenicSpotRouteInBroadcastMapper.insertService(sysScenicSpotRouteInBroadcast);
                j++;
            }
            return i;

        } else {
            //添加经典线路
            sysScenicSpotRecommendedRoute.setRouteId(IdUtils.getSeqId());
            sysScenicSpotRecommendedRoute.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotRecommendedRoute.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotRecommendedRouteMapper.insertSelective(sysScenicSpotRecommendedRoute);

            return i;
        }
    }
    /**
     *修改伴游线路
     * @param sysScenicSpotRecommendedRoute
     * @return
     */
    @Override
    public int exitRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {

        SysScenicSpotRouteInBroadcast sysScenicSpotRouteInBroadcast = new SysScenicSpotRouteInBroadcast();
        Long[] broadcastIds = sysScenicSpotRecommendedRoute.getBroadcastIds();
        if (broadcastIds.length > 0) {
            //修改经典线路
            sysScenicSpotRecommendedRoute.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotRecommendedRouteMapper.updateByPrimaryKeySelective(sysScenicSpotRecommendedRoute);
            //删除经典线路中的景点
            int j = sysScenicSpotRouteInBroadcastMapper.deleteByRouteId(sysScenicSpotRecommendedRoute.getRouteId());
            //添加线路中的景点
            Integer t = 1;
            for (Long broadcastId : broadcastIds) {
//                SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(broadcastId);sysScenicSpotRouteInBroadcast = new SysScenicSpotRouteInBroadcast();
                sysScenicSpotRouteInBroadcast.setId(IdUtils.getSeqId());
                sysScenicSpotRouteInBroadcast.setSysScenicSpotBroadcastId(broadcastId);
                sysScenicSpotRouteInBroadcast.setSysScenicSpotRouteId(sysScenicSpotRecommendedRoute.getRouteId());
                sysScenicSpotRouteInBroadcast.setSort(t.toString());
                sysScenicSpotRouteInBroadcast.setCreateTime(DateUtil.currentDateTime());
                sysScenicSpotRouteInBroadcast.setUpdateTime(DateUtil.currentDateTime());
                i = sysScenicSpotRouteInBroadcastMapper.insertService(sysScenicSpotRouteInBroadcast);
                t++;
            }
            return i;
        }else{
            //修改经典线路
            sysScenicSpotRecommendedRoute.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotRecommendedRouteMapper.updateByPrimaryKeySelective(sysScenicSpotRecommendedRoute);
            return i;
        }
    }

    /**
     * 删除伴游线路
     * @param routeId
     * @return
     */
    @Override
    public int delRecommendedRoute(Long routeId) {

        int i = sysScenicSpotRecommendedRouteMapper.deleteByPrimaryKey(routeId);
        int j = sysScenicSpotRouteInBroadcastMapper.deleteByRouteId(routeId);

        return i;
    }

    /**
     * 经典路线添加线路中的景点
     * @param routeId
     * @return
     */
    @Override
    public int addRouteInBroadcast(String routeId, String broadcastId) {

        String[] split = broadcastId.split(",");

        SysScenicSpotRouteInBroadcast sysScenicSpotRouteInBroadcast = new SysScenicSpotRouteInBroadcast();
        int i = 0 ;
        Integer j = 0;
        for (String s : split) {
            SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(Long.parseLong(s));

            sysScenicSpotRouteInBroadcast = new SysScenicSpotRouteInBroadcast();
            sysScenicSpotRouteInBroadcast.setId(IdUtils.getSeqId());
            sysScenicSpotRouteInBroadcast.setSysScenicSpotBroadcastId(sysScenicSpotBroadcast.getBroadcastId());
            sysScenicSpotRouteInBroadcast.setSysScenicSpotRouteId(Long.parseLong(routeId));
            sysScenicSpotRouteInBroadcast.setSort(j.toString());
            sysScenicSpotRouteInBroadcast.setCreateTime(DateUtil.currentDateTime());
            sysScenicSpotRouteInBroadcast.setUpdateTime(DateUtil.currentDateTime());
            i = sysScenicSpotRouteInBroadcastMapper.insertService(sysScenicSpotRouteInBroadcast);
            j++;
        }

        return i ;
    }

    /**
     * 查询线路中的景点
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getRouteInBroadcast(Long id, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("routeId",id);

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotRouteInBroadcast> sysScenicSpotRouteInBroadcasts = sysScenicSpotRouteInBroadcastMapper.getSpotIdAndRouteIdByList(search);
        if (sysScenicSpotRouteInBroadcasts.size()>0){
            PageInfo<SysScenicSpotRouteInBroadcast> pageInfo = new PageInfo<>(sysScenicSpotRouteInBroadcasts);
            pageDataResult.setCode(200);
            pageDataResult.setData(sysScenicSpotRouteInBroadcasts);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }

        return pageDataResult;

    }

    /**
     * 删除线路中的景点
     * @param id
     * @return
     */
    @Override
    public int delRouteInBroadcast(Long id) {
        int i = sysScenicSpotRouteInBroadcastMapper.deleteById(id);
        return i ;
    }

    /**
     * 后台管理——获取景区下的所有景点
     * @param spotId
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcast> getSpotLowerBroadcast(Long spotId) {

        Map map =  new HashMap<String,Object>();
        map.put("spotId",spotId);
        List spotBroadcastList = sysScenicSpotBroadcastMapper.getSpotBroadcastList(map);
        return spotBroadcastList;

    }

    /**
     * 后台管理——经典线路状态修改
     * @param routeId
     * @param routeState
     * @return
     */
    @Override
    public int exitRecommendedRouteState(String routeId, String routeState) {
        SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute = sysScenicSpotRecommendedRouteMapper.selectByPrimaryKey(Long.parseLong(routeId));

        sysScenicSpotRecommendedRoute.setRouteState(routeState);

        int i = sysScenicSpotRecommendedRouteMapper.updateByPrimaryKeySelective(sysScenicSpotRecommendedRoute);

        return i ;
    }

    /**
     * 后台管理--线路导出
     * @param search
     * @return
     */
    @Override
    public List<SysScenicReommendedRoute> uploadExcelRecommendedRoute(Map<String, String> search) {

        List<SysScenicReommendedRoute> sysScenicSpotRecommendedRouteList = sysScenicSpotRecommendedRouteMapper.uploadExcelRecommendedRoute(search);

        for (SysScenicReommendedRoute sysScenicReommendedRoute : sysScenicSpotRecommendedRouteList) {
            List<SysScenicSpotRouteInBroadcast> sysScenicSpotRouteInBroadcasts =  sysScenicSpotRouteInBroadcastMapper.selectSpotRouteId(sysScenicReommendedRoute.getRouteId());
            String broadcastName = "";
            for (SysScenicSpotRouteInBroadcast sysScenicSpotRouteInBroadcast : sysScenicSpotRouteInBroadcasts) {
                if (StringUtils.isEmpty(broadcastName)){
                    broadcastName = sysScenicSpotRouteInBroadcast.getBroadcastName();
                }else{
                    broadcastName = broadcastName + "," + sysScenicSpotRouteInBroadcast.getBroadcastName();
                }
            }
            sysScenicReommendedRoute.setBroadcastCount(String.valueOf(sysScenicSpotRouteInBroadcasts.size()));
            sysScenicReommendedRoute.setBroadcastName(broadcastName);
        }

//        for (SysScenicReommendedRoute sysScenicReommendedRoute : sysScenicSpotRecommendedRouteList) {
//            List<SysScenicSpotRouteInBroadcast> sysScenicSpotRouteInBroadcasts =  sysScenicSpotRouteInBroadcastMapper.selectSpotRouteId(sysScenicReommendedRoute.getRouteId());
//            String broadcastName = "";
//
//            for (SysScenicSpotRouteInBroadcast sysScenicSpotRouteInBroadcast : sysScenicSpotRouteInBroadcasts) {
//                if (StringUtils.isEmpty(broadcastName)){
//                    broadcastName = sysScenicSpotRouteInBroadcast.getBroadcastName();
//                }else{
//                    broadcastName = broadcastName + "," + sysScenicSpotRouteInBroadcast.getBroadcastName();
//                }
//            }
//            sysScenicReommendedRoute.setBroadcastCount(String.valueOf(sysScenicSpotRouteInBroadcasts.size()));
//            sysScenicReommendedRoute.setBroadcastName(broadcastName);
//        }
        return  sysScenicSpotRecommendedRouteList;
    }

    /**
     * 根据景区id和线路名称查询线路
     * @param scenicSpotId
     * @param routeName
     * @return
     */
    @Override
    public SysScenicSpotRecommendedRoute getSpotIdAndSpotRecommendedName(Long scenicSpotId, String routeName) {

        SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute = sysScenicSpotRecommendedRouteMapper.getSpotIdAndSpotRecommendedName(scenicSpotId,routeName);
        return sysScenicSpotRecommendedRoute;
    }

    /**
     * 定时获取线路数据添加
     * @param sysScenicSpotRecommendedRoute
     * @return
     */
    @Override
    public int insert(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {
        sysScenicSpotRecommendedRoute.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotRecommendedRoute.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotRecommendedRouteMapper.insertSelective(sysScenicSpotRecommendedRoute);
        return i;
    }

    /**
     * 根据id查询
     * @param routeId
     * @return
     */
    @Override
    public SysScenicSpotRecommendedRoute getRecommendedRouteId(Long routeId) {

        SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute = sysScenicSpotRecommendedRouteMapper.selectByPrimaryKey(routeId);
        return sysScenicSpotRecommendedRoute;
    }
}
