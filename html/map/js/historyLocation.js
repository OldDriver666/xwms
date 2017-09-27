/**
 * Created by Administrator on 2017/9/8 0008.
 */
$(function() {
    //获取地址栏传递的参数
    function Request(strName){
        var strHref = "";
        var intPos = strHref.indexOf("?");
        var strRight = strHref.substr(intPos + 1);
        var arrTmp = strRight.split("&");
        for(var i = 0; i < arrTmp.length; i++){
            var arrTemp = arrTmp[i ].split("=");
            if(arrTemp[0].toUpperCase() == strName.toUpperCase()) return arrTemp[1];
        };
        return "";
    };
    var url=location.search;
    var Request = new Object();
    if(url.indexOf("?")!=-1) {
        var str = url.substr(1)　//去掉?号
        strs = str.split("&");
        for(var i=0;i<strs.length;i++){
            Request[strs[i ].split("=")[0]]=unescape(strs[ i].split("=")[1]);
        }
    };

    var depart_id = Request["dId"];
    var user_id = Request["uId"];
    var company_id = Request["cId"];
    var dateTime = Request["time"];

    var map = new AMap.Map("map-container", {
        resizeEnable: true,
        center: [116.397428, 39.90923],
        zoom: 13
    });
    AMap.plugin(['AMap.ToolBar','AMap.Scale'], function(){
        map.addControl(new AMap.ToolBar({
            position: 'LT',
            offset: new AMap.Pixel(0, 40)
        }));
        map.addControl(new AMap.Scale());
    });
    AMapUI.loadUI(['control/BasicControl'], function(BasicControl) {
        // 卫星地图、路况地图等图层切换控件
        map.addControl(new BasicControl.LayerSwitcher({
            position: {top:'50px', right:'20px'}
        }));
    });

    var action = {
        //获取所有数据
        loadPageData: function() {
            var url = ctx + "boss/user/history";
            var data = {
                company_id: parseInt(company_id),
                user_id: parseInt(user_id),
                depart_id: parseInt(depart_id),
                query_date: dateTime
            }
            Util.ajaxLoadData(url, data, 1, "POST", true, function (result) {
                if (result.code == 0) {
                    var lnglats = [];
                    var userList = result.data;
                    for(var n = 0; n<userList.length; n++) {
                        var iconUrl = userList[n].online_status === 0 ? 'http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png' : 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
                        var lnglat = {icon: iconUrl, position: [userList[n].lng, userList[n].lat]}
                        lnglats.push(lnglat)
                    }

                    for (var i = 0, marker; i < lnglats.length; i++) {
                        marker = new AMap.Marker({
                            map: map,
                            position: lnglats[i].position,
                            icon: new AMap.Icon({
                                size: new AMap.Size(40, 50),  //图标大小
                                image: lnglats[i].icon,
                                imageOffset: new AMap.Pixel(0, 0)
                            })
                        });
                    }
                    map.setFitView();
                }
            },function() {
                alert("数据加载失败，请稍后再试！")
            });
        }
    };

    window.action = action;
    action.loadPageData();
});