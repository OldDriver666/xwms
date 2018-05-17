$(function () {
    var uId = Util.cookieStorage.getCookie("userId");
    var uname = Util.cookieStorage.getCookie("userName");
    var action = {
        //获取所有数据
        loadPageData : function(uname) {
            if (uname === "" || uname === "undefined") {
                $('.nothing').css('display', 'block')
                console.log('无记录')
            } else {
                var url = ctx + "boss/suggest/query";
                var data = {
                    "page_no": 1,
                    "page_size": 10,
                    "param": {
                        "type": 1,
                        "uname": uname,
                        "userId": parseInt(uId),
                        "title": ""
                    }
                };
                Util.ajaxLoadData(url,data,"POST",true,function(result) {
                    if(result.code == ReturnCode.SUCCESS){
                        var myData = result.data.result;
                        if (myData.length > 0) {
                            $('.nothing').css('display', 'none')
                            $("#pageTmpl").tmpl(myData).appendTo('#pageContent');
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
    action.loadPageData(uname);

})
