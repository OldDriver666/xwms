package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class DeviceConfMotorMeter implements Serializable {
    /**
     * 设备号
     */
    private Integer deviceId;

    /**
     * 管理员ID
     */
    private Integer masterId;

    /**
     * 设备本机号码
     */
    private String mobile;

    /**
     * 电量报警开光
     */
    private Boolean alarmBattery;

    /**
     * 关机报警开关
     */
    private Boolean alarmPoweroff;

    /**
     * 电子围栏报警开关
     */
    private Boolean alarmPen;

    /**
     * 通话报警开关
     */
    private Boolean alarmCall;

    /**
     * 静默监听开关
     */
    private Boolean listenSilent;

    /**
     * 工作模式 1-普通模式 2-省电模式 3-休眠模式
     */
    private Boolean mode;

    /**
     * 响铃模式 1-响铃 2震动 3响铃和震动
     */
    private Boolean bellMode;

    /**
     * 速度
     */
    private Integer speed;

    /**
     * 限速
     */
    private Integer speedLimit;

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

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
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