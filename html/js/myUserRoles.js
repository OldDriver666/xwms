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
        myUserRoles: function(){
            var myUserRolesArray = [];
            var url = ctx + "boss/role/query";
            var moduleId = 0;
            var data = new Object();
            data.role_id = parseInt(role_level);
            data.company_id = parseInt(company_id);
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS && result.data != ""){
                    myUserRolesArray = result.data;
                    localStorage.setItem("myUserRolesArray",JSON.stringify(myUserRolesArray));
                } else {
                    /*alert(result.msg);*/
                }
            },function() {
                /*alert("服务器开个小差，请稍后重试！")*/
            });
        }
	};
    Index.myUserRoles();
});

