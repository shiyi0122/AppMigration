package com.jxzy.AppMigration.NavigationApp.Service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotService;
import com.jxzy.AppMigration.NavigationApp.dao.*;
import com.jxzy.AppMigration.NavigationApp.entity.*;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotExcel;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotFilesExcel;
import com.jxzy.AppMigration.NavigationApp.entity.dto.PageDTO;
//import com.jxzy.AppMigration.NavigationApp.util.DistanceUtil;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class SysScenicSpotServiceImpl implements SysScenicSpotService {

    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;
    @Autowired
    private SysScenicSpotBindingMapper sysScenicSpotBindingMapper;
    @Autowired
    private SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;
    @Autowired
    private SysUserDistrictFabulousCollectionMapper sysUserDistrictFabulousCollectionMapper;
    @Autowired
    private SysScenicSpotUserStopMapper sysScenicSpotUserStopMapper;
    @Autowired
    private SysScenicSpotImgMapper sysScenicSpotImgMapper;
    @Autowired
    private SysScenicSpotBannerMapper sysScenicSpotBannerMapper;
    @Autowired
    private SysScenicSpotHeatMapper sysScenicSpotHeatMapper;
    @Autowired
    private SysScenicSpotGpsCoordinateMapper sysScenicSpotGpsCoordinateMapper;

    @Autowired
    private SysSpotDynamicMapper sysSpotDynamicMapper;

    @Autowired
    private SysScenicSpotAdmissionFeeMapper sysScenicSpotAdmissionFeeMapper;

    @Autowired
    private SysScenicSpotAudioMapper sysScenicSpotAudioMapper;

    @Value("${scenicSpotImgPatheGetPicPaht}")
    private String SCENIC_SPOT_IMG_PATHE_GET_PIC_PAHT;

    @Value("${scenicSpotImgPatheGetPicUrl}")
    private String SCENIC_SPOT_IMG_PATHE_GET_PIC_URL;


    @Value("${bannerPatheGetPicPaht}")
    private String GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH;

    @Value("${bannerPatheGetPicUrl}")
    private String GET_SYS_SCENIC_SPOT_BANNER_IMG_URL;

    @Value("${scenicSpotAudioPatheGetAudioPaht}")
    private String scenicSpotAudioPatheGetAudioPaht;

    @Value("${scenicSpotAudioPatheGetAudioUrl}")
    private String scenicSpotAudioPatheGetAudioUrl;

    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    PolygonOutlineUtil polygonOutlineUtil;

    @Override
    public List<SysScenicSpot> queryScenicSpotList() {
        return sysScenicSpotMapper.queryScenicSpotList();
    }

    /**
     * 景区搜索
     * @param pageDTO
     * @return
     */
    @Override
    public PageDataResult searchSpot(PageDTO pageDTO) {
        PageDataResult pageDataResult = new PageDataResult();

        if (!StringUtils.isEmpty(pageDTO.getLng()) && !StringUtils.isEmpty(pageDTO.getLat())){
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();
            String lng = pageDTO.getLng();
            String lat = pageDTO.getLat();
            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf(lng),Double.valueOf(lat));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            HashMap<String, Object> search = new HashMap<>();
            if (!StringUtils.isEmpty(pageDTO.getSpotName())){
                search.put("spotName",pageDTO.getSpotName());
            }
            if (!StringUtils.isEmpty(pageDTO.getCityId())){
                search.put("cityId",pageDTO.getCityId());
            }
            if (!StringUtils.isEmpty(pageDTO.getSort())){
                search.put("sort",pageDTO.getSort());
            }
            PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
            List<SysScenicSpot> spotList = sysScenicSpotMapper.selectBySearchNew(search);
            for (SysScenicSpot sysScenicSpot : spotList) {
                if (StringUtils.isEmpty(sysScenicSpot.getCoordinateRange())){
                    continue;
                }
                String[] split = sysScenicSpot.getCoordinateRange().split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lng),Double.valueOf(lat),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysScenicSpot.setDistance(distanceOne);
            }
            //判断是否是根据距离排序
            if (pageDTO.getSort()==2){
                Collections.sort(spotList, new Comparator<SysScenicSpot>() {
                    @Override
                    public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                        if (StringUtils.isEmpty(o1.getDistance())){
                            o1.setDistance(0d);
                        }
                        if (StringUtils.isEmpty(o2.getDistance())){
                            o2.setDistance(0d);
                        }
                        Double i = o1.getDistance() - o2.getDistance();
                        return  i.intValue() ;
                    }
                });
            }
            if (spotList.size()>0){
                PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(spotList);
                pageDataResult.setData(pageInfo.getList());
                pageDataResult.setTotals((int)pageInfo.getTotal());
            }
            return pageDataResult;
        }else{

            HashMap<String, Object> search = new HashMap<>();
            search.put("spotName",pageDTO.getSpotName());
//            search.put("cityId",pageDTO.getCityId());
            search.put("sort",pageDTO.getSort());
            PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
            List<SysScenicSpot> spotList = sysScenicSpotMapper.getScenicSpotAll(search);
//            for (SysScenicSpot sysScenicSpot : spotList) {
//                if (StringUtils.isEmpty(sysScenicSpot.getCoordinateRange())){
//                    continue;
//                }
//                String[] split = sysScenicSpot.getCoordinateRange().split(",");
//                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
//                to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
//                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
////            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lng),Double.valueOf(lat),Double.valueOf(split[0]),Double.valueOf(split[1]));
//                sysScenicSpot.setDistance(distanceOne);
//            }

            if (pageDTO.getSort()==2){
                Collections.sort(spotList, new Comparator<SysScenicSpot>() {
                    @Override
                    public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                        if (StringUtils.isEmpty(o2.getDistance()) && StringUtils.isEmpty(o1.getDistance())){
                            return 0;
                        }
                        Double i = o2.getDistance() - o1.getDistance();
                        return  i.intValue() ;
                    }
                });
            }
            if (spotList.size()>0){
                PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(spotList);
                pageDataResult.setData(pageInfo.getList());
                pageDataResult.setTotals((int)pageInfo.getTotal());
            }
            return pageDataResult;


        }

    }

    /**
     * 获取景区详情
     * @param spotId
     * @return
     */
    @Override
    public SysScenicSpot  spotDetails(String spotId, String lat, String lng) {
        SysScenicSpot scenicSpot = sysScenicSpotMapper.spotDetails(spotId);

        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(scenicSpot.getScenicSpotId());
        if (!StringUtils.isEmpty( sysScenicSpotBinding.getScenicSpotSid())){
            SysScenicSpotBinding sysScenicSpotBinding1 = sysScenicSpotBindingMapper.selectByPrimaryKey(sysScenicSpotBinding.getScenicSpotSid());
            scenicSpot.setCityName(sysScenicSpotBinding1.getScenicSpotFname());
        }

        List<SysScenicSpotAudio> sysScenicSpotAudios = sysScenicSpotAudioMapper.selectBySpotId(scenicSpot.getScenicSpotId());
        if (sysScenicSpotAudios.size() > 0){
            scenicSpot.setScneicSpotAudioUrl(sysScenicSpotAudios.get(0).getScneicSpotAudioUrl());
        }

        if (!StringUtils.isEmpty(lng) && !StringUtils.isEmpty(lat)){
            Point2D.Double from=new Point2D.Double();
            Point2D.Double to=new Point2D.Double();
            double[] doubles = LngLonUtil.bd09_To_gps84( Double.valueOf(lng),Double.valueOf(lat));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            if (!StringUtils.isEmpty(scenicSpot.getCoordinateRange())) {

                String[] split = scenicSpot.getCoordinateRange().split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//              double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat), Double.valueOf(lng), Double.valueOf(split[0]), Double.valueOf(split[1]));
                scenicSpot.setDistance(distanceOne);
            }else{
                scenicSpot.setDistance(0d);
            }
        }
        return scenicSpot;
    }

    /**
     * 根据景区ID查询景区数据
     * @param: parseLong
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    public SysScenicSpot queryScenicSpotData(long scenicSpotId) {
        return sysScenicSpotMapper.selectByPrimaryKey(scenicSpotId);
    }

    /**
     * 更新景区热度
     * @param: scenicSpot
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/29 0029
     */
    public int updateScenicSpotHeat(SysScenicSpot scenicSpot) {
        long heat = scenicSpot.getHeat() + 1;//自增热度
        scenicSpot.setHeat(heat);
        return sysScenicSpotMapper.updateByPrimaryKeySelective(scenicSpot);
    }

    /**
     * 查询景区排行
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    public List<SysScenicSpot> queryScenicSpotRankingList(int pageNum, int pageSize, Map<String, Object> search) {


        if (!StringUtils.isEmpty(search.get("cityName"))){
            SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname((String) search.get("cityName"));
            if (!StringUtils.isEmpty(sysScenicSpotBinding)){
                if (sysScenicSpotBinding.getScenicSpotType() == 4){
                    search.put("cityId",sysScenicSpotBinding.getScenicSpotPid());
                }else {
                    search.put("cityId",sysScenicSpotBinding.getScenicSpotFid());
                }
            }
        }

        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpot> spotList = sysScenicSpotMapper.queryScenicSpotRankingList(search);

        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();
        String lng = (String) search.get("lng");
        String lat = (String) search.get("lat");
        if (StringUtils.isEmpty(lat) && StringUtils.isEmpty(lng)){

            for (SysScenicSpot sysScenicSpot : spotList) {

                sysScenicSpot.setDistance(-1d);

                SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(sysScenicSpot.getScenicSpotId());
                if (!StringUtils.isEmpty(scenicSpotImgByScenicSpotId)){
                    sysScenicSpot.setScenicSpotImgUrl(scenicSpotImgByScenicSpotId.getScneicSpotImgUrl());
                }
                //景点讲解
                Integer spotCount = sysScenicSpotBroadcastMapper.selectSpotByCount(sysScenicSpot.getScenicSpotId());
                sysScenicSpot.setScenicSpotBroadcastCount(spotCount);

                //获取动态
                SysSpotDynamic sysSpotDynamic = sysSpotDynamicMapper.selectBySpotId(sysScenicSpot.getScenicSpotId());
                if (!StringUtils.isEmpty(sysSpotDynamic)){
                    sysScenicSpot.setDynamicTitle(sysSpotDynamic.getTitle());
                }

            }

        }else{

            double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf(lng),Double.valueOf(lat));
            double lng84 = doubles[0];
            double lat84 = doubles[1];
            for (SysScenicSpot sysScenicSpot : spotList) {

                if (StringUtils.isEmpty(sysScenicSpot.getCoordinateRange())){
                    continue;
                }


                String[] split = sysScenicSpot.getCoordinateRange().split(",");
                from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));
                to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
                double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lng),Double.valueOf(lat),Double.valueOf(split[0]),Double.valueOf(split[1]));
                sysScenicSpot.setDistance(distanceOne);

                SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(sysScenicSpot.getScenicSpotId());
                if (!StringUtils.isEmpty(scenicSpotImgByScenicSpotId)){
                    sysScenicSpot.setScenicSpotImgUrl(scenicSpotImgByScenicSpotId.getScneicSpotImgUrl());
                }
                //景点讲解
                Integer spotCount = sysScenicSpotBroadcastMapper.selectSpotByCount(sysScenicSpot.getScenicSpotId());
                sysScenicSpot.setScenicSpotBroadcastCount(spotCount);

                //获取动态
                SysSpotDynamic sysSpotDynamic = sysSpotDynamicMapper.selectBySpotId(sysScenicSpot.getScenicSpotId());
                if (!StringUtils.isEmpty(sysSpotDynamic)){
                    sysScenicSpot.setDynamicTitle(sysSpotDynamic.getTitle());
                }
            }
        }

        return spotList;
    }

    /**
     * 获取全国景区列表
     * 无当前坐标
     * zhang
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult currentCityAll(Integer sort,String content,String spotBindingName, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> search = new HashMap<>();
        List<SysScenicSpotAudio> sysScenicSpotAudios = new ArrayList<>();
        search.put("fName",spotBindingName);
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname(spotBindingName);
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("fid",sysScenicSpotBinding.getScenicSpotFid()) ;

        }
        search = new HashMap<>();
//        search.put("fid",sysScenicSpotBinding.getScenicSpotFid()) ;
        search.put("sort",sort);
        search.put("spotName",content);
//        List<SysScenicSpot> scenicSpots = sysScenicSpotBindingMapper.selectBySearch(search);
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpot> scenicSpots = sysScenicSpotMapper.getScenicSpotAll(search);
        //计算当前坐标与景点坐标之间的距离
        for (SysScenicSpot scenicSpot : scenicSpots) {
            scenicSpot.setDistance(-1d);
            SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(scenicSpot.getScenicSpotId());
            if (!StringUtils.isEmpty(scenicSpotImgByScenicSpotId)){
                scenicSpot.setScenicSpotImgUrl(scenicSpotImgByScenicSpotId.getScneicSpotImgUrl());
            }
            //计算景区中景区有多少景点讲解
            Integer spotCount = sysScenicSpotBroadcastMapper.selectSpotByCount(scenicSpot.getScenicSpotId());
            scenicSpot.setScenicSpotBroadcastCount(spotCount);
            String coordinateRange = scenicSpot.getCoordinateRange();
            if (StringUtils.isEmpty(coordinateRange)){
                continue;
            }
            //获取景区音频
            sysScenicSpotAudios = sysScenicSpotAudioMapper.selectBySpotId(scenicSpot.getScenicSpotId());
            if (sysScenicSpotAudios.size() > 0 ){
                scenicSpot.setScneicSpotAudioUrl(sysScenicSpotAudios.get(0).getScneicSpotAudioUrl());
            }

        }

        //判断是否是根据距离排序
        if (sort==3){
            Collections.sort(scenicSpots, new Comparator<SysScenicSpot>() {
                @Override
                public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                    Double i = o2.getDistance() - o1.getDistance();
                    return  i.intValue() ;
                }
            });
        }
        map.put("scenicSpot",scenicSpots);
        if (scenicSpots.size()>0){
            PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(scenicSpots);
            //pageDataResult.setData(pageInfo.getList());
            pageDataResult.setDataNew(map);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理系统中景区列表
     * @param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult adminSysScenicSpotList(Integer pageNum, Integer pageSize,Map<String,Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotWithBlogs> list = sysScenicSpotMapper.adminSysScenicSpotList(search);
        for (SysScenicSpotWithBlogs sysScenicSpotWithBlogs : list) {

//
            String workTime = sysScenicSpotWithBlogs.getWorkTime();
            String closingTime = sysScenicSpotWithBlogs.getClosingTime();
            int i = cn.hutool.core.date.DateUtil.thisHour(true);
            if (!StringUtils.isEmpty(workTime) && !StringUtils.isEmpty(closingTime)){
                Integer work = Integer.parseInt(workTime.substring(0, 2));
                Integer closing = Integer.parseInt(closingTime.substring(0, 2));
                if (work <= i && i <= closing){
                    sysScenicSpotWithBlogs.setBusinessStatus("1");
                }else if (work > i && i > closing ){
                    sysScenicSpotWithBlogs.setBusinessStatus("2");
                }else if (work > i && i < closing){
                    sysScenicSpotWithBlogs.setBusinessStatus("2");
                }else if (work < i && i > closing){
                    sysScenicSpotWithBlogs.setBusinessStatus("2");
                }
            }

        }
        if (list.size()>0){
            PageInfo<SysScenicSpotWithBlogs> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCount(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理——添加
     * @param sysScenicSpot
     * @return
     */
    @Override
    public int adminAddSysScenicSpot(SysScenicSpot sysScenicSpot) {
//        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
//        String filename = null;
//        if (type.equals(".png") || type.equals(".jpg")){
//            filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
//            String path = SCENIC_SPOT_IMG_PATHE_GET_PIC_PAHT + filename;// 存放位置
//            File destFile = new File(path);
//            try {
//                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        sysScenicSpot.setScenicSpotId(IdUtils.getSeqId());
        sysScenicSpot.setPinYingName(Tinypinyin.tinypinyin(sysScenicSpot.getScenicSpotName()));
        sysScenicSpot.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpot.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotMapper.insertSelective(sysScenicSpot);

        SysScenicSpotBinding sysScenicSpotBinding = new SysScenicSpotBinding();
        sysScenicSpotBinding.setScenicSpotFid(sysScenicSpot.getScenicSpotId());
        sysScenicSpotBinding.setScenicSpotFname(sysScenicSpot.getScenicSpotName());
        sysScenicSpotBinding.setScenicSpotPid(sysScenicSpot.getScenicSpotFid());
        sysScenicSpotBinding.setScenicSpotSid(sysScenicSpot.getScenicSpotSid());
        sysScenicSpotBinding.setScenicSpotQid(sysScenicSpot.getScenicSpotQid());
        sysScenicSpotBinding.setScenicSpotType(0);
        int i1 = sysScenicSpotBindingMapper.insertSelective(sysScenicSpotBinding);
//        if (i>0){
//            SysScenicSpotImg sysScenicSpotImg = new SysScenicSpotImg();
//            sysScenicSpotImg.setScneicSpotImgId(IdUtils.getSeqId());
//            sysScenicSpotImg.setScenicSpotId(sysScenicSpot.getScenicSpotId());
//            sysScenicSpotImg.setScneicSpotImgUrl(SCENIC_SPOT_IMG_PATHE_GET_PIC_URL+ filename);
//            sysScenicSpotImg.setCreateDate(DateUtil.currentDateTime());
//            sysScenicSpotImg.setUpdateDate(DateUtil.currentDateTime());
//            int i1 = sysScenicSpotImgMapper.insertSelective(sysScenicSpotImg);
//        }


        return i;
    }

    /**
     * 后台管理——修改
     * @param sysScenicSpot
     * @return
     */
    @Override
    public int adminEditSysScenicSpot(SysScenicSpot sysScenicSpot) {
//        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
//        String filename = null;
//
//        if (type.equals(".png") || type.equals(".jpg")){
//            filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
//            String path = SCENIC_SPOT_IMG_PATHE_GET_PIC_PAHT + filename;// 存放位置
//            File destFile = new File(path);
//            try {
//                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
        sysScenicSpot.setPinYingName(Tinypinyin.tinypinyin(sysScenicSpot.getScenicSpotName()));
        sysScenicSpot.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKeyAndName(sysScenicSpot.getScenicSpotId(),sysScenicSpot.getScenicSpotName());
        if (StringUtils.isEmpty(sysScenicSpotBinding)){
            sysScenicSpotBinding = new SysScenicSpotBinding();
            sysScenicSpotBinding.setScenicSpotFid(sysScenicSpot.getScenicSpotId());
            sysScenicSpotBinding.setScenicSpotFname(sysScenicSpot.getScenicSpotName());
            sysScenicSpotBinding.setScenicSpotPid(sysScenicSpot.getScenicSpotFid());
            sysScenicSpotBinding.setScenicSpotType(1);
            int i1 = sysScenicSpotBindingMapper.insertSelective(sysScenicSpotBinding);
        }else{
            sysScenicSpotBinding.setScenicSpotSid(sysScenicSpot.getScenicSpotSid());
            sysScenicSpotBinding.setScenicSpotQid(sysScenicSpot.getScenicSpotQid());
            sysScenicSpotBinding.setScenicSpotPid(sysScenicSpot.getScenicSpotFid());
            sysScenicSpotBinding.setScenicSpotFname(sysScenicSpot.getScenicSpotName());
            int i1 = sysScenicSpotBindingMapper.updateByPrimaryKeySelective(sysScenicSpotBinding);
        }



        //        if (i>0){
//            Map<String, Object> search = new HashMap<>();
//            search.put("spotId",sysScenicSpot.getScenicSpotId());
//            SysScenicSpotImg sysScenicSpotImg = sysScenicSpotImgMapper.selectBySearch(search);
//            if (StringUtils.isEmpty(sysScenicSpotImg)){
//                SysScenicSpotImg sysScenicSpotImg1 = new SysScenicSpotImg();
//                sysScenicSpotImg1.setScenicSpotId(sysScenicSpot.getScenicSpotId());
//                sysScenicSpotImg1.setScneicSpotImgId(IdUtils.getSeqId());
//                sysScenicSpotImg1.setScneicSpotImgUrl(SCENIC_SPOT_IMG_PATHE_GET_PIC_URL+ filename);
//                sysScenicSpotImg1.setCreateDate(DateUtil.currentDateTime());
//                sysScenicSpotImg1.setUpdateDate(DateUtil.currentDateTime());
//                sysScenicSpotImgMapper.insertSelective(sysScenicSpotImg1);
//            }else{
//                sysScenicSpotImg.setScneicSpotImgUrl(SCENIC_SPOT_IMG_PATHE_GET_PIC_URL+ filename);
//                sysScenicSpotImg.setUpdateDate(DateUtil.currentDateTime());
//                int i1 = sysScenicSpotImgMapper.updateByPrimaryKeySelective(sysScenicSpotImg);
//            }
//
//        }
        return i;
    }

    /**
     * 后台管理——删除
     * @param spotId
     * @return
     */
    @Override
    public int adminDelSysScenicSpot(String spotId) {
        int i = sysScenicSpotMapper.deleteByPrimaryKey(Long.parseLong(spotId));
        int i1 = sysScenicSpotBindingMapper.deleteByPrimaryKey(Long.parseLong(spotId));
        int j = sysUserDistrictFabulousCollectionMapper.deleteBySpotId(spotId);
        return i ;
    }

    /**
     * 后台管理——景区管理——查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult adminSysScenicSpotFilesList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpot> list = sysScenicSpotMapper.adminSysScenicSpotFilesList(search);
        Map<String, Object> searchHot = new HashMap<>();
        if (!StringUtils.isEmpty(list)){
            for (SysScenicSpot sysScenicSpot : list) {

                searchHot =  new HashMap<String,Object>();
                searchHot.put("scenicSpotId",sysScenicSpot.getScenicSpotId());
                searchHot.put("type",2);
                SysScenicSpotHeat sysScenicSpotHeat = sysScenicSpotHeatMapper.querybestPopularity(searchHot);
                if (!StringUtils.isEmpty(sysScenicSpotHeat)){
                    sysScenicSpot.setPeopleCount(sysScenicSpotHeat.getTotal());
                }else{
                    sysScenicSpot.setPeopleCount(0);
                }
                //景区轮播图数量
                Integer bCount = sysScenicSpotBannerMapper.getScenicSpotBannerCount(sysScenicSpot.getScenicSpotId());
                //景区点赞人数
                Integer fCount = sysUserDistrictFabulousCollectionMapper.getSpotIdFabulousCount(sysScenicSpot.getScenicSpotId());
                //景区收藏人数
                Integer cCount = sysUserDistrictFabulousCollectionMapper.getSpotIdCollectionCount(sysScenicSpot.getScenicSpotId());
                //驻足人数
                Integer stopCount = sysScenicSpotUserStopMapper.getSpotIdUserCount(sysScenicSpot.getScenicSpotId());
                //驻足时间和
                Integer timeCount = sysScenicSpotUserStopMapper.getSpotIdTimeSum(sysScenicSpot.getScenicSpotId());

                if (!StringUtils.isEmpty(stopCount)  && stopCount != 0 && !StringUtils.isEmpty(timeCount) && timeCount != 0 ){
                    double avgTime =(double) timeCount / (double)stopCount;
                    sysScenicSpot.setAvgTime(avgTime);
                }else{
                    sysScenicSpot.setAvgTime(0d);
                }
                sysScenicSpot.setFabulousCount(fCount);
                sysScenicSpot.setCollectionCount(cCount);
                sysScenicSpot.setBannerCount(bCount);
            }
        }
        if (list.size()>0){
            PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理——景区下拉选
     * @return
     */
    @Override
    public List<SysScenicSpot> scenicSpotDrop() {

        List<SysScenicSpot> list =  sysScenicSpotMapper.scenicSpotDrop();

        return list;
    }

    /**
     * 归属地下拉选
     *
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> placeDrop() {

       List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.placeDrop();

       return list;
    }

    /**
     * 后台管理——景区轮播图添加
     * @param file
     * @param
     * @return
     */
    @Override
    public int addSpotBanners(MultipartFile[] file, SysScenicSpotBanner sysScenicSpotBanner) {

        //判断文件数组是否为空或者长度为0
        int i = 0;
        if(file!=null && file.length>0){
            for(MultipartFile mf : file){
                //获取文件后缀
                //String suffixName = fileName.substring(fileName.lastIndexOf("."));
//                // 重新生成文件名
//                fileName = UUID.randomUUID()+suffixName;
                String type = mf.getOriginalFilename().substring(mf.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH + filename;// 存放位置
                File destFile = new File(path);
                //获取原文件名称
//                String filename=mf.getOriginalFilename();
                try{
                    //写入文件
                    FileUtils.copyInputStreamToFile(mf.getInputStream(), destFile);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(mf, GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH.substring(1) + filename);
                    System.out.println(upload);

                }catch (Exception e){
                  e.printStackTrace();
                }

                sysScenicSpotBanner.setId(IdUtils.getSeqId());
                sysScenicSpotBanner.setUrl(GET_SYS_SCENIC_SPOT_BANNER_IMG_URL+ filename);
                sysScenicSpotBanner.setState(1);
                sysScenicSpotBanner.setType(2);
                sysScenicSpotBanner.setCreateTime(DateUtil.currentDateTime());
                sysScenicSpotBanner.setUpdateTime(DateUtil.currentDateTime());
                i = sysScenicSpotBannerMapper.addScenicSpotBanner(sysScenicSpotBanner);
            }
        }
        return i;
    }

    /**
     * 查询景区轮播图
     * @param spotId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getSpotFilesBanners(Long spotId, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();

        Map<String, Object> search = new HashMap<>();

        search.put("spotId",spotId);
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotBanner> list = sysScenicSpotBannerMapper.getScenicSpotBanner(search);

        if (list.size()>0){
            PageInfo<SysScenicSpotBanner> pageInfo = new PageInfo<>(list);

            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理——删除景区轮播图
     * @param id
     * @return
     */
    @Override
    public int delSpotFilesBanner(String id) {

       int i = sysScenicSpotBannerMapper.delSpotFilesBanner(id);

       return i;
    }

    /**
     * 后台管理——添加景区坐标
     * @param
     * @param sysScenicSpot
     * @return
     */
    @Override
    public int addSpotFiles(SysScenicSpot sysScenicSpot) {

            SysScenicSpot sysScenicSpot1 = sysScenicSpotMapper.selectByPrimaryKey(sysScenicSpot.getScenicSpotId());
            sysScenicSpot1.setBroadcastContent(sysScenicSpot.getBroadcastContent());
            sysScenicSpot1.setScenicSpotAddres(sysScenicSpot.getScenicSpotAddres());
            sysScenicSpot1.setAdmissionTicket(sysScenicSpot.getAdmissionTicket());
            sysScenicSpot1.setStartLevel(sysScenicSpot.getStartLevel());
            sysScenicSpot1.setCoordinateRange(sysScenicSpot.getCoordinateRange());
            sysScenicSpot1.setCoordinateRangeBaiDu(sysScenicSpot.getCoordinateRangeBaiDu());
            sysScenicSpot1.setCoordinateRangeAdius(sysScenicSpot.getCoordinateRangeAdius());
            sysScenicSpot1.setUpdateDate(DateUtil.currentDateTime());
            sysScenicSpot1.setSpotLabel(sysScenicSpot.getSpotLabel());
            sysScenicSpot1.setTourDuration(sysScenicSpot.getTourDuration());
            sysScenicSpot1.setIfNeedAdmissionTicket(sysScenicSpot.getIfNeedAdmissionTicket());
            int i1 = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot1);
            return i1;
    }

    /**
     * 删除景区以及景区图片
     * @param spotId
     * @return
     */
    @Override
    public int delSpotFiles(String spotId) {

        int i = sysScenicSpotMapper.deleteByPrimaryKey( Long.parseLong(spotId));
        sysScenicSpotImgMapper.deleteByPrimaryKey(Long.parseLong(spotId));

        return i;
    }

    /**
     * 编辑景区坐标和图片
     * @param
     * @param sysScenicSpot
     * @return
     */
    @Override
    public int editSpotFiles(SysScenicSpot sysScenicSpot) {

            int i1 = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);
            return i1;
    }

    /**
     * 后台管理 ——景区轮播图的修改
     * @param file
     * @param sysScenicSpotBanner
     * @return
     */
    @Override
    public int editSpotBanners(MultipartFile[] file, SysScenicSpotBanner sysScenicSpotBanner) {
        //判断文件数组是否为空或者长度为0
        int i = 0;
        if(file!=null && file.length>0){
            for(MultipartFile mf : file){
                //获取文件后缀
                //String suffixName = fileName.substring(fileName.lastIndexOf("."));
//                // 重新生成文件名
//                fileName = UUID.randomUUID()+suffixName;
                String type = mf.getOriginalFilename().substring(mf.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH + filename;// 存放位置
                File destFile =  new File(path);
                //获取原文件名称
//                String filename=mf.getOriginalFilename();
                try{
                    //写入文件
                    FileUtils.copyInputStreamToFile(mf.getInputStream(), destFile);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(mf, GET_SYS_SCENIC_SPOT_BANNER_IMG_PATH.substring(1) + filename);
                    System.out.println(upload);
                }catch (Exception e){
                    e.printStackTrace();
                }

                sysScenicSpotBanner.setUrl(GET_SYS_SCENIC_SPOT_BANNER_IMG_URL + filename);
                sysScenicSpotBanner.setUpdateTime(DateUtil.currentDateTime());
                i = sysScenicSpotBannerMapper.editScenicSpotBanner(sysScenicSpotBanner);
            }
        }
        return i;



    }

    /**
     * 后台管理 —— 修改轮播图启用禁用状态
     * @param id
     * @param type
     * @return
     */
    @Override
    public int editSpotFilesState(Long id, Integer type) {

        SysScenicSpotBanner sysScenicSpotBanner = sysScenicSpotBannerMapper.selectByPrimaryKey(id);
        sysScenicSpotBanner.setType(type);
        int i = sysScenicSpotBannerMapper.editScenicSpotBanner(sysScenicSpotBanner);
        return i ;
    }

    /**
     * 后台管理——上传景区图片
     * @param file
     * @param spotId
     * @return
     */
    @Override
    public int addSpotPicture(MultipartFile file, Long spotId) {

        SysScenicSpotImg scenicSpotImg = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(spotId);
        if (!StringUtils.isEmpty(scenicSpotImg)){
            sysScenicSpotImgMapper.deleteByPrimaryKey(scenicSpotImg.getScneicSpotImgId());
        }
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".png") || type.equals(".jpg")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = SCENIC_SPOT_IMG_PATHE_GET_PIC_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                // 限制大小
//                long size = file.getSize() / 1024;//kb
//                if (size >= 2048){
//                    return 3;
//                }
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                //图片压缩上传


//                CompressUtils.compress(file.getInputStream(),destFile,10000);


                //   阿里OSS文件存储_图片上传
                String upload = fileUploadUtil.upload(file, SCENIC_SPOT_IMG_PATHE_GET_PIC_PAHT.substring(1) + filename);
                System.out.println(upload);

            } catch (Exception e) {
                e.printStackTrace();
            }//复制文件到指定目录

            SysScenicSpotImg sysScenicSpotImg = new SysScenicSpotImg();
            sysScenicSpotImg.setScenicSpotId(spotId);
            sysScenicSpotImg.setScneicSpotImgId(IdUtils.getSeqId());
            sysScenicSpotImg.setScneicSpotImgUrl(SCENIC_SPOT_IMG_PATHE_GET_PIC_URL+filename);
            sysScenicSpotImg.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotImg.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotImgMapper.insertSelective(sysScenicSpotImg);
            return  i;
        }else {
            return 0;
        }

    }

    /**
     * 后台管理——上传景区音频讲解
     * @param file
     * @param scenicSpotId
     * @return
     */
    @Override
    public int adminAddSysScenicSpotAudio(MultipartFile file, Long scenicSpotId) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp3") || type.equals(".mav")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = scenicSpotAudioPatheGetAudioPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                // 限制大小
