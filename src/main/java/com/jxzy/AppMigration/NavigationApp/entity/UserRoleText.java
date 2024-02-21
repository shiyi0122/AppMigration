package com.jxzy.AppMigration.NavigationApp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserRoleText对象", description="表")
public class UserRoleText implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "用户手机号")
    @TableField("user_phone")
    private String userPhone;

    @ApiModelProperty(value = "用户身份 0员工 1来宾 2高管")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("update_time")
    private String updateTime;
}
