package com.fise.server.vediorecord;

import java.text.ParseException;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.param.QueryVedioRecordParam;

public interface IVedioRecordService {
    //查询VideoRecord
    public Response queryVideoRecordByPage(Page<QueryVedioRecordParam> param) throws ParseException;

}
