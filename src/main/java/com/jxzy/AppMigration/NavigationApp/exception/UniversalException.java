package com.jxzy.AppMigration.NavigationApp.exception;

import com.jxzy.AppMigration.NavigationApp.util.Constant;


/**
 * @author Dong.w
 */
public class UniversalException extends RuntimeException {

    private String type;

    public UniversalException(String type, String message) {
        super(message);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
