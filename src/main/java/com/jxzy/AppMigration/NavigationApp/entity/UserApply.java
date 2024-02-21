package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_apply")
@ApiModel(value = "UserApply对象", description = "")
public class UserApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户报名id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名称")
    @TableField("username")
    private String username;

    @ApiModelProperty("用户电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private String updateTime;
}
