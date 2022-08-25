package com.jxzy.AppMigration.NavigationApp.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 删除信息接收数据
 *
 * @author
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseDataDTO extends BaseDTO {

    @Max(Long.MAX_VALUE)
    @Min(1)
    private Long id;


    @Min(10000)
    private Long oUid;

    @Min(-1)
    private Short flag;


    private Integer all;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;


}
