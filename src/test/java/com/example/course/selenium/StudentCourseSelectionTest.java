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
class StudentCourseSelectionTest {
    @LocalServerPort
    private int port;

    WebDriver driver;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Arka planda çalışır
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }




    @Test
    void student_selects_course() {
        System.out.println("DEBUG: Sayfa açılıyor...");
        driver.get("http://localhost:" + port + "/student.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // HATA AYIKLAMA BLOĞU
        try {
            // studentNumber kutusunu bekle
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentNumber")));
        } catch (Exception e) {
            System.out.println("\n❌ HATA! 'studentNumber' elementi bulunamadı.");
            System.out.println("Selenium şu an bu sayfayı görüyor:");
            System.out.println("======================================");
            System.out.println(driver.getPageSource()); // HTML KODUNU KONSOLA BAS
            System.out.println("======================================\n");
            throw e; // Testi durdur
        }

        // Eğer element bulunduysa işleme devam et
        driver.findElement(By.id("studentNumber")).sendKeys("202012345");

        // CSE102 dersini seç
        driver.findElement(By.id("courses")).sendKeys("CSE102");

        // Butona tıkla
        driver.findElement(By.id("selectBtn")).click();

        // Sonucu bekle
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("result"), "Selected"));

        assertTrue(driver.getPageSource().contains("Selected"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}