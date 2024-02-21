package com.jxzy.AppMigration.NavigationApp.util.Result;


import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(
        value = "Result",
        description = "通用返回结果"
)



    public class Result<T> implements Serializable {
        private static final long serialVersionUID = 1332083524709890293L;
        @ApiModelProperty("内部状态编码")
        private Integer code;
        @ApiModelProperty("提示消息")
        private String message;
        @ApiModelProperty("数据对象")
        private T data;
        @ApiModelProperty("操作是否成功")
        private Boolean success = true;
        @ApiModelProperty("用户登录成功token信息")
        private String token;

        public Result() {
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Boolean getSuccess() {
            return this.success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getToken() {
            return this.token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static Result success() {
            return success((String) MessageUtil.get(ResultCode.SUCCESS.message()), (Object)null);
        }

        public static Result success(String msg) {
            return success((String)msg, (Object)null);
        }

        public static <T> Result<T> success(String msg, T object) {
            Result result = new Result();
            result.setCode(ResultCode.SUCCESS.code());
            result.setMessage(msg);
            result.setData(object);
            result.setSuccess(true);
            return result;
        }

        public static Result success(Integer code, String message) {
            Result result = new Result();
            result.setCode(code);
            result.setMessage(message);
            result.setSuccess(true);
            return result;
        }

        public static Result failure() {
            return failure((String)MessageUtil.get(ResultCode.FAILURE.message()), (Object)null);
        }

        public static Result failure(String msg) {
            return failure((String)msg, (Object)null);
        }

        public static <T> Result<T> failure(String msg, T object) {
            Result<T> result = new Result();
            result.setCode(ResultCode.FAILURE.code());
            result.setMessage(msg);
            result.setSuccess(false);
            result.setData(object);
            return result;
        }

        public static Result failure(Integer code, String message) {
            Result result = new Result();
            result.setCode(code);
            result.setMessage(message);
            result.setSuccess(false);
            return result;
        }

        public static <T> Result<T> result(T operateResult) {
            Result<T> result = new Result();
            if (operateResult == null) {
                result.setSuccess(false);
                result.setCode(ResultCode.FAILURE.code());
                result.setMessage(MessageUtil.get(ResultCode.FAILURE.message()));
            } else {
                result.setCode(ResultCode.SUCCESS.code());
                result.setMessage(MessageUtil.get(ResultCode.SUCCESS.message()));
                result.setSuccess(true);
                result.setData(operateResult);
                if (operateResult instanceof Boolean) {
                    result.setData((T) null);
                    Boolean b = (Boolean)operateResult;
                    if (!b) {
                        result.setSuccess(false);
                        result.setCode(ResultCode.FAILURE.code());
                        result.setMessage(MessageUtil.get(ResultCode.FAILURE.message()));
                    }
                }

                if (operateResult instanceof Integer) {
                    result.setData((T) null);
                    Integer i = (Integer)operateResult;
                    if (i <= 0) {
                        result.setSuccess(false);
                        result.setCode(ResultCode.FAILURE.code());
                        result.setMessage(MessageUtil.get(ResultCode.FAILURE.message()));
                    }
                }
            }

            return result;
        }

        public static <T> Result<T> code(ResultCode resultCode, T object) {
            if (resultCode == null) {
                return result(object);
            } else {
                return resultCode.equals(ResultCode.SUCCESS) ? success(resultCode.code(), MessageUtil.get(resultCode.message(), new Object[]{object})) : failure(resultCode.code(), MessageUtil.get(resultCode.message(), new Object[]{object}));
            }
        }

        public static Result code(ResultCode resultCode) {
            if (resultCode == null) {
                return failure();
            } else {
                return resultCode.equals(ResultCode.SUCCESS) ? success(resultCode.code(), MessageUtil.get(resultCode.message())) : failure(resultCode.code(), MessageUtil.get(resultCode.message()));
            }
        }

        public String toString() {
            return "Result{code=" + this.code + ", message='" + this.message + '\'' + ", data=" + this.data + '}';
        }

        public String toJSONString() {
            return (new GsonBuilder()).create().toJson(this);
        }
    }


