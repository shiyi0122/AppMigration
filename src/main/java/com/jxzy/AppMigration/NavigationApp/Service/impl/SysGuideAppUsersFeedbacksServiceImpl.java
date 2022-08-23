package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersFeedbacksService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersFeedbacksMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedbacks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysGuideAppUsersFeedbacksServiceImpl implements SysGuideAppUsersFeedbacksService {
    @Autowired
    private SysGuideAppUsersFeedbacksMapper sysGuideAppUsersFeedbacksMapper;

    /**
     * 用户意见反馈
     * @param: user
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    public int insetUserFeedback(SysGuideAppUsersFeedbacks user) {
        return sysGuideAppUsersFeedbacksMapper.insertSelective(user);
    }
}
