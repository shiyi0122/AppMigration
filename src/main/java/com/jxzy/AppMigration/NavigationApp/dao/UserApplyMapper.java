package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.UserApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserApplyMapper extends BaseMapper<UserApply> {

    int addUserApply(UserApply userApply);

    List<UserApply> getByUser();
}

