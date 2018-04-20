<%@page import="music.util.SessionUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	boolean isLogin = SessionUtils.getUser(request)==null?false:true;
	String activat = (String)request.getAttribute("activat");
%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<title>激活</title>
<script>
	var basePath  = '<%=basePath%>';
	var isLogin = <%=isLogin%>;
	var activat = '<%=activat%>';
</script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
<!-- js -->
<script type="text/javascript" src="<%=basePath%>js/core/jquery-1.10.2.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap/js/bootstrap-paginator.js" ></script>
<script type="text/javascript" src="<%=basePath%>js/core/jquery-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>js/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/music/index.js"></script>
</head>
<body>
	<nav class="navbar navbar-default">
		 <div class="container-fluid">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="#"></a>
		    </div>
			<div class="collapse navbar-collapse row" id="bs-example-navbar-collapse-1">
				 <div class="col-md-2"></div>
				 <div class="navbar-form navbar-left search col-xs-12 col-sm-12 col-md-4" role="search">
				  	<div class="input-group">
				      <span class="input-group-btn">
				        <button class="btn btn-default searchBtn" type="button" onclick="searchMusic();">搜索</button>
				      </span>
				      <input type="text" class="form-control searchTxt" placeholder="歌名或歌手名">
				    </div>
				</div>
				<button type="button" id="login-btn" class="btn btn-default navbar-btn navbar-right" onclick="Login()">登录</button>
				<div class="dropdown navbar-right" id="dropdown">
					您好:
				  <button class="btn btn-default navbar-btn dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				    <span id="usernick"></span>
				    <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu user-menu" aria-labelledby="dropdownMenu1">
				    <li><a href="#">个人中心</a></li>
				    <li><a href="#">我的歌单</a></li>
				    <li><a href="#">我喜欢的</a></li>
				    <li role="separator" class="divider"></li>
				    <li><a onclick="toLogout()">注销</a></li>
				  </ul>
				</div>
			</div>
		</div>
	</nav>
	<h1><%=activat%></h1>
</body>
</html>