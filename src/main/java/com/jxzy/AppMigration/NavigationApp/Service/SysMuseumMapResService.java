package com.jxzy.AppMigration.NavigationApp.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumMapRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SysMuseumMapResService extends IService<SysMuseumMapRes> {
    /**
     * @param resPic         地图图片(png格式)
     * @param resMarkPic     标注地图(png格式)
     * @param resMarkPicFile 标注地图文件(dat格式)
     * @return -2：不支持的文件类型，-4,：不支持的文件大小
     */
    int save(MultipartFile resPic, MultipartFile resMarkPic, MultipartFile resMarkPicFile, SysMuseumMapRes museumMapRes);

    /**
     * @param resPic         地图图片(png格式)
     * @param resMarkPic     标注地图(png格式)
     * @param resMarkPicFile 标注地图文件(dat格式)
     * @return -2：不支持的文件类型，-4,：不支持的文件大小
     */
    int edit(MultipartFile resPic, MultipartFile resMarkPic, MultipartFile resMarkPicFile, SysMuseumMapRes museumMapRes);


    IPage<SysMuseumMapRes> page(Page<SysMuseumMapRes> page, Wrapper<SysMuseumMapRes> wrapper, SysMuseumMapRes sysMuseumMapRes);

    /**
     * 根据博物馆名搜索博物馆地图
     *
     * @param museumName
     * @return
     */
    List<SysMuseumMapRes> getMuseumMapResByCondition(String museumName);

}

