package com.jxzy.AppMigration.NavigationApp.Service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyCollectionService;
import com.jxzy.AppMigration.NavigationApp.Service.SysStrategyLikeService;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserFansService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppUsersMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysStrategyCollectionMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysStrategyLikeMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserFansMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.base.ThirdPartyLoginDTO;
import com.jxzy.AppMigration.NavigationApp.entity.example.UserExample;
import com.jxzy.AppMigration.NavigationApp.exception.ExistedException;
import com.jxzy.AppMigration.NavigationApp.exception.ForBiddenException;
import com.jxzy.AppMigration.NavigationApp.exception.InformationErrorException;
import com.jxzy.AppMigration.NavigationApp.exception.UniversalException;
import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.JWTUtils;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;

import io.swagger.models.auth.In;
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
    @Autowired
    private SysStrategyLikeMapper sysStrategyLikeMapper;

    @Autowired
    private SysStrategyCollectionMapper sysStrategyCollectionMapper;

    @Autowired
    private SysUserFansMapper sysUserFansMapper;


    /**
     * 查询令牌失效性
     *
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
     *
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
     *
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
     *
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
     *
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
        users.setUserName("游娱go用户" + ((String) search.get("userPhone")).substring(7));
        users.setCreateDate(DateUtil.currentDateTime());
        users.setUpdateDate(DateUtil.currentDateTime());
        int i = sysGuideAppUsersMapper.insertSelective(users);

        return users;

    }

    /**
     * 绑定手机号
     *
     * @param phone
     * @param openId
     * @param flag
     * @return
     */
    @Override
    public SysGuideAppUsers bindPhone(String phone, String openId, Integer flag) {

        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersMapper.selectPhoneByUser(phone);
        if (!org.springframework.util.StringUtils.isEmpty(sysGuideAppUsers)) {
            String threadId = null;
            if (flag == 1) {
                threadId = sysGuideAppUsers.getQqId();
            } else if (flag == 2) {
                threadId = sysGuideAppUsers.getWeChatId();
            } else if (flag == 3) {
                threadId = sysGuideAppUsers.getAppleId();
            }
            if (!StringUtils.isEmpty(threadId)) {
                throw new ExistedException("手机号已存在");
            } else {

                sysGuideAppUsers.setUpdateDate(DateUtil.currentDateTime());
                if (flag == 1) {
                    sysGuideAppUsers.setQqId(openId);
                } else if (flag == 2) {
                    sysGuideAppUsers.setWeChatId(openId);
                } else if (flag == 3) {
                    sysGuideAppUsers.setAppleId(openId);
                }
                sysGuideAppUsers.setLonginTokenId(JWTUtils.sign(sysGuideAppUsers.getUserPhone(), sysGuideAppUsers.getUserId(), new Date().toString()));
                int i = sysGuideAppUsersMapper.updateByPrimaryKeySelective(sysGuideAppUsers);
                if (i == 0) {
                    throw new InformationErrorException("绑定失败");
                }
                return sysGuideAppUsers;
            }
        } else {

            SysGuideAppUsers sysGuideAppUsersNew = new SysGuideAppUsers();
            sysGuideAppUsersNew.setUserId(IdUtils.getSeqId());
            if (flag == 1) {
                sysGuideAppUsersNew.setQqId(openId);
            } else if (flag == 2) {
                sysGuideAppUsersNew.setWeChatId(openId);
            } else if (flag == 3) {
                sysGuideAppUsersNew.setAppleId(openId);
            }
            sysGuideAppUsersNew.setCreateDate(DateUtil.currentDateTime());
            sysGuideAppUsersNew.setUserPhone(phone);
            sysGuideAppUsersNew.setUserName(phone.substring(0, 3) + phone.substring(7));
            sysGuideAppUsersNew.setUpdateDate(DateUtil.currentDateTime());
            sysGuideAppUsersNew.setLonginTokenId(JWTUtils.sign(phone, sysGuideAppUsersNew.getUserId(), new Date().toString()));
            int i = sysGuideAppUsersMapper.insertSelective(sysGuideAppUsersNew);
            if (i == 0) {
                throw new InformationErrorException("绑定失败");
            }
            return sysGuideAppUsersNew;
        }
    }

    /**
     * 第三方登录
     *
     * @param loginDTO
     * @return
     */
    @Override
    public SysGuideAppUsers thirdLogin(ThirdPartyLoginDTO loginDTO) {

        Map<String, Object> search = new HashMap<>();
        SysGuideAppUsers sysGuideAppUsers = new SysGuideAppUsers();
        if (!StringUtils.isEmpty(loginDTO.getQq())) {
            search.put("qq", loginDTO.getQq());
        }
        if (!StringUtils.isEmpty(loginDTO.getWechatId())) {
            search.put("weChatId", loginDTO.getWechatId());
        }

        List<SysGuideAppUsers> list = sysGuideAppUsersMapper.selectBySearch(search);

        if (CollectionUtil.isEmpty(list)) {
            throw new UniversalException(Constant.LOGIN_FAILURE, "未绑定手机号");
        } else if (CollectionUtil.isNotEmpty(list)) {

            sysGuideAppUsers = list.get(0);
            sysGuideAppUsers.setUpdateDate(DateUtil.currentDateTime());
            sysGuideAppUsers.setLonginTokenId(JWTUtils.sign(sysGuideAppUsers.getUserName(), sysGuideAppUsers.getUserId(), new Date().toString()));

            int i = sysGuideAppUsersMapper.updateByPrimaryKeySelective(sysGuideAppUsers);

            if (i != 0) {
                return sysGuideAppUsers;
            }

        }
        return sysGuideAppUsers;
    }

    /**
     * apple登录
     *
     * @param sysGuideAppUsers
     * @return
     */
    @Override
    public SysGuideAppUsers appleSignIn(SysGuideAppUsers sysGuideAppUsers) {

        Map<String, Object> search = new HashMap<>();
        String appleId = sysGuideAppUsers.getAppleId();

        search.put("appleId", appleId);

        List<SysGuideAppUsers> sysGuideAppUsersList = sysGuideAppUsersMapper.selectBySearch(search);

        if (StringUtils.isEmpty(sysGuideAppUsersList)) {

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

            search.put("userId", seqId);
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
     *
     * @param phoneNew
     * @return 张
     */
    @Override
    public SysGuideAppUsers selectPhoneByUser(String phoneNew) {

        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersMapper.selectPhoneByUser(phoneNew);
        return sysGuideAppUsers;
    }

    /**
     * 根据用户uid获取详细信息
     *
     * @param uid
     * @return
     */
    @Override
    public SysGuideAppUsers userDetails(String uid) {
        SysGuideAppUsers appUsers = sysGuideAppUsersMapper.getUid(Long.valueOf(uid));
        if (!StringUtils.isEmpty(appUsers)) {
            if (StringUtils.isEmpty(appUsers.getUserName())) {
                appUsers.setUserName("");
            }
            if (StringUtils.isEmpty(appUsers.getUserGender())) {
                appUsers.setUserGender("");
            }
            if (StringUtils.isEmpty(appUsers.getUserPhone())) {
                appUsers.setUserPhone("");
            }
            appUsers.setQqId("");
            appUsers.setWeChatId("");
            appUsers.setAppleId("");
            appUsers.setPhoneSign("");
        }

        //获取用户点赞数
        Integer likeNumber = sysStrategyLikeMapper.selectUidByLikeNumber(uid);
        //获取用户粉丝数
        Integer fansNumber = sysUserFansMapper.selectByFansNumber(uid);
        //获取用户关注数
        Integer followNumber = sysUserFansMapper.selectByFollowNumber(uid);

        appUsers.setLikeNumber(likeNumber);
        appUsers.setFansNumber(fansNumber);
        appUsers.setCollectionNumber(followNumber);
        return appUsers;
    }

    /**
     * 后台管理——用户列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param phone
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysGuideAppUsersList(Integer pageNum, Integer pageSize, String phone, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysGuideAppUsers> sysGuideAppUsers = sysGuideAppUsersMapper.selectBySearch(search);
        if (sysGuideAppUsers.size() > 0) {
            PageInfo<SysGuideAppUsers> pageInfo = new PageInfo<>(sysGuideAppUsers);
            pageDataResult.setData(sysGuideAppUsers);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 用户下拉选
     *
     * @return
     */
    @Override
    public List<SysGuideAppUsers> getSysGuideAppUsersDrop() {

        List<SysGuideAppUsers> list = sysGuideAppUsersMapper.getSysGuideAppUsersDrop();
        return list;
    }

    @Override
    public int delSysGuideAppusers(Long userId) {

        int i = sysGuideAppUsersMapper.deleteByPrimaryKey(userId);

        return i;
    }

    /**
     * 来访次数相加
     *
     * @param uid
     * @return
     */
    @Override
    public int addUserVisit(String uid) {

        int i = sysGuideAppUsersMapper.addUserVisit(uid);
        return i;

    }

    /**
     * 根据用户id，获取个推id
     *
     * @param userId
     * @return
     */
    @Override
    public String getUserIdByUserClientGtId(String userId) {

        String userClientGtId = sysGuideAppUsersMapper.getUserIdByUserClientGtId(userId);

        return userClientGtId;
    }

    /**
     * 扫码小程序端，登录同步到游娱go端登录 （添加一个用户信息）
     *
     * @param phone
     * @param openId
     * @return
     */
    @Override
    public int addRobotUserId(String phone, String openId, String userName) {

        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersMapper.selectPhoneByUser(phone);
        int i = 0;

        if (StringUtils.isEmpty(sysGuideAppUsers)) {
            sysGuideAppUsers = new SysGuideAppUsers();
            sysGuideAppUsers.setUserId(IdUtils.getSeqId());
            sysGuideAppUsers.setUserPhone(phone);
            String token = JWTUtils.sign(phone, sysGuideAppUsers.getUserId(), new Date().toString());
            sysGuideAppUsers.setLonginTokenId(token);
            sysGuideAppUsers.setWeChatId(openId);
            if (userName == null) {
                sysGuideAppUsers.setUserName("游娱go用户" + (phone.substring(7)));
            } else {
                sysGuideAppUsers.setUserName(userName);
            }
            sysGuideAppUsers.setCreateDate(DateUtil.currentDateTime());
            sysGuideAppUsers.setUpdateDate(DateUtil.currentDateTime());
            i = sysGuideAppUsersMapper.insertSelective(sysGuideAppUsers);

        }
        return i;
    }


    @Override
    public int insertUser(SysGuideAppUsers sysGuideAppUsers) {
        return sysGuideAppUsersMapper.insert(sysGuideAppUsers);
    }

    @Override
    public SysGuideAppUsers selectOpenIdByUser(String openid) {
        return sysGuideAppUsersMapper.selectOpenIdByUser(openid);
    }

    @Override
    public ReturnModel updateById(Long userId, String userName, String userPhone, String userClientGtId) {
        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersMapper.selectPhoneByUser(userPhone);
        ReturnModel returnModel = new ReturnModel();

        if (sysGuideAppUsers != null && sysGuideAppUsers.getWeChatId() != null) {
            returnModel.setMsg("手机号已被绑定");
            returnModel.setState(Constant.STATE_FAILURE);
        } else {
            SysGuideAppUsers sysGuideAppUsersToUpdate = new SysGuideAppUsers();
            String token = JWTUtils.sign(userPhone, userId, new Date().toString());

            if (sysGuideAppUsers != null) {
                // 当手机号已存在时更新绑定信息
                sysGuideAppUsersToUpdate.setUserId(sysGuideAppUsers.getUserId());
                sysGuideAppUsersToUpdate.setLonginTokenId(token);
                sysGuideAppUsersToUpdate.setUserClientGtId(userClientGtId);
                sysGuideAppUsersToUpdate.setUpdateDate(DateUtil.currentDateTime());
                sysGuideAppUsersToUpdate.setUserPhone(userPhone);
            } else {
                // 当手机号不存在时插入绑定信息
                sysGuideAppUsersToUpdate.setLonginTokenId(token);
                sysGuideAppUsersToUpdate.setUpdateDate(DateUtil.currentDateTime());
                sysGuideAppUsersToUpdate.setUserName(userName);
                sysGuideAppUsersToUpdate.setUserPhone(userPhone);
            }

            int i = sysGuideAppUsersMapper.updateById(sysGuideAppUsersToUpdate);

            if (i == 1) {
                returnModel.setMsg("绑定成功");
                returnModel.setState(Constant.STATE_SUCCESS);
            } else {
                returnModel.setMsg("绑定失败");
                returnModel.setState(Constant.STATE_FAILURE);
            }
        }
        returnModel.setData("");
        return returnModel;
    }


    @Override
    public ReturnModel delectWX(Long userId, String userPhone) {
        ReturnModel returnModel = new ReturnModel();
        String token = JWTUtils.sign(userPhone, userId, new Date().toString());
        SysGuideAppUsers users = new SysGuideAppUsers();
        users.setLonginTokenId(token);
        users.setUserId(userId);
        users.setUserPhone(userPhone);
        int i = sysGuideAppUsersMapper.updateById(users);
        if (i == 0) {
            returnModel.setMsg("解绑失败");
            returnModel.setState(Constant.STATE_FAILURE);
        } else {
            returnModel.setMsg("解绑成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        }
        returnModel.setData("");
        return returnModel;
    }
}

