package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/16 16:57
 * 地标
 */
@Data
public class SysSpotLandmark {

    private Long id;

    private Long  spotId ;

    private String landmarkName;

    private String landmarkNamePinyin;

    private String landmarkNameGps;

    private String landmarkNameGpsBaiDu;

    private String landmarkNameGpsGaoDe;

    private String coordinateRadius;

    private String landmarkContent;

    private String landmarkPicUrl;

    private String broadcastContent;

    private String createTime;

    private String updateTime;

    private String scenicSpotName;

    private String landmarkIntroduction;

    private Double distance;

    private String carrying;

    private String basicIntroduction;

    private String landmarkHistory;

}
