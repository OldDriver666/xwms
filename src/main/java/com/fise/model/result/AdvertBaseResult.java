package com.fise.model.result;


import com.fise.model.entity.AppAdvert;
import com.fise.utils.JsonUtil;

public class AdvertBaseResult {
	private Integer id;

	private String name;

	private String type;

	private Integer typeId;

	private String url;

	private Integer delayTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public void init(AppAdvert appAdvert) {
		this.id =appAdvert.getId();
	    this.name =appAdvert.getAdvName();
	    this.type= appAdvert.getType();
	    this.typeId=appAdvert.getTypeId();
		this.delayTime = appAdvert.getDelayTime();
		this.url = appAdvert.getAdvUrl();
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
