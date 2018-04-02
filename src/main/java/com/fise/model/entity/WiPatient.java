package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class WiPatient implements Serializable {
    /**
     * 用户小位号
     */
    private String domain;

    /**
     * 身体状况
     */
    private String bodyStatus;

    /**
     * 片区民警信息
     */
    private String policeInfo;

    /**
     * 直系亲属
     */
    private String cognate;

    /**
     * 监护人
     */
    private String guardian;

    /**
     * 有奖监护
     */
    private String reward;

    private static final long serialVersionUID = 1L;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBodyStatus() {
        return bodyStatus;
    }

    public void setBodyStatus(String bodyStatus) {
        this.bodyStatus = bodyStatus;
    }

    public String getPoliceInfo() {
        return policeInfo;
    }

    public void setPoliceInfo(String policeInfo) {
        this.policeInfo = policeInfo;
    }

    public String getCognate() {
        return cognate;
    }

    public void setCognate(String cognate) {
        this.cognate = cognate;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}