package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersFeedbackService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersFeedbackMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedback;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysGuideAppUsersFeedbackServiceImpl implements SysGuideAppUsersFeedbackService {
    @Autowired
    private SysGuideAppUsersFeedbackMapper sysGuideAppUsersFeedbackMapper;

    /**
     * 用户反馈内容
     * @param: userId 用户ID
     * @param: feedbackContent 反馈内容
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    public int insetUsersFeedback(Long userId, String feedbackContent) {
        SysGuideAppUsersFeedback feedback = new SysGuideAppUsersFeedback();
        feedback.setFeedbackId(IdUtils.getSeqId());
        feedback.setAppUserId(userId);
        feedback.setFeedbackContent(feedbackContent);
        feedback.setCreateDate(DateUtil.currentDateTime());
        feedback.setUpdateDate(DateUtil.currentDateTime());
        return sysGuideAppUsersFeedbackMapper.insertSelective(feedback);
    }
}
