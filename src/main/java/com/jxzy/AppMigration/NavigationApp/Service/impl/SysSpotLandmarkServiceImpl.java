package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysSpotLandmarkService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLabel;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLandmark;
import com.jxzy.AppMigration.NavigationApp.dao.SysSpotLandmarkMapper;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/16 17:03
 */
@Service
public class SysSpotLandmarkServiceImpl implements SysSpotLandmarkService {

    @Autowired
    SysSpotLandmarkMapper sysSpotLandmarkMapper;

    /**
     * 添加
     * @param sysSpotLandmark
     * @return
     */
    @Override
    public int addSpotLandmark(SysSpotLandmark sysSpotLandmark) {

        sysSpotLandmark.setId(IdUtils.getSeqId());
        sysSpotLandmark.setLandmarkNamePinyin(Tinypinyin.tinypinyin(sysSpotLandmark.getLandmarkName()));
        sysSpotLandmark.setCreateTime(DateUtil.currentDateTime());
        sysSpotLandmark.setUpdateTime(DateUtil.currentDateTime());
        int i = sysSpotLandmarkMapper.insert(sysSpotLandmark);
        return i;
    }

    /**
     * 修改
     * @param sysSpotLandmark
     * @return
     */
    @Override
    public int editSpotLandmark(SysSpotLandmark sysSpotLandmark) {

        sysSpotLandmark.setUpdateTime(DateUtil.currentDateTime());
        sysSpotLandmark.setLandmarkNamePinyin(Tinypinyin.tinypinyin(sysSpotLandmark.getLandmarkName()));
        int i = sysSpotLandmarkMapper.update(sysSpotLandmark);
        return i;
    }

    /**
     * s删除
     * @param id
     * @return
     */
    @Override
    public int delSpotLandmark(Long id) {

      int i =  sysSpotLandmarkMapper.delete(id);
      return i;
    }

    /**
     * 地标列表重新
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getSpotLandmarkList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        List<SysSpotLandmark> sysSpotLabelList = new ArrayList<>();

        PageDataResult pageDataResult = new PageDataResult();
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();
        PageHelper.startPage(pageNum,pageSize);
        List<SysSpotLandmark> list = sysSpotLandmarkMapper.list(search);

        for (SysSpotLandmark sysSpotLandmark : list) {
            String[] split = sysSpotLandmark.getLandmarkNameGpsBaiDu().split(",");
            if (!StringUtils.isEmpty(split) && split.length>0){

                from = new Point2D.Double( Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                sysSpotLandmark.setDistance(distanceOne);

                //距离限制
//                double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(split[1]), Double.valueOf(split[0]), Double.valueOf(lat), Double.valueOf(lng));
//                 if (distanceOne / 1000 < 0.01 ){
//                     sysSpotLabelList.add(sysSpotLandmark);
//                 }
            }else{
               continue;
            }
        }

        if (list.size()>0){
            PageInfo<SysSpotLandmark> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;

    }

    /**
     * 后台查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getAdminSpotLandmarkList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        List<SysSpotLandmark> sysSpotLabelList = new ArrayList<>();

        PageDataResult pageDataResult = new PageDataResult();
//        Point2D.Double from = new Point2D.Double();
//        Point2D.Double to = new Point2D.Double();
        PageHelper.startPage(pageNum,pageSize);
        List<SysSpotLandmark> list = sysSpotLandmarkMapper.list(search);
//        for (SysSpotLandmark sysSpotLandmark : list) {
//            String[] split = sysSpotLandmark.getLandmarkNameGpsBaiDu().split(",");
//            if (!StringUtils.isEmpty(split) && split.length>0){
//
//                from = new Point2D.Double( Double.valueOf(split[0]),Double.valueOf(split[1]));
//                to = new Point2D.Double(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
//                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
////                double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(split[1]), Double.valueOf(split[0]), Double.valueOf(lat), Double.valueOf(lng));
//                if (distanceOne / 1000 < 0.01 ){
//                    sysSpotLabelList.add(sysSpotLandmark);
//                }
//            }else{
//                continue;
//            }
//        }

        if (list.size()>0){
            PageInfo<SysSpotLandmark> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;


    }
}
