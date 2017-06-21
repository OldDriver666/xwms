$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
	var admin_id = Util.cookieStorage.getCookie("adminId");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/suggest/add";
            var data = new Object();
			data.user_id = parseInt($("#input-user_id").val());
			data.uname = $("#input-uname").val();
			data.suggestion = $("#input-suggestion").val();
			data.contact = $("#input-contact").val();

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
            var search_uname = $("#input-search-uname").val();
            var td_len = $("#table thead tr th").length;//表格字段数量

            var url = ctx + "boss/suggest/query";
            var data = new Object();
			if(search_uname == ""){
				data.uname = null;
			}else{
				data.uname = search_uname;
			}


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
		//编辑数据
		edit : function() {
			var url = ctx + "boss/suggest/update";
			var data = new Object();
			data.suggest_id = parseInt($("#input-suggest_id").val());
			data.user_id = parseInt($("#input-user_id").val());
			data.uname = $("#input-uname").val();
			data.status = parseInt($("#input-status").val());
			data.suggestion = $("#input-suggestion").val();
			data.contact = $("#input-contact").val();

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
				var url = ctx + "boss/suggest/del";
				var data = new Object();
                data.suggest_id = id;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
						$("#input-search-uname").val("");
                        action.loadPageData();
					}
				});
			}
		}
	};
	window.action = action;
	//action.loadPageData();

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑用户意见");
			$("#input-status-txt").show();
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加用户意见");
			$("#input-status-txt").hide();
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();

		$("#input-suggest_id").val(that.find("td").eq(0).text());
        $("#input-user_id").val(that.find("td").eq(1).text());
        $("#input-uname").val(that.find("td").eq(2).text());
		$("#input-status").val(that.find("td").eq(3).text());
		$("#input-suggestion").val(that.find("td").eq(4).text());
		$("#input-contact").val(that.find("td").eq(5).text());

        $("#addTempl-modal").modal("show");
    });

	//验证表单
    $("#form-addTempl").validate({
        rules : {
            client_type : {
                required : true
            },
            client_name : {
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
	$("#input-search-account").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

	});
	$("#input-search-uname").on('keydown', function(e) {
		if (e.keyCode == 13) {
			action.loadPageData();
		}

	});

});