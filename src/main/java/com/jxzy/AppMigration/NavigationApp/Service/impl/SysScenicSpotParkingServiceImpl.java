package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotParkingService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotParkingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotParkingExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParking;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParkingWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotParkingServiceImpl implements SysScenicSpotParkingService {

    @Autowired
    private SysScenicSpotParkingMapper sysScenicSpotParkingMapper;

    /**
     * 获取景区停靠点列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/27 0027
     */
    public List<SysScenicSpotBroadcast> getScenicSpotParkingList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum, pageSize);
        return sysScenicSpotParkingMapper.getScenicSpotParkingList(search);
    }

    @Override
    public PageDataResult selectBySearch(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotParkingWithBLOBs> list = sysScenicSpotParkingMapper.selectBySearch(search);

        if (list.size()>0){
            PageInfo<SysScenicSpotParkingWithBLOBs> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());

        }

        return pageDataResult;
    }

    /**
     * 后台管理——添加景区停放点
     * @param
     * @return
     */
    @Override
    public int addSpotParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        sysScenicSpotParkingWithBLOBs.setParkingId(IdUtils.getSeqId());
        sysScenicSpotParkingWithBLOBs.setParkingPinyinName(Tinypinyin.tinypinyin(sysScenicSpotParkingWithBLOBs.getParkingName()));
        sysScenicSpotParkingWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotParkingWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
//        if (StringUtils.isEmpty(sysScenicSpotParkingWithBLOBs.getCoordinateType())){
//            sysScenicSpotParkingWithBLOBs.setCoordinateType("3");
//        }
//        sysScenicSpotParkingWithBLOBs.setCoordinateType("3");
        int i = sysScenicSpotParkingMapper.insertSelective(sysScenicSpotParkingWithBLOBs);
        return i;
    }

    /**
     * 后台管理——修改景区停放点
     * @param sysScenicSpotParkingWithBLOBs
     * @return
     */
    @Override
    public int editSpotParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        sysScenicSpotParkingWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotParkingMapper.updateByPrimaryKeySelective(sysScenicSpotParkingWithBLOBs);
        return i;
    }

    /**
     * 后台管理——删除景区停放点
     * @param parkingId
     * @return
     */
    @Override
    public int delSpotParking(Long parkingId) {

        int i = sysScenicSpotParkingMapper.deleteByPrimaryKey(parkingId);
        return i;

    }

    /**
     * 修改景区停放点，出入口等等状态
     * @param parkingId
     * @param type
     * @return
     */
    @Override
    public int exitSpotParkingType(Long parkingId, String type) {

        SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs = sysScenicSpotParkingMapper.selectByPrimaryKey(parkingId);

        sysScenicSpotParkingWithBLOBs.setParkingType(type);
        int i = sysScenicSpotParkingMapper.updateByPrimaryKeySelective(sysScenicSpotParkingWithBLOBs);
        return i ;
    }

    /**
     * 景区中的停车场
     * @param pageNum
     * @param pageSize
     * @param scenicSpotId
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcast> queryScenicParkingLotLists(int pageNum, int pageSize, String scenicSpotId) {

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotBroadcast> list = sysScenicSpotParkingMapper.queryScenicParkingLotLists(scenicSpotId);
        return list;

    }

    /**
     * 景区中的出入口
     * @param pageNum
     * @param pageSize
     * @param scenicSpotId
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcast> queryScenicSpotEntranceLists(int pageNum, int pageSize, String scenicSpotId) {

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotBroadcast> list = sysScenicSpotParkingMapper.queryScenicSpotEntranceLists(scenicSpotId);
        return list;

    }

    /**
     * 导出景区中的出入口
     * @param
     * @param
     * @param
     * @return
     */
    @Override
    public List<SysScenicSpotParkingExcel> uploadExcelSpotEntrance(Map<String, Object> search) {

       List<SysScenicSpotParkingExcel> list = sysScenicSpotParkingMapper.uploadExcelSpotEntrance(search);

       return list;

    }

    /**
     * 出口名称查询
     * @param parkingName
     * @return
     */
    @Override
    public SysScenicSpotParkingWithBLOBs selectByParkingName(String parkingName) {


        SysScenicSpotParkingWithBLOBs sysScenicSpotParking = sysScenicSpotParkingMapper.selectByParkingName(parkingName);

        return sysScenicSpotParking;
    }

    /**
     * 导入景区出入口添加
     * @param
     * @return
     */
    @Override
    public int importScenicSpot(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        int i = sysScenicSpotParkingMapper.insertSelective(sysScenicSpotParkingWithBLOBs);
        return i;
    }
    /**
     * 导入景区出入口修改
     * @param
     * @return
     */
    @Override
    public int editImportScenicSpot(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        int i = sysScenicSpotParkingMapper.updateByPrimaryKeySelective(sysScenicSpotParkingWithBLOBs);
        return  i;
    }

    /**
     * 根据景区id和停放点名称查询数据
     * @param parkingScenicSpotId
     * @param parkingName
     * @return
     */
    @Override
    public SysScenicSpotParkingWithBLOBs getSpotIdAndParkingName(Long parkingScenicSpotId, String parkingName) {

        SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs = sysScenicSpotParkingMapper.getSpotIdAndParkingName(parkingScenicSpotId, parkingName);
        return sysScenicSpotParkingWithBLOBs;
    }

    /**
     * 定时任务中添加停放点
     * @param sysScenicSpotParkingWithBLOBs
     * @return
     */
    @Override
    public int insert(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {

        sysScenicSpotParkingWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotParkingWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotParkingMapper.insertSelective(sysScenicSpotParkingWithBLOBs);
        return i;
    }

    /**
     * 根据id查询
     * @param parkingId
     * @return
     */
    @Override
    public SysScenicSpotParkingWithBLOBs getSpotParkingId(Long parkingId) {

        SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs = sysScenicSpotParkingMapper.selectByPrimaryKey(parkingId);
        return sysScenicSpotParkingWithBLOBs;
    }
}
