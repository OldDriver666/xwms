$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");//所属公司id
    var url_param_id = Util.getParameter("id");
    var admin_id = Util.cookieStorage.getCookie("adminId");

	var action = {
		//获取所有数据
		loadPageData : function() {
            if($("#search-input-devType").val() == "") {
                $("#search-input-devType").parent().addClass("has-error");
                var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
                $("#search-input-devType").append(err_html);
                return;
            }

            var search_devType = parseInt($('#search-input-devType option:selected').val());
			var search_account = $("#input-search-account").val();
            var search_phone = $("#input-search-phone").val();
            var search_device_id = $("#input-search-device_id").val();


            if((search_account == "")&&(search_phone == "")&&(search_device_id == "")){
                alert("请至少输入“设备ID/设备账号/设备电话号码”中的一项进行搜索");
                return;
            }
            var td_len = $("#table thead tr th").length;//表格字段数量
            var td_len1 = $("#table1 thead tr th").length;//表格字段数量
            var td_len2 = $("#table2 thead tr th").length;//表格字段数量
            var td_len3 = $("#table3 thead tr th").length;//表格字段数量
            $("#pagination").hide();
            $("#pagination1").hide();
            $("#pagination2").hide();
            $("#pagination3").hide();
            var url = ctx + "boss/querydevice";
            var data = new Object();
                data.account = search_account;
                data.phone = search_phone;
                data.depart_id = parseInt(depart_id);
                data.type = search_devType;
                data.device_id = parseInt(search_device_id);


            Util.ajaxLoadData(url,data,"POST",true,function(result) {
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
                    alert("没有查到该设备信息");
                }else {
                    $('#pageContent').find("tr").remove();
                    $('#pageContent1').find("tr").remove();
                    $('#pageContent2').find("tr").remove();
                    $('#pageContent3').find("tr").remove();
                    alert(result.msg);
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

/*    //获取fence数据
    $("#pageContent").on("click",".table-fence-btn",function(){
        $("#addTempl-modal").modal("show");
    });

    //获取control数据
    $("#pageContent").on("click",".table-control-btn",function(){
        $("#addTempl-modal").modal("show");
    });

    //获取crontab数据
    $("#pageContent").on("click",".table-crontab-btn",function(){
        $("#addTempl-modal").modal("show");
    });*/




    $("#dev-query-condition").validate({
        rules : {
           /* devType : {
                required : true
            }*/
        }
    });

    $("#search-input-devType").change(function(){
        if($(this).val() != ""){
            $(this).parent().removeClass("has-error");
            $(this).next().remove();
        }
    });

	$("#btn-search").on('click', function() {
        if($("#search-input-devType").val() == "") {
            $("#search-input-devType").parent().addClass("has-error");
            var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
            $("#search-input-devType").append(err_html);
            return;
        }else{
            $("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();
            action.loadPageData();
        }
	});
    $("#input-search-devType").on('keydown', function(e) {
        if (e.keyCode == 13) {
            $("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();
            action.loadPageData();
        }

    });
	$("#input-search-account").on('keydown', function(e) {
        if (e.keyCode == 13) {
            $("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();
            action.loadPageData();
        }
	});
    $("#input-search-phone").on('keydown', function(e) {
        if (e.keyCode == 13) {
            $("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();
            action.loadPageData();
        }
    });
    $("#input-search-device_id").on('keydown', function(e) {
        if(isNaN($("#input-search-device_id").val())) {
            $("#input-search-device_id").parent().addClass("has-error");
            return;
        }
        if (e.keyCode == 13) {
            $("#pageThead").empty();
            $("#pageThead1").empty();
            $("#pageThead2").empty();
            $("#pageThead3").empty();
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

        $("#pageThead").empty();
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
            /*if(result.Status !=0){
                Util.showDialog({title:"提示",msg:result.ErrorInfo,okBth:"确定"});
            }*/
            if(!result.DeviceInfo){
                result.DeviceInfo = null;
            }
            that.allPageSize = Math.ceil(result.DeviceCount/that.pageSize);
            var list = null;
            if (that.resultFilter) {
                list = that.resultFilter(result);
            } else {
                list = result.DeviceInfo;
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

            that.initPageBtns(result.DeviceCount,that.allPageSize);
            target.find(".load_icon").remove();
            //如果查询到的数据长度为0；
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

        Util.ajaxLoadData(url,data,"POST",true,callback);
    };
    return Page;
})();

/*Util.ajaxLoadData(url,data,"POST",true,function(result) {
 if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
 $('#pageContent').find("tr").remove();
 $("#pageTmpl").tmpl(result.DeviceInfo).appendTo('#pageContent');

 if($('#pageContent tr').length == 0 || result.Status == 6){
 $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
 }
 }else if(result.Status == 6){
 $('#pageContent').find("tr").remove();
 $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
 }else {
 alert("请求出错！");
 }
 },function() {
 alert("服务器开个小差，请稍后重试！")
 });
 */