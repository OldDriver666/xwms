$(function(){
    var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var company_id = Util.cookieStorage.getCookie("companyId");
    var role_level = Util.cookieStorage.getCookie("userLevel");
    var admin_id = Util.cookieStorage.getCookie("adminId");
    var nick_name = Util.cookieStorage.getCookie("nickname");

    //页面加载左侧Menu菜单栏
	var Index = {
        myDevTypeQuery: function(){
            var dataArray1 = [];
            var dataArray2 = [];
            var myDevTypeArray = [];
            var url = ctx + "boss/clienttype/queryclienttype";
            var moduleId = 0;
            var data = new Object();
            data.client_type = null;
            data.client_name = "";

            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    dataArray1 = result.data;

                    var url_query = ctx + "boss/departconf/queryimdepartconfig";
                    var moduleId_query = 0;
                    var data_query = new Object();
                    data_query.client_type = null;
                    data_query.depart_id = null;

                    Util.ajaxLoadData(url_query,data_query,moduleId_query,"POST",true,function(result_query) {
                        if(result_query.code == ReturnCode.SUCCESS){
                            dataArray2 = result_query.data;
                            var Len1 = dataArray1.length;
                            var Len2 = dataArray2.length;
                            for(var i =0; i < Len2; i++){
                                for(var j=0; j<Len1; j++){
                                    if(dataArray2[i].client_type == dataArray1[j].client_type){
                                        var str ={
                                            client_type :dataArray1[j].client_type,
                                            client_name :dataArray1[j].client_name
                                        };
                                        myDevTypeArray.push(str);
                                    }
                                }
                            }
                            localStorage.setItem("myDevTypeArray",JSON.stringify(myDevTypeArray));
                            localStorage.setItem("allDevTypeArray",JSON.stringify(dataArray1));
                        } else {
                            //alert(result_query.msg);
                        }
                    },function(errorMsg) {
                        //alert(errorMsg)
                    });

                } else {
                   /* alert(result.msg);*/
                }
            },function(errorMsg) {
                //alert(errorMsg)
            });

        }
	};
    Index.myDevTypeQuery();
});

