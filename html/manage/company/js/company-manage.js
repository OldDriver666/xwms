$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var role_level = Util.cookieStorage.getCookie("userLevel");
    var admin_id = Util.cookieStorage.getCookie("adminId");

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "CooperativeCompany/AddCompany";
            var param_arr = [];
            var ipt_list = $("#authContent").find("input:checkbox:checked");
            for(i=0; i<ipt_list.length; i++){
                var ipt_item = ipt_list.eq(i).val();
                param_arr.push(parseInt(ipt_item));
            }
            var data = {
                UserName:userName,
                AuthenticCode:token_value,
                CompanyName:$("#input-companyName").val(),
                CompanyAddr:$("#input-companyAddr").val(),
                CompanyContacts:$("#input-contactName").val(),
                CompanyPhone:$("#input-phoneNo").val(),
                Email:$("#input-Email").val(),
                CompanyDescribe:$("#input-notes").val(),
                AuthenticId:param_arr
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.Status == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    action.loadPageData();
                }else if(result.Status == 1){
                    toastr.error(result.ErrorInfo);
				}else{
                	alert("添加失败！");
				}
            });
		},
		//获取所有数据
		loadPageData : function() {
			var search_txt = $("#input-search-txt").val();
            var url = ctx + "CooperativeCompany/QueryCompanyInfo";
            var data = {
                UserName:userName,
                AuthenticCode:token_value,
                CompanyName:search_txt,
                RoleLevel:parseInt(role_level),
                Page:1,
                PageSize:30
            };
            var opt = {
                "targetContentId" : "pageContent",
                "url" : url,
                "forAuth2" : true,
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
                    return result.CompanyInfo;
                },
                "param" : data
            };
            this.page = new Util.Page(opt);
		},
        //获取权限目录数据
        loadAuthData : function() {
            var url = ctx + "AuthorityManage/GetAuthority";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "AuthRole" : 2,
                "FatherId":0
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $("#pageAuth").tmpl(result.AuthenticInfo).appendTo('#authContent');
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

        },
        //获取公司已有的权限目录数据
        loadCompanyAuthData : function(c_id) {
            var url = ctx + "AdminManage/GetCompanyAuthInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "DepartId":parseInt(c_id),
                "AdminId" : parseInt(admin_id),
                "RoleLevel":parseInt(role_level)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    var allAuth = result.AuthDirInfo;
                    var auth_name_arr = [];
                    for (i=0; i<allAuth.length; i++){
                        auth_name_arr.push(allAuth[i].AuthDirName)
                    }
                    if(auth_name_arr.length == 0){
                        alert("没有分配权限！");
                    }else{
                        alert(auth_name_arr.join(" | "));
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
            var url = ctx + "CooperativeCompany/ModifyCompanyInfo";
            var data = new Object();
            data.UserName = userName;
            data.AuthenticCode = token_value;
            data.CompanyId = parseInt($("#input-id").val());
            data.CompanyName = $("#input-companyName").val();
            data.CompanyAddr = $("#input-companyAddr").val();
            data.CompanyDescribe = $("#input-notes").val();
            data.CompanyContacts = $("#input-contactName").val();
            data.CompanyPhone = $("#input-phoneNo").val();
            data.Email = $("#input-Email").val();
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.Status == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
                }
            });
		},
		//删除数据
        deleteItem : function(id, name) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "CooperativeCompany/DelCompanyInfo";
				var data = new Object();
                data.UserName = userName;
                data.AuthenticCode = token_value;
                data.CompanyId = id;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.Status == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
                        action.loadPageData();
					}
				});
			}
		}
	};
	window.action = action;
	action.loadPageData();
	action.loadAuthData();

    //编辑获取数据数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        $("#input-id").val(that.find(".td-id").text());
        $("#input-companyName").val(that.find(".td-companyName").text());
        $("#input-companyAddr").val(that.find(".td-companyAddr").text());
        $("#input-contactName").val(that.find(".td-contactName").text());
        $("#input-phoneNo").val(that.find(".td-phoneNo").text());
        $("#input-Email").val(that.find(".td-email").text());
        $("#input-notes").val(that.find(".td-notes").text());
        $("#addTempl-modal").modal("show");
    });

    $("#addTempl-modal").on('show.bs.modal', function(e) {
        // 处理modal label显示及表单重置
        var $form = $("form#form-addTempl");
        if (!e.relatedTarget) {
            $("h4#addTempl-modal-label").text("编辑公司信息");
            $(".authContent-wrap").hide();
            $form.data("action", "edit");
        } else if (e.relatedTarget.id = "btn-add") {
            $("h4#addTempl-modal-label").text("添加公司信息");
            $(".authContent-wrap").show();
            $form.data("action", "add");
            $form[0].reset();
        }
    });

	//验证表单
    $("#form-addTempl").validate({
        rules : {
            companyName : {
                required : true
            },
            companyAddr : {
                required : true
            },
            contactName : {
                required : true
            },
            phoneNo : {
                required : true
            },
            email : {
                required : true
            }
        }
    });

    $("#input-devType").change(function(){
        if($(this).val() != ""){
            $(this).parent().parent().removeClass("has-error");
            $(this).next().remove();
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
    $("#pageContent").on("click",".table-showAuth-btn",function(){
        var cId = $(this).parent().parent().find(".td-id").text();
        action.loadCompanyAuthData(cId);
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
                    cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA " title="第'
                        + pageNow + '页">' + pageNow + '</a>';
                    htmlStr = htmlStr + cBtnStr;
                }
            }
            if (pe > 3) {
                if (i == that.pageNow - 2 && i > 0) {
                    htmlStr = htmlStr + "<span>...</span>";
                }
                if (i >= (that.pageNow - 2) && i <= (that.pageNow + 2)) {
                    if (pageNow == that.pageNow) {
                        cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA current" title="第'
                            + pageNow + '页">' + pageNow + '</a>';
                    } else {
                        cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA " title="第'
                            + pageNow + '页">' + pageNow + '</a>';
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
                        cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA current" title="第'
                            + pageNow + '页">' + pageNow + '</a>';
                    } else {
                        cBtnStr = '<a href="javascript:void(0)" class="page_a current_page jsBtnA " title="第'
                            + pageNow + '页">' + pageNow + '</a>';
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
        $("#" + this.pageBtnsContentId).find(".current_page").bind("click",
            function() {// 点击某一页
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

        $("#" + this.pageBtnsContentId).find(".prev_page").bind(
            "click",
            function() {// 上一页
                if (1 == that.pageNow) {
                    return;
                }
                that.pageNow = that.pageNow - 1;
                that.loadTBodyData();
                $("#pageBtns .page_a").removeClass("current");
                $("#pageBtns .page_a").eq(that.pageNow - 1).addClass(
                    "current");
            });

        $("#" + this.pageBtnsContentId).find(".next_page").bind(
            "click",
            function() {// 下一页
                if (that.allPageSize == that.pageNow) {
                    return;
                }
                that.pageNow = that.pageNow + 1;
                that.loadTBodyData();
                $("#pageBtns .page_a").removeClass("current");
                $("#pageBtns .page_a").eq(that.pageNow - 1).addClass(
                    "current");
            });

        $("#" + this.pageBtnsContentId).find(".page_a_go").bind(
            "click",
            function() {// 下一页
                var pageNow = $(this).prev().val();
                var pageNowInt = parseInt(pageNow);
                if (isNaN(pageNowInt)) {
                    Util.showTit("请输入数字", "no");
                    return;
                }

                if (pageNowInt <= 0 || pageNowInt > that.allPageSize) {
                    Util.showTit("输入数字必须在 0 与  " + that.allPageSize
                        + " 之间", "no");
                    return;
                }

                if (pageNowInt == that.pageNow) {// 如果点击的页面跟当前页相等，则返回
                    return;
                }
                that.pageNow = pageNowInt;
                that.loadTBodyData();
                $("#pageBtns .page_a").removeClass("current");
                $("#pageBtns .page_a").eq(that.pageNow - 1).addClass(
                    "current");
            });
    };

    Page.prototype.loadTBodyData = function(data) {

        var target = null;
        if (typeof (this.targetContentId) == "string") {
            target = $("#" + this.targetContentId);
        } else {
            target = this.targetContentId;
        }
        var length = target.prev().find("th").length
            || target.prev().find("td").length || 7;
        var loadDiv = $('<tr><td colspan="' + length
            + '"><div class="loading"></div></td></tr>');
        target.css({
            "postion" : "relative"
        });
        target.html(loadDiv);
        var contentType = null;
        if (data) {
            this.filterParam = data;
            this.pageSize = data.PageSize;
            this.pageNow = data.Page;
        }
        var sendData = {
            "PageSize" : this.pageSize,
            "Page" : this.pageNow
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
        var data = sendData;
        var callback = function(result) {
            if(result.Status !=0){
                Util.showDialog({title:"提示",msg:result.ErrorInfo,okBth:"确定"});
                return;
            }
            if(!result.CompanyInfo){
                result.CompanyInfo = null;
            }
            /*that.allPageSize = result.data.totalPageCount;*/
            that.allPageSize = Math.ceil(result.CompanyCount/that.pageSize);
            var list = null;
            if (that.resultFilter) {
                list = that.resultFilter(result);
            } else {
                list = result.CompanyInfo;
            }

            // 把当前索引号添加进去
            for (var i = 0; i < list.length; i++) {
                var h_ = list[i];
                h_.DATAINDEX_ = (that.pageNow - 1) * that.pageSize + i + 1;
            }
            var html = "";
            if (that.tmplEvents) {
                html = $("#" + that.rowTemplateId).tmpl(list, that.tmplEvents);
            } else {
                html = $("#" + that.rowTemplateId).tmpl(list);
            }

            var bindTargets = html.find(".js_bind_data");
            if (typeof (that.targetContentId) == "string") {
                // if(html.length!=0){
                $("#" + that.targetContentId).html(html);
                // }
                bindTargets = $("#" + that.targetContentId).find(
                    ".js_bind_data");
            } else {
                that.targetContentId.html(html);
                bindTargets = that.targetContentId.find(".js_bind_data");
            }

            for (var i = 0; i < list.length; i++) {
                var h_ = $(bindTargets[i]);
                h_.data("personInfor", list[i]);
                h_.attr("html", i);
            }

            that.initPageBtns(result.CompanyCount,that.allPageSize);
            target.find(".load_icon").remove();
            if (list.length == 0) {
                var dom = target.get(0);
                var nodeName = dom.nodeName;

                nodeName = nodeName.toLowerCase();
                if (nodeName == "tbody") {
                    var length = target.prev().find("th").length || target.prev().find("td").length || 7;
                    target.append("<tr><td colspan='"
                        + length
                        + "' class='t_a_c'>暂无数据</td></tr>");
                } else {
                    target.append("<div class='no_data_div'>暂无数据</div>");
                }
            }
            if (that.callback) {
                that.callback();
            }
            if(typeof(that.tmplBindEvents)!="object"){
                that.tmplBindEvents();
            }
        };

        Util.ajaxLoadData(this.url,sendData,"POST",true,callback);
    };
    return Page;
})();