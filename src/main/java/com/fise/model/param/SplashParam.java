package com.fise.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class SplashParam {
	@JsonProperty("splash_id")
    private Integer splashid;

    /**
     * 闪屏页名称
     */
    private String name;

	public Integer getSplashid() {
		return splashid;
	}

	public void setSplashid(Integer splashid) {
		this.splashid = splashid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
    
    
}
