package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysFlowerIdentificationService;
import com.jxzy.AppMigration.NavigationApp.dao.SysFeaturedFoodFabulousMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysFlowerIdentificationMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysFlowerIdentification;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/19 16:41
 */
@Service
public class SysFlowerIdentificationServiceImpl implements SysFlowerIdentificationService {


    @Autowired
    SysFlowerIdentificationMapper sysFlowerIdentificationMapper;

    @Override
    public PageDataResult getSysFlowerIdentificationList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum, pageSize);
        List<SysFlowerIdentification> list = sysFlowerIdentificationMapper.list(search);
        if (list.size()>0){
            PageInfo<SysFlowerIdentification> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);

        }

        return pageDataResult;

    }
}
