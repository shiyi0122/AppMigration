package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysAppVersionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysAppVersionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysAppVersion;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/20 17:35
 */
@Service
public class SysAppVersionServiceImpl implements SysAppVersionService {

    @Autowired
    SysAppVersionMapper sysAppVersionMapper;

    //app版本文件上传
    @Value("${sysAppVersionPatheGetPicUrl}")
    private String sysAppVersionPatheGetPicUrl;
    @Value("${sysAppVersionPatheGetPicPaht}")
    private String sysAppVersionPatheGetPicPaht;


    @Value("${sysAppVersionPatheGetNewPicUrl}")
    private String sysAppVersionPatheGetNewPicUrl;
    @Value("${sysAppVersionPatheGetNewPicPaht}")
    private String sysAppVersionPatheGetNewPicPaht;

    //添加包
    @Override
    public int addAdminSysAppVersion(SysAppVersion sysAppVersion) {

        if (sysAppVersion.getSpotType().equals(1)) {
            sysAppVersion.setAppUrl("/pro/static/sysAppVersion/YYG.apk");
        } else if (sysAppVersion.getSpotType().equals(2)) {
            sysAppVersion.setAppUrl("/pro/static/sysAppVersion/ASG.apk");
        }

        sysAppVersion.setId(IdUtils.getSeqId());

        sysAppVersion.setCreateDate(DateUtil.currentDateTime());
        sysAppVersion.setUpdateDate(DateUtil.currentDateTime());

        int i = sysAppVersionMapper.insertSelective(sysAppVersion);

        return i;
    }

    //查询最新版本包
    @Override
    public SysAppVersion getAppNumberNew(String packageType) {

        SysAppVersion sysAppVersion = sysAppVersionMapper.getAppNumberNew(packageType);

        return sysAppVersion;
    }

    //编辑版本包
    @Override
    public int editAdminSysAppVersion(SysAppVersion sysAppVersion) {

        int i = sysAppVersionMapper.updateSelective(sysAppVersion);

        return i;
    }

    //删除版本包
    @Override
    public int delAdminSysAppVersion(Long id) {

        int i = sysAppVersionMapper.deleteByPrimaryKey(id);
        return i;
    }

    //后台列表查询
    @Override
    public PageDataResult getAdminSysAppVersionList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum, pageSize);
        List<SysAppVersion> list = sysAppVersionMapper.getAdminSysAppVersionList(search);

        if (list.size() > 0) {
            PageInfo<SysAppVersion> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    //app获取最新版本号
    @Override
    public SysAppVersion getSysAppVersionUpToDate() {

        SysAppVersion sysAppVersion = sysAppVersionMapper.getSysAppVersionUpToDate();

        return sysAppVersion;
    }

    //修改apk包启用禁用
    @Override
    public int editAdminSysAppVersionEnableDisable(Long id, String appType) {

        SysAppVersion sysAppVersion = sysAppVersionMapper.selectById(id);

        if (!StringUtils.isEmpty(sysAppVersion)) {
            sysAppVersion.setAppType(appType);
            int i = sysAppVersionMapper.updateSelective(sysAppVersion);
            return i;
        } else {
            return 0;
        }

    }

    /**
     * 奥森go和游娱go版本分开
     *
     * @param spotType
     * @return
     */
    @Override
    public SysAppVersion getSysAppVersionUpToDateNew(String spotType) {

        SysAppVersion sysAppVersion = sysAppVersionMapper.getSysAppVersionUpToDateNew(spotType);

        return sysAppVersion;
    }


    @Autowired
    FileUploadUtil fileUploadUtil;

    /**
     * 管理后台app版本添加(新)
     *
     * @param file
     * @param sysAppVersion
     * @return
     */
    @Override
    public int addAdminSysAppVersionNew(MultipartFile file, SysAppVersion sysAppVersion) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".apk"));// 取文件格式后缀名
        if (type.equals(".apk")) {
            String filename = "";
            if (sysAppVersion.getAppType().equals("1")) {
                filename = "YYG" + type;// 文件名
            } else if (sysAppVersion.getAppType().equals("2")) {
                filename = "ASG" + type;// 文件名
            }

            String path = sysAppVersionPatheGetNewPicUrl + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                FileWriter fileWriter = new FileWriter(destFile, false);
                try {
                    // 处理文件写入操作
                    String upload = fileUploadUtil.upload(file, sysAppVersionPatheGetNewPicPaht.substring(1) + filename);
                    System.out.println("下载地址：" + upload);
                } finally {
                    fileWriter.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录

            sysAppVersion.setAppSize(String.valueOf(Double.valueOf(file.getSize() / 1024)));
            sysAppVersion.setId(IdUtils.getSeqId());
            sysAppVersion.setAppUrl(sysAppVersionPatheGetNewPicUrl + filename);
            sysAppVersion.setCreateDate(DateUtil.currentDateTime());
            sysAppVersion.setUpdateDate(DateUtil.currentDateTime());

            int i = sysAppVersionMapper.insertSelective(sysAppVersion);

            return i;

        } else {
            return 2;
        }
    }


}
