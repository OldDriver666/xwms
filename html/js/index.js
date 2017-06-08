$(function(){
    var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var role_level = Util.cookieStorage.getCookie("userLevel");
	var admin_id = Util.cookieStorage.getCookie("adminId");
    var user_authCode = Util.cookieStorage.getCookie("userAuthCode");
    var nick_name = Util.cookieStorage.getCookie("nickname");
    //安全退出
    $("#header-safeExit").on('click', function(){
        Util.cookieStorage.clearCookie("username");
        Util.cookieStorage.clearCookie("accesstoken");
        Util.cookieStorage.clearCookie("nickname");
        Util.cookieStorage.clearCookie("adminId");
        Util.cookieStorage.clearCookie("departId");
        Util.cookieStorage.clearCookie("userLevel");
        Util.cookieStorage.clearCookie("userAuthCode");
        window.location.href = "login.html";
    });

	var Index = {
		init:function(){
			Index.loadMenu();
            $("#admin-header-nick").text(nick_name);
		},
		loadMenu : function(){
            var menu_json = JSON.parse(user_authCode);
            for(var i in menu_json){
                $("#pageMenu").tmpl(menu_json[i]).appendTo('#menuContent');
			}
		}

	};
	Index.init();
});

