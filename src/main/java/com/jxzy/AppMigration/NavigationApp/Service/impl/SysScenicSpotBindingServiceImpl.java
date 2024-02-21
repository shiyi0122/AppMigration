package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBindingService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotBindingServiceImpl implements SysScenicSpotBindingService {
    @Autowired
    private SysScenicSpotBindingMapper sysScenicSpotBindingMapper;
    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;//后台管系统域名地址
    /**
     * 获取景区和城市列表
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/1 0001
     */
    public List<SysScenicSpotBinding> queryCityAndScenicSpotLists(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotBinding> binding = sysScenicSpotBindingMapper.queryCityAndScenicSpotLists(search);
        for (int i = 0;i<binding.size();i++){
            binding.get(i).setCityPic("https://topsroboteer.ac.cn/"+binding.get(i).getCityPic());
        }
        return binding;
    }

    /**
     * 根据景区id，和父id查询归属信息
     * @param scenicSpotFid
     * @param scenicSpotPid
     * @return
     */
    @Override
    public SysScenicSpotBinding getSpotIdAndSpotFid(Long scenicSpotFid, Long scenicSpotPid) {

       SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.getSpotIdAndSpotFid(scenicSpotFid,scenicSpotPid);
       return sysScenicSpotBinding;
    }

    @Override
    public int insert(SysScenicSpotBinding sysScenicSpotBinding) {
     int i = sysScenicSpotBindingMapper.insertSelective(sysScenicSpotBinding);

     return i;
    }

    /**
     * 根据id查询
     * @param scenicSpotFid
     * @return
     */
    @Override
    public SysScenicSpotBinding getSpotBindingId(Long scenicSpotFid) {

        SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(scenicSpotFid);
        return sysScenicSpotBinding;

    }

    /**
     * 修改归属表信息
     * @param sysScenicSpotBinding
     * @return
     */
    @Override
    public int edit(SysScenicSpotBinding sysScenicSpotBinding) {

       int i =  sysScenicSpotBindingMapper.updateByPrimaryKeySelective(sysScenicSpotBinding);
       return i;
    }

    /**
     * 后台添加归属省
     * @param sysScenicSpotBinding
     * @return
     */
    @Override
    public int addScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding) {

//        SysScenicSpotBinding sysScenicSpotBinding1 = sysScenicSpotBindingMapper.selectSpotByFname(sysScenicSpotBinding.getScenicSpotFname());
        SysScenicSpotBinding sysScenicSpotBinding1 = sysScenicSpotBindingMapper.selectSpotByFnameN(sysScenicSpotBinding.getScenicSpotFname(),sysScenicSpotBinding.getScenicSpotType());

        if (StringUtils.isEmpty(sysScenicSpotBinding1)){
            sysScenicSpotBinding.setScenicSpotFid(IdUtils.getSeqId());
//            sysScenicSpotBinding.setScenicSpotType(1);
            int i = sysScenicSpotBindingMapper.insertSelective(sysScenicSpotBinding);
            return i;
        }else{
            return 0;
        }



    }

    /**
     * 后台修改归属省
     * @param sysScenicSpotBinding
     * @return
     */
    @Override
    public int editScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding) {

        int i = sysScenicSpotBindingMapper.updateByPrimaryKeySelective(sysScenicSpotBinding);

        return i;

    }
    /**
     * 后台删除归属省
     * @param
     * @return
     */
    @Override
    public int delScenicSpotBinding(Long scenicSpotFid) {

        Integer t = sysScenicSpotBindingMapper.selectFidByList(scenicSpotFid);
        int i = 0;
        if (t>0){
            return 2;
        }else{
            i = sysScenicSpotBindingMapper.deleteByPrimaryKey(scenicSpotFid);
        }


        return i;
    }

    /**
     * 归属省下拉选
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getSpotBindingProvince(String pid) {

       List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.getSpotBindingProvince(pid);
       return list;
    }

    /**
     * 归属市下拉选
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getSpotBindingCity(String pid) {

        List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.getSpotBindingCity(pid);
        return list;

    }
    /**
     * 归属区/县下拉选
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getSpotBindingArea(String pid) {

        List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.getSpotBindingArea(pid);
        return list;
    }

    /**
     * 后台归属地列表查询
     * @param pageNum
     * @param pageSize
     * @param sysScenicSpotBinding
     * @return
     */
    @Override
    public PageDataResult getScenicSpotBindingList(Integer pageNum, Integer pageSize, SysScenicSpotBinding sysScenicSpotBinding) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBinding> scenicSpotBinding = sysScenicSpotBindingMapper.getScenicSpotBindingList(sysScenicSpotBinding);
        if(scenicSpotBinding.size() != 0){
            PageInfo<SysScenicSpotBinding> pageInfo = new PageInfo<>(scenicSpotBinding);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;


    }

    /**
     * 多级联动下拉选
     * @return
     */

    @Override
    public List<SysScenicSpotBinding> getMulitistageDrop() {

        List<SysScenicSpotBinding> sysScenicSpotBindings = sysScenicSpotBindingMapper.placeDrop();

        for (SysScenicSpotBinding sysScenicSpotBinding : sysScenicSpotBindings) {

            List<SysScenicSpotBinding> spotBindingCity = sysScenicSpotBindingMapper.getSpotBindingCity(sysScenicSpotBinding.getScenicSpotFid().toString());

            for (SysScenicSpotBinding scenicSpotBinding : spotBindingCity) {
                List<SysScenicSpotBinding> spotBindingArea = sysScenicSpotBindingMapper.getSpotBindingArea(scenicSpotBinding.getScenicSpotFid().toString());
                scenicSpotBinding.setSysScenicBindingList(spotBindingArea);
            }
            sysScenicSpotBinding.setSysScenicBindingList(spotBindingCity);
        }

        return sysScenicSpotBindings;
    }

    /**
     * app端热门城市
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getHotCity() {

       List<SysScenicSpotBinding> list =  sysScenicSpotBindingMapper.getHotCity();

       return list;
    }

    /**
     * app端全部市
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getCityAll() {

        List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.getCityAll();

        return list;
    }

    /**
     * qpp端全部区县
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getAreaAll() {

        List<SysScenicSpotBinding> list =  sysScenicSpotBindingMapper.getAreaAll();
        return list;
    }


    /**
     * app端搜索城市区县
     * @param content
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getCityArea(String content) {

        List<SysScenicSpotBinding> sysScenicSpotBindingList = sysScenicSpotBindingMapper.selectSpotByFnameList(content);

        return sysScenicSpotBindingList;
    }

    /**
     * 省市查询
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getProvinceCity() {

        List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.getProvinceCity();
        return list;
    }

    /**
     * 根据城市名称获取城市信息
     * @param cityName
     * @return
     */
    @Override
    public SysScenicSpotBinding selectSpotByFnameT(String cityName) {

        List<SysScenicSpotBinding> sysScenicSpotBindings = sysScenicSpotBindingMapper.selectSpotByFnameT(cityName);

        return sysScenicSpotBindings.get(0);

    }

    /**
     * 获取城市下景区
     * @param scenicSpotFid
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getBindingIdBySpotList(Long scenicSpotFid) {

        List<SysScenicSpotBinding> bindingIdBySpotList = sysScenicSpotBindingMapper.getBindingIdBySpotList(scenicSpotFid);
        return bindingIdBySpotList;
    }
}
