var cusers = new Array();
$(function () {
  $('[data-toggle="tooltip"]').tooltip();
  if(action=="login"){//登录
	  $(".login").show();
	  $(".regist").hide();
	//移除密码
	  $('#reme').click(function () {
	      if (!$('#reme').attr('checked')) {
	          var cookie = new rememberPassword();
	          cookie.cookieDelete();
	      }
	  });
	  //cookie记住密码
	  var cookie = new rememberPassword();
	  cookie.cookieInit();
  }else{
	  $(".regist").show();
	  $(".login").hide();
  } 
})

//登录
function toLogin(){
	var account = $("#account").val().trim();
	if(account==""){
		tooltip("account","账号不能为空")
		return;
	}else{
		$('#account').tooltip('destroy');
	}
	var password = $("#password").val().trim();
	if(password==""){
		tooltip("password","密码不能为空")
		return;
	}else{
		$('#password').tooltip('destroy');
	}
	submitLogin();
}
//提示框
function tooltip(id,msg){
	$("#"+id).data({
		"toggle":"tooltip",
		"placement":"top",
		"title":msg
	}).tooltip('show');
}

function submitLogin(){
	var data = $(".login-form").serializeArray();
	var url = basePath + "rest/userAction/login";
	$.ajax({
		url:url,
		dataType:"json",
		type:"post",
		data:data,
		success:function(json){
			if(json.success){
				var msg = json.data;
				if(msg!="登录成功"){
					$("#login-alert").show();
					$("#login-msg").html(msg);
				}else{
					var ischeck = $("#reme").is(":checked");
					if(ischeck){
						var cookie = new rememberPassword();
					    cookie.cookieRemeber();
					}
					window.location.href = basePath;
				}
			}
		}
	});
}
//记住密码
function rememberPassword() {
    //存储变量
    this.account = $('#account').val();
    this.password = $('#password').val();
    this.cookie;
    if (!!$.cookie('user')) {
        this.cookie = eval($.cookie('user'));
    } else {
        $.cookie('user', '[]');
        this.cookie = eval($.cookie('user'));
    };
}
rememberPassword.prototype = {
    cookieInit: function() { //初始化
        var temp = this.cookie,
        	account = this.account,
            start = false;
        if (temp.length > 0) {
            $.each(temp, function(i, item) {
                if (item.first == true) {
                    $('#account').val(item.account);
                    $('#password').val(item.password);
                    $('#reme').attr('checked', true)
                }
            });
        }
        $('#account').blur(function() {
            //检查是否存在该用户名,存在则赋值，不存在则不做任何操作
            $.each(temp, function(i, item) {
                if (item.account == $('#account').val()) {
                    $('#account').val(item.account);
                    $('#password').val(item.password);
                    $('#remem').attr('checked', true)
                    start = true;
                    return false;
                } else {
                    $('#password').val('');
                }

            });
        });
    },
    //记住密码
    cookieRemeber: function() {
        var temp = this.cookie,
            account = this.account,
            password = this.password,
            start = false;
        //检测用户是否存在
        $.each(temp, function(i, item) {
            if (item.account == account) {
                //记录最后一次是谁登录的
                item.first = true;
                $('#password').val(item.password);
                start = true;
                return;
            } else {
                item.first = false;
            }
        });
        //不存在就把用户名及密码保存到cookie中
        if (!start) {
            temp.push({
                account: account,
                password: password,
                first: true
            });
        }
        //存储到cookie中
        $.cookie('user', JSON.stringify(temp));
    },
    //删除密码
    cookieDelete: function() {
        var temp = this.cookie,
            account = this.account,
            num = 0;
        //检测用户是否存在
        $.each(temp, function(i, item) {
            if (item.account === account) {
                num = i;
            }
        });
        //删除里面的密码
        temp.splice(num, 1);
        //存储到cookie中
        $.cookie('user', JSON.stringify(temp));
    }
}
/*********************Regist*****************************/

