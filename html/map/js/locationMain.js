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
    setTimeout(function () {
        AMapUI.loadUI(['control/BasicControl'], function(BasicControl) {
            // 卫星地图、路况地图等图层切换控件
            map.addControl(new BasicControl.LayerSwitcher({
                position: {top:'50px', right:'20px'}
            }));
        });
    }, 500)

    var action = {
        //获取所有数据
        loadPageData: function() {
            var today_date = action.getDateStr(0);
            var yesterday_date = action.getDateStr(-1);
            var before_yesterday_date = action.getDateStr(-2);

            var url =  ctx + "boss/user/patient";
            var data = {
                company_id: parseInt(company_id),
                depart_id: parseInt(depart_id)
            }
            Util.ajaxLoadData(url,data,1,"POST",true,function(result) {
                if(result.code == 0){
                    if (result.data != null){
                        var userList = result.data.user_list;

                        document.getElementById('allPeopleNum').innerHTML = result.data.total_cnt
                        document.getElementById('onlineNum').innerHTML = result.data.online_cnt


                        AMap.plugin('AMap.Geocoder', function() {
                            var geocoder = new AMap.Geocoder({
                                radius: 1000,
                                extensions: 'all'
                            })
                            var lnglats = [];

                            // 加载地图定位点
                            var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
                            var cluster, markers = []
                            for(var n = 0; n<userList.length; n++){
                                var addrs = []
                                var iconUrl = userList[n].online_status === 0 ? 'http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png' : 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
                                var lnglat = {icon:iconUrl, position:[userList[n].location_y, userList[n].location_x]}
                                lnglats.push(lnglat)

                                marker = new AMap.Marker({
                                    map: map,
                                    position: lnglats[n].position,
                                    icon: new AMap.Icon({
                                        size: new AMap.Size(40, 50),  //图标大小
                                        image: lnglats[n].icon,
                                        imageOffset: new AMap.Pixel(0, 0)
                                    })
                                });

                                //聚合点
                                markers.push(marker)

                                //构建信息窗体中显示的内容
                                var info = [];
                                var onlineStatus = userList[n].online_status == 1 ? '在线' : '不在线'
                                var xinhao = userList[n].sq > 60 ? '强' : '弱';
                                info.push('<div  class="infoWindow-wrap"><div class="infoWindow-item-wrap"><span class="infoWindow-item">名称：</span><span class="infoWindow-item-cont">' + userList[n].nick + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">身份证号码：</span><span class="infoWindow-item-cont">' + userList[n].phone + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">小位号：</span><span class="infoWindow-item-cont" style="color:#2B7AE3">' + userList[n].account + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">紧急联系方式：</span><span class="infoWindow-item-cont" style="color:#2B7AE3">' + userList[n].sos_phone + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">常住地址：</span><span class="infoWindow-item-cont">' + userList[n].province + '' + userList[n].city + userList[n].address + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">实时位置：</span><span class="infoWindow-item-cont" id="addrs"></span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">在线状态：</span><span class="infoWindow-item-cont" style="color:#2B7AE3">' + onlineStatus + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">电量、信号：</span><span class="infoWindow-item-cont" style="color:#2B7AE3">' + userList[n].battery + '% &nbsp;' + xinhao + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">历史轨迹：</span><span class="infoWindow-item-cont" style="color:#2B7AE3"><a href="historyLocation.html?dId=' + depart_id + '&uId=' + userList[n].uid + '&cId=' + company_id + '&time=' + today_date +'" target="_blank">今天</a> <a href="historyLocation.html?dId=' + depart_id + '&uId=' + userList[n].uid + '&cId=' + company_id + '&time=' + yesterday_date + '" target="_blank">昨天</a> <a href="historyLocation.html?dId=' + depart_id + '&uId=' + userList[n].uid + '&cId=' + company_id + '&time=' + before_yesterday_date + '" target="_blank">前天</a></span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">运动计步：</span><span class="infoWindow-item-cont" style="color:#2B7AE3">' + userList[n].step_cnt + '步</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">监护群：</span><span class="infoWindow-item-cont sendMsg"><a href="javascript:;">发送消息</a></span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">身体状况：</span><span class="infoWindow-item-cont">' + userList[n].bodyStatus + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">片区民警：</span><span class="infoWindow-item-cont">' + userList[n].policeInfo + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">直系亲属：</span><span class="infoWindow-item-cont">' + userList[n].cognate + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">监护人：</span><span class="infoWindow-item-cont">' + userList[n].guardian + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-item">有奖监护：</span><span class="infoWindow-item-cont">' + userList[n].reward + '</span></div>');
                                info.push('<div class="infoWindow-item-wrap"><span class="infoWindow-updata"><a href="javascript:;">更新数据</a></span></div></div>');

                                // 点击点标记打开信息窗口
                                marker.content = info.join("");
                                marker.on('click', markerClick);

                            }
                            //聚合点
                            cluster = new AMap.MarkerClusterer(map, markers,{gridSize:80});
                            function markerClick(e) {
                                infoWindow.setContent(e.target.content);
                                geocoder.getAddress(e.target.getPosition(), function (status, result) {
                                    if (status === 'complete' && result.info === 'OK') {
                                        document.getElementById('addrs').textContent = result.regeocode.formattedAddress
                                        addrs.push(result.regeocode.formattedAddress)
                                    } else {
                                        document.getElementById('addrs').textContent = "未知"
                                    }
                                })

                                infoWindow.open(map, e.target.getPosition());
                            }

                            map.setFitView()

                        })
                    } else{
                        //toastr.error('加载失败！');
                    }
                } else {
                    toastr.error(result.msg);
                }
            },function(errorMsg) {
                alert(errorMsg);
            });
        },
        getDateStr : function(AddDayCount) {
            var dd = new Date();
            dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
            var y = dd.getFullYear();
            var m = dd.getMonth()+1;//获取当前月份的日期
            var d = dd.getDate();
            if(m < 10)
                m = "0" + m;
            if(d < 10)
                d = "0" + d;
            return y + "-" + m + "-" + d;
        }
    };

    window.action = action;
    action.loadPageData();
});

