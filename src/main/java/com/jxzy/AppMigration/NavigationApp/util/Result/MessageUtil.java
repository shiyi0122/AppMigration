package com.jxzy.AppMigration.NavigationApp.util.Result;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
    private static MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    public static String get(String code) {
        try {
            return messageSource.getMessage(code, (Object[])null, LocaleContextHolder.getLocale());
        } catch (Exception var2) {
            return code;
        }
    }

    public static String get(String code, Object... args) {
        try {
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception var3) {
            return code;
        }
    }
}
