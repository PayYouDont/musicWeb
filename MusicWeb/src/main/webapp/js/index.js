var totalPages = 1;//总共多少页
var count = 10;//每页展示的数据条数
var songArray = new Array();//当前歌曲id列表
var songIndex;//当前歌曲
var songList = new Array();
$(function(){
	initSongList();
	searchMusicByKey();
	toLogin();
	Warning(warning);
});
//初始化歌单
function initSongList(){
	var newSongTopUrl = listUrl;
	var allSongTopUrl = "http://music.qq.com/musicbox/shop/v3/data/hit/hit_all.js";
	var songList = {newSong:newSongTopUrl,allSong:allSongTopUrl};
	$.ajax({
		  type: "get",
		  async: false,
		  url: songList.newSong,
		  dataType: "jsonp",
		  jsonp: "callback",
		  jsonpCallback: "MusicJsonCallbacktoplist",
		  //scriptCharset: 'GBK',//设置编码
		  success: function(data) {
			  songList = JSON.parse(JSON.stringify(data)).songlist;
			  startid = songList[0].data.songmid;
			  //加载第一首歌曲
			  var src = 'http://ws.stream.qqmusic.qq.com/C100'+startid+'.m4a?fromtag=0';
			  //显示歌单数量
			  $("#songCount").html("歌曲("+songList.length+")");
			  //下放播放器显示歌曲名字
			  $(".songName").html(songList[0].data.songname);
			  //下放播放器显示歌手名字
			  $(".songPlayer").html(songList[0].data.singer[0].name);
			  totalPages = Math.ceil(songList.length/count);
			  //分页
			  QueryPage(songList,1)
			  initPage(totalPages,songList);
			  //自动播放
			  play(startid,songList[0].data.albummid);
			  start();
		  },
		  error: function() {
		    alert('fail');
		  }
		});
}
//查询结果分页
function QueryPage(list,page){
	var data = getPageData(list,page);
	var html = "";
	  	songArray = new Array();
	  	for(var i=0;i<data.length;i++){
	  		var id = data[i].data.strMediaMid;
	  		var albumId = data[i].data.albummid;//专辑id
	  		var singerName = data[i].data.singer[0].name;
	  		var songName = data[i].data.songname;
	  		var singerId = data[i].data.singer[0].id;
	  		var albumName = data[i].data.albumname;//专辑名称
	  		var songImg = data[i].data.albummid;//专辑id
	  		var song = {
					  songName:songName,
					  albumName:albumName,
					  songId:id,
					  albumId:albumId,
					  singerId:singerId,
					  singer:singerName,
					  songImg:songImg
		      	}
	  		songArray.push(song)
	  		html += getSongHtml(song, i);
	  	}
	  	$(".songUL").html(html);
}
function getPageData(list,page){
	var data = new Array();
	var size = page*count<=list.length?page*count:list.length;
	for(var i=(page-1)*count;i<size;i++){
		data.push(list[i]);
	}
	return data;
}
//初始分页
function initPage(totalPages,list,search){
	$('#pageLimit').bootstrapPaginator({
	      currentPage: 1,//当前的请求页面。
	      totalPages: totalPages,//一共多少页。
	      count:"normal",//页眉的大小。
	      bootstrapMajorVersion: 3,//bootstrap的版本要求。
	      alignment:"right",
	      numberOfPages:5,//展示几个分页数量
	      itemTexts: function (type, page, current) {
	         switch (type) {
	         case "first": return "首页";
	         case "prev": return "上一页";
	         case "next": return "下一页";
	         case "last": return "末页";
	         case "page": return page;
	         }
	     },
	     onPageClicked: function (event,originalEvent,type,page){
	    	 if(search){
	    		 QueryPageToSearch(list,page)
	    	 }else{
	    		 QueryPage(list,page)
	    	 }
	    	 start();
	    }
	 });
	}
