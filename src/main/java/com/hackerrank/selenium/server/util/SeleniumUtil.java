package com.hackerrank.selenium.server.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumUtil {

  public static WebDriver createDriver() {
    WebDriver driver =
        new HtmlUnitDriver(BrowserVersion.CHROME, true) {
          @Override
          protected WebClient newWebClient(BrowserVersion version) {
            WebClient webClient = super.newWebClient(version);
            webClient.getOptions().setThrowExceptionOnScriptError(false);

            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

            return webClient;
          }
        };
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

    return driver;
  }
}
