package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysHeadMountedService;
import com.jxzy.AppMigration.NavigationApp.dao.SysHeadMountedMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysHeadMounted;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
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
 * @Date 2023/6/25 11:25
 */
@Service
public class SysHeadMountedServiceImpl implements SysHeadMountedService {

    @Autowired
    SysHeadMountedMapper sysHeadMountedMapper;

    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;

    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;


    /**
     * 根据博物馆id，获取设备列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysHeadMountedList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);

        List<SysHeadMounted> list = sysHeadMountedMapper.getSysHeadMountedList(search);

        if (list.size() > 0){
            PageInfo<SysHeadMounted> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;

    }

    /**
     *  修改设备使用状态
     * @param id
     * @param state
     * @return
     */
    @Override
    public int editSysHeadMountedState(Long id, String state) {

        int i = sysHeadMountedMapper.editSysHeadMountedState(id,state);
        return i;
    }

    /**
     * 添加头戴式设备
     * @param file
     * @param sysHeadMounted
     * @return
     */
    @Override
    public int addSysHeadMounted(MultipartFile file, SysHeadMounted sysHeadMounted) {

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

                sysHeadMounted.setId(IdUtils.getSeqId());
                sysHeadMounted.setHeadMountedPic(featuredFoodPatheGetPicUrl+filename);
                sysHeadMounted.setCreateTime(DateUtil.currentDateTime());
                sysHeadMounted.setUpdateTime(DateUtil.currentDateTime());
                int i = sysHeadMountedMapper.insertSelective(sysHeadMounted);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }

    }

    /**
     * 添加头戴式设备(无文件)
     * @param sysHeadMounted
     * @return
     */
    @Override
    public int addSysHeadMountedN(SysHeadMounted sysHeadMounted) {

        sysHeadMounted.setCreateTime(DateUtil.currentDateTime());
        sysHeadMounted.setUpdateTime(DateUtil.currentDateTime());
        int i = sysHeadMountedMapper.insertSelective(sysHeadMounted);
        return i;

    }

    /**
     * 修改头戴式设备
     * @param file
     * @param
     * @return
     */
    @Override
    public int editSysHeadMounted(MultipartFile file, SysHeadMounted sysHeadMounted) {

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
                sysHeadMounted.setHeadMountedPic(featuredFoodPatheGetPicUrl+filename);
                sysHeadMounted.setUpdateTime(DateUtil.currentDateTime());
                int i = sysHeadMountedMapper.updateSelective(sysHeadMounted);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }


    }

    /**
     * 修改头戴式设备(无文件)
     * @param sysHeadMounted
     * @return
     */
    @Override
    public int editSysHeadMountedN(SysHeadMounted sysHeadMounted) {

        sysHeadMounted.setUpdateTime(DateUtil.currentDateTime());
        int i = sysHeadMountedMapper.updateSelective(sysHeadMounted);
        return i;

    }

    /**
     * 删除头戴式设备
     * @param
     * @return
     */
    @Override
    public int delSysHeadMounted(Long id) {

        int i = sysHeadMountedMapper.deleteByPrimaryKey(id);

        return i;
    }

    /**
     * 后台管理-头戴式设备列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult adminSysHeadMountedList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);

        List<SysHeadMounted> sysHeadMountedList = sysHeadMountedMapper.getSysHeadMountedList(search);

        if (sysHeadMountedList.size() > 0){
            PageInfo<SysHeadMounted> pageInfo = new PageInfo<>(sysHeadMountedList);
            pageDataResult.setData(search);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
