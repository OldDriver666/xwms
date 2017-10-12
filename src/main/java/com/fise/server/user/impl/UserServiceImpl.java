package com.fise.server.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.IMUserMapper;
import com.fise.model.entity.IMUser;
import com.fise.model.entity.IMUserExample;
import com.fise.model.entity.IMUserExample.Criteria;
import com.fise.model.param.QueryUserParam;
import com.fise.model.result.UserDetail;
import com.fise.model.result.UserLocationInfo;
import com.fise.server.depart.IDepartmentService;
import com.fise.server.user.IUserService;
import com.fise.utils.StringUtil;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IMUserMapper IMUserDao;

    @Resource
    IDepartmentService departSvr;

    @Override
    public Response queryUserByPage(Page<QueryUserParam> param) {

        Response response = new Response();

        IMUserExample example = new IMUserExample();
        Criteria criteria = example.createCriteria();

        if (!StringUtil.isEmpty(param.getParam().getDomain())) {
            criteria.andDomainEqualTo(param.getParam().getDomain());
        }

        if (!StringUtil.isEmpty(param.getParam().getPhone())) {
            criteria.andPhoneEqualTo(param.getParam().getPhone());
        }

        if (param.getParam().getUserId() != null) {
            criteria.andIdEqualTo(param.getParam().getUserId());
        }

        if (param.getParam().getOnlineStatus() != null) {
            criteria.andOnlineStatusEqualTo(param.getParam().getOnlineStatus());
        }

        List<IMUser> list = IMUserDao.selectByExamplebypage(example, param);

        if (list.size() == 0) {
            return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
        }

        Page<IMUser> page = new Page<IMUser>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalCount(param.getTotalCount());
        page.setTotalPageCount(param.getTotalPageCount());
        page.setResult(list);

        response.success(page);
        return response;
    }

    @Override
    public Response queryUserLocation(QueryUserParam param) {

        Response response = new Response();

        IMUserExample example = new IMUserExample();
        Criteria criteria = example.createCriteria();

        if (!StringUtil.isEmpty(param.getDomain())) {
            criteria.andDomainEqualTo(param.getDomain());
        }

        if (!StringUtil.isEmpty(param.getPhone())) {
            criteria.andPhoneEqualTo(param.getPhone());
        }

        if (param.getUserId() != null) {
            criteria.andIdEqualTo(param.getUserId());
        }

        if (param.getOnlineStatus() != null) {
            criteria.andOnlineStatusEqualTo(param.getOnlineStatus());
        }

        if (param.getDepartId() != null && param.getDepartId() != 0) {
            List<Integer> idList = departSvr.getChildDepatId(param.getDepartId());
            criteria.andDepartidIn(idList);
        }

        criteria.andCompanyidEqualTo(param.getCompanyId());
//        criteria.andTypeEqualTo(19);
        List<IMUser> dataList = IMUserDao.selectByExample(example);
        if (dataList.size() == 0) {
            return response;
        }
        // List<UserLocation> user_list = new ArrayList<UserLocation>();
        List<UserDetail> user_list = new ArrayList<UserDetail>();
        UserLocationInfo data = new UserLocationInfo();
        data.setTotal_cnt(dataList.size());
        int onlineCnt = 0;
        for (int i = 0; i < dataList.size(); i++) {
            IMUser u = dataList.get(i);
            if (u.getOnlineStatus().intValue() == 1) {
                onlineCnt++;
            }
            /*
             * UserLocation usr = new UserLocation();
             * usr.setLocation_x(u.getLat()); usr.setLocation_y(u.getLng());
             * usr.setUid(u.getId()); usr.setUname(u.getNick());
             * usr.setOnline_status(u.getOnlineStatus()); user_list.add(usr);
             */
            UserDetail detail = new UserDetail();
            detail.initUserDetail(dataList.get(i));
            user_list.add(detail);
        }
        data.setUser_list(user_list);
        data.setOnline_cnt(onlineCnt);
        response.success(data);
        return response;
    }

    @Override
    public Response queryUserDetail(QueryUserParam param) {

        Response response = new Response();

        IMUserExample example = new IMUserExample();
        Criteria criteria = example.createCriteria();

        if (!StringUtil.isEmpty(param.getDomain())) {
            criteria.andDomainEqualTo(param.getDomain());
        }

        if (!StringUtil.isEmpty(param.getPhone())) {
            criteria.andPhoneEqualTo(param.getPhone());
        }

        if (param.getUserId() != null) {
            criteria.andIdEqualTo(param.getUserId());
        }

        if (param.getOnlineStatus() != null) {
            criteria.andOnlineStatusEqualTo(param.getOnlineStatus());
        }

        criteria.andTypeGreaterThanOrEqualTo(19);
        List<IMUser> dataList = IMUserDao.selectByExample(example);
        if (dataList.size() == 0) {
            response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
        } else {
            UserDetail detail = new UserDetail();
            detail.initUserDetail(dataList.get(0));
            response.success(detail);
        }
        return response;
    }

}
