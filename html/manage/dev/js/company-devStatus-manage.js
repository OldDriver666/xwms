$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var user_level = Util.cookieStorage.getCookie("userLevel");

	var action = {
		//获取所有数据
		loadPageData : function() {
            var url = ctx + "DeviceCount/GetAllDeviceCountInfo";
            var data = {
                UserName:userName,
                AuthenticCode:token_value,
                RoleLevel:parseInt(user_level)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $("#pageTmpl").tmpl(result.DeviceInfo).appendTo('#pageContent');
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
		}
	};
	window.action = action;
	action.loadPageData();

});