//登录
function toLogin(){
	$(".loginA").off("click").on("click",function(){
		window.location.href = basePath+"rest/userAction/toLogin";
	})
}
//键盘回车搜索
function searchMusicByKey(){
	$(".searchTxt").keydown(function(e){
		var key =  e.which;
		if(key==13){
			searchMusic();
		}
	})
}
//搜索音乐
function searchMusic(){
	var searcMsg = $(".searchTxt").val();
	if(searcMsg.trim()==""){
		return;
	}
	searcMsg = encodeURI(searcMsg);
	var url = basePath+"rest/musicApiAction/searchMusic";
	$.ajax({
		url:url,
		type:"post",
		data:{name:searcMsg,number:30},
		dataType:"json", 
		success:function(json){
			if(json.success){
				 var data = JSON.parse(json.data)
				 songList = data.data.song.list;
				 totalPages = Math.ceil(songList.length/count);
				 QueryPageToSearch(songList,1)
				 initPage(totalPages,songList,"search");
				 start();
			}else{
				alert("没有搜索到")
			}
		}
	})
}
function QueryPageToSearch(songList,page){
	 var list = getPageData(songList,page)
	 var html = "";
	 songArray = new Array();
	 for(var i=0;i<list.length;i++){
		 var f = list[i].f;//歌曲信息(97773|晴天 (;)|4558|周杰伦|8220|叶惠美|2186317|269|9|1|0|10793267|4319991|320000|0|30143423|31518872|5871273|6308305|0|0039MnYb0qxYhV|0025NhlN2yWrP4|000MkMni19ClKG|0|4009)
		 var fsinger = list[i].fsinger;//歌手名
		 var fsong = list[i].fsong;//歌曲名
		 var albumName = list[i].albumName_hilight;//专辑名称
		 var infoArr = f.split("|");
		 var songId = infoArr[0];//歌曲id
		 var albumId = infoArr[4];//专辑id
		 var singerId = infoArr[2];//歌手id
		 var newid = f.split("|")[20];//新接口歌曲id
		 var songImg = f.split("|")[22];//歌曲封面
		 var song = {
				 f:f,
				 fsinger:fsinger,
				 songName:fsong,
				 albumName:albumName,
				 songId:newid,
				 albumId:albumId,
				 singerId:singerId,
				 singer:fsinger,
				 songImg:songImg
		 	}
		 songArray.push(song);
		 html += getSongHtml(song,i);
	 }
	 $(".songUL").html(html);
}
//获取音乐html
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
						'<a class="cicon download" href='+url+' download=""><span class="glyphicon glyphicon-arrow-down"></span></a>'+
						'<a class="cicon love"></a>'+ 
						'<a class="cicon more"></a>'+
						'<a class="cicon dele"></a>'+
					'</div>'+
			   '</div>'+
			'</li>';
	return html
}

