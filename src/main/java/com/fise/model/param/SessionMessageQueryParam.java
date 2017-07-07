package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class SessionMessageQueryParam implements Serializable{
    private static final long serialVersionUid=1L;
    
    @JsonProperty("small_id")
    @NotNull
    private Integer smallId;
    
    @JsonProperty("big_id")
    @NotNull
    private Integer bigId;

    public Integer getSmallId() {
        return smallId;
    }

    public void setSmallId(Integer smallId) {
        this.smallId = smallId;
    }

    public Integer getBigId() {
        return bigId;
    }

    public void setBigId(Integer bigId) {
        this.bigId = bigId;
    }

    @Override
    public String toString() {    
        return JsonUtil.toJson(this);
    }
    
    
}
