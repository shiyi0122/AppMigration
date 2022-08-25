package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDtoConvert;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/3 18:20
 */
@Data
public class SysScenicRankingList extends BaseDTO {

    private Long  id ;

    private String content;

    private Long spotId;

    private String type;

    private String createTime;

    private String updateTime;

    private String pictureUrl;
}
