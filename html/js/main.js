$(function(){
    var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var role_level = Util.cookieStorage.getCookie("userLevel");
	var admin_id = Util.cookieStorage.getCookie("adminId");
    var nick_name = Util.cookieStorage.getCookie("nickname");
    var company_id = Util.cookieStorage.getCookie("companyId");
    var depart_id = Util.cookieStorage.getCookie("departId");

    //页面加载左侧Menu菜单栏
	var Index = {
		init:function(){
			Index.loadCheckMenu();
		},
        loadCheckMenu : function(){
            var url = ctx + "boss/depart/query";
            var moduleId= 0;
            var data = new Object();
            data.depart_name = '';
            data.creator_id = parseInt(admin_id);
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if (result.code == 0) {
                    var data = result.data;
                    var allMenuList = [];
                    for(var i=0; i<data.length; i++){
                        if(data[i].parentId == 0) {
                            var menuList1 = [];
                            menuList1.id = data[i].id
                            menuList1.departName = data[i].departName
                            menuList1.children = []
                            for (var j = 0; j < data.length; j++) {
                                if (data[i].id == data[j].parentId) {
                                    var menuList2 = [];
                                    menuList2.id = data[j].id
                                    menuList2.departName = data[j].departName
                                    menuList1.children.push(menuList2)
                                }
                            }
                            allMenuList.push(menuList1)
                        }
                    }
                    $("#pageMenu2").tmpl(allMenuList).appendTo('#menuContent2');
                }
            },function(errorMsg) {
                alert(errorMsg)
            });
        }
	};
	Index.init();
});

