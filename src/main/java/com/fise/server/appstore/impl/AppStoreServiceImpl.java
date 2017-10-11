package com.fise.server.appstore.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.AppAdvertMapper;
import com.fise.dao.AppChannelMapper;
import com.fise.dao.AppInformationMapper;
import com.fise.dao.AppStoreMapper;
import com.fise.model.entity.AppAdvert;
import com.fise.model.entity.AppAdvertExample;
import com.fise.model.entity.AppChannel;
import com.fise.model.entity.AppChannelExample;
import com.fise.model.entity.AppInformation;
import com.fise.model.entity.AppInformationExample;
import com.fise.model.entity.AppStore;
import com.fise.model.result.AdvertBaseResult;
import com.fise.model.result.AppBaseResult;
import com.fise.model.result.AppChannelResult;
import com.fise.model.result.AppDetailResult;
import com.fise.server.appstore.IAppStoreService;
import com.fise.utils.StringUtil;

@Service
public class AppStoreServiceImpl implements IAppStoreService {

	@Autowired
	AppStoreMapper appDao;

	@Autowired
	AppInformationMapper appInfoDao;

	@Autowired
	AppAdvertMapper appAdvertDao;

	@Autowired
	AppChannelMapper appChannelDao;

	@Override
	public Response appInsert(Map<String ,Object> param,MultipartFile[] uploadPhoto,MultipartFile uploadApp) {
		Response response = new Response();
		
		 if (uploadApp.isEmpty()) {
	 			response.setCode(400);
	 			response.setMsg("请选择上传的App");
	 			return response;
	 		}
		
		if(uploadPhoto==null||uploadPhoto.length==0){
			response.setCode(400);
 			response.setMsg("请选择上传的图片");
 			return response;	
		}
		//param.setAppIndex(StringUtil.md5(param.getAppIndex()));
		//param.setAppId(StringUtil.md5(param.getAppName()));
		
		String appName=(String)param.get("app_name");
		String appSpell=(String) param.get("app_spell");
		Integer creatorId=(Integer) param.get("creator_id");
		String topCategory=(String) param.get("top_category");
		String category=(String) param.get("category");
		String icon=(String) param.get("icon");
		Integer iconType=(Integer) param.get("icon_type");
		String description=(String) param.get("description");
		String version=(String) param.get("version");
		String vserionCode=(String) param.get("versioncode");
		//获取APP的size
		String appSize=getAppSize(uploadApp.getSize());
		
		//要获取app的id，并根据app_id,多次将图片分别插入到
		List<String> images=uploadPhoto(uploadPhoto);
		
		StringBuilder imagesUrl=new StringBuilder();
		for(int i=0;i<images.size();i++){
			imagesUrl.append(images.get(i)+";");
		}
		
		//获取App的下载路径
		String appUrl=uploadApp(uploadApp);
		
		String label=(String) param.get("label");
		Integer orientation=(Integer) param.get("orientation");
		
		AppInformation appInfo=new AppInformation();
		appInfo.setAppName(appName);
		appInfo.setAppSpell(appSpell);
		appInfo.setCreatorId(creatorId);
		appInfo.setIcon(icon);
		appInfo.setIconType(iconType);
		appInfo.setTopCategory(topCategory);
		appInfo.setCategory(category);
		appInfo.setVersion(version);
		appInfo.setVersioncode(vserionCode);
		appInfo.setDescription(description);
		appInfo.setLabel(label);
		appInfo.setOrientation(orientation);
		appInfo.setSize(appSize);
		appInfo.setImages(imagesUrl.toString());
		appInfo.setDownload(appUrl);
		
		int result=appInfoDao.insert(appInfo);
		if(result==0){
			response.setErrorCode(ErrorCode.ERROR_PARAM_BIND_EXCEPTION);
			response.setMsg("新增App失败");
			return response;
		}
		response.setData(param);
		return response;
	}
	
