package com.fise.model.result;

import com.fise.model.entity.AppSplash;
import com.fise.utils.JsonUtil;

public class StoreSplashResult {

    private String name;
    private String url;
    private String action;
    private Integer prority;
    private Integer delay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getPrority() {
        return prority;
    }

    public void setPrority(Integer prority) {
        this.prority = prority;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public void init(AppSplash data){
        this.name = data.getName();
        this.url = data.getUrl();
        this.action = data.getActionUrl();
        this.prority = data.getPrority();
        this.delay = data.getDelay();
    }
    
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
