$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "SmsPlatfrom/AddSmsInfo";
            var data = {
                UserName : userName,
                AuthenticCode : token_value,
                SmsPlatFromName : $("#input-smsConfName").val(),
                SmsPlatFromConf : JSON.parse($("#input-smsConfInfo").val()),
                SmsPlatFromStatus : parseInt($("input[name=status]:checked").val())
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
            var url = ctx + "SmsPlatfrom/GetSmsInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $('#pageContent').find("tr").remove();
                    $("#pageTmpl").tmpl(result.SmsPlatFromInfo).appendTo('#pageContent');

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
		//编辑获取数据数据
		loadEditData : function(id) {
			var url = ctx + "SmsPlatfrom/GetSmsInfo";
			var data = {
                AuthenticCode : token_value,
                UserName : userName
			};
			Util.ajaxLoadData(url,data,"POST",true,function(result) {
				if (result.Status == ReturnCode.SUCCESS) {
					var con_data = result.SmsPlatFromInfo;
                    $.each(con_data,function(index,item){
                        if(id == item.SmsPlatFromId){
                            $("#input-id").val(item.SmsPlatFromId);
                            $("#input-smsConfName").val(item.SmsPlatFromName);
                            $("#input-smsConfInfo").val(JSON.stringify(item.SmsPlatFromConf));
                            $("input[name=status]").filter("[value=" + item.SmsPlatFromStatus + "]").prop('checked', true);
                            $("#addTempl-modal").modal("show");
                        }
                    })
				}
			});

		},
		//编辑数据
		edit : function() {
			var url = ctx + "SmsPlatfrom/ModifySmsInfo";
			var data = new Object();
            data.UserName = userName;
            data.AuthenticCode = token_value;
			data.SmsPlatFromId = parseInt($("#input-id").val());
			data.SmsPlatFromName = $("#input-smsConfName").val();
			data.SmsPlatFromConf = JSON.parse($("#input-smsConfInfo").val());	
			data.SmsPlatFromStatus = parseInt($("input[name=status]:checked").val());
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
		deleteItem : function(id, name) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "SmsPlatfrom/DelSmsInfo";
				var data = new Object();
                data.UserName = userName;
                data.AuthenticCode = token_value;
                data.SmsPlatFromId = id;
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

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑短信配置");
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加短信配置");
			$form.data("action", "add");
			$form[0].reset();
		}
	});

	//验证表单
    $("#form-addTempl").validate({
        rules : {
            smsConfName : {
                required : true
            },
            smsConfInfo : {
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