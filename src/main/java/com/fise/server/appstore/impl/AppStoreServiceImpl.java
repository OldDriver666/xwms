package com.fise.server.appstore.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Response insert(AppStore param) {
		Response resp = new Response();
		param.setAppId(StringUtil.md5(param.getAppName()));
		appDao.insert(param);
		resp.setData(param);
		return resp;
	}

	@Override
	public List<AppInformation> queryByIdList(List<Integer> idList) {
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria con = example.createCriteria();
		example.setOrderByClause("prority desc");
		con.andIdIn(idList);
		List<AppInformation> data = appInfoDao.selectByExample(example);
		return data;
	}

	// 分页查询App
	@Override
	public Response queryAppAll(Page<AppInformation> param) {
		Response response = new Response();
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria con = example.createCriteria();

		con.andStatusEqualTo(1);
		example.setOrderByClause("prority desc");
		param.setPageSize(1);
		List<AppInformation> data = appInfoDao.selectByPage(example, param);
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
		int haveMore = (int) (param.getTotalCount() - param.getPageNo());
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

		List<AppBaseResult> appData = new ArrayList<AppBaseResult>();
		List<AppInformation> data = appInfoDao.selectByExample(example);

		int result = data.size();
		switch (result) {
		case 0:
			response.failure(ErrorCode.ERROR_SEARCH_APP_UNEXIST);
			break;
		case 1:
			AppBaseResult appResult = new AppBaseResult();
			appResult.init(data.get(0));
			appData.add(appResult);
			response.success(appData);
			break;
		default:
			if (param.getParam().getAutoApp().equals("true")) {
				for (int i = 0; i < 2; i++) {
					AppBaseResult appBase = new AppBaseResult();
					appBase.init(data.get(i));
					appData.add(appBase);
				}
				response.success(appData);
			}
			if (param.getParam().getAutoApp().equals("false")) {
				param.setPageSize(1);
				List<AppInformation> pageData = appInfoDao.selectByPage(example, param);
				for (int i = 0; i < pageData.size(); i++) {
					AppBaseResult appBase = new AppBaseResult();
					appBase.init(pageData.get(i));
					appData.add(appBase);
				}

				Page<AppBaseResult> page = new Page<AppBaseResult>();

				page.setPageNo(param.getPageNo());
				page.setPageSize(param.getPageSize());
				page.setTotalCount(param.getTotalCount());
				page.setTotalPageCount(param.getTotalPageCount());
				int haveMore = (int) (param.getTotalCount() - param.getPageNo());
				if (haveMore > 0) {
					page.setHasMore(true);
				} else {
					page.setHasMore(false);
				}
				page.setResult(appData);
				response.success(page);
			}
			break;
		}
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
	public AppInformation queryByAppIndex(String param) {
		AppInformationExample example = new AppInformationExample();
		AppInformationExample.Criteria con = example.createCriteria();
		con.andAppIndexEqualTo(param);
		List<AppInformation> data = appInfoDao.selectByExample(example);
		if (data.isEmpty())
			return null;
		else
			return data.get(0);
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
			for (int i = 0; i <data.size(); i++) {
				nameList.add(data.get(i).getAppName());
			}
		}
		response.success(nameList);
		return response;
	}

}
