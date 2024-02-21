package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class JWTUser {
    /**
     * 用户表ID
     * Column: ID
     */
    private Long id;

    /**
     * 用户头像id
     * Column: IMAGE_ID
     */
    private Long imageId;

    /**
     * 营业执照附件id
     * Column: LICENSE_ATTACH_ID
     */
    private Long licenseAttachId;

    /**
     * 省份ID
     * Column: PROVINCE_ID
     */
    private Integer provinceId;

    /**
     * 城市ID
     * Column: CITY_ID
     */
    private Integer cityId;

    /**
     * 区域ID
     * Column: REGION_ID
     */
    private Integer regionId;

    /**
     * 手机号
     * Column: PHONE
     */
    private String phone;

    /**
     * 用户名称
     * Column: USER_NAME
     */
    private String userName;

    /**
     * 公司名称
     * Column: COMPANY_NAME 
     */
    private String companyName;

    /**
     * 身份证号
     * Column: IDENTITY_CARD
     */
    private String identityCard;

    public String getIdentityCard() {
//        if(StringUtils.isNotEmpty(identityCard)){
//            identityCard = StringUtil.phoneReplaceWithStar(identityCard);
//        }
        return identityCard;
    }

    /**
     * 积分
     * Column: INTEGRAL
     */
    private Integer integral;

    /**
     * 微信号
     * Column: WECHAT
     */
    private String wechat;

    /**
     * 账户余额
     * Column: ACCOUNT_BALANCE
     */
    private BigDecimal accountBalance;

    /**
     * 地址
     * Column: ADDRESS
     */
    private String address;

    /**
     * 用户类型 0非合伙人 1是合伙人
     * Column: USER_TYPE
     */
    private Integer userType;

    /**
     * 景区状态 0无景区  1有景区
     * Column: SCENIC_TYPE
     */
    private Integer scenicType;

    /**
     * 图片路径
     */
    private String imagePath;

    /***
     * 营业执照
     */
    private String licenseAttachPath;

    /**
     * 用户状态：0 正常  -1禁用 -2删除 1 核验中
     * Column: USER_STATE
     */
    private Integer userState;

    /**
     * 省份name
     * Column: PROVINCE_ID
     */
    private String provinceName;

    /**
     * 城市Name
     * Column: CITY_ID
     */
    private String cityName;

    /**
     * 区域Name
     * Column: REGION_ID
     */
    private String regionName;

    /**
     * 提醒成为合伙人状态
     */
    private String partnerType;

    /**
     * 含有url的权限(权限)
     */
    private List<String> urlList = new ArrayList<>(0);
    /***
     * 等级名称
     */
    private String levelTitle;
    /***
     * 等级图片
     */
    private String levelPath;

    /**
     * 报备景区审核权限0报备端，1审核端
     */
    private String examineType;


}
