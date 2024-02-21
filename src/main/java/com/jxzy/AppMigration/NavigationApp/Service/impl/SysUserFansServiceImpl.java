package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysUserFansService;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserFansMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserFans;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author zhang
 * @Date 2023/1/12 18:10
 * 用户关注用户
 */
@Service
public class SysUserFansServiceImpl implements SysUserFansService {

    @Autowired
    SysUserFansMapper sysUserFansMapper;


    @Override
    public int addSysUserFans(SysUserFans sysUserFans) {

        Integer count = sysUserFansMapper.selectIsFans(sysUserFans.getFansUserId().toString(), sysUserFans.getCoverFansUserId());

        if (count>0){
            return 0;
        }else{
            sysUserFans.setId(IdUtils.getSeqId());
            sysUserFans.setCreateTime(DateUtil.currentDateTime());
            sysUserFans.setUpdateTime(DateUtil.currentDateTime());
            int i = sysUserFansMapper.insertSelective(sysUserFans);
            return i;
        }



    }

    /**
     * 取消关注用户
     * @param sysUserFans
     * @return
     */
    @Override
    public int cancelSysUserFans(SysUserFans sysUserFans) {

       int i = sysUserFansMapper.cancelSysUserFans(sysUserFans.getFansUserId(),sysUserFans.getCoverFansUserId());

        return  i;
    }

}
