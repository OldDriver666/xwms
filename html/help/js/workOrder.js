$(function() {
    var url=location.search;
    var Request = new Object();
    if(url.indexOf("?")!=-1) {
        var str = url.substr(1)　//去掉?号
        strs = str.split("&");
        for(var i=0;i<strs.length;i++){
            Request[strs[i ].split("=")[0]]=unescape(strs[ i].split("=")[1]);
        }
    };

    var req_uid = Request["uid"];
    var req_uname = Request["uname"];
    if (typeof(req_uid) == "undefined" || typeof(req_uname) == "undefined") {
        Util.cookieStorage.setCookie("userId","2000020000");
        Util.cookieStorage.setCookie("userName","anonymous")
    } else {
        Util.cookieStorage.setCookie("userId",req_uid);
        Util.cookieStorage.setCookie("userName",req_uname);
    }
    //$("#aaa").html(req_uid + ',' + req_uname + ',' + Util.cookieStorage.getCookie("userId") + ',' + Util.cookieStorage.getCookie("userName"))
    var tab_html = '<li class="tab-nav"><a href="feedback.html?uid=' + Util.cookieStorage.getCookie("userId") + '&uname=' + Util.cookieStorage.getCookie("userName") + '"><em class="octicon octicon-book"></em>知识库</a></li><li class="tab-nav active" ><a href="workOrder.html?uid=' + Util.cookieStorage.getCookie("userId") + '&uname=' + Util.cookieStorage.getCookie("userName") + '"><em class="octicon octicon-comment-discussion"></em>公开问题</a></li>'
    document.getElementById('tab-navs').innerHTML = tab_html;


    var action = {
        //新增数据
        add : function() {
        },
        //获取所有数据
        loadPageData : function() {
            var url = ctx + "boss/suggest/query";
            var data = {
                "param": {
                    "userId": null,
                    "uname": "",
                    "title": "",
                    "type": 0
                },
                "page_no": 1,
                "page_size": 10
            };
            var opt = {
                "targetContentId" : "pageContent",
                "url" : url,
                "rowTemplateId" : "pageTmpl",
                "contextUrl" : ctx,
                "pageBtnsContentId" : "pagination",
                "param" : data
            };
            this.page = new Util.Page(opt);
        },
        //搜索数据
        loadPageData2 : function(key) {
            var url = ctx + "boss/suggest/query";
            var data = {
                "param": {
                    "userId": null,
                    "uname": "",
                    "title": key,
                    "type": 0
                },
                "page_no": 1,
                "page_size": 10
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    console.log(result);
                    $("#search_results").empty();
                    $("#search_results").show()
                    $("#pageTmpl2").tmpl(result.data.result).appendTo('#search_results');
                }else {
                    console.log("请求出错！");
                }
            },function() {
                console.log("服务器异常！")
            });
        },
        //编辑数据
        edit : function() {
        },
        //删除数据
        deleteItem : function(id, name) {
            if (confirm("删除后不可恢复，确定删除" + name + "？")) {
            }
        },
        getSearchKey : function () {
            var key = document.getElementById("search").value;
            console.log(key)
            if (key === ""){
                $("#search_results").html("").hide()
            } else {
                action.loadPageData2(key)
            }
        }
    };
    window.action = action;
    action.loadPageData();

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
        this.showPageNum = 3;// 显示多少个页面数
        this.param = options.param;
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
        //htmlStr = '<span>共'+allPageSize+'页，'+totalCount+'条数据</span>';
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
        //htmlStr += '<input type="text"  style="width:30px;"/>';
        //htmlStr += '<a href="javascript:void(0)" class="page_a jsBtnA page_a_go" style="margin-left:5px;">Go</a>';
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

    Page.prototype.loadTBodyData = function(data1) {
        if (data1) {
            this.pageSize = data1.page_size;
            this.pageNow = data1.page_no;
        }
        var sendData = {
            "page_size" : this.pageSize,
            "page_no" : this.pageNow
        };
        if(data1){
            sendData = data1;
        }else{
            sendData = $.extend({},this.param,sendData);
        }
        var that = this;
        var url = this.url;
        var data = sendData;

        var callback = function(result) {
            if(result.data != null){
                var total_count = result.data.total_count;
                var list = result.data.result;

                that.allPageSize = Math.ceil(total_count/that.pageSize);

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

                that.initPageBtns(total_count,that.allPageSize);

            } else {
                /*var td_len = $("#table thead tr th").length;//表格字段数量
                 $('#pageContent').find("tr").remove();
                 $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");*/
            }
        };

        Util.ajaxLoadData(url,data,"POST",true,callback);
    };
    return Page;
})();