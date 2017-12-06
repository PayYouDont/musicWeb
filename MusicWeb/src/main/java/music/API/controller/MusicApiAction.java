package music.API.controller;

import java.util.HashMap;

import javax.annotation.Resource;

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
}
