package com.jxzy.AppMigration.NavigationApp.util.Result;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IBaseService<T> {
    IBaseMapper<T> getBaseMapper();

    Result<T> save(T var1);

    Result<T> saveOrUpdate(T var1);

    Result<?> removeById(Serializable var1);

    Result<?> removeByIds(Collection<? extends Serializable> var1);

    boolean remove(T var1);

    boolean remove(Wrapper<T> var1);

    Result<T> updateById(T var1);

    Result<T> updateByIdIncludeNull(T var1);

    boolean update(T var1, Map<String, Object> var2);

    boolean update(T var1, Wrapper<T> var2);

    default List<T> list() {
        return this.list((Wrapper) Wrappers.emptyWrapper());
    }

    List<T> list(T var1);

    default List<T> list(Wrapper<T> queryWrapper) {
        return this.getBaseMapper().selectList(queryWrapper);
    }

    default List<T> listByIds(Collection<? extends Serializable> idList) {
        return this.getBaseMapper().selectBatchIds(idList);
    }

    default T getById(Serializable id) {
        return this.getBaseMapper().selectById(id);
    }

    T getOne(T var1);

    default T getOne(Wrapper<T> queryWrapper) {
        return this.getBaseMapper().selectOne(queryWrapper);
    }

    default <E extends IPage<T>> E page(E page) {
        return this.page(page, (T) Wrappers.emptyWrapper());
    }

    <E extends IPage<T>> E page(E var1, T var2);

    default <E extends IPage<T>> E page(E page, Wrapper<T> queryWrapper) {
        return this.getBaseMapper().selectPage(page, queryWrapper);
    }

    default int count() {
        return this.count((Wrapper)Wrappers.emptyWrapper());
    }

    int count(T var1);

    default int count(Wrapper<T> queryWrapper) {
        return (int) SqlHelper.retCount((long) Math.toIntExact(this.getBaseMapper().selectCount(queryWrapper)));
    }

    int saveBatchReal(Collection<T> var1);
}
