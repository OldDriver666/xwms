package com.fise.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceVersionParam {
	@JsonProperty("version_id")
    private Integer versionid;

    /**
     * 公司ID
     */
	@JsonProperty("depart_id")
    private Integer departid;

    /**
     * 设备类型
     */
	@JsonProperty("dev_type")
    private Integer devType;

	public Integer getVersionid() {
		return versionid;
	}

	public void setVersionid(Integer versionid) {
		this.versionid = versionid;
	}

	public Integer getDepartid() {
		return departid;
	}

	public void setDepartid(Integer departid) {
		this.departid = departid;
	}

	public Integer getDevType() {
		return devType;
	}

	public void setDevType(Integer devType) {
		this.devType = devType;
	}
	
	
}
