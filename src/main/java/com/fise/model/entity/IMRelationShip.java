package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class IMRelationShip implements Serializable {
    private Integer id;

    /**
     * 用户A的id
     */
    private Integer smallid;

    /**
     * 用户B的id
     */
    private Integer bigid;

    /**
     * 用户:0-正常, 1-用户A删除,群组:0-正常, 1-被删除
     */
    private Boolean status;

    /**
     * small权限值
     */
    private Boolean smallpriority;

    /**
     * big权限值
     */
    private Boolean bigpriority;

    /**
     * 创建时间
     */
    private Integer created;

    /**
     * 更新时间
     */
    private Integer updated;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSmallid() {
        return smallid;
    }

    public void setSmallid(Integer smallid) {
        this.smallid = smallid;
    }

    public Integer getBigid() {
        return bigid;
    }

    public void setBigid(Integer bigid) {
        this.bigid = bigid;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getSmallpriority() {
        return smallpriority;
    }

    public void setSmallpriority(Boolean smallpriority) {
        this.smallpriority = smallpriority;
    }

    public Boolean getBigpriority() {
        return bigpriority;
    }

    public void setBigpriority(Boolean bigpriority) {
        this.bigpriority = bigpriority;
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