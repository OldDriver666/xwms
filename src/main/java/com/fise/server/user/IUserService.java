package com.fise.server.user;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.param.QueryUserParam;

public interface IUserService {
    /**
     * 查询User
     * @param param
     * @return
     */
    public Response queryUserByPage(Page<QueryUserParam> param);
    
    /**
     * 用户位置信息
     * @param param
     * @return
     */
    public Response queryUserLocation(QueryUserParam param);
    
    /**
     * 病人位置信息
     * @param param
     * @return
     */
    public Response queryUserPatient(QueryUserParam param);
    
    /**
     * 用户详细信息
     * @param param
     * @return
     */
    public Response queryUserDetail(QueryUserParam param);
}
