$(function() {
	var userName = Util.cookieStorage.getCookie("username");
    var token_value = Util.cookieStorage.getCookie("accesstoken");
    var depart_id = Util.cookieStorage.getCookie("departId");
    var role_level = Util.cookieStorage.getCookie("userLevel");
    var admin_id = Util.cookieStorage.getCookie("adminId");

    var table_th_html = '<tr>' +
        '<th>用户名称</th>' +
        '<th>昵称</th>' +
        '<th>所属公司</th>' +
        '<th>联系人</th>' +
        '<th>联系电话</th>' +
        '<th>邮箱</th>' +
        '<th>管理</th>' +
        '</tr>';
    var table_th_html2 = '<tr>' +
        '<th>用户名称</th>' +
        '<th>昵称</th>' +
        '<th>联系人</th>' +
        '<th>联系电话</th>' +
        '<th>邮箱</th>' +
        '<th>管理</th>' +
        '</tr>';
    if(role_level == 3){
        $("#table").find("thead").append(table_th_html);
    }else{
        $("#table").find("thead").append(table_th_html2);
    }

	var action = {
		//新增数据
		add : function() {
            var url = ctx + "AdminManage/CreateAdmin";
            var auth_val_list = $("#input-authList").text();
            var auth_arr = auth_val_list.split(",");
            var param_arr = [];
            for(i=0; i<auth_arr.length; i++){
                auth_arr[i] = Number(auth_arr[i]);
                param_arr.push(auth_arr[i]);
            }
            var company_name_txt;
            var company_val;
            if($("#input-userCompany").val() != ''){
                company_name_txt = $("#input-userCompany").find("option:selected").text();
                company_val = $("#input-userCompany").find("option:selected").val();
            }
            if(role_level == 3){
                var data = {
                    UserName:userName,
                    AuthenticCode:token_value,
                    DepartId:parseInt(company_val),
                    RoleLevel:parseInt(role_level),
                    NewUserName:$("#input-userName").val(),
                    NewUserPd:$.md5($("#input-userPd").val()),
                    NewUserNick:$("#input-userNick").val(),
                    NewUserCompany:company_name_txt,
                    LinkMan:$("#input-contactName").val(),
                    LinkPhone:$("#input-phoneNo").val(),
                    Email:$("#input-email").val(),
                    AuthInfo:param_arr
                }
            }else {
                var data = {
                    UserName:userName,
                    AuthenticCode:token_value,
                    DepartId:parseInt(depart_id),
                    RoleLevel:parseInt(role_level),
                    NewUserName:$("#input-userName").val(),
                    NewUserPd:$.md5($("#input-userPd").val()),
                    NewUserNick:$("#input-userNick").val(),
                    NewUserCompany:company_name_txt,
                    LinkMan:$("#input-contactName").val(),
                    LinkPhone:$("#input-phoneNo").val(),
                    Email:$("#input-email").val(),
                    AuthInfo:param_arr
                }
            }

            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.Status == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("添加成功!");
                    window.location.reload();
                    /*action.loadPageData();*/
                }else if(result.Status == 1){
                    toastr.error(result.ErrorInfo);
				}else{
                	alert("添加失败！");
				}
            });
		},
		//获取所有数据
		loadPageData : function() {
            var url = ctx + "AdminManage/GetAdminInfo";
            var td_len = $("#table thead tr th").length;//表格字段数量
            var data = {
                "UserName":userName,
                "AuthenticCode":token_value,
                "DepartId":parseInt(depart_id),
                "RoleLevel":parseInt(role_level)
            };

            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $('#pageContent').find("tr").remove();
                    var pageTmplHtml = '<tr>' +
                        '<td style="display:none;" class="td-id">${AdminId}</td>' +
                        '<td style="display:none;" class="td-userPd">${UserPd}</td>' +
                        '<td class="td-userName">${UserName}</td>' +
                        '<td class="td-userNick">${UserNick}</td>' +
                        '<td class="td-userCompany">${UserCompany}</td>' +
                        '<td class="td-linkMan">${LinkMan}</td>' +
                        '<td class="td-linkPhone">${LinkPhone}</td>' +
                        '<td class="td-email">${Email}</td>' +
                        '<td>' +
                        '<a href="javascript:void(0);" class="table-edit-btn">编辑 </a>' +
                        '<a href="javascript:void(0);" onclick="action.deleteItem(${AdminId},\'${UserName}\')"> 删除</a>' +
                        '</td>' +
                    '</tr>';

                    var pageTmplHtml2 = '<tr>' +
                        '<td style="display:none;" class="td-id">${AdminId}</td>' +
                        '<td style="display:none;" class="td-userPd">${UserPd}</td>' +
                        '<td class="td-userName">${UserName}</td>' +
                        '<td class="td-userNick">${UserNick}</td>' +
                        '<td class="td-linkMan">${LinkMan}</td>' +
                        '<td class="td-linkPhone">${LinkPhone}</td>' +
                        '<td class="td-email">${Email}</td>' +
                        '<td>' +
                        '<a href="javascript:void(0);" class="table-edit-btn">编辑 </a>' +
                        '<a href="javascript:void(0);" onclick="action.deleteItem(${AdminId},\'${UserName}\')"> 删除</a>' +
                        '</td>' +
                        '</tr>';

                    if(role_level == 3){
                        $.template('template', pageTmplHtml);
                    }else{
                        $.template('template', pageTmplHtml2);
                    }
                    $.tmpl('template', result.AdminInfo).appendTo('#pageContent');

                    if($('#pageContent tr').length == 0 || result.Status == 6){
                        $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
                    }
                }else if(result.Status == 6){
                    $('#pageContent').find("tr").remove();
                    $('#pageContent').append("<tr><td  colspan='" + td_len + "' class='t_a_c'>暂无数据</td></tr>");
                }else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
		},
        //获取公司列表数据
        loadCompanyData : function() {
            var url = ctx + "CooperativeCompany/QueryCompanyInfo";
            var data = {
                UserName:userName,
                AuthenticCode:token_value,
                RoleLevel:parseInt(role_level),
                Page:1,
                PageSize:0
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $("#pageCompany").tmpl(result.CompanyInfo).appendTo('#input-userCompany');
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
        },
        //获取公司列表数据
        /*loadCompanyData2 : function() {
            var url = ctx + "CooperativeCompany/QueryCompanyInfo";
            var data = {
                UserName:userName,
                AuthenticCode:token_value,
                RoleLevel:parseInt(role_level),
                Page:1,
                PageSize:0
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    var company_list = result.CompanyInfo;
                    for(i=1; i<company_list.length; i++){
                        var company_id = company_list[i].CompanyId;
                        if(depart_id == company_id){
                            var company_name = company_list[i].CompanyName;
                        }
                    }
                    return company_name;
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
        },*/
        //获取权限列表数据
        loadAuthData : function(c_id,u_id) {
            var url = ctx + "AdminManage/GetCompanyAuthInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "DepartId":parseInt(c_id),
                "AdminId" : parseInt(u_id),
                "RoleLevel":parseInt(role_level)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    var data_auth = result.AuthDirInfo;
                    var auth_id_arr = [];
                    for(i=0; i<data_auth.length; i++){
                        auth_id_arr.push(data_auth[i].AuthDirId);
                        for(j=0; j<data_auth[i].SubFunction.length; j++){
                            auth_id_arr.push(data_auth[i].SubFunction[j].SubFunId);
                        }
                    }
                    var auth_id_list = auth_id_arr.join(',');
                    $("#input-authList").empty();
                    $("#input-authList").text(auth_id_list);
                    $("#input-oldAuthList").text(auth_id_list);
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
        },
        //添加用户时获取权限列表数据显示在权限选择弹窗里
        loadAddAuthData : function(c_id,u_id) {
            var url = ctx + "AdminManage/GetCompanyAuthInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "DepartId":parseInt(c_id),
                "AdminId" : parseInt(u_id),
                "RoleLevel":parseInt(role_level)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $("#authContent").empty();
                    $("#pageAuth").tmpl(result.AuthDirInfo).appendTo('#authContent');
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
        },
        //获取管理员的所有权限
        loadAllAuthData : function(a_id){
            var url = ctx + "AdminManage/GetAdminAuthInfo";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "UserId" : parseInt(a_id)
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    $("#authContent").empty();
                    $("#pageAuth").tmpl(result.AdminAuthInfo).appendTo('#authContent');
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });
        },
        //获取当前用户拥有的系统操作权限信息
        /*loadPrivateAuthData : function() {
            var url = ctx + "Manage/GetPrivateAuth";
            var data = {
                "UserName":userName,
                "AuthenticCode": token_value,
                "AuthFid": parseInt(url_param_id),  //AuthDirId
                "UserId": parseInt(admin_id)   //AdminId
            };
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if(result.Status == ReturnCode.SUCCESS && result.AuthenticCode != ""){
                    var a = result.UsertAuthCode;
                    console.log(JSON.stringify(a));
                } else {
                    alert("请求出错！");
                }
            },function() {
                alert("服务器开个小差，请稍后重试！")
            });

        },*/

		//编辑数据
		edit : function() {
		    var curr_cId = $("#input-userCompany").val();
            var c_list = $("#input-userCompany").find("option");
            for(i=0; i<c_list.length; i++){
                if(c_list.eq(i).val() === curr_cId){
                    var curr_item = c_list.eq(i).text();
                }
            }

            var pd_val = $("#input-userPd").val();
            var curr_pd;
            if(pd_val.length == 32){
                curr_pd = $("#input-userPd").val();
            }else{
                curr_pd = $.md5($("#input-userPd").val());
            }

            var add_auth_code_list = $("#input-addAuthList").text();
            var add_auth_code_arr = add_auth_code_list.split(",");
            var add_auth_code = [];
            for(i=0; i<add_auth_code_arr.length; i++){
                add_auth_code.push(parseInt(add_auth_code_arr[i]));
            }

            var del_auth_code_list = $("#input-delAuthList").text();
            var del_auth_code_arr = del_auth_code_list.split(",");
            var del_auth_code = [];
            for(i=0; i<del_auth_code_arr.length; i++){
                del_auth_code.push(parseInt(del_auth_code_arr[i]));
            }

            var url = ctx + "AdminManage/ModifyAdminInfo";
            var data = new Object();
            data.UserName = userName;
            data.AuthenticCode = token_value;
            data.DepartId = parseInt(depart_id);
            data.RoleLevel = parseInt(role_level);
            data.AdminId = parseInt($("#input-id").val());
            data.NewUserName = $("#input-userName").val();
            data.NewUserPd = curr_pd;
            data.NewUserNick = $("#input-userNick").val();
            data.NewUserCompany = curr_item;
            data.LinkMan = $("#input-contactName").val();
            data.LinkPhone = $("#input-phoneNo").val();
            data.Email = $("#input-email").val();
            data.AddAuthCode = add_auth_code;
            data.DelAuthCode = del_auth_code;
            Util.ajaxLoadData(url,data,"POST",true,function(result) {
                if (result.Status == ReturnCode.SUCCESS) {
                    $("#addTempl-modal").modal('hide');
                    toastr.success("编辑成功!");
                    action.loadPageData();
                }
            });
		},
		//删除数据
        deleteItem : function(id, name) {
			if (confirm("删除后不可恢复，确定删除" + name + "？")) {
				var url = ctx + "AdminManage/DeleteAdmin";
				var data = new Object();
                data.UserName = userName;
                data.AuthenticCode = token_value;
                data.RoleLevel = parseInt(role_level);
                data.AdminId = parseInt(id);
				Util.ajaxLoadData(url,data,"POST",true,function(result) {
					if (result.Status == ReturnCode.SUCCESS) {
                        toastr.success("删除成功!");
                        action.loadPageData();
					}
				});
			}
		},
        //树形复选框
        // 页面加载完成后调用
        authListTree : function(){
            $(".tree_node .layui-form-checkbox").hide();
            $(".tree_node input").show();
            // 为所有的父节点添加点击事件
            $("#authContent").on('click','.tree_node_parent_checkbox', function(){
                // 获取父节点是否选中
                var isChange = $(this).prop("checked");
                if(isChange){
                    $(this).parent().find(".tree_node_child_checkbox").prop("checked", true);
                }else{
                    $(this).parent().find(".tree_node_child_checkbox").removeAttr("checked");
                }
            });

            // 为所有的子节点添加点击事件
            $("#authContent").on('click','.tree_node_child_checkbox', function(){
                // 获取选中的节点的父节点下的所有子节点选中的数量
                var length = $(this).parent().find(".tree_node_child_checkbox:checked").length;
                // 判断当前结点是否选中
                if($(this).prop("checked")){ // 选中
                    // 如果当前节点选中后,所有的选中节点数量1，选中父节点
                    if(length >= 1){
                        $(this).parent().parent().find(".tree_node_parent_checkbox").prop("checked", true);
                    }
                }else{ // 节点未选中
                    if(length < 1){
                        // 取消父节点的选中状态
                        $(this).parent().parent().find(".tree_node_parent_checkbox").removeAttr("checked");
                    }
                }
            });

            // 为所有的切换按钮添加点击事件
            $("#authContent").on('click','.tree_node_toggle_button', function(){
                // 获取需要隐藏或显示的节点
                var $toggle_node = $(this).parent().next().find(".tree_node_child");
                $toggle_node.toggle(); // 切换隐藏或显示
                // 切换按钮的显示
                if($toggle_node.is(":visible")){
                    $(this).val("-");
                }else{
                    $(this).val("+");
                }
            });
        }
	};
	window.action = action;
	action.loadPageData();
	action.authListTree();
    //action.loadPrivateAuthData();

    if(role_level == 3){
        action.loadCompanyData();
    }else{
        var option_html = '<option value='+depart_id+'></option>';
        $("#input-company-wrap").find("select").append(option_html);
        $("#input-company-wrap").css("display","none");
    }

    //编辑获取数据
    $("#pageContent").on("click",".table-edit-btn",function(){
        var that = $(this).parent().parent();
        var curr_item = that.find(".td-userCompany").text();//点编辑时获取该用户所属公司的名称
        var c_list = $("#input-userCompany").find("option");//找出表单中公司列表的所有下拉项
        for(i=0; i<c_list.length; i++){
            //遍历下拉列表的每一项公司名称，判断跟点编辑时获取的公司名称是否相同
            if(c_list.eq(i).text() === curr_item){
                var curr_cId = c_list.eq(i).val();//如果相同，则获取该项option的value值
                c_list.eq(i).attr('selected','');//并且给该项option添加selected属性
            }
        }
        var curr_uId = that.find(".td-id").text();
        //获取父级所有权限
        action.loadAllAuthData(curr_uId);
        //获取当前项所拥有的权限
        action.loadAuthData(curr_cId,curr_uId);

        $("#input-id").val(that.find(".td-id").text());
        $("#input-userName").val(that.find(".td-userName").text());
        $("#input-userPd").val(that.find(".td-userPd").text());
        $("#input-userNick").val(that.find(".td-userNick").text());
        $("#input-curr-userCompany").val(curr_cId);
        $("#input-contactName").val(that.find(".td-linkMan").text());
        $("#input-phoneNo").val(that.find(".td-linkPhone").text());
        $("#input-email").val(that.find(".td-email").text());
        $("#addTempl-modal").modal("show");
    });

    $("#addTempl-modal").on('show.bs.modal', function(e) {
        // 处理modal label显示及表单重置
        var $form = $("form#form-addTempl");
        if (!e.relatedTarget) {
            if(role_level == 3) {

            }else {
                var opts = $("#input-userCompany").find("option");
                for (i = 0; i < opts.length; i++) {
                    if (opts.eq(i).val() == depart_id) {
                        opts.eq(i).attr('selected', 'true');
                    }
                }
            }
            $("h4#addTempl-modal-label").text("编辑管理员信息");
            $("#input-company-wrap").hide();
            $form.data("action", "edit");
        } else if (e.relatedTarget.id = "btn-add") {
            $("#authContent").empty();
            //显示添加弹窗表单时，初始化所属公司下拉选项
            var ipt_uCompany_list = $("#input-userCompany").find("option");
            for(i=0; i<ipt_uCompany_list.length; i++){
                if(ipt_uCompany_list.eq(i).attr('selected') == 'selected'){
                    ipt_uCompany_list.eq(i).attr('selected',false);
                }
            }
            $("h4#addTempl-modal-label").text("添加管理员信息");
            if(role_level == 3){
                $("#input-company-wrap").show();
            }else{
                //其他等级角色用户显示添加用户弹窗表单时，所属公司为该用户depart_id，所属公司下拉列表已被隐藏
                action.loadAddAuthData(depart_id,admin_id);
                $("#input-company-wrap").hide();
            }
            $form.data("action", "add");
            $form[0].reset();
        }
    });

	//验证表单
    $("#form-addTempl").validate({
        rules : {
            userName : {
                required : true
            },
            password : {
                required : true,
                pdlength : 32
            },
            userNick : {
                required : true
            },
            userCompany : {
                required : true
            }
        }
    });

    $.validator.addMethod("pdlength",function(value,element,params){
        if(value.length==params){
            return true;
        }else{
            if(value.length>=17){
                return false;
            }else{
                return true;
            }
        }
    },"密码长度不能超过16个字符！");

    $("#btn-add-submit").on('click', function() {
        if (!$("#form-addTempl").valid()) {
            return;
        }
        var action = $("form#form-addTempl").data("action");
        switch (action) {
            case "add":
                window.action.add();
                break;
            case "edit":
                window.action.edit();
                break;
        }
    });

    //选择公司表单事件
    $("#input-userCompany").change(function(){
        var c_id = $(this).val();
        action.loadAddAuthData(c_id,admin_id);
    });

    //选择权限的事件
    function modalClose() {
        $("#addAuth-modal").removeClass('in').css("display","none");
        $("#auth-modal-mask").remove();
    }
    function modalOpen() {
        $("#addAuth-modal").addClass('in').css("display","block");
        var ui_master = '<div class="modal-backdrop fade in" id="auth-modal-mask" style="z-index:1051"></div>';
        $("body").append(ui_master);
    }
    function getCheckAuth(){
        var check_node = $("#authContent").find("input:checked");
        var check_list = [];
        for(i=0; i<check_node.length; i++){
            var check_val = check_node.eq(i).val();
            check_list.push(check_val);
        }
        $("#input-authList").text(check_list);
    }
    function getNotCheckAuth(){
        var old_auth_list = $("#input-oldAuthList").text();
        var old_auth_arr = old_auth_list.split(",");

        var check_node = $("#authContent").find("input:checked");
        var new_auth_arr = [];
        for(i=0; i<check_node.length; i++){
            var check_val = check_node.eq(i).val();
            new_auth_arr.push(check_val);
        }

        //获得新删除的权限项
        var del_auth_arr = [];
        for(i=0; i<old_auth_arr.length; i++){
            var isExist = false;
            for(j=0; j<new_auth_arr.length; j++){
                if(old_auth_arr[i] == new_auth_arr[j]){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                del_auth_arr.push(old_auth_arr[i]);
            }
        }
        $("#input-delAuthList").text(del_auth_arr);

        //获得新添加的权限项
        var add_auth_arr = [];
        for(k=0; k<new_auth_arr.length; k++){
            var isExist = false;
            for(l=0; l<old_auth_arr.length; l++){
                if(new_auth_arr[k] == old_auth_arr[l]){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                add_auth_arr.push(new_auth_arr[k]);
            }
        }
        $("#input-addAuthList").text(add_auth_arr);

    }
    $("#btn-addAuth").on('click', function() {
        var action = $("form#form-addTempl").data("action");
        //点击添加权限按钮判断如果是在编辑弹窗状态，显示对应所要编辑用户所拥有的权限
        if(action === 'edit') {
            var curr_auth_list = $("#input-authList").text();//拿到当前该用户拥有的权限id
            var curr_auth_arr = curr_auth_list.split(",");//转换为数组
            var all_ipt_list = $("#authContent").find('input[type="checkbox"]');//拿到权限选择窗口里所有checkbox元素
            for (i = 0; i < all_ipt_list.length; i++) {
                all_ipt_list.eq(i).prop('checked', false);//遍历所有checkbox元素并且去掉每一项的checked勾选
                for (j = 0; j < curr_auth_arr.length; j++) {//遍历用户当前所拥有的权限id
                    //将每一项checkbox元素的value与用户当前所拥有的权限id匹配
                    if (all_ipt_list.eq(i).val() == curr_auth_arr[j]) {
                        all_ipt_list.eq(i).prop('checked', true);//如果两个值相等，则将该checkbox元素设置为选中
                    }
                }
            }
        }else if(action === 'add'){
            var all_ipt_list = $("#authContent").find('input[type="checkbox"]');//拿到权限选择窗口里所有checkbox元素
            for(i=0; i<all_ipt_list.length; i++){
                all_ipt_list.eq(i).prop('checked',true);//将全部checkbox元素设置为选中
            }
        }
        modalOpen();//打开权限选中弹窗
    });
    $("#auth-modal-close").on('click', function() {
        modalClose();
    });
    $("#modal-btn-ok").on('click', function(){
        getNotCheckAuth();
        getCheckAuth();
        modalClose();
    });

});
