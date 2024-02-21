package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxzy.AppMigration.NavigationApp.Service.UserApplyService;
import com.jxzy.AppMigration.NavigationApp.dao.UserApplyMapper;
import com.jxzy.AppMigration.NavigationApp.entity.UserApply;
import com.jxzy.AppMigration.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApplyServiceImpl extends ServiceImpl<UserApplyMapper, UserApply> implements UserApplyService {

    @Autowired
    private UserApplyMapper userApplyMapper;

    @Override
    public int addUserApply(UserApply userApply) {
        userApply.setCreateTime(DateUtil.currentDateTime());
        userApply.setUpdateTime(DateUtil.currentDateTime());
        return userApplyMapper.addUserApply(userApply);
    }

    @Override
    public List<UserApply> getByUser() {
        return userApplyMapper.getByUser();
    }
}
