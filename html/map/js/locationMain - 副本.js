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

    var depart_id = Request["id"];
    var company_id = Util.cookieStorage.getCookie("companyId");

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
            var url =  ctx + "boss/user/location";
            var data = {
                company_id: parseInt(company_id),
                depart_id: parseInt(depart_id)
            }
            Util.ajaxLoadData(url,data,1,"POST",true,function(result) {
                if(result.code == 0){
                    console.log(result.data)
                    var lnglats = [];
                    var userList
                    userList = result.data.user_list;
                    var sexList = []
                    document.getElementById('allPeopleNum').innerHTML = result.data.total_cnt
                    document.getElementById('onlineNum').innerHTML = result.data.online_cnt
                    for(var n = 0; n<userList.length; n++){
                        var iconUrl = userList[n].online_status === 0 ? 'http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png' : 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
                        var lnglat = {icon:iconUrl, position:[userList[n].location_y, userList[n].location_x]}
                        lnglats.push(lnglat)
                        if(userList[n].sex === 1){
                            sexList.push('男')
                        }else{
                            sexList.push('女')
                        }
                    }

                    // 加载地图定位点
                    //var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
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

                        //构建信息窗体中显示的内容
                        //var info = [];
                        //info.push(userList[i].uid);

                        // 点击点标记存储当前用户id
                        marker.content = userList[i].uid;
                        marker.on('click', action.markerClick);
                    }
                    map.setFitView()



                }else if(result.code = 10041){
                    // alert("无数据记录")
                }
            },function() {
                alert("数据加载失败，请稍后再试！")
            });
        },
        markerClick: function(e) {
            // 根据id查询当前用户详细信息
            //loadDetailData(e.target.content);
            var infoHtml = action.loadDetailData(e.target.content);
            console.log('info:' + infoHtml)
            var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
            infoWindow.setContent(infoHtml);
            infoWindow.open(map, e.target.getPosition());
        },
        //获取所有数据
        loadDetailData: function(uId) {
            var url = ctx + "boss/user/detail";
            var data = {
                company_id: parseInt(company_id),
                depart_id: parseInt(depart_id),
                user_id: parseInt(uId)
            }
            Util.ajaxLoadData(url, data, 1, "POST", true, function (result) {
                if (result.code == 0) {
                    console.log(result)
                    var datas = result.data

                    //构建信息窗体中显示的内容
                    var info2 = [];
                    info2.push('<div><div>用户ID：' + datas.uid);
                    info2.push('<b>用户名称：' + datas.nick + '</b>');
                    info2.push('性别：' +  + ' 身高：' + datas.height + ' 体重：' +  datas.weight + "</div></div>");
                    aa(datas.uid)
                    /*var lnglats = []
                    lnglats.push(location_x)
                    lnglats.push(location_y)
                    console.log(lnglats)
                    var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
                    marker = new AMap.Marker({
                        map: map,
                        position: lnglats
                    });

                    //构建信息窗体中显示的内容
                    info2.push('<div><div>用户ID：' + datas.uid);
                    info2.push('<b>用户名称：' + datas.nick + '</b>');
                    info2.push('性别：' +  + ' 身高：' + datas.height + ' 体重：' +  datas.weight + "</div></div>");

                    marker.content = info2.join("<br/>");
                    marker.on('click', markerClick);
                    // marker.emit('click', {target: marker});

                    function markerClick(e) {
                        infoWindow.setContent(e.target.content);
                        infoWindow.open(map, e.target.getPosition());
                    }*/
                }
            });
            function aa(a) {
                var x = a
                function bb() {
                    console.log(x)
                }
                return bb
            }
            var cc = aa();
            console.log(cc())
            return cc()
        }
    };

    window.action = action;
    action.loadPageData();
});