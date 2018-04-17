<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登录</title>
<link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>css/user/login.css">
<script>
	var basePath  = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=basePath%>js/core/jquery-1.10.2.js"></script>
<!-- bootStrap -->
<script type="text/javascript" src="<%=basePath%>js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/user/login.js"></script>
</head>
<body>
	<div class="main">
		<div class="go-btn tologin">
			
		</div>
		<div class="go-btn toregist">
			
		</div>
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
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				      <div class="checkbox">
				        <label>
				          <input type="checkbox">记住我
				        </label>
				      </div>
				    </div>
				  </div>
			</form>
			<div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" class="btn btn-default" onclick="toLogin()">登录</button>
			    </div>
			</div>
		</div>
		<div class="regist">
			<form action="">
				
			</form>
		</div>
	</div>
</body>
</html>