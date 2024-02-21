package com.jxzy.AppMigration.NavigationApp.util.Result;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface IBaseMapper<T> extends BaseMapper<T> {
    int alwaysUpdateSomeColumnById(@Param("et") T var1);

    int updateBatchByIdReal(Collection<T> var1);

    int saveBatchReal(Collection<T> var1);

    int insertBatchSomeColumn(Collection<T> var1);
}
