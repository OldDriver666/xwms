$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var role_level = Util.cookieStorage.getCookie("userLevel");

	var action = {
		//获取用户和设备在线及注册人数的数据
		getUserDevData : function() {
            var url = ctx + "DateCountManage/GetDeviceCountInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "RoleLevel":parseInt(role_level),
                "DepartId":parseInt(depart_id)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $("#pageUserOnline").tmpl(result.XWInfo).appendTo('#xwUserOnlineInfo');
                    $("#pageUserReg").tmpl(result.XWInfo).appendTo('#xwUserRegInfo');
                    $("#pageDevOnline").tmpl(result.DeviceInfo).appendTo('#devUserOnlineInfo');
                    $("#pageDevActive").tmpl(result.DeviceInfo).appendTo('#devUserActiveInfo');
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！");
            });
		},

        //获取每天注册用户统计的数据
        getUseRegData : function(startTime,endTime,days) {
            var v_y = startTime.substr(0,4);
            var v_m = startTime.substr(5,2);
            var v_d = startTime.substr(8,2);
            var url = ctx + "DateCountManage/GetRegisterCountInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "RoleLevel":parseInt(role_level),
                "DepartId":parseInt(depart_id),
                "StartTime":startTime,
                "EndTime":endTime,
                "Days":days
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){

                    var data_arr = [];
                    var data_arr1 = [];
                    var dayCount = result.DeviceInfo;
                    for(i=0; i<dayCount.length; i++){

                        var startDay = new Date(v_y,v_m,v_d);
                        var paramDay = 24 * 3600 * 1000 * i;
                        var toolTipName = new Date(+startDay + paramDay);
                        var xName = [toolTipName.getFullYear(), toolTipName.getMonth(), toolTipName.getDate()].join('/');

                        var name = dayCount[i].Days;
                        var value = dayCount[i].RegisterCount;
                        var value1 = dayCount[i].RateOfIncrease;

                        var list_item = '{"name":"'+toolTipName.toString()+'","value":["'+xName+'",'+value+']}';
                        var list_item1 = '{"name":"'+toolTipName.toString()+'","value":["'+xName+'",'+value1+']}';

                        data_arr.push(list_item);
                        data_arr1.push(list_item1);
                    };

                    var data_str = "["+data_arr+"]";
                    var data = JSON.parse(data_str);
                    var data_str1 = "["+data_arr1+"]";
                    var data1 = JSON.parse(data_str1);

                    if(role_level == 3){
                        option = {
                            title: {
                                text: '用户注册数量',
                                x:'center'
                            },
                            tooltip: {
                                trigger: 'axis',
                                formatter: function (params) {
                                    params = params[0];
                                    var date = new Date(params.name);
                                    return date.getFullYear() + '/' + date.getMonth() + '/' + date.getDate() + ' : ' + params.value[1];
                                },
                                axisPointer: {
                                    animation: false
                                }
                            },
                            xAxis: {
                                type: 'time',
                                splitLine: {
                                    show: false
                                }
                            },
                            yAxis: {
                                type: 'value',
                                boundaryGap: [0, '100%'],
                                splitLine: {
                                    show: false
                                }
                            },
                            series: [{
                                name: '用户注册数量',
                                type: 'line',
                                showSymbol: true,
                                hoverAnimation: false,
                                smooth: true,
                                data: data
                            }]
                        };
                        userRegChart.setOption(option);

                        option1 = {
                            title: {
                                text: '用户注册增长率',
                                x:'center'
                            },
                            tooltip: {
                                trigger: 'axis',
                                formatter: function (params) {
                                    params = params[0];
                                    var date = new Date(params.name);
                                    return date.getFullYear() + '/' + date.getMonth() + '/' + date.getDate() + ' : ' + params.value[1]*100+"%";
                                },
                                axisPointer: {
                                    animation: false
                                }
                            },
                            xAxis: {
                                type: 'time',
                                splitLine: {
                                    show: false
                                }
                            },
                            yAxis: {
                                type: 'value',
                                boundaryGap: [0, '100%'],
                                axisLabel: {
                                    formatter: function (val) {
                                        return val * 100 + '%';
                                    }
                                },
                                splitLine: {
                                    show: false
                                }
                            },
                            series: [{
                                name: '用户注册增长率',
                                type: 'line',
                                showSymbol: true,
                                hoverAnimation: false,
                                smooth: true,
                                data: data1
                            }]
                        };
                        userGrowthChart.setOption(option1);
                    }else{
                        option = {
                            title: {
                                text: '设备激活数量',
                                x:'center'
                            },
                            tooltip: {
                                trigger: 'axis',
                                formatter: function (params) {
                                    params = params[0];
                                    var date = new Date(params.name);
                                    return date.getFullYear() + '/' + date.getMonth() + '/' + date.getDate() + ' : ' + params.value[1];
                                },
                                axisPointer: {
                                    animation: false
                                }
                            },
                            xAxis: {
                                type: 'time',
                                splitLine: {
                                    show: false
                                }
                            },
                            yAxis: {
                                type: 'value',
                                boundaryGap: [0, '100%'],
                                splitLine: {
                                    show: false
                                }
                            },
                            series: [{
                                name: '设备激活数量',
                                type: 'line',
                                showSymbol: true,
                                hoverAnimation: false,
                                smooth: true,
                                data: data
                            }]
                        };
                        userRegChart.setOption(option);

                        option1 = {
                            title: {
                                text: '设备激活增长率',
                                x:'center'
                            },
                            tooltip: {
                                trigger: 'axis',
                                formatter: function (params) {
                                    params = params[0];
                                    var date = new Date(params.name);
                                    return date.getFullYear() + '/' + date.getMonth() + '/' + date.getDate() + ' : ' + params.value[1]+"%";
                                },
                                axisPointer: {
                                    animation: false
                                }
                            },
                            xAxis: {
                                type: 'time',
                                splitLine: {
                                    show: false
                                }
                            },
                            yAxis: {
                                type: 'value',
                                boundaryGap: [0, '100%'],
                                axisLabel: {
                                    formatter: function (val) {
                                        return val + '%';
                                    }
                                },
                                splitLine: {
                                    show: false
                                }
                            },
                            series: [{
                                name: '设备激活增长率',
                                type: 'line',
                                showSymbol: true,
                                hoverAnimation: false,
                                smooth: true,
                                data: data1
                            }]
                        };
                        userGrowthChart.setOption(option1);
                    }

                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

        },
        //获取注册用户客户端类型的数据
        getClientTypeData : function() {
            var url = ctx + "DateCountManage/GetClientCountInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "RoleLevel":parseInt(role_level),
                "DepartId":parseInt(depart_id)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    //客户端类型信息
                    var data_arr = [];
                    for(i=0; i<result.ClientTypeCount.length; i++){
                        var name = result.ClientTypeCount[i].TypeName;
                        var value = result.ClientTypeCount[i].RegisterCount;
                        var list_item = '{"name":"'+name+'","value":"'+value+'","color":"#4'+i+'72a7"}';
                        data_arr.push(list_item);
                    };
                    var data_str = "["+data_arr+"]";
                    var data = JSON.parse(data_str);

                    //地域信息
                    var data_arr1 = [];
                    for(i=0; i<result.ClientPlaceCount.length; i++){
                        var name = result.ClientPlaceCount[i].AddressName;
                        //var name_filter = name.substr(0,name.length-1);
                        var value = result.ClientPlaceCount[i].RegisterCount;
                        var list_item1 = '{"name":"'+name+'","value":'+value+'}';
                        data_arr1.push(list_item1);
                    };
                    var data_str1 = "["+data_arr1+"]";
                    var data1 = JSON.parse(data_str1);

                    //用户客户端类型
                    option = {
                        title : {
                            text: '用户客户端类型',
                            x:'center'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        series : [
                            {
                                name: '用户客户端类型',
                                type: 'pie',
                                radius : '55%',
                                center: ['50%', '60%'],
                                data: data,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    userClientChart.setOption(option);


                    //用户地域分布
                    option1 = {
                        title: {
                            text: '用户地域分布',
                            x:'center'
                        },
                        tooltip: {
                            trigger: 'item'
                        },
                        visualMap: {
                            min: 0,
                            max: 2500,
                            left: 'left',
                            top: 'bottom',
                            text: ['高','低'], // 文本，默认为数值文本
                            calculable: true
                        },
                        toolbox: {
                            show: true,
                            orient: 'vertical',
                            left: 'right',
                            top: 'center',
                            feature: {
                                dataView: {readOnly: false},
                                restore: {},
                                saveAsImage: {}
                            }
                        },
                        series: [
                            {
                                name: '用户地域分布',
                                type: 'map',
                                mapType: 'china',
                                roam: false,
                                label: {
                                    normal: {
                                        show: false
                                    },
                                    emphasis: {
                                        show: false
                                    }
                                },
                                data:data1
                            }
                        ]
                    };

                    userAreaChart.setOption(option1);

                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
        }

	};
	window.action = action;
	action.getUserDevData();
	action.getClientTypeData();
	//action.refreshOnTime();


    var nowTime = getNowFormatDate();//当前日期
    var init_days = 3;//初始时的天数
    var init_starDate = changeDate(nowTime, init_days);//初始时的开始日期
    action.getUseRegData(init_starDate,nowTime,init_days);//初始统计数据
    //点击日期选择天数，显示相应天数的数据
    $(".group li").on("click",function(){
        $(this).addClass("curr").siblings().removeClass("curr");
        var days = $(this).data("days");
        var starDate = changeDate(nowTime, days);
        action.getUseRegData(starDate,nowTime,days);
    });
    //计算当前日期减去天数=目标日期
    function changeDate(date, days) {
        var d = new Date(date);
        d.setDate(d.getDate() - days);
        var month = d.getMonth() + 1;
        var day = d.getDate();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        var starDate = d.getFullYear() + "-" + month + "-" + day;
        return starDate;
    }
    //获取当前时间，格式YYYY-MM-DD
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }


	/*$("#btn-search").on('click', function() {
        action.loadPageData();
	});
	$("#input-search-txt").on('keydown', function(e) {
        if (e.keyCode == 13) {
            action.loadPageData();
        }

	});*/
});