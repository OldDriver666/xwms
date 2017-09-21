package com.fise.server.user;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.param.QueryUserParam;

public interface IUserService {
    //查询User
    public Response queryUserByPage(Page<QueryUserParam> param);
    
    //用户位置信息
    public Response queryUserLocation(QueryUserParam param);
    
  //用户详细信息
    public Response queryUserDetail(QueryUserParam param);
}
