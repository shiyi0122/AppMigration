package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class SysGuideAppUsersServiceImpl implements SysGuideAppUsersService {
    @Autowired
    private SysGuideAppUsersMapper sysGuideAppUsersMapper;

    /**
     * 查询令牌失效性
     * @param: token
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    public SysGuideAppUsers getToken(String longinTokenId) {
        return sysGuideAppUsersMapper.getToken(longinTokenId);
    }

    /**
     * 唯一标拾查询用户信息
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    public SysGuideAppUsers getPhoneSign(Map<String, Object> search) {
        return sysGuideAppUsersMapper.getPhoneSign(search);
    }

    /**
     * 游客模式注册
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    public int insetRegister(Map<String, Object> search) {
        SysGuideAppUsers users = new SysGuideAppUsers();
        users.setUserId(IdUtils.getSeqId());
        users.setLonginTokenId(IdUtils.getUUID());
        users.setUserPhone("");
        users.setPhoneSign(String.valueOf(search.get("phoneSign")));
        users.setUserClientGtId(String.valueOf(search.get("userClientGtId")));
        users.setCreateDate(DateUtil.currentDateTime());
        users.setUpdateDate(DateUtil.currentDateTime());
        return sysGuideAppUsersMapper.insertSelective(users);
    }

    /**
     * 一键登录注册
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    public int insetOneClickLoginRegister(Map<String, Object> search) {
        SysGuideAppUsers users = new SysGuideAppUsers();
        users.setUserId(IdUtils.getSeqId());
        users.setLonginTokenId(IdUtils.getUUID());
        users.setUserPhone(String.valueOf(search.get("userPhone")));
        users.setPhoneSign(String.valueOf(search.get("phoneSign")));
        users.setUserClientGtId(String.valueOf(search.get("userClientGtId")));
        users.setCreateDate(DateUtil.currentDateTime());
        users.setUpdateDate(DateUtil.currentDateTime());
        return sysGuideAppUsersMapper.insertSelective(users);
    }

    /**
     * 更新手机号
     * @param: user
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    public int updateRegister(SysGuideAppUsers user, Map<String, Object> search) {
        SysGuideAppUsers users = new SysGuideAppUsers();
        users.setUserId(user.getUserId());
        users.setUserPhone(String.valueOf(search.get("userPhone")));
        users.setPhoneSign(String.valueOf(search.get("phoneSign")));
        users.setUserClientGtId(String.valueOf(search.get("userClientGtId")));
        users.setUpdateDate(DateUtil.currentDateTime());
        return sysGuideAppUsersMapper.updateByPrimaryKeySelective(users);
    }
}
