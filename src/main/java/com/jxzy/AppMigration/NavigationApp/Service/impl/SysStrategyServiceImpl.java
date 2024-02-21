package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyService;
import com.jxzy.AppMigration.NavigationApp.dao.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.dto.UserOperationLogDTO;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 18:42
 */
@Service
public class SysStrategyServiceImpl implements SysStrategyService {

    @Autowired
    SysStrategyMapper sysStrategyMapper;

    @Autowired
    SysStrategyContentMapper sysStrategyContentMapper;

    @Value("${sysStrategyPatheGetPicUrl}")
    private String sysStrategyPatheGetPicUrl;
    @Value("${sysStrategyPatheGetPicPaht}")
    private String sysStrategyPatheGetPicPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    SysStrategyCollectionMapper sysStrategyCollectionMapper;

    @Autowired
    SysStrategyCommentMapper sysStrategyCommentMapper;

    @Autowired
    SysStrategyLikeMapper sysStrategyLikeMapper;

    @Autowired
    SysUserFansMapper sysUserFansMapper;

    @Override
    public int addSysStrategy(MultipartFile file, SysStrategy sysStrategy) {

        //添加广场
        if ("3".equals(sysStrategy.getType())) {

            if (!file.isEmpty()) {
                String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (type.equals(".jpg") || type.equals(".png")) {
                    String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                    String path = sysStrategyPatheGetPicPaht + filename;// 存放位置
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
                        String upload = fileUploadUtil.upload(file, sysStrategyPatheGetPicPaht.substring(1) + filename);
                        System.out.println(upload);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }//复制文件到指定目录

                    sysStrategy.setId(IdUtils.getSeqId());
                    sysStrategy.setTakePhotos(sysStrategyPatheGetPicUrl + filename);
                    sysStrategy.setCreateTime(DateUtil.currentDateTime());
                    sysStrategy.setUpdateTime(DateUtil.currentDateTime());
                    int i = sysStrategyMapper.insertSelective(sysStrategy);
                    return i;
                } else {
                    return 2;
                }
            } else {
                return 3;
            }

        } else {// 添加游记和攻略

            if (!file.isEmpty()) {
                String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (type.equals(".jpg") || type.equals(".png")) {
                    String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                    String path = sysStrategyPatheGetPicPaht + filename;// 存放位置
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
                        String upload = fileUploadUtil.upload(file, sysStrategyPatheGetPicPaht.substring(1) + filename);
                        System.out.println(upload);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }//复制文件到指定目录

                    sysStrategy.setId(IdUtils.getSeqId());
                    sysStrategy.setCoverPic(sysStrategyPatheGetPicUrl + filename);
                    sysStrategy.setCreateTime(DateUtil.currentDateTime());
                    sysStrategy.setUpdateTime(DateUtil.currentDateTime());
                    int i = sysStrategyMapper.insertSelective(sysStrategy);
                    return i;
                } else {
                    return 2;
                }
            } else {
                return 3;
            }
        }
    }

    /**
     * 游娱攻略App列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysStrategyAppList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)) {
            search.put("cityId", sysScenicSpotBinding.getScenicSpotFid());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SysStrategy> list = sysStrategyMapper.list(search);
        for (SysStrategy sysStrategy : list) {
            //计算评论数量
            Integer commentNumber = sysStrategyCommentMapper.selectStrategyNumber(sysStrategy.getId(), sysStrategy.getType());
            sysStrategy.setCommentNumber(commentNumber.toString());

            //获取内容
            List<SysStrategyContent> strategyIdByListN = sysStrategyContentMapper.getStrategyIdByListN(sysStrategy.getId());
            sysStrategy.setContentlist(strategyIdByListN);

        }
        if (list.size() > 0) {
            PageInfo<SysStrategy> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    //查询攻略，游记，广场详情
    @Override
    public SysStrategy getSysStrategyAppDetails(Map<String, Object> search) {

        SysStrategy sysStrategy = sysStrategyMapper.getSysStrategyAppDetails(search);
        //作者发布文章数量
        if (!StringUtils.isEmpty(sysStrategy.getUserId())) {
            Integer usn = sysStrategyMapper.getUserStrategyNumber(sysStrategy.getUserId(), sysStrategy.getType());
            sysStrategy.setUserStrategyNumber(usn.toString());
        }
        //文章收藏数
        Integer sscn = sysStrategyCollectionMapper.selectStrategyNumber(sysStrategy.getId(), sysStrategy.getType());
        sysStrategy.setCollectionNumber(sscn.toString());
        //文章喜欢数
        Integer ssln = sysStrategyLikeMapper.selectStrategyNumber(sysStrategy.getId(), sysStrategy.getType());
        sysStrategy.setLikeNumber(ssln.toString());
        //文章评论数
        Integer sscmn = sysStrategyCommentMapper.selectStrategyNumber(sysStrategy.getId(), sysStrategy.getType());
        sysStrategy.setCommentNumber(sscmn.toString());
        //用户是否关注作者
        if (!StringUtils.isEmpty(search.get("uid"))) {
            Integer uf = sysUserFansMapper.selectIsFans((String) search.get("uid"), sysStrategy.getUserId());
            sysStrategy.setIsFans(uf.toString());
            Integer isCollection = sysStrategyCollectionMapper.getUserIsCollection((String) search.get("uid"), sysStrategy.getId());
            sysStrategy.setIsCollection(isCollection.toString());
            Integer isLike = sysStrategyLikeMapper.getUserIsLike((String) search.get("uid"), sysStrategy.getId());
            sysStrategy.setIsLike(isLike.toString());
        }

        List<SysStrategyContent> list = sysStrategyContentMapper.getStrategyIdByListN((Long) search.get("id"));
        sysStrategy.setContentlist(list);
        return sysStrategy;
    }

    /**
     * 攻略游记，广场，浏览加一
     *
     * @param
     * @return
     */
    @Override
    public int addSysStrategyNumber(Long id) {

        int i = sysStrategyMapper.addSysStrategyNumber(id);

        return i;
    }

    /**
     * 后台游记列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysStrategyList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum, pageSize);
        List<SysStrategy> list = sysStrategyMapper.getSysStrategyList(search);
        if ("3".equals(search.get("type"))) {

            for (SysStrategy sysStrategy : list) {
                if (!StringUtils.isEmpty(sysStrategy.getTakePhotos())) {
                    List<SysStrategyContent> strategyIdByListN = sysStrategyContentMapper.getStrategyIdByListN(sysStrategy.getId());

                    sysStrategy.setPictureNumber(strategyIdByListN.size());
                } else {
                    sysStrategy.setPictureNumber(0);
                }
            }
        }
        if (list.size() > 0) {
            PageInfo<SysStrategy> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;
    }

    /**
     * 后台攻略游记审核
     *
     * @param id
     * @param state
     * @return
     */
    @Override
    public int editSysStrategyToExamine(Long id, String state) {


        int i = sysStrategyMapper.editSysStrategyToExamine(id, state);

        return i;
    }

    /**
     * 无文件添加攻略，游记，广场
     *
     * @param sysStrategy
     * @return
     */
    @Override
    public int addSysStrategyNoFile(SysStrategy sysStrategy) {
        sysStrategy.setId(IdUtils.getSeqId());
        sysStrategy.setUpdateTime(DateUtil.currentDateTime());
        if (StringUtils.isEmpty(sysStrategy.getCity())) {
            sysStrategy.setCity("0");
        }
        if (StringUtils.isEmpty(sysStrategy.getArea())) {
            sysStrategy.setArea("0");
        }
        sysStrategy.setCreateTime(DateUtil.currentDateTime());
        sysStrategy.setUpdateTime(DateUtil.currentDateTime());
        int i = sysStrategyMapper.insertSelective(sysStrategy);
        return i;
    }

    /**
     * 编辑
     *
     * @param sysStrategy
     * @return
     */
    @Override
    public int editSysStrategyNoFile(SysStrategy sysStrategy) {

        sysStrategy.setUpdateTime(DateUtil.currentDateTime());
        if (StringUtils.isEmpty(sysStrategy.getCity())) {
            sysStrategy.setCity("0");
        }
        if (StringUtils.isEmpty(sysStrategy.getArea())) {
            sysStrategy.setArea("0");
        }
        int i = sysStrategyMapper.updateSelective(sysStrategy);
        return i;
    }

    /**
     * 我的收藏
     *
     * @param uid
     * @param type
     * @return
     */
    @Override
    public PageDataResult myCollection(String uid, String type, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysStrategy> list = sysStrategyCollectionMapper.myCollection(uid, type);
        for (SysStrategy sysStrategy : list) {
            Integer commentNumber = sysStrategyCommentMapper.selectStrategyNumber(sysStrategy.getId(), type);
            sysStrategy.setCommentNumber(commentNumber.toString());
        }
        if (list.size() > 0) {
            PageInfo<SysStrategy> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 我的喜欢
     *
     * @param uid
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult myLike(String uid, String type, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysStrategy> list = sysStrategyLikeMapper.myLike(uid, type);
        for (SysStrategy sysStrategy : list) {
            Integer commentNumber = sysStrategyLikeMapper.selectStrategyNumber(sysStrategy.getId(), type);
            sysStrategy.setCommentNumber(commentNumber.toString());
        }
        if (list.size() > 0) {
            PageInfo<SysStrategy> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * app端全局搜索
     *
     * @param search
     * @return
     */
    @Override
    public PageDataResult getAllSysStrategy(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)) {
            search.put("cityId", sysScenicSpotBinding.getScenicSpotFid());
        }
        PageHelper.startPage((Integer) search.get("pageNum"), (Integer) search.get("pageSize"));
        List<SysStrategy> list = sysStrategyMapper.list(search);
        for (SysStrategy sysStrategy : list) {
            //计算评论数量
            Integer commentNumber = sysStrategyCommentMapper.selectStrategyNumber(sysStrategy.getId(), sysStrategy.getType());
            sysStrategy.setCommentNumber(commentNumber.toString());
        }
        if (list.size() > 0) {
            PageInfo<SysStrategy> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;


    }

    /**
     * app端添加游记攻略，广场
     *
     * @param sysStrategy
     * @return
     */
    @Override
    public int addAppSysStrategyNoFile(SysStrategy sysStrategy) {

        sysStrategy.setId(IdUtils.getSeqId());
        sysStrategy.setCreateTime(DateUtil.currentDateTime());
        sysStrategy.setUpdateTime(DateUtil.currentDateTime());
//        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectBySpotLikeName(sysStrategy.getPlaceOfOwnership());
//        if (!StringUtils.isEmpty(sysScenicSpot)){
//            sysStrategy.setStrategySpotId(sysScenicSpot.getScenicSpotId());
//        }


        List<SysScenicSpotBinding> sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotNameLikeByFname(sysStrategy.getPlaceOfOwnership());
        if (sysScenicSpotBinding.size() > 0) {
            sysStrategy.setStrategySpotId(sysScenicSpotBinding.get(0).getScenicSpotFid());
            sysStrategy.setProvince(sysScenicSpotBinding.get(0).getScenicSpotPid().toString());
            if (!StringUtils.isEmpty(sysScenicSpotBinding.get(0).getScenicSpotSid())) {
                sysStrategy.setCity(sysScenicSpotBinding.get(0).getScenicSpotSid().toString());
            }
            if (!StringUtils.isEmpty(sysScenicSpotBinding.get(0).getScenicSpotQid())) {
                sysStrategy.setArea(sysScenicSpotBinding.get(0).getScenicSpotQid().toString());
            }
        }
        int i = sysStrategyMapper.insertSelective(sysStrategy);

        List<SysStrategyContent> contentlist = sysStrategy.getContentlist();
        for (SysStrategyContent sysStrategyContent : contentlist) {
            sysStrategyContent.setId(IdUtils.getSeqId());
            sysStrategyContent.setSysStrategyId(sysStrategy.getId());
            sysStrategyContent.setCreateTime(DateUtil.currentDateTime());
            sysStrategyContent.setUpdateTime(DateUtil.currentDateTime());
            int j = sysStrategyContentMapper.insert(sysStrategyContent);
        }

        return i;

    }


    /**
     * app端添加游记攻略，广场(新)
     *
     * @param sysStrategy
     * @return
     */
    @Override
    public int addAppSysStrategyNoFileNew(SysStrategy sysStrategy) {

        sysStrategy.setId(IdUtils.getSeqId());
        sysStrategy.setCreateTime(DateUtil.currentDateTime());
        sysStrategy.setUpdateTime(DateUtil.currentDateTime());
//        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectBySpotLikeName(sysStrategy.getPlaceOfOwnership());
//        if (!StringUtils.isEmpty(sysScenicSpot)){
//            sysStrategy.setStrategySpotId(sysScenicSpot.getScenicSpotId());
//        }
        Long strategySpotId = sysStrategy.getStrategySpotId();
        if (!StringUtils.isEmpty(strategySpotId)) {
            SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(sysStrategy.getStrategySpotId());
//            List<SysScenicSpotBinding> sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotNameLikeByFname(sysStrategy.getPlaceOfOwnership());
            sysStrategy.setStrategySpotId(sysScenicSpotBinding.getScenicSpotFid());
            sysStrategy.setProvince(sysScenicSpotBinding.getScenicSpotPid().toString());
            sysStrategy.setCity(sysScenicSpotBinding.getScenicSpotSid().toString());
            sysStrategy.setArea(sysScenicSpotBinding.getScenicSpotQid().toString());
            sysStrategy.setCityType("1");
        } else {
            String city = sysStrategy.getCity();

            SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(Long.parseLong(city));

            sysStrategy.setProvince(sysScenicSpotBinding.getScenicSpotPid().toString());
            sysStrategy.setCityType("2");
        }

        int i = sysStrategyMapper.insertSelective(sysStrategy);

        List<SysStrategyContent> contentlist = sysStrategy.getContentlist();
        for (SysStrategyContent sysStrategyContent : contentlist) {
            sysStrategyContent.setId(IdUtils.getSeqId());
            sysStrategyContent.setSysStrategyId(sysStrategy.getId());
            sysStrategyContent.setCreateTime(DateUtil.currentDateTime());
            sysStrategyContent.setUpdateTime(DateUtil.currentDateTime());
            int j = sysStrategyContentMapper.insert(sysStrategyContent);
        }

        return i;

    }

    /**
     * 清空图片数据
     *
     * @param id
     * @return
     */
    @Override
    public int delSysStrategyContentPic(Long id) {

        int i = sysStrategyContentMapper.delSysStrategyContentPic(id);

        return i;
    }

    /**
     * 内容列表批量修改保存
     *
     * @param sysStrategyContentList
     * @return
     */
    @Override
    public int updateSysStrategyContentList(List<SysStrategyContent> sysStrategyContentList) {

        int i = 0;

        for (int i1 = 0; i1 < sysStrategyContentList.size(); i1++) {

            if (StringUtils.isEmpty(sysStrategyContentList.get(i).getId())) {
                sysStrategyContentList.get(i1).setId(IdUtils.getSeqId());
                sysStrategyContentList.get(i1).setCreateTime(DateUtil.currentDateTime());
                sysStrategyContentList.get(i1).setUpdateTime(DateUtil.currentDateTime());
                sysStrategyContentList.get(i1).setSort(i1 + 1 + "");
                i = sysStrategyContentMapper.insert(sysStrategyContentList.get(i1));
            } else {
                sysStrategyContentList.get(i1).setSort(i1 + 1 + "");
                sysStrategyContentList.get(i1).setUpdateTime(DateUtil.currentDateTime());
                i = sysStrategyContentMapper.updateSelective(sysStrategyContentList.get(i1));
            }

        }

        return i;
    }

    @Override
    public List<UserOperationLog> getUserOperation(UserOperationLogDTO userOperationLogDTO) {

        List<UserOperationLog> userOperationLogs = sysStrategyMapper.selectByUser(userOperationLogDTO);
        return userOperationLogs;

    }

    @Override
    public List<UserOperationLog> getUserOperationAll(UserOperationLogDTO userOperationLogDTO) {
        List<UserOperationLog> userOperationLogs = sysStrategyMapper.getUserOperationAll(userOperationLogDTO);
        return userOperationLogs;
    }

    @DS("master2")
    @Override
    public int addUserOperation(UserOperationLogDTO userOperationLogDTO) {
        //修改用户表
        SysGuideAppUsers users = new SysGuideAppUsers();
        users.setSpotId(userOperationLogDTO.getSpotId());
        users.setSpotName(userOperationLogDTO.getSpotName());
        if (userOperationLogDTO.getPhone() != null && !userOperationLogDTO.getPhone().equals("")) {
            users.setUserPhone(userOperationLogDTO.getPhone());
        } else {
            users.setUserPhone("1");
        }
        if (userOperationLogDTO.getSpotId() != null) {
            users.setFristSpotId(userOperationLogDTO.getFristSpotId());
            users.setFristSpotName(userOperationLogDTO.getFristSpotName());
        }
        users.setUserNowIp(userOperationLogDTO.getNowLocation());
        users.setUpdateDate(DateUtil.currentDateTime());
        sysStrategyMapper.updateByPhone(users);
        //添加用户操作表
        if (userOperationLogDTO.getType() == null) {
            return 0;
        }
        userOperationLogDTO.setId(IdUtils.getSeqId());
        userOperationLogDTO.setCreateTime(DateUtil.currentDateTime());
        userOperationLogDTO.setUpdateTime(DateUtil.currentDateTime());
//        UserOperationLog userOperationLog = new UserOperationLog();
//        BeanUtils.copyProperties(userOperationLogDTO,userOperationLog);
        return sysStrategyMapper.addUserOperation(userOperationLogDTO);
    }



    /**
     * 删除游记，动态，广场
     *
     * @param id
     * @return
     */
    @Override
    public int delSysStrategy(Long id) {

        int i = sysStrategyMapper.delete(id);
        int delete = sysStrategyContentMapper.deleteStrategyId(id);

        return i;
    }

    /**
     * 添加游记攻略广场，内容
     *
     * @param sysStrategyContent
     * @return
     */
    @Override
    public int addSysStrategyContent(SysStrategyContent sysStrategyContent) {

        SysStrategy sysStrategy = sysStrategyMapper.selectById(sysStrategyContent.getSysStrategyId());
        String type = sysStrategy.getType();
        if ("3".equals(type)) {
            if (!StringUtils.isEmpty(sysStrategy.getTakePhotos())) {
                sysStrategy.setTakePhotos(sysStrategy.getTakePhotos() + "," + sysStrategyContent.getPictureUrl());
                sysStrategyMapper.updateSelective(sysStrategy);
            } else {
                sysStrategy.setTakePhotos(sysStrategyContent.getPictureUrl());
                sysStrategyMapper.updateSelective(sysStrategy);
            }
        }
        sysStrategyContent.setId(IdUtils.getSeqId());
        sysStrategyContent.setCreateTime(DateUtil.currentDateTime());
        int i = sysStrategyContentMapper.insert(sysStrategyContent);

        return i;
    }

    /**
     * 删除内容
     *
     * @param id
     * @return
     */
    @Override
    public int delSysStrategyContent(Long id) {

        int i = sysStrategyContentMapper.delete(id);

        return i;
    }

    /**
     * 查询攻略，游记，广场内容
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysStrategyContentList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        if (!StringUtils.isEmpty(pageNum) && !StringUtils.isEmpty(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<SysStrategyContent> list = sysStrategyContentMapper.getStrategyIdByList(search);
        if (list.size() > 0) {

            pageDataResult.setData(list);

            pageDataResult.setCode(200);

        }
        return pageDataResult;
    }

    @Override
    public List<SysStrategy> getAllSysStrategyN(Map<String, Object> search) {

        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)) {
            search.put("cityId", sysScenicSpotBinding.getScenicSpotFid());
        }
        PageHelper.startPage((Integer) search.get("pageNum"), (Integer) search.get("pageSize"));
        List<SysStrategy> list = sysStrategyMapper.list(search);
        for (SysStrategy sysStrategy : list) {
            //计算评论数量
            Integer commentNumber = sysStrategyCommentMapper.selectStrategyNumber(sysStrategy.getId(), sysStrategy.getType());
            sysStrategy.setCommentNumber(commentNumber.toString());
        }
        return list;
    }

    /**
     * 我的攻略
     *
     * @param uid
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult myIntroduction(String uid, String type, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("uid", uid);
        search.put("type", type);
        PageHelper.startPage(pageNum, pageSize);
        List<SysStrategy> list = sysStrategyMapper.myList(search);
        for (SysStrategy sysStrategy : list) {
            List<SysStrategyContent> list1 = sysStrategyContentMapper.getStrategyIdByListN(sysStrategy.getId());
            sysStrategy.setContentlist(list1);
        }
        if (list.size() > 0) {
            PageInfo<SysStrategy> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 编辑游记攻略广场内容
     *
     * @param sysStrategyContent
     * @return
     */
    @Override
    public int editSysStrategyContent(SysStrategyContent sysStrategyContent) {

        sysStrategyContent.setUpdateTime(DateUtil.currentDateTime());

        int i = sysStrategyContentMapper.updateSelective(sysStrategyContent);

        return i;

    }
}
