package com.fise.model.param;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryUserParam implements Serializable{
    
    private static final long serialVersionUID=1L;
    
    private String domain;
    
    private String nick;

    @JsonProperty("user_id")
    private Integer userId;
    
    @JsonProperty("online_status")
    private Integer onlineStatus;
    
    
    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    
}
