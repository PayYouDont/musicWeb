package music.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import music.user.controller.UploaderAction;
import music.util.ExcelUtil;

@Service("uploaderService")
public class UploaderService {
	public String createExcel(String path,String sheetName) throws Exception{
		List<String> fileNames = UploaderAction.getFileName();
		InputStream is = null;
		List<String> ls = new ArrayList<String>();
		try {
			int index = 0;
			for(String fns:fileNames) {
				ls.add(fns);
				String filepath = UploaderAction.getTempFilePath1()+fns;
				File sourcefile = new File(filepath);
				is = new FileInputStream(sourcefile);
				Workbook wb = WorkbookFactory.create(is);// 此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
				int sheetCounts = wb.getNumberOfSheets(); // Sheet的数量
				for (int s = 0; s < sheetCounts; s++) {
					Sheet sheet = wb.getSheetAt(s);// 获取一个Sheet的内容
					if (!sheetName.equals(sheet.getSheetName())) {
						continue;
					}
					//sheet.getColumnStyle(int)
					index++;
					int lastRowNum = sheet.getLastRowNum();
					Row row = null; // 兼容
					Cell cell = null; // 兼容
					for (int rowNum = index==1?1:2; rowNum <= lastRowNum; rowNum++) {
						row = sheet.getRow(rowNum);
						if (row == null) {
							continue;
						}
						int lastColNum = row.getLastCellNum();
						StringBuilder sb = new StringBuilder("");
						for (int colNum = 0; colNum < lastColNum; colNum++) {
							cell = row.getCell(colNum);
							if (cell == null) { // 特殊情况 空白的单元格会返回null
								sb.append(" " + ",");
								continue;
							}
							int cellType = cell.getCellType();
							String stringValue =ExcelUtil.getCellValue(cell, cellType);
							if(colNum==0&&(stringValue==""||stringValue==null)) {
								break;
							}
							sb.append(stringValue + ",");
						}
						String str = sb.toString();
						if (str.length()>1) {
							ls.add(str.substring(0, str.length() - 1));
						}
					}
					if (ls == null || ls.size() == 0) {
						continue;
					}
					break;
				}
			}
			ExcelUtil.writeExcel(path,ls,sheetName);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
}
