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
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<title>Insert title here</title>
<script>
	var basePath  = '<%=basePath%>';
</script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/scroll.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/xiami.css">
<!-- js -->
<script type="text/javascript" src="<%=basePath%>js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap/js/bootstrap-paginator.js" ></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>js/canvas.js"></script>
<script type="text/javascript" src="<%=basePath%>js/mousewheel.js"></script>
<script type="text/javascript" src="<%=basePath%>js/scroll.js"></script>
<script type="text/javascript" src="<%=basePath%>js/xiami.js"></script>
<script type="text/javascript" src="<%=basePath%>js/index.js"></script>
</head>
<script type="text/javascript">
 	var height = document.documentElement.clientHeight-155;
	var agent = window.navigator.userAgent;
	$(function(){
		if(agent.toLowerCase().indexOf("windows")!=-1){
			$(".middle").css("height",height+"px");
		}else{
			$(".middle").css("height","");
		}
	}) 
</script>
<body>
	<!--模糊画布-->
	<!-- <div class="blur">
		<canvas style="width: 1366px; height: 700px; opacity: 0;" width="1366"
			height="700" id="canvas">
	
	</div> -->
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
					<button type="button" class="btn btn-default navbar-btn navbar-right">登陆</button>
				</div>
			</div>
		</nav>
		<div class="middle">
			<div class="mainWrap row">
				<div class="leftBar col-xs-12 col-sm-12 col-md-2">
					<ul class="menuUL">
						<li class="menuLi cur"><a href="#"> <i class="icon iplay"></i>
								正在播放
						</a></li>
						<li class="menuLi"><a href="#"> <i class="icon ihst"></i>
								播放历史
						</a></li>
						<li class="menuLi"><a href="#"> <i class="icon ishouc"></i>
								我收藏的单曲
						</a></li>
					</ul>
					<div class="collectOut">
						<span class="colS">我创建的精选集</span> <a href="#" class="colA"></a>
					</div>
					<div class="cellectList"></div>
					<audio id="audio"
						src="http://zjdx1.sc.chinaz.com/Files/DownLoad/sound1/201507/6065.mp3"></audio>
				</div>
				<div class="mainBody col-xs-11 col-sm-11 col-md-8">
					<div class="playHd">
						<div class="phInner">
							<div class="col">歌曲(50)</div>
							<div class="col">演唱者</div>
							<div class="col">专辑</div>
						</div>
					</div>
					<div class="playBd">
						<div class="scrollView">
							<!-- <div class="scroll"></div> -->
							<ul class="songUL">
								
							</ul>
						</div>
					</div>
					<div class="playFt">
						<div class="track">
							<div class="uiCheck">
								<input class="checkAll" type="checkbox" select="0">
							</div>
							<div class="uiItem">
								<a href="#" class="itIcon itDele">删除</a>
							</div>
							<div class="uiItem">
								<a href="#" class="itIcon itShou">收藏</a>
							</div>
							<div class="uiItem">
								<a href="#" class="itIcon itJin">添加到精选集</a>
							</div>
							<div class="uiItem">
								<a href="#" class="itIcon itMore">更多</a>
							</div>
						</div>
					</div>
					<!-- 分页 -->
					<div class="pageQuery" id="pageQuery" style="margin-left: auto;">
						<div style="text-align: center">
							<ul id="pageLimit"></ul> 
						</div>
					</div>
				</div>
				<div class="mainOuther col-xs-1 col-sm-1 col-md-2">
					<div class="albumCover">
						<a href="#"> <img src="<%=basePath%>images/6.jpg" id="canvas1">
						</a>
					</div>
					<div class="albumSale">
						<a href="#" class="asA"> <img src="">
						</a>
					</div>
					<div id="lyr"></div>
				</div>
			</div>
		</div>
		<div class="bottom">
			<div class="playerWrap">
				<div class="playerCon">
					<a href="#" class="pbtn prevBtn"></a>
					<a href="#" class="pbtn playBtn" isplay="0"></a> 
					<a href="#" class="pbtn nextBtn"></a> 
					<a href="#" class="mode"></a>
				</div>
				<div class="playInfo">
					<div class="trackInfo">
						<a href="#" class="songName">漂洋过海来看你(Live)</a> - 
						<a href="#" class="songPlayer">刘明湘</a>
						<div class="trackCon">
							<a href="#" class="tc1"></a> 
							<a href="#" class="tc2"></a> 
							<a href="#" class="tc3"></a>
						</div>
					</div>
					<div class="playerLength">
						<div class="position">00:00</div>
						<div class="progress">
							<div class="pro1"></div>
							<div class="pro2">
								<a href="#" class="dian"></a>
							</div>
						</div>
						<div class="duration">03:35</div>
					</div>
				</div>
				<div class="vol">
					<a href="#" class="pinBtn"></a>
					<div class="volm">
						<div class="volSpeaker"></div>
						<div class="volControl">
							<a href="#" class="dian2"></a>
						</div>
					</div>
				</div>
			</div>
			<!-- <div style="text-align: center; margin: 50px 0; font: normal 14px/24px 'MicroSoft YaHei';">
				<p>适用浏览器：360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗. 不支持IE8及以下浏览器。</p>
			</div> -->
		</div>
</body>
</html>