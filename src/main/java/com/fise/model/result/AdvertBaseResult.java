package com.fise.model.result;


import com.fise.model.entity.AppAdvert;
import com.fise.utils.JsonUtil;

public class AdvertBaseResult {
	private Integer id;

	private String advName;

	private String type;

	private Integer typeId;

	private String image;

	private Integer delayTime;
	
	private String innerName;
	
	public String getInnerName() {
		return innerName;
	}

	public void setInnerName(String innerName) {
		this.innerName = innerName;
	}

	public String getAdvName() {
		return advName;
	}

	public void setAdvName(String advName) {
		this.advName = advName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public void init(AppAdvert appAdvert) {
		this.id =appAdvert.getId();
	    this.advName =appAdvert.getAdvName();
	    this.type= appAdvert.getType();
	    this.typeId=appAdvert.getTypeId();
		this.delayTime = appAdvert.getDelayTime();
		this.image = appAdvert.getAdvUrl();
		this.innerName=appAdvert.getInnerName();
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
