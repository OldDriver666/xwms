package com.fise.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/**
 * @author 
 */
public class WiOrganizationRole implements Serializable {
    private Integer id;
    
    @JsonProperty("admin_id")
    private Integer adminid;
    
    private Integer authLevel;

    private String name;

    private String description;

    private Integer organizationId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }
    
    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
    
}