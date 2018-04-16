package music.API.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import music.API.service.MusicApiService;
import music.util.JsonWrapper;

@Controller
@RequestMapping("/musicApiAction")
public class MusicApiAction {
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
			e.printStackTrace();
			return JsonWrapper.failureWrapper("搜索失败");
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
			e.printStackTrace();
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
	public HashMap<String, Object> downLoad(HttpServletRequest request, HttpServletResponse response, String sid,
			String songName) {
		String urlStr = "http://ws.stream.qqmusic.qq.com/C100" + sid + ".m4a?fromtag=0";
		this.service.downLoad(request, response, urlStr, songName);
		return JsonWrapper.successWrapper(null, "下载成功");
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
			e.printStackTrace();
			return JsonWrapper.failureWrapper("获取失败");
		}
	}
}
