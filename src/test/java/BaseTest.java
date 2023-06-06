import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    WebDriver driver = new ChromeDriver();

    @BeforeMethod
    protected void beforeMethod() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.name("j_username")).sendKeys("kononirin");
        driver.findElement(By.name("j_password")).sendKeys("nthvbyfnjh123");
        driver.findElement(By.name("Submit")).click();
    }

    @AfterMethod
    protected void afterMethod() {
        driver.quit();
    }
}
