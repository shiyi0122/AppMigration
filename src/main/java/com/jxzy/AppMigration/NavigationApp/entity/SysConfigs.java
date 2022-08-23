package com.jxzy.AppMigration.NavigationApp.entity;

public class SysConfigs {
    private Long configsId;

    private String title;

    private String configsType;

    private String createTime;

    private String updateTime;

    private String configsValues;

    public Long getConfigsId() {
        return configsId;
    }

    public void setConfigsId(Long configsId) {
        this.configsId = configsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getConfigsType() {
        return configsType;
    }

    public void setConfigsType(String configsType) {
        this.configsType = configsType == null ? null : configsType.trim();
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

    public String getConfigsValues() {
        return configsValues;
    }

    public void setConfigsValues(String configsValues) {
        this.configsValues = configsValues == null ? null : configsValues.trim();
    }
}