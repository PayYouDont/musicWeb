$(function () {
  $('[data-toggle="tooltip"]').tooltip()
})
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
	var data = $(".login-form").serializeArray();
}

function tooltip(id,msg){
	$("#"+id).data({
		"toggle":"tooltip",
		"placement":"top",
		"title":msg
	}).tooltip('show');
}