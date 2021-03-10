package se.kth.iv1201.group4.recruitment.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.github.bonigarcia.wdm.WebDriverManager;
import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.LegacyUser;
import se.kth.iv1201.group4.recruitment.domain.Person;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class SeleniumAcceptanceTest {
  private WebDriver driver;

  @LocalServerPort
  private int port;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private PersonService service;

  private final Locale SWEDISH = new Locale("sv", "SE");
  private final Locale ENGLISH = Locale.ENGLISH;

  @BeforeEach
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--headless");

    // For github actions
    driver = new ChromeDriver(options);

    // For developing the tests
    // driver = new ChromeDriver();
  }

  @Test
  public void testAcceptance() {
    String username = "jdoe2";
    String password = "fdk839Sk@a";
    String baseURL = "http://localhost:" + port + "/";

    driver.get(baseURL + "?lang=en");

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.login.name", null, ENGLISH)));

    // Go to register page
    driver.findElement(By.linkText(messageSource.getMessage("loginForm.text.register", null, ENGLISH))).click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.register.name", null, ENGLISH)));

    // Register user
    driver.findElement(By.id("email")).sendKeys("john.doe2@gmail.com");
    driver.findElement(By.id("name")).sendKeys("John");
    driver.findElement(By.id("surname")).sendKeys("Doe");
    driver.findElement(By.id("password")).sendKeys(password);
    driver.findElement(By.id("username")).sendKeys(username);
    driver.findElement(By.id("ssn")).sendKeys("196701075634");

    WebElement registerButton = driver.findElement(By.xpath("/html/body/main/form/input[7]"));

    // Make sure it's the right button
    assertTrue(registerButton.getAttribute("value")
        .equals(messageSource.getMessage("loginForm.text.register", null, ENGLISH)));

    registerButton.click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.success.name", null, ENGLISH)));

    // Make sure the user got the successpage for the applicant
    assertTrue(driver.getCurrentUrl().equals(baseURL + "success-applicant"));

    // Log out
    driver.findElement(By.linkText(messageSource.getMessage("success.text.logout", null, ENGLISH))).click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.login.name", null, ENGLISH)));

    // Make sure the successful log out message is displayed
    assertTrue(driver.findElement(By.xpath("/html/body/main/form/div")).getText()
        .equals(messageSource.getMessage("loginForm.success.logout", null, ENGLISH)));
  }

  @Test
  public void testChangeLanguageAcceptance() {
    String username = "jdoe";
    String password = "fdk839Sk@a";
    String baseURL = "http://localhost:" + port + "/";

    driver.get(baseURL + "?lang=en");

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.login.name", null, ENGLISH)));

    // Change language
    Select locales = new Select(driver.findElement(By.id("locales")));
    locales.selectByValue(SWEDISH.getLanguage());

    assertTrue(driver.getCurrentUrl().equals(baseURL + "login?lang=sv"));

    // Go to register page
    driver.findElement(By.linkText(messageSource.getMessage("loginForm.text.register", null, SWEDISH))).click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, SWEDISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.register.name", null, SWEDISH)));

    // Register user
    driver.findElement(By.id("email")).sendKeys("john.doe@gmail.com");
    driver.findElement(By.id("name")).sendKeys("John");
    driver.findElement(By.id("surname")).sendKeys("Doe");
    driver.findElement(By.id("password")).sendKeys(password);
    driver.findElement(By.id("username")).sendKeys(username);
    driver.findElement(By.id("ssn")).sendKeys("196801075616");

    WebElement registerButton = driver.findElement(By.xpath("/html/body/main/form/input[7]"));

    // Make sure it's the right button
    assertTrue(registerButton.getAttribute("value")
        .equals(messageSource.getMessage("loginForm.text.register", null, SWEDISH)));

    // Go to the register page
    registerButton.click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, SWEDISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.success.name", null, SWEDISH)));

    // Make sure the user got the successpage for the applicant
    assertTrue(driver.getCurrentUrl().equals(baseURL + "success-applicant"));

    // Log out
    driver.findElement(By.linkText(messageSource.getMessage("success.text.logout", null, SWEDISH))).click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, SWEDISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.login.name", null, SWEDISH)));

    // Make sure the successful log out message is displayed
    assertTrue(driver.findElement(By.xpath("/html/body/main/form/div")).getText()
        .equals(messageSource.getMessage("loginForm.success.logout", null, SWEDISH)));
  }

  @Test
  public void testLegacyUserAcceptance() {
    String username = "jdoe3";
    String password = "fdk839Sk@a";
    String baseURL = "http://localhost:" + port + "/";

    // Add legacy user
    Person p = new Person("name", "surname", "john.doe3@gmail.com", "193801085618", username, password);
    service.addApplicant(new Applicant(p));
    service.addLegacyUser(new LegacyUser(p));

    driver.get(baseURL + "?lang=en");

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.login.name", null, ENGLISH)));

    // Log in
    driver.findElement(By.id("password")).sendKeys(password);
    driver.findElement(By.id("username")).sendKeys(username);

    // Get login button
    WebElement loginButton = driver.findElement(By.xpath("/html/body/main/form/input[3]"));

    // Make sure it's the right button
    assertTrue(
        loginButton.getAttribute("value").equals(messageSource.getMessage("loginForm.text.login", null, ENGLISH)));

    // Log in
    loginButton.click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.success.name", null, ENGLISH)));

    // Fill missing and incorrect data
    WebElement name = driver.findElement(By.id("name"));
    name.clear();
    name.sendKeys("John");
    WebElement surname = driver.findElement(By.id("surname"));
    surname.clear();
    surname.sendKeys("Doe");
    driver.findElement(By.id("password")).sendKeys(password);

    WebElement updateBtn = driver.findElement(By.xpath("/html/body/main/form/input[7]"));

    // Check that its the right button
    assertTrue(updateBtn.getAttribute("value")
        .equals(messageSource.getMessage("registerForm.value.updateButton", null, ENGLISH)));

    updateBtn.click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.login.name", null, ENGLISH)));

    // Login
    driver.findElement(By.id("username")).sendKeys(username);
    driver.findElement(By.id("password")).sendKeys(password);

    // Get login button
    loginButton = driver.findElement(By.xpath("/html/body/main/form/input[3]"));

    // Make sure it's the right button
    assertTrue(
        loginButton.getAttribute("value").equals(messageSource.getMessage("loginForm.text.login", null, ENGLISH)));

    // Log in
    loginButton.click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.success.name", null, ENGLISH)));

    // Make sure the user got the successpage for the applicant
    assertTrue(driver.getCurrentUrl().equals(baseURL + "success-applicant"));

    // Log out
    driver.findElement(By.linkText(messageSource.getMessage("success.text.logout", null, ENGLISH))).click();

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.login.name", null, ENGLISH)));

    // Make sure the successful log out message is displayed
    assertTrue(driver.findElement(By.xpath("/html/body/main/form/div")).getText()
        .equals(messageSource.getMessage("loginForm.success.logout", null, ENGLISH)));

  }

  @Test
  public void testLegacyUserSendMail() {
    String baseURL = "http://localhost:" + port + "/";
    String username = "assdfsdfsdfs";
    String email = "john.doe7@gmail.com";

    // Add legacy user
    Person p = new Person("name", "surname", "john.doe7@gmail.com", "193901087618", username, "passw234!ordfsdfsdf");
    service.addApplicant(new Applicant(p));
    service.addLegacyUser(new LegacyUser(p));

    driver.get(baseURL + "?lang=en");

    // Make sure the right page is displayed
    assertTrue(driver.getTitle().contains(messageSource.getMessage("heading.text.applicationName", null, ENGLISH)));
    assertTrue(driver.getTitle().contains(messageSource.getMessage("pages.login.name", null, ENGLISH)));

    // Go to register page
    driver.findElement(By.linkText(messageSource.getMessage("loginForm.text.forgot", null, ENGLISH))).click();

    // Fill in email
    driver.findElement(By.id("email")).sendKeys(email);

    WebElement sendResetLink = driver.findElement(By.xpath("/html/body/main/form/input[2]"));

    // Check that its the right button
    assertTrue(
        sendResetLink.getAttribute("value").equals(messageSource.getMessage("forgotForm.text.submit", null, ENGLISH)));

    sendResetLink.click();

    assertTrue(driver.findElement(By.xpath("/html/body/main/p")).getText()
        .equals(messageSource.getMessage("forgot.success", null, ENGLISH)));
  }

  @AfterEach
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  // Util for developing the tests
  private static void waitFor(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}