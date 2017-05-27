package com.fise.model.result;

import java.util.List;

import com.fise.model.dto.Register;

public class RegisterCount {
	private String UserName;
	private String AuthenticCode;
	private int Status=0;
	private String ErrorInfo="error cause";
	private List<Register> DeviceInfo;
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getAuthenticCode() {
		return AuthenticCode;
	}
	public void setAuthenticCode(String authenticCode) {
		AuthenticCode = authenticCode;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getErrorInfo() {
		return ErrorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}
	public List<Register> getDeviceInfo() {
		return DeviceInfo;
	}
	public void setDeviceInfo(List<Register> deviceInfo) {
		DeviceInfo = deviceInfo;
	}
	public RegisterCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegisterCount(String userName, String authenticCode) {
		super();
		UserName = userName;
		AuthenticCode = authenticCode;
	}
	
	
	
}
