package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumBannerService;
import com.jxzy.AppMigration.NavigationApp.dao.SysMuseumBannerMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumBanner;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.PictureType;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/8/8 10:23
 */
@Service
@Slf4j
public class SysMuseumBannerServiceImpl extends ServiceImpl<SysMuseumBannerMapper, SysMuseumBanner> implements SysMuseumBannerService {

    @Value("${bannerPatheGetPicUrl}")
    private String bannerPatheGetPicUrl;
    @Value("${bannerPatheGetPicPaht}")
    private String bannerPatheGetPicPaht;

    @Autowired
    SysMuseumBannerMapper sysMuseumBannerMapper;

    @Autowired
    FileUploadUtil fileUploadUtil;



    //    @Override
//    public List<SysMuseumBanner> getMuseumBannerList(Map<String, Object> search) {
//
//        List<SysMuseumBanner> list = sysMuseumBannerMapper.getMuseumBannerList(search);
//
//        return list;
//
//    }
    @Override
    public List<SysMuseumBanner> getMuseumBannerList(Map<String, Object> search) {

        List<SysMuseumBanner> list = sysMuseumBannerMapper.getMuseumBannerList(search);

        return list;

    }

    @Override
    public int save(MultipartFile photo, SysMuseumBanner museumBanner) {
        int res = 0;
        if (!photo.isEmpty()) {
            //判断文件大小,不能超过3MB
            long size = photo.getSize();
            if (size > 1024 * 1024 * 3) {
                return Constant.UN_SUPPORT_PIC_SIZE;
            }
            String originalFilename = photo.getOriginalFilename();
            String suffix = FileNameUtil.getSuffix(originalFilename);
            //判断文件类型
            if (!PictureType.generalPictureType.contains(suffix)) {
                return Constant.UN_SUPPORT_PICTURE_TYPE;
            }
            //保存图片到本地
            String filename = System.currentTimeMillis() + "." + suffix;
            String fileFinalPath = bannerPatheGetPicPaht + filename;
            String upload = fileUploadUtil.upload(photo, bannerPatheGetPicPaht.substring(1) + fileFinalPath);
            log.info("阿里云图片地址:{}", upload);
            museumBanner.setId(IdUtils.getSeqId());
            museumBanner.setMuseumBannerUrl(bannerPatheGetPicUrl + filename);
            museumBanner.setMuseumBannerType(suffix);
            museumBanner.setMuseumBannerSize(size);
            museumBanner.setCreateTime(DateUtil.currentDateTime());
            res += sysMuseumBannerMapper.insert(museumBanner);
        }

        return res;
    }

    @Override
    public int edit(MultipartFile photo, SysMuseumBanner museumBanner) {
        int res = 0;
        if (!photo.isEmpty()) {
            long size = photo.getSize();
            if (size > 1024 * 1024 * 3) {
                return Constant.UN_SUPPORT_PIC_SIZE;
            }
            String originalFilename = photo.getOriginalFilename();
            String suffix = FileNameUtil.getSuffix(originalFilename);
            //判断文件类型
            if (!PictureType.generalPictureType.contains(suffix)) {
                return Constant.UN_SUPPORT_PICTURE_TYPE;
            }
            //保存图片到本地
            String filename = System.currentTimeMillis() + "." + suffix;
            String fileFinalPath = bannerPatheGetPicPaht + filename;
            String upload = fileUploadUtil.upload(photo, bannerPatheGetPicPaht.substring(1) + fileFinalPath);
            log.info("阿里云图片地址:{}", upload);
            museumBanner.setMuseumBannerUrl(bannerPatheGetPicUrl + filename);
            museumBanner.setMuseumBannerType(suffix);
            museumBanner.setMuseumBannerSize(size);
            museumBanner.setUpdateTime(DateUtil.currentDateTime());
            res += sysMuseumBannerMapper.updateById(museumBanner);
        }

        return res;
    }


    @Override
    public IPage<SysMuseumBanner> page(IPage<SysMuseumBanner> page, LambdaQueryWrapper<SysMuseumBanner> smblqw, SysMuseumBanner sysMuseumBanner) {
        Long museumId = sysMuseumBanner.getMuseumId();
        smblqw.eq(!StringUtils.isEmpty(museumId), SysMuseumBanner::getMuseumId, museumId);
        sysMuseumBannerMapper.selectPage(page, smblqw);
        return page;
    }
}


