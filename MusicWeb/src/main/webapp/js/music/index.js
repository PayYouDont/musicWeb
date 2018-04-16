//总共多少页
var totalPages = 1;
// 每页展示的数据条数
var count = 10;
// 当前歌曲列表
var songArray = new Array();
// 当前歌曲
var songIndex;
// 歌曲总列表
var songList = new Array();
$(function() {
	initSongList();
	searchMusicByKey();
	toLogin();
	Warning(warning);
	jumpTime();
});
// 初始化歌单
function initSongList() {
	$.ajax({
		type : "get",
		async : false,
		url : listUrl,
		dataType : "jsonp",
		jsonp : "callback",
		jsonpCallback : "MusicJsonCallbacktoplist",
		// scriptCharset: 'GBK',//设置编码
		success : function(data) {
			songList = JSON.parse(JSON.stringify(data)).songlist;
			startid = songList[0].data.strMediaMid;
			songIndex = startid;
			// 加载第一首歌曲
			var src = 'http://ws.stream.qqmusic.qq.com/C100' + startid + '.m4a?fromtag=0';
			// 显示歌单数量
			$("#songCount").html("歌曲(" + songList.length + ")");
			// 下放播放器显示歌曲名字
			$(".songName").html(songList[0].data.songname);
			// 下放播放器显示歌手名字
			$(".songPlayer").html(songList[0].data.singer[0].name);
			totalPages = Math.ceil(songList.length / count);
			// 分页
			QueryPage(songList, 1)
			initPage(totalPages, songList);
			// 自动播放
			play(startid, songList[0].data.albummid);
			start();
		},
		error : function() {
			alert('fail');
		}
	});
}


// 查询结果分页
function QueryPage(list, page) {
	var data = getPageData(list, page);
	var html = "";
	songArray = new Array();
	for (var i = 0; i < data.length; i++) {
		var songmid = data[i].data.strMediaMid;// 歌曲播放id
		var albumId = data[i].data.albummid;// 专辑id
		var singerName = data[i].data.singer[0].name;
		var songName = data[i].data.songname;
		var singerId = data[i].data.singer[0].id;
		var albumName = data[i].data.albumname;// 专辑名称
		var songImg = data[i].data.albummid;// 专辑id
		var musicId = data[i].data.songid;// 歌曲id(歌词id);
		var song = {
			songName : songName,
			albumName : albumName,
			songId : songmid,
			albumId : albumId,
			singerId : singerId,
			singer : singerName,
			songImg : songImg,
			musicId:musicId
		}
		// addToSongListObj(song)
		songArray.push(song);
		getSongListObj(songArray);
		html += getSongHtml(song, i);
	}
	$(".songUL").html(html);
}
function getPageData(list, page) {
	var data = new Array();
	var size = page * count <= list.length ? page * count : list.length;
	for (var i = (page - 1) * count; i < size; i++) {
		data.push(list[i]);
	}
	return data;
}
// 初始分页
function initPage(totalPages, list, search) {
	$('#pageLimit').bootstrapPaginator({
		currentPage : 1,// 当前的请求页面。
		totalPages : totalPages,// 一共多少页。
		count : "normal",// 页眉的大小。
		bootstrapMajorVersion : 3,// bootstrap的版本要求。
		alignment : "right",
		numberOfPages : 5,// 展示几个分页数量
		itemTexts : function(type, page, current) {
			switch (type) {
			case "first":
				return "首页";
			case "prev":
				return "上一页";
			case "next":
				return "下一页";
			case "last":
				return "末页";
			case "page":
				return page;
			}
		},
		onPageClicked : function(event, originalEvent, type, page) {
			if (search) {
				QueryPageToSearch(list, page)
			} else {
				QueryPage(list, page)
			}
			start();
		}
	});
}
// 登录
function toLogin() {
	$(".loginA").off("click").on("click", function() {
		window.location.href = basePath + "rest/userAction/toLogin";
	})
}
// 键盘回车搜索
function searchMusicByKey() {
	$(".searchTxt").keydown(function(e) {
		var key = e.which;
		if (key == 13) {
			searchMusic();
		}
	})
}
// 搜索音乐
function searchMusic() {
	var searcMsg = $(".searchTxt").val();
	if (searcMsg.trim() == "") {
		return;
	}
	searcMsg = encodeURI(searcMsg);
	// 加载层
	var index = layer.load(0, {
		shade : false
	}); // 0代表加载的风格，支持0-2
	var url = basePath + "rest/musicApiAction/searchMusic";
	$.ajax({
		url : url,
		type : "post",
		data : {
			name : searcMsg,
			number : 50
		},
		dataType : "json",
		success : function(json) {
			if (json.success) {
				var data = JSON.parse(json.data)
				songList = data.data.song.list;
				totalPages = Math.ceil(songList.length / count);
				QueryPageToSearch(songList, 1)
				initPage(totalPages, songList, "search");
				start();
				layer.close(index);
			} else {
				alert("没有搜索到")
			}
		}
	})
}
function QueryPageToSearch(songList, page) {
	var list = getPageData(songList, page);
	var html = "";
	songArray = new Array();
	for (var i = 0; i < list.length; i++) {
		var f = list[i].f;// 歌曲信息(97773|晴天
							// (;)|4558|周杰伦|8220|叶惠美|2186317|269|9|1|0|10793267|4319991|320000|0|30143423|31518872|5871273|6308305|0|0039MnYb0qxYhV|0025NhlN2yWrP4|000MkMni19ClKG|0|4009)
		var fsinger = list[i].fsinger;// 歌手名
		var fsong = list[i].fsong;// 歌曲名
		var albumName = list[i].albumName_hilight;// 专辑名称
		var infoArr = f.split("|");
		var musicId = infoArr[0];// 歌曲id
		var albumId = infoArr[4];// 专辑id
		var singerId = infoArr[2];// 歌手id
		var songmid = f.split("|")[20];// 歌曲播放id
		var songImg = f.split("|")[22];// 歌曲封面
		var song = {
			f : f,
			fsinger : fsinger,
			songName : fsong,
			albumName : albumName,
			songId : songmid,
			albumId : albumId,
			singerId : singerId,
			singer : fsinger,
			songImg : songImg,
			musicId:musicId// 歌曲单独id(歌词展示需要)
		}
		// addToSongListObj(song)
		songArray.push(song);
		getSongListObj(songArray);
		html += getSongHtml(song, i);
	}
	$(".songUL").html(html);
}
// 获取音乐html
function getSongHtml(song,index){
	var url = 'http://ws.stream.qqmusic.qq.com/C100'+song.songId+'.m4a?fromtag=0';
	var html = '<li class="songList" data-songinfo="'+song+'">'+
				 '<div class="songLMain">'+
					'<div class="check">'+
						'<input class="checkIn" type="checkbox" select="0">'+
					'</div>'+
					'<div class="start">'+
						'<em sonN="'+song.songId+'" data-songimg='+song.songImg+'>'+(index+1)+'</em>'+
					'</div>'+
					'<div class="songBd">'+
						'<div class="col colsn">'+song.songName+'</div>'+
						'<div class="col colcn">'+song.singer+'</div>'+
						'<div class="col">'+song.albumName+'</div>'+
					'</div>'+
					'<div class="control">'+
						'<a data-songid="'+song.songId+'" data-songname="'+song.songName+'" class="cicon download" title="下载" onclick="toDownLoad(this);"></a>'+
						'<a class="cicon love" title="收藏"></a>'+ 
						'<a class="cicon more" title="更多"></a>'+
						'<a class="cicon dele" title="删除"></a>'+
					'</div>'+
			   '</div>'+
			'</li>';
	return html
}

