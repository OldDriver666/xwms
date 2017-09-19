package com.fise.model.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.model.entity.AppInformation;
import com.fise.utils.JsonUtil;

public class AppBaseResult {
    @JsonProperty("app_index")
    private String appIndex;
    private String name;
    private String type;
    private String size;
    private String icon;

    public String getAppIndex() {
        return appIndex;
    }

    public void setAppIndex(String appIndex) {
        this.appIndex = appIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void init(AppInformation data){
        this.appIndex = data.getAppIndex();
        this.name = data.getAppName();
        this.type = data.getCategory();
        this.size = data.getSize();
        this.icon = data.getIcon();
    }
    
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
