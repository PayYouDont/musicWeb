package music.API.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import music.API.service.MusicApiService;
import music.util.JsonWrapper;

@Controller
@RequestMapping("/musicApiAction")
public class MusicApiAction {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private MusicApiService service;

	/**
	 * @Title: searchMusic
	 * @Description: TODO(搜索)
	 * @param @param
	 *            name
	 * @param @param
	 *            number
	 * @param @return
	 *            设定文件
	 * @return HashMap<String,Object> 返回类型
	 * @throws @author
	 *             peiyongdong
	 * @date 2017年12月6日 上午10:49:52
	 */
	@RequestMapping("/searchMusic")
	@ResponseBody
	public HashMap<String, Object> searchMusic(String name, Integer number) {
		StringBuffer sb = new StringBuffer();
		try {
			sb = service.searchMusic(name, number);
			return JsonWrapper.successWrapper(sb);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper(e.getMessage(),e);
		}
	}

	/**
	 * @Title: getlyr
	 * @Description: TODO(获取歌词)
	 * @param @param
	 *            sid
	 * @param @return
	 *            设定文件
	 * @return HashMap<String,Object> 返回类型
	 * @throws @author
	 *             peiyongdong
	 * @date 2017年12月15日 下午2:27:15
	 */
	@RequestMapping("/getlyr")
	@ResponseBody
	public HashMap<String, Object> getlyr(String sid) {
		StringBuffer sb = new StringBuffer();
		String urlStr = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric.fcg?nobase64=1&musicid=" + sid
				+ "&callback=jsonp1&g_tk=5381&jsonpCallback=jsonp1&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
		try {
			sb = service.search(urlStr, "UTF-8");
			return JsonWrapper.successWrapper(sb);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper("获取失败");
		}
	}

	/**
	 * 下载歌曲
	 * 
	 * @param request
	 * @param response
	 * @param sid
	 * @param songName
	 * @return
	 */
	@RequestMapping("/toDownLoad")
	@ResponseBody
	public HashMap<String, Object> downLoad(HttpServletRequest request, HttpServletResponse response,String songmid,String vkey,String songName,int type) {
		String mp3Url = "http://dl.stream.qqmusic.qq.com/M500"+songmid+".mp3?vkey="+vkey+"&guid=8604243058&uin=0&fromtag=53";
		String mp3ProUrl = "http://dl.stream.qqmusic.qq.com/M800"+songmid+".mp3?vkey="+vkey+"&guid=8604243058&uin=0&fromtag=53";
		String url = "";
		if(type==0) {
			url = mp3Url;
		}else if(type==1) {
			url = mp3ProUrl;
		}
		try {
			this.service.downLoad(request, response, url, songName);
			return JsonWrapper.successWrapper(null, "下载成功");
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper("下载失败");
		}
	}

	@RequestMapping("/getVkey")
	@ResponseBody
	public HashMap<String, Object> getVkey(String songmid) {
		// https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?g_tk=5381&jsonpCallback=MusicJsonCallback5068780022221746&loginUin=0&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&cid=205361747&callback=MusicJsonCallback5068780022221746&uin=0&songmid=000edAg12jLBrN&filename=C400000edAg12jLBrN.m4a&guid=8604243058
		StringBuffer sb = new StringBuffer();
		String urlStr = "https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg?g_tk=5381&jsonpCallback=MusicJsonCallback5068780022221746&loginUin=0&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&cid=205361747&callback=MusicJsonCallback5068780022221746&uin=0&songmid="+songmid+"&filename=C400"+songmid+".m4a&guid=8604243058";
		try {
			sb = service.getVkey(urlStr);
			return JsonWrapper.successWrapper(sb);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonWrapper.failureWrapper("获取失败");
		}
	}
}
