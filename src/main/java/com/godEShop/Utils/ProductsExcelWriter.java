package com.godEShop.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.godEShop.Dto.ProductImageDto;
import com.godEShop.Dto.ProductsExcel;
import com.godEShop.Dto.ProductsStatisticDto;

public class ProductsExcelWriter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private String title;
    private String keyword;
    private String date;
    private String urlProduct = "src/main/resources/static/upload/ProductImages/";
    private String logo = "src/main/resources/static/admin/assets/images/Glogo/logo.png";
    private List<ProductsStatisticDto> listProds;
    private List<ProductImageDto> listProdImage;

    public ProductsExcelWriter(String title, String keyword, String date, List<ProductsStatisticDto> listProds,
	    List<ProductImageDto> listProdImage) {
	this.title = title;
	this.keyword = keyword;
	this.date = date;
	this.listProds = listProds;
	this.listProdImage = listProdImage;
	workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() throws IOException {
	sheet = workbook.createSheet("Report");

	int firstRow = 0;
	int lastRow = 0;
	int firstCol = 0;
	int lastCol = 5;

	Row title1 = sheet.createRow(0);

	sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));

	InputStream my_banner_image = new FileInputStream(logo);
	byte[] bytes = IOUtils.toByteArray(my_banner_image);
	int my_picture_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
	my_banner_image.close();
	XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
	XSSFClientAnchor my_anchor = new XSSFClientAnchor();

	my_anchor.setCol1(0);
	my_anchor.setRow1(0);
	my_anchor.setCol2(2);
	my_anchor.setRow2(1);

	drawing.createPicture(my_anchor, my_picture_id);

	CellStyle titleStyle = workbook.createCellStyle();
	XSSFFont fontTitle = workbook.createFont();
	fontTitle.setBold(true);
	fontTitle.setColor(IndexedColors.BLUE.getIndex());
	fontTitle.setFontHeight(30);
	titleStyle.setFont(fontTitle);
	titleStyle.setAlignment(HorizontalAlignment.RIGHT);
	titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	;
	titleStyle.setWrapText(true);

	createCell(title1, 0, title + "\n" + keyword, titleStyle);
	title1.setHeightInPoints(10 * 12);

	Row dateCell = sheet.createRow(1);

	int firstRowDate = 1;
	int lastRowDate = 1;
	int firstColDate = 0;
	int lastColDate = 5;
	sheet.addMergedRegion(new CellRangeAddress(firstRowDate, lastRowDate, firstColDate, lastColDate));

	CellStyle dateStyle = workbook.createCellStyle();
	XSSFFont fontDate = workbook.createFont();
	fontDate.setBold(true);
	fontDate.setItalic(true);
	fontDate.setColor(IndexedColors.BLUE.getIndex());
	fontDate.setFontHeight(20);
	dateStyle.setFont(fontDate);
	dateStyle.setAlignment(HorizontalAlignment.RIGHT);
	dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	;
	dateStyle.setWrapText(true);
	createCell(dateCell, 0, date, dateStyle);
	dateCell.setHeightInPoints(5 * 7);

	CellStyle style = workbook.createCellStyle();
	XSSFFont font = workbook.createFont();
	font.setBold(true);
	font.setFontHeight(20);
	style.setFont(font);
	style.setAlignment(HorizontalAlignment.CENTER);
	style.setWrapText(true);

	Row headline = sheet.createRow(2);

	createCell(headline, 0, "ID", style);
	createCell(headline, 1, "Image", style);
	createCell(headline, 2, "Name", style);
	createCell(headline, 3, "Stock", style);
	createCell(headline, 4, "Sold", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	sheet.autoSizeColumn(columnCount);
	sheet.setColumnWidth(1, 90 * 90);
	sheet.setColumnWidth(2, 120 * 120);
	Cell cell = row.createCell(columnCount);
	if (value instanceof Integer) {
	    cell.setCellValue((Integer) value);
	} else if (value instanceof Boolean) {
	    cell.setCellValue((Boolean) value);
	} else {
	    cell.setCellValue((String) value);
	}
	cell.setCellStyle(style);
    }

    private void writeDataLines() throws IOException {
	int rowCount = 3;

	CellStyle style = workbook.createCellStyle();
	XSSFFont font = workbook.createFont();
	font.setFontHeight(14);
	style.setFont(font);
	style.setVerticalAlignment(VerticalAlignment.CENTER);
	style.setAlignment(HorizontalAlignment.CENTER);
	style.setWrapText(true);

	CellStyle styleImage = workbook.createCellStyle();
	styleImage.setAlignment(HorizontalAlignment.CENTER);
	styleImage.setWrapText(true);

	List<ProductsExcel> listProducts = new ArrayList<>();
	for (int i = 0; i < listProds.size(); i++) {
	    for (int j = 0; j < listProdImage.size(); j++) {
		if (listProds.get(i).getProductId() == listProdImage.get(j).getProductId()) {
		    ProductsExcel product = new ProductsExcel();
		    product.setProductId(listProds.get(i).getProductId());
		    product.setPhoto(listProdImage.get(j).getImageId());
		    product.setProductName(listProds.get(i).getProductName());
		    product.setQuantityleft(listProds.get(i).getQuantityleft());
		    product.setQuantity(listProds.get(i).getQuantity());

		    listProducts.add(product);
		}
	    }
	}

	for (ProductsExcel item : listProducts) {
	    Row row = sheet.createRow(rowCount++);
	    row.setHeightInPoints((10 * sheet.getDefaultRowHeightInPoints()));
	    int columnCount = 0;

	    try {
		createCell(row, columnCount++, item.getProductId().intValue(), style);
		createCell(row, columnCount++, "", style);

		InputStream my_banner_image = new FileInputStream(urlProduct + item.getPhoto());
		byte[] bytes = IOUtils.toByteArray(my_banner_image);
		int my_picture_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		my_banner_image.close();
		XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
		XSSFClientAnchor my_anchor = new XSSFClientAnchor();

		my_anchor.setCol1(1); // Column B
		my_anchor.setRow1(rowCount - 1); // Row 3
		my_anchor.setCol2(2); // Column C
		my_anchor.setRow2(rowCount); // Row 4

		drawing.createPicture(my_anchor, my_picture_id);

		createCell(row, columnCount++, item.getProductName(), style);
		createCell(row, columnCount++, item.getQuantityleft(), style);
		createCell(row, columnCount++, item.getQuantity().intValue(), style);

	    } catch (Error error) {
		System.out.println(error);
	    }

	}
    }

    public void export(HttpServletResponse response) throws IOException {
	writeHeaderLine();
	writeDataLines();

	ServletOutputStream outputStream = response.getOutputStream();
	workbook.write(outputStream);
	workbook.close();

	outputStream.close();

    }
}