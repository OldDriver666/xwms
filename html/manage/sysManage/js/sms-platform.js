$(function() {
	var userName = Util.cookieStorage.getCookie("username");
	var token_value = Util.cookieStorage.getCookie("accesstoken");
	var depart_id = Util.cookieStorage.getCookie("departId");
	var role_level = Util.cookieStorage.getCookie("userLevel");
	var admin_id = Util.cookieStorage.getCookie("adminId");
	var nick_name = Util.cookieStorage.getCookie("nickname");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/smsplatfrom/add";
			var data = new Object();
			data.platfrom_name = $("#input-platfrom_name").val();
			data.status = parseInt($("input[name=status]:checked").val());
			data.config = $("#input-config").val();

            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    action.loadPageData();
                }else{
					alert(result.msg);
				}
            });
		},
		//获取所有数据
		loadPageData : function() {
			var search_uname = $("#input-search-uname").val();
            var td_len = $("#table thead tr th").length;//表格字段数量
			var url = ctx + "boss/smsplatfrom/query";
			var data = new Object();
			if(search_uname == ""){
				data.platfrom_name = "";
			}else{
				data.platfrom_name = search_uname;
			}

            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS && result.data != ""){
                    $('#pageContent').find("tr").remove();
                    $("#pageTmpl").tmpl(result.data).appendTo('#pageContent');

                    if($('#pageContent tr').length == 0){
                        $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
					}
                } else if(result.code == ReturnCode.SUCCESS && result.data.length == 0){
					$('#pageContent').find("tr").remove();
					alert("记录不存在");
				} else {
					alert(result.msg);
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

		},
		//编辑数据
		edit : function() {
			var url = ctx + "boss/smsplatfrom/update";
			var data = new Object();
			data.smsplatfrom_id = parseInt($("#input-smsplatfrom_id").val());
			data.platfrom_name = $("#input-platfrom_name-txt").val();
			data.status = parseInt($("input[name=status]:checked").val());
			data.config = $("#input-config").val();

			Util.ajaxLoadData(url,data,"POST",true,function(result) {
				if (result.code == ReturnCode.SUCCESS) {
			 		$("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
				}else{
					alert(result.msg);
				}
			});
		},
		//删除数据
		deleteItem : function(id) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "boss/smsplatfrom/del";
				var data = new Object();
                data.smsplatfrom_id = id;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
                        action.loadPageData();
					}else{
						alert(result.msg);
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
		var check_status = $.trim(that.find("td").eq(3).text());
		var status_val = null;
		if(check_status === "使用"){
			status_val = 1;
		}else if(check_status === "弃用"){
			status_val = 0;
		}
        $("#input-smsplatfrom_id").val(that.find("td").eq(0).text());
        $("#input-platfrom_name-txt").val(that.find("td").eq(1).text());
        $("#input-config").val(that.find("td").eq(2).text());
		$("input[name=status]").filter("[value=" + status_val + "]").prop('checked', true);
        $("#addTempl-modal").modal("show");
    });

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑菜单信息");
			$("#input-platfrom_name-wrap").hide();
			$("#input-platfrom_name-txt-wrap").show();
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加菜单信息");
			$("#input-platfrom_name-wrap").show();
			$("#input-platfrom_name-txt-wrap").hide();
			$form.data("action", "add");
			$form[0].reset();
		}
	});

	//验证表单
    $("#form-addTempl").validate({
        rules : {
			platfrom_name : {
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
	});

	$("#btn-search").on('click', function() {
        action.loadPageData();
	});
	$("#input-search-uname").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

	});
});