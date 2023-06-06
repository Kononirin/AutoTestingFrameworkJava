import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class JenkinsTest extends BaseTest{

    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By SAVE_BUTTON = By.name("Submit");
    private static final By OK_BUTTON = By.xpath("//button[@id='ok-button']");
    private static final By DASHBOARD = By.linkText("Dashboard");
    private static final By SET_ITEM_NAME = By.id("name");

    public void clickDropdown() {
        WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='jenkins-menu-dropdown-chevron'])[5]")));
        dropdown.click();
    }

    @Test
    public void testCreateFolder() {
        driver.findElement(NEW_ITEM).click();

        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("New folder");
        driver.findElement(By.xpath("//input[@value='com.cloudbees.hudson.plugins.folder.Folder']/..")).click();
        driver.findElement(By.id("ok-button")).click();

        driver.findElement(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']")).click();
        WebElement folderName = driver.findElement(By.xpath("//span[text() = 'New folder']"));

        Assert.assertEquals(folderName.getText(), "New folder");

        new Actions(driver).moveToElement(folderName).perform();

        WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='jenkins-menu-dropdown-chevron'])[5]")));
        dropdown.click();

        WebElement deleteButton = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/job/New%20folder/delete']")));
        deleteButton.click();

        driver.findElement(By.xpath("//button[@formnovalidate]")).click();
    }

    @Test
    public void testRenameFolder() {
        driver.findElement(NEW_ITEM).click();

        driver.findElement(SET_ITEM_NAME).sendKeys("New folder");
        driver.findElement(By.xpath("//input[@value='com.cloudbees.hudson.plugins.folder.Folder']/..")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(DASHBOARD).click();

        WebElement newFolder = driver.findElement(By.xpath("//a[@href='job/New%20folder/']"));
        new Actions(driver).moveToElement(newFolder).perform();

        WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='job/New%20folder/']/button")));
        dropdown.click();

        WebElement renameButton = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/job/New%20folder/confirm-rename']")));
        new Actions(driver).moveToElement(renameButton).perform();
        renameButton.click();

        WebElement input = driver.findElement(By.xpath("//input[@name='newName']"));
        input.clear();
        input.sendKeys("New name folder");

        driver.findElement(By.xpath("//button[@name='Submit']")).click();
        driver.findElement(DASHBOARD).click();

        WebElement newFolderName = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='job/New%20name%20folder/']/span")));

        Assert.assertEquals(newFolderName.getText(), "New name folder");
    }
}
