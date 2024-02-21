package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumService;
import com.jxzy.AppMigration.NavigationApp.dao.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Point2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author zhang
 * @Date 2023/6/20 11:14
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class SysMuseumServiceImpl extends ServiceImpl<SysMuseumMapper, SysMuseum> implements SysMuseumService {

    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private SysMuseumTagRelationMapper sysMuseumTagRelationMapper;
    @Autowired
    private SysMuseumBannerMapper sysMuseumBannerMapper;
    @Autowired
    private SysMuseumMapResMapper sysMuseumMapResMapper;

    @Autowired
    private SysMuseumCollectionMapper sysMuseumCollectionMapper;

    @Autowired
    private SysMuseumCollectionBroadcastMapper sysMuseumCollectionBroadcastMapper;

    @Autowired
    private SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    private SysMuseumPlanViewMapper sysMuseumPlanViewMapper;

    @Autowired
    private SysMuseumGpsCoordinateMapper sysMuseumGpsCoordinateMapper;

    @Autowired
    private SysMuseumTagMapper sysMuseumTagMapper;

    @Autowired
    private SysMuseumMapper sysMuseumMapper;

    @Value("${sysMuseumPatheGetPicUrl}")
    private String sysMuseumPatheGetPicUrl;
    @Value("${sysMuseumPatheGetPicPaht}")
    private String sysMuseumPatheGetPicPaht;


    @Override
    public int save(MultipartFile photo, SysMuseum sysMuseum) {
        int judgeRes = judgeFile(photo, sysMuseum);
        if (judgeRes == -2) {
            return Constant.UN_SUPPORT_PICTURE_TYPE;
        } else if (judgeRes == -3) {
            return Constant.PIC_IS_EMPTY;
        } else {
            //添加博物馆
            sysMuseum.setId(IdUtils.getSeqId());
            String museumName = sysMuseum.getMuseumName();
            sysMuseum.setMuseumNamePinying(Tinypinyin.tinypinyin(museumName));
            sysMuseum.setMuseumIntroducePingying(Tinypinyin.tinypinyin(sysMuseum.getMuseumIntroducePingying()));
            sysMuseum.setCreateTime(DateUtil.currentDateTime());
            int res = sysMuseumMapper.insert(sysMuseum);
            addTagForMuseum(sysMuseum);
            return res;
        }
    }

    @Override
    public boolean save(SysMuseum sysMuseum) {

        sysMuseum.setId(IdUtils.getSeqId());
        sysMuseum.setCreateTime(DateUtil.currentDateTime());
        sysMuseum.setUpdateTime(DateUtil.currentDateTime());
        String museumName = sysMuseum.getMuseumName();
        sysMuseum.setMuseumNamePinying(Tinypinyin.tinypinyin(museumName));
        sysMuseum.setMuseumIntroducePingying(Tinypinyin.tinypinyin(sysMuseum.getMuseumIntroducePingying()));
        //添加博物馆
        boolean res = SqlHelper.retBool(sysMuseumMapper.insert(sysMuseum));
        addTagForMuseum(sysMuseum);
        return res;
    }


    @Override
    public int update(MultipartFile photo, SysMuseum sysMuseum) {
        int res = judgeFile(photo, sysMuseum);
        if (res == -2) {
            return Constant.UN_SUPPORT_PICTURE_TYPE;
        } else if (res == -3) {
            return Constant.PIC_IS_EMPTY;
        } else {
            editTagForMuseum(sysMuseum);
            String museumName = sysMuseum.getMuseumName();
            sysMuseum.setMuseumNamePinying(Tinypinyin.tinypinyin(museumName));
            sysMuseum.setMuseumIntroducePingying(Tinypinyin.tinypinyin(sysMuseum.getMuseumIntroducePingying()));
            sysMuseum.setUpdateTime(DateUtil.currentDateTime());
            return sysMuseumMapper.updateById(sysMuseum);
        }
    }

    /**
     * 修改博物馆标签，前提是博物馆标签存在
     *
     * @param sysMuseum
     */
    private void editTagForMuseum(SysMuseum sysMuseum) {
        List<SysMuseumTag> museumTagList = sysMuseum.getMuseumTagList();
        if (ObjUtil.isNotEmpty(museumTagList)) {
            //1.删除旧的标签
            LambdaUpdateWrapper<SysMuseumTagRelation> smtrluw = Wrappers.lambdaUpdate(SysMuseumTagRelation.class);
            Long museumId = sysMuseum.getId();
            smtrluw.eq(ObjUtil.isNotEmpty(museumId), SysMuseumTagRelation::getMuseumId, museumId);
            sysMuseumTagRelationMapper.delete(smtrluw);
            //2.添加新的标签
            addTagForMuseum(sysMuseum);
        }
    }

    @Override
    public boolean updateById(SysMuseum sysMuseum) {
        editTagForMuseum(sysMuseum);
        String museumName = sysMuseum.getMuseumName();
        sysMuseum.setMuseumNamePinying(Tinypinyin.tinypinyin(museumName));
        sysMuseum.setMuseumIntroducePingying(Tinypinyin.tinypinyin(sysMuseum.getMuseumIntroducePingying()));
        sysMuseum.setUpdateTime(DateUtil.currentDateTime());
        return SqlHelper.retBool(sysMuseumMapper.updateById(sysMuseum));
    }

    @Override
    public List<SysMuseum> getMuseumInfo(Integer pageNum, Integer pageSize, SysMuseum sysMuseum) {

        Page<SysMuseum> page = new Page<>(pageNum, pageSize);
        //参数传类对象，如果传引用，则存在SQ语句BUG
        LambdaQueryWrapper<SysMuseum> smlqw = Wrappers.lambdaQuery(SysMuseum.class);
        String museumName = sysMuseum.getMuseumName();
        smlqw.like(StringUtils.hasText(museumName), SysMuseum::getMuseumName, museumName);
        smlqw.orderByDesc(SysMuseum::getCreateTime);
        sysMuseumMapper.selectPage(page, smlqw);
        List<SysMuseum> museumList = page.getRecords();

        Long museumId;
        LambdaQueryWrapper<SysMuseumBanner> smblqw;
        Long museumBannerCount;

        LambdaQueryWrapper<SysMuseumMapRes> smmrlqw;
        Long museumMapResCount;

        LambdaQueryWrapper<SysMuseumCollection> smclqw;
        Long museumCollectionCount;
        List<SysMuseumCollection> museumCollectionList;

        LambdaQueryWrapper<SysMuseumCollectionBroadcast> smcblqw;
        Long museumCollectionBroadcastCount = 0L;

        LambdaQueryWrapper<SysMuseumTagRelation> smtrlqw;
        List<SysMuseumTagRelation> sysMuseumTagRelationList;

        LambdaQueryWrapper<SysMuseumTag> smtlqw;
        SysMuseumTag sysMuseumTag;

        ArrayList<SysMuseumTag> museumTagList;
        for (SysMuseum museum : museumList) {
            //当前博物馆的轮播图数量
            smblqw = Wrappers.lambdaQuery();
            museumId = museum.getId();
            smblqw.eq(!StringUtils.isEmpty(museumId), SysMuseumBanner::getMuseumId, museumId);
            museumBannerCount = sysMuseumBannerMapper.selectCount(smblqw);
            //当前博物馆的地图数量
            smmrlqw = Wrappers.lambdaQuery();
            smmrlqw.eq(!StringUtils.isEmpty(museumId), SysMuseumMapRes::getResMuseumId, museumId);
            museumMapResCount = sysMuseumMapResMapper.selectCount(smmrlqw);
            //当前博物馆的藏品数量
            smclqw = Wrappers.lambdaQuery();
            smclqw.eq(!StringUtils.isEmpty(museumId), SysMuseumCollection::getMuseumId, museumId);
            museumCollectionCount = sysMuseumCollectionMapper.selectCount(smclqw);
            //当前博物馆的所有藏品
            museumCollectionList = sysMuseumCollectionMapper.selectList(smclqw);
            if (CollUtil.isNotEmpty(museumCollectionList)) {
                String museumCollectionIntroduce;
                String museumCollectionVideo;
                for (SysMuseumCollection museumCollection : museumCollectionList) {
                    museumCollectionIntroduce = museumCollection.getMuseumCollectionIntroduce();
                    museumCollectionVideo = museumCollection.getMuseumCollectionVideo();
                    //当前博物馆藏品有文字介绍或语音讲解之一，计算当前博物馆的语音讲解点数量时就加一；否则就不加。
                    if (!StrUtil.isAllBlank(museumCollectionIntroduce, museumCollectionVideo)) {
                        museumCollectionBroadcastCount++;
                    }
                }
            }
            //当前博物馆的标签
            smtrlqw = Wrappers.lambdaQuery();
            smtrlqw.eq(!StringUtils.isEmpty(museumId), SysMuseumTagRelation::getMuseumId, museumId);
            sysMuseumTagRelationList = sysMuseumTagRelationMapper.selectList(smtrlqw);
            museumTagList = new ArrayList<>();
            if (!ObjUtil.isEmpty(sysMuseumTagRelationList)) {
                Long tagId;
                for (SysMuseumTagRelation relation : sysMuseumTagRelationList) {
                    tagId = relation.getTagId();
                    smtlqw = Wrappers.lambdaQuery();
                    smtlqw.eq(!StringUtils.isEmpty(tagId), SysMuseumTag::getId, tagId);
                    sysMuseumTag = sysMuseumTagMapper.selectOne(smtlqw);
                    if (!ObjUtil.isEmpty(sysMuseumTag)) {
                        museumTagList.add(sysMuseumTag);
                    }
                }
            }
            museum.setMuseumBannerCount(museumBannerCount);
            museum.setMuseumMapResCount(museumMapResCount);
            museum.setMuseumCollectionCount(museumCollectionCount);
            museum.setMuseumCollectionBroadcastCount(museumCollectionBroadcastCount);
            museum.setMuseumTagList(museumTagList);
        }
        return museumList;
    }

    /**
     * 博物馆列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysMuseumList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        //经度
        String lng = (String) search.get("lng");
        //纬度
        String lat = (String) search.get("lat");

        if (StringUtils.isEmpty(lng) && StringUtils.isEmpty(lat)) {

        }
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)) {
            search.put("cityId", sysScenicSpotBinding.getScenicSpotFid());
        }

        PageHelper.startPage(pageNum, pageSize);
        List<SysMuseum> list = sysMuseumMapper.getSysMuseumList(search);

        if (!StringUtils.isEmpty(search.get("lng")) && !StringUtils.isEmpty(search.get("lat"))) {
            for (SysMuseum sysMuseum : list) {

                if (StringUtils.isEmpty(sysMuseum.getMuseumGpsBaiDu())) {
                    sysMuseum.setDistance(-1d);
                    continue;
                }
                String[] split = sysMuseum.getMuseumGpsBaiDu().split(",");
                if (split.length <= 0 && "0".equals(split[0]) && "0".equals(split[1])) {
                    sysMuseum.setDistance(-1d);
                    continue;
                }
                from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng), Double.valueOf(lat));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                sysMuseum.setDistance(distanceOne);

                //语音讲解点数量
                Integer broadcastNumber = sysMuseumCollectionBroadcastMapper.getMuseumCollectionBroadcastNumber(sysMuseum.getId());
                sysMuseum.setSysMuseumCollectionBroadcastNumber(broadcastNumber);
                //藏品数量
                Integer collectionNumber = sysMuseumCollectionMapper.getMuseumCollectionNumber(sysMuseum.getId());
                sysMuseum.setSysMuseumCollectionNumber(collectionNumber);

            }
        } else {
            for (SysMuseum sysMuseum : list) {
                sysMuseum.setDistance(-1d);

                //语音讲解点数量
                Integer broadcastNumber = sysMuseumCollectionBroadcastMapper.getMuseumCollectionBroadcastNumber(sysMuseum.getId());
                sysMuseum.setSysMuseumCollectionBroadcastNumber(broadcastNumber);

                //藏品数量
                Integer collectionNumber = sysMuseumCollectionMapper.getMuseumCollectionNumber(sysMuseum.getId());
                sysMuseum.setSysMuseumCollectionNumber(collectionNumber);


            }
        }
        if (list.size() > 0) {
            PageInfo<SysMuseum> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }


        return pageDataResult;
    }

    /**
     * 根据博物馆id，获取博物馆详情
     *
     * @param search
     * @return
     */
    @Override
    public SysMuseum getSysMuseumIdDetails(Map<String, Object> search) {

        SysMuseum sysMuseum = sysMuseumMapper.getSysMuseumIdDetails(search);

        List<SysMuseumCollection> list = sysMuseumCollectionMapper.selectMuseumIdByList(sysMuseum.getId());

        List<SysMuseumPlanView> museumPlanViews = sysMuseumPlanViewMapper.selectByMuseumId(sysMuseum.getId());
        List<SysMuseumMapRes> museumMapRes = sysMuseumMapResMapper.selectByMuseumId(sysMuseum.getId());
        sysMuseum.setMuseumMapResList(museumMapRes);
        sysMuseum.setMuseumCollectionList(list);
        sysMuseum.setMuseumPlanViewList(museumPlanViews);
        return sysMuseum;
    }

    @Override
    public SysMuseumCollectionBroadcast getCollectionIdBroadcast(Map<String, Object> search) {
        SysMuseumCollectionBroadcast sysMuseumCollectionBroadcast = sysMuseumCollectionBroadcastMapper.getCollectionIdBroadcast(search);

        return sysMuseumCollectionBroadcast;
    }

    /**
     * 标签列表如果不为空，则给博物馆添加标签
     *
     * @param sysMuseum 正在添加的博物馆
     */
    private void addTagForMuseum(SysMuseum sysMuseum) {

        List<SysMuseumTag> museumTagList = sysMuseum.getMuseumTagList();
        if (!ObjUtil.isEmpty(museumTagList)) {
            SysMuseumTagRelation relation;
            Long museumId = sysMuseum.getId();
            for (SysMuseumTag sysMuseumTag : museumTagList) {
                relation = new SysMuseumTagRelation();
                relation.setMuseumId(museumId);
                relation.setTagId(sysMuseumTag.getId());
                sysMuseumTagRelationMapper.insert(relation);
            }
        }
    }

    /**
     * 根据坐标获取是否在博物馆内
     *
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getGpsMuseum(String lng, String lat) {

        Map<String, Object> map = new HashMap<>();
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();

        List<SysMuseumGpsCoordinateWithBLOBs> list = sysMuseumGpsCoordinateMapper.selectAll();

        Long museumId = 0l;
        Double distance = 0d;
        for (SysMuseumGpsCoordinateWithBLOBs sysMuseumGpsCoordinateWithBLOBs : list) {

            String[] coordinateOuterring = sysMuseumGpsCoordinateWithBLOBs.getCoordinateOuterringBaiDu().split("!");
            if (coordinateOuterring != null && coordinateOuterring.length > 0) {
                Point[] ps = new Point[coordinateOuterring.length];
                for (int i = 0; i < coordinateOuterring.length; i++) {
                    String[] str = coordinateOuterring[i].split(",");
                    ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                }


                boolean ptInPoly = JudgingCoordinates.isPtInPoly(Double.valueOf(lng), Double.valueOf(lat), ps);
                if (ptInPoly) {
                    distance = 0d;
                    museumId = sysMuseumGpsCoordinateWithBLOBs.getCoordinateScenicSpotId();
                    break;
                } else {
                    continue;
                }
            }
        }

        if (museumId == 0) {
            map.put("museum", null);
            return map;
        } else {
            SysMuseum sysMuseum = sysMuseumMapper.selectById(museumId);
            List<SysMuseumMapRes> museumMapResList = sysMuseumMapResMapper.selectByMuseumId(sysMuseum.getId());
            List<SysMuseumCollection> sysMuseumCollections = sysMuseumCollectionMapper.selectMuseumIdByList(sysMuseum.getId());
            List<SysMuseumPlanView> museumPlanViews = sysMuseumPlanViewMapper.selectByMuseumId(sysMuseum.getId());
            sysMuseum.setMuseumPlanViewList(museumPlanViews);
            sysMuseum.setMuseumCollectionList(sysMuseumCollections);
            sysMuseum.setMuseumMapResList(museumMapResList);
            map.put("museum", sysMuseum);
        }
        return map;
    }

    /**
     * 根据藏品id，获取藏品详情
     *
     * @param
     * @return
     */
    @Override
    public List<SysMuseumCollection> getMuseumCollectionIdDetails(Map<String, Object> search) {

        List<SysMuseumCollection> sysMuseumCollection = sysMuseumCollectionMapper.getMuseumCollectionIdDetails(search);

        return sysMuseumCollection;
    }

    /**
     * 判断文件类型，
     * 返回判断结果 1:保存到本地，并上传到阿里云OSS.  -2:不支持的文件类型，-3:文件内容为空
     *
     * @param file
     * @param sysMuseum
     * @return
     */
    private int judgeFile(MultipartFile file, SysMuseum sysMuseum) {
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String suffix = FileNameUtil.getSuffix(originalFilename);
            //文件类型限制
            if (PictureType.generalPictureType.contains(suffix)) {
                String filename = System.currentTimeMillis() + "." + suffix;
                //存储到阿里云OSS
                String upload = fileUploadUtil.upload(file, sysMuseumPatheGetPicPaht.substring(1) + filename);
                log.info("阿里云图片地址:{}", upload);
                //封面图和详情图
                sysMuseum.setCoverPic(sysMuseumPatheGetPicUrl + filename);
                sysMuseum.setDetailsPic(sysMuseumPatheGetPicUrl + filename);
                return Constant.SAVE_TO_OSS_SUCCESSFUL;
            }
            return Constant.UN_SUPPORT_PICTURE_TYPE;
        }
        return Constant.PIC_IS_EMPTY;
    }
}