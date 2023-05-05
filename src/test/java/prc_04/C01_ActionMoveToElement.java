package prc_04;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Set;

public class C01_ActionMoveToElement {
    // https://amazon.com adresine gidiniz
    // sag ust bolumde bulunan dil secenek menusunun acilmasi icin mause'u bu menunun ustune getirelim
    // Change country/region butonuna basiniz
    // United States dropdown'undan 'Turkey (Türkiye)' seciniz
    // Go to website butonuna tiklayiniz
    // acilan yeni sayfadanin Title'inin Elektronik icerdigini test ediniz

    WebDriver driver;
    @Test
    public void test01() throws InterruptedException {
        driver.get("https://amazon.com");
        Actions actions=new Actions(driver);
        String sayfa1Handle=driver.getWindowHandle();

        WebElement dilMenu=driver.findElement(By.xpath("//a[@id='icp-nav-flyout']"));
        actions.moveToElement(dilMenu).perform();
        Thread.sleep(2000);

        driver.findElement(By.xpath("(//div[@class='icp-mkt-change-lnk'])[1]")).click();

        //dropdown için 1:dropdown locate edilir, 2: select objesi oluşturulur. 3: seçilecek seçeneklocate edilir
        WebElement dropdown=driver.findElement(By.xpath("//select[@id='icp-dropdown']"));
        Thread.sleep(2000);
        Select select=new Select(dropdown);
        select.selectByVisibleText("Turkey (Türkiye)");
        Thread.sleep(2000);
        //dropdown toparlanması için farklı bir yere clik yapıyoruz.
        driver.findElement(By.xpath("//span[text()='Changing country/region website']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//input[@class='a-button-input']")).click();
        Thread.sleep(2000);

        Set<String> wH=driver.getWindowHandles();
        System.out.println("sayfa 1 handle = "+sayfa1Handle);
        System.out.println("wH değerleri= " + wH);

        String sayfa2Handle="";
        for (String each: wH){
            if (!each.equals(sayfa1Handle)){
                sayfa2Handle=each;
            }
        }

        driver.switchTo().window(sayfa2Handle);

        String title=driver.getTitle();
        System.out.println("sayfa basligi = "+title);
        Assert.assertTrue(title.contains("Elektronik"));

    }
}
