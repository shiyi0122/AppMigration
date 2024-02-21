package com.jxzy.AppMigration.NavigationApp.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="UserRoleDTO对象", description="APP用户权限查询体")
public class UserRoleDTO {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户手机号")
    private String userPhone;

    @ApiModelProperty(value = "用户类型 1管理员 2商户")
    private String userType;

    @ApiModelProperty(value = "景区ID")
    private String scenicSpotId;

    @ApiModelProperty(value = "景区名称")
    private String scenicSpotName;
}
