package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysScenicSpotShopsType {
    private Long typeId;

    private Long shopsId;

    private String typeName;

    private String createTime;

    private String updateTime;

    private Long commodityId;

    private List<SysScenicSpotShopsDetails> sysScenicSpotShopsDetails;
}