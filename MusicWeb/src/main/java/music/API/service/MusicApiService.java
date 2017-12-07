package music.API.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service("musicApiService")
public class MusicApiService {
	
	/**
	 * 
	 * @Title: searchMusic 
	 * @Description: TODO(音乐搜索) 
	 * @param @param name（歌曲名）
	 * @param @param number（返回的数量）
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @return StringBuffer    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2017年12月6日 上午10:46:01
	 */
	public StringBuffer searchMusic(String name,Integer number) throws Exception{
		String urlStr = "http://s.music.qq.com/fcgi-bin/music_search_new_platform?t=0&n="+number;
		   	   urlStr += "&aggr=1&cr=1&loginUin=0&format=json&inCharset=GB2312&outCharset=utf-8&notice=0&platform=jqminiframe.json&needNewCode=0&p=1&catZhida=0&remoteplace=sizer.newclient.next_song&w="+name;
	    StringBuffer sb = search(urlStr,"utf-8");
		return sb;
	}
	/**
	 * @Title: search 
	 * @Description: TODO(根据url字符串返回搜索结果) 
	 * @param @param urlStr
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @return StringBuffer    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2017年12月7日 上午9:43:49
	 */
	public StringBuffer search(String urlStr,String encoding) throws Exception{
		URL url = new URL(urlStr);
		BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream(),encoding) );   
		StringBuffer sb = new StringBuffer(); 
	    String str = null;
	    while((str = in.readLine()) != null) {
    		sb.append(str);
    	}
	    in.close();
		return sb;
	}
}
