
jQuery(document).ready(function() {

    $('.page-container form').submit(function(){
        var username = $(this).find('.username').val();
        var password = $(this).find('.password').val();
        if(username == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '27px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.username').focus();
            });
            return false;
        }
        if(password == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '96px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.password').focus();
            });
            return false;
        }
    });

    $('.page-container form .username, .page-container form .password').keyup(function(){
        $(this).parent().find('.error').fadeOut('fast');
    });

});

$("#btn_submit").bind('click',function(){
    $("#modal-loading").modal({backdrop: 'static', keyboard: false, show: true});
    var url = ctx + "boss/admin/login";
	var data = {"account":$("#ipt_username").val(),"password": $.md5($("#ipt_password").val())};
    Util.ajaxLoginData(url,data,"POST",true,function(result) {
        $("#modal-loading").modal("hide")
        if(result.code == ReturnCode.SUCCESS ){
            Util.cookieStorage.setCookie("accesstoken",result.data.accessToken);
            Util.cookieStorage.setCookie("username",result.data.account);
            Util.cookieStorage.setCookie("nickname",result.data.nickName);
            Util.cookieStorage.setCookie("userLevel",result.data.roleId);
            Util.cookieStorage.setCookie("companyId",result.data.companyId);
			Util.cookieStorage.setCookie("departId",result.data.departId);
            Util.cookieStorage.setCookie("adminId",result.data.id);
			Util.cookieStorage.setCookie("phone",result.data.phone);
			Util.cookieStorage.setCookie("home",result.data.home);

            window.location.href = result.data.home;
        } else {
            toastr.error(result.msg);
        }
    },function() {
        $("#modal-loading").modal("hide")
        alert("服务器异常，请稍后重试！");
    });
});