     private String uploadApp(MultipartFile uploadApp){
 		
 		//String appPath = "/home/fise/www/boss/appmarket/app/";
 		String appPath="E:/QQGame/";
 		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
 		String currentTime = df.format(new Date());
 		
 		String fileName = uploadApp.getOriginalFilename();
 		// 获取文件名的后缀
 		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
 		// 获取文件名的前半部分
 		String prefix = fileName.substring(0, fileName.lastIndexOf("."));
 		// 新的文件名加上当前时间的年月日时分秒。
 		String newPath = appPath + prefix +"_" +currentTime + "." + suffix;
 		
 		if (suffix.equals("apk") || suffix.equals("ipa")) {
 			try {
 				uploadApp.transferTo(new File(newPath));
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
 		}
 		String urlPath="http://192.168.2.250:8888/boss/appmarket/app/";
		return urlPath+newPath;
      }	

     private List<String> uploadPhoto(MultipartFile[] uploadPhotos){
    	String appPath="E:/QQGame/";
  		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
  		String currentTime = df.format(new Date());
  		List<String> result =new ArrayList<String>(); 
  		for(int i=0;i<uploadPhotos.length;i++){
  			MultipartFile photo=uploadPhotos[i];
  			
  			String fileName = photo.getOriginalFilename();
  	 		// 获取文件名的后缀
  	 		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
  	 		// 获取文件名的前半部分
  	 		String prefix = fileName.substring(0, fileName.lastIndexOf("."));
  	 		// 新的文件名加上当前时间的年月日时分秒。
  	 		String newPath = appPath + prefix +"_" +currentTime + "." + suffix;
  	 		String urlPath="http://192.168.2.250:8888/boss/appmarket/photo/";
  	 		result.add(urlPath+newPath);
  	 		
  		}
  		
    	return result;
     }
     
	// 分页查询App
	@Override
	public Response queryAppAll(Page<AppInformation> param) {
		Response response = new Response();
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria con = example.createCriteria();

		con.andStatusEqualTo(1);
		example.setOrderByClause("prority desc");
		param.setPageSize(10);
		List<AppInformation> data = appInfoDao.selectByPage(example, param);

		if (data.size() == 0) {
			response.setErrorCode(ErrorCode.ERROR_SEARCH_APP_UNEXIST);
			response.setMsg("亲，没有更多应用咯~");
			return response;
		}
		List<AppBaseResult> appData = new ArrayList<AppBaseResult>();
		for (int i = 0; i < data.size(); i++) {
			AppBaseResult appBase = new AppBaseResult();
			appBase.init(data.get(i));
			appData.add(appBase);
		}

		Page<AppBaseResult> page = new Page<AppBaseResult>();

		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalCount(param.getTotalCount());
		page.setTotalPageCount(param.getTotalPageCount());
		int haveMore = (int) (param.getTotalPageCount() - param.getPageNo());
		if (haveMore > 0) {
			page.setHasMore(true);
		} else {
			page.setHasMore(false);
		}

		page.setResult(appData);

		response.success(page);

		return response;
	}

	@Override
	public Response queryByAppName(Page<AppInformation> param) {
		Response response = new Response();
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria criteria = example.createCriteria();
		criteria.andAppNameLike("%" + param.getParam().getAppName() + "%");
		criteria.andStatusEqualTo(1);
		example.setOrderByClause("prority desc");
		param.setPageSize(10);
		List<AppInformation> data = appInfoDao.selectByPage(example, param);
		if (data.size() == 0) {
			response.failure(ErrorCode.ERROR_SEARCH_APP_UNEXIST);
			response.setMsg("亲，找不到您要的APP~");
			return response;
		}

		List<AppBaseResult> appData = new ArrayList<AppBaseResult>();
		for (int i = 0; i < data.size(); i++) {
			AppBaseResult appBase = new AppBaseResult();
			appBase.init(data.get(i));
			appData.add(appBase);
		}

		Page<AppBaseResult> page = new Page<AppBaseResult>();

		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalCount(param.getTotalCount());
		page.setTotalPageCount(param.getTotalPageCount());
		int haveMore = (int) (param.getTotalPageCount() - param.getPageNo());
		if (haveMore > 0) {
			page.setHasMore(true);
		} else {
			page.setHasMore(false);
		}
		page.setResult(appData);
		response.success(page);

		return response;
	}

	/**
	 * 先检查该广告的状态。0-待审核 1-发布 2-下架
	 */
	@Override
	public Response queryAdvertAll() {
		Response response = new Response();
		AppAdvertExample example = new AppAdvertExample();
		AppAdvertExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		example.setOrderByClause("prority desc");
		List<AppAdvert> datas = appAdvertDao.selectByExample(example);
		List<AdvertBaseResult> advertData = new ArrayList<AdvertBaseResult>();
		for (int i = 0; i < datas.size(); i++) {
			AdvertBaseResult advertBase = new AdvertBaseResult();
			advertBase.init(datas.get(i));
			advertData.add(advertBase);
		}
		response.success(advertData);
		return response;
	}

	@Override
	public Response queryChannelAll() {
		Response response = new Response();
		AppChannelExample example = new AppChannelExample();
		AppChannelExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		example.setOrderByClause("prority desc");
		List<AppChannel> datas = appChannelDao.selectByExample(example);
		List<AppChannelResult> channelData = new ArrayList<AppChannelResult>();
		for (int i = 0; i < datas.size(); i++) {
			AppChannelResult channelBase = new AppChannelResult();
			channelBase.init(datas.get(i));
			channelData.add(channelBase);
		}
		response.success(channelData);
		return response;
	}

	@Override
	public Response queryByAppId(Integer param) {
		Response response = new Response();
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria con = example.createCriteria();
		//con.andAppIndexEqualTo(param);
		con.andIdEqualTo(param);
		List<AppInformation> data = appInfoDao.selectByExample(example);
		if (data.size() == 0) {
			response.setCode(200);
			response.setMsg("亲，找不到您要的APP~");
			return response;
		}
		AppDetailResult result = new AppDetailResult();
		result.init(data.get(0));
		response.success(result);
		return response;
	}

	@Override
	public Response hotSearch() {
		Response response = new Response();
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		example.setOrderByClause("prority desc");

		List<String> nameList = new ArrayList<String>();
		List<AppInformation> data = appInfoDao.selectByExample(example);
		if (data.size() > 4) {
			for (int i = 0; i < 4; i++) {
				nameList.add(data.get(i).getAppName());
			}
		} else {
			for (int i = 0; i < data.size(); i++) {
				nameList.add(data.get(i).getAppName());
			}
		}
		response.success(nameList);
		return response;
	}

	@Override
	public Response queryByIdList(Page<AppChannel> param, List<Integer> idList) {
		Response response = new Response();
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria con = example.createCriteria();
		con.andStatusEqualTo(1);
		example.setOrderByClause("prority desc");
		con.andIdIn(idList);
		param.setPageSize(10);
		List<AppInformation> appList = appInfoDao.selectByPage(example, param);
		if (appList.size() == 0) {
			response.setErrorCode(ErrorCode.ERROR_SEARCH_APP_UNEXIST);
			response.setMsg("亲，没有更多应用咯~");
			return response;
		}
		List<AppBaseResult> appData = new ArrayList<AppBaseResult>();
		for (int i = 0; i < appList.size(); i++) {
			AppBaseResult appBase = new AppBaseResult();
			appBase.init(appList.get(i));
			appData.add(appBase);
		}

		Page<AppBaseResult> page = new Page<AppBaseResult>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalCount(param.getTotalCount());
		page.setTotalPageCount(param.getTotalPageCount());
		int haveMore = (int) (param.getTotalPageCount() - param.getPageNo());
		if (haveMore > 0) {
			page.setHasMore(true);
		} else {
			page.setHasMore(false);
		}
		page.setResult(appData);
		response.success(page);
		return response;
	}

	@Override
	public Response queryByAppName(String param) {

		Response response = new Response();
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria criteria = example.createCriteria();
		criteria.andAppNameLike("%" + param + "%");
		criteria.andStatusEqualTo(1);
		example.setOrderByClause("prority desc");

		List<AppBaseResult> appData = new ArrayList<AppBaseResult>();
		List<AppInformation> data = appInfoDao.selectByExample(example);

		int result = data.size();
		switch (result) {
		case 0:
			response.failure(ErrorCode.ERROR_SEARCH_APP_UNEXIST);
			response.setMsg("亲，找不到您要的APP~");
			break;
		case 1:
			AppBaseResult appResult = new AppBaseResult();
			appResult.init(data.get(0));
			appData.add(appResult);
			response.success(appData);
			break;
		default:
			for (int i = 0; i < 2; i++) {
				AppBaseResult appBase = new AppBaseResult();
				appBase.init(data.get(i));
				appData.add(appBase);
			}
			response.success(appData);
			break;
		}
		
		return response;
	}

	@Override
	public Response appModify(Map<String ,Object> param) {
		Response response = new Response();
		AppInformation appInfo=new AppInformation();
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria criteria=example.createCriteria();
		Integer appId=(Integer) param.get("app_id");
		String appSpell=(String) param.get("app_spell");
		appInfo.setAppSpell(appSpell);
		
		criteria.andIdEqualTo(appId);
		
		int result=appInfoDao.updateByExampleSelective(appInfo, example);
		if (result==0){
			response.setErrorCode(ErrorCode.ERROR_PARAM_BIND_EXCEPTION);
			response.setMsg("修改App失败");
			return response;
		}
		return response;
	}

	@Override
	public Response appDelete(Integer param) {
		Response response = new Response();
//		AppInformationExample example = new AppInformationExample();
//		AppInformationExample.Criteria criteria=example.createCriteria();
		int result=appInfoDao.deleteByPrimaryKey(param);
		if(result==0){
			response.setErrorCode(ErrorCode.ERROR_SEARCH_APP_UNEXIST);
			response.setMsg("删除App失败");
			return response;
		}
		return response;
	}

	
	private String getAppSize(long size) {
		BigDecimal filesize = new BigDecimal(size);
		BigDecimal megabyte = new BigDecimal(1024 * 1000);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
		return returnValue+"M";
	}
}
