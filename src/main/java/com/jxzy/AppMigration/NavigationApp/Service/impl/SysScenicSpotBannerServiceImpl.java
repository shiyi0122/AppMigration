package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBannerService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBannerMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBanner;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import io.swagger.models.auth.In;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/7/30 13:48
 */
@Service
public class SysScenicSpotBannerServiceImpl implements SysScenicSpotBannerService {

    @Autowired
    SysScenicSpotBannerMapper sysScenicSpotBannerMapper;
    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;

    @Value("${bannerPatheGetPicPaht}")
    private String GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH;

    @Value("${bannerPatheGetPicUrl}")
    private String GET_SYS_SCENIC_SPOT_BANNER_IMG_URL;

    @Override
    public List getScenicSpotBanner(String lng,String lat,Map<String, Object> search) {
        if ((Integer) search.get("type") == 1){//首页轮播图

            List<SysScenicSpotBanner> list = new ArrayList<>();
            if (!StringUtils.isEmpty(lng) && !StringUtils.isEmpty(lat)){
                list = sysScenicSpotBannerMapper.getScenicSpotBanner(search);
                if (!StringUtils.isEmpty(list)){
                    String spotId = list.get(0).getSpotId();
                    Point2D.Double from = new Point2D.Double();
                    Point2D.Double to = new Point2D.Double();
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(Long.parseLong(spotId));
                    if (!StringUtils.isEmpty(sysScenicSpot.getCoordinateRange())){
                        String[] split = sysScenicSpot.getCoordinateRange().split(",");
                        from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                        to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                        double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                        list.get(0).setDistance(distanceOne);
                    }
                }
            }else{
                list = sysScenicSpotBannerMapper.getScenicSpotBanner(search);
            }
            return list;

        }else{
            //景区轮播图
            List<SysScenicSpotBanner> list = new ArrayList<>();
            SysScenicSpotBanner sysScenicSpotBanner = new SysScenicSpotBanner();
            list = sysScenicSpotBannerMapper.getScenicSpotBanner(search);
            Point2D.Double from = new Point2D.Double();
            Point2D.Double to = new Point2D.Double();
//        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey((Long) search.get("spotId"));
            List<SysScenicSpot> scenicSpots = sysScenicSpotMapper.adminSysScenicSpotFilesList(search);
            if (list.size()==0){
               sysScenicSpotBanner.setSpotId(scenicSpots.get(0).getScenicSpotId().toString());
               list.add(sysScenicSpotBanner);
            }
            list.get(0).setUrl(scenicSpots.get(0).getScenicSpotImgUrl());
            if (!StringUtils.isEmpty(lng) && !StringUtils.isEmpty(lat)){
                if (!StringUtils.isEmpty(scenicSpots.get(0).getCoordinateRange())){
                    String[] split = scenicSpots.get(0).getCoordinateRange().split(",");
                    from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                    list.get(0).setDistance(distanceOne);
                }
            }
            return list;
        }
    }

    @Override
    public int addScenicSpotBanner(MultipartFile file, SysScenicSpotBanner sysScenicSpotBanner) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".png") || type.equals(".jpg")){
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH + filename;// 存放位置
            File destFile = new File(path);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            }catch (Exception e){
                e.printStackTrace();
            }
            sysScenicSpotBanner.setUrl(GET_SYS_SCENIC_SPOT_BANNER_IMG_URL+ filename);
        }
        sysScenicSpotBanner.setId(IdUtils.getSeqId());
        sysScenicSpotBanner.setCreateTime(DateUtil.currentDateTime());
        sysScenicSpotBanner.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotBannerMapper.addScenicSpotBanner(sysScenicSpotBanner);
        return  i ;
    }

    @Override
    public int editScenicSpotBanner(MultipartFile file, SysScenicSpotBanner sysScenicSpotBanner) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名

        if (type.equals(".png") || type.equals(".jpg")){

            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH + filename;// 存放位置
            File destFile = new File(path);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            }catch (Exception e){
                e.printStackTrace();
            }
            sysScenicSpotBanner.setUrl(GET_SYS_SCENIC_SPOT_BANNER_IMG_URL+ filename);
        }

        sysScenicSpotBanner.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotBannerMapper.editScenicSpotBanner(sysScenicSpotBanner);
        return  i ;
    }
}
