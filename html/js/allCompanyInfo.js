$(function(){
	var Index = {
        //获取全部公司团体数据
        allCompanyQuery: function(){
            var allCompanyArray = [];
            var url = ctx + "boss/organization/query";
            var moduleId = 0;
            var data = new Object();
            data.name = "";

            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    allCompanyArray = result.data;
                    localStorage.setItem("allCompanyArray",JSON.stringify(allCompanyArray));
                } else {
                }
            },function(errorMsg) {
                alert(errorMsg)
            });
        },
	};
    Index.allCompanyQuery();
});

