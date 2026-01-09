package com.example.course.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentCourseSelectionE2E {

    @LocalServerPort
    int port;

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void openPage() {
        driver.get("http://localhost:" + port + "/student.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentNumber")));
    }

    private String submit(String studentNo, String course) {
        driver.findElement(By.id("studentNumber")).sendKeys(studentNo);
        driver.findElement(By.id("courses")).sendKeys(course);
        driver.findElement(By.id("selectBtn")).click();
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBe(By.id("result"), "Processing...")
        ));
        return driver.findElement(By.id("result")).getText();
    }

    @Test
    void student_not_found() {
        openPage();
        String result = submit("0000000", "CSE101");
        assertTrue(result.contains("Student not found"));
    }

    @Test
    void course_not_found() {
        openPage();
        String result = submit("202012345", "ERROR404");
        assertTrue(result.contains("Course not found"));
    }

    @Test
    void class_year_not_sufficient() {
        openPage();
        String result = submit("202012345", "CSE401");
        assertTrue(result.contains("Class year not sufficient"));
    }

    @Test
    void credit_limit_exceeded() {
        openPage();
        String result = submit("202012345", "CREDIT999");
        assertTrue(result.contains("Credit limit exceeded"));
    }

    @Test
    void quota_full() {
        openPage();
        String result = submit("202012345", "FULL101");
        assertTrue(result.contains("Course quota full"));
    }

    @Test
    void successful_selection() {
        openPage();
        String result = submit("202012345", "CSE102");
        assertTrue(result.contains("Selected"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
