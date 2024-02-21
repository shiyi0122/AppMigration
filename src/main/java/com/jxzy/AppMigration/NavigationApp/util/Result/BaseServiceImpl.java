package com.jxzy.AppMigration.NavigationApp.util.Result;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BaseServiceImpl<M extends IBaseMapper<T>, T> implements IBaseService<T> {
    @Autowired
    protected M baseMapper;

    public BaseServiceImpl() {
    }

    public M getBaseMapper() {
        return this.baseMapper;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Result<T> save(T entity) {
        int i = this.baseMapper.insert(entity);
        return i > 0 ? Result.success(MessageUtil.get(ResultCode.SUCCESS.message()), entity) : Result.failure();
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Result<T> saveOrUpdate(T entity) {
        if (entity == null) {
            return Result.failure();
        } else {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!", new Object[0]);
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!", new Object[0]);
            Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
            return !StringUtils.checkValNull(idVal) && !Objects.isNull(this.getById((Serializable)idVal)) ? this.updateById(entity) : this.save(entity);
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Result<?> removeById(Serializable id) {
        T entity = this.getById(id);
        if (entity == null) {
            return Result.failure();
        } else {
            int i = this.baseMapper.deleteById(id);
            return i > 0 ? Result.success(MessageUtil.get(ResultCode.SUCCESS.message()), entity) : Result.failure();
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean remove(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper();
        wrapper.setEntity(entity);
        return this.remove((Wrapper)wrapper);
    }

    public boolean remove(Wrapper<T> queryWrapper) {
        int i = this.baseMapper.delete(queryWrapper);
        return i > 0;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Result<?> removeByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Result.failure();
        } else {
            List<T> list = this.listByIds(idList);
            int i = this.baseMapper.deleteBatchIds(idList);
            return i > 0 ? Result.success(MessageUtil.get(ResultCode.SUCCESS.message()), list) : Result.failure();
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Result<T> updateById(T entity) {
        int i = this.baseMapper.updateById(entity);
        return i > 0 ? Result.success(MessageUtil.get(ResultCode.SUCCESS.message()), entity) : Result.failure();
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Result<T> updateByIdIncludeNull(T entity) {
        int i = this.baseMapper.alwaysUpdateSomeColumnById(entity);
        return i > 0 ? Result.success(MessageUtil.get(ResultCode.SUCCESS.message()), entity) : Result.failure();
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean update(T dest, Map<String, Object> columnMap) {
        UpdateWrapper<T> wrapper = new UpdateWrapper();
        wrapper.allEq(columnMap, false);
        return this.update(dest, (Wrapper)wrapper);
    }

    public boolean update(T entity, Wrapper<T> updateWrapper) {
        int i = this.baseMapper.update(entity, updateWrapper);
        return i > 0;
    }

    public List<T> list(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper();
        wrapper.setEntity(entity);
        return this.list(wrapper);
    }

    public T getOne(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper();
        wrapper.setEntity(entity);
        return this.getOne(wrapper);
    }

    public <E extends IPage<T>> E page(E page, T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper();
        wrapper.setEntity(entity);
        return this.page(page, wrapper);
    }

    public int count(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper();
        wrapper.setEntity(entity);
        return this.count(wrapper);
    }

    public int saveBatchReal(Collection<T> entityList) {
        return CollUtil.isEmpty(entityList) ? 0 : this.baseMapper.saveBatchReal(entityList);
    }
}
