package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumMapResService;
import com.jxzy.AppMigration.NavigationApp.dao.SysMuseumMapResMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysMuseumMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumMapRes;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.PictureType;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 地图资源维护 服务实现类
 * </p>
 *
 * @author libofan
 * @since 2023-10-29
 */
@Service
public class SysMuseumMapResServiceImpl extends ServiceImpl<SysMuseumMapResMapper, SysMuseumMapRes> implements SysMuseumMapResService {


    @Value("${sysMuseumMapListPatheGetPicUrl}")
    private String sysMuseumMapListPatheGetPicUrl;
    @Value("${sysMuseumspotMapListPatheGetPicPaht}")
    private String sysMuseumspotMapListPatheGetPicPaht;
    @Autowired
    private SysMuseumMapResMapper sysMuseumMapResMapper;
    @Autowired
    private SysMuseumMapper sysMuseumMapper;

    @Override
    public int save(MultipartFile resPic, MultipartFile resMarkPic, MultipartFile resMarkPicFile, SysMuseumMapRes museumMapRes) {
        //封装地图图片
        if (resPic != null && !resPic.isEmpty()) {
            String filename = UUID.randomUUID().toString().replace("-", "") + "." + FileNameUtil.getSuffix(resPic.getOriginalFilename());
            int res = judgeFileRes(resPic, filename);
            if (res == -4 || res == -2) {
                return res;
            }
            museumMapRes.setResUrl(sysMuseumMapListPatheGetPicUrl + filename);
            museumMapRes.setResPic(sysMuseumMapListPatheGetPicUrl + filename);
        }
        //封装标注地图
        if (resMarkPic != null && !resMarkPic.isEmpty()) {
            String filename = UUID.randomUUID().toString().replace("-", "") + "." + FileNameUtil.getSuffix(resMarkPic.getOriginalFilename());
            int res1 = judgeFileRes(resMarkPic, filename);
            if (res1 == -4 || res1 == -2) {
                return res1;
            }
            museumMapRes.setResMarkPic(sysMuseumMapListPatheGetPicUrl + filename);
        }
        if (resMarkPicFile != null && !resMarkPicFile.isEmpty()) {
            //封装标注地图文件
            String filename = UUID.randomUUID().toString().replace("-", "") + "." + FileNameUtil.getSuffix(resMarkPicFile.getOriginalFilename());
            int res2 = judgeFileRes(resMarkPicFile, filename);
            if (res2 == -4 || res2 == -2) {
                return res2;
            }
            museumMapRes.setResMarkPicFile(sysMuseumMapListPatheGetPicUrl + filename);
        }
        museumMapRes.setResId(IdUtils.getSeqId());
        museumMapRes.setCreateDate(DateUtil.currentDateTime());
        return sysMuseumMapResMapper.insert(museumMapRes);
    }


    @Override
    public int edit(MultipartFile resPic, MultipartFile resMarkPic, MultipartFile resMarkPicFile, SysMuseumMapRes museumMapRes) {
        //封装地图图片
        if (resPic != null && !resPic.isEmpty()) {
            String filename = UUID.randomUUID().toString().replace("-", "") + "." + FileNameUtil.getSuffix(resPic.getOriginalFilename());
            int res = judgeFileRes(resPic, filename);
            if (res == -4 || res == -2) {
                return res;
            }
            museumMapRes.setResUrl(sysMuseumMapListPatheGetPicUrl + filename);
            museumMapRes.setResPic(sysMuseumMapListPatheGetPicUrl + filename);
        }
        //封装标注地图
        if (resMarkPic != null && !resMarkPic.isEmpty()) {
            String filename = UUID.randomUUID().toString().replace("-", "") + "." + FileNameUtil.getSuffix(resMarkPic.getOriginalFilename());
            int res1 = judgeFileRes(resMarkPic, filename);
            if (res1 == -4 || res1 == -2) {
                return res1;
            }
            museumMapRes.setResMarkPic(sysMuseumMapListPatheGetPicUrl + filename);
        }
        if (resMarkPicFile != null && !resMarkPicFile.isEmpty()) {
            //封装标注地图文件
            String filename = UUID.randomUUID().toString().replace("-", "") + "." + FileNameUtil.getSuffix(resMarkPicFile.getOriginalFilename());
            int res2 = judgeFileRes(resMarkPicFile, filename);
            if (res2 == -4 || res2 == -2) {
                return res2;
            }
            museumMapRes.setResMarkPicFile(sysMuseumMapListPatheGetPicUrl + filename);
        }
        museumMapRes.setUpdateDate(DateUtil.currentDateTime());
        return sysMuseumMapResMapper.updateById(museumMapRes);
    }

    @Override
    public IPage<SysMuseumMapRes> page(Page<SysMuseumMapRes> page, Wrapper<SysMuseumMapRes> wrapper, SysMuseumMapRes sysMuseumMapRes) {
        LambdaQueryWrapper<SysMuseumMapRes> smmrlqw = (LambdaQueryWrapper<SysMuseumMapRes>) wrapper;
        Long museumId = sysMuseumMapRes.getResMuseumId();
        smmrlqw.eq(!StringUtils.isEmpty(museumId), SysMuseumMapRes::getResMuseumId, museumId);
        sysMuseumMapResMapper.selectPage(page, smmrlqw);
        List<SysMuseumMapRes> museumMapResList = page.getRecords();
        Long resMuseumId;
        SysMuseum sysMuseum;
        for (SysMuseumMapRes museumMapRes : museumMapResList) {
            resMuseumId = museumMapRes.getResMuseumId();
            if (ObjUtil.isNotEmpty(resMuseumId)) {
                sysMuseum = sysMuseumMapper.selectById(resMuseumId);
                if (ObjUtil.isNotEmpty(sysMuseum)) {
                    museumMapRes.setMuseumName(sysMuseum.getMuseumName());
                }
            }
        }
        return page;
    }

    @Override
    public List<SysMuseumMapRes> getMuseumMapResByCondition(String museumName) {
        return sysMuseumMapResMapper.getMuseumMapResByCondition(museumName);
    }

    @Autowired
    private FileUploadUtil fileUploadUtil;
    /**
     * 文件不允许为空。不支持的的文件大小：返回-4:，不支持的文件类型：返回-2：。否则保存文件，封装文件路径，并返回0。
     *
     * @param file
     * @param
     * @return
     */
    private int judgeFileRes(MultipartFile file, String filename) {
        long size = file.getSize();
        if (size > 1024 * 1024 * 100) {
            return Constant.UN_SUPPORT_PIC_SIZE;
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = FileNameUtil.getSuffix(originalFilename);
        if (!PictureType.generalPictureType.contains(suffix)) {
            return Constant.UN_SUPPORT_PICTURE_TYPE;
        }
        String upload = fileUploadUtil.upload(file, sysMuseumspotMapListPatheGetPicPaht.substring(1) + filename);
        System.out.println("upload = " + upload);
        return 0;
    }
}
