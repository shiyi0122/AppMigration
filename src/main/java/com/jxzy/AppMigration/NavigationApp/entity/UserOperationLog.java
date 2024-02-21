package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserOperationLog对象", description = "表")
public class UserOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "操作类型 0安装 1注册 2打开")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "景区id")
    @TableField("spot_id")
    private String spotId;

    @ApiModelProperty(value = "景区名称")
    @TableField("spot_name")
    private String spotName;

    @ApiModelProperty(value = "微信")
    @TableField("wx_name")
    private String wxName;

    @ApiModelProperty(value = "首次景区id")
    @TableField("spot_id")
    private String fristSpotId;

    @ApiModelProperty(value = "首次景区名称")
    @TableField("spot_name")
    private String fristSpotName;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    //所有数据的总和
    private String allNumber;

    //安装总和
    private String installNumber;

    //注册总和
    private String registerNumber;

    //打开总和
    private String openNumber;


}
