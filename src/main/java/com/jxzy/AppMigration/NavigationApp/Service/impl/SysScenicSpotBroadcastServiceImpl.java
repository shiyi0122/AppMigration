package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastService;
import com.jxzy.AppMigration.NavigationApp.dao.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotBroadcastExcel;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
//import com.jxzy.AppMigration.NavigationApp.util.DistanceUtil;
import com.jxzy.AppMigration.NavigationApp.util.FileUploadUtil;
import com.jxzy.AppMigration.NavigationApp.util.LngLonUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysScenicSpotBroadcastServiceImpl implements SysScenicSpotBroadcastService {

    @Autowired
    private SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;
    @Autowired
    private SysScenicSpotBroadcastExtendMapper sysScenicSpotBroadcastExtendMapper;
    @Autowired
    SysUserScenicFabulousCollectionMapper sysUserScenicFabulousCollectionMapper;
    @Autowired
    SysScenicSpotUserStopMapper sysScenicSpotUserStopMapper;
    @Autowired
    SysScenicBroadcastReadTimeMapper sysScenicBroadcastReadTimeMapper;
    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    SysScenicSpotBroadcastAdmissionFeeMapper sysScenicSpotBroadcastAdmissionFeeMapper;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址

    @Value("${spotBroadcastPatheGetPicUrl}")
    private String spotBroadcastPatheGetPicUrl;

    @Value("${spotBroadcastPatheGetPicPaht}")
    private String spotBroadcastPatheGetPicPaht;

    @Value("${spotBroadcastAudioUrl}")
    private String spotBroadcastAudioUrl;

    @Value("${spotBroadcastAudioPaht}")
    private String spotBroadcastAudioPaht;

    @Value("${spotBroadcastVodioUrl}")
    private String spotBroadcastVodioUrl;

    @Value("${spotBroadcastVodioPaht}")
    private String spotBroadcastVodioPaht;

    @Autowired
    FileUploadUtil fileUploadUtil;


    /**
     * @param: pageNum 当前页
     * @param: pageSize当前页总条数
     * @param: search  map对象
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcast(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBroadcast> broadcast = sysScenicSpotBroadcastMapper.queryWordsScenicSpotBroadcast(search);
        for (int i = 0; i < broadcast.size(); i++) {
            List<SysScenicSpotBroadcastExtendWithBLOBs> sysScenicSpotBroadcastExtend = broadcast.get(i).getSysScenicSpotBroadcastExtend();
            if (sysScenicSpotBroadcastExtend.size() > 0) {
                for (int j = 0; j < sysScenicSpotBroadcastExtend.size(); j++) {
                    sysScenicSpotBroadcastExtend.get(j).setPictureUrl(DOMAIN_NAME + sysScenicSpotBroadcastExtend.get(j).getPictureUrl());
                }
            }
        }
        return sysScenicSpotBroadcastMapper.queryWordsScenicSpotBroadcast(search);
    }

    /**
     * 查询景区停靠点
     *
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotBroadcast> queryScenicSpotStop(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotBroadcastMapper.queryScenicSpotStop(search);
    }

    /**
     * 查询景点排行
     *
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    public List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcastList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotBroadcastMapper.queryWordsScenicSpotBroadcastList(search);
    }


    /**
     * 查询景点详情
     *
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs
     * @author: qushaobei
     * @date: 2022/8/5 0005
     */
    public List<SysScenicSpotBroadcastExtendWithBLOBs> queryscenicSpotContent(Map<String, Object> search) {
        List<SysScenicSpotBroadcastAdmissionFee> admissionFeeList = new ArrayList<>();
        List<SysScenicSpotBroadcastExtendWithBLOBs> Broadcast = sysScenicSpotBroadcastExtendMapper.queryscenicSpotContent(search);
        if (!StringUtils.isEmpty(Broadcast)) {
            String content = "";
            for (SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs : Broadcast) {

                if (!StringUtils.isEmpty(sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastContent())) {
                    content = sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastContent();
                    break;
                } else {
                    continue;
                }
            }

            SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(Long.parseLong((String) search.get("broadcastId")));
            Broadcast.get(0).setIntroduce(sysScenicSpotBroadcast.getIntroduce());
            Broadcast.get(0).setBroadcastContent(content);
            if ("1".equals(sysScenicSpotBroadcast.getIfNeedAdmissionTicket())) {
                //判断景点是否需要门票
                admissionFeeList = sysScenicSpotBroadcastAdmissionFeeMapper.selectByBroadcastId(sysScenicSpotBroadcast.getBroadcastId());
                Broadcast.get(0).setAdmissionFeeList(admissionFeeList);
            }
        }
        return Broadcast;
//        return sysScenicSpotBroadcastExtendMapper.queryscenicSpotContent(search);

    }

    /**
     * 获取景点列表
     * 张
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSpotBroadcastList(Integer pageNum, Integer pageSize, Integer sort, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> map = new HashMap<>();
        List<SysScenicSpotBroadcast> list = new ArrayList<>();
        List<SysScenicSpotBroadcastAdmissionFee> admissionFeeList = new ArrayList<>();
        if (!StringUtils.isEmpty(search.get("lat")) && !StringUtils.isEmpty(search.get("lat"))) {
            Point2D.Double from = new Point2D.Double();
            Point2D.Double to = new Point2D.Double();
            search.put("sort", sort);
            PageHelper.startPage(pageNum, pageSize);
            list = sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);
            JSONObject obj = new JSONObject();
            for (SysScenicSpotBroadcast sysScenicSpotBroadcast : list) {

                List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastExtend = sysScenicSpotBroadcastExtendMapper.getBroadcastId(sysScenicSpotBroadcast.getBroadcastId());
                for (SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs : broadcastExtend) {

                    if (!StringUtils.isEmpty(sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastContent())) {
                        sysScenicSpotBroadcast.setBroadcastContent(sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastContent());
                        break;
                    }
                }
                //景点当前人数
                Integer peopleCount = sysScenicBroadcastReadTimeMapper.getBroadcastRealTimePeople(sysScenicSpotBroadcast.getBroadcastId());

                Long bearPeople = sysScenicSpotBroadcast.getBearPeople();

                if (!StringUtils.isEmpty(peopleCount) && !StringUtils.isEmpty(bearPeople)) {

                    if (peopleCount == 0 && bearPeople == 0) {
                        sysScenicSpotBroadcast.setBroadcastSaturation("0");
                    } else {
                        Double d = ((double) peopleCount / bearPeople) * 100;
                        sysScenicSpotBroadcast.setBroadcastSaturation(d.toString());
                    }
                } else {
                    sysScenicSpotBroadcast.setBroadcastSaturation("0");
                }

                String[] split = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (split.length > 2) {
                    String[] split1 = sysScenicSpotBroadcast.getBroadcastGps().split("!");
                    split = split1[0].split(",");
                }
                String[] baiDusplit = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (!StringUtils.isEmpty(split) && split.length > 0) {

                    from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf((String) search.get("lng")), Double.valueOf((String) search.get("lat")));
                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                    sysScenicSpotBroadcast.setDistance(distanceOne);
                    //获取景区地址
//                    obj = DistanceUtil.getAddressInfoByLngAndLat(baiDusplit[0], baiDusplit[1]);
//                    String status = String.valueOf(obj.get("status"));
//                    if ("0".equals(status)) {
//                        String result = String.valueOf(obj.get("result"));
//                        JSONObject resultObj = JSONObject.parseObject(result);
////                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
//                        String formattedAddress = String.valueOf(resultObj.get("formatted_address"));
//
//                        sysScenicSpotBroadcast.setSpotBroadcastAddress(formattedAddress);
//                    }
                }
            }
        } else {

            Point2D.Double from = new Point2D.Double();
            Point2D.Double to = new Point2D.Double();
            search.put("sort", sort);
            PageHelper.startPage(pageNum, pageSize);
            list = sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);
            JSONObject obj = new JSONObject();
            for (SysScenicSpotBroadcast sysScenicSpotBroadcast : list) {


                //查询景区播报资源
                List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastExtend = sysScenicSpotBroadcastExtendMapper.getBroadcastId(sysScenicSpotBroadcast.getBroadcastId());
                for (SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs : broadcastExtend) {

                    if (!StringUtils.isEmpty(sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastContent())) {
                        sysScenicSpotBroadcast.setBroadcastContent(sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastContent());
                        break;
                    }
                }


                String[] split = sysScenicSpotBroadcast.getBroadcastGps().split(",");
                String[] baiDusplit = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (!StringUtils.isEmpty(split) && split.length > 0) {
//                    from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
//                    to = new Point2D.Double(Double.valueOf((String) search.get("lng")),Double.valueOf((String) search.get("lat")));
//                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
////                double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf((String)search.get("lat")),Double.valueOf((String)search.get("lng")), Double.valueOf(split[1]), Double.valueOf(split[0]));
//                    sysScenicSpotBroadcast.setDistance(distanceOne);

                    //获取景区地址
//                    obj = DistanceUtil.getAddressInfoByLngAndLat(baiDusplit[0], baiDusplit[1]);
//                    String status = String.valueOf(obj.get("status"));
//                    if ("0".equals(status)) {
//                        String result = String.valueOf(obj.get("result"));
//                        JSONObject resultObj = JSONObject.parseObject(result);
////                String addressComponent = String.valueOf(resultObj.get("addressComponent"));
//                        String formattedAddress = String.valueOf(resultObj.get("formatted_address"));
//                        sysScenicSpotBroadcast.setSpotBroadcastAddress(formattedAddress);
//                    }
                }

                //景点当前人数
                Integer peopleCount = sysScenicBroadcastReadTimeMapper.getBroadcastRealTimePeople(sysScenicSpotBroadcast.getBroadcastId());

                Long bearPeople = sysScenicSpotBroadcast.getBearPeople();

                if (!StringUtils.isEmpty(peopleCount) && !StringUtils.isEmpty(bearPeople)) {

                    if (peopleCount == 0 && bearPeople == 0) {
                        sysScenicSpotBroadcast.setBroadcastSaturation("0");
                    } else {
                        Double d = ((double) peopleCount / bearPeople) * 100;
                        sysScenicSpotBroadcast.setBroadcastSaturation(d.toString());
                    }
                } else {
                    sysScenicSpotBroadcast.setBroadcastSaturation("0");
                }

            }
        }
        if (sort == 2) {
            Collections.sort(list, new Comparator<SysScenicSpotBroadcast>() {
                @Override
                public int compare(SysScenicSpotBroadcast o1, SysScenicSpotBroadcast o2) {
                    double i = o1.getDistance() - o2.getDistance();
                    if (i > 0) {
                        return 1;
                    } else if (i < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.getSpotBroadcastIdMinimum(search);
        if (StringUtils.isEmpty(sysScenicSpotBroadcast)) {
            map.put("defaultBroadcastId", 0);
        } else {
            map.put("defaultBroadcastId", sysScenicSpotBroadcast.getBroadcastId());
        }
        map.put("list", list);
        if (list.size() > 0) {
            PageInfo<SysScenicSpotBroadcast> pageInfo = new PageInfo<>(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setData(map);
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 景区热度加一
     *
     * @param id
     * @return
     */
    @Override
    public int addHotSpotBroadcast(Long id) {
        int i = sysScenicSpotBroadcastMapper.addHotSpotBroadcast(id);
        return i;
    }

    /**
     * 后台管理——景点搜索
     *
     * @param pageDTO
     * @return
     */
    @Override
    public PageDataResult getBroadcastList(PageDTO pageDTO) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        SysScenicSpotBroadcastExtend sysScenicSpotBroadcastExtend = new SysScenicSpotBroadcastExtend();
        search.put("startTime", pageDTO.getStartTime());
        search.put("endTime", pageDTO.getEndTime());
        search.put("spotId", pageDTO.getSpotId());
        search.put("broadcastName", pageDTO.getBroadcastName());
        search.put("spotName", pageDTO.getSpotName());
        if (StringUtils.isEmpty(pageDTO.getSort())) {
            search.put("sort", 6);
        }
//        search.put("type","1");
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        List<SysScenicSpotBroadcastWithBlogs> spotBroadcastListNew = sysScenicSpotBroadcastMapper.getSpotBroadcastListNewT(search);
        for (SysScenicSpotBroadcastWithBlogs sysScenicSpotBroadcastWithBlogs : spotBroadcastListNew) {

            sysScenicSpotBroadcastExtend = sysScenicSpotBroadcastExtendMapper.getBroadcastIdAndTypeByExtend(sysScenicSpotBroadcastWithBlogs.getBroadcastId(), "2");
            if (!StringUtils.isEmpty(sysScenicSpotBroadcastExtend)) {
                sysScenicSpotBroadcastWithBlogs.setVideoUrl(sysScenicSpotBroadcastExtend.getMediaResourceUrl());
            }
            sysScenicSpotBroadcastExtend = sysScenicSpotBroadcastExtendMapper.getBroadcastIdAndTypeByExtend(sysScenicSpotBroadcastWithBlogs.getBroadcastId(), "3");
            if (!StringUtils.isEmpty(sysScenicSpotBroadcastExtend)) {
                sysScenicSpotBroadcastWithBlogs.setAudioUrl(sysScenicSpotBroadcastExtend.getMediaResourceUrl());
            }
            Integer fabulousCount = sysUserScenicFabulousCollectionMapper.getSpotIdFabulousCount(sysScenicSpotBroadcastWithBlogs.getBroadcastId());

            Integer collectionCount = sysUserScenicFabulousCollectionMapper.getSpotIdCollectionCount(sysScenicSpotBroadcastWithBlogs.getBroadcastId());

            Integer peopleStopCount = sysScenicSpotUserStopMapper.getSpotIdUserCount(sysScenicSpotBroadcastWithBlogs.getBroadcastId());

            Integer timeCount = sysScenicSpotUserStopMapper.getSpotIdTimeSum(sysScenicSpotBroadcastWithBlogs.getBroadcastId());
            if (!StringUtils.isEmpty(peopleStopCount) && peopleStopCount != 0 && !StringUtils.isEmpty(timeCount) && timeCount != 0) {
                double avgTime = (double) timeCount / (double) peopleStopCount;
                sysScenicSpotBroadcastWithBlogs.setAvgTime(avgTime);
            } else {
                sysScenicSpotBroadcastWithBlogs.setAvgTime(0d);
            }
            sysScenicSpotBroadcastWithBlogs.setLikeSearchCount(fabulousCount.longValue());
            sysScenicSpotBroadcastWithBlogs.setCollectionSearchCount(collectionCount.longValue());
        }

        if (spotBroadcastListNew.size() > 0) {
            PageInfo<SysScenicSpotBroadcastWithBlogs> pageInfo = new PageInfo<>(spotBroadcastListNew);
            pageDataResult.setData(spotBroadcastListNew);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 景点资源详情
     *
     * @param broadcastId
     * @return
     */
    @Override
    public PageDataResult getBroadcastDetails(String broadcastId) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("broadcastId", broadcastId);
//        search.put("mediaType","3");
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastExtendWithBLOBsList = sysScenicSpotBroadcastMapper.getBroadcastDetails(search);
        if (broadcastExtendWithBLOBsList.size() != 0) {
            PageInfo<SysScenicSpotBroadcastExtendWithBLOBs> pageInfo = new PageInfo<>(broadcastExtendWithBLOBsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;

    }

    /**
     * 后台管理——添加景点
     *
     * @param broadcast
     * @return
     */

    @Override
    public int addBroadcast(SysScenicSpotBroadcast broadcast) {
        broadcast.setBroadcastId(IdUtils.getSeqId());
        broadcast.setUpdateDate(DateUtil.currentDateTime());
        broadcast.setPinYinName(Tinypinyin.tinypinyin(broadcast.getBroadcastName()));
        broadcast.setCreateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastMapper.insertSelective(broadcast);
        return i;
    }

    /**
     * 后台管理——删除景点
     *
     * @param broadcastId
     * @param scenicSpotId
     * @return
     */
    @Override
    public int delBroadcast(Long broadcastId, Long scenicSpotId) {

        int i = sysScenicSpotBroadcastMapper.deleteByPrimaryKey(broadcastId);
        int j = sysUserScenicFabulousCollectionMapper.deleteByBroadcastId(broadcastId);
        return i;

    }

    /**
     * 后台管理——修改景点
     *
     * @param broadcast
     * @return
     */
    @Override
    public int editBroadcast(SysScenicSpotBroadcast broadcast) {
        broadcast.setPinYinName(Tinypinyin.tinypinyin(broadcast.getBroadcastName()));
        broadcast.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastMapper.updateByPrimaryKeySelective(broadcast);
        return i;
    }

    /**
     * 景点内容新增（文字）
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int addBroadcastContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = spotBroadcastPatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048) {
                        return 3;
                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //压缩上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(file, spotBroadcastPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                Map<String, Object> search = new HashMap<>();
                search.put("broadcastId", sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
                search.put("mediaType", "1");
                List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);

                if (broadcastBySearch.size() == 0) {
                    sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
                    sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
                    sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
                    return i;
                } else {
                    SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBsNew = broadcastBySearch.get(0);
                    sysScenicSpotBroadcastExtendWithBLOBsNew.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
                    sysScenicSpotBroadcastExtendWithBLOBsNew.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBsNew);
                    return i;
                }
            } else {
                return 2;
            }
        } else {
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
            sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl("");
            sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
            return i;
        }
    }

    /**
     * 景点播报内容新增
     *
     * @param
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int addBroadcastContentExtendAudio(SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
//        if (!file.isEmpty()) {
//            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
//            if (type.equals(".mp3") || type.equals(".wav")) {
//                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
//                String path = spotBroadcastAudioPaht + filename;// 存放位置
//                File destFile = new File(path);
//                try {
//                    // 限制大小
////                    long size = file.getSize() / 1024;//kb
////                    if (size >= 2048) {
////                        return 3;
////                    }
//                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
//                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//
//                    //压缩上传
////                    CompressUtils.compress(file.getInputStream(),destFile,10000);
//
//                    //阿里OSS文件存储_图片上传
//                      String upload = fileUploadUtil.upload(file, spotBroadcastAudioPaht.substring(1) + filename);
//                      System.out.println(upload);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }//复制文件到指定目录
//                    sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
//                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(spotBroadcastAudioUrl + filename);
//                    sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
//                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
//                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
//                    int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
//                    return i;
//
//            }else{
//                return 2;
//            }
//        }else{
//            return 0;
//        }

        sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
//        sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(spotBroadcastAudioUrl + filename);
        sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
        int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
        return i;


    }

    /**
     * 上传景点图片(未使用)
     *
     * @param file
     * @param
     * @return
     */
    @Override
    public int addBroadcastPicture(MultipartFile file, Long broadcastId) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = spotBroadcastPatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048) {
                        return 3;
                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //压缩上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(file, spotBroadcastPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                Map<String, Object> search = new HashMap<>();
                search.put("broadcastId", broadcastId);
                search.put("mediaType", 1);
                List<SysScenicSpotBroadcastExtendWithBLOBs> list = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);
                if (StringUtils.isEmpty(list)) {
                    SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs = new SysScenicSpotBroadcastExtendWithBLOBs();
                    sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                    sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastId(broadcastId);
                    sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
                    sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
                    return i;

                } else {
                    SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs = list.get(0);

                    sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);
                    return i;
                }
            } else {
                return 2;
            }
        } else {
            return 0;
        }

    }

    /**
     * 景点内容修改（图片文字）
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int exitBroadcastContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = spotBroadcastPatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048) {
                        return 3;
                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //压缩上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(file, spotBroadcastPatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                Map<String, Object> search = new HashMap<>();
                search.put("broadcastId", sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
                search.put("mediaType", "1");
                List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);

                if (StringUtils.isEmpty(broadcastBySearch)) {
                    sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
                    sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
                    sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
                    return 1;
                } else {
                    SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBsNew = broadcastBySearch.get(0);
                    sysScenicSpotBroadcastExtendWithBLOBsNew.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
                    sysScenicSpotBroadcastExtendWithBLOBsNew.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBsNew);
                    return i;
                }
            } else {
                return 2;
            }
        } else {
//            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
//            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
//            sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl("");
//            sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
//            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());

            int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);
            return i;
        }

    }

    /**
     * 景点播报内容修改
     *
     * @param
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int exitBroadcastContentExtendAudio(SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {

//        if (!file.isEmpty()) {
//            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
//            if (type.equals(".mp3") || type.equals(".wav")) {
//                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
//                String path = spotBroadcastAudioPaht + filename;// 存放位置
//                File destFile = new File(path);
//                try {
//
//                    // 限制大小
//                    long size = file.getSize() / 1024;//kb
//                    if (size >= 2048) {
//                        return 3;
//                    }
//                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
//                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//
//                    //压缩上传
////                    CompressUtils.compress(file.getInputStream(),destFile,10000);
//
//                    //阿里OSS文件存储_图片上传
//                    //  String upload = fileUploadUtil.upload(file, GET_BROADCAST_PIC_PAHT.substring(1) + filename);
//                    //  System.out.println(upload);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }//复制文件到指定目录
//                Map<String, Object> search = new HashMap<>();
//                search.put("broadcastId",sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
//                search.put("mediaType","3");
//                List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);
//
//                if (StringUtils.isEmpty(broadcastBySearch)){
//                    sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
//                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
//                    sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(spotBroadcastAudioUrl + filename);
//                    sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
//                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
//                    int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
//                    return 1;
//                }else{
////                    SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBsNew = broadcastBySearch.get(0);
////                    sysScenicSpotBroadcastExtendWithBLOBsNew.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
////                    sysScenicSpotBroadcastExtendWithBLOBsNew.setUpdateDate(DateUtil.currentDateTime());
//                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(spotBroadcastPatheGetPicUrl + filename);
//                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
//                    int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);
//                    return i;
//                }
//            }else{
//                return 2;
//            }
//        }else{
////            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
////            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
////            sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl("");
////            sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
////            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
//            int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);
//            return i;
//        }
        sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);
        return i;
    }

    @Override
    public int delBroadcastContentExtendAudio(String broadcastResId) {

        int i = sysScenicSpotBroadcastExtendMapper.deleteByPrimaryKey(Long.parseLong(broadcastResId));
        return i;
    }

    /**
     * 景点导出
     *
     * @param search
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcastExcel> uploadExcelSpotBroadcast(Map<String, Object> search) {

        List<SysScenicSpotBroadcastExcel> spotBroadcastListNew = sysScenicSpotBroadcastMapper.uploadExcelSpotBroadcast(search);

        return spotBroadcastListNew;

    }

    /**
     * 根据景点id，查询景点详情
     *
     * @param broadcastId
     * @return
     */
    @Override
    public SysScenicSpotBroadcast getSpotBroadcastId(Long broadcastId) {

        SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(broadcastId);
        return sysScenicSpotBroadcast;
    }

    /**
     * 景点管理导入（添加）
     *
     * @param sysScenicSpotBroadcast
     * @return
     */
    @Override
    public int importScenicSpotBroadcast(SysScenicSpotBroadcast sysScenicSpotBroadcast) {

        int i = sysScenicSpotBroadcastMapper.insertSelective(sysScenicSpotBroadcast);
        return i;

    }

    /**
     * 景点管理导入（修改）
     *
     * @param sysScenicSpotBroadcast
     * @return
     */
    @Override
    public int editImportScenicSpot(SysScenicSpotBroadcast sysScenicSpotBroadcast) {

        int i = sysScenicSpotBroadcastMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcast);
        return i;
    }

    @Override
    public SysScenicSpotBroadcast getSpotBroadcastName(String broadcastName) {

        SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.getSpotBroadcastName(broadcastName);
        return sysScenicSpotBroadcast;
    }

    /**
     * 根据景区id和景点名称查询景点
     *
     * @param scenicSpotId
     * @param broadcastName
     * @return
     */
    @Override
    public SysScenicSpotBroadcast getSpotIdAndBroadcastName(Long scenicSpotId, String broadcastName) {
        SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.getSpotIdAndBroadcastName(scenicSpotId, broadcastName);
        return sysScenicSpotBroadcast;
    }

    /**
     * 定时任务中从后台管理获取景点，添加
     *
     * @param sysScenicSpotBroadcast
     * @return
     */
    @Override
    public int insert(SysScenicSpotBroadcast sysScenicSpotBroadcast) {
        sysScenicSpotBroadcast.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotBroadcast.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastMapper.insertSelective(sysScenicSpotBroadcast);
        return i;
    }

    /**
     * 首页景点查询
     *
     * @param search
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcast> getAllSysBroadcast(Map<String, Object> search) {
        HashMap<String, Object> searchN = new HashMap<>();
        List<SysScenicSpotBroadcast> broadcastList = new ArrayList<>();
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();
        searchN.put("fName", search.get("cityName"));
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)) {
            List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.getBindingIdBySpotList(sysScenicSpotBinding.getScenicSpotFid());
            for (SysScenicSpotBinding scenicSpotBinding : list) {
                List<SysScenicSpotBroadcast> content = sysScenicSpotBroadcastMapper.getSpotIdAndBroadcastNameList(scenicSpotBinding.getScenicSpotFid(), (String) search.get("content"));
                broadcastList.addAll(content);
            }


            for (SysScenicSpotBroadcast sysScenicSpotBroadcast : broadcastList) {

                String[] split = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (split.length > 2) {
                    String[] split1 = sysScenicSpotBroadcast.getBroadcastGps().split("!");
                    split = split1[0].split(",");
                }
                String[] baiDusplit = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (!StringUtils.isEmpty(split) && split.length > 0) {

                    from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf((String) search.get("lng")), Double.valueOf((String) search.get("lat")));
                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                    sysScenicSpotBroadcast.setDistance(distanceOne);
                }
            }
            return broadcastList;

        } else {

            broadcastList = sysScenicSpotBroadcastMapper.getSpotBroadcastNameList((String) search.get("content"));
            for (SysScenicSpotBroadcast sysScenicSpotBroadcast : broadcastList) {

                String[] split = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (split.length > 2) {
                    String[] split1 = sysScenicSpotBroadcast.getBroadcastGps().split("!");
                    split = split1[0].split(",");
                }
                String[] baiDusplit = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (!StringUtils.isEmpty(split) && split.length > 0 && !StringUtils.isEmpty(search.get("lng"))) {

                    from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf((String) search.get("lng")), Double.valueOf((String) search.get("lat")));
                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                    sysScenicSpotBroadcast.setDistance(distanceOne);
                }
            }
            return broadcastList;
        }
    }

    /**
     * 首页景点查询
     *
     * @param search
     * @return
     */
    @Override
    public PageDataResult getAllSysBroadcastPage(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        HashMap<String, Object> searchN = new HashMap<>();
        List<SysScenicSpotBroadcast> broadcastList = new ArrayList<>();
        Point2D.Double from = new Point2D.Double();
        Point2D.Double to = new Point2D.Double();
        searchN.put("fName", search.get("cityName"));
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
        if (!StringUtils.isEmpty(sysScenicSpotBinding)) {
            List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.getBindingIdBySpotList(sysScenicSpotBinding.getScenicSpotFid());
            for (SysScenicSpotBinding scenicSpotBinding : list) {
                List<SysScenicSpotBroadcast> content = sysScenicSpotBroadcastMapper.getSpotIdAndBroadcastNameList(scenicSpotBinding.getScenicSpotFid(), (String) search.get("content"));
                broadcastList.addAll(content);
            }

            for (SysScenicSpotBroadcast sysScenicSpotBroadcast : broadcastList) {

                String[] split = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (split.length > 2) {
                    String[] split1 = sysScenicSpotBroadcast.getBroadcastGps().split("!");
                    split = split1[0].split(",");
                }
                String[] baiDusplit = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (!StringUtils.isEmpty(split) && split.length > 0) {

                    from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf((String) search.get("lng")), Double.valueOf((String) search.get("lat")));
                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                    sysScenicSpotBroadcast.setDistance(distanceOne);
                }
            }


        } else {

            broadcastList = sysScenicSpotBroadcastMapper.getSpotBroadcastNameList((String) search.get("content"));
            for (SysScenicSpotBroadcast sysScenicSpotBroadcast : broadcastList) {

                String[] split = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (split.length > 2) {
                    String[] split1 = sysScenicSpotBroadcast.getBroadcastGps().split("!");
                    split = split1[0].split(",");
                }
                String[] baiDusplit = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split(",");
                if (!StringUtils.isEmpty(split) && split.length > 0 && !StringUtils.isEmpty(search.get("lng"))) {

                    from = new Point2D.Double(Double.valueOf(split[0]), Double.valueOf(split[1]));
                    to = new Point2D.Double(Double.valueOf((String) search.get("lng")), Double.valueOf((String) search.get("lat")));
                    double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                    sysScenicSpotBroadcast.setDistance(distanceOne);
                }
            }
        }


        List<SysScenicSpotBroadcast> subList = broadcastList.stream().skip(((int) search.get("pageNum") - 1) * (int) search.get("pageSize")).limit((int) search.get("pageSize")).
                collect(Collectors.toList());

        pageDataResult.setTotals(broadcastList.size());
        pageDataResult.setData(subList);
        pageDataResult.setCode(200);
        return pageDataResult;

    }

    /**
     * 添加景点音频
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int addBroadcastAudio(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp3") || type.equals(".wav")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = spotBroadcastAudioPaht + filename;// 存放位置
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
                    String upload = fileUploadUtil.upload(file, spotBroadcastAudioPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                Map<String, Object> search = new HashMap<>();
                search.put("broadcastId", sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
                search.put("mediaType", "3");
                List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);
                if (broadcastBySearch.size() > 0) {
                    SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs1 = broadcastBySearch.get(0);
                    sysScenicSpotBroadcastExtendWithBLOBs1.setMediaResourceUrl(spotBroadcastAudioUrl + filename);
                    sysScenicSpotBroadcastExtendWithBLOBs1.setUpdateDate(DateUtil.currentDateTime());
                    int j = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeyWithBLOBs(sysScenicSpotBroadcastExtendWithBLOBs1);
                    return j;
                }

                sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(spotBroadcastAudioUrl + filename);
                sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
                int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
                return i;

            } else {
                return 2;
            }
        } else {
            return 0;
        }

    }

    /**
     * 修改景点音频
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int editBroadcastAudio(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp3") || type.equals(".wav")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = spotBroadcastAudioPaht + filename;// 存放位置
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
                String upload = fileUploadUtil.upload(file, spotBroadcastAudioPaht.substring(1) + filename);
                //  System.out.println(upload);

            } catch (Exception e) {
                e.printStackTrace();
            }//复制文件到指定目录
            Map<String, Object> search = new HashMap<>();
            search.put("broadcastId", sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
            search.put("mediaType", "3");
            List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);

            if (StringUtils.isEmpty(broadcastBySearch)) {
                sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
                sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(spotBroadcastAudioUrl + filename);
                sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
                return 1;
            } else {
//                    SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBsNew = broadcastBySearch.get(0);
//                    sysScenicSpotBroadcastExtendWithBLOBsNew.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
//                    sysScenicSpotBroadcastExtendWithBLOBsNew.setUpdateDate(DateUtil.currentDateTime());
                broadcastBySearch.get(0).setMediaResourceUrl(spotBroadcastPatheGetPicUrl + filename);
                broadcastBySearch.get(0).setUpdateDate(DateUtil.currentDateTime());
                int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(broadcastBySearch.get(0));
                return i;
            }
        } else {
            return 2;
        }
    }

    /**
     * 景点视频上传
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int addBroadcastVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp4") || type.equals("flv") || type.equals("avi") || type.equals("rm") || type.equals("rmvb") || type.equals("wmv")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = spotBroadcastVodioPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                String upload = fileUploadUtil.upload(file, GET_BROADCAST_VODIO_PAHT.substring(1) + filename);
//                System.out.println(upload);

                //阿里OSS文件存储_图片上传
                String upload = fileUploadUtil.upload(file, spotBroadcastAudioPaht.substring(1) + filename);


            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录

            Map<String, Object> search = new HashMap<>();
            search.put("broadcastId", sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
            search.put("mediaType", 2);
            List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);
            if (broadcastBySearch.size() > 0) {
                SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs1 = broadcastBySearch.get(0);
                sysScenicSpotBroadcastExtendWithBLOBs1.setMediaResourceUrl(spotBroadcastVodioUrl + filename);
                sysScenicSpotBroadcastExtendWithBLOBs1.setUpdateDate(DateUtil.currentDateTime());
                int j = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeyWithBLOBs(sysScenicSpotBroadcastExtendWithBLOBs1);
                return j;

            }
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("2");
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(spotBroadcastVodioUrl + filename);
            sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
            return i;
        } else {
            return 2;
        }

    }

    /**
     * 景点视频修改
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int editBroadcastVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp4") || type.equals("flv") || type.equals("avi") || type.equals("rm") || type.equals("rmvb") || type.equals("wmv")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = spotBroadcastAudioPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, GET_BROADCAST_VODIO_PAHT.substring(1) + filename);
//                    System.out.println(upload);

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(spotBroadcastAudioUrl + filename);
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("2");
            sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl("");
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastContent("");
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);

            Map<String, Object> search = new HashMap<>();
            search.put("broadcastId", sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
            search.put("mediaType", "2");
            List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);
            if (StringUtils.isEmpty(broadcastBySearch)) {
                sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("2");
                sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(spotBroadcastAudioUrl + filename);
                sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
                return 1;
            } else {
//                    SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBsNew = broadcastBySearch.get(0);
//                    sysScenicSpotBroadcastExtendWithBLOBsNew.setPictureUrl(spotBroadcastPatheGetPicUrl + filename);
//                    sysScenicSpotBroadcastExtendWithBLOBsNew.setUpdateDate(DateUtil.currentDateTime());
                broadcastBySearch.get(0).setMediaResourceUrl(spotBroadcastPatheGetPicUrl + filename);
                broadcastBySearch.get(0).setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(broadcastBySearch.get(0));
                return i;
            }
        } else {
            return 2;
        }

    }

    /**
     * 删除景点音视频
     *
     * @param broadcastId
     * @param type
     * @return
     */
    @Override
    public int delBroadcastType(Long broadcastId, String type) {

        Map<String, Object> search = new HashMap<>();
        search.put("broadcastId", broadcastId);
        search.put("mediaType", type);
        int i = 0;
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);
        if (broadcastBySearch.size() > 0) {
            for (SysScenicSpotBroadcastExtendWithBLOBs bySearch : broadcastBySearch) {
                i = sysScenicSpotBroadcastExtendMapper.deleteByPrimaryKey(bySearch.getBroadcastResId());
            }
        }
        return i;
    }

    /**
     * 根据景区id，获取景点下拉选
     *
     * @param spotId
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcast> getSpotIdByBroadcastDropDown(String spotId) {

        Map<String, Object> search = new HashMap<>();

        List<SysScenicSpotBroadcast> list = sysScenicSpotBroadcastMapper.getSpotIdByBroadcastDropDown(spotId);

        return list;
    }


    @Override
    public void upText() {

        Map<String, Object> search = new HashMap<>();
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastBySearch = sysScenicSpotBroadcastExtendMapper.getBroadcastBySearch(search);

        for (SysScenicSpotBroadcastExtendWithBLOBs bySearch : broadcastBySearch) {

            String pictureUrl = bySearch.getPictureUrl();
            if (!StringUtils.isEmpty(pictureUrl)) {

                String substring = pictureUrl.substring(0, 1);
                if ("s".equals(substring)) {
                    String substring1 = pictureUrl.substring(7, pictureUrl.length());
                    bySearch.setPictureUrl(substring1);
                    int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(bySearch);
                    System.out.println(i);
                }

            }

        }


    }

    @Override
    public void editspotpicture(Long spotId) {

        List<SysScenicSpotBroadcastExtendWithBLOBs> list = sysScenicSpotBroadcastExtendMapper.qiliangxiugaichaxun(spotId);

        if (list.size() > 0) {
            for (SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs : list) {
                String pictureUrl = sysScenicSpotBroadcastExtendWithBLOBs.getPictureUrl();
                if (!StringUtils.isEmpty(pictureUrl)) {
                    String url = "broadcast/upload_pic/33010523072523.jpg";
                    System.out.println(sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastContent());
                    sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(url);

                    int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeyWithBLOBs(sysScenicSpotBroadcastExtendWithBLOBs);
                }
            }

        } else {
            System.out.println("无景点！！");
        }


    }


}