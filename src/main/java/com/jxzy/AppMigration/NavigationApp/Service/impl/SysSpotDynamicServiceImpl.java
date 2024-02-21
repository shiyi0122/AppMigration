package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysSpotDynamicService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysSpotDynamicBannerMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysSpotDynamicContentMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysSpotDynamicMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamic;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamicBanner;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamicContent;
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
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/12 19:13
 * 动态
 */
@Service
public class SysSpotDynamicServiceImpl implements SysSpotDynamicService {

    @Autowired
    SysSpotDynamicMapper sysSpotDynamicMapper;

    @Autowired
    SysSpotDynamicContentMapper sysSpotDynamicContentMapper;

    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Value("${sysSpotDynamicPatheGetPicPaht}")
    private String sysSpotDynamicPatheGetPicPaht;
    @Value("${sysSpotDynamicPatheGetPicUrl}")
    private String sysSpotDynamicPatheGetPicUrl;

    @Autowired
    SysSpotDynamicBannerMapper sysSpotDynamicBannerMapper;

    @Autowired
    FileUploadUtil fileUploadUtil;

    /**
     * app端查询动态列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysSpotDynamicAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }

        PageHelper.startPage(pageNum,pageSize);
        List<SysSpotDynamic> list =  sysSpotDynamicMapper.list(search);
        for (SysSpotDynamic sysSpotDynamic : list) {
            List<SysSpotDynamicBanner> bannerList = sysSpotDynamicBannerMapper.list(sysSpotDynamic.getId());
            sysSpotDynamic.setBannerList(bannerList);

            List<SysSpotDynamicContent> contentList = sysSpotDynamicContentMapper.list(sysSpotDynamic.getId());
            sysSpotDynamic.setContentList(contentList);

        }
        if (list.size()>0){
            PageInfo<SysSpotDynamic> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;
    }

    /**
     *  后台添加动态
     * @param file
     * @param sysSpotDynamic
     * @return
     */
    @Override
    public int addSysSpotDynamic(MultipartFile file, SysSpotDynamic sysSpotDynamic) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = sysSpotDynamicPatheGetPicPaht + filename;// 存放位置
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
                    String upload = fileUploadUtil.upload(file, sysSpotDynamicPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录

                sysSpotDynamic.setId(IdUtils.getSeqId());
                sysSpotDynamic.setCoverPic(sysSpotDynamicPatheGetPicUrl+filename);
                sysSpotDynamic.setCreateTime(DateUtil.currentDateTime());
                sysSpotDynamic.setUpdateTime(DateUtil.currentDateTime());
                int i = sysSpotDynamicMapper.insertSelective(sysSpotDynamic);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }




    }

    @Override
    public int editSysSpotDynamic(MultipartFile file, SysSpotDynamic sysSpotDynamic) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = sysSpotDynamicPatheGetPicPaht + filename;// 存放位置
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
                    String upload = fileUploadUtil.upload(file, sysSpotDynamicPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysSpotDynamic.setCoverPic(sysSpotDynamicPatheGetPicUrl+filename);
                sysSpotDynamic.setUpdateTime(DateUtil.currentDateTime());
                int i = sysSpotDynamicMapper.updateSelective(sysSpotDynamic);
                return i;
            }else{
                return 2;
            }
        }else{
            return 3;
        }



    }

    /**
     * 后台删除动态
     * @param id
     * @return
     */
    @Override
    public int delSysSpotDynamic(Long id) {

        int i =  sysSpotDynamicMapper.deleteByPrimaryKey(id);

        return i;

    }

    /**
     * 后台查询动态列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysSpotDynamicList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysSpotDynamic> list = sysSpotDynamicMapper.list(search);
        if (list.size()>0){
            PageInfo<SysSpotDynamic> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    @Override
    public PageDataResult getAllSysSpotDynamic(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }


        PageHelper.startPage((Integer) search.get("pageNum"),(Integer) search.get("pageSize"));
        List<SysSpotDynamic> list =  sysSpotDynamicMapper.list(search);

        if (list.size()>0){
            PageInfo<SysSpotDynamic> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;
    }

    /**
     * 无文件添加动态
     * @param sysSpotDynamic
     * @return
     */
    @Override
    public int addSysSpotDynamicN(SysSpotDynamic sysSpotDynamic) {

        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(Long.parseLong(sysSpotDynamic.getAscriptionSpotId()));

        if (StringUtils.isEmpty(sysScenicSpotBinding)){
            sysSpotDynamic.setProvince(sysScenicSpotBinding.getScenicSpotPid());
            sysSpotDynamic.setCity(sysScenicSpotBinding.getScenicSpotSid());
            sysSpotDynamic.setCity(sysScenicSpotBinding.getScenicSpotQid());
        }
        sysSpotDynamic.setId(IdUtils.getSeqId());
        sysSpotDynamic.setCreateTime(DateUtil.currentDateTime());
        sysSpotDynamic.setUpdateTime(DateUtil.currentDateTime());

        int i = sysSpotDynamicMapper.insertSelective(sysSpotDynamic);
        return i;
    }

    /**
     * 无文件修改动态
     * @param sysSpotDynamic
     * @return
     */
    @Override
    public int editSysSpotDynamicN(SysSpotDynamic sysSpotDynamic) {

        sysSpotDynamic.setUpdateTime(DateUtil.currentDateTime());
        int i = sysSpotDynamicMapper.updateSelective(sysSpotDynamic);
        return i;

    }

    /**
     * 上传动态轮播图
     * @return
     */
    @Override
    public int addSysSpotDynamicBanner(SysSpotDynamicBanner sysSpotDynamicBanner) {

        sysSpotDynamicBanner.setId(IdUtils.getSeqId());
        sysSpotDynamicBanner.setCreateTime(DateUtil.currentDateTime());
        sysSpotDynamicBanner.setUpdateTime(DateUtil.currentDateTime());

        int i = sysSpotDynamicBannerMapper.insertSelective(sysSpotDynamicBanner);

        return i;

    }

    /**
     * 上传动态轮播图
     * @param sysSpotDynamicBanner
     * @return
     */
    @Override
    public int editSysSpotDynamicBanner(SysSpotDynamicBanner sysSpotDynamicBanner) {

        sysSpotDynamicBanner.setUpdateTime(DateUtil.currentDateTime());

        int i = sysSpotDynamicBannerMapper.updateSelective(sysSpotDynamicBanner);

        return i;
    }

    /**
     * 删除轮播图
     * @param id
     * @return
     */
    @Override
    public int delSysSpotDynamicBanner(Long id) {

        int i = sysSpotDynamicBannerMapper.delete(id);
        return i;
    }

    /**
     * 轮播图列表查询
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getSysSpotDynamicBannerList(Long id, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);

        List<SysSpotDynamicBanner> list = sysSpotDynamicBannerMapper.list(id);
        if (list.size()>0){
            PageInfo<SysSpotDynamicBanner> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 添加动态内容
     * @param
     * @return
     */
    @Override
    public int addSysSpotDynamicContent(SysSpotDynamicContent sysSpotDynamicContent) {

        sysSpotDynamicContent.setId(IdUtils.getSeqId());

        sysSpotDynamicContent.setCreateTime(DateUtil.currentDateTime());

        int i =  sysSpotDynamicContentMapper.insert(sysSpotDynamicContent);

        return i;
    }

    /**
     * 删除 动态内容
     * @param
     * @return
     */
    @Override
    public int delSysSpotDynamicContent(Long id) {

       int i = sysSpotDynamicContentMapper.delete(id);

       return i;
    }

    /**
     * 动态内容列表查询
     *
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getSysSpotDynamicContentList(Long id, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);

        List<SysSpotDynamicContent> list = sysSpotDynamicContentMapper.list(id);

        if (list.size()>0){
            PageInfo<SysSpotDynamicContent> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;
    }

    @Override
    public List<SysSpotDynamic> getAllSysSpotDynamicN(Map<String, Object> search) {

        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
        }


        PageHelper.startPage((Integer) search.get("pageNum"),(Integer) search.get("pageSize"));
        List<SysSpotDynamic> list =  sysSpotDynamicMapper.list(search);

        return list;
    }

    /**
     * 浏览数量+1
     * @param id
     * @return
     */
    @Override
    public int addSysSpotDynamicBrowse(Long id) {

        int i = sysSpotDynamicMapper.addSysSpotDynamicBrowse(id);

        return i;
    }

    @Override
    public int editSysSpotDynamicContent(SysSpotDynamicContent sysSpotDynamicContent) {

         int i =sysSpotDynamicContentMapper.update(sysSpotDynamicContent);
         return i;
    }

    /**
     * 删除动态内容图片
     * @param id
     * @return
     */
    @Override
    public int delSysSpotDynamicContentPic(Long id) {
        int i =  sysSpotDynamicContentMapper.delSysSpotDynamicContentPic(id);
        return i;
    }


}
