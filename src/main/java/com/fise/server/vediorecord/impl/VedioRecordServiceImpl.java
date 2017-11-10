package com.fise.server.vediorecord.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.IMVedioRecordeMapper;
import com.fise.framework.config.ConfigProperties;
import com.fise.model.entity.IMVedioRecorde;
import com.fise.model.entity.IMVedioRecordeExample;
import com.fise.model.entity.IMVedioRecordeExample.Criteria;
import com.fise.model.param.QueryVedioRecordParam;
import com.fise.server.vediorecord.IVedioRecordService;
import com.fise.utils.StringUtil;

@Service
public class VedioRecordServiceImpl implements IVedioRecordService {

    @Autowired
    IMVedioRecordeMapper videoRecordDao;

    @Override
    public Response queryVideoRecordByPage(Page<QueryVedioRecordParam> param) throws ParseException {

        Response response = new Response();
        String basePath = ConfigProperties.getValue("dev_medio_base_path").trim();

        IMVedioRecordeExample example = new IMVedioRecordeExample();
        Criteria criteria = example.createCriteria();
        if (null != param.getParam().getCompanyId()) {
        	criteria.andCompanyIdEqualTo(param.getParam().getCompanyId());
		}
        if (StringUtil.isNotEmpty(param.getParam().getAccount())) {
        	criteria.andAccountEqualTo(param.getParam().getAccount());
		}
        

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String date = param.getParam().getQueryDate();
    	if (StringUtil.isNotEmpty(date)) {
    		long value1 = sdf.parse(date).getTime()/1000;
        	criteria.andCreatedBetween(value1, value1 + 3600*24);
		}


        List<IMVedioRecorde> list = videoRecordDao.selectByPage(example, param);

        if (list.size() == 0) {
            return response.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_UNEXIST);
        }
        for (IMVedioRecorde imVedioRecorde : list) {
        	if (StringUtils.isNotBlank(imVedioRecorde.getImageUrl())) {
        		imVedioRecorde.setImageUrl(basePath + imVedioRecorde.getImageUrl().trim());
			}
        	if (StringUtils.isNotBlank(imVedioRecorde.getVedioUrl())) {
        		imVedioRecorde.setVedioUrl(basePath + imVedioRecorde.getVedioUrl().trim());
			}
		}

        Page<IMVedioRecorde> page = new Page<IMVedioRecorde>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalCount(param.getTotalCount());
        page.setTotalPageCount(param.getTotalPageCount());
        page.setResult(list);

        response.success(page);
        return response;
    }



}
