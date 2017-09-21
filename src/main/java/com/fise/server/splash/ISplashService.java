package com.fise.server.splash;

import com.fise.base.Response;
import com.fise.model.entity.IMSplash;
import com.fise.model.param.SplashParam;

public interface ISplashService {
	/*添加闪屏信息*/
	public Response insertSplash(IMSplash record);
	
	/*查询闪屏信息*/
	public Response querySplash();
	
	/*删除闪屏信息*/
	public Response delSplash(SplashParam param);
	
	/*修改闪屏信息*/
	public Response updateSplash(IMSplash record);
}
