var date = new Date().getTime()-3*24*60*60*1000;
date = new Date(date)
date = FormatDate("yyyy-MM-dd",date);
//date = "2018-03-21";
//歌曲列表接口
var listUrl = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?" +
		"tpl=3&page=detail&date="+date+"&topid=4&type=top&" +
		"song_begin=0&song_num=30&g_tk=5381&jsonpCallback=MusicJsonCallbacktoplist&" +
		"loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&" +
		"notice=0&platform=yqq&needNewCode=0";
//单首歌曲
//var songUrl = 'http://ws.stream.qqmusic.qq.com/C100'+startid+'.m4a?fromtag=0';

//歌曲封面
//'https://y.gtimg.cn/music/photo_new/T002R300x300M000'+songImg+'.jpg?max_age=2592000';


var warning = "本网站仅个人学习、研究使用，请勿用于商业用途。" +
			  "网站内容均来自互联网，如有侵权请联系删除!!";

function Warning(msg){
	layer.open({
		  title: '<span style="color:red;font-size:20px;">警告</span>',
		  content: msg
		});    
}


var songListObj;

var getSongListObj = function(songArray){
	songListObj = {};//清空当前数据
	for(var i=0;i<songArray.length;i++){
		var song = songArray[i];
		var songid = song.songId;
		songListObj[songid] = song;
	}
}


function FormatDate(fmt,date)   
{ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 


/*
[ti:老街]
[ar:李荣浩]
[al:在线热搜（华语）系列3]
[by:]
[offset:0]
[00:00.10]老街 - 李荣浩
[00:00.20]词：李荣浩
[00:00.30]曲：李荣浩
[00:00.40]
[00:16.99]一张褪色的照片
[00:20.11]好像带给我一点点怀念
[00:23.92]巷尾老爷爷卖的热汤面
[00:27.90]味道弥漫过旧旧的后院
[00:31.89]流浪猫睡熟在摇晃秋千
[00:35.82]夕阳照了一遍他眯着眼
[00:40.32]那张同桌寄的明信片
[00:44.00]安静的躺在课桌的里面
[00:49.05]快要过完的春天
[00:51.86]还有雕刻着图案的门帘
[00:55.93]窄窄的长长的过道两边
[00:59.79]老房子依然升起了炊烟
[01:03.78]刚刚下完了小雨的季节
[01:07.84]爸妈又一起走过的老街
[01:12.27]记不得哪年的哪一天
[01:15.96]很漫长又很短暂的岁月
[01:20.33]现在已经回不去
[01:24.27]早已流逝的光阴
[01:28.21]手里的那一张渐渐模糊不清的车票
[01:33.01]成了回忆的信号
[01:37.88]忘不掉的是什么我也不知道
[01:42.44]想不起当年模样
[01:46.29]看也看不到 去也去不了的地方
[01:54.01]也许那老街的腔调是属于我的忧伤
[02:02.30]嘴角那点微笑越来越勉强
[02:08.65]
[02:09.99]忘不掉的是什么我也不知道
[02:14.22]放不下熟悉片段
[02:18.31]回头望一眼 已经很多年的时间
[02:25.88]透过手指间看着天
[02:30.25]我又回到那老街
[02:34.30]靠在你们身边渐行渐远
[02:42.31]
[03:10.95]快要过完的春天
[03:13.95]还有雕刻着图案的门帘
[03:17.88]窄窄的长长的过道两边
[03:21.87]老房子依然升起了炊烟
[03:25.90]刚刚下完了小雨的季节
[03:29.91]爸妈又一起走过的老街
[03:34.26]记不得哪年的哪一天
[03:37.88]很漫长又很短暂的岁月
[03:42.31]现在已经回不去
[03:46.31]早已流逝的光阴
[03:50.05]手里的那一张渐渐模糊不清的车票
[03:55.01]成了回忆的信号
[03:59.76]忘不掉的是什么我也不知道
[04:04.19]想不起当年模样
[04:08.26]看也看不到 去也去不了的地方
[04:15.87]也许那老街的腔调是属于我的忧伤
[04:24.26]嘴角那点微笑越来越勉强
[04:31.88]忘不掉的是什么我也不知道
[04:36.24]放不下熟悉片段
[04:40.22]回头望一眼 已经很多年的时间
[04:47.84]透过手指间看着天
[04:52.33]我又回到那老街
[04:56.26]靠在你们身边渐行渐远*/