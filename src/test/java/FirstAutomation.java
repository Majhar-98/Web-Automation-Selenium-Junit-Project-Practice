import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static java.lang.Thread.sleep;
import static org.apache.commons.io.FileUtils.copyFile;


public class FirstAutomation {
    WebDriver driver;
    @Before
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

       }
       @Test
       public void getTitle(){
        driver.get("https://demoqa.com/");
        String title_actual = driver.getTitle();
        String title_expected = "DEMOQA";
        System.out.println(title_actual);
        Assert.assertEquals(title_actual,title_expected);
       }
       @Test
       public void checkIFImageExist(){
        driver.get("https://demoqa.com/");
        boolean status = driver.findElement(By.xpath("//header/a[1]/img[1]")).isDisplayed();
        System.out.println(status);
        Assert.assertTrue(status);
       }
       @Test
       public void submitForm() throws InterruptedException {
           driver.get("https://demoqa.com/text-box");
           //driver.findElement(By.id("userName")).sendKeys("user");
           //driver.findElement(By.cssSelector("[type=text]")).sendKeys("user");
           //driver.findElement(By.id("userEmail")).sendKeys("user@test.com");

           List <WebElement> formControl= driver.findElements(By.className("form-control"));
           formControl.get(0).sendKeys("user");
           formControl.get(1).sendKeys("user@test.com");
           WebElement txtAddress= By.cssSelector("[placeholder='Current Address']").findElement(driver);
           txtAddress.sendKeys("Dhaka");
           WebElement btnSubmit= driver.findElement(By.id("submit"));
           JavascriptExecutor executor= (JavascriptExecutor) driver;
           executor.executeScript("window.scrollBy(0,500)");
           //executor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
           sleep(1000);
           btnSubmit.click();
           //driver.findElement(By.id("submit")).click();
           String name_actual=driver.findElement(By.id("name")).getText();
           String name_expected="user";
           Assert.assertTrue(name_actual.contains(name_expected));
       }

       @Test
       public void ClickOnButtons(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement>buttons = driver.findElements(By.cssSelector("[type=button]"));
        Actions actions  = new Actions(driver);
        actions.doubleClick(buttons.get(1)).perform();
        actions.contextClick(buttons.get(2)).perform();
        actions.click(buttons.get(3)).perform();

        String actual_message1 = driver.findElement(By.id("doubleClickMessage")).getText();
        String expected_message1 = "You have done a double click";
        String actual_message2 = driver.findElement(By.id("rightClickMessage")).getText();
        String expected_message2 = "You have done a right click";
        String actual_message3 = driver.findElement(By.id("dynamicClickMessage")).getText();
        String expected_message3 = "You have done a dynamic click";

        Assert.assertTrue(actual_message1.contains(expected_message1));
        Assert.assertTrue(actual_message2.contains(expected_message2));
        Assert.assertTrue(actual_message3.contains(expected_message3));

       }
       @Test
       public void alert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        sleep(2000);
        driver.switchTo().alert().accept();

        driver.findElement(By.id("timerAlertButton")).click();
        sleep(6000);
        driver.switchTo().alert().accept();

        driver.findElement(By.id("confirmButton")).click();
        sleep(1000);
        driver.switchTo().alert().accept();

       }
       @Test
       public void SelectDate(){
        driver.get("https://demoqa.com/date-picker");
        //driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.CONTROL+"a",Keys.BACK_SPACE);
        //driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("02/10/2023");
        //driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
        WebElement calander = driver.findElement(By.id("datePickerMonthYearInput"));
        calander.sendKeys(Keys.CONTROL+"a",Keys.BACK_SPACE);
        calander.sendKeys("02/10/2023");
        calander.sendKeys(Keys.ENTER);
       }
       @Test
       public void selectDropdownClassic() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");
        Select select = new Select(driver.findElement(By.id("oldSelectMenu")));
        //select.selectByValue("2");
        //select.selectByIndex(5);
           sleep(6000);
        select.selectByVisibleText("Green");

       }

    @Test
    public void selectDropdown() {
        driver.get("https://demoqa.com/select-menu");
        Select color = new Select(driver.findElement(By.id("oldSelectMenu")));
        color.selectByValue("1");
        Select cars = new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
        }
    }

    @Test
    public void capsLetter(){
        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(By.name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("Selenium Webdriver");
        action.keyUp(Keys.SHIFT);
        action.perform();
    }

    public void mouseHover(){
        driver.get("https://green.edu.bd/");
        List <WebElement> menuAboutElement=driver.findElements(By.xpath("//a[contains(text(),\"About Us\")]"));
        Actions actions=new Actions(driver);
        actions.moveToElement(menuAboutElement.get(2)).perform();


    }

    @Test
    public void takeScreenShot() throws IOException {
        driver.get("https://demoqa.com/");
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resurces/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        copyFile(screenshotFile, DestFile);
    }

    @Test
    public void keyboardEvents() throws InterruptedException {
        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(By.name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("Selenium Webdriver")
                .keyUp(Keys.SHIFT)
                .doubleClick()
                .contextClick()
                .perform();

        sleep(5000);
    }

    @Test
    public void handleMultipleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        sleep(3000);
        ArrayList<String> w = new ArrayList(driver.getWindowHandles());
        //switch to open tab
        driver.switchTo().window(w.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertEquals(text,"This is a sample page");
        driver.close();
        driver.switchTo().window(w.get(0));


    }

    @Test
    public void webTables(){
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.xpath("//span[@id='edit-record-1']//*[@stroke='currentColor']")).click();
        driver.findElement(By.id("submit")).click();

        }
    @Test
    public void handleMultipleWindows(){
        driver.get("https://demoqa.com/browser-windows");

        //Thread.sleep(5000);
        //WebDriverWait wait = new WebDriverWait(driver, 30);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text= driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
            }

        }
        driver.close();
        driver.switchTo().window(mainWindowHandle);


    }
        @Test
    public void scrapData() {
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i = 0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num[" + i + "] " + cell.getText());
            }
        }
    }
    @Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();

    }

    @Test
    public void modalDialog(){
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(By.id("showSmallModal")).click();
        String text= driver.findElement(By.className("modal-body")).getText();
        System.out.println(text);
        driver.findElement(By.id("closeSmallModal")).click();
    }
    
    @After
    public void closeBrowser(){
        //driver.quit();
       }
    }

