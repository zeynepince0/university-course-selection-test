package com.example.course.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
class AdvisorApprovalE2E {
    @LocalServerPort
    private int port;
    WebDriver driver;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Test
    void advisor_approves_enrollment() {
        driver.get("http://localhost:" + port + "/advisor.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enrollmentId"))).sendKeys("1");

        new Select(driver.findElement(By.id("decision"))).selectByVisibleText("APPROVE");
        driver.findElement(By.id("decisionBtn")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "APPROVED"));
        assertTrue(driver.getPageSource().contains("APPROVED"));
    }

    @Test
    void advisor_rejects_enrollment() {
        driver.get("http://localhost:" + port + "/advisor.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enrollmentId")));

        driver.findElement(By.id("enrollmentId")).sendKeys("1");

        new Select(driver.findElement(By.id("decision")))
                .selectByVisibleText("REJECT");

        driver.findElement(By.id("decisionBtn")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("result"), "REJECTED"
        ));

        assertTrue(driver.findElement(By.id("result"))
                .getText().contains("REJECTED"));
    }


    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }
}