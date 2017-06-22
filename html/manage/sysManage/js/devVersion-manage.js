$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
	var admin_id = Util.cookieStorage.getCookie("adminId");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/deviceversion/add";
            var data = new Object();
			data.depart_id = parseInt($("#input-depart_id").val());
			data.dev_type = parseInt($("#input-dev_type").val());
			data.dev_version = $("#input-dev_version").val();
			data.version_info = $("#input-version_info").val();
			data.update_url = $("#input-update_url").val();

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
            var search_depart_id = parseInt($("#input-search-depart_id").val());
			var search_dev_type = parseInt($("#input-search-dev_type").val());
            var td_len = $("#table thead tr th").length;//表格字段数量

            var url = ctx + "boss/deviceversion/query";
            var data = new Object();
			data.depart_id = search_depart_id;
			data.dev_type = search_dev_type;

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
			var url = ctx + "boss/deviceversion/update";
			var data = new Object();
			data.version_id = parseInt($("#input-version_id").val());
			data.depart_id = parseInt($("#input-depart_id").val());
			data.dev_type = parseInt($("#input-dev_type-wrap").val());
			data.dev_version = $("#input-dev_version").val();
			data.status = parseInt($("#input-status").val());
			data.version_info = $("#input-version_info").val();
			data.update_url = $("#input-update_url").val();

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
				var url = ctx + "boss/deviceversion/del";
				var data = new Object();
                data.version_id = id;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
						/*$("#input-search-depart_id").val("");
						$("#input-search-dev_type").val("");*/
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
			$("h4#addTempl-modal-label").text("编辑设备新版本");
			$("#input-dev_type-txt").hide();
			$("#input-dev_type-txt-wrap").show();
			$("#input-status-txt").show();
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加设备新版本");
			$("#input-dev_type-txt").show();
			$("#input-dev_type-txt-wrap").hide();
			$("#input-status-txt").hide();
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();

		$("#input-version_id").val(that.find("td").eq(0).text());
        $("#input-depart_id").val(that.find("td").eq(1).text());
        $("#input-dev_type-wrap").val(that.find("td").eq(2).text());
		$("#input-dev_version").val(that.find("td").eq(3).text());
		$("#input-status").val(that.find("td").eq(4).text());
		$("#input-version_info").val(that.find("td").eq(5).text());
		$("#input-update_url").val(that.find("td").eq(6).text());

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
	$("#input-search-depart_id").on('keydown', function(e) {
		if (e.keyCode == 13) {
			action.loadPageData();
		}

	});
	$("#input-search-dev_type").on('keydown', function(e) {
		if (e.keyCode == 13) {
			action.loadPageData();
		}

	});

});