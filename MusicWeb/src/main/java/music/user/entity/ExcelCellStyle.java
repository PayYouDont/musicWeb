package music.user.entity;

import java.util.Arrays;

import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelCellStyle {
	private CellStyle rowStyle;
	private CellStyle[] cellStyles;
	private String[] values;
	public CellStyle getRowStyle() {
		return rowStyle;
	}
	public void setRowStyle(CellStyle rowStyle) {
		this.rowStyle = rowStyle;
	}
	public CellStyle[] getCellStyles() {
		return cellStyles;
	}
	public void setCellStyles(CellStyle[] cellStyles) {
		this.cellStyles = cellStyles;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	@Override
	public String toString() {
		return "ExcelCellStyle [rowStyle=" + rowStyle + ", cellStyles=" + Arrays.toString(cellStyles) + ", values="
				+ Arrays.toString(values) + "]";
	}
	
}
