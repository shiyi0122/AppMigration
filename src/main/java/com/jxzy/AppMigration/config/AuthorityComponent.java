package com.jxzy.AppMigration.config;

import cn.hutool.core.collection.CollectionUtil;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppUsersService;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.exception.ForBiddenException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 权限控制
 *
 * @author Dong.w
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AuthorityComponent {

    @Autowired
    private SysGuideAppUsersService sysGuideAppUsersService;


    //跳过认证接口方法
    private static final List<String> AUTH_LIST = Arrays.asList("oneClickLogin", "loginOrVisitor", "thirdLogin", "userRegister");


    //后台接口
//    @Pointcut("execution(* com.jxzy.AppMigration.NavigationApp.controller.*.*(..))")
//    public void adminPointCut() {
//    }

    //app接口
    @Pointcut("execution(* com.jxzy.AppMigration.NavigationApp.controller.*.*(..))")
    public void appPointCut() {
    }


    @Around("appPointCut()")
    public Object appAuthorityControl(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        BaseDTO data = null;
        for (Object arg : args) {
            if (arg instanceof BaseDTO) {
                data = (BaseDTO) arg;
            }
        }
        String functionName = joinPoint.getSignature().getName();
        String token = "";
        if (Objects.nonNull(data) && StringUtils.isEmpty(data.getToken())) {
            throw new ForBiddenException();
        } else if (Objects.nonNull(data)) {
            token = data.getToken();
        }
        if (isRelease(token, functionName, 0)) {
            return joinPoint.proceed(args);
        } else {
            throw new ForBiddenException();
        }
    }

//    @Around("adminPointCut()")
//    public Object adminAuthorityControl(ProceedingJoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        BaseDTO data = null;
//        for (Object arg : args) {
//            if (arg instanceof BaseDTO) {
//                data = (BaseDTO) arg;
//            }
//        }
//        try {
//            String functionName = joinPoint.getSignature().getName();
//            String token = "";
//            if (Objects.nonNull(data) && StringUtils.isEmpty(data.getToken())) {
//                throw new ForBiddenException();
//            } else if (Objects.nonNull(data)) {
//                token = data.getToken();
//            }
//            if (isRelease(token, functionName, 1)) {
//                return joinPoint.proceed(args);
//            } else {
//                throw new ForBiddenException();
//            }
//        } catch (UniversalException | ForBiddenException | InformationErrorException e) {
//            throw e;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            return new FailedResultVO();
//        }
//    }

    private boolean isRelease(String token, String method, int flag) {
        if (flag == 0) {
            return CollectionUtil.contains(AUTH_LIST, method)
                    || sysGuideAppUsersService.getContrastToken(token);
        } else {
            return CollectionUtil.contains(AUTH_LIST, method)
                    || sysGuideAppUsersService.getContrastToken(token);
        }
    }

}
