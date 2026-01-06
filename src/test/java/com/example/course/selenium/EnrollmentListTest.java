package com.example.course.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EnrollmentListTest {
    @LocalServerPort
    private int port;

    WebDriver driver;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    @Test
    void student_views_enrollments() {
        driver.get("http://localhost:8080/enrollments.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentNumber")));

        driver.findElement(By.id("studentNumber")).sendKeys("202012345");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("getBtn"))).click();

        // SQL'deki CSE101 dersinin ekranda belirmesini bekle
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "CSE101"));
        assertTrue(driver.getPageSource().contains("CSE101"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}