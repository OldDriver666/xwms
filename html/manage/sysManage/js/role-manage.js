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
		//获取所有数据
		loadPageData : function() {
            var search_name = $("#input-search-name").val();
            var url = ctx + "boss/role/queryRoleByPage";
            var data = {
                "page_no": 1,
                "page_size": 10,
                "param": {
                    "role_name": search_name
                }
            };
            var opt = {
                "targetContentId" : "pageContent",
                "url" : url,
                "forAuth2" : true,
                "moduleId" : moduleId,
                "rowTemplateId" : "pageTmpl",
                "contextUrl" : ctx,
                "pageBtnsContentId" : "pagination",
                "tmplEvents" : {
                    setTime : function(time) {
                        if (time) {
                            var times = new Date(time);
                            time = times.format('yyyy-MM-dd hh:mm:ss');
                        }
                        return time;
                    }
                },
                "resultFilter" : function(result) {
                    return result.data.result;
                },
                "param" : data
            };
            this.page = new Util.Page(opt);
		},
        loadDepartData : function() {
            var url = ctx + "boss/depart/query";
            var data = new Object();
            data.depart_name = '';
            data.creator_id = parseInt(admin_id);
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    $("#pageUserRoles").tmpl(result.data).appendTo('#role-departId');
                    localStorage.setItem("departArray",JSON.stringify(result.data));
                }else {
                }
            },function(errorMsg) {
                alert(errorMsg);
            });
        },
        loadMenu1 : function(){
            var url = ctx + "boss/role/queryAuth";
            var moduleId= 0;
            var data = {
                "role_id":parseInt(role_level),
                "company_id":parseInt(company_id),
                "creator_id": parseInt(admin_id)
            };
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    var rdata = result.data;
                    var data = rdata.sort(action.sortBy('priority',false))
                    var Len = data.length;
                    var parent_data = new Array;

                    for(var i=0; i<Len; i++){
                        var mid = data[i].module_id	;
                        var pid = data[i].parent_id;

                        if(0 == pid){
                            var children_menu = new Array;
                            for(var j=0; j<Len; j++){
                                if(mid == data[j].parent_id){
                                    var str = data[j];
                                    children_menu.push(str);
                                }
                            }
                            data[i].children = children_menu;
                            parent_data.push(data[i]);
                        }
                    }
                    $("#menuContent").empty();
                    $("#pageMenu").tmpl(parent_data).appendTo('#menuContent');
                } else {
                }
            },function(errorMsg) {
                alert(errorMsg)
            });
        },
        sortBy: function(attr,rev){
            //第二个参数没有传递 默认升序排列
            if(rev ==  undefined){
                rev = 1;
            }else{
                rev = (rev) ? 1 : -1;
            }
            return function(a,b){
                a = a[attr];
                b = b[attr];
                if(a < b){
                    return rev * -1;
                }
                if(a > b){
                    return rev * 1;
                }
                return 0;
            }
        },
		//新增用户角色
		add : function() {
        	var p_len = $("#menuContent .tree_node_parent_checkbox").length;
            var c_len = $("#menuContent .tree_node_child_checkbox").length;

			var auth_data = [];
			for(var i = 0; i<p_len; i++){
				var curr_moduleId = $("#menuContent .tree_node_parent_checkbox").eq(i).val();
                var status_val1
                if($("#menuContent .tree_node_parent_checkbox").eq(i).is(':checked')){
                    status_val1 = 1
                }else{
                    status_val1 = 0
                }
                auth_data.push({module_id:parseInt(curr_moduleId),status:status_val1,insert_auth:0,update_auth:0,query_auth:0})
			}
            for(var j = 0; j<c_len; j++){
                var curr_moduleId = $("#menuContent .tree_node_child_checkbox").eq(j).val();
                var curr_query_auth = $("#menuContent .tree_node_child_checkbox").eq(j).next().find(".prem_query")
                var curr_insert_auth = $("#menuContent .tree_node_child_checkbox").eq(j).next().find(".prem_add")
                var curr_update_auth = $("#menuContent .tree_node_child_checkbox").eq(j).next().find(".prem_update")

                var status_val2,query_auth_val,insert_auth_val,update_auth_val

                if(curr_query_auth.is(':checked')){
                    query_auth_val = 1
                }else{
                    query_auth_val = 0
				}
				if(curr_insert_auth.is(':checked')){
                    insert_auth_val = 1
				}else{
                    insert_auth_val = 0
				}
                if(curr_update_auth.is(':checked')){
                    update_auth_val = 1
                }else{
                    update_auth_val = 0
				}
				if($("#menuContent .tree_node_child_checkbox").eq(j).is(':checked')){
                    status_val2 = 1
                }else{
                    status_val2 = 0
                }
                auth_data.push({module_id:parseInt(curr_moduleId),status:status_val2,query_auth:query_auth_val,insert_auth:insert_auth_val,update_auth:update_auth_val})
            }

			var url =  ctx + "boss/role/insertRoleAndAuths";
            var data = {
            	"role": {
            		"role_level": parseInt($("#input-authLevel").val()),
					"role_name": $("#input-name").val(),
					"desc": $("#input-description").val(),
					"company_id": parseInt(company_id),
					"depart_id": parseInt($('#role-departId option:selected').val()),
					"creator_id": parseInt(admin_id)
				},
				"auths": auth_data
            };

			Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
				if(result.code == ReturnCode.SUCCESS){
					$("#addTempl2-modal").modal('hide');
					toastr.success("添加成功!");
					action.loadPageData();
				} else {
                    toastr.error(result.msg);
				}
			},function(errorMsg) {
                toastr.error(errorMsg);
            });
		},
		//编辑数据
		edit : function() {
            var p_len = $("#menuContent .tree_node_parent_checkbox").length;
            var c_len = $("#menuContent .tree_node_child_checkbox").length;

            var auth_data = [];
            for(var i = 0; i<p_len; i++){
                var curr_moduleId = $("#menuContent .tree_node_parent_checkbox").eq(i).val();
                var permiss_id = $("#menuContent .tree_node_parent_checkbox").eq(i).attr("data-keyid");
                var status_val1
                if($("#menuContent .tree_node_parent_checkbox").eq(i).is(':checked')){
                    status_val1 = 1
                }else{
                    status_val1 = 0
                }
                auth_data.push({module_id:parseInt(curr_moduleId),status:status_val1,insert_auth:0,update_auth:0,query_auth:0})
            }
            for(var j = 0; j<c_len; j++){
                var curr_moduleId = $("#menuContent .tree_node_child_checkbox").eq(j).val();
                var permiss_id = $("#menuContent .tree_node_child_checkbox").eq(j).attr("data-keyid");
                var curr_query_auth = $("#menuContent .tree_node_child_checkbox").eq(j).next().find(".prem_query")
                var curr_insert_auth = $("#menuContent .tree_node_child_checkbox").eq(j).next().find(".prem_add")
                var curr_update_auth = $("#menuContent .tree_node_child_checkbox").eq(j).next().find(".prem_update")

                var status_val2,query_auth_val,insert_auth_val,update_auth_val

                if(curr_query_auth.is(':checked')){
                    query_auth_val = 1
                }else{
                    query_auth_val = 0
                }
                if(curr_insert_auth.is(':checked')){
                    insert_auth_val = 1
                }else{
                    insert_auth_val = 0
                }
                if(curr_update_auth.is(':checked')){
                    update_auth_val = 1
                }else{
                    update_auth_val = 0
                }
                if($("#menuContent .tree_node_child_checkbox").eq(j).is(':checked')){
                    status_val2 = 1
                }else{
                    status_val2 = 0
                }
                auth_data.push({module_id:parseInt(curr_moduleId),status:status_val2,query_auth:query_auth_val,insert_auth:insert_auth_val,update_auth:update_auth_val})
            }
			var url = ctx + "boss/role/updateRoleAndAuths";
			var data = {
				"role": {
                    "id": parseInt($("#input-id").val()),
                    "role_level": parseInt($("#input-authLevel").val()),
                    "role_name": $("#input-name").val(),
                    "desc": $("#input-description").val(),
                    "company_id": parseInt(company_id),
                    "depart_id": parseInt(depart_id)
				},
                "auths": auth_data
        	}

			Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
				if (result.code == ReturnCode.SUCCESS) {
			 		$("#addTempl2-modal").modal('hide');
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
        deleteItem : function(id, name) {
            if (confirm("删除后不可恢复，确定删除" + name + "？")) {
                var url = ctx + "boss/role/delete";
                var data = new Object();
                data.id = id;
                Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                    if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
                        action.loadPageData();
                    }
                });
            }
        }
	};
	window.action = action;
	action.init();
	action.loadPageData();
    action.loadMenu1();

	$("#addTempl2-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl2");
        if (!e.relatedTarget) {
            $("#role-departId-wrap").hide();
            $("#role-depart-txt-wrap").show();
            $("h4#addTempl2-modal-label").text("编辑用户角色");
            $form.data("action", "edit");
        } else if (e.relatedTarget.id = "btn-add-userRoles") {
            $("#role-departId-wrap").show();
            $("#role-depart-txt-wrap").hide();
            $("h4#addTempl2-modal-label").text("添加用户角色");
            $form.data("action", "add");
            $form[0].reset();
        }
	});

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        $("#menuContent").find("input").removeAttr("checked");
        var that = $(this).parent().parent();
        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-authLevel").val(that.find("td").eq(1).text());
        $("#input-name").val(that.find("td").eq(2).text());
		$("#input-description").val(that.find("td").eq(3).text());
		$("#role-depart-txt").val(that.find("td").eq(5).text());

        $("#addTempl2-modal").modal("show");

        var id = that.find("td").eq(0).text();
        function queryAuthData(id) {
            var url = ctx + "boss/role/queryAuth";
            var data = {
                "role_id": parseInt(id),
                "company_id": parseInt(company_id),
                "include_all": 0
            };
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    var data_auth = result.data
                    var tree_node_parent_length = $("#menuContent .tree_node_parent_checkbox").length
                    for(var j = 0; j<tree_node_parent_length; j++){
                        var v = $("#menuContent .tree_node_parent_checkbox").eq(j).val();
                        for(var i = 0; i<data_auth.length; i++){
                            if(v == data_auth[i].module_id && data_auth[i].status == 1){
                                $("#menuContent input.tree_node_parent_checkbox").eq(j).prop("checked",true);
                            }
                        }
                    }

                    var tree_node_parent_length = $("#menuContent .tree_node_child_checkbox").length
                    for(var k = 0; k<tree_node_parent_length; k++){
                        var w = $("#menuContent .tree_node_child_checkbox").eq(k).val();
                        for(var l = 0; l<data_auth.length; l++){
                            if(w == data_auth[l].module_id && data_auth[l].status == 1){
                                $("#menuContent input.tree_node_child_checkbox").eq(k).prop("checked",true);
                                if(data_auth[l].query_auth == 1){
                                    $("#menuContent input.tree_node_child_checkbox").eq(k).next().find('input.prem_query').eq(0).prop("checked",true);
                                }
                                if(data_auth[l].insert_auth == 1){
                                    $("#menuContent input.tree_node_child_checkbox").eq(k).next().find('input.prem_add').eq(0).prop("checked",true);
                                }
                                if(data_auth[l].update_auth == 1){
                                    $("#menuContent input.tree_node_child_checkbox").eq(k).next().find('input.prem_update').eq(0).prop("checked",true);
                                }
                            }
                        }
                    }

                }else {
                }
            },function(errorMsg) {
                console.log(errorMsg);
            });
        }
        setTimeout(function () {
            queryAuthData(id)
        },200)

    });

	//验证表单
   /* $("#form-addTempl").validate({
        rules : {
			account : {
                required : true
            }
        }
    });*/
	$("#form-addTempl2").validate({
		rules : {
			authLevel : {
				required : true
			},
			name : {
				required : true
			}
		}
	});

	$("#btn-add-submit2").on('click', function() {
		var action = $("form#form-addTempl2").data("action");
		if(action == "add"){
			if (!$("#form-addTempl2").valid()) {
				return;
			}else if(isNaN($("#input-authLevel").val())) {
				$("#input-authLevel").parent().parent().addClass("has-error");
				var err_html = "<label class='error control-label' style='padding-left: 5px;'>请填入数字</label>";
				$("#input-authLevel").parent().append(err_html);
				return;
			}else {
				window.action.add();
			}
		}else if(action == "edit"){
			window.action.edit();
		}
	});

	$("#input-authLevel").change(function () {
		if(!isNaN($(this).val())) {
			$(this).parent().removeClass("has-error");
			$(this).next().remove();
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





//分页
Util.Page = (function() {
    var Page = function(options) {
        var opt = options ? options : {};
        this.targetContentId = opt.targetContentId ? opt.targetContentId : "";// 显示查询到结果的区域
        this.url = opt.url ? opt.url : "";
        this.ctx = opt.ctx ? opt.ctx : "";
        this.pageBtnsContentId = opt.pageBtnsContentId ? opt.pageBtnsContentId
            : "";// 显示分页按钮的区域
        this.rowTemplateId = opt.rowTemplateId ? opt.rowTemplateId : "";// 每一行的模板
        this.tmplBindEvents = opt.tmplBindEvents ? opt.tmplBindEvents : {};
        this.tmplEvents = opt.tmplEvents;
        this.allNumContentId = opt.allNumContentId ? opt.allNumContentId : "";// 显示所有条数的区域
        this.resultFilter = opt.resultFilter ? opt.resultFilter : null;// 过滤要遍历的数据
        this.callback = opt.callback ? opt.callback : null;// 成功加载所有html后的回调
        this.allNumberAreaId = opt.allNumberAreaId ? opt.allNumberAreaId : "";// 每一行的模板
        this.Page = 1;
        this.PageSize = 20;
        this.allPageSize = 0;
        this.showPageNum = 7;// 显示多少个页面数
        this.param = options.param;
        this.filterParam = null;
        this.forAuth2 = typeof (opt.forAuth2) != "undefined" ? opt.forAuth2
            : false;
        this.updateAuth = typeof (opt.updateAuth) != "undefined" ? opt.updateAuth
            : false;
        this.moduleId = typeof (opt.moduleId) != "undefined" ? opt.moduleId
            : false;
        this.loadTBodyData(this.param);
        // this.initPageBtns();
    };
    Page.prototype.setCurrentPage = function() {
        $(_this).siblings(".page_a").removeClass("current");
        $(_this).addClass("current");
        var pageNow = $(_this).text();
        var pageNowInt = parseInt(pageNow);
        if (isNaN(pageNowInt)) {
            return;
        }
        if (pageNowInt == ManagerSet.productManager.pageNow) {// 如果点击的页面跟当前页相等，则返回
            return;
        }
        ManagerSet.productManager.pageNow = pageNowInt;
        ManagerSet.productManager.loadTBodyData();
    };
    Page.prototype.refreshData = function(data) {
        this.pageNow = 1;
        this.loadTBodyData(data);
    };

    Page.prototype.refreshCurrentPageData = function(data) {
        this.loadTBodyData(data);
    };

    Page.prototype.initPageBtns = function(totalCount,allPageSize) {
        var that = this;
        var htmlStr = '';
        var hasDots = false;
        htmlStr = '<span>共'+allPageSize+'页，'+totalCount+'条数据</span>';
        htmlStr += '<a href="javascript:void(0)" title="上一页" class="page_a jsBtnA prev_page">上一页</a><span id="pageBtns">';
        for (var i = 0; i < allPageSize; i++) {
            var pageNow = i + 1;
            var pe = allPageSize - that.pageNow;
            if (that.pageNow >= 5) {
                if (i < 2) {
                    cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA " title="第' + pageNow + '页">' + pageNow + '</a>';
                    htmlStr = htmlStr + cBtnStr;
                }
            }
            if (pe > 3) {
                if (i == that.pageNow - 2 && i > 0) {
                    htmlStr = htmlStr + "<span>...</span>";
                }
                if (i >= (that.pageNow - 2) && i <= (that.pageNow + 2)) {
                    if (pageNow == that.pageNow) {
                        cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA current" title="第' + pageNow + '页">' + pageNow + '</a>';
                    } else {
                        cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA " title="第' + pageNow + '页">' + pageNow + '</a>';
                    }
                    htmlStr = htmlStr + cBtnStr;
                } else {
                    if (i == (that.pageNow + 2)) {
                        htmlStr = htmlStr + "<span>...</span>";
                    }
                }
            } else {
                if (i == (that.pageNow - 2) && i > 0) {
                    htmlStr = htmlStr + "<span>...</span>";
                }
                if (i > (that.pageNow - 3)) {
                    if (pageNow == that.pageNow) {
                        cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA current" title="第' + pageNow + '页">' + pageNow + '</a>';
                    } else {
                        cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA " title="第' + pageNow + '页">' + pageNow + '</a>';
                    }
                    htmlStr = htmlStr + cBtnStr;
                }
            }
        }
        htmlStr += '</span><a href="javascript:void(0)" title="下一页" class="page_a jsBtnA next_page"">下一页</a>';
        htmlStr += '<input type="text"  style="width:30px;"/>';
        htmlStr += '<a href="javascript:void(0)" class="page_a jsBtnA page_a_go" style="margin-left:5px;">Go</a>';
        $("#" + this.pageBtnsContentId).html(htmlStr);

        if (allPageSize == 0) {
            $("#" + this.pageBtnsContentId).hide();
        } else {
            $("#" + this.pageBtnsContentId).show();
        }
        $("#" + this.pageBtnsContentId).find(".current_page").bind("click", function() {// 点击某一页
            $(this).siblings(".page_a").removeClass("current");
            $(this).addClass("current");
            var pageNow = $(this).text();
            var pageNowInt = parseInt(pageNow);
            if (isNaN(pageNowInt)) {
                return;
            }
            if (pageNowInt == that.pageNow) {// 如果点击的页面跟当前页相等，则返回
                return;
            }
            that.pageNow = pageNowInt;
            that.loadTBodyData();
        });

        $("#" + this.pageBtnsContentId).find(".prev_page").bind("click", function() {// 上一页
            if (1 == that.pageNow) {
                return;
            }
            that.pageNow = that.pageNow - 1;
            that.loadTBodyData();
            $("#pageBtns .page_a").removeClass("current");
            $("#pageBtns .page_a").eq(that.pageNow - 1).addClass("current");
        });

        $("#" + this.pageBtnsContentId).find(".next_page").bind("click", function() {// 下一页
            if (that.allPageSize == that.pageNow) {
                return;
            }
            that.pageNow = that.pageNow + 1;
            that.loadTBodyData();
            $("#pageBtns .page_a").removeClass("current");
            $("#pageBtns .page_a").eq(that.pageNow - 1).addClass("current");
        });

        $("#" + this.pageBtnsContentId).find(".page_a_go").bind("click", function() {// 下一页
            var pageNow = $(this).prev().val();
            var pageNowInt = parseInt(pageNow);
            if (isNaN(pageNowInt)) {
                Util.showTit("请输入数字", "no");
                return;
            }
            if (pageNowInt <= 0 || pageNowInt > that.allPageSize) {
                Util.showTit("输入数字必须在 0 与  " + that.allPageSize + " 之间", "no");
                return;
            }
            if (pageNowInt == that.pageNow) {// 如果点击的页面跟当前页相等，则返回
                return;
            }
            that.pageNow = pageNowInt;
            that.loadTBodyData();
            $("#pageBtns .page_a").removeClass("current");
            $("#pageBtns .page_a").eq(that.pageNow - 1).addClass("current");
        });
    };

    Page.prototype.loadTBodyData = function(data) {
        if (data) {
            this.filterParam = data;
            this.pageSize = data.page_size;
            this.pageNow = data.page_no;
        }
        var sendData = {
            "page_size" : this.pageSize,
            "page_no" : this.pageNow
        };
        if (this.forAuth2 == true) {
            if(data){
                sendData = data;
            }else{
                sendData = $.extend({},this.param,sendData);
            }
        }
        if (this.filterParam) {
            $.extend(this.filterParam,sendData);
        }
        var that = this;
        var url = this.url;
        var moduleId = this.moduleId;
        var data = sendData;

        var callback = function(result) {
            if(result.data != null) {
                var list = result.data.result;
                that.allPageSize = Math.ceil(result.data.total_count / that.pageSize);

                // 把当前索引号添加进去
                for (var i = 0; i < list.length; i++) {
                    var h_ = list[i];
                    h_.DATAINDEX_ = (that.pageNow - 1) * that.pageSize + i + 1;
                }
                var html = $("#" + that.rowTemplateId).tmpl(list);

                var bindTargets = html.find(".js_bind_data");
                if (typeof (that.targetContentId) == "string") {
                    $("#" + that.targetContentId).html(html);
                    bindTargets = $("#" + that.targetContentId).find(".js_bind_data");
                } else {
                    that.targetContentId.html(html);
                    bindTargets = that.targetContentId.find(".js_bind_data");
                }

                for (var i = 0; i < list.length; i++) {
                    var h_ = $(bindTargets[i]);
                    h_.data("personInfor", list[i]);
                    h_.attr("html", i);
                }

                that.initPageBtns(result.data.total_count, that.allPageSize);

            }else{
                var td_len = $("#table thead tr th").length;//表格字段数量
                $('#pageContent').find("tr").remove();
                $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
            }
        };

        Util.ajaxLoadData(url,data,moduleId,"POST",true,callback);
    };
    return Page;
})();