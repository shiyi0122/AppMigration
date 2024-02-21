package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersFeedbacksService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersFeedbacksMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedbacks;
import com.jxzy.AppMigration.NavigationApp.util.DateUtil;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 后台管理——app意见反馈列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGuideAppNewsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysGuideAppUsersFeedbacks> list = sysGuideAppUsersFeedbacksMapper.selectBySearch(search);
        for (SysGuideAppUsersFeedbacks sysGuideAppUsersFeedbacks : list) {
            String urlPic = sysGuideAppUsersFeedbacks.getUrlPic();
            if (!StringUtils.isEmpty(urlPic)){
                String[] split = urlPic.split(",");
                sysGuideAppUsersFeedbacks.setPicCount(split.length);
            }else{
                sysGuideAppUsersFeedbacks.setPicCount(0);
            }
        }
        if (list.size()>0){
            PageInfo<SysGuideAppUsersFeedbacks> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 后台管理——app意见反馈状态修改
     * @param id
     * @param states
     * @return
     */
    @Override
    public int exitSysGuideAppUsersFeedbacksStates(Long id, String states) {

        SysGuideAppUsersFeedbacks sysGuideAppUsersFeedbacks = sysGuideAppUsersFeedbacksMapper.selectByPrimaryKey(id);

        sysGuideAppUsersFeedbacks.setStatus(states);
        int i = sysGuideAppUsersFeedbacksMapper.updateByPrimaryKeySelective(sysGuideAppUsersFeedbacks);
        return  i;
    }

    /**
     * 后台删除用户反馈
     * @param id
     * @return
     */
    @Override
    public int delSysGuideAppUsersFeedbacks(Long id) {

        int i = sysGuideAppUsersFeedbacksMapper.deleteByPrimaryKey(id);
        return i;
    }


    /**
     * 后台意见 回复内容 （修改）
     * @param sysGuideAppUsersFeedbacks
     * @return
     */
    @Override
    public int exitSysGuideAppUsersFeedbacksReply(SysGuideAppUsersFeedbacks sysGuideAppUsersFeedbacks) {


        sysGuideAppUsersFeedbacks.setUpdateTime(DateUtil.currentDateTime());

        int i = sysGuideAppUsersFeedbacksMapper.updateByPrimaryKeySelective(sysGuideAppUsersFeedbacks);

        return i;

    }

    /**
     * 根据反馈id，获取反馈列表
     * @param id
     * @return
     */
    @Override
    public List<SysGuideAppUsersFeedbacks> getUsersFeedbacksPicturl(Long id) {

        List<SysGuideAppUsersFeedbacks> list = new ArrayList<>();
        SysGuideAppUsersFeedbacks sysGuideAppUsersFeedbacks = sysGuideAppUsersFeedbacksMapper.selectByPrimaryKey(id);

        String[] split = sysGuideAppUsersFeedbacks.getUrlPic().split(",");

        for (String s : split) {
            SysGuideAppUsersFeedbacks sysGuideAppUsersFeedbacks1 = new SysGuideAppUsersFeedbacks();
            sysGuideAppUsersFeedbacks1.setUrlPic(s);
            list.add(sysGuideAppUsersFeedbacks1);
        }

        return list;
    }


}
