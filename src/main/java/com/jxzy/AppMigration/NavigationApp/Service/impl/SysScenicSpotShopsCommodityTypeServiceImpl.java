package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotShopsCommodityTypeService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotShopsCommodityTypeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShops;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsCommodityType;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/6 17:49
 */
@Service
public class SysScenicSpotShopsCommodityTypeServiceImpl implements SysScenicSpotShopsCommodityTypeService {

    @Autowired
    SysScenicSpotShopsCommodityTypeMapper sysScenicSpotShopsCommodityTypeMapper;

    @Value("${spotShopDetailsPatheGetPicUrl}")
    private String spotShopDetailsPatheGetPicUrl;
    @Value("${spotShopdetailsPatheGetPicPaht}")
    private String spotShopDetailsPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;

    /**
     * 后台管理——查询商品类型
     * @param pageNum
     * @param pageSize
     * @param content
     * @return
     */
    @Override
    public PageDataResult getShopsCommodityTypeList(Integer pageNum, Integer pageSize, String content) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();

        PageHelper.startPage(pageNum,pageSize);
        search.put("shopsTypeName",content);
        List<SysScenicSpotShopsCommodityType> list = sysScenicSpotShopsCommodityTypeMapper.getShopsCommodityTypeList(search);
        if (list.size()>0){
            PageInfo<SysScenicSpotShopsCommodityType> pageInfo = new PageInfo<>(list);
            pageDataResult.setCode(200);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;

    }
    /**
     * 后台管理——添加商品类型
     * @param
     * @param
     * @param
     * @return
     */
    @Override
    public int addShopsCommodityType(MultipartFile file, SysScenicSpotShopsCommodityType sysScenicSpotShopsCommodityType) {

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

                sysScenicSpotShopsCommodityType.setTypeId(IdUtils.getSeqId());
                sysScenicSpotShopsCommodityType.setTypeUrl(spotShopDetailsPatheGetPicUrl + filename);
                sysScenicSpotShopsCommodityType.setCreateTime(DateUtil.currentDateTime());
                sysScenicSpotShopsCommodityType.setUpdateTime(DateUtil.currentDateTime());
                int i = sysScenicSpotShopsCommodityTypeMapper.insertSelective(sysScenicSpotShopsCommodityType);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }

    }

    /**
     * 后台管理——修改商品类型
     * @param file
     * @param sysScenicSpotShopsCommodityType
     * @return
     */
    @Override
    public int editShopsCommodityType(MultipartFile file, SysScenicSpotShopsCommodityType sysScenicSpotShopsCommodityType) {
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


                sysScenicSpotShopsCommodityType.setTypeUrl(spotShopDetailsPatheGetPicUrl + filename);
                sysScenicSpotShopsCommodityType.setUpdateTime(DateUtil.currentDateTime());
                int i = sysScenicSpotShopsCommodityTypeMapper.updateSelective(sysScenicSpotShopsCommodityType);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }
    }

    @Override
    public int delShopsCommodityType(Long typeId) {

       int i = sysScenicSpotShopsCommodityTypeMapper.deleteByPrimaryKey(typeId);
       return i;

    }

    /**
     * 后台管理——商品下拉选
     * @return
     */
    @Override
    public List<SysScenicSpotShopsCommodityType> shopsCommodityTypeDrop() {

       List<SysScenicSpotShopsCommodityType> list = sysScenicSpotShopsCommodityTypeMapper.shopsCommodityTypeDrop();

       return list;

    }

}
