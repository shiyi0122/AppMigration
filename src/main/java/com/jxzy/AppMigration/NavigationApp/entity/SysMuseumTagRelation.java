package com.jxzy.AppMigration.NavigationApp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author libofan
 * @since 2023-10-30
 */
@Getter
@Setter
@TableName("sys_museum_tag_relation")
@ApiModel(value = "SysMuseumTagRelation对象", description = "")
public class SysMuseumTagRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关联表id")
    @TableId("id")
    private Long id;

    @ApiModelProperty("博物馆id")
    @TableField("museum_id")
    private Long museumId;

    @ApiModelProperty("标签id")
    @TableField("tag_id")
    private Long tagId;


}
