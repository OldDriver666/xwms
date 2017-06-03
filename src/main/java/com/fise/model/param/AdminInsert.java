package com.fise.model.param;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

public class AdminInsert {
	
	@NotNull
	@JsonProperty("admin_id")
	private Integer adminId;
	
	@NotEmpty
	@MaxLength(value = 40)
	private String account;
	
	@NotEmpty
	@MaxLength(value = 50)
	private String password;
	
	@JsonProperty("nick_name")
	private String nickName;
	
	@JsonProperty("rule_id")
	private Integer ruleId;
	
	@NotNull
	@JsonProperty("organization_id")
	private Integer organizationId;
	
	private String phone;
	
	private String email;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	public String toString() {
		return JsonUtil.toJson(this);
	}

}
