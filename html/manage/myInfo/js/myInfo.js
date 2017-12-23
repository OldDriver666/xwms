$(function() {
    //获取cookie
    var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var company_id = Util.cookieStorage.getCookie("companyId");
    var role_level = Util.cookieStorage.getCookie("userLevel");
    var admin_id = Util.cookieStorage.getCookie("adminId");
    var nick_name = Util.cookieStorage.getCookie("nickname");


    function getMyInfoData () {
        var url = ctx + "boss/admin/queryself";
        var moduleId = 0;
        var data = new Object();
        data.account = userName;

        Util.ajaxLoadData(url,data,moduleId,"POST",true,function(result) {
            if(result.code == ReturnCode.SUCCESS){
                $(".nickName_txt").text(result.data.nickName);
                $(".userName_txt").text(result.data.account);
            } else {
                toastr.error(result.msg);
            }
        },function(errorMsg) {
            alert(errorMsg)
        });
    }
    getMyInfoData()

    //修改密码
    $("#btn_submit").bind('click', function (e) {
        var pwd1 = $("#ipt_old_password").val();
        var pwd2 = $("#ipt_new_password").val();
        if (pwd1 == "" ) {
            $("#ipt_old_password").parent().parent().addClass("has-error");
            var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
            $("#ipt_old_password").parent().append(err_html);
            return;
        } else if(pwd2 == ""){
            $("#ipt_new_password").parent().parent().addClass("has-error");
            var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
            $("#ipt_new_password").parent().append(err_html);
            return;
        }else if(pwd1 == pwd2){
            var pwd =  $.md5(pwd2);
            var url = ctx + "boss/admin/update";
            var moduleId = 0;
            var data = new Object();
            data.login_id = parseInt(admin_id);
            data.admin_id = parseInt(admin_id);
            data.password = pwd;
            data.depart_id = parseInt(depart_id);
            data.company_id = parseInt(company_id);

            Util.ajaxLoadData(url, data,moduleId,"POST", true, function (result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#editPW-modal").modal('hide');
                    toastr.success("修改成功");
                    getMyInfoData()
                } else {
                    toastr.error(result.msg);
                }
            },function(errorMsg) {
                alert(errorMsg)
            });
        }else{
            toastr.error("两次输入的密码不一致！")
        }
    });

    //修改昵称
    $("#btn_submit1").bind('click',function(e){
        if ($("#ipt_nickname").val() == "") {
            $("#ipt_nickname").parent().parent().addClass("has-error");
            var err_html = "<label class='error control-label' style='padding-left: 5px;'>必填字段</label>";
            $("#ipt_nickname").parent().append(err_html);
            return;
        }else {
            var newNickName = $("#ipt_nickname").val();
            var url = ctx + "boss/admin/update";
            var moduleId = 0;
            var data = new Object();
            data.login_id = parseInt(admin_id);
            data.admin_id = parseInt(admin_id);
            data.nick_name = newNickName;
            data.depart_id = parseInt(depart_id);
            data.company_id = parseInt(company_id);

            Util.ajaxLoadData(url, data,moduleId, "POST", true, function (result) {
                if (result.code == ReturnCode.SUCCESS) {
                    $("#NickName-modal").modal('hide');
                    toastr.success("修改成功");
                    getMyInfoData()
                } else {
                    toastr.error(result.msg);
                }
            },function(errorMsg) {
                alert(errorMsg)
            });
        }
    });

    $("#ipt_old_password").change(function(){
        if($(this).val() != ""){
            $(this).parent().parent().removeClass("has-error");
            $(this).next().remove();
        }
    });

    $("#ipt_new_password").change(function(){
        if($(this).val() != ""){
            $(this).parent().parent().removeClass("has-error");
            $(this).next().remove();
        }
    });

    $("#ipt_nickname").change(function(){
        if($(this).val() != ""){
            $(this).parent().parent().removeClass("has-error");
            $(this).next().remove();
        }
    });

});