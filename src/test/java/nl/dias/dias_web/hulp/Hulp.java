package nl.dias.dias_web.hulp;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Hulp {
    private static final Logger LOGGER = Logger.getLogger(Hulp.class);

    private Hulp() {
    }

    public static void vulVeld(WebElement element, String waarde) {
        if (waarde != null) {
            element.clear();
            element.sendKeys(waarde);
            wachtFf();
        }
    }

    public static void klikEnWacht(WebElement element) {
        Hulp.klikEnWacht(element, 1500);
    }

    public static void klikEnWacht(WebElement element, int timeout) {
        element.click();
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    public static void naarAdres(WebDriver driver, String adres) {
        driver.get(adres);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    public static String getText(WebElement element) {
        return element.getAttribute("value");
    }

    public static void wachtFf() {
        wachtFf(1500);
    }

    public static void wachtFf(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
        }
    }

    public static boolean waardeUitCheckbox(WebElement element) {
        return "true".equals(element.getAttribute("checked"));
    }

    public static void selecteerUitSelectieBox(WebElement element, String waarde) {
        Select select = new Select(element);
        select.selectByValue(waarde);
        wachtFf();
    }
}