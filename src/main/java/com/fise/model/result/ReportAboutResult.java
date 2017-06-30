package com.fise.model.result;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fise.base.KeyValueMap;
import com.fise.utils.JsonUtil;

public class ReportAboutResult implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Map<String,List<KeyValueMap>> data;
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
