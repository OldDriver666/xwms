package com.fise.model.param;

import java.io.Serializable;

import com.fise.utils.JsonUtil;

/** 
 * @author bension
 * @email liaoguoshun@qq.com
 * @date 2017-6-2
 * @desc 查询模块参数返回实体类
 */

public class ModuleQueryResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer permissId;
    
    private Integer moduleId;
    
    private String moduleName;
    
    private Integer priority;
    
    private String sn;
    
    private String url;
    
    private Integer parentId;
    
    private Integer insertAuth;
    
    private Integer updateAuth;
    
    private Integer queryAuth;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParent_id() {
        return parentId;
    }

    public void setParent_id(Integer parent_id) {
        this.parentId = parent_id;
    }

    public Integer getInsertAuth() {
        return insertAuth;
    }

    public void setInsertAuth(Integer insertAuth) {
        this.insertAuth = insertAuth;
    }

    public Integer getUpdateAuth() {
        return updateAuth;
    }

    public void setUpdateAuth(Integer updateAuth) {
        this.updateAuth = updateAuth;
    }

    public Integer getQueryAuth() {
        return queryAuth;
    }

    public void setQueryAuth(Integer queryAuth) {
        this.queryAuth = queryAuth;
    }

    public Integer getPermissId() {
        return permissId;
    }

    public void setPermissId(Integer permissId) {
        this.permissId = permissId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
