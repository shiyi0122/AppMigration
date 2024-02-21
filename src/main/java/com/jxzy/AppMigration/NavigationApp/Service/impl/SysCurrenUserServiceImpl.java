package com.jxzy.AppMigration.NavigationApp.Service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.jxzy.AppMigration.NavigationApp.Service.SysCurrenUserService;
import com.jxzy.AppMigration.NavigationApp.dao.SysCurrenUserMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysCurrenUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mars
 * @ClassName: SysCurrenUserServiceImpl
 * @Description: 前台用户业务逻辑实现层
 * @date 2018年4月3日
 */
@Service
public class SysCurrenUserServiceImpl implements SysCurrenUserService {

    @Autowired
    SysCurrenUserMapper sysCurrenUserMapper;

    @DS("master2")
    @Override
    public SysCurrenUser selectPhoneByUser(String phone) {

        SysCurrenUser sysCurrenUser = sysCurrenUserMapper.selectPhoneByUser(phone);
        return sysCurrenUser;
    }
}
