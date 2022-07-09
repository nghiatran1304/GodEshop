package com.godEShop.OrderProcessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class OrderProcessing {
    public WebDriver driver;
    private XSSFWorkbook workbook;
    private XSSFSheet worksheet;
    private Map<String, Object[]> TestNGResult;
    private Map<String, String[]> dataLoginTest;

    private final String EXCEL_DIR = "D:/DA2/GodEshop/test-resources/data/";
    private final String IMAGE_DIR = "D:/DA2/GodEshop/test-resources/images/";

    // đọc dữ liệu từ file excel
    private void readDataFromExcel() {
	try {
	    dataLoginTest = new HashMap<String, String[]>();
	    worksheet = workbook.getSheet("TestData"); // tên sheet cần lấy data
	    if (worksheet == null) {
		System.out.println("Không tìm thấy worksheet : TestData");
	    } else {
		Iterator<Row> rowIterator = worksheet.iterator();
		DataFormatter dataFormat = new DataFormatter();
		while (rowIterator.hasNext()) {
		    Row row = rowIterator.next();
		    if (row.getRowNum() >= 1) {
			Iterator<Cell> cellIterator = row.cellIterator();
			String key = ""; // key - ô stt
			String username = ""; // giá trị ô username
			String password = ""; // giá trị ô password
			String email = "";
			String telephone = ""; // giá trị ô telephone
			String expected = ""; // giá trị ô expected
			while (cellIterator.hasNext()) {
			    Cell cell = cellIterator.next();
			    if (cell.getColumnIndex() == 0) {
				key = dataFormat.formatCellValue(cell);
			    } else if (cell.getColumnIndex() == 1) {
				username = dataFormat.formatCellValue(cell);
			    } else if (cell.getColumnIndex() == 2) {
				password = dataFormat.formatCellValue(cell);
			    } else if (cell.getColumnIndex() == 3) {
				email = dataFormat.formatCellValue(cell);
			    } else if (cell.getColumnIndex() == 4) {
				telephone = dataFormat.formatCellValue(cell);
			    } else if (cell.getColumnIndex() == 5) {
				expected = dataFormat.formatCellValue(cell);
			    }

			    String[] myArr = { username, password, email, telephone, expected };
			    dataLoginTest.put(key, myArr);
			}
		    }
		}
	    }
	} catch (Exception e) {
	    System.out.println("readDataFromExcel() : " + e.getMessage());
	}
    }
    // ---------- Kết thúc đọc dữ liệu -------------------

    public void takeScreenShot(WebDriver driver, String outputSrc) throws IOException {
	Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
		.takeScreenshot(driver);
	ImageIO.write(screenshot.getImage(), "PNG", new File(outputSrc));
    }

    public void writeImage(String imgSrc, Row row, Cell cell, XSSFSheet sheet) throws IOException {
	InputStream is = new FileInputStream(imgSrc);
	byte[] bytes = IOUtils.toByteArray(is);
	int idImg = sheet.getWorkbook().addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_PNG);
	is.close();

	XSSFDrawing drawing = sheet.createDrawingPatriarch();

	ClientAnchor anchor = new XSSFClientAnchor();

	anchor.setCol1(cell.getColumnIndex() + 1);
	anchor.setRow1(row.getRowNum());
	anchor.setCol2(cell.getColumnIndex() + 2);
	anchor.setRow2(row.getRowNum() + 1);

	drawing.createPicture(anchor, idImg);

    }

    // ---------- Kết thúc Xử lý chụp ảnh ----------------

    // ------------ Before Class ------------

    @BeforeClass(alwaysRun = true)
    public void suiteTest() {
	try {
	    TestNGResult = new LinkedHashMap<String, Object[]>();
	    WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();
	    driver.manage().window().maximize();
//	    workbook = new XSSFWorkbook(new FileInputStream(new File(EXCEL_DIR + "TEST_LOGIN.xlsx")));
	    workbook = new XSSFWorkbook(
		    new FileInputStream(new File("D:/DA2/GodEshop/test-resources/data/TEST_REGISTRATION.xlsx")));
	    worksheet = workbook.getSheet("TestData");
	    readDataFromExcel(); // đọc dữ liệu
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	    workbook = new XSSFWorkbook();
	    worksheet = workbook.createSheet("TestNG Result Summary");
	    // thêm test result vào file excel ở cột header
	    CellStyle rowStyle = workbook.createCellStyle();
	    rowStyle.setAlignment(HorizontalAlignment.CENTER);
	    rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    rowStyle.setWrapText(true);

	    // viết header vào dòng đầu tiên
	    TestNGResult.put("1", new Object[] { "STT", "Username", "Password", "Email", "Telephone", "Action",
		    "Expected", "Actual", "Status", "Date Check", "LINK", "Image" });
	} catch (Exception e) {
	    System.out.println("suiteTest() : " + e.getMessage());
	}
    }

}