function start(){
	/* 点击列表播放按钮 */
	$(".start em").click(function() {
		/* 开始放歌 */
			var sid = $(this).attr("sonN");
			var songImg = $(this).data("songimg");
			songIndex = sid;
			play(sid,songImg)
		});
}
function play(sid,songImg){
	//添加歌曲源
	$("#audio").attr("src", 'http://ws.stream.qqmusic.qq.com/C100'+sid+'.m4a?fromtag=0');
	// 获得音频元素
	audio = document.getElementById("audio");
	 /*显示歌曲总长度 */
	if (audio.paused) {
		audio.play();
	} else{
		audio.pause();
	}
	audio.addEventListener('timeupdate', updateProgress,false);
	audio.addEventListener('play', audioPlay, false);
	audio.addEventListener('pause', audioPause, false);
	audio.addEventListener('ended', audioEnded, false);
	/* 播放歌词 */
	//getReady1(sid);// 准备播放
	mPlay();// 显示歌词
	// 对audio元素监听pause事件
	/* 外观改变 */
	var html = "";
	html += '<div class="manyou">';
	html += '	<a href="#" class="manyouA">漫游相似歌曲</a>';
	html += '</div>';
	$(".start em").css({"background" : "","color" : ""});
	$(".manyou").remove();
	$(".songList").css("background-color", "#f5f5f5");
	var ems = $(".start em");
	var em;
	for(var i=0;i<ems.length;i++){
		var sonN = $(ems[i]).attr("sonN");
		if(sid==sonN){
			em = ems[i];
			break;
		}
	}
	$(em).css({"background" : 'url("css/images/T1X4JEFq8qXXXaYEA_-11-12.gif") no-repeat',"color" : "transparent"});
	$(em).parent().parent().parent().append(html);
	$(em).parent().parent().parent().css("background-color", "#f0f0f0");

	/* 底部显示歌曲信息 */
	var songName = $(em).parent().parent().find(".colsn").html();
	var singerName = $(em).parent().parent().find(".colcn").html();
	$(".songName").html(songName);
	$(".songPlayer").html(singerName);
	/* 换右侧图片 */
	var url = "";
	if(songImg){
		url = 'https://y.gtimg.cn/music/photo_new/T002R300x300M000'+songImg+'.jpg?max_age=2592000';
		$("#canvas1").attr("src", url);
	}
	$("#canvas1").load(function() {
		//loadBG();
	});
	// setTimeout('loadBG()',100);
	$(".blur").css("opacity", "0");
	$(".blur").animate({opacity : "1"}, 1000);
}
function getReady1(sid){// 在显示歌词前做好准备工作
	var ly = "";
	var url = basePath+"rest/musicApiAction/getlyr";
	$.ajax({
		url:url,
		type:"post",
		data:{sid:sid},
		dataType:"json", 
		async:false, 
		success:function(json){
			if(json.success){
				ly = json.data.toString();// 得到歌词
			}else{
				ly = "";
			}
		}
	});
	if (ly == "") {
		$("#lry").html("本歌暂无歌词！");
	}
	var arrly = ly.split(".");// 转化成数组
	tflag = 0;
	for (var i = 0; i < lytext.length; i++) {
		lytext[i] = "";
	}
	for (var i = 0; i < lytime.length; i++) {
		lytime[i] = "";
	}
	$("#lry").html(" ");
	document.getElementById("lyr").scrollTop = 0;
	
	for (var i = 0; i < arrly.length; i++){
		sToArray(arrly[i]);
	}
	sortAr();
	scrollBar();
}
//上一首
function playPrev(){
	
	/* 切歌 */
		var prev = 0;
		for(var i=0;i<songArray.length;i++){
			if(songIndex==songArray[i].songId){
				if(i!=0){
					prev = songArray[i-1].songId;
				}else{
					prev = songArray[songArray.length-1].songId;
				}
				break;
			}
		}
		$(".start em[sonN=" + prev + "]").click();
}
function playNext(){
	//下一首
	var next = 0;
	for(var i=0;i<songArray.length;i++){
		if(songIndex==songArray[i].songId){
			if(i!=songArray.length-1){
				next = songArray[i+1].songId;
			}else{
				next = songArray[0].songId;
			}
			break;
		}
	}
	$(".start em[sonN=" + next + "]").click();
}
//更新歌曲进度条
function updateProgress(ev) {
	/* 显示歌曲总长度 */
	var songTime = calcTime(Math.floor(audio.duration));
	$(".duration").html(songTime);
	/* 显示歌曲当前时间 */
	var curTime = calcTime(Math.floor(audio.currentTime));
	$(".position").html(curTime);
	/* 进度条 */
	var lef = 678 * (Math.floor(audio.currentTime) / Math.floor(audio.duration));
	var llef = Math.floor(lef).toString() + "px";
	$(".dian").css("left", llef);
	//自动播放下一首
	var next = 0;
	var nextImg = "";
	for(var i=0;i<songArray.length;i++){
		if(songIndex==songArray[i].songId){
			if(i!=songArray.length-1){
				next = songArray[i+1].songId;
				nextImg = songArray[i+1].songImg;
			}else{
				next = songArray[0].songId;
				nextImg = songArray[0].songImg;
			}
			break;
		}
	}
	var item = setInterval(function() {
		if(songTime.toString()==curTime.toString()){
			play(next,nextImg);
			songIndex = next;
			clearInterval(item);
		}
	},1000)
}

