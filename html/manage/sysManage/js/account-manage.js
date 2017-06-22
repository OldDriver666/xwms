$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
	var admin_id = Util.cookieStorage.getCookie("adminId");
	var role_id = Util.cookieStorage.getCookie("userLevel");
	var depart_id = Util.cookieStorage.getCookie("departId");//所属公司id

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/admin/insert";
            var data = new Object();
			data.admin_id = parseInt(admin_id);
			data.account = $("#input-account").val();
			data.password = $.md5($("#input-password").val());
			data.nick_name = $("#input-nickName").val();
			data.role_id = $('#add-search-userRoles option:selected').val();
			//data.role_id = parseInt($('#add-search-userRoles option:selected').val());
			data.phone = $("#input-phone").val();
			data.email = $("#input-email").val();
			data.organization_id = parseInt($("#input-companyId").val());
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    action.loadPageData();
                }
            });
		},
		//获取所有数据
		loadPageData : function() {
            var search_account = $("#input-search-account").val();
            var search_role_id = parseInt($('#input-search-userRoles option:selected').val());
			var search_company_id = parseInt($("#input-search-company_id").val());

            var td_len = $("#table thead tr th").length;//表格字段数量

            var url = ctx + "boss/admin/query";
            var data = new Object();
			data.admin_id = parseInt(admin_id);
			data.account = search_account;
            data.role_id = search_role_id;
			data.company_id = search_company_id;

            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS && result.data != ""){
                    $('#pageContent').find("tr").remove();
                    $("#pageTmpl").tmpl(result.data).appendTo('#pageContent');

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
		//获取当前用户角色列表数据
		loadUserRolesData : function() {
			var rolesArray = [
				{
					"id": 1,
					"name": "BOSS"
				},
				{
					"id": 2,
					"name": "超级管理员"
				},
				{
					"id": 3,
					"name": "管理员"
				}
			];
			$("#pageUserRoles").tmpl(rolesArray).appendTo('#input-search-userRoles');

			var url = ctx + "boss/role/query";
			var data = new Object();
			data.role_id = parseInt(role_id);
			data.organ_id = parseInt(depart_id);
			Util.ajaxLoadData(url,data,"POST",true,function(result) {
				if(result.code == ReturnCode.SUCCESS && result.data != ""){
					$("#pageUserRoles").tmpl(result.data).appendTo('#add-search-userRoles');
				} else {
					alert("请求出错！");
				}
			},function() {
				alert("服务器开个小差，请稍后重试！")
			});
		},
		//编辑数据
		edit : function() {
			var url = ctx + "boss/admin/update";
			var data = new Object();
			data.login_id = parseInt(admin_id);
			data.admin_id = parseInt($("#input-id").val());
			data.account = $("#input-account").val();
			data.password = $("#input-password-wrap").val();
			data.nick_name = $("#input-nickName").val();
			data.role_id = parseInt($("#input-roleId").val());
			data.organization_id = parseInt($("#input-companyId").val());
			data.phone = $("#input-phone").val();
			data.email = $("#input-email").val();

			Util.ajaxLoadData(url,data,"POST",true,function(result) {
				if (result.code == ReturnCode.SUCCESS) {
			 		$("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
				}
			});
		},
		//删除数据
		deleteConfig : function(id) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "boss/clienttype/delclienttype";
				var data = new Object();
                data.type_id = id;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
						$("#input-search-client_type").val("");
						$("#input-search-client_name").val("");
                        action.loadPageData();
					}
				});
			}
		}
	};
	window.action = action;
	action.loadPageData();
	action.loadUserRolesData();

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑管理员");
			$("#input-password-txt").hide();
			$("#input-password-txt-wrap").show();
			$("#input-roleId-wrap").hide();
			$("#add-userRoles-wrap").hide();
			$("#input-roleName-txt-wrap").show();
			//$("#input-companyId-txt").show();
			/*$("#input-status-txt").show();*/
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加管理员");
			$("#input-password-txt").show();
			$("#input-password-txt-wrap").hide();
			$("#input-roleId-wrap").hide();
			$("#add-userRoles-wrap").show();
			$("#input-roleName-txt-wrap").hide();
			//$("#input-companyId-txt").hide();
			/*$("#input-status-txt").hide();*/
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();

        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-account").val(that.find("td").eq(1).text());
        $("#input-password-wrap").val(that.find("td").eq(2).text());
		$("#input-nickName").val(that.find("td").eq(3).text());
		$("#input-roleName-txt").val(that.find("td").eq(4).text());
		$("#input-roleId").val(that.find("td").eq(8).text());
		$("#input-phone").val(that.find("td").eq(5).text());
		$("#input-email").val(that.find("td").eq(6).text());
		$("#input-companyId").val(that.find("td").eq(7).text());
		/*$("#input-status").val(that.find("td").eq(8).text());*/


        $("#addTempl-modal").modal("show");
    });

	//验证表单
    $("#form-addTempl").validate({
        rules : {
			account : {
                required : true
            },
			/*password : {
                required : true
            },*/
			companyId : {
				required : true
			}
			/*adduserRoles : {
		        required : true
	       }*/
        }
    });

	$("#btn-add-submit").on('click', function() {
		var action = $("form#form-addTempl").data("action");
		if(action == "add"){
			if (!$("#form-addTempl").valid()) {
				return;
			}else if($("#input-password").val() == "") {
				$("#input-password").parent().parent().addClass("has-error");
				var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
				$("#input-password").parent().append(err_html);
				return;
			}else if($("#add-search-userRoles").val() == "") {
				$("#add-search-userRoles").parent().parent().addClass("has-error");
				var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
				$("#add-search-userRoles").parent().append(err_html);
				return;
			}else {
				window.action.add();
			}
		}else if(action == "edit"){
			//window.action.edit();
			if (!$("#form-addTempl").valid()) {
				return;
			}else{
				window.action.edit();
			}
		}
	});

	$("#btn-search").on('click', function() {
        action.loadPageData();
	});
	$("#input-search-account").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

	});
	$("#input-search-role_id").on('keydown', function(e) {
		if (e.keyCode == 13) {
			action.loadPageData();
		}

	});
	$("#input-search-company_id").on('keydown', function(e) {
		if (e.keyCode == 13) {
			action.loadPageData();
		}

	});

});