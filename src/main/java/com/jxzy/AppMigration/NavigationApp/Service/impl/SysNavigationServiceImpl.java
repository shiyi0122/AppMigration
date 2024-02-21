package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysNavigationService;
import com.jxzy.AppMigration.NavigationApp.dao.SysNavigationMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;
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

/**
 * @Author zhang
 * @Date 2023/1/9 15:42
 * 首页导航
 */
@Service
public class SysNavigationServiceImpl implements SysNavigationService {

    @Autowired
    SysNavigationMapper sysNavigationMapper;

    @Value("${navigationPatheGetPicUrl}")
    private String sysNavigationPatheGetPicUrl;
    @Value("${navigationPatheGetPicPaht}")
    private String sysNavigationPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;



    /**
     * 添加首页导航
     * @param file
     * @param sysNavigation
     * @return
     */
    @Override
    public int addSysNavigation(MultipartFile file, SysNavigation sysNavigation) {

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

                sysNavigation.setId(IdUtils.getSeqId());
                sysNavigation.setNavigationPicUrl(sysNavigationPatheGetPicUrl+filename);
                sysNavigation.setCreateTime(DateUtil.currentDateTime());
                sysNavigation.setUpdateTime(DateUtil.currentDateTime());
                int i = sysNavigationMapper.insertSelective(sysNavigation);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }
    }

    /**
     * 编辑首页导航
     * @param file
     * @param sysNavigation
     * @return
     */
    @Override
    public int editSysNavigation(MultipartFile file, SysNavigation sysNavigation) {
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
                sysNavigation.setNavigationPicUrl(sysNavigationPatheGetPicUrl+filename);
                sysNavigation.setUpdateTime(DateUtil.currentDateTime());
                int i = sysNavigationMapper.updateSelective(sysNavigation);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int delSysNavigation(Long id) {

       int i =  sysNavigationMapper.deleteByPrimaryKey(id);
        return i;
    }

    /**
     * 列表查询
     * @param pageNum
     * @param pageSize
     * @param content
     * @return
     */
    @Override
    public PageDataResult getSysNavigationList(Integer pageNum, Integer pageSize, String content) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysNavigation> list = sysNavigationMapper.list(content);

        if (list.size()>0){
            PageInfo<SysNavigation> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }
}
