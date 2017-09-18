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
        push: function(){
            var url = ctx + "boss/admin/islogin";
            var data = new Object();
            var moduleId = 0;
            data.accessToken = token_value;
            data.depart_id = parseInt(depart_id);
            data.company_id = parseInt(company_id);

            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS && result.data != ""){

                }else if(result.code == ReturnCode.TOKEN_ERROR){
                    alert(result.msg);
                    window.location.href = "login.html";
                }
            },function(errorMsg) {
                /*alert(errorMsg);*/
            });

        }
	};
    setTimeout(function(){
        Index.push();
    },200);

    setInterval(function(){
        Index.push();
    },300000);
});

