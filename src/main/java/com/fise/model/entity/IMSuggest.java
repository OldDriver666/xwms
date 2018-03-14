package com.fise.model.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class IMSuggest implements Serializable {
    /**
     * 问题id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 回复数量
     */
    private Integer count;

    /**
     * 用户名
     */
    private String uname;

    /**
     * 记录状态 0 :初始正常 1:已经回复
     */
    private Byte status;

    /**
     * 0-公开 1-私有
     */
    private Boolean type;

    /**
     * 标题
     */
    private String title;

    /**
     * 建议id
     */
    private String suggestId;

    /**
     * 内容
     */
    private String content;

    /**
     * 联系方式
     */
    private String contact;

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

    public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(String suggestId) {
        this.suggestId = suggestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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