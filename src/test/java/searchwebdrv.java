/**
 * Created by Администратор on 08.06.17.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.*;


public class searchwebdrv {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String requiredText;

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        baseUrl = "http://stackoverflow.com/";
        requiredText = "webdriver";
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get(baseUrl);
        WebElement element = driver.findElement(By.cssSelector("button.btn.js-search-submit"));
        driver.findElement(By.xpath("//*[@id=\"search\"]/input")).sendKeys(requiredText +"\n");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

     /*   Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        element.click();
     */
    }

    @Test
    public void allPages() throws Exception {

//        while (isElementPresent(By.xpath("//*[@id=\"mainbar\"]/div[4]/a[6]")) == true)// пока есть куда листать - присутствует кнопка "next"
//        {
        List<WebElement> allLinks = driver.findElements(By.className("result-link"));


        //for(WebElement url:allLinks){
//            for (int i = 0; i<allLinks.size(); i++) {
        for (int i = 0; i < 3; i++) {

            WebElement url = allLinks.get(i);
            String s = allLinks.get(i).getText();

            boolean isContain = s.contains("webdriver");
            if (isContain = false) {
                System.out.println("ссылка " + s + " не содержит слово \"webdriver\"");
            }

            url.click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            s = s.substring(3);
            Assert.assertEquals(s, driver.findElement(By.xpath("//*[@id=\"question-header\"]/h1/a")).getText());
            driver.navigate().back();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            allLinks = driver.findElements(By.className("result-link"));
        }
//            driver.findElement(By.xpath("//*[@id=\"mainbar\"]/div[4]/a[6]")).click(); //click "next"
//        }

        driver.findElement(By.id("nav-tags")).click();
        driver.findElement(By.id("tagfilter")).sendKeys(requiredText);

        driver.get("https://stackoverflow.com/questions/tagged/webdriver");
        List<WebElement> allTags = driver.findElements(By.className("summary"));

        for (int i = 0; i<allTags.size(); i++) {
            Assert.assertEquals(requiredText, allTags.get(i).findElement(By.linkText(requiredText)).getText());

        }
    }
        /*for(WebElement url:allTags) {
            System.out.println("333");
            System.out.println(url.getText());
            Assert.assertEquals(url.getText(), requiredText);
            System.out.println("444");
        }

        driver.findElement(By.linkText(requiredText)).click();
        allTags = driver.findElements(By.className("question-hyperlink"));

        for(WebElement url:allTags) {
            Assert.assertEquals(url.getTagName(), requiredText);
        }

*/

    public boolean isElementPresent(By by) {    //если элемент присутствует на странице
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }





    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

/*    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
*/
    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

