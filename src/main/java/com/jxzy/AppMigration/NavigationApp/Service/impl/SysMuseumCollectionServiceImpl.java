package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumCollectionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysMuseumCollectionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollection;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/25 19:24
 */
@Service
@Slf4j
public class SysMuseumCollectionServiceImpl extends ServiceImpl<SysMuseumCollectionMapper, SysMuseumCollection> implements SysMuseumCollectionService {


    @Autowired
    SysMuseumCollectionMapper sysMuseumCollectionMapper;

    @Value("${featuredFoodPatheGetPicUrl}")
    private String featuredFoodPatheGetPicUrl;
    @Value("${featuredFoodPatheGetPicPaht}")
    private String featuredFoodPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Override
    public int save(MultipartFile photo, MultipartFile video, SysMuseumCollection sysMuseumCollection) {
        int res = judgeFile(photo, video, sysMuseumCollection);
        if (res == -3) {
            return Constant.PIC_IS_EMPTY;
        } else if (res == -2) {
            return Constant.UN_SUPPORT_PICTURE_TYPE;
        } else {
            sysMuseumCollection.setId(IdUtils.getSeqId());
            sysMuseumCollection.setCreateTime(DateUtil.currentDateTime());
            return sysMuseumCollectionMapper.insert(sysMuseumCollection);
        }
    }

    @Override
    public boolean save(SysMuseumCollection sysMuseumCollection) {
        sysMuseumCollection.setId(IdUtils.getSeqId());
        sysMuseumCollection.setCreateTime(DateUtil.currentDateTime());
        return SqlHelper.retBool(sysMuseumCollectionMapper.insert(sysMuseumCollection));
    }

    @Override
    public int edit(MultipartFile photo, MultipartFile video, SysMuseumCollection sysMuseumCollection) {
        int res = judgeFile(photo, video, sysMuseumCollection);
        if (res == -2) {
            return Constant.UN_SUPPORT_PICTURE_TYPE;
        } else if (res == -3) {
            return Constant.PIC_IS_EMPTY;
        } else {
            if (sysMuseumCollection.getMuseumCollectionName() != null) {
                sysMuseumCollection.setMuseumCollectionPinyin(Tinypinyin.tinypinyin(sysMuseumCollection.getMuseumCollectionName()));
            }
            sysMuseumCollection.setUpdateTime(DateUtil.currentDateTime());
            return sysMuseumCollectionMapper.updateById(sysMuseumCollection);
        }
    }

    @Override
    public boolean updateById(SysMuseumCollection sysMuseumCollection) {
        if (sysMuseumCollection.getMuseumCollectionName() != null) {
            sysMuseumCollection.setMuseumCollectionPinyin(Tinypinyin.tinypinyin(sysMuseumCollection.getMuseumCollectionName()));
        }
        sysMuseumCollection.setUpdateTime(DateUtil.currentDateTime());
        return super.updateById(sysMuseumCollection);
    }

    @Override
    public PageInfo<SysMuseumCollection> getCollectionByCondition(Map<String, Object> map, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysMuseumCollection> sysMuseumCollections = sysMuseumCollectionMapper.selectCollectionByCondition(map);
        return new PageInfo<>(sysMuseumCollections);
    }

    /**
     * 判断文件类型，如果支持文件类型保存到本地，并上传到阿里云OSS.
     * 否则返回判断结果 -2:不支持的文件类型，-3:文件内容为空
     *
     * @param photo
     * @param sysMuseumCollection
     * @return
     */
    public int judgeFile(MultipartFile photo, MultipartFile video, SysMuseumCollection sysMuseumCollection) {
        if (!photo.isEmpty()) {
            String originalFilename = photo.getOriginalFilename();
            assert originalFilename != null;
            String suffix = FileNameUtil.getSuffix(originalFilename);

            //音频文件
            String videoOriginalFilename = video.getOriginalFilename();
            String videoSuffix = FileNameUtil.getSuffix(videoOriginalFilename);
            String videoFilename = System.currentTimeMillis() + "." + videoSuffix;
            if (PictureType.generalPictureType.contains(suffix)) {
                String filename = System.currentTimeMillis() + "." + suffix;
//                String path = featuredFoodPatheGetPicPaht + filename;
//                    FileOutputStream fileOutputStream = new FileOutputStream(path);
//                    //拷贝后不关闭流(图片拷贝）
//                    InputStream inputStream = photo.getInputStream();
//                    IoUtil.copy(inputStream, fileOutputStream);
//                    inputStream.close();
//                    fileOutputStream.close();
//                    //音频拷贝
//                    InputStream videoInputStream = video.getInputStream();
//                    FileOutputStream vos = new FileOutputStream(featuredFoodPatheGetPicPaht + videoFilename);
//                    IoUtil.copy(videoInputStream, vos);
//                    videoInputStream.close();
//                    vos.close();
                //存储到阿里云OSS
                String upload = fileUploadUtil.upload(photo, featuredFoodPatheGetPicPaht.substring(1) + filename);
                log.info("阿里云图片地址:{}", upload);
                sysMuseumCollection.setMuseumCollectionCoverPic(featuredFoodPatheGetPicUrl + filename);
                sysMuseumCollection.setMuseumCollectionPictures(featuredFoodPatheGetPicUrl + filename);
                sysMuseumCollection.setMuseumCollectionVideo(featuredFoodPatheGetPicUrl + videoFilename);
                return Constant.SAVE_TO_OSS_SUCCESSFUL;
            }
            return Constant.UN_SUPPORT_PICTURE_TYPE;
        }
        return Constant.PIC_IS_EMPTY;
    }
}
