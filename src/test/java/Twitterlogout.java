import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;

class TwitterLogout extends PageBase {

    public TwitterLogout(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return this.waitAndReturnElement(By.xpath("//div[@tabindex='0']//div//span")).getText();
    }

    public Twitter clickLogout() {
        this.waitAndReturnElement(By.xpath("//div[@data-testid='confirmationSheetConfirm']")).click();
        return new Twitter(this.driver);
    }
}
