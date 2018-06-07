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
            var url = ctx + "boss/suggest/add";
            var data = new Object();
			data.user_id = parseInt($("#input-user_id").val());
			data.uname = $("#input-uname").val();
			data.suggestion = $("#input-suggestion").val();
			data.contact = $("#input-contact").val();
			data.depart_id = parseInt(depart_id);
			data.company_id = parseInt(company_id);

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
            var search_uname = $("#input-search-uname").val();

            var url = ctx + "boss/suggest/query";
			if(search_uname == ""){
				var uname = null;
			}else{
				var uname = search_uname;
			}
            var search_title = $("#input-search-title").val();
            var search_status = parseInt($('#input-search-status option:selected').val());
            var search_type = parseInt($('#input-search-type option:selected').val());

			var data = new Object();
			data.page_no = 1;
			data.page_size = 10;
			data.param = {
				"uname":uname,
                "title":search_title,
                "type":search_type,
                "status":search_status,
				"depart_id":parseInt(depart_id),
				"company_id":parseInt(company_id)
			};

			var opt = {
				"targetContentId" : "pageContent",
				"url" : url,
				"moduleId" : moduleId,
				"forAuth2" : true,
				"updateAuth" : updateAuth,
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
		//编辑数据
		edit : function() {
			var url = ctx + "boss/suggest/update";
			var data = new Object();
			data.suggestId = $("#input-suggest_id").val();
			data.status = parseInt($("input[name=status]:checked").val());
            data.type = parseInt($("input[name=type]:checked").val());
			data.depart_id = parseInt(depart_id);
			data.company_id = parseInt(company_id);

			Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
				if (result.code == ReturnCode.SUCCESS) {
			 		$("#addTempl-modal2").modal('hide');
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
		deleteConfig : function(id) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "boss/suggest/del";
				var data = new Object();
                data.suggestId = id;
				data.depart_id = parseInt(depart_id);
				data.company_id = parseInt(company_id);
				Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
					if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
						$("#input-search-uname").val("");
                        action.loadPageData();
					}else{
                        toastr.error(result.msg);
					}
				},function(errorMsg) {
                    toastr.error(errorMsg);
                });
			}
		},
        //新增数据
        add1 : function(suggestId, type) {
            if($('#addReply').find('textarea').val().trim() === "") {
                toastr.error("内容不能为空!");
                return false
            }
            //获取图片url
            var imgEle = $('#file_list').find('img');
            var imgUrlArr = [];
            var imgUrl = '';
            if (imgEle.length > 0) {
                for (var i = 0; i<imgEle.length; i++) {
                    imgUrlArr.push(imgEle[i].src)
                }
                imgUrl = imgUrlArr.join(',')
            }

            if (suggestId === '' || suggestId === undefined || suggestId === null) {
                toastr.error("提交失败，请稍后再试!");
            } else if ($('textarea[name=content]').val() === '') {
                toastr.error("回复内容不能为空!");
            } else if ($('textarea[name=content]').val().length > 200) {
                toastr.error("回复内容已超过了字数限制");
            } else {
                var url = ctx + "boss/suggest/add";
                var data = {
                    "userId": 2000020000,
                    "uname": "anonymous",
                    "title": "",
                    "type": type, //0-公开 1-私有
                    "content": $('textarea[name=content]').val(),
                    "contact": "",
                    "pictures": imgUrl,
                    "suggestId": suggestId
                };
                Util.ajaxworkOrderData(url,data,"POST",true,function(result) {
                    if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("添加成功!");
                        $('textarea[name=content]').val('');
                        $('#file_list .attachment2-item').remove();
                        action.loadPageData2(suggestId);
                    } else {
                        console.log("添加失败！");
                    }
                },function() {
                    console.log("服务器异常！")
                });
            }
        },
        //根据建议id查询用户回复建议
        loadPageData2 : function(suggestId) {
            var url = ctx + "boss/suggest/queryBySuggestId";
            var data = {
                "page_no": 1,
                "page_size": 10,
                "param": {
                    "suggestId": suggestId
                }
            };
            if (suggestId === undefined || suggestId === null || suggestId === '') {
                console.log('无记录')
            } else {
                Util.ajaxworkOrderData(url,data,"POST",true,function(result) {
                    if(result.code == ReturnCode.SUCCESS){
                        var myData = result.data.result
                        $(".ticket .replyNum").html(result.data.result.length - 1)
                        $('#pageContent1').empty();
                        var html = '';
                        var imgListArr = []
                        for (var n = 1; n < result.data.result.length; n++) {
                            var picArr = result.data.result[n].pictures.split(',');
                            if (result.data.result[n].pictures === '' && typeof result.data.result[n].pictures === 'string') {

                            } else {
                                for (var m = 0; m < picArr.length; m++) {
                                    html = '<div class="attachment-item">' +
                                        '<a href="' + picArr[m] + '" target="_blank"><img src="' + picArr[m] + '?w=88&h=88" /></a>' +
                                        '</div>';
                                    imgListArr.push(html);
                                }
                                myData[n].pictures = imgListArr.join('');
                                imgListArr.length = 0;
                            }
                        }
                        var html2 = '';
                        for (var i = 1; i < myData.length; i++) {
                            html2 = '<div class="reply-item user">' +
                                '<div class="info clearfix">' +
                                '<div class="avatar">' +
                                '<img src="../../images/none.png" />' +
                                '</div>' +
                                '<span class="author">' + formatname((myData[i].uname)) + '</span>' +
                                '<span class="time">' + timestampToTime(myData[i].created) + '</span>' +
                                '</div>' +
                                '<div class="content">' +
                                '<p>' + myData[i].content + '</p>' +
                                '<div class="attachment">' + myData[i].pictures + '</div>' +
                                '</div>' +
                                '</div>';
                            $('#pageContent1').append(html2)
                        }
                    } else {
                        console.log("请求出错！");
                    }
                },function() {
                    console.log("服务器异常！")
                });
            }
        }
	};
	window.action = action;
	action.loadPageData();

	$("#addTempl-modal").on('show.bs.modal', function(e) {
		// 处理modal label显示及表单重置
		if (!e.relatedTarget) {
			$("h4#addTempl-modal-label").text("回复用户意见");
		} else if (e.relatedTarget.id = "btn-add") {
			/*$("h4#addTempl-modal-label").text("添加用户意见");
			$("#input-status-txt-wrap").hide();
			$("#input-suggestion-wrap").show();
			$("#input-suggestion-txt-wrap").hide();
			$form.data("action", "add");
			$form[0].reset();*/
		}
	});
    $("#addTempl-modal2").on('show.bs.modal', function(e) {
        // 处理modal label显示及表单重置
        var $form = $("form#form-addTempl");
        if (!e.relatedTarget) {
             $("h4#addTempl-modal-label2").text("编辑用户意见");
             $("#input-status-txt-wrap").show();
             $("#input-suggestion-wrap").hide();
             $("#input-suggestion-txt-wrap").show();
             $form.data("action", "edit");
        } else if (e.relatedTarget.id = "btn-add") {
           /* $("h4#addTempl-modal-label").text("添加用户意见");
            $("#input-status-txt-wrap").hide();
            $("#input-suggestion-wrap").show();
            $("#input-suggestion-txt-wrap").hide();
            $form.data("action", "add");
            $form[0].reset();*/
        }
    });

    //回复内容
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        var id = parseInt(that.find(".td-id").text());
        var suggestId = that.find(".td-suggestId").text();
        var uname = that.find(".td-uname").text();
        var type = parseInt(that.find(".td-type").text());

        var imgStr = that.find(".td-pictures").text();
        var htmlImg = ''
        var htmlImgList = [];
        $(".ticket .title").find('h2').html(that.find(".td-title").text());
        $(".ticket .title").find('.time').html(timestampToTime(that.find(".td-updated").text()));
        $(".ticket .markdown-body").find('p').html(that.find(".td-content").text());
        if (imgStr != '') {
            var imgArr = imgStr.split(',');
            for (var j = 0; j<imgArr.length; j++) {
                htmlImg = '<div class="attachment-item"><a href="' + imgArr[j] + '" target="_blank"><img src="' + imgArr[j] + '?w=88&h=88" /></a></div>'
                htmlImgList.push(htmlImg)
            }
        }
        $(".ticket .attachment").html(htmlImgList.join(''));

        window.action.loadPageData2(suggestId);
        $("#addTempl-modal").modal("show");
        $('#addReply').find('button[type=submit]').click(function (t) {
            t.preventDefault();
            window.action.add1(suggestId, type)
        })
    });

    //修改状态
    $("#pageContent").on("click",".table-modify-btn",function(){
        var that = $(this).parent().parent();
        var status_val = $.trim(that.find(".td-status").text());
        var type_val = $.trim(that.find(".td-type").text());


        $("#input-suggest_id").val(that.find(".td-suggestId").text());
        $("#input-user_id").val(that.find(".td-userId").text());
        $("#input-uname").val(that.find(".td-uname").text());
        $("#input-title-txt").val(that.find(".td-title").text());
        $("#input-suggestion-txt").val(that.find(".td-content").text());
        $("#input-contact").val(that.find(".td-contact").text());
        $("input[name=status]").filter("[value=" + status_val + "]").prop('checked', true);
        $("input[name=type]").filter("[value=" + type_val + "]").prop('checked', true);


        $("#addTempl-modal2").modal("show");
    });
    //删除数据
    $("#pageContent").on("click",".table-delete-btn",function(){
        var that = $(this).parent().parent();
        var suggestId = that.find("td").eq(0).text();
        window.action.deleteConfig(suggestId)
    });

	//验证表单
    $("#form-addTempl").validate({
        rules : {
			userid : {
                required : true
            },
			uname : {
				required : true
			}
        }
    });
    $("#btn-add-submit2").on('click', function() {
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
	$("#input-search-uname").on('keydown', function(e) {
		if (e.keyCode == 13) {
			action.loadPageData();
		}

	});

    function timestampToTime(timestamp) {
        var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
        s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
        return Y+M+D+h+m+s;
    }
    function formatname(uname) {
        if((/^1[34578]\d{9}$/.test(uname))) {
            return uname.substr(0,3) + '****' + uname.substr(7)
        } else {
            return uname
        }
    }

   /* $('#addReply').find('button[type=submit]').click(function (t) {
        t.preventDefault();
        action.add1(suggestId)
    })*/

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

                if (0 == that.updateAuth) {
                    $(".table-update").hide();
                    $(".table-manage").hide();
                }

                if (typeof(that.tmplBindEvents) != "object") {
                    that.tmplBindEvents();
                }
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