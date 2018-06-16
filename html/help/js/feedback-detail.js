$(function () {
    var url=location.search;
    var Request = new Object();
    if(url.indexOf("?")!=-1) {
        var str = url.substr(1)　//去掉?号
        strs = str.split("&");
        for(var i=0;i<strs.length;i++){
            Request[strs[i ].split("=")[0]]=unescape(strs[ i].split("=")[1]);
        }
    };
    var articleId = parseInt(Request["id"]);
    var articleType = parseInt(Request["type"]);

    var action = {
        //获取所有数据
        loadPageData : function(articleId) {
            var url = ctx + "boss/wiki/query";
            var data = {
                "id": articleId
            };
            if (articleId === undefined || articleId === null || articleId === '' || isNaN(articleId)) {
                console.log('无记录')
            } else {
                Util.ajaxLoadData(url,data,"POST",true,function(result) {
                    if(result.code == ReturnCode.SUCCESS){
                        $("#upvote").find('number').html(result.data[0].helpful)
                        $("#downvote").find('number').html(result.data[0].nohelp)
                        $("#pageTmpl").tmpl(result.data).appendTo('#pageContent');
                    }else {
                        console.log("请求出错！");
                    }
                },function() {
                    console.log("服务器异常！")
                });
            }
        },
        //获取所有数据
        loadPageData2 : function(articleId) {
            var url = ctx + "boss/wiki/query";
            var data = {
                "id": articleId
            };
            if (articleId === undefined || articleId === null || articleId === '' || isNaN(articleId)) {
                console.log('无记录')
            } else {
                Util.ajaxLoadData(url,data,"POST",true,function(result) {
                    if(result.code == ReturnCode.SUCCESS){
                        $("#upvote").find('number').html(result.data[0].helpful)
                        $("#downvote").find('number').html(result.data[0].nohelp)
                    }else {
                        console.log("请求出错！");
                    }
                },function() {
                    console.log("服务器异常！")
                });
            }
        },
        //编辑数据
        edit : function(count) {
            var url = ctx + "boss/wiki/update";
            var data = {
                "id": articleId,
                "helpful": parseInt(count) + 1
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#upvote").addClass("voted");
                    $("#downvote").addClass("voted");
                    action.loadPageData2(articleId);
                }
            });
        },
        //编辑数据
        edit2 : function(count) {
            var url = ctx + "boss/wiki/update";
            var data = {
                "id": articleId,
                "nohelp": parseInt(count) + 1
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#upvote").addClass("voted");
                    $("#downvote").addClass("voted");
                    action.loadPageData2(articleId);
                }
            });
        }
    };
    window.action = action;
    action.loadPageData(articleId);

    $("#upvote").click(function () {
        var val = $(this).find('number').text()
        if ($(this).hasClass("voted")) {
        } else {
            action.edit(val);
        }
    })
    $("#downvote").click(function () {
        var val = $(this).find('number').text()
        if ($(this).hasClass("voted")) {
        } else {
            action.edit2(val);
        }
    })
})
