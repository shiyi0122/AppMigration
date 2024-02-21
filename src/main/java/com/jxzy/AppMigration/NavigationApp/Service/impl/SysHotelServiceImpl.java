package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysHotelService;
import com.jxzy.AppMigration.NavigationApp.dao.SysHotelDetailsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysHotelMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import net.bytebuddy.asm.Advice;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 15:33
 * 酒店民宿
 */
@Service
public  class SysHotelServiceImpl implements SysHotelService {

    @Autowired
    SysHotelMapper sysHotelMapper;


    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;
    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;
    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    SysHotelDetailsMapper sysHotelDetailsMapper;

    /**
     * 添加酒店民宿
     * @param file
     * @param sysHotel
     * @return
     */
    @Override
    public int addSysHotel(MultipartFile file, SysHotel sysHotel) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = featuredFoodPatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    // 限制大小
//                    long size = file.getSize() / 1024;//kb
//                    if (size >= 2048) {
//                        return 3;
//                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //压缩上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(file, featuredFoodPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录

                sysHotel.setId(IdUtils.getSeqId());
                sysHotel.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysHotel.setCreateTime(DateUtil.currentDateTime());
                sysHotel.setUpdateTime(DateUtil.currentDateTime());
                int i = sysHotelMapper.insertSelective(sysHotel);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }




    }

    /**
     * 修改酒店民宿
     * @param file
     * @param sysHotel
     * @return
     */
    @Override
    public int editSysHotel(MultipartFile file, SysHotel sysHotel) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = featuredFoodPatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    // 限制大小
//                    long size = file.getSize() / 1024;//kb
//                    if (size >= 2048) {
//                        return 3;
//                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //压缩上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(file, featuredFoodPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysHotel.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysHotel.setUpdateTime(DateUtil.currentDateTime());
                int i = sysHotelMapper.updateSelective(sysHotel);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }




    }

    @Override
    public int delSysHotel(Long id) {

       int i = sysHotelMapper.deleteByPrimaryKey(id);

       return i;
    }

    /**
     * 查询酒店民宿
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysHotelList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysHotel> list = sysHotelMapper.list(search);

        if (list.size()>0){
            PageInfo<SysHotel> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;


    }

    /**
     * app查询酒店民宿
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysHotelAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        String lng = (String) search.get("lng");
        String lat = (String) search.get("lat");
        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }

        PageHelper.startPage(pageNum,pageSize);
        List<SysHotel> list = sysHotelMapper.list(search);
        if (!StringUtils.isEmpty(search.get("lng")) && !StringUtils.isEmpty(search.get("lat"))){
            for (SysHotel sysHotel : list) {

                String[] split = sysHotel.getHotelGpsBaiDu().split(",");

                if (split.length <= 0 &&  "0".equals(split[0]) && "0".equals(split[1]) ){
                    sysHotel.setDistance(-1);
                    continue;
                }

                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                sysHotel.setDistance(distanceOne);

//                if (!StringUtils.isEmpty(search.get("uid"))){
//                    Integer i = sysFeaturedFoodFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"),1,sysFeaturedFoodShop.getId());
//                    sysFeaturedFoodShop.setIsFabulous(i.toString());
//                }

            }

        }else{
            for (SysHotel sysHotel : list) {
                sysHotel.setDistance(-1);

//                if (!StringUtils.isEmpty(search.get("uid"))){
//                    Integer i = sysFeaturedFoodFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"),1,sysFeaturedFoodShop.getId());
//                    sysFeaturedFoodShop.setIsFabulous(i.toString());
//                }
            }
        }
        if (list.size()>0){
            PageInfo<SysHotel> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;



    }

    /**
     * 添加酒店民宿（无文件）
     * @param sysHotel
     * @return
     */
    @Override
    public int addSysHotelN(SysHotel sysHotel) {

        sysHotel.setId(IdUtils.getSeqId());
        sysHotel.setCreateTime(DateUtil.currentDateTime());
        sysHotel.setUpdateTime(DateUtil.currentDateTime());
        sysHotel.setHotelPinyin(sysHotel.getHotelName());
        int i = sysHotelMapper.insertSelective(sysHotel);
        return i;
    }

    /**
     * 修改酒店民宿（无文件）
     * @param sysHotel
     * @return
     */
    @Override
    public int editSysHotelN(SysHotel sysHotel) {


        sysHotel.setUpdateTime(DateUtil.currentDateTime());
        sysHotel.setHotelPinyin(sysHotel.getHotelName());
        int i = sysHotelMapper.updateSelective(sysHotel);
        return i;
    }

    @Override
    public PageDataResult getAllSysHotel(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }
        List<SysHotel> list = sysHotelMapper.list(search);
        if (StringUtils.isEmpty((String)search.get("lng")) || StringUtils.isEmpty((String)search.get("lat"))){

        }else{//计算距离

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();

            for (SysHotel sysHotel : list) {

                String coordinateRange = sysHotel.getHotelGps();
                if (StringUtils.isEmpty(coordinateRange)){
                    sysHotel.setDistance(-1d);
                    continue;
                }
                String[] split = coordinateRange.split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysHotel.setDistance(distanceOne);
            }
        }

        if (list.size()>0){
            PageInfo<SysHotel> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    @Override
    public List<SysHotel> getAllSysHotelN(Map<String, Object> search) {


        Map<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }
        List<SysHotel> list = sysHotelMapper.list(search);
        if (StringUtils.isEmpty((String)search.get("lng")) || StringUtils.isEmpty((String)search.get("lat"))){

        }else{//计算距离

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();

            for (SysHotel sysHotel : list) {

                String coordinateRange = sysHotel.getHotelGps();
                if (StringUtils.isEmpty(coordinateRange)){
                    sysHotel.setDistance(-1d);
                    continue;
                }
                String[] split = coordinateRange.split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysHotel.setDistance(distanceOne);
            }
        }
        return list;
    }

    /**
     * 酒店民宿导出
     * @param search
     * @return
     */
    @Override
    public List<SysHotel> uploadExcelHotel(Map<String, Object> search) {

        List<SysHotel> list = sysHotelMapper.uploadExcelHotel(search);
        return list;

    }

    /**
     * 根据酒店id，获取酒店详情
     * @param search
     * @return
     */
    @Override
    public  SysHotel  getSysHotelIdDetails(Map<String, Object> search) {

        SysHotel sysHotel = sysHotelMapper.selectById(search.get("id").toString());

        List<SysHotelDetails> list = sysHotelDetailsMapper.getSysHotelIdDetails(search);

        sysHotel.setSysHotelDetailsList(list);
        return sysHotel;
    }
}
