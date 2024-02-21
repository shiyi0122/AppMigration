package com.jxzy.AppMigration.NavigationApp.util.Result;

public class MinioException extends CommonException {
    public MinioException() {
    }

    public MinioException(String message) {
        super(message);
    }

    public MinioException(Throwable cause) {
        super(cause);
    }

    public MinioException(String message, Throwable cause) {
        super(message, cause);
    }
}
