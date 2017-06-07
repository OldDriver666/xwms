package com.fise.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 
 */
public class IMServiceConf implements Serializable {
	@JsonProperty("config_id")
    private Integer configid;
	
	@JsonProperty("service_name")
    private String serviceName;
	
	@JsonProperty("service_pwd")
    private String servicePwd;
	
	@JsonProperty("auth_code")
    private String authcode;

	@JsonProperty("time_tamp")
    private Integer timetamp;

    private Integer updateTime;
    
    private Integer status;

    private static final long serialVersionUID = 1L;

    

    public Integer getConfigid() {
		return configid;
	}

	public void setConfigid(Integer configid) {
		this.configid = configid;
	}

	public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePwd() {
        return servicePwd;
    }

    public void setServicePwd(String servicePwd) {
        this.servicePwd = servicePwd;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public Integer getTimetamp() {
        return timetamp;
    }

    public void setTimetamp(Integer timetamp) {
        this.timetamp = timetamp;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}