package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.base.ThirdPartyLoginDTO;
import com.jxzy.AppMigration.NavigationApp.exception.ExistedException;
import com.jxzy.AppMigration.NavigationApp.exception.ForBiddenException;
import com.jxzy.AppMigration.NavigationApp.exception.InformationErrorException;
import com.jxzy.AppMigration.NavigationApp.exception.UniversalException;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.JWTUtils;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        users.setLonginTokenId(JWTUtils.sign((String) search.get("userPhone"), users.getUserId(), new Date().toString()));
        users.setUserPhone(String.valueOf(search.get("userPhone")));
        users.setPhoneSign(String.valueOf(search.get("phoneSign")));
        users.setUserClientGtId(String.valueOf(search.get("userClientGtId")));
        users.setCreateDate(DateUtil.currentDateTime());
        users.setUpdateDate(DateUtil.currentDateTime());
        int i = sysGuideAppUsersMapper.insertSelective(users);
        return i;
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

    @Override
    public boolean getContrastToken(String token) {

        if (StringUtils.isEmpty(token)) {
            throw new ForBiddenException();
        }
        Long uid = JWTUtils.getUid(token);

        if (NumberUtil.compare(uid, 0) == 0) {
            return true;
        }


        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersMapper.getUid(uid);
        String longinTokenId = sysGuideAppUsers.getLonginTokenId();

        return StringUtils.pathEquals(token, longinTokenId);
    }


    @Override
    public int updateAppUsers(SysGuideAppUsers user) {

        user.setUpdateDate(DateUtil.currentDateTime());

        int i = sysGuideAppUsersMapper.updateByPrimaryKeySelective(user);
        return i;
    }

    @Override
    public SysGuideAppUsers insertAppUser(Map<String, Object> search) {

        SysGuideAppUsers users = new SysGuideAppUsers();
        users.setUserId(IdUtils.getSeqId());
        users.setLonginTokenId(JWTUtils.sign((String) search.get("userPhone"), users.getUserId(), new Date().toString()));
        users.setUserPhone(String.valueOf(search.get("userPhone")));
        users.setPhoneSign(String.valueOf(search.get("phoneSign")));
        users.setUserClientGtId(String.valueOf(search.get("userClientGtId")));
        users.setCreateDate(DateUtil.currentDateTime());
        users.setUpdateDate(DateUtil.currentDateTime());
        int i = sysGuideAppUsersMapper.insertSelective(users);

        return users;

    }

    /**
     * 绑定手机号
     * @param phone
     * @param openId
     * @param flag
     * @return
     */
    @Override
    public SysGuideAppUsers bindPhone(String phone, String openId, Integer flag) {

        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersMapper.selectPhoneByUser(phone);
        if (!org.springframework.util.StringUtils.isEmpty(sysGuideAppUsers)){
            String threadId = null;
            if (flag == 1){
                threadId = sysGuideAppUsers.getQqId();
            }else if (flag == 2){
                threadId = sysGuideAppUsers.getWeChatId();
            }else if (flag == 3){
                threadId = sysGuideAppUsers.getAppleId();
            }
            if (!StringUtils.isEmpty(threadId)){
                throw new ExistedException("手机号已存在");
            }else{

                sysGuideAppUsers.setUpdateDate(DateUtil.currentDateTime());
                if (flag == 1){
                    sysGuideAppUsers.setQqId(openId);
                }else if (flag ==2){
                    sysGuideAppUsers.setWeChatId(openId);
                }else if (flag == 3){
                    sysGuideAppUsers.setAppleId(openId);
                }
                sysGuideAppUsers.setLonginTokenId(JWTUtils.sign(sysGuideAppUsers.getUserPhone(),sysGuideAppUsers.getUserId(),new Date().toString()));
                int i = sysGuideAppUsersMapper.updateByPrimaryKeySelective(sysGuideAppUsers);
                if (i==0){
                    throw new InformationErrorException("绑定失败");
                }
                return sysGuideAppUsers;

            }
        }else{

            SysGuideAppUsers sysGuideAppUsersNew = new SysGuideAppUsers();
            sysGuideAppUsersNew.setUserId(IdUtils.getSeqId());
            if (flag == 1){
                sysGuideAppUsersNew.setQqId(openId);
            }else if (flag ==2){
                sysGuideAppUsersNew.setWeChatId(openId);
            }else if (flag == 3){
                sysGuideAppUsersNew.setAppleId(openId);
            }
            sysGuideAppUsersNew.setCreateDate(DateUtil.currentDateTime());
            sysGuideAppUsersNew.setUserPhone(phone);
            sysGuideAppUsersNew.setUserName(phone.substring(0, 3) + phone.substring(7));
            sysGuideAppUsersNew.setUpdateDate(DateUtil.currentDateTime());
            sysGuideAppUsersNew.setLonginTokenId(JWTUtils.sign(phone,sysGuideAppUsersNew.getUserId(),new Date().toString()));
            int i = sysGuideAppUsersMapper.insertSelective(sysGuideAppUsersNew);
            if (i == 0) {
                throw new InformationErrorException("绑定失败");
            }
            return sysGuideAppUsersNew;
        }
    }

    /**
     * 第三方登录
     * @param loginDTO
     * @return
     */
    @Override
    public SysGuideAppUsers thirdLogin(ThirdPartyLoginDTO loginDTO) {

        Map<String, Object> search = new HashMap<>();
        SysGuideAppUsers sysGuideAppUsers = new SysGuideAppUsers();
        if (!StringUtils.isEmpty(loginDTO.getQq())){
            search.put("qq",loginDTO.getQq());
        }
        if (!StringUtils.isEmpty(loginDTO.getWechatId())){
            search.put("weChatId",loginDTO.getWechatId());
        }

        List<SysGuideAppUsers> list = sysGuideAppUsersMapper.selectBySearch(search);

        if (CollectionUtil.isEmpty(list)){
            throw new UniversalException(Constant.LOGIN_FAILURE, "未绑定手机号");
        }else if (CollectionUtil.isNotEmpty(list)){

             sysGuideAppUsers = list.get(0);
            sysGuideAppUsers.setUpdateDate(DateUtil.currentDateTime());
            sysGuideAppUsers.setLonginTokenId(JWTUtils.sign(sysGuideAppUsers.getUserName(),sysGuideAppUsers.getUserId(),new Date().toString()));

            int i = sysGuideAppUsersMapper.updateByPrimaryKeySelective(sysGuideAppUsers);

            if (i!=0){
                return sysGuideAppUsers;
            }

        }
        return sysGuideAppUsers;
    }

    /**
     * apple登录
     * @param sysGuideAppUsers
     * @return
     */
    @Override
    public SysGuideAppUsers appleSignIn(SysGuideAppUsers sysGuideAppUsers) {

        Map<String, Object> search = new HashMap<>();
        String appleId = sysGuideAppUsers.getAppleId();

        search.put("appleId",appleId);

        List<SysGuideAppUsers> sysGuideAppUsersList = sysGuideAppUsersMapper.selectBySearch(search);

        if (StringUtils.isEmpty(sysGuideAppUsersList)){

            SysGuideAppUsers sysGuideAppUsersNew = new SysGuideAppUsers();
            Long seqId = IdUtils.getSeqId();
            sysGuideAppUsersNew.setUserId(seqId);
            String tokenSign = JWTUtils.sign(appleId, sysGuideAppUsersNew.getUserId(), new Date().toString());
            sysGuideAppUsersNew.setLonginTokenId(tokenSign);
            sysGuideAppUsersNew.setCreateDate(DateUtil.currentDateTime());
            sysGuideAppUsersNew.setUpdateDate(DateUtil.currentDateTime());
            sysGuideAppUsersNew.setUserName(RandomUtil.randomNumbers(4));

            int i = sysGuideAppUsersMapper.insertSelective(sysGuideAppUsersNew);
            search = new HashMap<>();

            search.put("userId",seqId);
            List<SysGuideAppUsers> sysGuideAppUsersNewT = sysGuideAppUsersMapper.selectBySearch(search);
            return sysGuideAppUsersNewT.get(0);
        }

        SysGuideAppUsers sysGuideAppUsersNew = sysGuideAppUsersList.get(0);
        sysGuideAppUsersNew.setUpdateDate(DateUtil.currentDateTime());
        String sign = JWTUtils.sign(sysGuideAppUsersNew.getUserName(), sysGuideAppUsersNew.getUserId(), new Date().toString());
        sysGuideAppUsersNew.setLonginTokenId(sign);

        int i = sysGuideAppUsersMapper.updateByPrimaryKeySelective(sysGuideAppUsersNew);

        return sysGuideAppUsersNew;


    }

    /**
     * 根据手机号查询是否已存在用户
     * @param phoneNew
     * @return
     * 张
     */
    @Override
    public SysGuideAppUsers selectPhoneByUser(String phoneNew) {

        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersMapper.selectPhoneByUser(phoneNew);
        return sysGuideAppUsers;
    }

    /**
     * 根据用户uid获取详细信息
     * @param uid
     * @return
     */
    @Override
    public SysGuideAppUsers userDetails(String uid) {
        SysGuideAppUsers appUsers = sysGuideAppUsersMapper.getUid(Long.valueOf(uid));
        if (!StringUtils.isEmpty(appUsers)){
            appUsers.setQqId("");
            appUsers.setWeChatId("");
            appUsers.setAppleId("");
            appUsers.setPhoneSign("");
        }
        return appUsers;
    }
}
