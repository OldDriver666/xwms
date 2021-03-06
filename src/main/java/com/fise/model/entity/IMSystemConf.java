package com.fise.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/**
 * @author 
 */
public class IMSystemConf implements Serializable {
	@JsonProperty("config_id")
    private Integer configid;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 配置类型名称
     */
    private String name;

    /**
     * 配置具体值
     */
    private String value;

    /**
     * 配置目标action
     */
    private String action;

    /**
     * 0-弃用 1-启用
     */
    private Boolean status;
    
    @JsonProperty("parent_id")
    private Integer parentId;

    private Integer updated;

    private Integer created;

    private static final long serialVersionUID = 1L;


    public Integer getConfigid() {
		return configid;
	}

	public void setConfigid(Integer configid) {
		this.configid = configid;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
    
    
}