//                long size = file.getSize() / 1024;//kb
//                if (size >= 2048){
//                    return 3;
//                }
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                //图片压缩上传

//                CompressUtils.compress(file.getInputStream(),destFile,10000);

                //   阿里OSS文件存储_图片上传
                String upload = fileUploadUtil.upload(file, scenicSpotAudioPatheGetAudioPaht.substring(1) + filename);
                System.out.println(upload);

            } catch (Exception e) {
                e.printStackTrace();
            }

            List<SysScenicSpotAudio> list = sysScenicSpotAudioMapper.selectBySpotId(scenicSpotId);

            if (list.size()>0){
                SysScenicSpotAudio sysScenicSpotAudio = list.get(0);
                sysScenicSpotAudio.setScneicSpotAudioUrl(scenicSpotAudioPatheGetAudioUrl + filename);
                sysScenicSpotAudio.setUpdateDate(DateUtil.currentDateTime());
                int i =  sysScenicSpotAudioMapper.updateSelective(sysScenicSpotAudio);
                return i;
            }else{
                SysScenicSpotAudio sysScenicSpotAudio = new SysScenicSpotAudio();
                sysScenicSpotAudio.setScneicSpotAudioId(IdUtils.getSeqId());
                sysScenicSpotAudio.setScenicSpotId(scenicSpotId);
                sysScenicSpotAudio.setScneicSpotAudioUrl(scenicSpotAudioPatheGetAudioUrl + filename);
                sysScenicSpotAudio.setCreateDate(DateUtil.currentDateTime());
                sysScenicSpotAudio.setUpdateDate(DateUtil.currentDateTime());
                int i = sysScenicSpotAudioMapper.insertSelective(sysScenicSpotAudio);
                return i;
            }

        }else{
            return 0;
        }
    }

    /**
     * 根据坐标获取是否在景区内
     * @param
     * @return GPS_COORDINATE
     */
    @Override
    public Map<String, Object>  spotGpsGoordinate(String lng, String lat) {

        double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf(lng),Double.valueOf(lat));
        double lng84 = doubles[0];
        double lat84 = doubles[1];

        List<SysScenicSpotGpsCoordinateWithBLOBs> list = sysScenicSpotGpsCoordinateMapper.selectByAll();

        Map<String, Object> map = new HashMap<>();
        Long spotId = 0l;
        Double distance = 0d;
        for (SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs : list) {

            String[] coordinateOuterring = sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateOuterringBaiDu().split("!");
            if (coordinateOuterring != null && coordinateOuterring.length > 0) {
                Point[] ps = new Point[coordinateOuterring.length];
                for (int i = 0; i < coordinateOuterring.length; i++) {
                    String[] str = coordinateOuterring[i].split(",");
                    ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                }

                boolean ptInPoly = JudgingCoordinates.isPtInPoly(Double.valueOf(lng), Double.valueOf(lat), ps);
                if (ptInPoly){
                    distance = 0d;
                    spotId =  sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateScenicSpotId();
                    break;
                }else{

                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateScenicSpotId());
                    if (!StringUtils.isEmpty(sysScenicSpot.getCoordinateRange())){

                        String[] str = sysScenicSpot.getCoordinateRange().split(",");
                        Point2D.Double from = new Point2D.Double();
                        Point2D.Double to = new Point2D.Double();
                        from = new Point2D.Double(Double.valueOf(lng84), Double.valueOf(lat84));
                        to = new Point2D.Double(Double.valueOf(str[0]), Double.valueOf(str[1]));
                        double distanceOne = LngLonUtil.calculateWithSdk(from, to);
                        if (distance == 0 ){
                            distance =   distanceOne;
                            spotId = sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateScenicSpotId();
                        }else{
                            if (distanceOne < distance){
                                distance = distanceOne;
                                spotId = sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateScenicSpotId();
                            }
                        }
                    }else{
                        continue;
                    }
                }
            }
        }
        System.out.println(spotId);
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(spotId);
        map.put("spotId",sysScenicSpot);
        map.put("distance",distance);
        return map;
    }

    /**
     * 根据id查询景区
     * @param spotId
     * @return
     */
    @Override
    public SysScenicSpot selectById(Long spotId) {

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(spotId);
        return sysScenicSpot;
    }

    /**
     * 根据坐标和景区id获取是否在景区内
     * @param lng
     * @param lat
     * @param spotId
     * @return
     */
    @Override
    public Map<String, Object> spotIdGpsCoordinate(String lng, String lat, Long spotId) {

//        double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf(lng),Double.valueOf(lat));
//        double lng84 = doubles[0];
//        double lat84 = doubles[1];
        Map<String, Object> map = new HashMap<>();
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(spotId);
        SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs = sysScenicSpotGpsCoordinateMapper.selectBySpotId(spotId);
        String coordinateOuterringBaiDu1 = sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateOuterringBaiDu();

        //外扩后的围栏
//        String s = polygonOutlineUtil.computeLine(coordinateOuterringBaiDu1, Double.valueOf(sysScenicSpot.getCoordinateRangeAdius()) / 1000);

        String[] coordinateOuterringBaiDu =  coordinateOuterringBaiDu1.split("!");
            if (coordinateOuterringBaiDu != null && coordinateOuterringBaiDu.length > 0) {
                Point[] ps = new Point[coordinateOuterringBaiDu.length];
                for (int i = 0; i < coordinateOuterringBaiDu.length; i++) {
                    String[] str = coordinateOuterringBaiDu[i].split(",");
                    ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                }
                boolean ptInPoly = JudgingCoordinates.isPtInPoly(Double.valueOf(lng), Double.valueOf(lat), ps);
                if (ptInPoly){
                    map.put("state",1);
                    map.put("spotDetails",sysScenicSpot);
                    return map;
                }else{
                    map.put("state",0);
                    map.put("spotDetails",sysScenicSpot);
                    return map;
                }
            }


        return map;
    }


    /**
     * 修改景区启用禁用状态
     * @param id
     * @param type
     * @return
     */
    @Override
    public int editSpotState(Long id, Integer type) {

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(id);

        sysScenicSpot.setScenicSpotStatus(type.toString());

        int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);

        return i;
    }

    /**
     * 景区管理--导出
     * @param search
     * @return
     */
    @Override
    public List<SysScenicSpotFilesExcel> getOrderVoExcelPoi(Map<String, Object> search) {

        List<SysScenicSpotFilesExcel> sysScenicSpotFilesExcels = new ArrayList<>();

        sysScenicSpotFilesExcels = sysScenicSpotMapper.getOrderVoExcelPoi(search);



        return sysScenicSpotFilesExcels;
    }

    /**
     * 景区管理 -- 导入(添加)
     * @param sysScenicSpot
     * @return
     */
    @Override
    public int importScenicSpot(SysScenicSpot sysScenicSpot) {

        int i = sysScenicSpotMapper.insertSelective(sysScenicSpot);
        return i;
    }

    /**
     * 景区管理 -- 导入(修改)
     * @param sysScenicSpot
     * @return
     */
    @Override
    public int editImportScenicSpot(SysScenicSpot sysScenicSpot) {

        int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);
        return i ;
    }

    /**
     * 景区档案导出
     * @param search
     * @return
     */
    @Override
    public List<SysScenicSpotExcel> uploadExcelSpot(Map<String, Object> search) {


        List<SysScenicSpotExcel> list = sysScenicSpotMapper.uploadExcelSpot(search);

        return list;

    }

    /**
     * 根据坐标和景区id获取是否在景点内
     * @param lng
     * @param lat
     * @param spotId
     * @param broadcastId
     * @return
     */
    @Override
    public int broadcastIdGpsCoordinate(String lng, String lat, Long spotId, Long broadcastId) {

//        double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf(lng),Double.valueOf(lat));
//        double lng84 = doubles[0];
//        double lat84 = doubles[1];
        SysScenicSpotBroadcast sysScenicSpotBroadcast = sysScenicSpotBroadcastMapper.selectByPrimaryKey(broadcastId);

        String broadcastGpsBaiDu = sysScenicSpotBroadcast.getBroadcastGpsBaiDu();
        String scenicSpotRange = sysScenicSpotBroadcast.getScenicSpotRange();

        String[] split = broadcastGpsBaiDu.split("!");

        int b = 0 ;
        for (String s : split) {
            String[] split1 = s.split(",");
            boolean inCircle = JudgingCoordinates.isInCircle(Double.valueOf(scenicSpotRange), Double.valueOf(split1[1]), Double.valueOf(split1[0]), Double.valueOf(lat), Double.valueOf(lng));
            if (inCircle){
                b = 1;
                break;
            }
        }

        return b;

    }

    /**
     * 据景区，和坐标，判断坐标是否在景点范围内
     * @param lng
     * @param lat
     * @param spotId
     * @return
     */
    @Override
    public  Map<String ,Object> spotIdBroadcastGpsCoordinate(String lng, String lat, Long spotId) {

//        double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf(lng),Double.valueOf(lat));
//        double lng84 = doubles[0];
//        double lat84 = doubles[1];
        Map<String, Object> search = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();

        Double  distance = 0d;
        SysScenicSpotBroadcast broadcast = new SysScenicSpotBroadcast();
        search.put("spotId",spotId);
        List<SysScenicSpotBroadcast> spotBroadcastList = sysScenicSpotBroadcastMapper.getSpotBroadcastList(search);

        for (SysScenicSpotBroadcast sysScenicSpotBroadcast : spotBroadcastList) {
            String[] split = sysScenicSpotBroadcast.getBroadcastGpsBaiDu().split("!");
            String[] split1 ;
            String scenicSpotRange = sysScenicSpotBroadcast.getScenicSpotRange();
            if (split.length>1){
                 split1 = split[0].split(",");
            }else{
                split1 =split[0].split(",");
            }
            boolean inCircle = JudgingCoordinates.isInCircle(Double.valueOf(scenicSpotRange), Double.valueOf(split1[1]), Double.valueOf(split1[0]), Double.valueOf(lat), Double.valueOf(lng));
            if (inCircle){
                broadcast = sysScenicSpotBroadcast;
                distance = 0d;
                break;
            }else{
                 broadcast = sysScenicSpotBroadcast;
                 from=new Point2D.Double(Double.valueOf(split1[0]),Double.valueOf(split1[1]));
                 to=new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
                 Double  distance1 = LngLonUtil.calculateWithSdk(from, to);
                 if (distance == 0){
                     distance = distance1;
                     broadcast = sysScenicSpotBroadcast;
                 }else{
                     if (distance1 < distance){
                         distance = distance1;
                         broadcast = sysScenicSpotBroadcast;
                     }
                 }
            }
        }

        map.put("broadcast",broadcast);
        map.put("distance",distance);

        return map;
    }

    @Override
    public SysScenicSpot selectBySpotName(String scenicSpotName) {
        SysScenicSpot sysScenicSpot =  sysScenicSpotMapper.selectBySpotName(scenicSpotName);
        return sysScenicSpot;
    }

    @Override
    public int insert(SysScenicSpot sysScenicSpot) {

        sysScenicSpot.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpot.setUpdateDate(DateUtil.currentDateTime());
       int i = sysScenicSpotMapper.insertSelective(sysScenicSpot);

       return i;
    }

    /**
     * 获取推荐景区
     * @return
     */
    @Override
    public List<SysScenicSpot> getRecommendSpot() {

        List<SysScenicSpot> listAll = new ArrayList<>();

        List<SysScenicSpot> spotListHot = sysScenicSpotMapper.getRecommendSpot("1");
        List<SysScenicSpot> spotListPeople = sysScenicSpotMapper.getRecommendSpot("2");
        List<SysScenicSpot> spotListWelcome = sysScenicSpotMapper.getRecommendSpot("3");
        List<SysScenicSpot> spotListCollection = sysScenicSpotMapper.getRecommendSpot("4");
        List<SysScenicSpot> spotListLike = sysScenicSpotMapper.getRecommendSpot("5");

        if (spotListHot.size()>0){
//            SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(spotListHot.get(0).getScenicSpotId());
            spotListHot.get(0).setListType(1);
            listAll.add(spotListHot.get(0));
        }
        if (spotListPeople.size()>0){
            spotListPeople.get(0).setListType(2);
            listAll.add(spotListPeople.get(0));
        }
        if (spotListWelcome.size()>0){
            spotListWelcome.get(0).setListType(3);
            listAll.add(spotListWelcome.get(0));
        }
        if (spotListCollection.size()>0){
            spotListCollection.get(0).setListType(4);
            listAll.add(spotListCollection.get(0));
        }
        if (spotListLike.size()>0){
            spotListLike.get(0).setListType(5);
            listAll.add(spotListLike.get(0));
        }
        return listAll;

    }

    /**
     * 首页顶部搜索景区（返回列表）
     * @param sort
     * @param content
     * @param spotBindingName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<SysScenicSpot> currentCityAllN(Integer sort, String content, String spotBindingName, Integer pageNum, Integer pageSize) {

        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> search = new HashMap<>();
        search.put("fName",spotBindingName);
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname(spotBindingName);
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("fid",sysScenicSpotBinding.getScenicSpotFid()) ;

        }
        search = new HashMap<>();
//        search.put("fid",sysScenicSpotBinding.getScenicSpotFid()) ;
        search.put("sort",sort);
        search.put("spotName",content);
//        List<SysScenicSpot> scenicSpots = sysScenicSpotBindingMapper.selectBySearch(search);
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpot> scenicSpots = sysScenicSpotMapper.getScenicSpotAll(search);
        //计算当前坐标与景点坐标之间的距离
        for (SysScenicSpot scenicSpot : scenicSpots) {
            scenicSpot.setDistance(-1d);
            SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(scenicSpot.getScenicSpotId());
            if (!StringUtils.isEmpty(scenicSpotImgByScenicSpotId)){
                scenicSpot.setScenicSpotImgUrl(scenicSpotImgByScenicSpotId.getScneicSpotImgUrl());
            }
            //计算景区中景区有多少景点讲解
            Integer spotCount = sysScenicSpotBroadcastMapper.selectSpotByCount(scenicSpot.getScenicSpotId());
            scenicSpot.setScenicSpotBroadcastCount(spotCount);
            String coordinateRange = scenicSpot.getCoordinateRange();
            if (StringUtils.isEmpty(coordinateRange)){
                continue;
            }

        }

        //判断是否是根据距离排序
        if (sort==3){
            Collections.sort(scenicSpots, new Comparator<SysScenicSpot>() {
                @Override
                public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                    Double i = o2.getDistance() - o1.getDistance();
                    return  i.intValue() ;
                }
            });
        }
        map.put("scenicSpot",scenicSpots);

        return scenicSpots;
    }

    @Override
    public List<SysScenicSpot> currentCityN(String lng, String lat, String content, String spotBindingName, Integer sort, Integer pageNum, Integer pageSize) {

        List<SysScenicSpot> sysScenicSpots = new ArrayList<>();

        double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf(lng),Double.valueOf(lat));

        double lng84 = doubles[0];
        double lat84 = doubles[1];
        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();

        Map<String, Object> search = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectSpotByFname(spotBindingName);
        if (!StringUtils.isEmpty(sysScenicSpotBinding)){
            search.put("scenicSpotPid",sysScenicSpotBinding.getScenicSpotFid());
        }
        PageHelper.startPage(pageNum,pageSize);
//        search.put("scenicSpotPid",sysScenicSpotBinding.getScenicSpotFid());
        search.put("sort",sort);
        search.put("content",content);
        List<SysScenicSpot> scenicSpots = sysScenicSpotBindingMapper.selectBySearch(search);
        //计算当前坐标与景点坐标之间的距离
        for (SysScenicSpot scenicSpot : scenicSpots) {

            SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(scenicSpot.getScenicSpotId());

            if (!StringUtils.isEmpty(scenicSpotImgByScenicSpotId)){
                scenicSpot.setScenicSpotImgUrl(scenicSpotImgByScenicSpotId.getScneicSpotImgUrl());
            }

            String coordinateRange = scenicSpot.getCoordinateRange();
            if (StringUtils.isEmpty(coordinateRange)){
                scenicSpot.setDistance(-1d);
                continue;
            }
            String[] split = coordinateRange.split(",");
            from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

            to = new Point2D.Double(Double.valueOf(lng84),Double.valueOf(lat84));
            double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
            scenicSpot.setDistance(distanceOne);
            //计算景区中景区有多少景点讲解
            Integer spotCount = sysScenicSpotBroadcastMapper.selectSpotByCount(scenicSpot.getScenicSpotId());
            scenicSpot.setScenicSpotBroadcastCount(spotCount);
        }

        //判断是否是根据距离排序
        if (sort==3){
            Collections.sort(scenicSpots, new Comparator<SysScenicSpot>() {
                @Override
                public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                    if (o1.getDistance() == -1 || o2.getDistance() == -1){
                        return 0;
                    }
                    Double i =  o1.getDistance() - o2.getDistance() ;
                    return  i.intValue() ;
                }
            });
        }
//        map.put("city",sysScenicSpotBinding.getScenicSpotFid());
//        map.put("cityName",sysScenicSpotBinding.getScenicSpotFname());
        map.put("scenicSpot",scenicSpots);

        return scenicSpots;
    }

    @Override
    public SysScenicSpot selectByIdAndName(Long scenicSpotId, String scenicSpotName) {

        SysScenicSpot sysScenicSpot =  sysScenicSpotMapper.selectByIdAndName(scenicSpotId,scenicSpotName);

        return sysScenicSpot;
    }

    /**
     * 获取推荐景区（新）
     * @return
     */
    @Override
    public List<SysScenicSpot> getRecommendSpotNew() {

//       List<SysScenicSpot> list = sysScenicSpotMapper.getRecommendSpotNew();
//
//       if (StringUtils.isEmpty(list)){
//           list = new ArrayList<>();
//       }

        List<SysScenicSpot> list = new ArrayList<>();

        List<SysScenicSpot> spotListHot = sysScenicSpotMapper.getRecommendSpot("1");
        List<SysScenicSpot> spotListPeople = sysScenicSpotMapper.getRecommendSpot("2");
        List<SysScenicSpot> spotListWelcome = sysScenicSpotMapper.getRecommendSpot("3");
        List<SysScenicSpot> spotListCollection = sysScenicSpotMapper.getRecommendSpot("4");
        List<SysScenicSpot> spotListLike = sysScenicSpotMapper.getRecommendSpot("5");

        if (spotListHot.size()>0){
//            SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(spotListHot.get(0).getScenicSpotId());
            spotListHot.get(0).setListType(1);
            list.add(spotListHot.get(0));
        }
        if (spotListPeople.size()>0){
            spotListPeople.get(0).setListType(2);
            list.add(spotListPeople.get(0));
        }
        if (spotListWelcome.size()>0){
            spotListWelcome.get(0).setListType(3);
            list.add(spotListWelcome.get(0));
        }
        if (spotListCollection.size()>0){
            spotListCollection.get(0).setListType(4);
            list.add(spotListCollection.get(0));
        }
        if (spotListLike.size()>0){
            spotListLike.get(0).setListType(5);
            list.add(spotListLike.get(0));
        }


       return list;
    }

    /**
     * 根据景区id获取门票详情
     * @param spotId
     * @return
     */
    @Override
    public List<SysScenicSpotAdmissionFee> getSpotIdAdmissionTicket(String spotId) {

        List<SysScenicSpotAdmissionFee> admissionFeeList = sysScenicSpotAdmissionFeeMapper.selectBySpotId(Long.parseLong(spotId));

        return admissionFeeList;

    }

    @DS("master2")
    @Override
    public SysScenicSpot selectSpotIdByDetail(String scenicSpotId) {

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(Long.parseLong(scenicSpotId));
        return sysScenicSpot;
    }


    /**
     * 游小伴pp首页全部景区
     * 有当前坐标
     * @param
     * @return
     */
    @Override
    public PageDataResult currentCity(String lng,String lat,String content,String spotBindingName, Integer sort, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<SysScenicSpot> sysScenicSpots = new ArrayList<>();
        List<SysScenicSpotAudio> sysScenicSpotAudios = new ArrayList<>();
//        double[] doubles = LngLonUtil.bd09_To_gps84(Double.valueOf(lng),Double.valueOf(lat));
//
//        double lng84 = doubles[0];
//        double lat84 = doubles[1];
        Point2D.Double from=new Point2D.Double();
        Point2D.Double to=new Point2D.Double();

        Map<String, Object> search = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        List<SysScenicSpotBinding> sysScenicSpotBindings = sysScenicSpotBindingMapper.selectSpotByFnameT(spotBindingName);
        if (sysScenicSpotBindings.size()>0){
            search.put("scenicSpotPid",sysScenicSpotBindings.get(0).getScenicSpotFid());
        }
        PageHelper.startPage(pageNum,pageSize);
//        search.put("scenicSpotPid",sysScenicSpotBinding.getScenicSpotFid());
        search.put("sort",sort);
        search.put("content",content);
        List<SysScenicSpot> scenicSpots = sysScenicSpotBindingMapper.selectBySearch(search);


        //计算当前坐标与景点坐标之间的距离
        for (SysScenicSpot scenicSpot : scenicSpots) {

            SysScenicSpotImg scenicSpotImgByScenicSpotId = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(scenicSpot.getScenicSpotId());
            if (!StringUtils.isEmpty(scenicSpotImgByScenicSpotId)){
                scenicSpot.setScenicSpotImgUrl(scenicSpotImgByScenicSpotId.getScneicSpotImgUrl());
            }
            sysScenicSpotAudios = sysScenicSpotAudioMapper.selectBySpotId(scenicSpot.getScenicSpotId());
            if (sysScenicSpotAudios.size() > 0){
                scenicSpot.setScneicSpotAudioUrl(sysScenicSpotAudios.get(0).getScneicSpotAudioUrl());
            }

            String coordinateRange = scenicSpot.getCoordinateRangeBaiDu();
            if (StringUtils.isEmpty(coordinateRange)){
                scenicSpot.setDistance(-1d);
                continue;
            }
            String[] split = coordinateRange.split(",");
            from = new Point2D.Double(Double.valueOf(split[0]),Double.valueOf(split[1]));

            to = new Point2D.Double(Double.valueOf(lng),Double.valueOf(lat));
            double distanceOne = LngLonUtil.calculateWithSdk(from, to);
//            double distanceOne = DistanceUtil.getDistanceOne(Double.valueOf(lat),Double.valueOf(lng),Double.valueOf(split[0]),Double.valueOf(split[1]));
            scenicSpot.setDistance(distanceOne);
            //计算景区中景区有多少景点讲解
            Integer spotCount = sysScenicSpotBroadcastMapper.selectSpotByCount(scenicSpot.getScenicSpotId());
            scenicSpot.setScenicSpotBroadcastCount(spotCount);
        }


        //判断是否是根据距离排序

        if (sort==3){
            Collections.sort(scenicSpots, new Comparator<SysScenicSpot>() {
                @Override
                public int compare(SysScenicSpot o1, SysScenicSpot o2) {
                    if (o1.getDistance() == -1 && o2.getDistance() == -1){
                        return 0;
                    }else if (o1.getDistance() == -1){
                        return -1;
                    }else if (o2.getDistance() == -1){
                        return 1;
                    }
                    Double i =  o1.getDistance() - o2.getDistance() ;
                    return  i.intValue() ;
                }
            });
        }

        map.put("scenicSpot",scenicSpots);
        if (scenicSpots.size()>0){
            PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(scenicSpots);
            //pageDataResult.setData(pageInfo.getList());
            pageDataResult.setDataNew(map);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }


//    /**
//     * 批量景区添加拼音
//     */
//    @Override
//    public void adminPinyin() {
//        HashMap<String, Object> search = new HashMap<>();
//        List<SysScenicSpot> scenicSpotAll = sysScenicSpotMapper.getScenicSpotAll(search);
//        for (SysScenicSpot sysScenicSpot : scenicSpotAll) {
//            String scenicSpotName = sysScenicSpot.getScenicSpotName();
//            if (!StringUtils.isEmpty(scenicSpotName)){
//                sysScenicSpot.setPinYingName(Tinypinyin.tinypinyin(scenicSpotName));
//                System.out.println(sysScenicSpot.getPinYingName());
//                sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);
//            }
//
//        }
//
//    }

}
