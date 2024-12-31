package com.projects.homepageapi.services

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Service

@Service
class SeleniumService() {
    fun connect(url: String): String {
        WebDriverManager.chromedriver().setup()

        val options = ChromeOptions()
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920x1080")
        val driver: WebDriver = ChromeDriver(options)

        driver.get(url)

        val content = driver.pageSource
        driver.quit()
        return content
    }

}
