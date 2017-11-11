package com.fise.server.vediorecord;

import java.text.ParseException;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.param.QueryVedioRecordParam;

public interface IVedioRecordService {
	
    /**
     * 查询设备多媒体信息
     * @param param
     * @return
     * @throws ParseException
     */
    public Response queryVideoRecordByPage(Page<QueryVedioRecordParam> param) throws ParseException;
    
    
    /**
     * 查询设备多媒体信息
     * @param param
     * @return
     * @throws ParseException
     */
    public Response queryVideoRecordCount(Integer companyId) throws ParseException;

}
