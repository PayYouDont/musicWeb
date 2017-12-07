$(function(){
	searchMusicByKey();
});
//键盘回车搜索
function searchMusicByKey(){
	$(".searchTxt").keydown(function(e){
		var key =  e.which;
		if(key==13){
			searchMusic();
		}
	})
}
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
				 console.log(data);
				 var list = data.data.song.list;
				 var html = "";
				 for(var i=0;i<list.length;i++){
					 var f = list[i].f;//歌曲信息(97773|晴天 (;)|4558|周杰伦|8220|叶惠美|2186317|269|9|1|0|10793267|4319991|320000|0|30143423|31518872|5871273|6308305|0|0039MnYb0qxYhV|0025NhlN2yWrP4|000MkMni19ClKG|0|4009)
					 var fsinger = list[i].fsinger;//歌手名
					 var fsong = list[i].fsong;//歌曲名
					 var albumName = list[i].albumName_hilight;//专辑名称
					 var infoArr = f.split("|");
					/* var ssong = infoArr[1];//歌曲名
					 var songtype = ssong.substring(ssong.indexOf("("),ssong.length);
					 console.log(songtype)
					 if(songtype.length>3){
						 fsong = ssong;
					 }*/
					 var songId = infoArr[0];//歌曲id
					 var albumId = infoArr[4];//专辑id
					 var singerId = infoArr[2];//歌手id
					 var song = {
							 f:f,
							 fsinger:fsinger,
							 fsong:fsong,
							 albumName:albumName,
							 songId:songId,
							 albumId:albumId,
							 singerId:singerId,
					 	}
					 html += '<li class="songList" data-songinfo="'+song+'">'+
								'<div class="songLMain">'+
									'<div class="check">'+
										'<input class="checkIn" type="checkbox" select="0">'+
									'</div>'+
									'<div class="start">'+
										'<em sonN="'+songId+'">'+(i+1)+'</em>'+
										'</div>'+
									'<div class="songBd">'+
										'<div class="col colsn">'+fsong+'</div>'+
										'<div class="col colcn">'+fsinger+'</div>'+
										'<div class="col">'+albumName+'</div>'+
									'</div>'+
									'<div class="control">'+
										'<a class="cicon love"></a>'+ 
										'<a class="cicon more" style="display: none"></a>'+
										'<a class="cicon dele"style="display: none"></a>'+
									'</div>'+
							   '</div>'+
						 '</li>';
				 }
				 $(".songUL").html(html);
				 start();
			}else{
				alert("没有搜索到")
			}
		}
	})
}
function getSong(sid){
	var url = basePath+"rest/musicApiAction/getSongById";
	$.ajax({
		url:url,
		type:"post",
		data:{sid:sid},
		dataType:"json", 
		async:false, 
		success:function(json){
			console.log(json)
		}
	});
}
function start(){
	/* 点击列表播放按钮 */
	$(".start em").click(function() {
						/* 开始放歌 */
						var sid = $(this).attr("sonN");
						songIndex = sid;
						$("#audio").attr("src", 'http://ws.stream.qqmusic.qq.com/'+sid+'.m4a?fromtag=46');
						audio = document.getElementById("audio");// 获得音频元素
						/* 显示歌曲总长度 */
						if (audio.paused) {
							audio.play();
						} else
							audio.pause();
						audio.addEventListener('timeupdate', updateProgress,
								false);

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
						$(this)
								.css(
										{
											"background" : 'url("css/images/T1X4JEFq8qXXXaYEA_-11-12.gif") no-repeat',
											"color" : "transparent"
										});
						$(this).parent().parent().parent().append(html);
						$(this).parent().parent().parent().css(
								"background-color", "#f0f0f0");

						/* 底部显示歌曲信息 */
						var songName = $(this).parent().parent().find(".colsn")
								.html();
						var singerName = $(this).parent().parent().find(
								".colcn").html();
						$(".songName").html(songName);
						$(".songPlayer").html(singerName);
						/* 换右侧图片 */
						//http://imgcache.qq.com/music/photo/album_300/20/300_albumpic_140820_0.jpg
						//http://imgcache.qq.com/music/photo/album_250/73/250_albumpic_97773_0.jpg
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

					});
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
		
	// alert(lytext[2]);
	// alert(lytime[2]);
	sortAr();
	// $("#lyr").html("");
	// for(var j=0;j<lytext.length;j++)
	// {
	// document.getElementById("lyr").innerHTML+=lytime[j]+lytext[j]+"<br>";
	// }
	scrollBar();
}
