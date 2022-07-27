package com.jxzy.AppMigration.NavigationApp.exception;

import com.jxzy.AppMigration.NavigationApp.util.Constant;


/**
 * @author Dong.w
 */
public class UniversalException extends RuntimeException {

    private Constant type;

    public UniversalException(Constant type, String message) {
        super(message);
        this.type = type;
    }

    public Constant getType() {
        return type;
    }
}
