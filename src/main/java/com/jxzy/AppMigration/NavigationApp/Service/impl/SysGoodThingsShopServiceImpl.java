package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysGoodThingsShopService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGoodThingsFabulousMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysGoodThingsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysGoodThingsShopMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author zhang
 * @Date 2023/1/10 18:06
 */
@Service
public class SysGoodThingsShopServiceImpl implements SysGoodThingsShopService {

    @Autowired
    SysGoodThingsShopMapper sysGoodThingsShopMapper;
    @Autowired
    SysGoodThingsMapper sysGoodThingsMapper;

    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;
    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;
    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    SysGoodThingsFabulousMapper sysGoodThingsFabulousMapper;


    /**
     * 添加地道好物店铺
     * @param file
     * @param sysGoodThingsShop
     * @return
     */
    @Override
    public int addSysGoodThingsShop(MultipartFile file, SysGoodThingsShop sysGoodThingsShop) {

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

                sysGoodThingsShop.setId(IdUtils.getSeqId());
                sysGoodThingsShop.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysGoodThingsShop.setGoodThingsPinyin(Tinypinyin.tinypinyin(sysGoodThingsShop.getGoodThingsName()));
                sysGoodThingsShop.setCreateTime(DateUtil.currentDateTime());
                sysGoodThingsShop.setUpdateTime(DateUtil.currentDateTime());
                int i = sysGoodThingsShopMapper.insertSelective(sysGoodThingsShop);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }


    }

    /**
     * 修改地道好物店铺
     * @param file
     * @param sysGoodThingsShop
     * @return
     */
    @Override
    public int editSysGoodThingsShop(MultipartFile file, SysGoodThingsShop sysGoodThingsShop) {

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
                sysGoodThingsShop.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysGoodThingsShop.setUpdateTime(DateUtil.currentDateTime());
                sysGoodThingsShop.setGoodThingsPinyin(Tinypinyin.tinypinyin(sysGoodThingsShop.getGoodThingsName()));
                int i = sysGoodThingsShopMapper.updateSelective(sysGoodThingsShop);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }


    }

    /**
     * 删除好物店铺
     * @param id
     * @return
     */
    @Override
    public int delSysGoodThingsShop(Long id) {

       int i = sysGoodThingsShopMapper.deleteByPrimaryKey(id);

       return i;

    }

    /**
     * 好物店铺列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGoodThingsShopList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysGoodThingsShop> list = sysGoodThingsShopMapper.list(search);

        if (list.size()>0){
            PageInfo<SysGoodThingsShop> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;


    }

    /**
     * 上传好物轮播图
     * @param file
     * @param id
     * @return
     */
    @Override
    public int addSysGoodThingsShopBanner(MultipartFile[] file, String id) {

        int t= 0;
        String filename = null;
        String url = null;
        if(file!=null&&file.length>0){
//            String uploadPath = FEEDBACK_PIC;
            String uploadPath = featuredFoodPatheGetPicUrl;

            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            try {
                //循环获取file数组中得文件
                for(int i = 0;i<file.length;i++){
                    MultipartFile files = file[i];
                    String OriginalFilename = files.getOriginalFilename();
                    String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
                    //重新随机生成名字
                    filename = UUID.randomUUID().toString() +suffixName;//重命名
                    File localFile = new File(uploadPath+filename);
                    if (StringUtils.isEmpty(url)){
                        url = uploadPath+filename;
                    }else{
                        url = url + "," + uploadPath+filename;
                    }
                    //保存文件
                    //saveFile(files, uploadPath);
//                    files.transferTo(localFile); //把上传的文件保存至本地
                    FileUtils.copyInputStreamToFile(files.getInputStream(), localFile);
                }
                //这里应该把filename保存到数据库,供前端访问时使用
                SysGoodThingsShop sysFeaturedFoodShop = new SysGoodThingsShop();
                sysFeaturedFoodShop.setDetailsPic(url);
                sysFeaturedFoodShop.setId(Long.valueOf(id));
                t = sysGoodThingsShopMapper.updateSelective(sysFeaturedFoodShop);

            }catch (IOException e){
                e.printStackTrace();
            }

        }else {
            return t;
        }

        return t;


    }

    /**
     * 查询好物店铺列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGoodThingsShopAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

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
        List<SysGoodThingsShop> list = sysGoodThingsShopMapper.list(search);
        if (!StringUtils.isEmpty(search.get("lng")) && !StringUtils.isEmpty(search.get("lat"))){
            for (SysGoodThingsShop sysGoodThingsShop : list) {

                String[] split = sysGoodThingsShop.getGoodThingsGpsBaiDu().split(",");

                if (split.length <= 0 &&  "0".equals(split[0]) && "0".equals(split[1]) && "0.0".equals(split[0]) && "0.0".equals(split[1]) ){
                    sysGoodThingsShop.setDistance(-1);
                    continue;
                }

                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                sysGoodThingsShop.setDistance(distanceOne);
                if (!StringUtils.isEmpty(search.get("uid"))){
                    Integer i = sysGoodThingsFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"),1,sysGoodThingsShop.getId());
                    if(!StringUtils.isEmpty(i)){
                        sysGoodThingsShop.setIsFabulous(i.toString());
                    }
                }
            }

        }else{
            for (SysGoodThingsShop sysGoodThingsShop : list) {
                sysGoodThingsShop.setDistance(-1);

                if (!StringUtils.isEmpty(search.get("uid"))){
                    Integer i = sysGoodThingsFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"),1,sysGoodThingsShop.getId());
                    if(!StringUtils.isEmpty(i)){
                        sysGoodThingsShop.setIsFabulous(i.toString());
                    }
                }
            }
        }
        if (list.size()>0){
            PageInfo<SysGoodThingsShop> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 好物店铺点赞或者好物店铺商品点赞
     * @param id
     * @param type
     * @param uid
     * @return
     */
    @Override
    public int addGiveTheThumbsUp(Long id, String type, String uid) {
        int i= 0;

        if ("1".equals(type)){
            i = sysGoodThingsShopMapper.addGiveTheThumbsUp(id);
            SysGoodThingsFabulous sysGoodThingsFabulous = new SysGoodThingsFabulous();
            sysGoodThingsFabulous.setId(IdUtils.getSeqId());
            sysGoodThingsFabulous.setFeaturedFoodId(id);
            sysGoodThingsFabulous.setType(type);
            sysGoodThingsFabulous.setCreateTime(DateUtil.currentDateTime());
            sysGoodThingsFabulous.setUpdateTime(DateUtil.currentDateTime());
            sysGoodThingsFabulousMapper.insert(sysGoodThingsFabulous);
        }else if("2".equals(type)){
            i = sysGoodThingsShopMapper.addGiveTheThumbsUp(id);
            SysGoodThingsFabulous sysGoodThingsFabulous = new SysGoodThingsFabulous();
            sysGoodThingsFabulous.setId(IdUtils.getSeqId());
            sysGoodThingsFabulous.setFeaturedFoodId(id);
            sysGoodThingsFabulous.setType(type);
            sysGoodThingsFabulous.setCreateTime(DateUtil.currentDateTime());
            sysGoodThingsFabulous.setUpdateTime(DateUtil.currentDateTime());
            sysGoodThingsFabulousMapper.insert(sysGoodThingsFabulous);
        }
        return i;

    }

    @Override
    public int delGiveTheThumbsUp(Long id, String type, String uid) {

       int i = sysGoodThingsFabulousMapper.delete(id,type,uid);

       return i;
    }

    /**
     * 添加好物店铺（无文件）
     * @param sysGoodThingsShop
     * @return
     */
    @Override
    public int addSysGoodThingsShopN(SysGoodThingsShop sysGoodThingsShop) {

        sysGoodThingsShop.setId(IdUtils.getSeqId());
        sysGoodThingsShop.setCreateTime(DateUtil.currentDateTime());
        sysGoodThingsShop.setUpdateTime(DateUtil.currentDateTime());
        sysGoodThingsShop.setGoodThingsPinyin(Tinypinyin.tinypinyin(sysGoodThingsShop.getGoodThingsName()));
        int i = sysGoodThingsShopMapper.insertSelective(sysGoodThingsShop);
        return i;
    }
    /**
     * 修改好物店铺（无文件）
     * @param sysGoodThingsShop
     * @return
     */
    @Override
    public int editSysGoodThingsShopN(SysGoodThingsShop sysGoodThingsShop) {

        sysGoodThingsShop.setUpdateTime(DateUtil.currentDateTime());
        sysGoodThingsShop.setGoodThingsPinyin(Tinypinyin.tinypinyin(sysGoodThingsShop.getGoodThingsName()));
        int i = sysGoodThingsShopMapper.updateSelective(sysGoodThingsShop);
        return i;

    }

    /**
     * app全局搜索好物店铺
     * @param search
     * @return
     */
    @Override
    public PageDataResult getAllSysThingsShop(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }
        List<SysGoodThingsShop> list = sysGoodThingsShopMapper.list(search);
        if (StringUtils.isEmpty((String)search.get("lng")) || StringUtils.isEmpty((String)search.get("lat"))){

        }else{//计算距离

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();

            for (SysGoodThingsShop sysGoodThingsShop : list) {

                String coordinateRange = sysGoodThingsShop.getGoodThingsGps();
                if (StringUtils.isEmpty(coordinateRange)){
                    sysGoodThingsShop.setDistance(-1d);
                    continue;
                }
                String[] split = coordinateRange.split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysGoodThingsShop.setDistance(distanceOne);
            }
        }

        if (list.size()>0){
            PageInfo<SysGoodThingsShop> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     *
     * @param search
     * @return
     */
    @Override
    public List<SysGoodThingsShop> getAllSysThingsShopN(Map<String, Object> search) {

        Map<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }
        List<SysGoodThingsShop> list = sysGoodThingsShopMapper.list(search);
        if (StringUtils.isEmpty((String)search.get("lng")) || StringUtils.isEmpty((String)search.get("lat"))){

        }else{//计算距离

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();

            for (SysGoodThingsShop sysGoodThingsShop : list) {

                String coordinateRange = sysGoodThingsShop.getGoodThingsGps();
                if (StringUtils.isEmpty(coordinateRange)){
                    sysGoodThingsShop.setDistance(-1d);
                    continue;
                }
                String[] split = coordinateRange.split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysGoodThingsShop.setDistance(distanceOne);
            }
        }
        return list;
    }

    /**
     * 导出地道好物
     * @param search
     * @return
     */
    @Override
    public List<SysGoodThingsShop> uploadExcelGoodThings(Map<String, Object> search) {


        List<SysGoodThingsShop> list = sysGoodThingsShopMapper.list(search);
        return list;

    }


}
