package com.fise.base;

import java.io.Serializable;

import com.fise.utils.JsonUtil;

public class KeyValueMap  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Object key;
    
    private Object value;

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
