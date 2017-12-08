package music.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

public class ExcelUtil {
	/**
	 * 
	 * @Title: hasDatas 
	 * @Description: TODO(判断某个sheet是否有数据) 
	 * @param @param sheet
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2017年12月8日 下午3:48:34
	 */
	public static boolean hasDatas(Sheet sheet) {
		if (sheet.getLastRowNum() == 0 && sheet.getPhysicalNumberOfRows() == 0) {
			return false;
		}
		return true;
	}
	/***
	 * 
	 * @Title: getSheetName 
	 * @Description: TODO(获取excel中数据不为空sheet表的名称) 
	 * @param @param workbook
	 * @param @param path
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @return List<String>    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2017年12月8日 下午3:48:59
	 */
	public static List<String> getSheetName(Workbook workbook, String path) throws Exception {
		List<String> list_name = new ArrayList<String>();
		FileInputStream is = null;
		try {
			if (workbook == null) {
				File excelFile = new File(path); // 创建文件对象
				is = new FileInputStream(excelFile); // 文件流
				workbook = WorkbookFactory.create(is);
			}
			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);
				if (hasDatas(sheet)) {
					String name = sheet.getSheetName();
					list_name.add(name);
				}
			}
			return list_name;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	/**
	 * 
	 * @Title: maxCol 
	 * @Description: TODO(获取最大的列数) 
	 * @param @param sheet
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2017年12月8日 下午3:49:50
	 */
	public static int maxCol(Sheet sheet) {
		int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
		if (rowCount == 0) {
			return -1;
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int r = 0; r < rowCount; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				int cellCount = 0;
				list.add(cellCount);
				continue;
			}
			int cellCount = row.getPhysicalNumberOfCells(); // 获取最后一个不为空的列数
			list.add(cellCount);
		}
		int[] arr = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		Arrays.sort(arr);
		int maxCol = arr[arr.length - 1];
		return maxCol;
	}
	/**
	 * 
	 * @Title: getCellValue 
	 * @Description: TODO(获取excel中每个单元格的数据) 
	 * @param @param cell
	 * @param @param cellType
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 * @author peiyongdong
	 * @date 2017年12月8日 下午3:51:03
	 */
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
		/*
		 * case Cell.CELL_TYPE_ERROR: //错误 cellValue = "错误"; break; case
		 * Cell.CELL_TYPE_FORMULA: //公式 cellValue = "错误"; break;
		 */
		default:
			return "";
		}
	}
}
