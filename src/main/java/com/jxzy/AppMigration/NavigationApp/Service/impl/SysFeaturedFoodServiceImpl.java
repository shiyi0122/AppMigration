package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysFeaturedFoodService;
import com.jxzy.AppMigration.NavigationApp.dao.SysFeaturedFoodFabulousMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysFeaturedFoodMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysFeaturedFoodShopMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFood;
import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFoodShop;
import com.jxzy.AppMigration.NavigationApp.entity.SysGame;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
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
 * @Date 2023/1/10 13:25
 * 特色美食店铺商品
 */
@Service
public class SysFeaturedFoodServiceImpl implements SysFeaturedFoodService {

    @Autowired
    SysFeaturedFoodMapper sysFeaturedFoodMapper;

    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;
    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    SysFeaturedFoodShopMapper sysFeaturedFoodShopMapper;

    @Autowired
    SysFeaturedFoodFabulousMapper sysFeaturedFoodFabulousMapper;

    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    /**
     * 添加店铺商品
     * @param file
     * @param sysFeaturedFood
     * @return
     */
    @Override
    public int addSysFeaturedFood(MultipartFile file, SysFeaturedFood sysFeaturedFood) {

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

                sysFeaturedFood.setId(IdUtils.getSeqId());
                sysFeaturedFood.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysFeaturedFood.setCreateTime(DateUtil.currentDateTime());
                sysFeaturedFood.setUpdateTime(DateUtil.currentDateTime());
                int i = sysFeaturedFoodMapper.insertSelective(sysFeaturedFood);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }
    }

    /**
     * 修改店铺商品
     * @param file
     * @param sysFeaturedFood
     * @return
     */
    @Override
    public int editSysFeaturedFood(MultipartFile file, SysFeaturedFood sysFeaturedFood) {

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
                sysFeaturedFood.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysFeaturedFood.setUpdateTime(DateUtil.currentDateTime());
                int i = sysFeaturedFoodMapper.updateSelective(sysFeaturedFood);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }


    }

    /**
     * 删除店铺商品
     * @param id
     * @return
     */
    @Override
    public int delSysFeaturedFood(Long id) {

        int i = sysFeaturedFoodMapper.deleteByPrimaryKey(id);
        return i;
    }

    /**
     * 店铺商品列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysFeaturedFoodList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysFeaturedFood> list = sysFeaturedFoodMapper.list(search);

        if (list.size()>0){
            PageInfo<SysFeaturedFood> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * app特色美食详情
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysFeaturedFoodAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();


        SysFeaturedFoodShop sysFeaturedFoodShop = sysFeaturedFoodShopMapper.selectById(String.valueOf(search.get("Id")));

        List<SysFeaturedFood> list = sysFeaturedFoodMapper.list(search);

        for (SysFeaturedFood sysFeaturedFood : list) {
            Integer i = sysFeaturedFoodFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"), 2, sysFeaturedFood.getId());
            sysFeaturedFood.setIsFabulous(i.toString());
        }

        sysFeaturedFoodShop.setSysFeaturedFoodList(list);
        if (list.size()>0){
            PageInfo<SysFeaturedFood> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(sysFeaturedFoodShop);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 添加特色美食店铺商品(无文件)
     * @param sysFeaturedFood
     * @return
     */
    @Override
    public int addSysFeaturedFoodN(SysFeaturedFood sysFeaturedFood) {

        sysFeaturedFood.setId(IdUtils.getSeqId());
        sysFeaturedFood.setCreateTime(DateUtil.currentDateTime());
        sysFeaturedFood.setUpdateTime(DateUtil.currentDateTime());
        int i = sysFeaturedFoodMapper.insertSelective(sysFeaturedFood);
        return i;
    }

    @Override
    public int editSysFeaturedFoodN(SysFeaturedFood sysFeaturedFood) {

        sysFeaturedFood.setUpdateTime(DateUtil.currentDateTime());
        int i = sysFeaturedFoodMapper.updateSelective(sysFeaturedFood);
        return i;
    }

    /**
     * app端全局搜索特色美食店铺
     * @param search
     * @return
     */
    @Override
    public PageDataResult getAllSysFeaturedFoodShop(Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }
        PageHelper.startPage((Integer) search.get("pageNum"),(Integer) search.get("pageSize"));
        List<SysFeaturedFoodShop> list = sysFeaturedFoodShopMapper.list(search);
        if (StringUtils.isEmpty((String)search.get("lng")) || StringUtils.isEmpty((String)search.get("lat"))){

        }else{//计算距离

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();

            for (SysFeaturedFoodShop sysFeaturedFoodShop : list) {

                String coordinateRange = sysFeaturedFoodShop.getFeaturedGps();
                if (StringUtils.isEmpty(coordinateRange)){
                    sysFeaturedFoodShop.setDistance(-1d);
                    continue;
                }
                String[] split = coordinateRange.split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysFeaturedFoodShop.setDistance(distanceOne);
            }
        }

        if (list.size()>0){
            PageInfo<SysFeaturedFoodShop> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;


    }

    @Override
    public List<SysFeaturedFoodShop> getAllSysFeaturedFoodShopN(Map<String, Object> search) {

        Map<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }
        PageHelper.startPage((Integer) search.get("pageNum"),(Integer) search.get("pageSize"));
        List<SysFeaturedFoodShop> list = sysFeaturedFoodShopMapper.list(search);
        if (StringUtils.isEmpty((String)search.get("lng")) || StringUtils.isEmpty((String)search.get("lat"))){

        }else{//计算距离

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();

            for (SysFeaturedFoodShop sysFeaturedFoodShop : list) {

                String coordinateRange = sysFeaturedFoodShop.getFeaturedGps();
                if (StringUtils.isEmpty(coordinateRange)){
                    sysFeaturedFoodShop.setDistance(-1d);
                    continue;
                }
                String[] split = coordinateRange.split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysFeaturedFoodShop.setDistance(distanceOne);
            }
        }

        return list;

    }


}
