package com.fise.controller.appstore;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.entity.AppChannel;
import com.fise.model.entity.AppInformation;
import com.fise.model.entity.AppSplash;
import com.fise.model.result.AppChannelResult;
import com.fise.server.appstore.IAppChannelService;
import com.fise.server.appstore.IAppSplashService;
import com.fise.server.appstore.IAppStoreService;

@RestController
@RequestMapping("/boss/store")
public class AppStoreController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	IAppStoreService appSvr;

	@Resource
	IAppSplashService splashSvr;

	@Resource
	IAppChannelService channelSvr;

	/**
	 * 在我的应用的首页，根据权重的不同展示所有的app
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/appAll", method = RequestMethod.POST)
	public Response getAppAll(@RequestBody @Valid Page<AppInformation> param) {
		Response response = new Response();
		logger.info(param.toString());
		response = appSvr.queryAppAll(param);
		return response;
	}

	/**
	 * 在我的应用的首页，根据权重的不同展示所有的广告栏
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/advertAll", method = RequestMethod.POST)
	public Response getAdvertAll(@RequestBody @Valid Map<String, String> param) {
		Response response = new Response();
		response = appSvr.queryAdvertAll();
		return response;
	}

	/**
	 * 在我的应用的首页，根据权重的不同展示所有的频道
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/channelAll", method = RequestMethod.POST)
	public Response getChannelAll(@RequestBody @Valid Map<String, String> param) {
		Response response = new Response();
		logger.info(param.toString());
		response = appSvr.queryChannelAll();
		return response;
	}

	/**
	 * 获取指定频道下的app的list
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/channel", method = RequestMethod.POST)
	public Response getChannelInfo(@RequestBody @Valid Page<AppChannel> param) {
		Response response = new Response();
		Integer channelId = param.getParam().getId();
		AppChannelResult data = new AppChannelResult();
		AppChannel channel = new AppChannel();
		channel = channelSvr.getChannelInfo(channelId);
		if (null==channel) {
			response.failure(ErrorCode.ERROR_PARAM_MEMBER_MOBILE_IS_EMPTY);
			response.setMsg("亲，频道信息不存在哦~");
			return response;
		}
		data.init(channel);
		List<Integer> appIdList = channelSvr.getChannelAppId(channelId);
		if(appIdList.size()==0){
			response.failure(ErrorCode.ERROR_SEARCH_APP_UNEXIST);
			response.setMsg("亲，该频道下没有应用哦~");
			return response;
		}
		
		response = appSvr.queryByIdList(param, appIdList);
		return response;
	}

	/**
	 * 根据传入的app_name参数判断是模糊查询两条app，还是查询所有的相关的app
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/simpleSearch", method = RequestMethod.POST)
	public Response getsimpleSearch(@RequestBody @Valid Map<String, Object> param) {
		Response response = new Response();
		logger.info(param.toString());
		String appName = (String) param.get("app_name");
		response = appSvr.queryByAppName(appName);
		return response;
	}

	@IgnoreAuth
	@RequestMapping(value = "/allSearch", method = RequestMethod.POST)
	public Response getAllSearch(@RequestBody @Valid Page<AppInformation> param) {
		Response response = new Response();
		logger.info(param.toString());
		response = appSvr.queryByAppName(param);
		return response;
	}

	/**
	 * 热门搜索
	 */
	@IgnoreAuth
	@RequestMapping(value = "/hotSearch", method = RequestMethod.POST)
	public Response getHotSearch(@RequestBody @Valid Map<String, Object> param) {
		Response response = new Response();
		logger.info(param.toString());
		response = appSvr.hotSearch();
		return response;
	}

	/**
	 * 查询出app的具体信息
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/appinfo", method = RequestMethod.POST)
	public Response getAppInfo(@RequestBody @Valid Map<String, Object> param) {
		Response response = new Response();
		Integer appIdex = (Integer) param.get("app_id");
		if(appIdex.equals("")||appIdex==null){
			return response.failure(ErrorCode.ERROR_PARAM_BIND_EXCEPTION);
		}
		response = appSvr.queryByAppId(appIdex);
		return response;
	}

	@IgnoreAuth
	@RequestMapping(value = "/home/aplash", method = RequestMethod.POST)
	public Response getHomeSplash(@RequestBody @Valid Map<String, String> param) {
		Response resp = new Response();
		List<AppSplash> data = splashSvr.querySpalsh();
		resp.success(data);
		return resp;
	}

	/**
	 * 开发者模式下的新增APP
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/appInsert", method = RequestMethod.POST)
	public Response appInsert(@RequestBody  Map<String, Object> param, 
			                  @RequestParam("images") MultipartFile[] uploadPhoto,
			                  @RequestParam("app") MultipartFile uploadApp) {
		
		Response response = new Response();
		
		response = appSvr.appInsert(param,uploadPhoto,uploadApp);
		return response;
	}

	/**
	 * 开发者模式下的修改APP
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/appModify", method = RequestMethod.POST)
	public Response appModify(@RequestBody @Valid Map<String, Object> param) {
		Response response = new Response();
		response = appSvr.appModify(param);
		return response;
	}

	/**
	 * 开发者模式下的删除APP
	 * 
	 * @param param
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping(value = "/appDelete", method = RequestMethod.POST)
	public Response appDelete(@RequestBody @Valid Map<String, Object> param) {
		Response response = new Response();
		Integer appId = (Integer) param.get("app_id");
		response = appSvr.appDelete(appId);
		return response;
	}

	/**
	 * 
	 */

	// @IgnoreAuth
	// @RequestMapping(value = "/home", method = RequestMethod.POST)
	// public Response homePage(@RequestBody @Valid Map<String, String> param) {
	//
	// Response resp = new Response();
	// Map<String, Object> data = new HashMap<String,Object>();
	// List<AppSplash> splashData = splashSvr.querySpalsh();
	// List<StoreSplashResult> splashRet = new ArrayList<StoreSplashResult>();
	// for(AppSplash tmp : splashData){
	// StoreSplashResult splash = new StoreSplashResult();
	// splash.init(tmp);
	// splashRet.add(splash);
	// }
	// data.put("splash", splashRet);
	//
	// List<AppChannel> channelData = channelSvr.query();
	// List<AppChannelResult> baseData = new ArrayList<AppChannelResult>();
	// List<AppBaseResult> appData = new ArrayList<AppBaseResult>();
	// for(AppChannel tmp2 : channelData){
	// AppChannelResult channel = new AppChannelResult();
	// appData.clear();
	// channel.init(tmp2);
	// if(tmp2.getChannelType() != 0){
	// List<Integer> appIdList = channelSvr.getChannelAppId(tmp2.getId());
	// List<AppInformation> appList = appSvr.queryByIdList(appIdList);
	// for(int i=0;i<appList.size();i++){
	// AppBaseResult appBase = new AppBaseResult();
	// appBase.init(appList.get(i));
	// appData.add(appBase);
	// }
	// channel.setAppList(appData);
	// baseData.add(channel);
	// } else {
	// baseData.add(channel);
	// }
	// }
	// data.put("channel", baseData);
	// resp.success(data);
	// return resp;
	// }
}
