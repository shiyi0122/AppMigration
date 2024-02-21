package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysFeaturedFoodShopService;
import com.jxzy.AppMigration.NavigationApp.dao.SysFeaturedFoodFabulousMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysFeaturedFoodMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysFeaturedFoodShopMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.util.*;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author zhang
 * @Date 2023/1/9 17:32
 * 特色美食
 */
@Service
public class SysFeaturedFoodShopServiceImpl implements SysFeaturedFoodShopService {

    @Autowired
    SysFeaturedFoodShopMapper sysFeaturedFoodShopMapper;
    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;
    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;
    @Autowired
    FileUploadUtil fileUploadUtil;


    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    SysFeaturedFoodMapper sysFeaturedFoodMapper;

    @Autowired
    SysFeaturedFoodFabulousMapper sysFeaturedFoodFabulousMapper;

    /**
     * 添加特色美食店铺
     * @param file
     * @param sysFeaturedFoodShop
     * @return
     */
    @Override
    public int addSysFeaturedFoodShop(MultipartFile file, SysFeaturedFoodShop sysFeaturedFoodShop) {
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

                sysFeaturedFoodShop.setId(IdUtils.getSeqId());
                sysFeaturedFoodShop.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysFeaturedFoodShop.setFeaturedFoodPinyin(Tinypinyin.tinypinyin(sysFeaturedFoodShop.getFeaturedFoodName()));
                sysFeaturedFoodShop.setCreateTime(DateUtil.currentDateTime());
                sysFeaturedFoodShop.setUpdateTime(DateUtil.currentDateTime());
                int i = sysFeaturedFoodShopMapper.insertSelective(sysFeaturedFoodShop);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }

    }

    /**
     * 修改特色美食店铺
     * @param file
     * @param sysFeaturedFoodShop
     * @return
     */
    @Override
    public int editSysFeaturedFoodShop(MultipartFile file, SysFeaturedFoodShop sysFeaturedFoodShop) {

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
                sysFeaturedFoodShop.setCoverPic(featuredFoodPatheGetPicUrl+filename);
                sysFeaturedFoodShop.setUpdateTime(DateUtil.currentDateTime());
                sysFeaturedFoodShop.setFeaturedFoodPinyin(Tinypinyin.tinypinyin(sysFeaturedFoodShop.getFeaturedFoodName()));
                int i = sysFeaturedFoodShopMapper.updateSelective(sysFeaturedFoodShop);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }


    }

    /**
     * 删除特色美食店铺
     * @param id
     * @return
     */
    @Override
    public int delSysFeaturedFoodShop(Long id) {

        int i = sysFeaturedFoodShopMapper.deleteByPrimaryKey(id);
        return i;
    }

    /**
     * 查询特色美食店铺列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getSysFeaturedFoodShopList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysFeaturedFoodShop> list = sysFeaturedFoodShopMapper.list(search);
//        for (SysFeaturedFoodShop sysFeaturedFoodShop : list) {
//
//
//        }

        if (list.size()>0){
            PageInfo<SysFeaturedFoodShop> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    @Override
    public int addSysFeaturedFoodShopBanner(MultipartFile[] file, String id) {
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
                SysFeaturedFoodShop sysFeaturedFoodShop = new SysFeaturedFoodShop();
                sysFeaturedFoodShop.setDetailsPic(url);
                sysFeaturedFoodShop.setId(Long.valueOf(id));
                t = sysFeaturedFoodShopMapper.updateSelective(sysFeaturedFoodShop);

            }catch (IOException e){
              e.printStackTrace();
            }

        }else {
            return t;
        }

        return t;
    }

    /**
     * app特色美食店铺搜索
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysFeaturedFoodShopAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
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
        List<SysFeaturedFoodShop> list = sysFeaturedFoodShopMapper.list(search);
        if (!StringUtils.isEmpty(search.get("lng")) && !StringUtils.isEmpty(search.get("lat"))){
            for (SysFeaturedFoodShop sysFeaturedFoodShop : list) {


                String[] split = sysFeaturedFoodShop.getFeaturedGpsBaiDu().split(",");
                if (split.length <= 0 &&  "0".equals(split[0]) && "0".equals(split[1]) ){
                    sysFeaturedFoodShop.setDistance(-1);
                    continue;
                }
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                sysFeaturedFoodShop.setDistance(distanceOne);

                if (!StringUtils.isEmpty(search.get("uid"))){
                  Integer i = sysFeaturedFoodFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"),1,sysFeaturedFoodShop.getId());
                  sysFeaturedFoodShop.setIsFabulous(i.toString());
                }
            }

        }else{
            for (SysFeaturedFoodShop sysFeaturedFoodShop : list) {
                sysFeaturedFoodShop.setDistance(-1);

                if (!StringUtils.isEmpty(search.get("uid"))){
                    Integer i = sysFeaturedFoodFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"),1,sysFeaturedFoodShop.getId());
                    sysFeaturedFoodShop.setIsFabulous(i.toString());
                }
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

    /**
     * 店铺或店铺商品点赞
     * @param id
     * @param type
     * @return
     */
    @Override
    public int addGiveTheThumbsUp(Long id, String type,String uid) {
        int i= 0;

        if ("1".equals(type)){
            i = sysFeaturedFoodShopMapper.addGiveTheThumbsUp(id);
            SysFeaturedFoodFabulous sysFeaturedFoodFabulous = new SysFeaturedFoodFabulous();
            sysFeaturedFoodFabulous.setId(IdUtils.getSeqId());
            sysFeaturedFoodFabulous.setFeaturedFoodId(id);
            sysFeaturedFoodFabulous.setType(type);
            sysFeaturedFoodFabulous.setCreateTime(DateUtil.currentDateTime());
            sysFeaturedFoodFabulous.setUpdateTime(DateUtil.currentDateTime());
            sysFeaturedFoodFabulousMapper.insert(sysFeaturedFoodFabulous);
        }else if("2".equals(type)){
            i = sysFeaturedFoodMapper.addGiveTheThumbsUp(id);
            SysFeaturedFoodFabulous sysFeaturedFoodFabulous = new SysFeaturedFoodFabulous();
            sysFeaturedFoodFabulous.setId(IdUtils.getSeqId());
            sysFeaturedFoodFabulous.setFeaturedFoodId(id);
            sysFeaturedFoodFabulous.setType(type);
            sysFeaturedFoodFabulous.setCreateTime(DateUtil.currentDateTime());
            sysFeaturedFoodFabulous.setUpdateTime(DateUtil.currentDateTime());
            sysFeaturedFoodFabulousMapper.insert(sysFeaturedFoodFabulous);
        }

        return i;
    }

    /**
     * 取消点赞
     * @param id
     * @param type
     * @param uid
     * @return
     */
    @Override
    public int delGiveTheThumbsUp(Long id, String type, String uid) {

       int i = sysFeaturedFoodFabulousMapper.delete(id,type,uid);

       return i;
    }

    /**
     * 后台管理——添加特色美食店铺(无文件)
     *
     * @param
     * @return
     */
    @Override
    public int addSysFeaturedFoodShopN(SysFeaturedFoodShop sysFeaturedFoodShop) {

        sysFeaturedFoodShop.setId(IdUtils.getSeqId());
        sysFeaturedFoodShop.setCreateTime(DateUtil.currentDateTime());
        sysFeaturedFoodShop.setUpdateTime(DateUtil.currentDateTime());
        sysFeaturedFoodShop.setFeaturedFoodPinyin(Tinypinyin.tinypinyin(sysFeaturedFoodShop.getFeaturedFoodName()));
        int i = sysFeaturedFoodShopMapper.insertSelective(sysFeaturedFoodShop);
        return i ;

    }
    /**
     * 后台管理——修改特色美食店铺(无文件)
     *
     * @param
     * @return
     */
    @Override
    public int editSysFeaturedFoodShopN(SysFeaturedFoodShop sysFeaturedFoodShop) {

        sysFeaturedFoodShop.setUpdateTime(DateUtil.currentDateTime());
        sysFeaturedFoodShop.setFeaturedFoodPinyin(Tinypinyin.tinypinyin(sysFeaturedFoodShop.getFeaturedFoodName()));

        int i = sysFeaturedFoodShopMapper.updateSelective(sysFeaturedFoodShop);
        return i;

    }
    @Override
    public int addSysFeaturedFoodShopBannerN(Long id,String url) {

        SysFeaturedFoodShop sysFeaturedFoodShop = new SysFeaturedFoodShop();
        sysFeaturedFoodShop.setDetailsPic(url);
        sysFeaturedFoodShop.setId(Long.valueOf(id));
        int i= sysFeaturedFoodShopMapper.updateSelective(sysFeaturedFoodShop);
        return i;
    }

    /**
     * 导出美食店铺
     * @param search
     * @return
     */
    @Override
    public List<SysFeaturedFoodShop> uploadExcelFeaturedFood(Map<String, Object> search) {


        List<SysFeaturedFoodShop> list = sysFeaturedFoodShopMapper.list(search);

        return list;
    }
}
