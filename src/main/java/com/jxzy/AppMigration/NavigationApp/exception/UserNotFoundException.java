package com.jxzy.AppMigration.NavigationApp.exception;

/**
 * 用户未找到异常
 *
 * @author Dong.w
 */
public class UserNotFoundException extends RuntimeException {

    private String message;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("此用户不存在");
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
