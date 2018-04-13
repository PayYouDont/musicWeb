package music.API.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//初始化一个链接到那个url的连接
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接  
		//设置User-Agent 加上下面这句后便可进行爬取
		connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		connection.setRequestProperty("referer","https://y.qq.com/portal/playlist.html");
		connection.connect();// 连接会话 
		
		BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream(),"utf-8"));   
		StringBuffer sb = new StringBuffer(); 
	    String str = null;
	    while((str = in.readLine()) != null) {
    		sb.append(str);
    	}
	    in.close();
		return sb;
	}
	public void downLoad(HttpServletRequest request,HttpServletResponse response,String urlStr,String songName) {
		songName = songName+".m4a";
		@SuppressWarnings("deprecation")
		File folder = new File(request.getRealPath("/") + "export");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		try {
			response.setContentType("application/x-download");
			//下载名转成中文
			songName = new String(songName.getBytes(), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename="+songName);
			URL url = new URL(urlStr);
			InputStream inputStream = url.openStream();
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[inputStream.available()];
			int length = 0;
			while ((length = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}

			inputStream.close();
			out.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}