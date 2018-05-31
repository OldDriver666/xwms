package com.fise.model.param;

import com.fise.utils.JsonUtil;

public class SuggestParam {
	/**
	 * 回答id
	 */
    private Integer id;
    /**
     * 建议id
     */
    private String suggestId;
    
    /**
     * 建议标题
     */
    private String title;
	
    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String uname;
    
    /**
     * 0-公有 1-私有
     */
    private Integer type;
    
    /**
     * 0-开启 1-处理中 2-关闭
     */
    private Integer status;


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSuggestId() {
		return suggestId;
	}

	public void setSuggestId(String suggestId) {
		this.suggestId = suggestId;
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

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
    
    
}
