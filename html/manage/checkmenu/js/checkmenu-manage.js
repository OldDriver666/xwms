$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var admin_id = Util.cookieStorage.getCookie("adminId");
    var company_id = Util.cookieStorage.getCookie("companyId");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "boss/depart/insert";
            var moduleId= 0;
            var data = {
                company_id: parseInt(company_id),
                depart_name: $("#input-menuName").val(),
                parent_id: parseInt($("#input-parentMenuName").val())
            };
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if (result.code == 0) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    action.loadPageData();
                }
            });

		},
		//获取所有数据
		loadPageData : function() {
            var td_len = $("#table thead tr th").length;//表格字段数量
            var url = ctx + "boss/depart/query";
            var moduleId= 0;
            var data = {
                "company_id":parseInt(company_id)
            };
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == 0){
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
			var url = ctx + "boss/depart/update";
            var moduleId= 0;
			var data = new Object();
            data.company_id = parseInt(company_id);
            data.depart_id = parseInt($("#input-id").val());
			data.depart_name = $("#input-menuName").val();
			data.parent_id = parseInt($("#input-parentMenuName").val());

			Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
				if (result.code == 0) {
			 		$("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
				}else{
					alert("编辑失败！请正确填写内容");
				}
			});
		},
		//删除数据
		deleteFunc : function(id, name) {
			if (confirm('删除后不可恢复，确定删除 " ' + name + ' " 权限？')) {
				var url = ctx + "boss/depart/update";
                var moduleId= 0;
				var data = new Object();
                data.company_id = parseInt(company_id);
                data.depart_id = id;
                data.status = 0;
				Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
					if (result.code == 0) {
                        toastr.success("删除成功!");
                        action.loadPageData();
					}
				});
			}
		}

	};
	window.action = action;
	action.loadPageData();

    //跳转页面
    $("#pageContent").on("click",".toPage-btn",function(){
        var that = $(this).parent().parent();
        var authorityId = that.find("td").eq(0).text();
        var authRole = that.find(".auth-role").text();
        url = "auth-manage-handle.html?authorityId="+escape(authorityId);
        url += "&authRole=" + escape(authRole);
        location.href=url;
    });

    //编辑获取数据数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-menuName").val(that.find("td").eq(2).text());
        $("#input-parentMenuName").val(that.find("td").eq(1).text());
        $("#addTempl-modal").modal("show");
    });

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑目录权限");
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加目录权限");
			$form.data("action", "add");
			$form[0].reset();
		}
	});

	//验证表单
    $("#form-addTempl").validate({
        rules : {
            authName : {
                required : true
            },
            authRole : {
            	required : true
            },
            authInter : {
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