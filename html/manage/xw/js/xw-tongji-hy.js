$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = 1;//所属公司id

	var action = {
		//获取所有数据
		loadPageData : function() {
			var search_txt = $("#input-search-txt").val();
            var td_len = $("#table thead tr th").length;//表格字段数量
            if (search_txt && $.trim(search_txt) != "") {
                var url = ctx + "UserManage/QueryUserFriend";
                var data = {
                    XWNo:search_txt,
                    UserName:userName,
                    AuthenticCode:token_value,
                    Page:1,
                    PageSize:50
                }
            }else{
                alert("请输入查询内容！");
            }
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $('#pageContent').find("tr").remove();
                    $("#pageTmpl").tmpl(result.FriendInfo).appendTo('#pageContent');

                    if($('#pageContent tr').length == 0){
                        $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
                    }
                }else if(result.Status == 3 || result.Status == 5){
                    $('#pageContent').find("tr").remove();
                    $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>没有该用户信息</td></tr>");
				}else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

		}

	};


	$("#btn-search").on('click', function() {
        action.loadPageData();
	});
	$("#input-search-txt").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

	});
});