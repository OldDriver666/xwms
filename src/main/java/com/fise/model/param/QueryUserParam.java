package com.fise.model.param;

import java.io.Serializable;

public class QueryUserParam implements Serializable{
    private static final long serialVersionUid=1L;
    
    private String domain;
    
    private String nick;

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
    
    
}
