$(function(){
    var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
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
            var data = new Object();
            data.client_type = null;
            data.client_name = "";
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS && result.data != ""){
                    dataArray1 = result.data;

                    var url_query = ctx + "boss/departconf/queryimdepartconfig";
                    var data_query = new Object();
                    data_query.depart_id = parseInt(depart_id);
                    data_query.client_type = null;
                    Util.ajaxLoadData(url_query,data_query,"POST",true,function(result_query) {
                        if(result_query.code == ReturnCode.SUCCESS && result_query.data != ""){
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
                            Util.cookieStorage.setCookie("myDevTypeArray",JSON.stringify(myDevTypeArray));
                            Util.cookieStorage.setCookie("allDevTypeArray",JSON.stringify(dataArray1));
                        } else {
                            alert(result_query.msg);
                        }
                    },function() {
                        alert("服务器开个小差，请稍后重试！")
                    });

                } else {
                    alert(result.msg);
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

        }
	};
    Index.myDevTypeQuery();
});

