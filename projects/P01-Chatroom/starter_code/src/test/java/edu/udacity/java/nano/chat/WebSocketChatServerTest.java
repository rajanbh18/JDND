package edu.udacity.java.nano.chat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class WebSocketChatServerTest {


    private WebDriver driver;

    @Before
    public void setup(){
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/");
    }

    @After
    public void quit(){
        driver.quit();
    }

    @Test
    public void testLoginViewPage(){
        driver.get("http://localhost:8080/");
        Assert.assertEquals("Chat Room Login", driver.getTitle());
    }

    @Test
    public void testChatViewMapping(){
        driver.get("http://localhost:8080/index?username="+"rajan");
        Assert.assertEquals("Chat Room", driver.getTitle());
    }

    /**
     * user enter the chat room
     */
    @Test
    public void testUserJoinedChatRoom(){
        driver.get("http://localhost:8080/index?username="+"rajan");
        WebElement element1 = driver.findElement(By.className("message-container"));
        Assert.assertEquals("rajan joined the chat room.", element1.getText());
    }

    /**
     * user sends message
     */
    @Test
    public void testUserSendMessage(){
        driver.get("http://localhost:8080/index?username="+"rajan");
        WebElement element1 = driver.findElement(By.className("mdui-textfield-input"));
        element1.sendKeys("hello all");
        WebElement element = driver.findElement(By.id("sendButton"));
        element.click();
        String msg = driver.findElement(By.className("message-container")).getText();
        Assert.assertTrue(msg.contains("hello all"));
    }

    /**
     *  user leave the chat room
     */
    @Test
    public void testUserLeftChatRoom(){
        driver.get("http://localhost:8080/index?username="+"rajan");
        WebElement element = driver.findElement(By.id("sendButton"));
        element.click();
        WebElement message = driver.findElement(By.className("mdui-card-content"));
        Assert.assertEquals("rajan joined the chat room.", message.getText());

        WebElement exitElement = driver.findElement(By.className("mdui-btn-icon"));
        exitElement.click();
        Assert.assertEquals("Chat Room Login", driver.getTitle());
    }

}
