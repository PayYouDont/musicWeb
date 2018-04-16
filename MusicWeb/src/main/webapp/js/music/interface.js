var date = new Date().getTime()-2*24*60*60*1000;
date = new Date(date)
date = FormatDate("yyyy-MM-dd",date);
//date = "2018-03-21";
//歌曲列表接口
var listUrl = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?" +
		"tpl=3&page=detail&date="+date+"&topid=27&type=top&" +
		"song_begin=0&song_num=30&g_tk=5381&jsonpCallback=MusicJsonCallbacktoplist&" +
		"loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&" +
		"notice=0&platform=yqq&needNewCode=0";
//歌曲封面
//'https://y.gtimg.cn/music/photo_new/T002R300x300M000'+songImg+'.jpg?max_age=2592000';


//http://dl.stream.qqmusic.qq.com/C400000edAg12jLBrN.m4a?vkey=B96E257FBFE7C4DAC213D6C322839B6C8F21D4D3B9FC2A181A2DDB78CBB88870B37CE373376C77A6F7209102251C2AF09F9286B1C583AA40&guid=8604243058&uin=0&fromtag=66

//单首歌曲播放地址m4a格式
//于2018年4月16日19:21:15更新，qq音乐播放接口增加vkey

//获取m4a格式播放地址
function getM4a(songmid,vkey){
	return 'http://dl.stream.qqmusic.qq.com/C400'+songmid+'.m4a?vkey='+vkey+'&guid=8604243058&uin=0&fromtag=66'
}
//获取Mp3普通格式播放地址
function getMp3(songmid,vkey){
	
	return 'http://dl.stream.qqmusic.qq.com/M500'+songmid+'.mp3?vkey='+vkey+'&guid=8604243058&uin=0&fromtag=53'
}
//获取mp3高品质格式播放地址
function getMp3Pro(songmid,vkey){
	return 'http://dl.stream.qqmusic.qq.com/M800'+songmid+'.mp3?vkey='+vkey+'&guid=8604243058&uin=0&fromtag=53'
}

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
		var songmid = song.songmid;
		songListObj[songmid] = song;
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

/**
 * 点击除指定元素之外的地方隐藏该元素
 * @param target=触发显示的div(可以是元素id、class或者某个元素)
 * @param close=被隐藏的元素的id
 * @returns
 */
function hideElement(target, close) {
	$(document).bind('click', function(e) {
		var e = e || window.event; //浏览器兼容性 
		var elem = e.target || e.srcElement;
		while (elem) { //循环判断至跟节点，防止点击的是div子元素 
			if ((elem.className || elem.id) && (elem.id == close || elem.id == target || elem == target || elem.className.indexOf(target) != -1)) {
				return;
			}
			elem = elem.parentNode;
		}
		$("#" + close).hide()
	});
}