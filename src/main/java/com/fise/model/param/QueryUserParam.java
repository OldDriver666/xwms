package com.fise.model.param;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryUserParam implements Serializable{
    private static final long serialVersionUid=1L;
    
    private String domain;
    
    private String nick;

    @JsonProperty("user_id")
    private Integer userId;
    
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
