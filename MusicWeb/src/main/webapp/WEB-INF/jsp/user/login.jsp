<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String action = (String)request.getAttribute("action");
%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%=action%></title>
<link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>css/user/login.css">
<script>
	var basePath  = '<%=basePath%>';
	var action = '<%=action%>';
</script>
<script type="text/javascript" src="<%=basePath%>js/core/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/core/jquery.cookie.js"></script>
<!-- bootStrap -->
<script type="text/javascript" src="<%=basePath%>js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/user/login.js"></script>
</head>
<body>
	<div class="main">
		<div class="login">
			<form class="form-horizontal login-form">
				  <div class="form-group">
				    <label class="col-sm-2 control-label">账号:</label>
				    <div class="col-sm-9">
				      <input type="text" class="form-control" name="account" id="account" placeholder="账号">
				    </div>
				  </div>
				  <div class="form-group">
				    <label class="col-sm-2 control-label">密码:</label>
				    <div class="col-sm-9">
				      <input type="password" class="form-control" name="password" id="password" placeholder="密码">
				    </div>
				  </div>
				  <div class="to-regist">
				  	<a href="<%=basePath%>rest/userAction/toRegist">去注册>></a>
				  </div>
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				      <div class="checkbox">
				        <label>
				          <input type="checkbox" id="reme">记住我
				        </label>
				      </div>
				    </div>
				  </div>
			</form>
			<div class="form-group login-btn">
			    <div class="col-sm-offset-2 col-sm-10">
			      	<button class="btn btn-default" onclick="toLogin()">登录</button>
			    </div>
			</div>
			<div class="alert alert-danger" role="alert" id="login-alert">
				<strong>登录失败：</strong><span id="login-msg">账号或密码错误</span>
			</div>
		</div>
		<div class="regist">
			<form  class="form-inline regist-form">
			   <div class="form-group">
			     <label>账号:</label>
			     <input type="text" class="form-control" name="account" placeholder="账号">
			   </div>
			   <div class="form-group">
			     <label>Email</label>
			     <input type="email" class="form-control" name="email" placeholder="邮箱">
			   </div>
			   <div class="form-group">
			     <label>密码:</label>
			     <input type="text" class="form-control" name="password" placeholder="密码">
			   </div>
			   <div class="form-group">
			     <label>昵 称:</label>
			     <input type="text" class="form-control" name="nick" placeholder="昵称">
			   </div>
			   <div class="form-group">
			     <label>姓名:</label>
			     <input type="text" class="form-control" name="name" placeholder="姓名">
			   </div>
			   <div class="form-group">
			     <label>电 话:</label>
			     <input type="text" class="form-control" name="phone" placeholder="电话">
			   </div>
			</form>
		</div>
	</div>
</body>
</html>