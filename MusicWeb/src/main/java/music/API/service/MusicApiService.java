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
		String urlString = "http://s.music.qq.com/fcgi-bin/music_search_new_platform?t=0&n="+number;
		   	   urlString += "&aggr=1&cr=1&loginUin=0&format=json&inCharset=GB2312&outCharset=utf-8&notice=0&platform=jqminiframe.json&needNewCode=0&p=1&catZhida=0&remoteplace=sizer.newclient.next_song&w="+name;
	   	URL url = new URL(urlString);
	    BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") );   
	    StringBuffer sb = new StringBuffer(); 
	    String str = null;
	    while((str = in.readLine()) != null) { 
    		sb.append(str);
    	}
	    in.close();
		return sb;
	}
}
