package com.fise.model.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.model.entity.AppChannel;
import com.fise.utils.JsonUtil;

public class AppChannelResult {
	private String name;
	private String textColor;
	private String backgroundColor;
	private String image;
	@JsonProperty("app_list")
	private List<AppBaseResult> appList;
	
	public List<AppBaseResult> getAppList() {
		return appList;
	}

	public void setAppList(List<AppBaseResult> appList) {
		this.appList = appList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void init(AppChannel data) {
		this.name = data.getChannelName();
	    this.textColor = data.getTextcolor();
	    this.backgroundColor =data.getBackground();
	    this.image =data.getImage();
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
	
}
