$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var admin_id = Util.cookieStorage.getCookie("adminId");

    //获取地址栏传递的参数
    function Request(strName){
        var strHref = "";
        var intPos = strHref.indexOf("?");
        var strRight = strHref.substr(intPos + 1);
        var arrTmp = strRight.split("&");
        for(var i = 0; i < arrTmp.length; i++){
            var arrTemp = arrTmp[i ].split("=");
            if(arrTemp[0].toUpperCase() == strName.toUpperCase()) return arrTemp[1];
        };
        return "";
    };
    var url=location.search;
    var Request = new Object();
    if(url.indexOf("?")!=-1) {
        var str = url.substr(1)　//去掉?号
        strs = str.split("&");
        for(var i=0;i<strs.length;i++){
            Request[strs[i ].split("=")[0]]=unescape(strs[ i].split("=")[1]);
        }
    };
    var father_id = Request["authorityId"];
    var auth_role = Request["authRole"];
	var action = {
		//新增数据
		add : function() {
            var url = ctx + "AuthorityManage/AddAuthority";
            var data = {
                UserName : userName,
                AuthenticCode : token_value,
                UserId : parseInt(admin_id),
                AuthorityName : $("#input-authName").val(),
                AuthRole : parseInt(auth_role),
                AuthInterface : $("#input-authInter").val(),
                AuthDescrible : $("#input-authNotes").val(),
                AuthorityLevel: 2, //1-目录，2-功能
                FatherId : parseInt(father_id) //父权限ID
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
                "FatherId":parseInt(father_id)
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

		//编辑数据
		edit : function() {
			var url = ctx + "AuthorityManage/ModifyAuthority";
			var data = new Object();
            data.UserName = userName;
            data.AuthenticCode = token_value;
			data.AuthenticId = parseInt($("#input-id").val());
			data.AuthorityName = $("#input-authName").val();
			data.AuthorityLevel = 2;//1-目录，2-功能
			data.AuthRole = parseInt(auth_role);//目录所属角色
			data.FatherId = parseInt(father_id);//父权限ID
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
                data.UserId = parseInt(admin_id);
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

    //编辑获取数据数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-authName").val(that.find("td").eq(1).text());
        $("#input-authInter").val(that.find("td").eq(2).text());
        $("#input-authNotes").val(that.find("td").eq(3).text());
        $("#addTempl-modal").modal("show");
    });

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑权限");
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加权限");
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
	$("#input-search-confname").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

	});
});