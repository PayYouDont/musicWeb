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
<title>Insert title here</title>
<link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css" >
<link rel="stylesheet" href="<%=basePath%>js/uploader/css/webuploader.css">
<script>
	var basePath  = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploader/js/webuploader.nolog.js"></script>
<!-- bootStrap -->
<script type="text/javascript" src="<%=basePath%>js/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	 <div id="uploader" class="wu-example">
	    <!-- 用来存放文件信息 -->
	    <div id="thelist" class="uploader-list"></div>
	    <div class="btns">
	        <div id="picker">选择文件</div>

	        <button id="ctlBtn" class="btn btn-default" onclick="uploadTest()">开始上传</button>
	    </div>
	</div>
	<div class="down">
		<input type="text" style="width:260px" placeholder="输入sheet名(如:城市运营供应商汇总)" class="form-control" id="sheet"/>
		<button id="getBtn" class="btn btn-default" style="display: none;" onclick="getFile()">生成文件</button>
	</div>
<script type="text/javascript" src="<%=basePath%>js/user/uploader.js"></script>
</body>
</html>