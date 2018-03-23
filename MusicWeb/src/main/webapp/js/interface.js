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