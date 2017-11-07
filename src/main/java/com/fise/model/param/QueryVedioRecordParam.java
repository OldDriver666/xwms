package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryVedioRecordParam implements Serializable{
    
    private static final long serialVersionUID=1L;


    /**
     * 设备编号
     */
    @JsonProperty("dev_id")
	private Integer devId;


	public Integer getDevId() {
		return devId;
	}


	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	
}
