package com.fise.model.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.model.entity.AppChannel;
import com.fise.utils.JsonUtil;

public class AppChannelResult {
    @JsonProperty("channel_id")
    private Integer channelId;
    private String name;
    private Integer type;
    private String background;
    private String desc;
    @JsonProperty("app_list")
    private List<AppBaseResult> appList;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<AppBaseResult> getAppList() {
        return appList;
    }

    public void setAppList(List<AppBaseResult> appList) {
        this.appList = appList;
    }
    
    public void init(AppChannel data){
        this.channelId = data.getId();
        this.name = data.getChannelName();
        this.type = data.getChannelType();
        this.background = data.getBackground();
        this.desc = data.getDesc();
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
