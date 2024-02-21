package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/9/3 16:12
 */

@Data
public class SysScenicSpotAudio {

    private Long scneicSpotAudioId;

    private Long scenicSpotId;

    private String scneicSpotAudioUrl;

    private String createDate;

    private String updateDate;

}
