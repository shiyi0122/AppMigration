package com.jxzy.AppMigration.NavigationApp.entity.dto;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 搜索数据DTO
 *
 * @author
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchDTO extends BaseDTO {


    private String  spotId;

    private String city;

    private String phone;


    private String title;

    private String name;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    //省
    private String province;
    //市
    private String market;


    //经度
    private String lng;
    //纬度
    private String lat;

    private Integer sort;


}