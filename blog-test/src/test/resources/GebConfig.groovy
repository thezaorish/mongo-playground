// See: http://www.gebish.org/manual/current/configuration.html

import org.openqa.selenium.firefox.FirefoxDriver

waiting {
    timeout = 2
}

reportsDir = "target/geb-reports"

driver = { new FirefoxDriver() }