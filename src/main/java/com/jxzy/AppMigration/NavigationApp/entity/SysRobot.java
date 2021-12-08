package com.jxzy.AppMigration.NavigationApp.entity;

public class SysRobot {
    private Long robotId;

    private String robotCodeSim;

    private Long scenicSpotId;

    private String robotCodeCid;

    private String robotCode;

    private String robotRunState;

    private String robotPowerState;

    private String robotFaultState;

    private String robotType;

    private String robotPollingType;

    private String robotGpsSmallApp;

    private String robotGpsBaiDu;

    private String robotGpsGpgga;

    private String robotAdminLocking;

    private String pushStatus;

    private String updatePushDate;

    private String robotOperationStatus;

    private String createDate;

    private String updateDate;

    private String robotRemarks;

    private String clientVersion;

    private String robotBatchNumber;

    private String robotModel;

    public Long getRobotId() {
        return robotId;
    }

    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    public String getRobotCodeSim() {
        return robotCodeSim;
    }

    public void setRobotCodeSim(String robotCodeSim) {
        this.robotCodeSim = robotCodeSim == null ? null : robotCodeSim.trim();
    }

    public Long getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(Long scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public String getRobotCodeCid() {
        return robotCodeCid;
    }

    public void setRobotCodeCid(String robotCodeCid) {
        this.robotCodeCid = robotCodeCid == null ? null : robotCodeCid.trim();
    }

    public String getRobotCode() {
        return robotCode;
    }

    public void setRobotCode(String robotCode) {
        this.robotCode = robotCode == null ? null : robotCode.trim();
    }

    public String getRobotRunState() {
        return robotRunState;
    }

    public void setRobotRunState(String robotRunState) {
        this.robotRunState = robotRunState == null ? null : robotRunState.trim();
    }

    public String getRobotPowerState() {
        return robotPowerState;
    }

    public void setRobotPowerState(String robotPowerState) {
        this.robotPowerState = robotPowerState == null ? null : robotPowerState.trim();
    }

    public String getRobotFaultState() {
        return robotFaultState;
    }

    public void setRobotFaultState(String robotFaultState) {
        this.robotFaultState = robotFaultState == null ? null : robotFaultState.trim();
    }

    public String getRobotType() {
        return robotType;
    }

    public void setRobotType(String robotType) {
        this.robotType = robotType == null ? null : robotType.trim();
    }

    public String getRobotPollingType() {
        return robotPollingType;
    }

    public void setRobotPollingType(String robotPollingType) {
        this.robotPollingType = robotPollingType == null ? null : robotPollingType.trim();
    }

    public String getRobotGpsSmallApp() {
        return robotGpsSmallApp;
    }

    public void setRobotGpsSmallApp(String robotGpsSmallApp) {
        this.robotGpsSmallApp = robotGpsSmallApp == null ? null : robotGpsSmallApp.trim();
    }

    public String getRobotGpsBaiDu() {
        return robotGpsBaiDu;
    }

    public void setRobotGpsBaiDu(String robotGpsBaiDu) {
        this.robotGpsBaiDu = robotGpsBaiDu == null ? null : robotGpsBaiDu.trim();
    }

    public String getRobotGpsGpgga() {
        return robotGpsGpgga;
    }

    public void setRobotGpsGpgga(String robotGpsGpgga) {
        this.robotGpsGpgga = robotGpsGpgga == null ? null : robotGpsGpgga.trim();
    }

    public String getRobotAdminLocking() {
        return robotAdminLocking;
    }

    public void setRobotAdminLocking(String robotAdminLocking) {
        this.robotAdminLocking = robotAdminLocking == null ? null : robotAdminLocking.trim();
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus == null ? null : pushStatus.trim();
    }

    public String getUpdatePushDate() {
        return updatePushDate;
    }

    public void setUpdatePushDate(String updatePushDate) {
        this.updatePushDate = updatePushDate == null ? null : updatePushDate.trim();
    }

    public String getRobotOperationStatus() {
        return robotOperationStatus;
    }

    public void setRobotOperationStatus(String robotOperationStatus) {
        this.robotOperationStatus = robotOperationStatus == null ? null : robotOperationStatus.trim();
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

    public String getRobotRemarks() {
        return robotRemarks;
    }

    public void setRobotRemarks(String robotRemarks) {
        this.robotRemarks = robotRemarks == null ? null : robotRemarks.trim();
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion == null ? null : clientVersion.trim();
    }

    public String getRobotBatchNumber() {
        return robotBatchNumber;
    }

    public void setRobotBatchNumber(String robotBatchNumber) {
        this.robotBatchNumber = robotBatchNumber == null ? null : robotBatchNumber.trim();
    }

    public String getRobotModel() {
        return robotModel;
    }

    public void setRobotModel(String robotModel) {
        this.robotModel = robotModel == null ? null : robotModel.trim();
    }
}