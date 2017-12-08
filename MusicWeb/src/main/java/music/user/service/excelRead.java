package music.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import music.user.controller.UploaderAction;

public class excelRead {
	
	public static void main(String[] args) throws Exception {
		name("C:\\Users\\Pay\\Desktop\\文档\\02 已审\\02 已审\\11月城市车辆运营费用-阜阳.xlsx","城市运营供应商汇总");
	}
	public static void name(String path,String sheetName) throws Exception{
		List<String> fileNames = UploaderAction.getFileName();
		InputStream is = null;
		List<String> ls = new ArrayList<String>();
		try {
			for(String fns:fileNames) {
				path = UploaderAction.getTempFilePath1()+fns;
				File sourcefile = new File(path);
				is = new FileInputStream(sourcefile);
				Workbook wb = WorkbookFactory.create(is);// 此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
				int sheetCounts = wb.getNumberOfSheets(); // Sheet的数量
				for (int s = 0; s < sheetCounts; s++) {
					Sheet sheet = wb.getSheetAt(s);// 获取一个Sheet的内容
					if (!sheetName.equals(sheet.getSheetName())) {
						continue;
					}
					int lastRowNum = sheet.getLastRowNum();
					Row row = null; // 兼容
					Cell cell = null; // 兼容
					for (int rowNum =2; rowNum <= lastRowNum; rowNum++) {
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
							String stringValue =getCellValue(cell, cellType);
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
			System.out.println(ls);
			getExcelInfo("",ls);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	public static String getCellValue(Cell cell, int cellType) {
		switch (cellType) {
		case Cell.CELL_TYPE_STRING: // 文本
			return cell.getStringCellValue().toString();
		case Cell.CELL_TYPE_NUMERIC: // 数字、日期
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				return sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				return sdf.format(date);
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				return format.format(value);
			}
		case Cell.CELL_TYPE_BOOLEAN: // 布尔型
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_BLANK: // 空白
			return "";
		default:
			return "";
		}
	}
	
	public static String getExcelInfo(String path , List<String> list)throws Exception {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook();// HSSFWorkbook();//WorkbookFactory.create(inputStream);
			if (workbook != null) {
				Sheet sheet = workbook.createSheet("错误数据");
				Row row0 = sheet.createRow(0);
				/*for (int i = 0; i < 19; i++) {
					Cell cell_1 = row0.createCell(i, Cell.CELL_TYPE_STRING);
					// CellStyle style = getStyle(workbook);
					// cell_1.setCellStyle(style);
					if (i == 0) {
						cell_1.setCellValue("任务类型");
					} else if (i == 1) {
						cell_1.setCellValue("区/县");
					} else if (i == 2) {
						cell_1.setCellValue("乡/镇");
					} else if (i == 3) {
						cell_1.setCellValue("村");
					} else if (i == 4) {
						cell_1.setCellValue("内容");
					}
					sheet.autoSizeColumn(i);
				}*/
				
				
				
				for (int rowNum = 1; rowNum <= list.size(); rowNum++) {
					Row row = sheet.createRow(rowNum);
					for (int i = 0; i < list.size(); i++) {
						String[] arr =list.get(i).split(",");
						for (String string : arr) {
							Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
							sheet.setColumnWidth(i, 20 * 256);
							cell.setCellValue(string);
						}
					}
				}
				FileOutputStream outputStream = new FileOutputStream("D:\\abc2.xlsx");
				workbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
			}
			System.out.println("写出完毕");
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
}
