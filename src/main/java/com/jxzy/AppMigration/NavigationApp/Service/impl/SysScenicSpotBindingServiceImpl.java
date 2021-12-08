package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBindingService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBindingMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            binding.get(i).setCityPic(DOMAIN_NAME+binding.get(i).getCityPic());
        }
        return binding;
    }
}
