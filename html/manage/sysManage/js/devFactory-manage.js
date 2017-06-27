$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/accountmanage/add";
            var data = new Object();
			data.description = $("#input-description").val();
			data.status = parseInt($("input[name=status]:checked").val());
			data.depart_id = parseInt($('#input-depart_id option:selected').val());
			data.begin_account = $("#input-begin_account").val();
			data.end_account = $("#input-end_account").val();

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
            var search_id = parseInt($('#input-search-name option:selected').val());
            var td_len = $("#table thead tr th").length;//表格字段数量

            var url = ctx + "boss/accountmanage/query";
            var data = new Object();
            data.depart_id = search_id;

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
		//获取全部公司团体数据
		loadCompanyInfoData: function(){
			var allCompanyArray = JSON.parse(Util.cookieStorage.getCookie("allCompanyArray"));
			$("#pageCompanyInfo").tmpl(allCompanyArray).appendTo('#input-search-name ');
			$("#pageCompanyInfo").tmpl(allCompanyArray).appendTo('#input-depart_id ');
		},
		//编辑数据
		edit : function() {
			var url = ctx + "boss/accountmanage/update";
			var data = new Object();
            data.id = parseInt($("#input-id").val());
            data.description = $("#input-description").val();
			data.status = parseInt($("input[name=status]:checked").val());
            data.depart_id = parseInt($('#input-depart_idNo').val());
			data.begin_account = $("#input-begin_account").val();
			data.end_account = $("#input-end_account").val();

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
				var url = ctx + "boss/accountmanage/del";
				var data = new Object();
                data.id = id;
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
	action.loadCompanyInfoData();
	action.loadPageData();

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑公司团体信息");
			$("#input-depart_id-wrap").hide();
			$("#input-depart_id-txt-wrap").show();
			$("#input-depart_idNo-wrap").hide();
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加公司团体信息");
			$("#input-depart_id-wrap").show();
			$("#input-depart_id-txt-wrap").hide();
			$("#input-depart_idNo-wrap").hide();
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
		var check_status = $.trim(that.find("td").eq(2).text());
		var status_val = null;
		if(check_status === "未激活"){
			status_val = 0;
		}else if(check_status === "激活"){
			status_val = 1;
		}

        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-description").val(that.find("td").eq(1).text());
		$("input[name=status]").filter("[value=" + status_val + "]").prop('checked', true);
		$("#input-depart_id-txt").val(that.find("td").eq(4).text());
		$("#input-depart_idNo").val(that.find("td").eq(7).text());

        $("#input-begin_account").val(that.find("td").eq(5).text());
		$("#input-end_account").val(that.find("td").eq(6).text());
        $("#addTempl-modal").modal("show");
    });

	$("#input-name").change(function(){
		if($(this).val() != ""){
			$(this).parent().parent().removeClass("has-error");
			$(this).next().remove();
		}
	});
	//验证表单
    $("#form-addTempl").validate({
        rules : {
			name : {
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

	$("#input-search-name").on('keydown', function(e) {
		if (e.keyCode == 13) {
			action.loadPageData();
		}

	});
});