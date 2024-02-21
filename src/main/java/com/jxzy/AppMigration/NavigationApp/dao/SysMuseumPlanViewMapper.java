package com.jxzy.AppMigration.NavigationApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumPlanView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/8/7 18:13
 */
@Mapper
public interface SysMuseumPlanViewMapper extends BaseMapper<SysMuseumPlanView> {
    List<SysMuseumPlanView> selectByMuseumId(Long id);

}

