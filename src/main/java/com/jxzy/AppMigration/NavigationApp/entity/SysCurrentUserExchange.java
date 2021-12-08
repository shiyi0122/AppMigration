package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysCurrentUserExchange {
    private Long exchangeId;

    private Long userId;

    private String exchangeNumber;

    private Long scenicSpotId;

    private String exchangeState;

    private String exchangeName;

    private String startValidity;

    private String endValidity;

    private String createDate;

    private String updateDate;

}