package music.user.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import music.user.service.UploaderService;
import music.util.ExcelUtil;
import music.util.JsonWrapper;

@Controller
@RequestMapping("/uploaderAction")
public class UploaderAction {
	@Resource(name = "uploaderService")
	private UploaderService service;
	@RequestMapping("/upload")
	@ResponseBody
	public HashMap<String, Object> upload(MultipartFile file,  String chunks,  String chunk,String name) throws Exception { 
		try {
			 if (file!=null) {   
				//判断上传的文件是否被分片  
                if(null == chunks && null == chunk){  
                	File destTempFile = new File(getTempFilePath1(), name);
                	if (!destTempFile.exists()) {  
                		destTempFile.mkdirs();  
                    } 
                	file.transferTo(destTempFile);
                	destTempFile.createNewFile();  
                    return JsonWrapper.successWrapper("上传完毕");
                }
                String tempFileDir = getTempFilePath()  + File.separator + name;  
                File parentFileDir = new File(tempFileDir);  
                if (!parentFileDir.exists()) {  
                    parentFileDir.mkdirs();  
                }  
                File f = new File(tempFileDir + File.separator+ name + "_" + chunk + ".part");  
                file.transferTo(f);  
                f.createNewFile();  
             // 是否全部上传完成  
                // 所有分片都存在才说明整个文件上传完成  
                boolean uploadDone = true;  
                for (int i = 0; i < Integer.parseInt(chunks); i++) {  
                    File partFile = new File(tempFileDir, name + "_" + i + ".part");  
                    if (!partFile.exists()) {  
                        uploadDone = false;  
                        return JsonWrapper.successWrapper("上传完毕"); 
                    }  
                } 
                // 所有分片文件都上传完成  
                // 将所有分片文件合并到一个文件中  
                if (uploadDone) {  
                	synchronized (this) {  
                		File destTempFile = new File(getTempFilePath1(), name);
                		if (!destTempFile.exists()) {  
                    		destTempFile.mkdirs();  
                        } 
                        for (int i = 0; i < Integer.parseInt(chunks); i++) {  
                            File partFile = new File(tempFileDir, name + "_" + i + ".part");  
                            FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);  
                            FileUtils.copyFile(partFile,destTempfos);
                            destTempfos.close();  
                        }  
                        FileUtils.deleteDirectory(parentFileDir);  
                	}
                }
                return JsonWrapper.successWrapper("上传完毕"); 
			 }
			 return JsonWrapper.failureWrapper("文件不存在");
		}catch(Exception e) {
			e.printStackTrace();
			return JsonWrapper.failureWrapper("上传失败");
		}
	}
	
	@RequestMapping("/download")
	@ResponseBody
	public HashMap<String, Object> download(HttpServletRequest request,String sheetName) {
		/*String ctxDir = request.getSession().getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String path="printExclFile"+File.separatorChar+"newExcel.xlsx";
		String excelPath = ctxDir + path;*/
		String newFileName = "newfile.xlsx";
		String path= downLoadPath()+newFileName;
		try {
			service.createExcel(path, sheetName);
			File destTempFile = new File(getTempFilePath1());//删除上传文件
			if(destTempFile.isDirectory()) {
				File[] files = destTempFile.listFiles();
				for (File file : files) {
					file.delete();
				}
			}
        	if (destTempFile.exists()) {  
        		destTempFile.delete();  
            } 
        	String OS = System.getProperty("os.name").toLowerCase();
    		if(OS.toLowerCase().indexOf("windows")!=-1) {
    			newFileName = "download\\"+newFileName;
    		}else {
    			newFileName = "download/"+newFileName;
    		}
			return JsonWrapper.successWrapper(newFileName);
		}catch(Exception e) {
			e.printStackTrace();
			return JsonWrapper.successWrapper("");
		}
	}
	public static String getTempFilePath() {  
		String OS = System.getProperty("os.name").toLowerCase();
		if(OS.toLowerCase().indexOf("windows")!=-1) {
			return "D:\\upload\\part";
		}else {
			return "/usr/upload/part";
		}
    }  
      
    public static String getTempFilePath1() {  
    	String OS = System.getProperty("os.name").toLowerCase();
		if(OS.toLowerCase().indexOf("windows")!=-1) {
			return "D:\\upload\\";
		}else {
			return "/usr/upload/";
		} 
    }
    public static String downLoadPath() {  
    	String OS = System.getProperty("os.name").toLowerCase();
		if(OS.toLowerCase().indexOf("windows")!=-1) {
			return "D:\\download\\";
		}else {
			return "/usr/download/";
		} 
    }
    //获取文件夹中的所有目录
    public static List<String> getFileName() {
    	//
    	File file = new File(getTempFilePath1());
    	if(!file.exists()) {
    		return null;
    	}
    	File[] sunfiles = file.listFiles();
    	List<String> fnames = new ArrayList<>();
    	 for (int i = 0; i < sunfiles.length; i++) {
    		 if(!sunfiles[i].isDirectory()) {
    			 fnames.add(sunfiles[i].getName());
    		 }
    	 }
    	 return fnames;
    }
}
