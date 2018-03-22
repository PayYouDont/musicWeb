var date = new Date().getTime()-2*24*60*60*1000;
date = new Date(date)
date = FormatDate("yyyy-MM-dd",date);
date = "2018-03-16";
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

//newSongList
//https://u.y.qq.com/cgi-bin/musicu.fcg?callback=recom46165693867385316&g_tk=1945804241&jsonpCallback=recom46165693867385316&loginUin=471113255&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&data=%7B%22comm%22%3A%7B%22ct%22%3A24%7D%2C%22category%22%3A%7B%22method%22%3A%22get_hot_category%22%2C%22param%22%3A%7B%22qq%22%3A%22%22%7D%2C%22module%22%3A%22music.web_category_svr%22%7D%2C%22recomPlaylist%22%3A%7B%22method%22%3A%22get_hot_recommend%22%2C%22param%22%3A%7B%22async%22%3A1%2C%22cmd%22%3A2%7D%2C%22module%22%3A%22playlist.HotRecommendServer%22%7D%2C%22playlist%22%3A%7B%22method%22%3A%22get_playlist_by_category%22%2C%22param%22%3A%7B%22id%22%3A8%2C%22curPage%22%3A1%2C%22size%22%3A40%2C%22order%22%3A5%2C%22titleid%22%3A8%7D%2C%22module%22%3A%22playlist.PlayListPlazaServer%22%7D%2C%22new_song%22%3A%7B%22module%22%3A%22QQMusic.MusichallServer%22%2C%22method%22%3A%22GetNewSong%22%2C%22param%22%3A%7B%22type%22%3A0%7D%7D%2C%22new_album%22%3A%7B%22module%22%3A%22music.web_album_library%22%2C%22method%22%3A%22get_album_by_tags%22%2C%22param%22%3A%7B%22area%22%3A7%2C%22company%22%3A-1%2C%22genre%22%3A-1%2C%22type%22%3A-1%2C%22year%22%3A-1%2C%22sort%22%3A2%2C%22get_tags%22%3A1%2C%22sin%22%3A0%2C%22num%22%3A40%2C%22click_albumid%22%3A0%7D%7D%2C%22toplist%22%3A%7B%22module%22%3A%22music.web_toplist_svr%22%2C%22method%22%3A%22get_toplist_index%22%2C%22param%22%3A%7B%7D%7D%2C%22focus%22%3A%7B%22module%22%3A%22QQMusic.MusichallServer%22%2C%22method%22%3A%22GetFocus%22%2C%22param%22%3A%7B%7D%7D%7D








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
var date = new Date().getTime()-2*24*60*60*1000;
date = new Date(date)
date = FormatDate("yyyy-MM-dd",date);
date = "2018-03-16";
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