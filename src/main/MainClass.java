package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import test.CommonMethods;

public class MainClass {
	public WebDriver driver;

	
	
	@BeforeTest
	public void launchApplication() {
		CommonMethods commonMethod = new CommonMethods(driver);
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = new ChromeDriver();
		String url = (commonMethod.getPropertiesValue("URL"));
		driver.get(url);
		driver.manage().window().maximize();

	}

	@Test(priority = 0)
	public void login() {
		CommonMethods commonMethod = new CommonMethods(driver);
		commonMethod.loginApplication();
		String pageTitle = driver.findElement(By.id("page_title")).getText();
		Assert.assertEquals(pageTitle, "Dashboard");
	}

	@Test(priority = 2)
	public void validateProductAttributeCharacterLength() {
		CommonMethods commonMethod = new CommonMethods(driver);
		WebElement titleTextBox = driver.findElement(By.xpath("//input[@id='product_title']"));
		String maxCharLimitTitle = titleTextBox.getAttribute("maxlength");
		WebElement titleTextBoxsku = driver.findElement(By.xpath("//input[@id='product_sku']"));
		String maxCharLimitSku = titleTextBoxsku.getAttribute("maxlength");

		Assert.assertEquals(maxCharLimitTitle, commonMethod.getPropertiesValue("CharacterLimitFirTitle"));
		Assert.assertEquals(maxCharLimitSku, commonMethod.getPropertiesValue("CharacterLimitFirSku"));

	}

	@Test(priority = 2)
	public void validateErrorMessageForEmptyProductTitle() {
		CommonMethods commonMethod = new CommonMethods(driver);
		commonMethod.enterNewProductDetails("No Value", commonMethod.getPropertiesValue("Sku"), commonMethod.getPropertiesValue("Description"));
		String errorMessgae = driver.findElement(By.xpath("//*[contains(@id,'title')]//*[@class='inline-errors']"))
				.getText();

		Assert.assertEquals(errorMessgae, commonMethod.getPropertiesValue("EmptyFieldErrorMeggase"));
	}

	@Test(priority = 2)
	public void validateErrorMessageForEmptyProductSku() {
		CommonMethods commonMethod = new CommonMethods(driver);
		commonMethod.enterNewProductDetails(commonMethod.getPropertiesValue("Title"), "No Value", commonMethod.getPropertiesValue("Description"));
		String errorMessgae = driver.findElement(By.xpath("//*[contains(@id,'sku')]//*[@class='inline-errors']"))
				.getText();

		Assert.assertEquals(errorMessgae, commonMethod.getPropertiesValue("EmptyFieldErrorMeggase"));
	}

	@Test(priority = 1)
	public void validateErrorMessageForEmptyProductDescription() {
		CommonMethods commonMethod = new CommonMethods(driver);
		commonMethod.navigateToNewProductCreation();
		commonMethod.enterNewProductDetails(commonMethod.getPropertiesValue("Title"), commonMethod.getPropertiesValue("Sku"), "No Value");
		String errorMessgae = driver
				.findElement(By.xpath("//*[contains(@id,'description')]//*[@class='inline-errors']")).getText();

		Assert.assertEquals(errorMessgae, "can't be blank");
	}

	@Test(priority = 3)
	public void verifyNewProductCreation() {
		boolean created = false;
		CommonMethods commonMethod = new CommonMethods(driver);
		commonMethod.enterNewProductDetails(commonMethod.getPropertiesValue("Title"), commonMethod.getPropertiesValue("Sku"), commonMethod.getPropertiesValue("Description"));
		WebElement successMessage = driver
				.findElement(By.xpath("//div[contains(text(),'Product was successfully created.')]"));
		try {
			if (successMessage.isDisplayed()) {
				created = true;
			}
		} catch (Exception e) {
			created = false;
		}
		Assert.assertEquals(created, true);

	}

	@Test(priority = 4)
	public void verifyNewProductListing() {
		boolean listed = false;
		driver.findElement(By.xpath("//*[@id='products']//a")).click();

		WebElement productListing = driver.findElement(
				By.xpath("//div[@class='paginated_collection_contents']//td[contains(text(),'Automation_Title')]"));
		try {
			if (productListing.isDisplayed()) {
				listed = true;
			}
		} catch (Exception e) {
			listed = false;
		}
		Assert.assertEquals(listed, true);
	}

	@Test(priority = 5)
	public void verifyUdpateProduct() {
		boolean updated = false;
		CommonMethods commonMethod = new CommonMethods(driver);
		commonMethod.editProduct(commonMethod.getPropertiesValue("Title"));
		commonMethod.enterNewProductDetails(commonMethod.getPropertiesValue("Title_Edit"), commonMethod.getPropertiesValue("Sku"), commonMethod.getPropertiesValue("Description"));
		WebElement successMessage = driver
				.findElement(By.xpath("//div[contains(text(),'Product was successfully updated.')]"));
		try {
			if (successMessage.isDisplayed()) {
				updated = true;
			}
		} catch (Exception e) {
			updated = false;
		}
		Assert.assertEquals(updated, true);

	}
	
	@Test(priority = 6)
	public void verifyDeleteProduct() {
		boolean updated = false;
		CommonMethods commonMethod = new CommonMethods(driver);
		commonMethod.deleteProduct(commonMethod.getPropertiesValue("Title_Edit"));
		WebElement deleteMessage = driver
				.findElement(By.xpath("//div[contains(text(),'Product was successfully destroyed.')]"));
		try {
			if (deleteMessage.isDisplayed()) {
				updated = true;
			}
		} catch (Exception e) {
			updated = false;
		}
		Assert.assertEquals(updated, true);

	}

	@AfterTest
	public void closeBrowser(){
		driver.close();
	}
}
