$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
	var admin_id = Util.cookieStorage.getCookie("adminId");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/deviceversion/add";
            var data = new Object();
			data.depart_id = parseInt($("#input-depart_id  option:selected").val());
			data.dev_type = parseInt($('#input-devType option:selected').val());
			data.dev_version = $("#input-dev_version").val();
			data.version_info = $("#input-version_info").val();
			data.update_url = $("#input-update_url").val();

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
            var search_depart_id = parseInt($('#input-search-depart_id option:selected').val());
			var search_dev_type = parseInt($('#input-search-client_type option:selected').val());
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
					$('#pageContent').find("tr").remove();
					alert(result.msg);
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

		},
		//获取设备类型列表数据
		loadDevTypeData : function() {
			var allDevTypeArray = JSON.parse(Util.cookieStorage.getCookie("allDevTypeArray"));
			$("#pageDevType").tmpl(allDevTypeArray).appendTo('#input-search-client_type');
			$("#pageDevType").tmpl(allDevTypeArray).appendTo('#input-devType');
		},
		//获取全部公司团体数据
		loadCompanyInfoData: function(){
			var allCompanyArray = JSON.parse(Util.cookieStorage.getCookie("allCompanyArray"));
			$("#pageCompanyInfo").tmpl(allCompanyArray).appendTo('#input-search-depart_id');
			$("#pageCompanyInfo").tmpl(allCompanyArray).appendTo('#input-depart_id');
		},
		//编辑数据
		edit : function() {
			var url = ctx + "boss/deviceversion/update";
			var data = new Object();
			data.version_id = parseInt($("#input-version_id").val());
			data.depart_id = parseInt($("#input-depart_id  option:selected").val());
			data.dev_type = parseInt($("#input-devTypeNo").val());
			data.dev_version = $("#input-dev_version").val();
			data.status = parseInt($("#input-status").val());
			data.version_info = $("#input-version_info").val();
			data.update_url = $("#input-update_url").val();

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
				var url = ctx + "boss/deviceversion/del";
				var data = new Object();
                data.version_id = id;
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
	action.loadDevTypeData();
	action.loadCompanyInfoData();
	//action.loadPageData();

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑设备新版本");
			$("#input-depart_id-wrap").hide();
			$("#input-devType-wrap").hide();
			$("#input-devTypeNo-wrap").hide();
			$("#input-devType-txt-wrap").show();
			$("#input-status-txt").show();
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加设备新版本");
			$("#input-depart_id-wrap").show();
			$("#input-devType-wrap").show();
			$("#input-devTypeNo-wrap").hide();
			$("#input-devType-txt-wrap").hide();
			$("#input-status-txt").hide();
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
		var depart_id_val = that.find("td").eq(1).val();

		$("#input-version_id").val(that.find("td").eq(0).text());
		$("#input-depart_id option[value='"+depart_id_val+"']").attr("selected","selected");
		$("#input-devType-txt").val(that.find("td").eq(2).text());
		$("#input-devTypeNo").val(that.find("td").eq(7).text());
		$("#input-dev_version").val(that.find("td").eq(3).text());
		$("#input-status").val(that.find("td").eq(4).text());
		$("#input-version_info").val(that.find("td").eq(5).text());
		$("#input-update_url").val(that.find("td").eq(6).text());

        $("#addTempl-modal").modal("show");
    });

	$("#input-search-depart_id").change(function () {
		if ($(this).val() != "") {
			$(this).parent().removeClass("has-error");
			$(this).next().remove();
		}
	});

	//验证表单
    $("#form-addTempl").validate({
        rules : {
			depart_id : {
                required : true
            },
			devType : {
                required : true
            },
			dev_version : {
				required : true
			},
			update_url : {
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
		if($("#input-search-depart_id").val() == "") {
			$("#input-search-depart_id").parent().addClass("has-error");
			var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
			$("#input-search-depart_id").append(err_html);
			return;
		}
		action.loadPageData();
	});
	$("#input-search-depart_id").on('keydown', function(e) {
		if($("#input-search-depart_id").val() == "") {
			$("#input-search-depart_id").parent().addClass("has-error");
			var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
			$("#input-search-depart_id").append(err_html);
			return;
		}
		action.loadPageData();

	});

});