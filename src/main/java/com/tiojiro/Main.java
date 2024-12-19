package com.tiojiro;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.tiojiro.Constants.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        StringBuilder checkDivErrorText = new StringBuilder(CHECK_STRING_ERROR);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        do {
            ChromeDriver driver = new ChromeDriver(options);
            try {
                printConsoleMsg(BEGIN);
                driver.get("https://www.sef.pt/pt/Pages/Homepage.aspx");
                String currentTitle = driver.getTitle();
                printConsoleMsg(LOADED_PAGE + currentTitle);

                WebElement loginLauncher = driver.findElement(new By.ByClassName("login-launcher"));
                loginLauncher.click();

                WebElement txtUsername = driver.findElement(new By.ById("txtUsername"));
                WebElement txtPassword = driver.findElement(new By.ById("txtPassword"));
                txtUsername.sendKeys(USER);
                txtPassword.sendKeys(PASS);

                WebElement btnLogin = driver.findElement(new By.ById("btnLogin"));
                btnLogin.click();

                currentTitle = driver.getTitle();

                if (HOME.equals(currentTitle.trim())) {
                    printConsoleMsg(LOGIN_OK);

                    WebElement optionAutoRenew = driver.findElement(new By.ById("renovacaoAutomaticaLink"));
                    optionAutoRenew.click();

                    WebElement txtAuthPanelPassword = driver.findElement(new By.ById("txtAuthPanelPassword"));
                    WebElement txtAuthPanelDocument = driver.findElement(new By.ById("txtAuthPanelDocument"));
                    txtAuthPanelPassword.sendKeys(PASS);
                    txtAuthPanelDocument.sendKeys(DOCUMENT);

                    WebElement btnLoginSubmit = driver.findElement(new By.ById("btnAutenticaUtilizador"));
                    btnLoginSubmit.click();

                    checkErrorText(driver, checkDivErrorText);
                } else {
                    printConsoleMsg(LOGIN_ERROR);
                }
                printConsoleMsg(END);
            } catch (NoSuchElementException e) {
                printConsoleMsg(OPEN_COLOR_RED + ELEMENT_NOT_FOUND + CLOSE_COLOR_RED);
                printConsoleMsg(END);
            } finally {
                driver.quit();
                TimeUnit.HOURS.sleep(TWO);
            }
        } while (checkDivErrorText.toString().startsWith(CHECK_STRING_ERROR));
    }

    private static void checkErrorText(ChromeDriver driver, StringBuilder checkDivErrorText) {
        try {
            WebElement divError = driver.findElement(new By.ById("ctl00_ctl53_g_49abea8d_9129_4f50_bf46_33662dfac0a6_ctl00_lblAuthError"));
            checkDivErrorText.setLength(ZERO);
            checkDivErrorText.append(divError.getText().trim());
            printConsoleMsg(checkDivErrorText.toString());
            if (!checkDivErrorText.toString().startsWith(CHECK_STRING_ERROR)) {
                checkSefWebsiteAlert();
            }
        } catch (NoSuchElementException e) {
            checkSefWebsiteAlert();
        }
    }

    private static void checkSefWebsiteAlert() {
        printConsoleMsg(OPEN_COLOR_RED + CHECK_SEF_WEBSITE + CLOSE_COLOR_RED);
        playWindowsAlertSong();
        openPopUpAlertMsg();
    }

    private static void printConsoleMsg(String msg) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String log = OPEN_BRACKET + LocalDateTime.now().format(sdf) + CLOSE_BRACKET;
        System.out.println(log + msg);
    }

    private static void openPopUpAlertMsg() {
        JFrame j = new JFrame();
        j.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(j, CHECK_SEF_WEBSITE);
    }

    private static void playWindowsAlertSong() {
        final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
        if (runnable != null)
            runnable.run();
    }
}