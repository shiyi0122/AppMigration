package com.jxzy.AppMigration.NavigationApp.entity.dto;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 获取分页数据DTO
 *
 * @author
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PageDTO extends BaseDTO {

    @Max(Long.MAX_VALUE)
    @Min(1)
    private Long id;

    @Max(Integer.MAX_VALUE)
    @Min(1)
    @NotNull
    private Integer pageNum;

    @Max(Integer.MAX_VALUE)
    @Min(1)
    @NotNull
    private Integer pageSize;

    //经度
    private String lng;
    //纬度
    private String lat;

    //排序
    private Integer sort;

    private String spotName;

    private Long cityId;




}
