package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserRole对象", description="表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户Id")
    @TableField("USER_ID")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "用户手机号")
    @TableField("USER_PHONE")
    private String userPhone;

    @ApiModelProperty(value = "用户类型 1管理员 2商户")
    @TableField("USER_TYPE")
    private String userType;

    @ApiModelProperty(value = "景区ID")
    @TableField("SCENIC_SPOT_ID")
    private String scenicSpotId;

    @ApiModelProperty(value = "景区名称")
    @TableField("SCENIC_SPOT_NAME")
    private String scenicSpotName;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private String updateTime;
}
