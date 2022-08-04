package com.jxzy.AppMigration.NavigationApp.entity;

public class SysUserScenicFabulousCollection {
    private Long id;

    private Long userId;

    private Long scenicDistrictId;

    private String fabulous;

    private String collection;

    private String createTime;

    private String updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScenicDistrictId() {
        return scenicDistrictId;
    }

    public void setScenicDistrictId(Long scenicDistrictId) {
        this.scenicDistrictId = scenicDistrictId;
    }

    public String getFabulous() {
        return fabulous;
    }

    public void setFabulous(String fabulous) {
        this.fabulous = fabulous == null ? null : fabulous.trim();
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection == null ? null : collection.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }
}