package com.jxzy.AppMigration.NavigationApp.Service;


import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.base.ThirdPartyLoginDTO;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;

import java.util.List;
import java.util.Map;

public interface SysGuideAppUsersService {
    /**
     * 查询用户令牌失效性
     *
     * @param: token
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/2 0002
     */
    SysGuideAppUsers getToken(String longinTokenId);

    /**
     * 唯一标拾查询用户信息
     *
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    SysGuideAppUsers getPhoneSign(Map<String, Object> search);

    /**
     * 游客模式注册
     *
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    int insetRegister(Map<String, Object> search);

    /**
     * 一键登录注册
     *
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    int insetOneClickLoginRegister(Map<String, Object> search);

    /**
     * @param: search
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    int updateRegister(SysGuideAppUsers user, Map<String, Object> search);

    /**
     * 判断token的有效性
     */
    boolean getContrastToken(String token);


    int updateAppUsers(SysGuideAppUsers user);


    SysGuideAppUsers insertAppUser(Map<String, Object> search);

    SysGuideAppUsers bindPhone(String phone, String openId, Integer flag);

    SysGuideAppUsers thirdLogin(ThirdPartyLoginDTO loginDTO);

    SysGuideAppUsers appleSignIn(SysGuideAppUsers sysGuideAppUsers);

    SysGuideAppUsers selectPhoneByUser(String phoneNew);


    SysGuideAppUsers userDetails(String uid);

    PageDataResult getSysGuideAppUsersList(Integer pageNum, Integer pageSize, String phone, Map<String, Object> search);

    List<SysGuideAppUsers> getSysGuideAppUsersDrop();


    int delSysGuideAppusers(Long userId);

    int addUserVisit(String uid);

    String getUserIdByUserClientGtId(String userId);


    int addRobotUserId(String phone, String openId, String userName);



    int insertUser(SysGuideAppUsers sysGuideAppUsers);

    SysGuideAppUsers selectOpenIdByUser(String openid);

    ReturnModel updateById(Long userId, String userName, String userPhone, String userClientGtId);

    ReturnModel delectWX(Long userId,String userPhone);
}
