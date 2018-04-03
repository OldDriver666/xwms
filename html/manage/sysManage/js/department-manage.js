$(function() {
	var userName = Util.cookieStorage.getCookie("username");
	var token_value = Util.cookieStorage.getCookie("accesstoken");
	var depart_id = Util.cookieStorage.getCookie("departId");
	var company_id = Util.cookieStorage.getCookie("companyId");
	var role_level = Util.cookieStorage.getCookie("userLevel");
	var admin_id = Util.cookieStorage.getCookie("adminId");
	var nick_name = Util.cookieStorage.getCookie("nickname");

	var url=location.search;
	var Request = new Object();
	if(url.indexOf("?")!=-1) {
		var str = url.substr(1)　//去掉?号
		strs = str.split("&");
		for(var i=0;i<strs.length;i++){
			Request[strs[i ].split("=")[0]]=unescape(strs[ i].split("=")[1]);
		}
	};
	var moduleId = parseInt(Request["moduleId"]);
	var insertAuth = parseInt(Request["insertAuth"]);
	var queryAuth = parseInt(Request["queryAuth"]);
	var updateAuth = parseInt(Request["updateAuth"]);

	var action = {
		init: function(){
			if(0 == insertAuth){
				$("#btn-add").hide();
			}
			if(0 == queryAuth){

			}
			if(0 == updateAuth){

			}
		},
		//新增数据
		add : function() {
            var url = ctx + "boss/depart/insert";
            //var url = "http://192.168.2.48:8080/managesvr/boss/depart/insert";
            var data = new Object();

			data.depart_name = $("#input-name").val();
			data.parent_id = parseInt($("#input-parentId").val());
			data.company_id = parseInt(company_id);
			data.creator_id = parseInt(admin_id);

            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    action.loadPageData();
                }else{
                    toastr.error(result.msg);
				}
            });
		},
		//获取所有数据
		loadPageData : function() {
            var search_name = $("#input-search-name").val();
            var td_len = $("#table thead tr th").length;//表格字段数量

            var url = ctx + "boss/depart/query";
            var data = new Object();
            data.depart_name = search_name;
            data.creator_id = parseInt(admin_id);
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    $('#pageContent').find("tr").remove();
                    $("#pageTmpl").tmpl(result.data).appendTo('#pageContent');

                    if($('#pageContent tr').length == 0){
                        $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
					}
					if(updateAuth == 0){
						$(".table-update").hide();
						$(".table-manage").hide();
					}
                }else {
                    toastr.error(result.msg);
				}
            },function(errorMsg) {
                alert(errorMsg);
            });

		},
		//编辑数据
		edit : function() {
			var url = ctx + "boss/depart/update";
			var data = new Object();
            data.depart_id = parseInt($("#input-id").val());
            data.depart_name = $("#input-name").val();
			data.status = parseInt($("input[name=status]:checked").val());

			Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
				if (result.code == ReturnCode.SUCCESS) {
			 		$("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
				}else{
                    toastr.error(result.msg);
				}
			},function(errorMsg) {
                alert(errorMsg);
            });
		},
		//删除数据
		deleteConfig : function(id, name) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "boss/depart/delete";
				var data = new Object();
                data.depart_id = id;
				Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
						$("#input-search-name").val("");
                        action.loadPageData();
					}else{
                        toastr.error(result.msg);
					}
				},function(errorMsg) {
                    alert(errorMsg);
                });
			}
		}
	};
	window.action = action;
	action.init();
	action.loadPageData();

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑部门信息");
			$("#parentDepartment-ipt").hide();
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加部门信息");
            $("#parentDepartment-ipt").show();
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-name").val(that.find("td").eq(1).text());
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
			if (!$("#form-addTempl").valid()) {
				return;
			}else {
			    window.action.edit();
			}
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