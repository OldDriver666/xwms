$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/systemconf/addsystemconf";
            var data = new Object();
            data.type = $("#input-confType").val();
            data.name = $("#input-confName").val();
            data.value = $("#input-confValue").val();
            data.action = $("#input-confAction").val();
            data.status = $("input[name=status]:checked").val();
            data.parent_id = $("#input-confParent_id").val();

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
            var conftype = $("#input-search-type").val();
            var confname = $("#input-search-name").val();
            var td_len = $("#table thead tr th").length;//表格字段数量

            var url = ctx + "boss/systemconf/querysystemconf";
            var data = new Object();
            data.type = conftype;
            data.name = confname;

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
			var url = ctx + "boss/systemconf/updatesystemconf";
			var data = new Object();
            data.config_id = parseInt($("#input-id").val());
            data.type = $("#input-confType").val();
            data.name = $("#input-confName").val();
            data.value = $("#input-confValue").val();
            data.action = $("#input-confAction").val();
            data.status = $("input[name=status]:checked").val();
            data.parent_id = $("#input-confParent_id").val();

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
				var url = ctx + "boss/systemconf/delsystemconf";
				var data = new Object();
                data.config_id = id;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
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
        var check_status = $.trim(that.find("td").eq(6).text());
        var status_val = null;
        if(check_status === "启用"){
            status_val = true;
        }else if(check_status === "弃用"){
            status_val = false;
        }

        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-confType").val(that.find("td").eq(1).text());
        $("#input-confName").val(that.find("td").eq(2).text());
        $("#input-confValue").val(that.find("td").eq(3).text());
        $("#input-confAction").val(that.find("td").eq(4).text());
        $("#input-confParent_id").val(that.find("td").eq(5).text());
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
            }
        }
    });

	$("#btn-add-submit").on('click', function() {
        var action = $("form#form-addTempl").data("action");
        if(action == "add"){
            if (!$("#form-addTempl").valid()) {
                return;
            }else {
                window.action.add();
            }
        }else if(action == "edit"){
            window.action.edit();
        }

		/*if (!$("#form-addTempl").valid()) {
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
		}*/
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