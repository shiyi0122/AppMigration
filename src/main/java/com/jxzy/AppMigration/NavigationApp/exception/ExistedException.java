package com.jxzy.AppMigration.NavigationApp.exception;

/**
 * 资源存在异常
 *
 * @author Dong.w
 */
public class ExistedException extends RuntimeException {
    public ExistedException() {
        super("资源已存在");
    }

    public ExistedException(String message) {
        super(message);
    }
}
