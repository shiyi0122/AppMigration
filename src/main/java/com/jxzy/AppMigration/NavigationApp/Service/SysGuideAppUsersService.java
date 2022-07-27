package com.jxzy.AppMigration.NavigationApp.Service;



import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;

import java.util.Map;

public interface SysGuideAppUsersService {
    /**
     * 查询用户令牌失效性
     * @param: token
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    SysGuideAppUsers getToken(String longinTokenId);

    /**
     * 唯一标拾查询用户信息
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    SysGuideAppUsers getPhoneSign(Map<String, Object> search);

    /**
     * 游客模式注册
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    int insetRegister(Map<String, Object> search);

    /**
     * 一键登录注册
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    int insetOneClickLoginRegister(Map<String, Object> search);

    /**
     * 
     * @param: search 
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    int updateRegister(SysGuideAppUsers user, Map<String, Object> search);
    /**
     * 判断token的有效性
     *
     */
    boolean getContrastToken(String token);


    int updateAppUsers(SysGuideAppUsers user);


    SysGuideAppUsers insertAppUser(Map<String, Object> search);

    SysGuideAppUsers bindPhone(String phone, String openId, Integer flag);

}
