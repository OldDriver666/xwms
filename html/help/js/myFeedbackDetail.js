$(function () {
    var userId = Util.cookieStorage.getCookie("userId") === "" || isNaN(Util.cookieStorage.getCookie("userId")) || typeof(Util.cookieStorage.getCookie("userId")) == "undefined" ? 2000020000 : Util.cookieStorage.getCookie("userId");
    var userName = Util.cookieStorage.getCookie("userName") === "" || typeof(Util.cookieStorage.getCookie("userName")) == "undefined" ? "anonymous" : Util.cookieStorage.getCookie("userName")
    var fqType = 1;

    // 获取url参数
    var url=location.search;
    var Request = new Object();
    if(url.indexOf("?")!=-1) {
        var str = url.substr(1)　//去掉?号
        strs = str.split("&");
        for(var i=0;i<strs.length;i++){
            Request[strs[i ].split("=")[0]]=unescape(strs[ i].split("=")[1]);
        }
    };
    var suggestId = Request["sid"];
    var id = parseInt(Request["id"])
    var uname = Request["uname"];
    var user_id = null;

    var action = {
        //新增数据
        add : function() {
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
                    var fliterSrc = imgEle[i].src
                    if(fliterSrc.indexOf("?") != -1){
                        imgUrlArr.push(fliterSrc.split("?")[0])
                    } else {
                        imgUrlArr.push(fliterSrc)
                    }
                }
                imgUrl = imgUrlArr.join(',')
            }

            if (suggestId === '' || suggestId === undefined || suggestId === null) {
                toastr.error("提交失败，请稍后再试!");
            } else if ($('textarea[name=content]').val().replace(/^\s+|\s+$/gm,'') === '') {
                toastr.error("回复内容不能为空!");
            } else if ($('textarea[name=content]').val().length > 200) {
                toastr.error("回复内容已超过了字数限制");
            } else {
                var url = ctx + "boss/suggest/add";
                var data = {
                    "userId": parseInt(userId),
                    "uname": userName,
                    "title": "",
                    "type": fqType, //0-公开 1-私有
                    "content": $('textarea[name=content]').val(),
                    "contact": "",
                    "pictures": imgUrl,
                    "suggestId": suggestId
                };
                Util.ajaxLoadData(url,data,"POST",true,function(result) {
                    if (result.code == ReturnCode.SUCCESS) {
                        toastr.success("添加成功!");
                        $('textarea[name=content]').val('');
                        $('#file_list .attachment2-item').remove();
                        action.loadPageData();
                    } else {
                        console.log("添加失败！");
                    }
                },function() {
                    console.log("服务器异常！")
                });
            }
        },
        loadPageData : function() {
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
                Util.ajaxLoadData(url,data,"POST",true,function(result) {
                    if(result.code == ReturnCode.SUCCESS){
                        var myData = result.data.result
                        user_id = myData[0].userId

                        //判断工单状态显示隐藏内容status == 2 当前为关闭状态
                        if (myData[0].status == 2) {
                            $('#addReply').hide();
                            $('.closeFeedbackInfo').show();
                            $('.closeFeedbackInfo').find('em').html(timestampToTime(myData[0].updated))
                        } else {
                            $('#addReply').show();
                            $('.closeFeedbackInfo').hide();
                        }

                        //判断是公开工单还是私有工单0:公开、1：私有
                        if (myData[0].type == 0) {
                            $('#publicToMyFeedback').show();
                        } else {
                            $('#publicToMyFeedback').hide();
                        }

                        //内容区
                        var imgStr = myData[0].pictures;
                        var htmlImg = ''
                        var htmlImgList = [];
                        $(".ticket .title").find('h2').html(myData[0].title);
                        $(".ticket .title").find('.time').html(timestampToTime(myData[0].created));
                        $(".ticket .markdown-body").find('p').html(myData[0].content);
                        if (imgStr != '') {
                            var imgArr = imgStr.split(',');
                            for (var j = 0; j<imgArr.length; j++) {
                                htmlImg = '<div class="attachment-item"><a href="' + imgArr[j] + '" target="_blank"><img src="' + imgArr[j] + '" /></a></div>'
                                htmlImgList.push(htmlImg)
                            }
                        }
                        $(".ticket .attachment").html(htmlImgList.join(''));

                        //回复区
                        fqType = result.data.result[0].type
                        $(".ticket .replyNum").html(result.data.result.length - 1)
                        $('#pageContent').empty();
                        var html = '';
                        var imgListArr = []
                        for (var n = 1; n < result.data.result.length; n++) {
                            if (result.data.result[n].pictures === '' && typeof result.data.result[n].pictures === 'string' || result.data.result[n].pictures === null) {
                                result.data.result[n].pictures = ''
                            } else {
                                var picArr = result.data.result[n].pictures.split(',');
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
                                '<img src="../img/none.png" />' +
                                '</div>' +
                                '<span class="author">' + formatname(myData[i].uname) + '</span>' +
                                '<span class="time">' + timestampToTime(myData[i].created) + '</span>' +
                                '<div class="delReply" data-id="' + myData[i].id + '">删除</div>'+
                                '</div>' +
                                '<div class="content">' +
                                '<p>' + myData[i].content + '</p>' +
                                '<div class="attachment">' + myData[i].pictures + '</div>' +
                                '</div>' +
                                '</div>';
                            $('#pageContent').append(html2)
                        }
                    } else {
                        console.log("请求出错！");
                    }
                },function() {
                    console.log("服务器异常！")
                });
            }
        },
        closeFeedback : function() {
            var url = ctx + "boss/suggest/update";
            var data = {
                "suggestId": suggestId,
                "user_id": parseInt(userId),
                "status": 2 //0:待处理  1:处理中  2:关闭
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $('#closeMyFeedback').html('已关闭')
                }
            });
        },
        openFeedback : function() {
            var url = ctx + "boss/suggest/update";
            var data = {
                "suggestId": suggestId,
                "user_id": parseInt(userId),
                "status": 1 //0:待处理  1:处理中  2:关闭
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $('#openMyFeedback').html('已开启')
                }
            });
        },
        publicToPrivate : function () {
            var url = ctx + "boss/suggest/update";
            var data = {
                "suggestId": suggestId,
                "user_id": parseInt(userId),
                "type": 1 //0:公开  1:私有
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $('#publicToMyFeedback').html('完成')
                }
            });
        },
        delReply : function (replayId) {
            var url = ctx + "boss/suggest/del";
            var data = {
                "id": parseInt(replayId)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.code == ReturnCode.SUCCESS) {
                    location.reload();
                }
            });
        }
    };
    window.action = action;
    action.loadPageData();

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

    $('#addReply').find('button[type=submit]').click(function (t) {
        t.preventDefault();
        action.add()
    })
    $('#closeMyFeedback').click(function () {
        if(userId == user_id) {
            action.closeFeedback();
        } else {
            toastr.error("权限错误!");
        }
    })
    $('#openMyFeedback').click(function () {
        if(userId == user_id) {
            action.openFeedback();
        } else {
            toastr.error("权限错误!");
        }
    })
    $('#publicToMyFeedback').click(function () {
        action.publicToPrivate();
    })
    $('#pageContent').on('click', '.delReply', function () {
        var replayId = $(this).data('id')
        if(window.confirm('确认要删除吗？')){
            action.delReply(replayId);
        }
    })
})
