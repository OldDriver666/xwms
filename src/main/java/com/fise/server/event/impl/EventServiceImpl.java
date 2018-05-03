package com.fise.server.event.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.IMEventMapper;
import com.fise.dao.IMUserMapper;
import com.fise.model.entity.IMEvent;
import com.fise.model.entity.IMEventExample;
import com.fise.model.entity.IMUser;
import com.fise.model.entity.IMUserExample;
import com.fise.model.param.EventQueryParam;
import com.fise.server.event.IEventService;


@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private IMEventMapper eventDao;
    @Autowired
    private IMUserMapper userDao;

    @Override
    public Response query(Page<EventQueryParam> param) {
        Response resp = new Response();
        //查询获取用户ID
        IMUserExample example = new IMUserExample();
        IMUserExample.Criteria criteria = example.createCriteria();
        criteria.andDomainEqualTo(param.getParam().getAccount());
        List<IMUser> userList = userDao.selectByExample(example);
        if(userList.isEmpty()){
            resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
            return resp;
        }
        Integer userId = userList.get(0).getId();
        String tableName = "IMEvent_" + userId.intValue() % 8;

        IMEventExample example2 = new IMEventExample();
        IMEventExample.Criteria criteria2 = example2.createCriteria();
        criteria2.andUserIdEqualTo(userId);
        example2.setOrderByClause("created desc");
        List<IMEvent> eventList = eventDao.selectByExample(tableName, example2,param);
        
        Page<IMEvent> page=new Page<IMEvent>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalCount(param.getTotalCount());
        page.setTotalPageCount(param.getTotalPageCount());
        page.setResult(eventList);
              
        resp.success(page);
        return resp;
    }

	@Override
	public Map<Integer, Integer> queryTypeDayEvents(String daytime) {

		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		List<IMEvent> list = null;

		for (int i = 0; i < 8; i++) {
			list = eventDao.queryTypeDayEvents("IMEvent_" + i, daytime);
			for (IMEvent event : list) {
				if (null == map.get(event.getEventKey())) {
					map.put(event.getEventKey(), event.getCount());
				} else {
					map.put(event.getEventKey(), map.get(event.getEventKey()) + event.getCount());
				}
			}
		}
		return map;
	}

}
