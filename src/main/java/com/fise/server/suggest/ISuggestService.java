package com.fise.server.suggest;

import com.fise.base.Response;
import com.fise.model.entity.IMSuggest;
import com.fise.model.param.SuggestParam;

public interface ISuggestService {
	/*添加suggest信息*/
	public Response insertSuggest(IMSuggest record);
	
	/*查询suggest信息*/
	public Response querySuggest(SuggestParam param);
	
	/*删除suggest信息*/
	public Response delSuggest(SuggestParam param);
	
	/*修改suggest信息*/
	public Response updateSuggest(IMSuggest record);
}
