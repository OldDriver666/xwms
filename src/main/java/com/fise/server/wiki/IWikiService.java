package com.fise.server.wiki;

import com.fise.base.Response;
import com.fise.model.entity.Wiki;
import com.fise.model.param.WikiParam;

public interface IWikiService {
	/*添加suggest信息*/
	public Response insertWiki(Wiki record);
	
	/*查询suggest信息*/
	public Response queryWiki(WikiParam param);
	
	/*删除suggest信息*/
	public Response delWiki(WikiParam param);
	
	/*修改suggest信息*/
	public Response updateWiki(Wiki record);
}
