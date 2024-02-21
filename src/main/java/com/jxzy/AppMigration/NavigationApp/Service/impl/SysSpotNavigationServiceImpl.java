package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysSpotNavigationService;
import com.jxzy.AppMigration.NavigationApp.dao.SysSpotNavigationMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotNavigation;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/13 9:46
 */
@Service
public class SysSpotNavigationServiceImpl implements SysSpotNavigationService {

    @Autowired
    SysSpotNavigationMapper sysSpotNavigationMapper;

    @Value("${navigationPatheGetPicUrl}")
    private String sysNavigationPatheGetPicUrl;
    @Value("${navigationPatheGetPicPaht}")
    private String sysNavigationPatheGetPicPaht;
    @Autowired
    FileUploadUtil fileUploadUtil;


    @Override
    public int addSysSpotNavigation(MultipartFile file, SysSpotNavigation sysSpotNavigation) {
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = sysNavigationPatheGetPicPaht + filename;// 存放位置
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
                    String upload = fileUploadUtil.upload(file, sysNavigationPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录

                sysSpotNavigation.setId(IdUtils.getSeqId());
                sysSpotNavigation.setSpotNavigationPicUrl(sysNavigationPatheGetPicUrl+filename);
                sysSpotNavigation.setCreateTime(DateUtil.currentDateTime());
                sysSpotNavigation.setUpdateTime(DateUtil.currentDateTime());
                int i = sysSpotNavigationMapper.insertSelective(sysSpotNavigation);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }


    }

    @Override
    public int editSysSpotNavigation(MultipartFile file, SysSpotNavigation sysSpotNavigation) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = sysNavigationPatheGetPicPaht + filename;// 存放位置
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
                    String upload = fileUploadUtil.upload(file, sysNavigationPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysSpotNavigation.setSpotNavigationPicUrl(sysNavigationPatheGetPicUrl+filename);
                sysSpotNavigation.setUpdateTime(DateUtil.currentDateTime());
                int i = sysSpotNavigationMapper.updateSelective(sysSpotNavigation);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }


    }

    @Override
    public int delSysSpotNavigation(Long id) {

       int i = sysSpotNavigationMapper.deleteByPrimaryKey(id);
       return i;
    }

    @Override
    public PageDataResult getSysSpotNavigationList(Integer pageNum, Integer pageSize, String content) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysNavigation> list = sysSpotNavigationMapper.list(content);

        if (list.size()>0){
            PageInfo<SysNavigation> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }


}
