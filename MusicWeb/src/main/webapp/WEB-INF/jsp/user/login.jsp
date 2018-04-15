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
<title>注册</title>
<link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>css/user/login.css">
<script>
	var basePath  = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=basePath%>js/core/jquery-1.10.2.js"></script>
<!-- bootStrap -->
<script type="text/javascript" src="<%=basePath%>js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.js"></script>
</head>
<body>
	<div class="main">
		<div class="login"></div>
		<div class="regst">
			<form action="">
				
			</form>
		</div>
	</div>
</body>
</html>