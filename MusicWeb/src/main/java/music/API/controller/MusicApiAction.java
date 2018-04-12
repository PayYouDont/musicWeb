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
		try {
			sb = service.search(urlStr,"GB2312");
			return JsonWrapper.successWrapper(sb);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonWrapper.failureWrapper("获取失败");
		}
	}
	/**
	 * 下载歌曲
	 * @param request
	 * @param response
	 * @param sid
	 * @param songName
	 * @return
	 */
	@RequestMapping("/toDownLoad")
	@ResponseBody
	public HashMap<String, Object> downLoad(HttpServletRequest request,HttpServletResponse response,String sid,String songName) {
		String urlStr = "http://ws.stream.qqmusic.qq.com/C100"+sid+".m4a?fromtag=0";
		this.service.downLoad(request,response,urlStr,songName);
		return JsonWrapper.successWrapper(null,"下载成功");
	}
}
