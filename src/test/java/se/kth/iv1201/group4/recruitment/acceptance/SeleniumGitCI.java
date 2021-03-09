package se.kth.iv1201.group4.recruitment.acceptance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

@TestInstance(Lifecycle.PER_CLASS)
public class SeleniumGitCI {
  private WebDriver driver;

  @BeforeAll
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--headless");
    driver = new ChromeDriver(options);
    // driver = new ChromeDriver();
  }

  @Test
  public void test() {
    driver.get("https://www.google.com/");
    System.out.println(driver.getTitle());
    assertTrue(driver.getTitle().contains("Googfle"));
  }

  @AfterAll
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}