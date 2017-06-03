package com.fise.base;

/**
 * API 错误码
 * 
 * @author 
 * @version 2016-01-03
 */
public enum ErrorCode {
	ERROR_OK("ok", 0),
	
	
	ERROR_PARAM_MEMBER_MOBILE_IS_EMPTY("参数手机号不能为空！", 10001),
	ERROR_PARAM_MEMBER_PASSWD_IS_EMPTY("参数密码不能为空！", 10002),
	ERROR_REGISTER_MEMBER_MOBILE_EXISTED("手机号已注册！", 10003),
	ERROR_MEMBER_INDB_IS_NULL("该用户不存在！", 10004),
	ERROR_PASSWORD_INCORRECT("密码错误！", 10005),
	ERROR_PRRAM_NOT_COMPLETE_EXCEPTION("缺少请求参数！",10006),
	ERROR_PARAM_PARSE_JSON_EXCEPTION("JSON解析异常！",10007),
	ERROR_PARAM_NOT_VALID_EXCEPTION("参数验证异常！", 10008),
	ERROR_PARAM_BIND_EXCEPTION("参数绑定异常！", 10009),
	ERROR_PARAM_VIOLATION_EXCEPTION("参数违反约束！", 10010),
	ERROR_PARAM_VALIDATION_EXCEPTION("参数验证失败！", 10011),
	ERROR_REQUEST_METHOD_NOT_SUPPORTED("不支持该请求方法！", 10012),
	ERROR_MEDIATYPE_NOT_SUPPORTED("不支持的媒体类型！", 10013),
	ERROR_DB_RECORD_ALREADY_EXIST("记录已存在！", 10014),
	ERROR_GYM_INDB_IS_NULL("该商户不存在！", 10015),
	ERROR_MEMBER_ACCOUNT_INDB_IS_NULL("该用户的账户信息不存在！", 10016),
	ERROR_MEMBER_ACCOUNT_AVAILABLEBALANCE_LACK("用户账户余额不足！", 10017),
	ERROR_GYM_ITEM_NOT_EXIST("该健身项目不存在！", 10018),
	ERROR_SMS_CODE_SEND_FAILED("短信验证码发送失败！", 10019),
	ERROR_REQUEST_AUTH_FAILED("鉴权失败！", 10020),
	ERROR_MEMBER_REGISTER_VERIFY_CODE_ERROR("验证码输入错误！", 10021),
	ERROR_ORDER_NOT_EXIST("没有符合条件的订单！", 10022),
	ERROR_WECHAT_LOGIN_QUEST_TOKEN_ERROR("微信授权登录获取token失败！", 10023),
	ERROR_WECHAT_LOGIN_QUEST_USERINFO_ERROR("微信授权登录获取userinfo失败！", 10024),
	ERROR_WECHAT_LOGIN_NOT_BIND("微信登录未绑定！", 10025),
	ERROR_LOGIN_BIND_THIRDPARTY_NOT_AUTHED("请先获得第三方登录授权！", 10026),
	ERROR_LOGIN_BIND_THIRDPARTY_NEED_REAUTHE("请重新获得第三方登录授权！", 10027),
	ERROR_GYM_UPDATE_DUPLICATE_GYM_NAME("存在同名的健身项目名！", 10028),
	ERROR_MOBILE_CHANGE_STEP_ERROR("修改手机号码：请先完成前置步骤验证！", 10029),
	ERROR_MOBILE_ERROR("手机号错误！", 10030),
	ERROR_PARAMETER_BUSINESS_CHECK_ERROR("业务侧参数验证错误！", 10031),
	ERROR_ACCOUNT_PASSWORD_IS_EMPTY("账户密码为空！", 10032),
	ERROR_REQUEST_HEADER_ERROR("请求头错误！", 10033),
	ERROR_GYM_ACCOUNT_SETTLEAMOUNT_GT_WAITBALANCE("商户账户结算金额超出待结算余额！", 10034),
	ERROR_GYM_NOT_COMPLETE_ORDER_EXIST("在本店有未完成的订单！", 10035),
	ERROR_GYM_ACCOUNT_INDB_IS_NULL("该商户的账户信息不存在！", 10036),
	ERROR_MANAGER_INDB_IS_NULL("该管理员不存在！", 10037),
	ERROR_FISE_DEVICE_IME_EXISTED("设备IME已经存在",10038),
	ERROR_FISE_DEVICE_ACCOUNT_EXISTED("设备账号已经存在",10039),
	ERROR_FISE_DEVICE_PARAM_NULL("参数不能为空",10040),
	ERROR_DB_RECORD_ALREADY_UNEXIST("记录不存在",10041),
	
	ERROR_CACHE("缓存错误！", 997),
	ERROR_DATABASE("数据库错误！", 998),
	ERROR_SYSTEM("系统错误！", 999);
		
	private String errorMsg;
	private int errorCode;

	private ErrorCode(String msg, int code) {
		this.errorMsg = msg;
		this.errorCode = code;
	}

	public String getMsg() {
		return errorMsg;
	}

	public int getCode() {
		return errorCode;
	}
}
