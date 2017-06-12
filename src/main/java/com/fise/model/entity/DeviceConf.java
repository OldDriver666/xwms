package com.fise.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 
 */
public class DeviceConf implements Serializable {
    /**
     * 设备号
     */
    @JsonProperty("device_id")
    private Integer deviceId;

    /**
     * 家庭群组ID
     */
    @JsonProperty("group_id")
    private Integer groupId;

    /**
     * 管理员ID
     */
    @JsonProperty("master_id")
    private Integer masterId;

    /**
     * 设备本机号码
     */
    private String mobile;

    /**
     * 电量报警开关
     */
    @JsonProperty("alarm_battery")
    private Boolean alarmBattery;

    /**
     * 关机报警开关
     */
    @JsonProperty("alarm_power_off")
    private Boolean alarmPoweroff;

    /**
     * 电子围栏报警开关
     */
    @JsonProperty("alarm_pen")
    private Boolean alarmPen;
    
    @JsonProperty("alarm_call")
    private Boolean alarmCall;

    /**
     * 静默监听开关
     */
    @JsonProperty("listen_silent")
    private Boolean listenSilent;

    /**
     * 是否在充电，0-没充，1-充电
     */
    private Boolean electricize;

    /**
     * 工作模式
     */
    private Boolean mode;

    /**
     * 响铃模式 1-响铃 2震动 3响铃和震动
     */
    @JsonProperty("bell_mode")
    private Boolean bellMode;

    private Integer updated;

    private Integer created;

    private static final long serialVersionUID = 1L;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Boolean getAlarmPen() {
        return alarmPen;
    }

    public void setAlarmPen(Boolean alarmPen) {
        this.alarmPen = alarmPen;
    }

    public Boolean getAlarmCall() {
        return alarmCall;
    }

    public void setAlarmCall(Boolean alarmCall) {
        this.alarmCall = alarmCall;
    }

    public Boolean getListenSilent() {
        return listenSilent;
    }

    public void setListenSilent(Boolean listenSilent) {
        this.listenSilent = listenSilent;
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