package com.jxzy.AppMigration.NavigationApp.util.Result;

public enum ResultCode {
    SUCCESS(200, "operate.result.success"),
    FAILURE(500, "operate.result.failure"),
    PERMISSION_NO_ACCESS(401, "no.access"),
    DATABASE_ERROR(1001, "database.operate.exception"),
    PARAM_IS_INVALID(1002, "invalid.parameter"),
    SYSTEM_INNER_ERROR(1003, "system.error.try.later");

    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ResultCode item = var1[var3];
            if (item.name().equals(name)) {
                return item.message;
            }
        }

        return name;
    }

    public static Integer getCode(String name) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ResultCode item = var1[var3];
            if (item.name().equals(name)) {
                return item.code;
            }
        }

        return null;
    }
}

