package com.jxzy.AppMigration.NavigationApp.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxzy.AppMigration.NavigationApp.entity.UserApply;

import java.util.List;

public interface UserApplyService extends IService<UserApply> {
    int addUserApply(UserApply userApply);

    List<UserApply> getByUser();
}
