package com.jxzy.AppMigration.NavigationApp.entity;

public class SysScenicSpotImg {
    private Long scneicSpotImgId;

    private Long scenicSpotId;

    private String scneicSpotImgUrl;

    private String createDate;

    private String updateDate;

    public Long getScneicSpotImgId() {
        return scneicSpotImgId;
    }

    public void setScneicSpotImgId(Long scneicSpotImgId) {
        this.scneicSpotImgId = scneicSpotImgId;
    }

    public Long getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(Long scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public String getScneicSpotImgUrl() {
        return scneicSpotImgUrl;
    }

    public void setScneicSpotImgUrl(String scneicSpotImgUrl) {
        this.scneicSpotImgUrl = scneicSpotImgUrl == null ? null : scneicSpotImgUrl.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate == null ? null : updateDate.trim();
    }
}