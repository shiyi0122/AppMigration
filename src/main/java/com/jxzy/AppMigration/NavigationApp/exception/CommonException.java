package com.jxzy.AppMigration.NavigationApp.exception;

/**
 * 自定义异常
 */
public class CommonException extends Exception {
    /**
     * 异常状态码
     */
    private int value;

    private Object data;
    public CommonException(String msg) {
        super(msg);
        this.value = -1;
        this.data = null;

    }
    public CommonException() {
        super();
    }

    public CommonException(String msg, int value, Object data){
        super(msg);
        this.value = value;
        this.data = data;
    }
    public CommonException(String msg, int value){
        super(msg);
        this.value = value;
        this.data = null;
    }

    public int getValue() {
        return value;
    }
    public Object getData() {
        return data;
    }


}
