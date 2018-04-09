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
    var typeId = parseInt(Request["id"]);

    var action = {
        //获取所有数据
        loadPageData : function(typeId) {
            var url = ctx + "boss/wiki/query";
            var data = {
                "type": typeId
            };
            if (typeId === undefined || typeId === null || typeId === '' || isNaN(typeId)) {
                console.log('无记录')
            } else {
                Util.ajaxLoadData(url,data,"POST",true,function(result) {
                    if(result.code == ReturnCode.SUCCESS){
                        $("#pageTmpl").tmpl(result.data).appendTo('#pageContent');
                    }else {
                        console.log("请求出错！");
                    }
                },function() {
                    console.log("服务器异常！")
                });
            }
        }
    };
    window.action = action;
    action.loadPageData(typeId);
})
