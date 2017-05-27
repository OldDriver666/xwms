package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class TBCompany implements Serializable {
    private Integer id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司名称
     */
    private String address;

    /**
     * 公司联系方式
     */
    private String contact;

    private String email;

    /**
     * 公司简介
     */
    private String describe;

    /**
     * 0-删除，1-正常
     */
    private Boolean status;

    /**
     * 创建时间´
     */
    private Integer created;

    /**
     * 更新时间´
     */
    private Integer updated;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }
}