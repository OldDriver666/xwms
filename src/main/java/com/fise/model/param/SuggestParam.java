package com.fise.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class SuggestParam {
	@JsonProperty("suggest_id")
    private String suggestId;
	
	@JsonProperty("user_id")
    private Integer userId;

    /**
     * 用户名
     */
    private String uname;


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

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
    
    
}
