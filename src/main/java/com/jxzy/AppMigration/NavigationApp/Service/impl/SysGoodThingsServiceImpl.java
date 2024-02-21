package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysGoodThingsService;
import com.jxzy.AppMigration.NavigationApp.Service.SysGoodThingsShopService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGoodThingsFabulousMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysGoodThingsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysGoodThingsShopMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFood;
import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFoodShop;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThings;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 19:05
 */
@Service
public class SysGoodThingsServiceImpl  implements SysGoodThingsService {

    @Autowired
    SysGoodThingsMapper sysGoodThingsMapper;

    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;
    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    SysGoodThingsShopMapper sysGoodThingsShopMapper;

    @Autowired
    SysGoodThingsFabulousMapper sysGoodThingsFabulousMapper;



    @Override
    public int addSysGoodThings(MultipartFile file, SysGoodThingsShop sysGoodThingsShop) {

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
                sysGoodThingsShop.setCreateTime(DateUtil.currentDateTime());
                sysGoodThingsShop.setUpdateTime(DateUtil.currentDateTime());
                int i = sysGoodThingsMapper.insertSelective(sysGoodThingsShop);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }


    }

    /**
     * 修改好物店铺商品
     * @param file
     * @param sysGoodThingsShop
     * @return
     */
    @Override
    public int editSysGoodThings(MultipartFile file, SysGoodThingsShop sysGoodThingsShop) {

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
                int i = sysGoodThingsMapper.updateSelective(sysGoodThingsShop);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }

    }

    /**
     * 删除好物店铺商品
     * @param id
     * @return
     */
    @Override
    public int delSysGoodThings(Long id) {

       int i =  sysGoodThingsMapper.deleteByPrimaryKey(id);
       return i;
    }

    /**
     * 查询好物店铺商品列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGoodThingsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysGoodThings> list = sysGoodThingsMapper.list(search);

        if (list.size()>0){
            PageInfo<SysGoodThings> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 好物店铺详情
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGoodThingsAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();


        SysGoodThingsShop sysGoodThingsShop = sysGoodThingsShopMapper.selectById((String)search.get("shopsId"));

        List<SysGoodThings> list = sysGoodThingsMapper.list(search);

        for (SysGoodThings sysGoodThings : list) {
            Integer i = sysGoodThingsFabulousMapper.getUidAndShopIdAndTypeByFabulous((String) search.get("uid"), 2, sysGoodThings.getId());
            sysGoodThings.setIsFabulous(i.toString());
        }
        sysGoodThingsShop.setList(list);
        if (list.size()>0){
            PageInfo<SysGoodThings> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(sysGoodThingsShop);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }
}
