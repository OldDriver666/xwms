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
                    var userList = result.data.user_list;

                    document.getElementById('allPeopleNum').innerHTML = result.data.total_cnt
                    document.getElementById('onlineNum').innerHTML = result.data.online_cnt

                    AMap.plugin('AMap.Geocoder', function() {
                        var geocoder = new AMap.Geocoder({
                            radius: 1000,
                            extensions: 'all'
                        })
                        var lnglats = [];

                        for(var n = 0; n<userList.length; n++){
                            var addrs = []
                            var iconUrl = userList[n].online_status === 0 ? 'http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png' : 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
                            var lnglat = {icon:iconUrl, position:[userList[n].location_y, userList[n].location_x]}
                            lnglats.push(lnglat)

                            //alert(lnglats[n].position)
                            //setTimeout(function () {},1000)


                                    geocoder.getAddress(lnglats[n].position, function (status, result) {
                                        if (status === 'complete' && result.info === 'OK') {
                                            addrs.push(result.regeocode.formattedAddress)
                                        } else {
                                            addrs.push('未知')
                                        }

                                        // 加载地图定位点
                                        var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
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
                                            var info = [];
                                            var onlineStatus = userList[i].online_status == 1 ? '在线' : '不在线'
                                            var xinhao = userList[i].sq > 60 ? '强' : '弱';
                                            info.push('<div  class="infoWindow-wrap"><div class="infoWindow-item-wrap"><span class="infoWindow-item">名称：</span>' + userList[i].nick + '</div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">身份证号码：</span><span>' + userList[i].phone + '</span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">小位号：</span><span style="color:#2B7AE3">' + userList[i].account + '</span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">紧急联系方式：</span><span style="color:#2B7AE3">' + userList[i].sos_phone + '</span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">常住地址：</span><span>' + userList[i].province + '' + userList[i].city + userList[i].address + '</span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">实时位置：</span><span>' + addrs[i] + '</span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">在线状态：</span><span style="color:#2B7AE3">' + onlineStatus + '</span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">电量、信号：</span><span style="color:#2B7AE3">' + userList[i].battery + '% &nbsp;' + xinhao + '</span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">历史轨迹：</span><span style="color:#2B7AE3"><a href="historyLocation.html?dId=' + depart_id + '&uId=' + userList[i].uid + '&cId=' + company_id + '&time=20170908" target="_blank">今天</a> <a href="historyLocation.html?dId=' + depart_id + '&uId=' + userList[i].uid + '&cId=' + company_id + '&time=20170907" target="_blank">昨天</a> <a href="historyLocation.html?dId=' + depart_id + '&uId=' + userList[i].uid + '&cId=' + company_id + '&time=20170906" target="_blank">前天</a></span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">运动计步：</span><span style="color:#2B7AE3">' + userList[i].step_cnt + '步</span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">监护群：</span><span class="sendMsg"><a href="javascript:;">发送消息</a></span></div>');
                                            info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-updata"><a href="javascript:;">更新数据</a></span></div></div>');

                                            // 点击点标记存储当前用户id
                                            marker.content = info.join("");
                                            marker.on('click', markerClick);
                                        }
                                        function markerClick(e) {
                                            infoWindow.setContent(e.target.content);
                                            infoWindow.open(map, e.target.getPosition());
                                        }

                                        map.setFitView()
                                    })

                        }


                    })

                }else if(result.code = 10041){
                    // alert("无数据记录")
                }
            },function() {
                alert("数据加载失败，请稍后再试！")
            });
        }
    };

    window.action = action;
    action.loadPageData();
});