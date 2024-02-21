package com.jxzy.AppMigration.NavigationApp.util.Result;

public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -9127122568791186301L;
    private Integer code;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
