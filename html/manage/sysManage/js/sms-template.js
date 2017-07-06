$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
	var admin_id = Util.cookieStorage.getCookie("adminId");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/sms/add";
            var data = new Object();
			data.platfrom_id = parseInt($("#input-platfrom_name  option:selected").val());
			data.action = $("#input-action").val();
			data.action_name = $("#input-action_name").val();
			data.template_name = $("#input-template_name").val();

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

            var url = ctx + "boss/sms/query";
            var data = new Object();
			if(search_uname == ""){
				data.action = null;
			}else{
				data.action = search_uname;
			}


            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS && result.data != ""){
                    $('#pageContent').find("tr").remove();
					action.loadSMSData(JSON.stringify(result.data));
                    /*if($('#pageContent tr').length == 0){
                        $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
					}*/
                } else {
					alert(result.msg);
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

		},
		//获取所有短信平台数据
		loadSMSData : function(paramArray) {
			var url = ctx + "boss/smsplatfrom/query";
			var data = new Object();
			data.platfrom_name = "";
			Util.ajaxLoadData(url,data,"POST",true,function(result) {
				if(result.code == ReturnCode.SUCCESS && result.data != ""){
					$("#pageSMSTmpl").tmpl(result.data).appendTo('#input-platfrom_name');
					var platformArray = result.data;
					var templateArray = JSON.parse(paramArray);
					for(var i=0; i< templateArray.length; i++){
						for(var j=0; j< platformArray.length; j++){
							if (templateArray[i].platfrom_id == platformArray[j].smsplatfrom_id){
								var t = platformArray[j].platfrom_name;
								templateArray[i].platfrom_name = t;
							}
						}

					}
					$("#pageTmpl").tmpl(templateArray).appendTo('#pageContent');

				} else {
					alert(result.msg);
				}
			},function() {
				alert("服务器开个小差，请稍后重试！")
			});
		},
		//编辑数据
		edit : function() {
			var url = ctx + "boss/sms/update";
			var data = new Object();
			data.id = parseInt($("#input-id").val());
			data.platfrom_id = parseInt($("#input-platfrom_id-txt").val());
			data.action = $("#input-action").val();
			data.action_name = $("#input-action_name").val();
			data.template_name = $("#input-template_name").val();

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
		deleteConfig : function(id) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "boss/sms/del";
				var data = new Object();
                data.id = id;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
						$("#input-search-uname").val("");
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

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑短信模板");
			$("#input-platfrom_id-txt-wrap").hide();
			$("#input-platfrom_name-wrap").hide();
			$("#input-platfrom_name-txt-wrap").show();
			$("#input-action-wrap").show();
			$("#input-action-txt-wrap").hide();
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加短信模板");
			$("#input-platfrom_id-txt-wrap").hide();
			$("#input-platfrom_name-wrap").show();
			$("#input-platfrom_name-txt-wrap").hide();
			$("#input-action-wrap").show();
			$("#input-action-txt-wrap").hide();
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
		$("#input-id").val(that.find("td").eq(0).text());
		$("#input-action").val(that.find("td").eq(1).text());
		$("#input-action_name").val(that.find("td").eq(2).text());
		$("#input-platfrom_id-txt").val(that.find("td").eq(7).text());
		$("#input-template_name").val(that.find("td").eq(4).text());
		$("#input-platfrom_name-txt").val(that.find("td").eq(3).text());


        $("#addTempl-modal").modal("show");
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