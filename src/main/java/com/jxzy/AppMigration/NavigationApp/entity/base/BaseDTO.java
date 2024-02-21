package com.jxzy.AppMigration.NavigationApp.entity.base;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO基类
 *
 * @author
 */
@Data
public class BaseDTO implements Serializable {

    @NotNull
    private String token;


    private String uid;


}
