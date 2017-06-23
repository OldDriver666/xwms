$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");//所属公司id
    var url_param_id = Util.getParameter("id");
    var admin_id = Util.cookieStorage.getCookie("adminId");

	var action = {
		//新增数据
		/*add : function() {
            var url = ctx + "boss/fisedevice/addfisedevice";
            var data = new Object();
            data.ime = $("#input-devIME").val();
            data.account = $("#input-devXW").val();
            data.depart_id = parseInt(depart_id);
            data.type = parseInt($("#input-devType").val());
            data.mobile = $("#input-phoneNo").val();
            data.mark = $("#input-Mark").val();
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    action.loadPageData();
                }else if(result.code == 1){
                    toastr.error(result.ErrorInfo);
				}else{
                	alert("添加失败！");
				}
            });
		},*/
        //获取当前用户拥有的系统操作权限信息
       /* loadPrivateAuthData : function() {
            var url = ctx + "Manage/GetPrivateAuth";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "AuthFid": parseInt(url_param_id),  //AuthDirId
                "UserId": parseInt(admin_id)   //AdminId
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    var a = result.UsertAuthCode;
                    console.log(JSON.stringify(a));
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

        },*/
		//获取所有数据
		loadPageData : function() {
            var search_devType = parseInt($('#search-input-devType option:selected').val());
			var search_txt = $("#input-search-txt").val();
            var type_num = $("#search-type").val();
            var td_len = $("#table thead tr th").length;//表格字段数量
            $("#pagination").hide();
            var url = ctx + "boss/querydevice";
            if(type_num == 0){
                var data = new Object();
                data.account = search_txt;
                data.phone = "";
                data.depart_id = parseInt(depart_id);
                data.type = search_devType;
            }else if(type_num == 1){
                var data = new Object();
                data.account = "";
                data.phone = search_txt;
                data.depart_id = parseInt(depart_id);
                data.type = search_devType;
            }else if(type_num == 2){
                var data = new Object();
                data.depart_id = parseInt(depart_id);
                data.type = search_devType;
            }


            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS && result.data != ""){
                    $('#pageContent').find("tr").remove();
                    var pageTmplTheads = "#" + "pageTmplThead" + search_devType;
                    var pageTmplTbodys = "#" + "pageTmplTbody" + search_devType;
                    $(pageTmplTheads).tmpl().appendTo('#pageThead');
                    $(pageTmplTbodys).tmpl(result.data).appendTo('#pageContent');

                    if($('#pageContent tr').length == 0){
                        $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
                    }
                } else if(result.code == ReturnCode.DEVICE_NOT_EXIST_ERROR){
                    $('#pageContent').find("tr").remove();
                    alert(result.msg);
                }else {
                    $('#pageContent').find("tr").remove();
                    alert(result.msg);
                }
            },function() {
                $('#pageContent').find("tr").remove();
                alert("服务器开个小差，请稍后重试！")
            });


            /*var pageTmplTbodys = "pageTmplTbody" + search_devType;
            var opt = {
                "targetContentId" : "pageContent",
                "url" : url,
                "forAuth2" : true,
                "rowTemplateId" : pageTmplTbodys,
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
                    var pageTmplTheads = "#" + "pageTmplThead" + search_devType;
                    $(pageTmplTheads).tmpl().appendTo('#pageThead');
                    return result.data;
                },
                "param" : data
            };
            this.page = new Util.Page(opt);*/
		},
        //获取设备类型列表数据
        loadDevTypeData : function() {
            var myDevTypeArray = JSON.parse(Util.cookieStorage.getCookie("myDevTypeArray"));
            $("#pageDevType").tmpl(myDevTypeArray).appendTo('#search-input-devType');
        },

		//编辑数据
		/*edit : function() {
            var url = ctx + "boss/fisedevice/updatefisedevice";
            var data = new Object();
            data.fise_id = $("#input-id").val();
            data.ime = $("#input-devIME").val();
            data.account = $("#input-devXW").val();
            data.depart_id = parseInt(depart_id);
            data.type = parseInt($("#input-devType").val());
            data.mobile = $("#input-phoneNo").val();
            data.mark = $("#input-Mark").val();
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
                }
            });
		},*/
		//删除数据
        /*deleteItem : function(id) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "boss/fisedevice//delfisedevice";
				var data = new Object();
                data.fise_id = id;
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
                        action.loadPageData();
					}
				});
			}
		},*/
        //获取设备统计数据
        /*getDevStatusData : function() {
            var url = ctx + "FiseDeviceManage/GetCompanyDeviceInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode":token_value,
                "DepartId":parseInt(depart_id)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $('#dev_active_count').empty();
                    $('#dev_unActive_count').empty();
                    $('#dev_online_count').empty();
                    $("#page_dev_active_count").tmpl(result.DeviceInfo).appendTo('#dev_active_count');
                    $("#page_dev_unActive_count").tmpl(result.DeviceInfo).appendTo('#dev_unActive_count');
                    $("#page_dev_online_count").tmpl(result.DeviceInfo).appendTo('#dev_online_count');
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

        },*/
        //获取并处理批量插入的数据
       /* getDevTxtInfo : function () {
            var addCount = 10;
            var txtDevInfoList = $(".fileContentTxt").text();
            var txtDevType = $("#input-devType2").val();
            var txtDevInfo_arr = txtDevInfoList.split(/\s+/);//文件里一共有多少条数据
            var txt_IME_arr = [];
            var txt_XW_arr = [];
            for(i=0; i<txtDevInfo_arr.length; i++){
                var txt_dev_item_arr = txtDevInfo_arr[i].split(",");
                txt_IME_arr.push(txt_dev_item_arr[1]);//得到所有IME参数值的数组
                txt_XW_arr.push(txt_dev_item_arr[3]);//得到所有XW参数值的数组
            };
            var add_data_count = Math.ceil(txtDevInfo_arr.length/addCount);//计算得到要通过执行几次插入
            var add_data_last =  txtDevInfo_arr.length % addCount;//计算得到最后一次插入时，还剩几条数据

            //每隔一定时间调用一次插入
            var s = null;
            var k = -1;
           s = setInterval(function(){
                k++;
                var flag = null;
                var n = k * addCount;
                var num = 10;//每次插入几条数据的参数
                if(k == add_data_count-1){
                    var m = n + add_data_last;
                    flag = 1;
                    num = add_data_last;//如果执行到最后一次调用，最后一次剩下的数据条数赋给num,作为对应的参数值
                    clearInterval(s);//执行完最后一次调用后关闭计时器
                }else{
                    var m = n + addCount;
                }
                var param_arr = [];
                param_arr.splice(0, param_arr.length);//每执行完一次后清空下该数组
               //遍历分批次插入时，每一次要插入的数据
                for (l = n; l < m; l++) {
                    var param = new Object();
                    param.DeviceIME = txt_IME_arr[l];
                    param.DeviceXW = txt_XW_arr[l];
                    param.DepartId = parseInt(depart_id);
                    param.ProductType = parseInt(txtDevType);
                    param_arr.push(param);
                }
                action.addDevFile(param_arr,flag,num)
            },1000);

        },*/
        //批量添加设备信息
        /*addDevFile : function (param_arr,flag,num) {
            var url = ctx + "FiseDeviceManage/InsertInfo";
            var data = {
                UserName: userName,
                AuthenticCode: token_value,
                DeviceNo: num,//每次插入几条数据
                DeviceInfo: param_arr
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.Status == ReturnCode.SUCCESS) {
                    //通过函数传参得到flag,如果值为1时说明已经执行到最后一次调用，然后关闭弹窗，提示添加完成，获取最新数据
                     if(parseInt(flag) == 1){
                         $("#modal-loading").modal('hide');
                         toastr.success("添加完成!");
                         action.loadPageData();
                         action.getDevStatusData();
                     }
                }else if(result.Status == 1){
                    toastr.error(result.ErrorInfo);
                    $("#modal-loading").modal('hide');
                }else{
                    alert("添加失败！");
                    $("#modal-loading").modal('hide');
                }
            })
        }*/
	};
	window.action = action;
    action.loadDevTypeData();
	/*action.loadPageData();*/
    /*action.getDevStatusData();
    action.loadPrivateAuthData();*/

    //编辑获取数据数据
    /*$("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        $("#input-id").val(that.find("td").eq(0).text());
        $("#input-devIME").val(that.find("td").eq(1).text());
        $("#input-devXW").val(that.find("td").eq(2).text());
        $("#input-devType").val(that.find("td").eq(3).text());
       /!* $("#input-devType-txt").val(that.find("td").eq(3).text());*!/
        $("#input-phoneNo").val(that.find("td").eq(7).text());
        $("#input-Mark").val(that.find("td").eq(8).text());
        $("#addTempl-modal").modal("show");
    });*/

	/*$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		var $form = $("form#form-addTempl");
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("编辑设备信息");
            $("#input-phoneNo-wrap").show();
            $("#input-devType-wrap").hide();
            $("#input-devType-txt-wrap").show();
			$form.data("action", "edit");
		} else if (e.relatedTarget.id = "btn-add") {
			$("h4#addTempl-modal-label").text("添加设备信息");
			//$("#input-phoneNo-wrap").hide();
            $("#input-devType-wrap").show();
            $("#input-devType-txt-wrap").hide();
			$form.data("action", "add");
			$form[0].reset();
		}
	});

    $("#addTempl-modal2").on('show.bs.modal', function(e) {

    });*/

	//验证表单
    /*$("#form-addTempl").validate({
        rules : {
            devIME : {
                required : true
            },
            devXW : {
                required : true
            },
            devDepartID : {
                required : true
            },
            devType : {
                required : true
            }
        }
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
    /*$("#btn-add-submit").on('click', function() {
        var action = $("form#form-addTempl").data("action");
        if(action == "add"){
            if (!$("#form-addTempl").valid()) {
                return;
            }else if($("#input-devType").val() == "") {
                $("#input-devType").parent().parent().addClass("has-error");
                var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
                $("#input-devType").parent().append(err_html);
                return;
            }else {
                window.action.add();
            }
        }else if(action == "edit"){
            if (!$("#form-addTempl").valid()) {
                return;
            }else{
                window.action.edit();
            }
        }
    });*/

    /*$("#btn-add-submit2").click(function(){
        if($("#filepath").val() != '' && $("#input-devType2").val() != ''){
            $("#addTempl-modal2").modal('hide');
            $("#modal-loading").modal({backdrop: 'static', keyboard: false, show: true});
            action.getDevTxtInfo();
        }else if($("#filepath").val() == ''){
            alert("请选择文件！");
        }else if($("#input-devType2").val() == ''){
            alert("请选择设备类型！");
        }
    });*/

	$("#btn-search").on('click', function() {
        if($("#search-input-devType").val() == "") {
            $("#search-input-devType").parent().addClass("has-error");
            var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
            $("#search-input-devType").append(err_html);
            return;
        }
        $("#pageThead").empty();
        action.loadPageData();
	});
    $("#input-search-devType").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

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