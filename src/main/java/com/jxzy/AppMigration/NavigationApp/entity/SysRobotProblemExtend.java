package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/11/2 19:31
 */
@Data
public class SysRobotProblemExtend {

    private Long extendId;

    private Long  corpusId;

    private Long scenicSpotId;

    private String  extendCorpusProblem;

    private String  extendCorpusPinyin;

    private String  extendType;

    private String  createDate;

    private String  updateDate;

}
