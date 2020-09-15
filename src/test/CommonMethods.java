package test;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommonMethods {
	public WebDriver driver;

	public CommonMethods(WebDriver driver) {
		this.driver = driver;
	}

	public void loginApplication() {
		driver.findElement(By.xpath("//*[@id='admin_user_email']")).sendKeys(getPropertiesValue("UserName"));
		driver.findElement(By.id("admin_user_password")).sendKeys(getPropertiesValue("Password"));
		driver.findElement(By.name("commit")).click();
	}

	public void navigateToNewProductCreation() {
		driver.findElement(By.xpath("//*[@id='products']//a")).click();
		try {
			if (driver.findElement(By.xpath("//a[contains(text(),'Create one')]")).isDisplayed()) {
				driver.findElement(By.xpath("//a[contains(text(),'Create one')]")).click();
			} else {
				driver.findElement(By.xpath("//a[contains(text(),'New Product')]")).click();
			}
		} catch (Exception e) {
			driver.findElement(By.xpath("//a[contains(text(),'New Product')]")).click();
		}
	}

	public void enterNewProductDetails(String title, String sku, String description) {
		driver.findElement(By.id("product_title")).clear();
		driver.findElement(By.id("product_sku")).clear();
		driver.findElement(By.id("product_description")).clear();

		if (!title.equals("No Value")) {
			driver.findElement(By.id("product_title")).sendKeys(title);
		}
		if (!sku.equals("No Value")) {
			driver.findElement(By.id("product_sku")).sendKeys(sku);
		}
		if (!description.equals("No Value")) {
			driver.findElement(By.id("product_description")).sendKeys(description);
		}
		driver.findElement(By.name("commit")).click();
	}

	public void editProduct(String title) {
		driver.findElement(By.xpath("//*[@id='products']//a")).click();
		driver.findElement(By.xpath("//td[contains(text(),'" + title + "')]//parent::tr//a[@title='Edit']")).click();
	}

	public void deleteProduct(String title) {
		driver.findElement(By.xpath("//*[@id='products']//a")).click();
		driver.findElement(By.xpath("//td[contains(text(),'" + title + "')]//parent::tr//a[@title='Delete']")).click();
		driver.switchTo().alert().accept();
	}

	public String getPropertiesValue(String keyValue) {
		String value = "";
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src//DataFile.properties"));
			value = prop.getProperty(keyValue);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return value;
	}

}
