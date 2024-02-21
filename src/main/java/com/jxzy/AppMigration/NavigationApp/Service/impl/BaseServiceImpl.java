package com.jxzy.AppMigration.NavigationApp.Service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.Service.BaseService;
import com.jxzy.AppMigration.NavigationApp.util.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Transactional
@Service("baseService")
public class BaseServiceImpl implements BaseService {



    @DS("master2")
    @Override
    public Object getObjById(Long id, Class clazz) throws Exception {
        String className = clazz.getName();
        String mapperName = className+"Mapper";
        mapperName = mapperName.replace("entity", "dao");
        Class<?> mapperClass = Class.forName(mapperName);
        Object bean = SpringUtils.getBean(mapperClass);
        Class<?> classN = bean.getClass();
        Method method = classN.getMethod("selectByPrimaryKey", Long.class);
        Object invoke = method.invoke(bean, id);
        return invoke;
    }

    @DS("master2")
    @Override
    public void insertObj(Object obj) throws Exception {
        Class clazz = obj.getClass();
        String className = clazz.getName();
        String mapperName = className+"Mapper";
        mapperName = mapperName.replace("entity", "dao");
        Class<?> mapperClass = Class.forName(mapperName);
        Object bean = SpringUtils.getBean(mapperClass);
        Class<?> classN = bean.getClass();
        Method method = classN.getMethod("insertSelective", clazz);
        method.invoke(bean, obj);
    }

    @DS("master2")
    @Override
    public void delObjById(Long id, Class clazz) throws Exception {
        String className = clazz.getName();
        String mapperName = className+"Mapper";
        mapperName = mapperName.replace("entity", "dao");
        Class<?> mapperClass = Class.forName(mapperName);
        Object bean = SpringUtils.getBean(mapperClass);
        Class<?> classN = bean.getClass();
        Method method = classN.getMethod("deleteByPrimaryKey",  Long.class);
        method.invoke(bean, id);
    }

    @DS("master2")
    @Override
    public void updateObj(Object obj) throws Exception {
        Class clazz = obj.getClass();
        String className = clazz.getName();
        String mapperName = className+"Mapper";
        mapperName = mapperName.replace("entity", "dao");
        Class<?> mapperClass = Class.forName(mapperName);
        Object bean = SpringUtils.getBean(mapperClass);
        Class classN = bean.getClass();
        Method method = classN.getMethod("updateByPrimaryKeySelective",  clazz);
        method.invoke(bean, obj);
    }

}
