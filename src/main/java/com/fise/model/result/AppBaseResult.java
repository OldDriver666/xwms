package com.fise.model.result;


import com.fise.model.entity.AppInformation;
import com.fise.utils.JsonUtil;

public class AppBaseResult {
	private Integer appId;
	private String appName;
	private String downLoad;
	private String description;
	private String version;
	private String versionCode;
	private String category;
	private String star;
	private String icon;
	private Integer iconType;
	private String size;
	
	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDownLoad() {
		return downLoad;
	}

	public void setDownLoad(String downLoad) {
		this.downLoad = downLoad;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIconType() {
		return iconType;
	}

	public void setIconType(Integer iconType) {
		this.iconType = iconType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void init(AppInformation data) {
		this.appId = data.getId();
		this.appName = data.getAppName();
		this.downLoad = data.getDownload();
		this.description = data.getDescription();
		this.version = data.getVersion();
		this.versionCode = data.getVersioncode();
		this.category = data.getCategory();
		this.star = data.getStart();
		this.icon = data.getIcon();
		this.iconType = data.getIconType();
		this.size = data.getSize();
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
