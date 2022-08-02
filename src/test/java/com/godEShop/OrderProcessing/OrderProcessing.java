package com.godEShop.OrderProcessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	    workbook = new XSSFWorkbook();
	    worksheet = workbook.createSheet("TestNG Result Summary");
	    // thêm test result vào file excel ở cột header
	    CellStyle rowStyle = workbook.createCellStyle();
	    rowStyle.setAlignment(HorizontalAlignment.CENTER);
	    rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    rowStyle.setWrapText(true);

	    // viết header vào dòng đầu tiên
	    TestNGResult.put("1",
		    new Object[] { "STT", "Action", "Expected", "Actual", "Status", "Date Check", "LINK", "Image" });
	} catch (Exception e) {
	    System.out.println("suiteTest() : " + e.getMessage());
	}
    }

    @AfterClass
    public void suiteTearDown() {
	Set<String> keyset = TestNGResult.keySet();
	int rownum = 0;
	for (String key : keyset) {
	    CellStyle rowStyle = workbook.createCellStyle();
	    Row row = worksheet.createRow(rownum++);
	    Object[] objArr = TestNGResult.get(key);
	    int cellnum = 0;
	    for (Object obj : objArr) {
		rowStyle.setAlignment(HorizontalAlignment.CENTER);
		rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		rowStyle.setWrapText(true);
		int flag = cellnum++;
		Cell cell = row.createCell(flag);
		if (obj instanceof Date) {
		    cell.setCellValue((Date) obj);
		} else if (obj instanceof Boolean) {
		    cell.setCellValue((Boolean) obj);
		} else if (obj instanceof String) {
		    cell.setCellValue((String) obj);
		} else if (obj instanceof Double) {
		    cell.setCellValue((Double) obj);
		}

		if (obj.toString().contains("failure") && obj.toString().contains(".png")) {
		    try {
			row.setHeightInPoints(80);
			writeImage(obj.toString(), row, cell, worksheet);
			CreationHelper creationHelper = worksheet.getWorkbook().getCreationHelper();
			XSSFHyperlink hyperlink = (XSSFHyperlink) creationHelper.createHyperlink(HyperlinkType.URL);
			cell.setCellValue("Full Image");
			hyperlink.setAddress(obj.toString().replace("\\", "/"));
			cell.setHyperlink(hyperlink);
			rowStyle.setAlignment(HorizontalAlignment.CENTER);
			rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			rowStyle.setWrapText(true);
			row.setRowStyle(rowStyle);

		    } catch (Exception d) {
			System.out.println("Write Image : " + d.getMessage());
		    }
		}
		rowStyle.setAlignment(HorizontalAlignment.CENTER);
		rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		rowStyle.setWrapText(true);
		worksheet.autoSizeColumn(cellnum);
		worksheet.setColumnWidth(6, 7000);
		row.setRowStyle(rowStyle);
	    }
	    try {
		FileOutputStream out = new FileOutputStream(
			new File("test-resources/data/RESULT_TEST_ORDER.xlsx").getAbsolutePath());
		workbook.write(out);
		out.close();
		System.out.println("Successfully save to Excel File!!!");
	    } catch (Exception e) {
		System.out.println("suiteTearDown() : " + e.getMessage());
	    }
	}
    }

    // -------------- TEST CAST -------------------------

    @Test(description = "Test Order", priority = 1)
    public void LoginRegistration() {
	try {
	    String expected = "http://localhost:8080/information";
	    LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss | dd-MM-yyyy");
	    String formattedDate = myDateObj.format(myFormatObj);
	    
	    // go to product page
	    driver.get("http://localhost:8080/product");
	    
	    // view product
	    WebElement btnProduct = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div/div/div[2]/div[2]/div[3]/div[1]/div/div[5]/div/div/div[2]/div[1]/h2/a"));
	    Actions actionViewProduct = new Actions(driver).click(btnProduct);
	    actionViewProduct.build().perform();
	    Thread.sleep(1000);
	    
	    // add product in to cart
	    WebElement btnAddProduct = driver.findElement(By.xpath("/html/body/div[1]/main/div[1]/div[2]/div/div/div/div[2]/div/div[2]/div/a"));
	    Actions actionAddProduct = new Actions(driver).click(btnAddProduct);
	    actionAddProduct.build().perform();
	    Thread.sleep(1000);
	    
	    // click view cart
	    driver.get("http://localhost:8080/cart");
	    Thread.sleep(1000);
	    
	    // click order
	    WebElement btnClickOrder = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div/div/div/form/div[2]/div[2]/div/div/a"));
	    Actions actionClickOrder = new Actions(driver).click(btnClickOrder);
	    actionClickOrder.build().perform();
	    Thread.sleep(1000);
	    
	    
	    // login
//	    driver.get("http://localhost:8080/account/login/form");
	    driver.findElement(By.xpath("/html/body/div[1]/main/div[4]/div[2]/form/input")).sendKeys("cust08");
	    driver.findElement(By.xpath("/html/body/div[1]/main/div[4]/div[2]/form/div[2]/input")).sendKeys("MatKhau@123");
	    WebElement btnSignIn = driver.findElement(By.xpath("/html/body/div[1]/main/div[4]/div[2]/form/button"));
	    Actions actionSignIn = new Actions(driver).click(btnSignIn);
	    actionSignIn.build().perform();
	    Thread.sleep(1000);	
	    
	    
	    // start Order
	    WebElement btnOrder = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div[2]/div/div/div/div[1]/div/div[4]/div/div/button"));
	    Actions actionOrder = new Actions(driver).click(btnOrder);
	    actionOrder.build().perform();
	    Thread.sleep(8000);	
	    
	    // view history
	    WebElement btnViewHistory = driver.findElement(By.xpath("/html/body/div[1]/main/div[2]/div/div/div/div[2]/div/div[1]/div/div/table/tbody/tr[1]/td[9]/button"));
	    Actions actionViewHistory = new Actions(driver).click(btnViewHistory);
	    actionViewHistory.build().perform();
	    Thread.sleep(3000);	

	    String currentUrl = driver.getCurrentUrl();
	    String actualTitle = currentUrl;

	    Thread.sleep(1000);

	    if (actualTitle.equalsIgnoreCase(expected)) {
		TestNGResult.put(String.valueOf(2), new Object[] { String.valueOf(1), // STT
			"Test Order",
			expected, // expected
			actualTitle, // actual
			"PASS", // status
			formattedDate, // date check
			"" // image
		});
	    } else {
		driver.get("http://localhost:8080/account/login/form");
		WebElement btnSignUp3 = driver
			.findElement(By.xpath("/html/body/div[1]/main/div[4]/div[3]/div/div[2]/button"));
		Actions actionSignUp3 = new Actions(driver).click(btnSignUp3);
		actionSignUp3.build().perform();
		String path = "test-resources/images/" + "failure-" + System.currentTimeMillis() + ".png";
		takeScreenShot(driver, path);
		TestNGResult.put(String.valueOf(2), new Object[] { String.valueOf(1), "Test Order", expected,
			actualTitle, "FAILED", formattedDate, path.replace("\\", "/") });
	    }
	} catch (Exception e) {
	    System.out.println("Order() : " + e.getMessage());
	}
    }

}
