package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryVedioRecordParam implements Serializable{
    
    private static final long serialVersionUID=1L;


//    /**
//     * 设备编号
//     */
//    @JsonProperty("dev_id")
//	private Integer devId;
    
    /**
     * 小位号-账号
     */
    private String account;
    
//    /**
//     * 部门ID
//     */
//    @JsonProperty("depart_id")
//    private Integer departId;
    
    /**
     * 公司ID
     */
    @JsonProperty("company_id")
    private Integer companyId;
    
    /**
     * 日期
     */
    @JsonProperty("query_date")
    private String queryDate;


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public String getQueryDate() {
		return queryDate;
	}


	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	
}
