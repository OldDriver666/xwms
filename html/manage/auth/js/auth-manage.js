$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var admin_id = Util.cookieStorage.getCookie("adminId");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "AuthorityManage/AddAuthority";
            var data = {
                UserName : userName,
                AuthenticCode : token_value,
                AuthorityName : $("#input-authName").val(),
                AuthRole : parseInt($("#input-authRole").val()),
                AuthInterface : $("#input-authInter").val(),
                AuthDescrible : $("#input-authNotes").val(),
                AuthorityLevel:1,
                FatherId : 0,
                UserId : parseInt(admin_id)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.Status == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    action.loadPageData();
                }
            });
		},
		//获取所有数据
		loadPageData : function() {
            var td_len = $("#table thead tr th").length;//表格字段数量
            var url = ctx + "AuthorityManage/GetAuthority";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "AuthRole" : 3,
                "FatherId":0
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $('#pageContent').find("tr").remove();
                    $("#pageTmpl").tmpl(result.AuthenticInfo).appendTo('#pageContent');

                    if($('#pageContent tr').length == 0){
                        $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
					}
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
		},
        //获取角色列表数据
        loadRoleData : function() {
            var url = ctx + "RoleManage/GetRoleInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $("#pageRoleList").tmpl(result.RoleInfo).appendTo('#input-authRole');
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
        },

		//编辑数据
		edit : function() {
			var url = ctx + "AuthorityManage/ModifyAuthority";
			var data = new Object();
            data.UserName = userName;
            data.AuthenticCode = token_value;
			data.AuthenticId = parseInt($("#input-id").val());
			data.AuthorityName = $("#input-authName").val();
			data.AuthorityLevel = 1;//1-目录，2-功能
			data.AuthRole = parseInt($("#input-authRole").val());//目录所属角色
			data.FatherId = 0;//父权限ID
		 	data.AuthInterface = $("#input-authInter").val();
			data.AuthDescrible = $("#input-authNotes").val();
			Util.ajaxLoadData(url,data,"POST",true,function(result) {
				if (result.Status == ReturnCode.SUCCESS) {
			 		$("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
				}else{
					alert("编辑失败！请正确填写内容");
				}
			});
		},
		//删除数据
		deleteFunc : function(id, name, level) {
			if (confirm('删除后不可恢复，确定删除 " ' + name + ' " 权限？')) {
				var url = ctx + "AuthorityManage/DelAuthority";
				var data = new Object();
                data.UserName = userName;
                data.AuthenticCode = token_value;
                data.AuthenticId = id;
                data.AuthorityLevel = level;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.Status == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
                        action.loadPageData();
					}
				});
			}
		}

	};
	window.action = action;
	action.loadPageData();
	action.loadRoleData();

    //跳转页面
    $("#pageContent").on("click",".toPage-btn",function(){
        var that = $(this).parent().parent();
        var authorityId = that.find("td").eq(0).text();
        var authRole = that.find(".auth-role").text();
        url = "auth-manage-handle.html?authorityId="+escape(authorityId);
        url += "&authRole=" + escape(authRole);
        location.href=url;
    });

    //编辑获取数据数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-authName").val(that.find("td").eq(1).text());
        $("#input-authRole").val(that.find("td").eq(6).text());
        $("#input-authInter").val(that.find("td").eq(3).text());
        $("#input-authNotes").val(that.find("td").eq(4).text());
        $("#addTempl-modal").modal("show");
    });

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑目录权限");
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加目录权限");
			$form.data("action", "add");
			$form[0].reset();
		}
	});

	//验证表单
    $("#form-addTempl").validate({
        rules : {
            authName : {
                required : true
            },
            authRole : {
            	required : true
            },
            authInter : {
                required : true
            }
        }
    });

	$("#btn-add-submit").on('click', function() {
		if (!$("#form-addTempl").valid()) {
			return;
		}
		var action = $("form#form-addTempl").data("action");
		switch (action) {
		case "add":
			window.action.add();
			break;
		case "edit":
			window.action.edit();
			break;
		}
	});

	$("#btn-search").on('click', function() {
        action.loadPageData();
	});
	$("#input-search-txt").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

	});
});