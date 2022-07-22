package com.jxzy.AppMigration.NavigationApp.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dypnsapi.model.v20170525.GetMobileRequest;
import com.aliyuncs.dypnsapi.model.v20170525.GetMobileResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.util.*;
import com.jxzy.AppMigration.common.utils.AuroraOneClickLogin;
import com.jxzy.AppMigration.common.utils.CacheManagers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "导览APP登录相关功能")
@RestController
@RequestMapping("login")
public class GuideAppLoginController extends PublicUtil {
    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;

    @ApiOperation("号码认证服务(暂停)")
    @GetMapping("authentication")
    public String getPhone(String token) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");//自己阿里云的配置
        IAcsClient client =new DefaultAcsClient(profile);
        GetMobileRequest request = new GetMobileRequest();
        request.setAccessToken(token);//app端传过来的，需要用户授权拿到
        request.setRegionId("cn-hangzhou");
        String phone = null;
        try {
            GetMobileResponse response = client.getAcsResponse(request);
            if("OK".equals(response.getCode())) {
                phone = response.getGetMobileResultDTO().getMobile();
            };
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return phone;
    }

    /**
     * 一键登录认证
     * @param: phoneSign
     * @param: userClientGtId
     * @param: token 换取极光手机号token
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    @ApiOperation("一键登录认证")
    @PostMapping("/oneClickLogin")
    @ApiImplicitParams({@ApiImplicitParam(name ="phoneSign", value = "手机唯一标识",dataType="string", required = true),
            @ApiImplicitParam(name ="token", value = "一键登录令牌",dataType="String", required = true),
            @ApiImplicitParam(name ="userClientGtId", value = "推送ID",dataType="String", required = true)})
    public ReturnModel oneClickLogin(String phoneSign, String userClientGtId, String token) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        try {
            String userPhone = AuroraOneClickLogin.decrypt(token);
            System.out.println("当前极光解密手机号" + userPhone);
            search.put("phoneSign", phoneSign);
            search.put("userClientGtId", userClientGtId);
            search.put("userPhone", userPhone);
            SysGuideAppUsers user = sysGuideAppUsersService.getPhoneSign(search);
            System.out.println(user+"当前用户信息+++++++++++++++++++++++++++++一键登录");
            if (user != null) {
                if (StringUtil.isEmpty(user.getUserPhone()) && userPhone.equals(user.getUserPhone())) {
                    returnModel.setData(user);
                    returnModel.setMsg("成功获取用户信息！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }
            }

            int register;
            if (user != null) {
                register = sysGuideAppUsersService.updateRegister(user, search);
            } else {
                register = sysGuideAppUsersService.insetOneClickLoginRegister(search);
            }
            if (register > 0) {
                SysGuideAppUsers users = sysGuideAppUsersService.getPhoneSign(search);
                System.out.println(user+"当前用户信息————————————————————————————————————一键登录");
                returnModel.setData(users);
                returnModel.setMsg("一键登录成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
            } else {
                returnModel.setMsg("一键登录失败！");
                returnModel.setState(Constant.STATE_FAILURE);
            }
            return returnModel;
        } catch (Exception e) {
            logger.info("loginOrVisitor", e);
            returnModel.setData("");
            returnModel.setMsg("失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    /**
     * 登录或游客模式注册
     * @param: phoneSign
     * @param: userClientGtId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    @ApiOperation("登录或游客模式注册")
    @PostMapping("/loginOrVisitor")
    @ApiImplicitParams({@ApiImplicitParam(name ="phoneSign", value = "手机唯一标识",dataType="string", required = true),
            @ApiImplicitParam(name ="userClientGtId", value = "个推ID",dataType="string", required = true)})
    public ReturnModel loginOrVisitor(String phoneSign, String userClientGtId) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        try {
            search.put("phoneSign",phoneSign);
            search.put("userClientGtId",userClientGtId);
            SysGuideAppUsers user = sysGuideAppUsersService.getPhoneSign(search);
            if (user != null) {
                returnModel.setData(user);
                returnModel.setMsg("成功获取用户信息！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }
            int inset = sysGuideAppUsersService.insetRegister(search);
            if (inset > 0) {
                SysGuideAppUsers users = sysGuideAppUsersService.getPhoneSign(search);
                returnModel.setData(users);
                returnModel.setMsg("游客注册成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
            }else {
                returnModel.setMsg("游客注册失败！");
                returnModel.setState(Constant.STATE_FAILURE);
            }
            return returnModel;
        } catch (Exception e) {
            logger.info("loginOrVisitor", e);
            returnModel.setData("");
            returnModel.setMsg("失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 发送验证码
     * @param: phone
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/15 0015
     */
    @ApiOperation("短信验证")
    @PostMapping("/sendPhoneCode")
    @ApiImplicitParams({@ApiImplicitParam(name ="phone", value = "手机号",dataType="string", required = true)})
    public ReturnModel sendPhoneCode(String phone) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        try {
            int random=(int)(Math.random()*1000000.0);
            String code = String.format("%06d", random);
            String key = MD5Util.getMD5Str(phone+code+"0");
            SendSMS sms = new SendSMS();
            boolean sendPhoneCode = sms.sendPhoneCode(key, phone, code);
            if (sendPhoneCode) {
                returnModel.setData(key);
                returnModel.setMsg("发送短信成功");
                returnModel.setState(Constant.STATE_SUCCESS);
            }else {
                returnModel.setData("");
                returnModel.setMsg("发送短信失败，请稍后重试！");
                returnModel.setState(Constant.STATE_FAILURE);
            }
            return returnModel;//加密返回
        } catch (Exception e) {
            logger.info("loginOrVisitor", e);
            returnModel.setData("");
            returnModel.setMsg("失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 短信登录注册
     * @param: userPhone
     * @param: code
     * @param: key
     * @param: phoneSign
     * @param: userClientGtId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei，zhang
     * @date: 2021/11/15 0015
     */
    @ApiOperation("短信验证注册登录")
    @PostMapping("/userRegister")
    @ApiImplicitParams({@ApiImplicitParam(name ="userPhone", value = "手机号",dataType="string", required = true),
            @ApiImplicitParam(name ="code", value = "验证码",dataType="string", required = true),
            @ApiImplicitParam(name ="key", value = "公钥",dataType="string", required = true),
            @ApiImplicitParam(name ="phoneSign", value = "手机唯一标识",dataType="string", required = true),
            @ApiImplicitParam(name ="userClientGtId", value = "个推ID",dataType="string", required = true)})
    public ReturnModel userRegister(String userPhone, String code, String key, String phoneSign, String userClientGtId) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        try {
            if (!code.equals(CacheManagers.getData(key))) {
                returnModel.setData("");
                returnModel.setMsg("验证码不正确或已失效！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;//加密返回
            }
            System.out.println(phoneSign+"当前是手机唯一标识￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
            search.put("phoneSign", phoneSign);
            search.put("userClientGtId", userClientGtId);
            search.put("userPhone", userPhone);

            SysGuideAppUsers user = sysGuideAppUsersService.getPhoneSign(search);

            System.out.println(user+"当前用户信息+++++++++++++++++++++++++++++");
            if (user != null) {
                String token = JWTUtils.sign((String) search.get("userPhone"), user.getUserId(), new Date().toString());
                user.setLonginTokenId(token);
                int i = sysGuideAppUsersService.updateAppUsers(user);

                if (StringUtil.isEmpty(user.getUserPhone()) && userPhone.equals(user.getUserPhone())) {
                    returnModel.setData(user);
                    returnModel.setMsg("成功获取用户信息！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }
            }else{
                user = sysGuideAppUsersService.insertAppUser(search);
                returnModel.setData(user);
                returnModel.setMsg("注册成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
            }

            return returnModel;
        } catch (Exception e) {
            logger.info("loginOrVisitor", e);
            returnModel.setData("");
            returnModel.setMsg("失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 绑定手机号
     */
    @PostMapping("/bindPhone")
    public ReturnModel bindPhone(@RequestBody @NotNull String params) {

        ReturnModel returnModel = new ReturnModel();

        JSONObject jsonObject = JSONObject.parseObject(params);
        String phone = jsonObject.getString("phone");
        String openId = jsonObject.getString("openId");
        String key = jsonObject.getString("key");
        String code = jsonObject.getString("code");
        Integer flag = jsonObject.getInteger("flag");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(openId) || StringUtils.isEmpty(flag) || StringUtils.isEmpty(code) || StringUtils.isEmpty(flag)){
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData("");
            returnModel.setMsg("请求参数有误!");
        }

        if (!code.equals(CacheManagers.getData(key))) {
            returnModel.setData("");
            returnModel.setMsg("验证码不正确或已失效！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

        SysGuideAppUsers sysGuideAppUsers = sysGuideAppUsersService.bindPhone(phone,openId,flag);


        returnModel.setData(sysGuideAppUsers);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("绑定成功！");
        return returnModel;
    }


}
