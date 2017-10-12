package com.fise.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 
 */
public class CameraConf implements Serializable {
    /**
     * 设备号
     */
    @JsonProperty("device_id")
    private Integer deviceId;

    /**
     * 管理员ID
     */
    @JsonProperty("master_id")
    private Integer masterId;

    /**
     * 家庭群组ID
     */
    @JsonProperty("group_id")
    private Integer groupId;

    /**
     * 管理员号码
     */
    private String mobile;

    /**
     * 供电报警
     */
    @JsonProperty("alarm_power")
    private Integer alarmPower;

    /**
     * 用电报警开光
     */
    @JsonProperty("alarm_battery")
    private Integer alarmBattery;

    /**
     * 关机报警开关
     */
    @JsonProperty("alarm_off")
    private Integer alarmOff;

    /**
     * 移动报警
     */
    @JsonProperty("alarm_move")
    private Integer alarmMove;

    /**
     * 是否在充电，0-没充，1-充电
     */
    
    private Integer electricize;

    /**
     * 工作模式 1-普通模式 2-省电模式 3-休眠模式
     */
    private Integer mode;

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

    public Integer getAlarmPower() {
        return alarmPower;
    }

    public void setAlarmPower(Integer alarmPower) {
        this.alarmPower = alarmPower;
    }

    public Integer getAlarmBattery() {
        return alarmBattery;
    }

    public void setAlarmBattery(Integer alarmBattery) {
        this.alarmBattery = alarmBattery;
    }

    public Integer getAlarmOff() {
        return alarmOff;
    }

    public void setAlarmOff(Integer alarmOff) {
        this.alarmOff = alarmOff;
    }

    public Integer getAlarmMove() {
        return alarmMove;
    }

    public void setAlarmMove(Integer alarmMove) {
        this.alarmMove = alarmMove;
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