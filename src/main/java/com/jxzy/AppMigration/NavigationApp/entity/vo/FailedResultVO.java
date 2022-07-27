package com.jxzy.AppMigration.NavigationApp.entity.vo;


import com.jxzy.AppMigration.NavigationApp.util.Constant;
import com.jxzy.AppMigration.NavigationApp.util.ReturnModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FailedResultVO extends ReturnModel {

    public FailedResultVO(){
        super("", "访问出错", Constant.STATE_FAILURE, "error");
    }

    public FailedResultVO(Object data){
        super("", "访问出错", Constant.STATE_FAILURE, "error");
    }
}