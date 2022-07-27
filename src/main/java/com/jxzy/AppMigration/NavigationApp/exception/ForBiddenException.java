package com.jxzy.AppMigration.NavigationApp.exception;

/**
 * @author Dong.w
 */
public class ForBiddenException extends RuntimeException {

    public ForBiddenException() {
        super("权限不足");
    }

}
