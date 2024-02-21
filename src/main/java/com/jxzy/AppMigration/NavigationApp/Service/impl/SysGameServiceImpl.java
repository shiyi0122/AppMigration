package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysGameService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGameMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainment;
import com.jxzy.AppMigration.NavigationApp.entity.SysGame;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;
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
 * @Date 2023/1/11 17:18
 */
@Service
public class SysGameServiceImpl implements SysGameService {

    @Autowired
    SysGameMapper sysGameMapper;

    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;
    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;
    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;



    @Override
    public int addSysGame(MultipartFile file, SysGame sysGame) {

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

                sysGame.setId(IdUtils.getSeqId());
                sysGame.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysGame.setGamePinyin(Tinypinyin.tinypinyin(sysGame.getGameName()));
                sysGame.setCreateTime(DateUtil.currentDateTime());
                sysGame.setUpdateTime(DateUtil.currentDateTime());
                int i = sysGameMapper.insertSelective(sysGame);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }

    }

    /**
     * 修改娱乐设施
     * @param file
     * @param sysGame
     * @return
     */
    @Override
    public int editSysGame(MultipartFile file, SysGame sysGame) {

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
                sysGame.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysGame.setUpdateTime(DateUtil.currentDateTime());
                sysGame.setGamePinyin(Tinypinyin.tinypinyin(sysGame.getGameName()));
                int i = sysGameMapper.updateSelective(sysGame);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }

    }

    /**
     * 删除娱乐设施
     * @param id
     * @return
     */
    @Override
    public int delSysGame(Long id) {

       int i = sysGameMapper.deleteByPrimaryKey(id);
       return i;
    }

    /**
     * 娱乐设施列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGameList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysGame> list = sysGameMapper.list(search);

        if (list.size()>0){
            PageInfo<SysGame> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * app娱乐设施列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGameAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
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
        List<SysGame> list = sysGameMapper.list(search);
        if (!StringUtils.isEmpty(search.get("lng")) && !StringUtils.isEmpty(search.get("lat"))){
            for (SysGame sysGame : list) {

                String[] split = sysGame.getGameGpsBaiDu().split(",");
                if (split.length <= 0 &&  "0".equals(split[0]) && "0".equals(split[1]) ){
                    sysGame.setDistance(-1);
                    continue;
                }
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                sysGame.setDistance(distanceOne);
//                if (!StringUtils.isEmpty(search.get("uid"))){
//                    Integer i = sysGoodThingsFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"),1,sysGoodThingsShop.getId());
//                    sysGoodThingsShop.setIsFabulous(i.toString());
//                }
            }
        }else{
            for (SysGame sysGame : list) {
                sysGame.setDistance(-1);

//                if (!StringUtils.isEmpty(search.get("uid"))){
//                    Integer i = sysGoodThingsFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"),1,sysGoodThingsShop.getId());
//                    sysGoodThingsShop.setIsFabulous(i.toString());
//                }
            }
        }
        if (list.size()>0){
            PageInfo<SysGame> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 添加娱乐设施（无文件）
     * @param
     * @param
     * @return
     */
    @Override
    public int addSysGameN(SysGame sysGame) {

        sysGame.setId(IdUtils.getSeqId());
        sysGame.setCreateTime(DateUtil.currentDateTime());
        sysGame.setUpdateTime(DateUtil.currentDateTime());
        sysGame.setGamePinyin(Tinypinyin.tinypinyin(sysGame.getGameName()));
        int i = sysGameMapper.insertSelective(sysGame);
        return i;
    }

    /**
     * 修改娱乐设施（无文件）
     * @param
     * @param
     * @return
     */
    @Override
    public int editSysGameN(SysGame sysGame) {


        sysGame.setUpdateTime(DateUtil.currentDateTime());
        sysGame.setGamePinyin(Tinypinyin.tinypinyin(sysGame.getGameName()));
        int i = sysGameMapper.updateSelective(sysGame);
        return i;


    }

    /**
     * app全局搜索娱乐设施
     * @param search
     * @return
     */
    @Override
    public PageDataResult getAllSysGameShop(Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }
        List<SysGame> list = sysGameMapper.list(search);
        if (StringUtils.isEmpty((String)search.get("lng")) || StringUtils.isEmpty((String)search.get("lat"))){

        }else{//计算距离

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();

            for (SysGame sysGame : list) {

                String coordinateRange = sysGame.getGameGps();
                if (StringUtils.isEmpty(coordinateRange)){
                    sysGame.setDistance(-1d);
                    continue;
                }
                String[] split = coordinateRange.split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysGame.setDistance(distanceOne);
            }
        }

        if (list.size()>0){
            PageInfo<SysGame> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    @Override
    public List<SysGame> getAllSysGameShopN(Map<String, Object> search) {

        Map<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }
        List<SysGame> list = sysGameMapper.list(search);
        if (StringUtils.isEmpty((String)search.get("lng")) || StringUtils.isEmpty((String)search.get("lat"))){

        }else{//计算距离

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();

            for (SysGame sysGame : list) {

                String coordinateRange = sysGame.getGameGps();
                if (StringUtils.isEmpty(coordinateRange)){
                    sysGame.setDistance(-1d);
                    continue;
                }
                String[] split = coordinateRange.split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysGame.setDistance(distanceOne);
            }
        }
        return list;
    }

}
