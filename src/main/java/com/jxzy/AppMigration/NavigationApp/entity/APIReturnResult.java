package com.jxzy.AppMigration.NavigationApp.entity;

public class APIReturnResult {
    /**
     * 返回值编码
     */
    private String code;
    /**
     * 接口访问状态
     */
    private String status;
    /**
     * 返回值内容
     */
    private String msg;
    /**
     * 数据封装
     */
    private Object Data;


    public Object getData() {
        return Data;
    }
    public void setData(Object data) {
        Data = data;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * 全字段数据返回
     * @param code 接口返回编码
     * @param status 接口返回状态
     * @param msg 接口消息
     * @param data 接口返回数据
     */
    public APIReturnResult(String code, String status, String msg, Object data) {
        super();
        this.code = code;
        this.status = status;
        this.msg = msg;
        Data = data;
    }
    /**
     * 接口成功返回（消息）
     * @param msg 消息
     */
    public static APIReturnResult ok(String msg) {
        return new APIReturnResult("0000", "200", msg, null);
    }
    public static APIReturnResult ok(String code, String msg, Object data) {
        return new APIReturnResult(code, "200", msg, data);
    }
    /**
     * 接口成功返回（消息含有数据）
     * @param msg
     * @param data
     */
    public static APIReturnResult ok(String msg, Object data) {
        return new APIReturnResult("0000", "200", msg, data);
    }

    /**
     * 接口失败返回（消息）
     * @param msg 消息
     */
    public static APIReturnResult error(String msg) {
        return new APIReturnResult("-1", "500", msg, null);
    }
    /**
     * 接口失败返回（消息）
     * @param msg 消息
     */
    public static APIReturnResult error(String code, String msg) {
        return new APIReturnResult(code, "500", msg, null);
    }

    /**
     * 接口失败返回（消息含有数据）
     * @param msg 消息
     */
    public static APIReturnResult error(String msg, Object data) {
        return new APIReturnResult("-1", "200", msg, data);
    }

    /**
     * 接口错误失败返回（消息）
     * @param msg 消息
     */
    public static APIReturnResult errorvisit(String msg) {
        return new APIReturnResult("-1", "500", msg, null);
    }
    /**
     * 接口错误失败返回（消息）
     * @param msg 消息
     */
    public static APIReturnResult errorvi(String msg) {
        return new APIReturnResult("0000", "400", msg, 1);
    }
}
