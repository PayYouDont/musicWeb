package music.API.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioInputStream;

import org.apache.commons.io.output.WriterOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import music.API.service.MusicApiService;
import music.util.JsonWrapper;

@Controller
@RequestMapping("/musicApiAction")
public class MusicApiAction {
	@Resource(name = "musicApiService")
	private MusicApiService service;
	/**
	 * @Title: searchMusic 
	 * @Description: TODO(搜索) 
	 * @param @param name
	 * @param @param number
	 * @param @return    设定文件 
	 * @return HashMap<String,Object>    返回类型 
	 * @throws 
	 * @author peiyongdong
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
	 * @param @param sid
	 * @param @return    设定文件 
	 * @return HashMap<String,Object>    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2017年12月15日 下午2:27:15
	 */
	@RequestMapping("/getlyr")
	@ResponseBody
	public HashMap<String, Object> getlyr(String sid) {
		StringBuffer sb = new StringBuffer();
		String urlStr = "http://music.qq.com/miniportal/static/lyric/"+(new Integer(sid)%100)+"/"+sid+".xml";
		//https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric.fcg
		//https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric.fcg?nobase64=1&musicid=212877900&callback=jsonp1&g_tk=5381&jsonpCallback=jsonp1&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0

		try {
			sb = service.search(urlStr,"GB2312");
			return JsonWrapper.successWrapper(sb);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonWrapper.failureWrapper("获取失败");
		}
	}
	@RequestMapping("/down")
	@ResponseBody
	public HashMap<String, Object> down(HttpServletRequest request,String sid) {
		String urlStr = "http://ws.stream.qqmusic.qq.com/C100"+sid+".m4a?fromtag=0";
		String path = this.service.downLoad(request, urlStr);
		if("".equals(path)) {
			return JsonWrapper.failureWrapper("失败");
		}
		return JsonWrapper.successWrapper(path);
	}
}
