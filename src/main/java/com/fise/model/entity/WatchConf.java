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

    private Integer alarmTakeoff;

    /**
     * 电量报警开光
     */
    private Integer alarmBattery;

    /**
     * 关机报警开关
     */
    private Integer alarmPoweroff;

    private Integer stepMode;

    /**
     * 通话报警开关
     */
    private Integer alarmCall;

    /**
     * 是否在充电，0-没充，1-充电
     */
    private Integer electricize;

    /**
     * 工作模式 1-普通模式 2-省电模式 3-休眠模式
     */
    private Integer mode;

    /**
     * 响铃模式 1-响铃 2震动 3响铃和震动
     */
    private Integer bellMode;

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

    public Integer getAlarmTakeoff() {
        return alarmTakeoff;
    }

    public void setAlarmTakeoff(Integer alarmTakeoff) {
        this.alarmTakeoff = alarmTakeoff;
    }

    public Integer getAlarmBattery() {
        return alarmBattery;
    }

    public void setAlarmBattery(Integer alarmBattery) {
        this.alarmBattery = alarmBattery;
    }

    public Integer getAlarmPoweroff() {
        return alarmPoweroff;
    }

    public void setAlarmPoweroff(Integer alarmPoweroff) {
        this.alarmPoweroff = alarmPoweroff;
    }

    public Integer getStepMode() {
        return stepMode;
    }

    public void setStepMode(Integer stepMode) {
        this.stepMode = stepMode;
    }

    public Integer getAlarmCall() {
        return alarmCall;
    }

    public void setAlarmCall(Integer alarmCall) {
        this.alarmCall = alarmCall;
    }

    public Integer getElectricize() {
        return electricize;
    }

    public void setElectricize(Integer electricize) {
        this.electricize = electricize;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getBellMode() {
        return bellMode;
    }

    public void setBellMode(Integer bellMode) {
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