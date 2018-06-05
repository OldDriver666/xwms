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
    //$('#aaa').html(req_uid + ',' + req_uname)
    if (typeof(req_uid) == "undefined" || typeof(req_uname) == "undefined") {
        aa = 0;
        Util.cookieStorage.setCookie("userId","2000020000");
        Util.cookieStorage.setCookie("userName","anonymous")
    } else {
        Util.cookieStorage.setCookie("userId",req_uid);
        Util.cookieStorage.setCookie("userName",req_uname);
    }
    var tab_html = '<li class="tab-nav active"><a href="feedback.html?uid=' + Util.cookieStorage.getCookie("userId") + '&uname=' + Util.cookieStorage.getCookie("userName") + '"><em class="octicon octicon-book"></em>知识库</a></li><li class="tab-nav"><a href="workOrder.html?uid=' + Util.cookieStorage.getCookie("userId") + '&uname=' + Util.cookieStorage.getCookie("userName") + '"><em class="octicon octicon-comment-discussion"></em>公开问题</a></li>'
    document.getElementById('tab-navs').innerHTML = tab_html;


    var action = {
        //新增数据
        add : function() {
            var url = ctx + "AdminManage/CreateAdmin";
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    toastr.success("添加成功!");
                }else{
                    console.log("添加失败！");
                }
            },function() {
                console.log("服务器异常！")
            });
        },
        //获取所有数据
        loadPageData : function(t) {
            var url = ctx + "boss/wiki/query";
            var data = {
                "type": t
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    //$("#pageTmpl").tmpl(result.data).appendTo('#pageContent' + t);
                }else {
                    console.log("请求出错！");
                }
            },function() {
                console.log("服务器异常！")
            });
        },
        //获取所有数据
        loadPageData1 : function() {
            var url = ctx + "boss/wiki/query";
            var data = {};
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    var html_li = '';
                    var li_arr1 = [];
                    var li_arr2 = [];
                    var li_arr3 = [];
                    var li_arr4 = [];
                    var li_arr5 = [];
                    var li_arr6 = [];
                    for (var i = 0; i < result.data.length; i++) {
                        switch (result.data[i].type) {
                            case 1:
                                html_li = '<li class="article-item">' +
                                    '<a href="feedback-detail.html?id=' + result.data[i].id + '&type=' + result.data[i].type + '">' +
                                    '<em class="octicon octicon-chevron-right"></em>' + result.data[i].title +
                                    '</a>' +
                                    '</li>';
                                li_arr1.push(html_li);
                                break;
                            case 2:
                                html_li = '<li class="article-item">' +
                                    '<a href="feedback-detail.html?id=' + result.data[i].id + '&type=' + result.data[i].type + '">' +
                                    '<em class="octicon octicon-chevron-right"></em>' + result.data[i].title +
                                    '</a>' +
                                    '</li>';
                                li_arr2.push(html_li);
                                break;
                            case 3:
                                html_li = '<li class="article-item">' +
                                    '<a href="feedback-detail.html?id=' + result.data[i].id + '&type=' + result.data[i].type + '">' +
                                    '<em class="octicon octicon-chevron-right"></em>' + result.data[i].title +
                                    '</a>' +
                                    '</li>';
                                li_arr3.push(html_li);
                                break;
                            case 4:
                                html_li = '<li class="article-item">' +
                                    '<a href="feedback-detail.html?id=' + result.data[i].id + '&type=' + result.data[i].type + '">' +
                                    '<em class="octicon octicon-chevron-right"></em>' + result.data[i].title +
                                    '</a>' +
                                    '</li>';
                                li_arr4.push(html_li);
                                break;
                            case 5:
                                html_li = '<li class="article-item">' +
                                    '<a href="feedback-detail.html?id=' + result.data[i].id + '&type=' + result.data[i].type + '">' +
                                    '<em class="octicon octicon-chevron-right"></em>' + result.data[i].title +
                                    '</a>' +
                                    '</li>';
                                li_arr5.push(html_li);
                                break;
                            case 6:
                                html_li = '<li class="article-item">' +
                                    '<a href="feedback-detail.html?id=' + result.data[i].id + '&type=' + result.data[i].type + '">' +
                                    '<em class="octicon octicon-chevron-right"></em>' + result.data[i].title +
                                    '</a>' +
                                    '</li>';
                                li_arr6.push(html_li);
                                break;
                        }
                    }
                    var html_li1 = '<div class="collection-item">' +
                        '<a href="feedback-class.html?id=1" class="title">入门指南</a>' +
                        '<ul class="article-list">' + li_arr1.join('') + '</ul></div>';
                    var html_li2 = '<div class="collection-item">' +
                        '<a href="feedback-class.html?id=2" class="title">常见问题</a>' +
                        '<ul class="article-list">' + li_arr2.join('') + '</ul></div>';
                    var html_li3 = '<div class="collection-item">' +
                        '<a href="feedback-class.html?id=3" class="title">使用技巧</a>' +
                        '<ul class="article-list">' + li_arr3.join('') + '</ul></div>';
                    var html_li4 = '<div class="collection-item">' +
                        '<a href="feedback-class.html?id=4" class="title">高级版相关</a>' +
                        '<ul class="article-list">' + li_arr4.join('') + '</ul></div>';
                    var html_li5 = '<div class="collection-item">' +
                        '<a href="feedback-class.html?id=5" class="title">账号相关</a>' +
                        '<ul class="article-list">' + li_arr5.join('') + '</ul></div>';
                    var html_li6 = '<div class="collection-item">' +
                        '<a href="feedback-class.html?id=6" class="title">关于我们</a>' +
                        '<ul class="article-list">' + li_arr6.join('') + '</ul></div>';
                    $('#collection-list').html(html_li1 + html_li2 + html_li3 + html_li4 + html_li5 + html_li6)
                }else {
                    console.log("请求出错！");
                }
            },function() {
                console.log("服务器异常！")
            });
        },
        //搜索数据
        loadPageData2 : function(key) {
            var url = ctx + "boss/wiki/query";
            var data = {
                "title": key
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.code == ReturnCode.SUCCESS){
                    $("#search_results").empty();
                    $("#search_results").show()
                    $("#pageTmpl2").tmpl(result.data).appendTo('#search_results');
                }else {
                    console.log("请求出错！");
                }
            },function() {
                console.log("服务器异常！")
            });
        },
        //编辑数据
        edit : function() {
            var url = ctx + "AdminManage/ModifyAdminInfo";
            var data = new Object();
            data.UserName = userName;
            data.AuthenticCode = token_value;
            data.DepartId = parseInt(depart_id);
            data.RoleLevel = parseInt(role_level);
            data.AdminId = parseInt($("#input-id").val());
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    toastr.success("编辑成功!");
                    action.loadPageData();
                }
            });
        },
        //删除数据
        deleteItem : function(id, name) {
            if (confirm("删除后不可恢复，确定删除" + name + "？")) {
                var url = ctx + "AdminManage/DeleteAdmin";
                var data = new Object();
                data.RoleLevel = parseInt(role_level);
                data.AdminId = parseInt(id);
                Util.ajaxLoadData(url,data,"POST",true,function(result) {
                    if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
                        action.loadPageData();
                    }
                });
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
/*    action.loadPageData(1);
    action.loadPageData(2);
    action.loadPageData(3);
    action.loadPageData(4);
    action.loadPageData(5);
    action.loadPageData(6);*/
    action.loadPageData1();

    $("#auth-modal-close").on('click', function() {
        modalClose();
    });

});
