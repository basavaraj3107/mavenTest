package test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class BrowserStackTest {
    public static void main(String[] args) {
        // Set BrowserStack credentials
        String userName = "basavarajapatil_YDIkUX";
        String accessKey = "kRxFznJCJSS6Fzxw5wJa";

        // DesiredCapabilities for the browser
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "130");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "11");
        caps.setCapability("name", "BrowserStack Test");

        try {
            // Create a Remote WebDriver instance to trigger the BrowserStack session
            WebDriver driver = new RemoteWebDriver(
                    new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"),
                    caps
            );

            // Open a website
            driver.get("http://www.fireflink.com");

            // Perform your test steps
            System.out.println("Title of the page is: " + driver.getTitle());

            // Quit the driver session after the test is complete
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
