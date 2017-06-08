$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "BackStageManage/AddConfInfo";
            var data = {
                UserName : userName,
                AuthenticCode : token_value,
                BackStageConfName : $("#input-confName").val(),
                BackStageConfType : $("#input-confType").val(),
                BackStageConfValue : $("#input-confValue").val(),
                BackStageConfAction : $("#input-confAction").val(),
                BackStageConfStatus : $("input[name=status]:checked").val()
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
            var confname = $("#input-search-txt").val();
            var td_len = $("#table thead tr th").length;//表格字段数量

            var url = ctx + "BackStageManage/QueryConfInfo";
            var data = {
                BackStageConfName : confname,
                "UserName":userName,
                "AuthenticCode": token_value
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $('#pageContent').find("tr").remove();
                    $("#pageTmpl").tmpl(result.BackStageConfInfo).appendTo('#pageContent');

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
			var url = ctx + "BackStageManage/ModifyConfInfo";
			var data = new Object();
            data.UserName = userName;
            data.AuthenticCode = token_value;
			data.BackStageConfId = $("#input-id").val();
			data.BackStageConfName = $("#input-confName").val();
			data.BackStageConfType = $("#input-confType").val();
			data.BackStageConfValue = $("#input-confValue").val();
			data.BackStageConfAction = $("#input-confAction").val();
			data.BackStageConfStatus = $("input[name=status]:checked").val();
			Util.ajaxLoadData(url,data,"POST",true,function(result) {
				if (result.Status == ReturnCode.SUCCESS) {
			 		$("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
				}
			});
		},
		//删除数据
		deleteConfig : function(id, name) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "BackStageManage/DelConfInfo";
				var data = new Object();
                data.UserName = userName;
                data.AuthenticCode = token_value;
                data.BackStageId = id;
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
			$("h4#addTempl-modal-label").text("编辑系统配置");
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加系统配置");
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        var check_status = $.trim(that.find("td").eq(5).text());
        var status_val = null;
        if(check_status === "启用"){
            status_val = 0;
        }else if(check_status === "禁用"){
            status_val = 1;
        }

        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-confName").val(that.find("td").eq(1).text());
        $("#input-confType").val(that.find("td").eq(2).text());
        $("#input-confValue").val(that.find("td").eq(3).text());
        $("#input-confAction").val(that.find("td").eq(4).text());
        $("input[name=status]").filter("[value=" + status_val + "]").prop('checked', true);
        $("#addTempl-modal").modal("show");
    });

	//验证表单
    $("#form-addTempl").validate({
        rules : {
            confname : {
                required : true
            },
            conftype : {
                required : true
            },
            confvalue : {
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