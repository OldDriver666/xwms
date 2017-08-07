package com.fise.server.auth;

public interface IAuthService {
    //查询用户是否有该功能模块的查询权限
    public Boolean queryAuth(Integer module_id);
    
    //查询用户是否有该功能模块的添加权限
    public Boolean inserAuth(Integer module_id);
    
    //查询用户是否有该功能模块的更新权限
    public Boolean updateAuth(Integer module_id);
}
