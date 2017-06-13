package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class WatchConf implements Serializable {
    /**
     * 设备号
     */
    private Integer deviceId;

    /**
     * 管理员ID
     */
    private Integer masterId;

    /**
     * 管理群组ID
     */
    private Integer groupId;

    /**
     * 管理员号码
     */
    private String mobile;

    private Boolean alarmTakeoff;

    /**
     * 电量报警开光
     */
    private Boolean alarmBattery;

    /**
     * 关机报警开关
     */
    private Boolean alarmPoweroff;

    private Boolean stepMode;

    /**
     * 通话报警开关
     */
    private Boolean alarmCall;

    /**
     * 是否在充电，0-没充，1-充电
     */
    private Boolean electricize;

    /**
     * 工作模式 1-普通模式 2-省电模式 3-休眠模式
     */
    private Boolean mode;

    /**
     * 响铃模式 1-响铃 2震动 3响铃和震动
     */
    private Boolean bellMode;

    /**
     * 亮屏时间
     */
    private Integer lightTime;

    private Integer updated;

    private Integer created;

    private static final long serialVersionUID = 1L;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getAlarmTakeoff() {
        return alarmTakeoff;
    }

    public void setAlarmTakeoff(Boolean alarmTakeoff) {
        this.alarmTakeoff = alarmTakeoff;
    }

    public Boolean getAlarmBattery() {
        return alarmBattery;
    }

    public void setAlarmBattery(Boolean alarmBattery) {
        this.alarmBattery = alarmBattery;
    }

    public Boolean getAlarmPoweroff() {
        return alarmPoweroff;
    }

    public void setAlarmPoweroff(Boolean alarmPoweroff) {
        this.alarmPoweroff = alarmPoweroff;
    }

    public Boolean getStepMode() {
        return stepMode;
    }

    public void setStepMode(Boolean stepMode) {
        this.stepMode = stepMode;
    }

    public Boolean getAlarmCall() {
        return alarmCall;
    }

    public void setAlarmCall(Boolean alarmCall) {
        this.alarmCall = alarmCall;
    }

    public Boolean getElectricize() {
        return electricize;
    }

    public void setElectricize(Boolean electricize) {
        this.electricize = electricize;
    }

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean mode) {
        this.mode = mode;
    }

    public Boolean getBellMode() {
        return bellMode;
    }

    public void setBellMode(Boolean bellMode) {
        this.bellMode = bellMode;
    }

    public Integer getLightTime() {
        return lightTime;
    }

    public void setLightTime(Integer lightTime) {
        this.lightTime = lightTime;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }
}