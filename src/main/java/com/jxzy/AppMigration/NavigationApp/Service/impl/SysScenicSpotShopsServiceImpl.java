package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotShopsService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotShopsCommodityTypeMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotShopsDetailsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotShopsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotShopsTypeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotShopsExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsCommodityType;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsDetails;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsType;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class SysScenicSpotShopsServiceImpl implements SysScenicSpotShopsService {

    @Autowired
    private SysScenicSpotShopsMapper sysScenicSpotShopsMapper;
    @Autowired
    private SysScenicSpotShopsTypeMapper sysScenicSpotShopsTypeMapper;
    @Autowired
    private SysScenicSpotShopsDetailsMapper sysScenicSpotShopsDetailsMapper;
    @Autowired
    private SysScenicSpotShopsCommodityTypeMapper sysScenicSpotShopsCommodityTypeMapper;

    @Value("${spotShopListPatheGetPicUrl}")
    private String spotShopListPatheGetPicUrl;
    @Value("${spotShopListPatheGetPicPaht}")
    private String spotShopListPatheGetPicPaht;
    @Value("${spotShopDetailsPatheGetPicUrl}")
    private String spotShopDetailsPatheGetPicUrl;
    @Value("${spotShopdetailsPatheGetPicPaht}")
    private String spotShopDetailsPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;


    /**
     * 查询商品店铺详情
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops>
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    public List<SysScenicSpotShops> queryScenicShopsList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotShopsMapper.queryScenicShopsList(search);
    }

    /**
     * 查询最近的店铺信息
     * zhang
     * @param spotId
     * @param lng
     * @param lat
     * @return
     */
    @Override
    public SysScenicSpotShops getLatelyScewnicShops(String spotId, String lng, String lat) {

        Map<String,Object> search = new HashMap<>();

        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();
//        search.put("scenicSpotId",spotId);
        List<SysScenicSpotShops> sysScenicSpotShops = sysScenicSpotShopsMapper.getScenicShopsList(spotId);

        if (sysScenicSpotShops.size() > 0){
            for (SysScenicSpotShops sysScenicSpotShop : sysScenicSpotShops) {
                String[] split = sysScenicSpotShop.getShopsGps().split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                sysScenicSpotShop.setDistance(distanceOne);
            }

            Collections.sort(sysScenicSpotShops, new Comparator<SysScenicSpotShops>() {
                @Override
                public int compare(SysScenicSpotShops o1, SysScenicSpotShops o2) {
                    //降序
                    double i = o2.getDistance() - o1.getDistance();
                    if (i>0){
                        return 1;
                    }else if (i<0){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
            return sysScenicSpotShops.get(0);
        }else{

            return null;
        }

    }

    /**
     * 后台店铺管理——店铺列表
     * zhang
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSpotShopsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotShops> list = sysScenicSpotShopsMapper.getSotShopsList(search);

        if (list.size()>0){
            PageInfo<SysScenicSpotShops> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
            return pageDataResult;
    }

    /**
     * 后台管理——添加店铺
     * @param sysScenicSpotShops
     * @return
     */
    @Override
    public int addSpotShops( SysScenicSpotShops sysScenicSpotShops) {
       sysScenicSpotShops.setShopsId(IdUtils.getSeqId());
       sysScenicSpotShops.setShopsPinyinName(Tinypinyin.tinypinyin(sysScenicSpotShops.getShopsName()));
       sysScenicSpotShops.setCreateTime(DateUtil.currentDateTime());
       sysScenicSpotShops.setUpdateTime(DateUtil.currentDateTime());
       int i = sysScenicSpotShopsMapper.insertSelective(sysScenicSpotShops);
        return i;
    }

    /**
     * 后台管理——修改店铺
     * @param sysScenicSpotShops
     * @return
     */
    @Override
    public int exitSpotShops(SysScenicSpotShops sysScenicSpotShops) {

        sysScenicSpotShops.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotShopsMapper.updateByPrimaryKeySelective(sysScenicSpotShops);
        return i;
    }

    /**
     * 根据id删除店铺
     * @param shopsId
     * @return
     */
    @Override
    public int delSpotShops(Long shopsId) {

        int i = sysScenicSpotShopsMapper.deleteByPrimaryKey(shopsId);
        Map<String, Object> search = new HashMap<>();
        search.put("shopsId",shopsId);
        List<SysScenicSpotShopsType> list = sysScenicSpotShopsTypeMapper.selectBySearch(search);
        if (list.size()>0 && !StringUtils.isEmpty(list)){
            for (SysScenicSpotShopsType sysScenicSpotShopsType : list) {
                int j = sysScenicSpotShopsTypeMapper.deleteByPrimaryKey(sysScenicSpotShopsType.getTypeId());

                int k = sysScenicSpotShopsDetailsMapper.deleteByPrimaryTypeId(sysScenicSpotShopsType.getTypeId());
            }
        }
        return i;
    }

    /**
     * 后台店铺管理——店铺上传图片
     * 张
     * @param file
     * @param shopsId
     * @return
     */
    @Override
    public int addSpotShopsPicture(MultipartFile file, Long shopsId) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = spotShopListPatheGetPicPaht + filename;// 存放位置
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
                      String upload = fileUploadUtil.upload(file, spotShopListPatheGetPicPaht.substring(1) + filename);
                      System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                SysScenicSpotShops sysScenicSpotShops = sysScenicSpotShopsMapper.selectByPrimaryKey(shopsId);
                if (!StringUtils.isEmpty(sysScenicSpotShops)){
                    sysScenicSpotShops.setShopsPic(spotShopListPatheGetPicUrl + filename);
                    int i = sysScenicSpotShopsMapper.updateByPrimaryKeySelective(sysScenicSpotShops);
                    return i;
                }else{
                    return 0;
                }
            }else{
                return 2;
            }
        }else{
            return 3;
        }
    }

    /**
     * 后台管理——添加商品店铺详情
     * @param sysScenicSpotShopsDetails
     * @return
     */
    @Override
    public int addSpotShopsDetails(SysScenicSpotShopsDetails sysScenicSpotShopsDetails) {

        String typeName = sysScenicSpotShopsDetails.getTypeName();
        Long typeId = sysScenicSpotShopsDetails.getTypeId();
        String shopsId = sysScenicSpotShopsDetails.getShopsId();
        Map<String, Object> search = new HashMap<>();

        //查询商品类别
        SysScenicSpotShopsCommodityType shopsCommodityTypeList = sysScenicSpotShopsCommodityTypeMapper.selectByPrimaryKey(typeId);

        //查询店铺是否有这个商品类别
        search.put("shopsId",shopsId);
        search.put("commodityId",typeId);
        List<SysScenicSpotShopsType> list = sysScenicSpotShopsTypeMapper.selectBySearch(search);
        if (list.size()<=0){
            //添加商品类别与商铺的中间表
            SysScenicSpotShopsType sysScenicSpotShopsTypeNew = new SysScenicSpotShopsType();
            sysScenicSpotShopsTypeNew.setTypeId(IdUtils.getSeqId());
            sysScenicSpotShopsTypeNew.setCommodityId(shopsCommodityTypeList.getTypeId());
            sysScenicSpotShopsTypeNew.setShopsId(Long.parseLong(shopsId));
            sysScenicSpotShopsTypeNew.setTypeName(shopsCommodityTypeList.getTypeName());
            sysScenicSpotShopsTypeNew.setCreateTime(DateUtil.currentDateTime());
            sysScenicSpotShopsTypeNew.setUpdateTime(DateUtil.currentDateTime());
            int i = sysScenicSpotShopsTypeMapper.insertSelective(sysScenicSpotShopsTypeNew);
            //添加商品详情
            sysScenicSpotShopsDetails.setDetailsId(IdUtils.getSeqId());
            sysScenicSpotShopsDetails.setTypeId(sysScenicSpotShopsTypeNew.getTypeId());
            sysScenicSpotShopsDetails.setCreateTime(DateUtil.currentDateTime());
            sysScenicSpotShopsDetails.setUpdateTime(DateUtil.currentDateTime());
            int i1 = sysScenicSpotShopsDetailsMapper.insertSelective(sysScenicSpotShopsDetails);
            return i1;
        }else{
            //添加商品详情
            sysScenicSpotShopsDetails.setDetailsId(IdUtils.getSeqId());
            sysScenicSpotShopsDetails.setTypeId(list.get(0).getTypeId());
            sysScenicSpotShopsDetails.setCreateTime(DateUtil.currentDateTime());
            sysScenicSpotShopsDetails.setUpdateTime(DateUtil.currentDateTime());
            int i1 = sysScenicSpotShopsDetailsMapper.insertSelective(sysScenicSpotShopsDetails);
            return i1;
        }

    }

    /**
     * 后台管理——添加商品店铺详情
     * @param file
     * @param detailsId
     * @return
     */
    @Override
    public int addSpotShopsDetailsPicture(MultipartFile file, Long detailsId) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = spotShopDetailsPatheGetPicPaht + filename;// 存放位置
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
                      String upload = fileUploadUtil.upload(file, spotShopDetailsPatheGetPicPaht.substring(1) + filename);
                      System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录

                SysScenicSpotShopsDetails sysScenicSpotShopsDetails = sysScenicSpotShopsDetailsMapper.selectByPrimaryKey(detailsId);
                    sysScenicSpotShopsDetails.setProductPicUrl(spotShopDetailsPatheGetPicUrl + filename);
                    sysScenicSpotShopsDetails.setUpdateTime(DateUtil.currentDateTime());
                    int i = sysScenicSpotShopsDetailsMapper.updateByPrimaryKeySelective(sysScenicSpotShopsDetails);
                    return i;
            } else {
                return 2;
            }
        } else {
            return 3;
        }

    }

    /**
     * 后台管理——查询店铺详情
     * @param
     * @return
     */
    @Override
    public PageDataResult getSpotShopsDetails(Integer pageNum,Integer pageSize, Map<String, Object>  search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotShopsDetails> list =  sysScenicSpotShopsDetailsMapper.getSpotShopsDetails(search);
        if (list.size()>0){
           PageInfo<SysScenicSpotShopsDetails> pageInfo = new PageInfo<>(list);
           pageDataResult.setData(pageInfo);
           pageDataResult.setCode(200);
           pageDataResult.setTotals((int)pageInfo.getTotal());
       }

       return pageDataResult;
    }

    /**
     * 景区中全部店铺详情
     * @param spotId
     * @return
     */
    @Override
    public List<SysScenicSpotShops> getScenicShopsList(String spotId) {

        List<SysScenicSpotShops> list = sysScenicSpotShopsMapper.getScenicShopsList(spotId);

        return list;
    }

    /**
     * 后台管理——修改商品详情
     * @param sysScenicSpotShopsDetails
     * @return
     */
    @Override
    public int editSpotShopsDetails(SysScenicSpotShopsDetails sysScenicSpotShopsDetails) {
        String typeName = sysScenicSpotShopsDetails.getTypeName();
        String shopsId = sysScenicSpotShopsDetails.getShopsId();
        Map<String, Object> search = new HashMap<>();
        search.put("typeName",typeName);
//        search.put("")
        search.put("shopsId",shopsId);
        List<SysScenicSpotShopsType> sysScenicSpotShopsType = sysScenicSpotShopsTypeMapper.selectBySearch(search);
        if (sysScenicSpotShopsType.size() == 0){
            //添加商品类别
            SysScenicSpotShopsType sysScenicSpotShopsTypeNew = new SysScenicSpotShopsType();
            sysScenicSpotShopsTypeNew.setTypeId(IdUtils.getSeqId());
            sysScenicSpotShopsTypeNew.setShopsId(Long.parseLong(shopsId));
            sysScenicSpotShopsTypeNew.setTypeName(typeName);
            sysScenicSpotShopsTypeNew.setCreateTime(DateUtil.currentDateTime());
            sysScenicSpotShopsTypeNew.setUpdateTime(DateUtil.currentDateTime());
            sysScenicSpotShopsTypeNew.setCommodityId(sysScenicSpotShopsDetails.getTypeId());
            int i = sysScenicSpotShopsTypeMapper.insertSelective(sysScenicSpotShopsTypeNew);
            //修改商品详情
            sysScenicSpotShopsDetails.setTypeId(sysScenicSpotShopsTypeNew.getTypeId());
            sysScenicSpotShopsDetails.setCreateTime(DateUtil.currentDateTime());
            sysScenicSpotShopsDetails.setUpdateTime(DateUtil.currentDateTime());
            int i1 = sysScenicSpotShopsDetailsMapper.updateByPrimaryKeySelective(sysScenicSpotShopsDetails);
            return i1;
        }else{

            SysScenicSpotShopsType sysScenicSpotShopsTypeN = sysScenicSpotShopsType.get(0);
            Long detailsId = sysScenicSpotShopsDetails.getDetailsId();
            SysScenicSpotShopsDetails sysScenicSpotShopsDetails1 = sysScenicSpotShopsDetailsMapper.selectByPrimaryKey(detailsId);
            sysScenicSpotShopsDetails1.setTypeId(sysScenicSpotShopsTypeN.getTypeId());
            sysScenicSpotShopsDetails1.setState(sysScenicSpotShopsDetails.getState());
            sysScenicSpotShopsDetails1.setProductName(sysScenicSpotShopsDetails.getProductName());
            sysScenicSpotShopsDetails1.setUpdateTime(DateUtil.currentDateTime());
            int i = sysScenicSpotShopsDetailsMapper.updateByPrimaryKeySelective(sysScenicSpotShopsDetails1);
            return i;
        }
    }

    /**
     * 后台管理——店铺的启用禁用
     * @param
     * @return
     */
    @Override
    public int editSpotShopsState(Long shopsId, String state) {
        SysScenicSpotShops sysScenicSpotShops = sysScenicSpotShopsMapper.selectByPrimaryKey(shopsId);
        sysScenicSpotShops.setShopsState(state);
        sysScenicSpotShops.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotShopsMapper.updateByPrimaryKeySelective(sysScenicSpotShops);
        return i;
    }
    /**
     * 后台管理——店铺详情删除
     * @param detailsId
     * @return
     */
    @Override
    public int delSpotShopsDetails(Long detailsId) {

        int i = sysScenicSpotShopsDetailsMapper.deleteByPrimaryKey(detailsId);
        return i ;
    }

    /**
     * 后台管理——店铺商品上下架
     * @param detailsId
     * @param state
     * @return
     */
    @Override
    public int editSpotShopsDetailsState(Long detailsId, String state) {

        SysScenicSpotShopsDetails sysScenicSpotShopsDetails = sysScenicSpotShopsDetailsMapper.selectByPrimaryKey(detailsId);
        sysScenicSpotShopsDetails.setState(state);
        sysScenicSpotShopsDetails.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotShopsDetailsMapper.updateByPrimaryKeySelective(sysScenicSpotShopsDetails);
        return i;
    }

    /**
     * 后台管理--店铺导出
     * @param search
     * @return
     */
    @Override
    public List<SysScenicSpotShopsExcel> uploadExcelShop(Map<String, Object> search) {

        List<SysScenicSpotShopsExcel> list = sysScenicSpotShopsMapper.uploadExcelShop(search);
        return list;
    }

    /**
     * 导入添加店铺
     * @param sysScenicSpotServiceResExcel
     * @return
     */
    @Override
    public int addImportShops(SysScenicSpotShopsExcel sysScenicSpotServiceResExcel) {

        sysScenicSpotServiceResExcel.setShopsId(IdUtils.getSeqId());
        sysScenicSpotServiceResExcel.setCreateTime(DateUtil.currentDateTime());
        sysScenicSpotServiceResExcel.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotShopsMapper.addImportShops(sysScenicSpotServiceResExcel);
        return i;
    }
}
