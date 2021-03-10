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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class SeleniumGitCITest {
  private WebDriver driver;

  @LocalServerPort
  private int port;

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
    driver.get("http://localhost:" + port);
    System.out.println(driver.getTitle());
    assertTrue(driver.getTitle().contains("Recruitment | Login"));
  }

  @AfterAll
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}