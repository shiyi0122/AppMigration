package com.jxzy.AppMigration.NavigationApp.entity.base;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO基类
 *
 * @author Dong.w
 */
@Data
public class BaseDTO implements Serializable {

    @NotNull
    private String token;

    @Min(10000)
    private Long uid;

}
