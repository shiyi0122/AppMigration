package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBannerService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBannerMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBanner;
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
 * @Date 2022/7/30 13:48
 */
@Service
public class SysScenicSpotBannerServiceImpl implements SysScenicSpotBannerService {

    @Autowired
    SysScenicSpotBannerMapper sysScenicSpotBannerMapper;

    @Value("${bannerPatheGetPicPaht}")
    private String GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH;

    @Value("${bannerPatheGetPicUrl}")
    private String GET_SYS_SCENIC_SPOT_BANNER_IMG_URL;

    @Override
    public List getScenicSpotBanner(Map<String, Object> search) {

        List<SysScenicSpotBanner> list = sysScenicSpotBannerMapper.getScenicSpotBanner(search);

        return list;
    }

    @Override
    public int addScenicSpotBanner(MultipartFile file, SysScenicSpotBanner sysScenicSpotBanner) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".png") || type.equals(".jpg")){
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH + filename;// 存放位置
            File destFile = new File(path);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            }catch (Exception e){
                e.printStackTrace();
            }
            sysScenicSpotBanner.setUrl(GET_SYS_SCENIC_SPOT_BANNER_IMG_URL+ filename);
        }
        sysScenicSpotBanner.setId(IdUtils.getSeqId());
        sysScenicSpotBanner.setCreateTime(DateUtil.currentDateTime());
        sysScenicSpotBanner.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotBannerMapper.addScenicSpotBanner(sysScenicSpotBanner);
        return  i ;
    }

    @Override
    public int editScenicSpotBanner(MultipartFile file, SysScenicSpotBanner sysScenicSpotBanner) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名

        if (type.equals(".png") || type.equals(".jpg")){

            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH + filename;// 存放位置
            File destFile = new File(path);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            }catch (Exception e){
                e.printStackTrace();
            }
            sysScenicSpotBanner.setUrl(GET_SYS_SCENIC_SPOT_BANNER_IMG_URL+ filename);
        }

        sysScenicSpotBanner.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotBannerMapper.editScenicSpotBanner(sysScenicSpotBanner);
        return  i ;
    }
}
