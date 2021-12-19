import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ToDo {
    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait waitVar;

    @BeforeAll
    public static void initialize() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void prepareDriver(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        waitVar = new WebDriverWait(driver, Duration.ofSeconds(40)); // explicit wait
        js = (JavascriptExecutor) driver;
    }
    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void todoTestCase() throws InterruptedException {
        driver.get("https://todomvc.com");
        setPlatform("Backbone.js");
        String[] todos = {"Wake up","Study","Work","Repeat"};
        for (String todo : todos){
            addTodo(todo);
        }
        tickTodo(1);
        tickTodo(2);
        tickTodo(3);
        expectTodosLeftCount(2);
        tickTodo(4);
        tickTodo(4);
        expectFunctionalToggle();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Backbone.js",
            "React",
            "Dijon"
    })
    public void todoTestCase(String platform) throws InterruptedException {
        driver.get("https://todomvc.com");
        setPlatform(platform);
        String[] todos = {"Wake up","Study","Work","Repeat"};
        for (String todo : todos){
            addTodo(todo);
        }
        // Randomly ticking a todo
        tickTodo(2);
        tickTodo(3);
        expectTodosLeftCount(2);
        tickTodo(1);
        tickTodo(2);
        tickTodo(3);
        expectFunctionalToggle();
    }


    private void setPlatform(String platform) {
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(platform)));
        WebElement chosenPlatform = driver.findElement(By.linkText(platform));
        chosenPlatform.click();
    }

    private void addTodo(String todo) {
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'What needs to be done?')]")));
        WebElement addTodoElement = driver.findElement(By.xpath("//input[contains(@placeholder,'What needs to be done?')]"));
        addTodoElement.sendKeys(todo);
        addTodoElement.sendKeys(Keys.RETURN);
    }

    private void tickTodo(int index) {
        WebElement pickedElement = driver.findElement(By.cssSelector("li:nth-child("+index+") .toggle"));
        pickedElement.click();
    }
    private void expectTodosLeftCount(int expectedCount) {
        WebElement todoCountElement = driver.findElement(By.className("todo-count"));
        ExpectedConditions.textToBePresentInElement(todoCountElement, Integer.toString(expectedCount));
    }
    private void expectFunctionalToggle() {
                int totalTodos = driver.findElements(By.xpath("//ul[contains(@class,'todo-list')]//li")).size();
        int completedTodos = driver.findElements(By.xpath("//ul[contains(@class,'todo-list')]//li[contains(@class,'completed')]")).size();
        WebElement todoCountElement = driver.findElement(By.className("todo-count"));
        ExpectedConditions.textToBePresentInElement(todoCountElement, Integer.toString(totalTodos-completedTodos));
    }

}