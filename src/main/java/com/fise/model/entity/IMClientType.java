package com.fise.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 
 */
public class IMClientType implements Serializable {
	@JsonProperty("type_id")
    private Integer typeid;

    /**
     * 设备类型
     */
	@JsonProperty("client_type")
    private Integer clienttype;

    /**
     * 设备类型名称
     */
	@JsonProperty("client_name")
    private String clientname;

    private Integer created;

    private static final long serialVersionUID = 1L;

    public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public Integer getClienttype() {
        return clienttype;
    }

    public void setClienttype(Integer clienttype) {
        this.clienttype = clienttype;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }
}