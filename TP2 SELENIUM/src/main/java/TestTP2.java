import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestTP2 {
    WebDriver driver;
    JavascriptExecutor js;

    public void initialiseDriver() throws  InterruptedException{
        WebDriverManager.chromedriver().setup();
        WebDriver chrome = new ChromeDriver();
        chrome.get("https://www.tunisianet.com.tn/");
        Thread.sleep(2000);
        chrome.quit();
    }

    public static void main(String[] args) throws  InterruptedException{
        // initialise webdrivermanager
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.tunisianet.com.tn/");
        //Mazimize current window
        //driver.manage().window().maximize();
        Thread.sleep(2000);
        //open menu
        driver.findElement(By.cssSelector(".nav-link > svg")).click();
        Thread.sleep(2000);
        // click login button
        driver.findElement(By.xpath("//div[@id='_desktop_user_info']/ul/li/a/span")).click();
        Thread.sleep(2000);
        // input credentials
        driver.findElement(By.name("email")).sendKeys("khalilouertani@gmail.com");
        driver.findElement(By.name("password")).sendKeys("Khalil123");
        Thread.sleep(1000);
        // login
        driver.findElement(By.id("submit-login")).click();
        //search computer
        driver.findElement(By.id("search_query_top")).sendKeys("PC portable MacBook M1 13.3 ");
        driver.findElement(By.name("submit_search")).click();
        Thread.sleep(4000);
        //select computer
        Thread.sleep(1500);
        List<WebElement> productsTitle = driver.findElements(By.className("product-title"));
        productsTitle.get(0).click();
        //Add product to cart
        Thread.sleep(1500);
        WebElement addToCartButton = driver.findElement(By.className("add-to-cart"));
        addToCartButton.click();
        //Order product
        Thread.sleep(1500);
        WebElement orderButton = driver.findElement(By.cssSelector("a.btn-block"));
        orderButton.click();
        Thread.sleep(5000);
        driver.quit();
    }
}