package com.jxzy.AppMigration.NavigationApp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/9/7 19:24
 */
@Data
public class SysRobotCorpus {

    private Long corpusId;

    @Excel(name = "语义问题",orderNum = "1")
    private String  corpusProblem;
    @Excel(name = "语义问题拼音",orderNum = "2")
    private String  pinYinProblem;
    @Excel(name = "语义问题答案",orderNum = "3")
    private String corpusAnswer;

    private String  semanticType;

    private String  corpusType;

    private String  genericType;

    private String corpusResUrl;

    private String  corpusResUrlPic;
    @Excel(name = "创建时间",orderNum = "4")
    private String createDate;

    private String updateDate;
}
