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
  initVerify();
})

var verifyCode = -1;
var validateObj;
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
	if(verifyCode==-1){
		tooltip("captcha-login","请点击验证码验证")
		return;
	}else if(verifyCode==0){
		tooltip("captcha-login","验证不正确")
		return;
	}else{
		$('#captcha-login').tooltip('destroy');
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
//初始化验证码
function initVerify(){
	$.ajax({
	    url:basePath + "rest/userAction/initVerify?t=" + (new Date()).getTime(), // 加随机数防止缓存",
	    type: "get",
	    dataType: "json",
	    success: function (json) {
	    	if(json.success){
	    	var data = json.data;
	    	data = JSON.parse(data);
	        //请检测data的数据结构， 保证data.gt, data.challenge, data.success有值
	        initGeetest({
	            // 以下配置参数来自服务端 SDK
	            gt: data.gt,
	            challenge: data.challenge,
	            new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
	            width: "281px",
	            new_captcha: true
	        },function (captchaObj) {
	        	 captchaObj.appendTo("#captcha-"+action); //将验证按钮插入到宿主页面中captchaBox元素内
	        	 captchaObj.onReady(function(){
	 	        	$(".wait").hide();
	        	    }).onSuccess(function(){
	        	    	var result = captchaObj.getValidate();
	        	    	validateObj = {
        	    			geetest_challenge: result.geetest_challenge,
        	                geetest_validate: result.geetest_validate,
        	                geetest_seccode: result.geetest_seccode
	        	    	}
	        	    	verifyCode = 1;
	        	    }).onError(function(){
	        	    	verifyCode = 0;
	        	    })
	        });
	      }
	    }
	})
}

function submitLogin(){
	var data = $(".login-form").serializeArray();
	var geetest_challenge = validateObj.geetest_challenge;
	var geetest_validate = validateObj.geetest_validate;
	var geetest_seccode = validateObj.geetest_seccode;
	data.push({
		geetest_challenge:geetest_challenge,
		geetest_validate:geetest_validate,
		geetest_seccode:geetest_seccode
		});
	var url = basePath + "rest/userAction/login";
	$.ajax({
		url:url,
		dataType:"json",
		type:"post",
		data:data,
		success:function(json){
			if(json.success){
				var msg = json.data;
				console.log(msg)
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
/********************************************************/

/*********************Regist*****************************/
function toRegist(){
	var account = $("#account-regist").val().trim();
	if(account==""){
		tooltip("account-regist","账号不能为空")
		return;
	}else{
		$('#account-regist').tooltip('destroy');
	}
	var email = $("#email-regist").val().trim();
	if(email==""){
		tooltip("email-regist","邮箱不能为空")
		return;
	}else{
		$('#email-regist').tooltip('destroy');
	}
	var password = $("#password-regist").val().trim();
	if(password==""){
		tooltip("password-regist","密码不能为空")
		return;
	}else{
		$('#password-regist').tooltip('destroy');
	}
	var confirmPwd = $("#confirmPwd").val().trim();
	if(confirmPwd!=password){
		tooltip("confirmPwd","密码输入不一致")
	}else{
		$('#confirmPwd').tooltip('destroy');
	}
	if(verifyCode==-1){
		tooltip("captcha-regist","请点击验证码验证")
		return;
	}else if(verifyCode==0){
		tooltip("captcha-regist","验证不正确")
		return;
	}else{
		$('#captcha-regist').tooltip('destroy');
	}
	submitRegist();
}
function submitRegist(){
	var data = $(".regist-form").serializeArray();
	var geetest_challenge = validateObj.geetest_challenge;
	var geetest_validate = validateObj.geetest_validate;
	var geetest_seccode = validateObj.geetest_seccode;
	data.push({
		geetest_challenge:geetest_challenge,
		geetest_validate:geetest_validate,
		geetest_seccode:geetest_seccode
		});
	var url = basePath + "rest/userAction/regist";
	$.ajax({
		url:url,
		dataType:"json",
		type:"post",
		data:data,
		success:function(json){
			if(json.success){
				var msg = json.data;
				if(msg!="注册成功"){
					$("#regist-alert").show();
					$("#regist-msg").html(msg);
					console.log(msg)
				}else{
					window.location.href = basePath;
				}
			}
		}
	});
}
