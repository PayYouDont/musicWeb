var songArray = new Array();//当前歌曲id列表
var songIndex;//当前歌曲
$(function(){
	initSongList();
	searchMusicByKey();
	toLogin();
	playNext();
});
//初始化歌单
function initSongList(){
	var newSongTopUrl = "http://music.qq.com/musicbox/shop/v3/data/hit/hit_newsong.js";
	var allSongTopUrl = "http://music.qq.com/musicbox/shop/v3/data/hit/hit_all.js";
	var songList = {newSong:newSongTopUrl,allSong:allSongTopUrl};
	$.ajax({
		  type: "get",
		  async: false,
		  url: songList.newSong,
		  dataType: "jsonp",
		  jsonp: "callback",
		  jsonpCallback: "JsonCallback",
		  scriptCharset: 'GBK',//设置编码
		  success: function(data) {
			  data = JSON.parse(JSON.stringify(data)).songlist;
			  var html = "";
			  songArray = new Array();
			  for(var i=0;i<data.length;i++){
				  var id = data[i].id;
				  songArray.push(id)
				  var albumId = data[i].albumId;//专辑id
				  var singerName = data[i].singerName;
				  var songName = data[i].songName;
				  var singerId = data[i].singerId;
				  var albumName = data[i].albumName;//专辑名称
				  var song = {
						  songName:songName,
						  albumName:albumName,
						  songId:id,
						  albumId:albumId,
						  singerId:singerId,
						  singer:singerName,
				      }
				  html += getSongHtml(song, i);
			  }
			  $(".songUL").html(html);
				 start();
		  },
		  error: function() {
		    alert('fail');
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
	var url = basePath+"rest/musicApiAction/searchMusic";
	$.ajax({
		url:url,
		type:"post",
		data:{name:searcMsg,number:10},
		dataType:"json", 
		success:function(json){
			if(json.success){
				 var data = JSON.parse(json.data)
				 var list = data.data.song.list;
				 var html = "";
				 songArray = new Array();
				 for(var i=0;i<list.length;i++){
					 var f = list[i].f;//歌曲信息(97773|晴天 (;)|4558|周杰伦|8220|叶惠美|2186317|269|9|1|0|10793267|4319991|320000|0|30143423|31518872|5871273|6308305|0|0039MnYb0qxYhV|0025NhlN2yWrP4|000MkMni19ClKG|0|4009)
					 var fsinger = list[i].fsinger;//歌手名
					 var fsong = list[i].fsong;//歌曲名
					 var albumName = list[i].albumName_hilight;//专辑名称
					 var infoArr = f.split("|");
					 var songId = infoArr[0];//歌曲id
					 songArray.push(songId);
					 var albumId = infoArr[4];//专辑id
					 var singerId = infoArr[2];//歌手id
					 var song = {
							 f:f,
							 fsinger:fsinger,
							 songName:fsong,
							 albumName:albumName,
							 songId:songId,
							 albumId:albumId,
							 singerId:singerId,
							 singer:fsinger
					 	}
					 html += getSongHtml(song,i);
				 }
				 $(".songUL").html(html);
				 start();
			}else{
				alert("没有搜索到")
			}
		}
	})
}
//获取音乐html
function getSongHtml(song,index){
	var html = '<li class="songList" data-songinfo="'+song+'">'+
				 '<div class="songLMain">'+
					'<div class="check">'+
						'<input class="checkIn" type="checkbox" select="0">'+
					'</div>'+
					'<div class="start">'+
						'<em sonN="'+song.songId+'">'+(index+1)+'</em>'+
						'</div>'+
					'<div class="songBd">'+
						'<div class="col colsn">'+song.songName+'</div>'+
						'<div class="col colcn">'+song.singer+'</div>'+
						'<div class="col">'+song.albumName+'</div>'+
					'</div>'+
					'<div class="control">'+
						'<a class="cicon love"></a>'+ 
						'<a class="cicon more" style="display: none"></a>'+
						'<a class="cicon dele"style="display: none"></a>'+
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
			songIndex = sid;
			play(sid)
		});
}
function play(sid){
	$("#audio").attr("src", 'http://ws.stream.qqmusic.qq.com/'+sid+'.m4a?fromtag=46');
	audio = document.getElementById("audio");// 获得音频元素
	 /*显示歌曲总长度 */
	if (audio.paused) {
		audio.play();
	} else
		audio.pause();
	audio.addEventListener('timeupdate', updateProgress,false);
	audio.addEventListener('play', audioPlay, false);
	audio.addEventListener('pause', audioPause, false);
	audio.addEventListener('ended', audioEnded, false);
	/* 播放歌词 */
	getReady1(sid);// 准备播放
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
	for(var i=0;i<ems.length;i++){
		var sonN = $(ems[i]).attr("sonN");
		if(sid==sonN){
			em = ems[i];
			break;
		}
	}
	$(em).css({
				"background" : 'url("css/images/T1X4JEFq8qXXXaYEA_-11-12.gif") no-repeat',
				"color" : "transparent"
				});
	$(em).parent().parent().parent().append(html);
	$(em).parent().parent().parent().css("background-color", "#f0f0f0");

	/* 底部显示歌曲信息 */
	var songName = $(em).parent().parent().find(".colsn").html();
	var singerName = $(em).parent().parent().find(".colcn").html();
	$(".songName").html(songName);
	$(".songPlayer").html(singerName);
	/* 换右侧图片 */
	var url = 'http://imgcache.qq.com/music/photo/album_300/'+(sid%100)+'/300_albumpic_'+sid+'_0.jpg';
	$("#canvas1").attr("src", url);
	$("#canvas1").load(function() {
		loadBG();
	});
	// setTimeout('loadBG()',100);
	$(".blur").css("opacity", "0");
	$(".blur").animate({
		opacity : "1"
	}, 1000);
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
function playNext(){
	/* 切歌 */
	$(".prevBtn").click(function() {
		var prev = 0;
		for(var i=0;i<songArray.length;i++){
			if(songIndex==songArray[i]){
				if(i!=0){
					prev = songArray[i-1];
				}else{
					prev = songArray[songArray.length-1];
				}
				break;
			}
		}
		$(".start em[sonN=" + prev + "]").click();
	});
	//下一首
	$(".nextBtn").click(function() {
		var next = 0;
		for(var i=0;i<songArray.length;i++){
			if(songIndex==songArray[i]){
				if(i!=songArray.length-1){
					next = songArray[i+1];
				}else{
					next = songArray[0];
				}
				break;
			}
		}
		$(".start em[sonN=" + next + "]").click();
	});

}
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
	for(var i=0;i<songArray.length;i++){
		if(songIndex==songArray[i]){
			if(i!=songArray.length-1){
				next = songArray[i+1];
			}else{
				next = songArray[0];
			}
			break;
		}
	}
	var item = setInterval(function() {
		if(songTime.toString()==curTime.toString()){
			play(next);
			clearInterval(item);
		}
	},1000)
}