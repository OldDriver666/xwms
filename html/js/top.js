$(function(){
    var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var role_level = Util.cookieStorage.getCookie("userLevel");
	var admin_id = Util.cookieStorage.getCookie("adminId");
    var nick_name = Util.cookieStorage.getCookie("nickname");
    var company_id = Util.cookieStorage.getCookie("companyId");
    var depart_id = Util.cookieStorage.getCookie("departId");
    //安全退出
    $("#header-safeExit").on('click', function(){
        var url = ctx + "boss/admin/logout";
        var data = {"adminId":parseInt(admin_id)};
        Util.ajaxLoadData(url,data,0,"POST",true,function(result) {
            if(result.code == ReturnCode.SUCCESS){
                Util.cookieStorage.clearCookie("username");
                Util.cookieStorage.clearCookie("accesstoken");
                Util.cookieStorage.clearCookie("nickname");
                Util.cookieStorage.clearCookie("adminId");
                Util.cookieStorage.clearCookie("departId");
                Util.cookieStorage.clearCookie("userLevel");
                localStorage.removeItem("myDevTypeArray");
                localStorage.removeItem("myUserRolesArray");
                localStorage.removeItem("allDevTypeArray");
                localStorage.removeItem("allCompanyArray");

                window.open('login.html', '_parent');
            } else {
            }
        },function(errorMsg) {
            alert(errorMsg)
        });
    });

    //页面加载左侧Menu菜单栏
	var Index = {
		init:function(){
			Index.loadMenu();
            //$("#admin-header-nick").text(nick_name);
		},
		loadMenu : function(){
            var url = ctx + "boss/role/queryPatientAuth";
            var moduleId= 0;
            var data = {
                "role_id":parseInt(role_level)
            };
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    $("#pageMenu").tmpl(result.data).appendTo('#menuContent');
                    /*var rdata = result.data;
                    var data = rdata.sort(Index.sortBy('priority',false))
                    var Len = data.length;
                    var parent_data = new Array;

                    for(var i=0; i<Len; i++){
                        var mid = data[i].id;
                        var pid = data[i].parentId;

                        if(0 == pid){
                            var children_menu = new Array;
                            for(var j=0; j<Len; j++){
                                if(mid == data[j].parentId){
                                    var str = data[j];
                                    children_menu.push(str);
                                }
                            }
                            data[i].children = children_menu;
                            parent_data.push(data[i]);
                        }
                    }
                    for(var i in parent_data){
                        $("#pageMenu").tmpl(parent_data[i]).appendTo('#menuContent');
                    }*/
                } else {
                }
            },function(errorMsg) {
                alert(errorMsg)
            });
		},
        sortBy: function(attr,rev){
            //第二个参数没有传递 默认升序排列
            if(rev ==  undefined){
                rev = 1;
            }else{
                rev = (rev) ? 1 : -1;
            }
            return function(a,b){
                a = a[attr];
                b = b[attr];
                if(a < b){
                    return rev * -1;
                }
                if(a > b){
                    return rev * 1;
                }
                return 0;
            }
        }
	};
	Index.init();
});