function getVkey(songmid){
	var url = basePath + "rest/musicApiAction/getVkey";
	var vkey = "";
	$.ajax({
		url:url,
		type:"post",
		data:{songmid:songmid},
		async : false,
		dataType : "json",
		success:function(json){
			if(json.success){
				var data = json.data;
				data = data.substring(data.indexOf("(")+1,data.length-1);
				data = JSON.parse(data);
				vkey = data.data.items["0"].vkey;
			}
		}
	})
	return vkey;
}

function start() {
	/* 点击列表播放按钮 */
	$(".start em").click(function() {
		/* 开始放歌 */
		var sid = $(this).attr("sonN");
		var songImg = $(this).data("songimg");
		songIndex = sid;
		play(sid, songImg);
	});
}
function play(sid, songImg) {
	var vkey = getVkey(sid)
	// 添加歌曲源
	$("#audio").attr("src",'http://dl.stream.qqmusic.qq.com/C400'+sid+'.m4a?vkey='+vkey+'&guid=8604243058&uin=0&fromtag=66');
	// 获得音频元素
	audio = document.getElementById("audio");
	/* 显示歌曲总长度 */
	if (audio.paused) {
		audio.play();
	} else {
		audio.pause();
	}
	audio.addEventListener('timeupdate', updateProgress, false);
	audio.addEventListener('play', audioPlay, false);
	audio.addEventListener('pause', audioPause, false);
	audio.addEventListener('ended', audioEnded, false);
	/* 播放歌词 */
	var musicId = songListObj[sid].musicId;
	getReady1(musicId);// 准备播放
	mPlay();// 显示歌词
	// 对audio元素监听pause事件
	/* 外观改变 */
	var html = "";
	html += '<div class="manyou">';
	html += '	<a href="#" class="manyouA">漫游相似歌曲</a>';
	html += '</div>';
	$(".start em").css({
		"background" : "",
		"color" : ""
	});
	$(".manyou").remove();
	$(".songList").css("background-color", "#f5f5f5");
	var ems = $(".start em");
	var em;
	for (var i = 0; i < ems.length; i++) {
		var sonN = $(ems[i]).attr("sonN");
		if (sid == sonN) {
			em = ems[i];
			break;
		}
	}
	$(em).css({"background" : 'url("css/images/T1X4JEFq8qXXXaYEA_-11-12.gif") no-repeat',
				"color" : "transparent"});
	$(em).parent().parent().parent().append(html);
	$(em).parent().parent().parent().css("background-color", "#f0f0f0");

	/* 底部显示歌曲信息 */
	var songName = $(em).parent().parent().find(".colsn").html();
	var singerName = $(em).parent().parent().find(".colcn").html();
	$(".songName").html(songName);
	$(".songPlayer").html(singerName);
	/* 换右侧图片 */
	var url = "";
	if (songImg) {
		url = 'https://y.gtimg.cn/music/photo_new/T002R300x300M000'+songImg+'.jpg?max_age=2592000';
		$("#canvas1").attr("src", url);
	}
	$("#canvas1").load(function() {
		// loadBG();
	});
	// setTimeout('loadBG()',100);
	$(".blur").css("opacity", "0");
	$(".blur").animate({
		opacity : "1"
	}, 1000);
}
function getReady1(musicId) {// 在显示歌词前做好准备工作
	var ly = "";
	var url = basePath + "rest/musicApiAction/getlyr";
	$.ajax({
		url : url,
		type : "post",
		data : {
			sid : musicId
		},
		dataType : "json",
		async : false,
		success : function(json) {
			if (json.success) {
				var data = json.data;
				data = data.substring(7,data.length-1);
				data = JSON.parse(data);
				var lrc = data.lyric;
				ly = lrc.replace(/&#(\d+);/g, (str, match) => String.fromCharCode(match));
			}
		}
	});
	var arrly=ly.split("\n");// 转化成数组
  	// alert(arrly[1]);
  	tflag=0;
  	for( var i=0;i<lytext.length;i++){
		lytext[i]="";
	}
	for( var i=0;i<lytime.length;i++){
		lytime[i]="";
	}
  	$("#lry").html(" ");
  	document.getElementById("lyr").scrollTop=0;
	for(var i=0;i<arrly.length;i++) {
		var str = arrly[i];
	  	sToArray(str);
	}
	sortAr();
	scrollBar(); 
}
// 上一首
function playPrev() {
	var prev = 0;
	if (circleIndex < 2) {// 不是随机播放
		for (var i = 0; i < songArray.length; i++) {
			if (songIndex == songArray[i].songId) {
				if (i != 0) {
					prev = songArray[i - 1].songId;
				} else {
					prev = songArray[songArray.length - 1].songId;
				}
				break;
			}
		}
	} else {
		circleType[circleIndex].circlePlay();
	}
	$(".start em[sonN=" + prev + "]").click();
}
function playNext() {
	// 下一首
	var next = 0;
	if (circleIndex < 2) {// 不是随机播放
		for (var i = 0; i < songArray.length; i++) {
			if (songIndex == songArray[i].songId) {
				if (i != songArray.length - 1) {
					next = songArray[i + 1].songId;
				} else {
					next = songArray[0].songId;
				}
				break;
			}
		}
	} else {
		circleType[circleIndex].circlePlay();
	}
	$(".start em[sonN=" + next + "]").click();
}
// 更新歌曲进度条
function updateProgress(ev) {
	/* 显示歌曲总长度 */
	var songTime = calcTime(Math.floor(audio.duration));
	$(".duration").html(songTime);
	/* 显示歌曲当前时间 */
	var curTime = calcTime(Math.floor(audio.currentTime));
	$(".position").html(curTime);
	/* 进度条 */
	var count = $(".progress").width();
	var lef = count * (Math.floor(audio.currentTime) / Math.floor(audio.duration));
	var llef = Math.floor(lef).toString() + "px";
	$(".dian").css("left", llef);
	if (songTime.toString() == curTime.toString()) {
		circleType[circleIndex].circlePlay();
	}
}
// 循环类型
var circleType = {
	0 : {// 顺序播放
		background : "-181px",
		circlePlay : function() {
			var next = 0;
			var nextImg = "";
			for (var i = 0; i < songArray.length; i++) {
				if (songIndex == songArray[i].songId) {
					if (i != songArray.length - 1) {
						next = songArray[i + 1].songId;
						nextImg = songArray[i + 1].songImg;
					} else {
						next = songArray[0].songId;
						nextImg = songArray[0].songImg;
					}
					break;
				}
			}
			play(next, nextImg);
			songIndex = next;
		}
	},
	1 : {// 单曲循环
		background : "-201px",
		circlePlay : function() {
			var next = songIndex;
			var nextImg = $(".start em").data("songimg");
			play(next, nextImg);
			songIndex = next;
		}
	},
	2 : {// 随机播放
		background : "-221px",
		circlePlay : function() {
			var rand = parseInt(Math.random() * songArray.length);
			var next = songArray[rand].songId;
			var nextImg = songArray[rand].songImg;
			play(next, nextImg);
			songIndex = next;
		}
	}
}
// 循环
var circleIndex = 0;
function circle(a) {
	var circle = $(a).data("circle");
	if (circle > 1) {
		circle = 0;
	} else {
		circle++;
	}
	$(a).data("circle", circle);
	var bg = circleType[circle].background;
	$(a).css("background-position-y", bg);
	circleIndex = circle;
}
// 下载
function toDownLoad(a){
	var songid = $(a).data("songid");
	var songName = $(a).data("songname");
	
	window.location.href = basePath + "rest/musicApiAction/toDownLoad?sid="+songid+"&songName="+encodeURI(songName);
}
function jumpTime(){
	$(".progress").off("click").on("click",function(e){
		var width = $(".progress").width();
		var x = e.offsetX;
		// audio.duration 总时间
		var audio = document.getElementById("audio");
		var duration = audio.duration;// 总时间
		audio.currentTime = duration*(Math.floor(x)/Math.floor(width));
	})
}


/****************************************************************/
/* 显示歌词部分 */
var scrollt = 0;
var tflag = 0;// 存放时间和歌词的数组的下标
var lytext = new Array();// 放存汉字的歌词
var lytime = new Array();// 存放时间
var delay = 10;
var line = 0;
var scrollh = 0;
var songIndex = 2;
// 开始播放
function mPlay(){
	var ms = audio.currentTime;
	show(ms);
	window.setTimeout("mPlay()", 100)
}

// 显示歌词
function show(t){
	var div1 = document.getElementById("lyr");// 取得层
	document.getElementById("lyr").innerHTML = " ";// 每次调用清空以前的一次
	if (t < lytime[lytime.length - 1]){// 先舍弃数组的最后一个
		for (var k = 0; k < lytext.length; k++) {
			if (lytime[k] <= t && t < lytime[k + 1]) {
				scrollh = k * 25;// 让当前的滚动条的顶部改变一行的高度
				div1.innerHTML += "<font color=#f60 style=font-weight:bold>"
						+ lytext[k] + "</font><br>";
			} else if (t < lytime[lytime.length - 1])// 数组的最后一个要舍弃
				div1.innerHTML += lytext[k] + "<br>";
		}
	} else{// 加上数组的最后一个
		for (var j = 0; j < lytext.length - 1; j++){
			div1.innerHTML += lytext[j] + "<br>";
		}
		div1.innerHTML += "<font color=red style=font-weight:bold>"+ lytext[lytext.length - 1] + "</font><br>";
	}
}

// 设置滚动条的滚动
function scrollBar(){
	if (document.getElementById("lyr").scrollTop <= scrollh)
		document.getElementById("lyr").scrollTop += 1;
	if (document.getElementById("lyr").scrollTop >= scrollh + 50)
		document.getElementById("lyr").scrollTop -= 1;
	window.setTimeout("scrollBar()", delay);
}

//按时间重新排序时间和歌词的数组
function sortAr(){
	var temp = null;
	var temp1 = null;
	for (var k = 0; k < lytime.length; k++) {
		for (var j = 0; j < lytime.length - k; j++) {
			if (lytime[j] > lytime[j + 1]) {
				temp = lytime[j];
				temp1 = lytext[j];
				lytime[j] = lytime[j + 1];
				lytext[j] = lytext[j + 1];
				lytime[j + 1] = temp;
				lytext[j + 1] = temp1;
			}
		}
	}
}

//解析如“[02:02][00:24]没想到是你”的字符串前放入数组
function sToArray(str){
	var left = 0;// "["的个数
	var leftAr = new Array();
	for (var k = 0; k < str.length; k++) {
		if (str.charAt(k) == "[") {
			leftAr[left] = k;
			left++;
		}
	}
	if (left != 0) {
		for (var i = 0; i < leftAr.length; i++) {
			lytext[tflag] = str.substring(str.lastIndexOf("]")+1);// 放歌词
			lytime[tflag] = conSeconds(str.substring(leftAr[i] + 1,leftAr[i] + 6));// 放时间
			tflag++;
		}
	}
}
//把形如：01：25的时间转化成秒；
function conSeconds(t){
	var m = t.substring(0, t.indexOf(":"));
	var s = t.substring(t.indexOf(":") + 1);
	m = parseInt(m.replace(/0/, ""));
	var totalt = parseInt(m) * 60 + parseInt(s);
	return totalt;
}