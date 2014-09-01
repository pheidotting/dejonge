package nl.dias.dias_web.hulp;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Hulp {
    private static final int timeout = 1000;

    private Hulp() {
    }

    public static void vulVeld(WebElement element, String waarde) {
        if (waarde != null && !waarde.equals("")) {
            element.clear();
            element.sendKeys(waarde);
            wachtFf();
        }
    }

    public static void klikEnWacht(WebElement element) {
        Hulp.klikEnWacht(element, timeout);
    }

    public static void klikEnWacht(WebElement element, int timeout) {
        try {
            wachtFf(timeout);
            wachtFf(timeout);
            element.click();
        } catch (NoSuchElementException e) {
        }
    }

    public static void naarAdres(WebDriver driver, String adres) {
        driver.get(adres);

        wachtFf();
    }

    public static String getText(WebElement element) {
        return element.getAttribute("value");
    }

    public static void wachtFf() {
        wachtFf(timeout);
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
        try {
            select.selectByValue(waarde);
        } catch (NoSuchElementException e) {
            select.selectByVisibleText(waarde);
        }
        wachtFf();
    }

    public static boolean controleerVeld(WebElement element, String verwacht) {
        return getText(element).equals(verwacht);
    }
}