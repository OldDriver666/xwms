package com.fise.model.result;

import java.io.Serializable;

/**
 * @author 
 */
public class IMVedioResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 公司名称
	 */
    private String companyName;

    /**
     * 设备数量
     */
    private Integer devCount;

    /**
     * 图片数量
     */
    private Integer imgCount;

    /**
     * 视频数量
     */
    private Integer vedioCount;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getDevCount() {
		return devCount;
	}

	public void setDevCount(Integer devCount) {
		this.devCount = devCount;
	}

	public Integer getImgCount() {
		return imgCount;
	}

	public void setImgCount(Integer imgCount) {
		this.imgCount = imgCount;
	}

	public Integer getVedioCount() {
		return vedioCount;
	}

	public void setVedioCount(Integer vedioCount) {
		this.vedioCount = vedioCount;
	}


}