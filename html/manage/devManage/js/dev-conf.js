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
		//获取所有数据
		loadPageData : function() {
            var search_devType = parseInt($('#search-input-devType option:selected').val());
			var search_account = $("#input-search-account").val();
            var search_phone = $("#input-search-phone").val();
            var search_device_id = $("#input-search-device_id").val();

            if($("#search-input-devType").val() == "") {
                toastr.error("请选择设备类型");
                return;
            }
            if((search_account == "")&&(search_phone == "")&&(search_device_id == "")){
                toastr.error("请至少输入“设备ID/设备账号/设备电话号码”中的一项进行搜索");
                return;
            }

            var td_len = $("#table thead tr th").length;//表格字段数量
            var td_len1 = $("#table1 thead tr th").length;//表格字段数量
            var td_len2 = $("#table2 thead tr th").length;//表格字段数量
            var td_len3 = $("#table3 thead tr th").length;//表格字段数量
            $("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();
            $("#table tfoot").hide();
            $("#table1 tfoot").hide();
            $("#table2 tfoot").hide();
            $("#table3 tfoot").hide();
            var url = ctx + "boss/querydevice";
            var data = new Object();
                data.account = search_account;
                data.phone = search_phone;
                data.type = search_devType;
                data.device_id = parseInt(search_device_id);
                data.depart_id = parseInt(depart_id);
                data.company_id = parseInt(company_id);
            Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS && result.data != ""){
                    $('#pageContent').find("tr").remove();
                    $('#pageContent1').find("tr").remove();
                    $('#pageContent2').find("tr").remove();
                    $('#pageContent3').find("tr").remove();

                    var pageTmplTheads = "#" + "pageTmplThead" + search_devType;
                    var pageTmplTbodys = "#" + "pageTmplTbody" + search_devType;
                    $(pageTmplTheads).tmpl().appendTo('#pageThead');
                    $(pageTmplTbodys).tmpl(result.data.base_info).appendTo('#pageContent');
                    if((search_devType == 19) || (search_devType == 23) || (search_devType == 25)){
                        $(FenceTmplThead).tmpl().appendTo('#pageThead1');
                        $(FenceTmplTbody).tmpl(result.data.electri_fence).appendTo('#pageContent1');

                        if($('#pageContent1 tr').length == 0){
                            $('#pageContent1').append("<tr><td  colspan='" + td_len1 + "' class='t_a_c'>暂无数据</td></tr>");
                        }
                    }
                    $(ControlTmplThead).tmpl().appendTo('#pageThead2');
                    $(ControlTmplTbody).tmpl(result.data.control).appendTo('#pageContent2');
                    if(search_devType == 25) {
                        $(CrontabTmplThead).tmpl().appendTo('#pageThead3');
                        $(CrontabTmplTbody).tmpl(result.data.crontab).appendTo('#pageContent3');
                        if($('#pageContent3 tr').length == 0){
                            $('#pageContent3').append("<tr><td  colspan='" + td_len3 + "' class='t_a_c'>暂无数据</td></tr>");
                        }
                    }
                    if($('#pageContent tr').length == 0){
                        $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
                    }
                    if($('#pageContent2 tr').length == 0){
                        $('#pageContent2').append("<tr><td  colspan='" + td_len2 + "' class='t_a_c'>暂无数据</td></tr>");
                    }
                } else if(result.code == ReturnCode.SUCCESS && result.data.length == 0){
                    $('#pageContent').find("tr").remove();
                    $('#pageContent1').find("tr").remove();
                    $('#pageContent2').find("tr").remove();
                    $('#pageContent3').find("tr").remove();
                    toastr.error("没有查到该设备信息");
                }else {
                    $('#pageContent').find("tr").remove();
                    $('#pageContent1').find("tr").remove();
                    $('#pageContent2').find("tr").remove();
                    $('#pageContent3').find("tr").remove();
                    toastr.error("未查询到相关信息");
                }
            },function(errorMsg) {
                $('#pageContent').find("tr").remove();
                $('#pageContent1').find("tr").remove();
                $('#pageContent2').find("tr").remove();
                $('#pageContent3').find("tr").remove();
                alert(errorMsg)
            });


		},
        //获取设备类型列表数据
        loadDevTypeData : function() {
            var myDevTypeArray = JSON.parse(localStorage.getItem("myDevTypeArray"));
            $("#pageDevType").tmpl(myDevTypeArray).appendTo('#search-input-devType');
        }
	};
	window.action = action;
    action.loadDevTypeData();

	$("#btn-search").on('click', function() {
        if($("#search-input-devType").val() == "") {
            toastr.error("请选择设备类型");
        /*} else if (!(/^[0-9]*$/.test($("#input-search-device_id").val()))) {
            toastr.error("设备ID不能为非数字");*/
        } else {
            /*$("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();*/
            action.loadPageData();
        }
	});
	$("#input-search-account").on('keydown', function(e) {
        if (e.keyCode == 13) {
            /*$("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();*/
            action.loadPageData();
        }
	});
    $("#input-search-phone").on('keydown', function(e) {
        if (e.keyCode == 13) {
            /*$("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();*/
            action.loadPageData();
        }
    });
    $("#input-search-device_id").on('keydown', function(e) {
        if (e.keyCode == 13) {
            /*$("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();*/
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
            if(result.DeviceInfo != null) {
                var list = list = result.DeviceInfo;
                that.allPageSize = Math.ceil(result.DeviceCount / that.pageSize);

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

                var h_ = $(bindTargets[i]);
                h_.data("personInfor", list[i]);
                h_.attr("html", i);

                that.initPageBtns(result.DeviceCount, that.allPageSize);

            }else{
                var td_len = $("#table thead tr th").length;//表格字段数量
                $('#table1').remove();
                $('#table2').remove();
                $('#table3').remove();
                $('#pageContent').find("tr").remove();
                $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
            }
        };

        Util.ajaxLoadData(url,data,moduleId,"POST",true,callback);
    };
    return Page;
})();