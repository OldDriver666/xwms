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
			if(insertAuth == 0){
				$("#btn-add").hide();
			}
			if(queryAuth == 0){

			}
			if(updateAuth == 0){

			}
		},
		//新增数据
		add : function() {
            var url = ctx + "boss/wiki/add";
            var data = new Object();
			data.type = parseInt($('#feedbackType option:selected').val());
			data.title = $("#input-fqTitle").val();
			data.content = $("#input-fqContent").val();

            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    action.loadPageData();
                }else{
                    toastr.error(result.msg);
				}
            },function(errorMsg) {
                toastr.error(errorMsg);
            });
		},
		//获取所有数据
		loadPageData : function() {
            var searchKey = $("#input-search-key").val();
            var searchType = $("#search-feedbackType").val();

            var url = ctx + "boss/wiki/query";
			var data = new Object();
            if (searchKey !== "" && searchType !== "") {
                data.title = searchKey;
                data.type = searchType;
            } else if (searchKey !== "") {
                data.title = searchKey;
            } else if (searchType !== "") {
                data.type = searchType;
            } else {
                data = {};
            }
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $('#pageContent').empty();
                    $('#pageTmpl').tmpl(result.data).appendTo('#pageContent');
                }else{
                    toastr.error(result.msg);
                }
            },function(errorMsg) {
                toastr.error(errorMsg);
            });
		},
        loadFeedbackType : function () {
            var data = [{'id': 1, 'typeName': '入门指南'}, {'id': 2, 'typeName': '常见问题'}, {'id': 3, 'typeName': '使用技巧'}, {'id': 4, 'typeName': '高级版相关'}, {'id': 5, 'typeName': '账号相关'}, {'id': 6, 'typeName': '关于我们'}]
            $('#pageType').tmpl(data).appendTo('#feedbackType');
            $('#pageType2').tmpl(data).appendTo('#search-feedbackType');
		},
		//编辑数据
		edit : function() {
			var url = ctx + "boss/wiki/update";
			var data = new Object();
			data.id = parseInt($("#input-fqId").val());
            data.type = parseInt($('#feedbackType option:selected').val());
            data.title = $("#input-fqTitle").val();
            data.content = $("#input-fqContent").val();

			Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
				if (result.code == ReturnCode.SUCCESS) {
			 		$("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
				}else{
                    toastr.error(result.msg);
				}
			},function(errorMsg) {
                toastr.error(errorMsg);
            });
		},
		//删除数据
        deleteItem : function(id) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "boss/wiki/del";
				var data = new Object();
                data.id = id;
				Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
                        action.loadPageData();
					}else{
                        toastr.error(result.msg);
					}
				},function(errorMsg) {
                    toastr.error(errorMsg);
                });
			}
		}
	};
	window.action = action;
	action.loadPageData();
	action.loadFeedbackType();

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑常见问题");
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加常见问题");
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();

		$("#input-fqId").val(that.find("td").eq(0).text());
        $("#input-fqTitle").val(that.find("td").eq(2).text());
        $("#input-fqContent").val(that.find("td").eq(3).find('span').text());

        $("#addTempl-modal").modal("show");
    });

	//验证表单
    $("#form-addTempl").validate({
        rules : {
            feedbackType : {
                required : true
            },
            fqTitle : {
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
	$("#input-search-account").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

	});
	$("#input-search-key").on('keydown', function(e) {
		if (e.keyCode == 13) {
			action.loadPageData();
		}

	});

    $("#pageContent").on("click",".check-content-btn",function(){
        var that = $(this).parent().parent();
        $("#modal_fqTitle").html(that.find("td").eq(2).text());
        $("#modal_check_content_wrap").html(that.find("td").eq(3).find('span').text());
        $("#contentCheckTempl-modal").modal("show");
    });

});