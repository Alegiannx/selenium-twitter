import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;

public class SideBar extends PageBase {
    public SideBar(WebDriver driver) {
        super(driver);
    }

    public TwitterLogout clickLogOut() {
        this.waitAndReturnElement(By.xpath("//div[@data-testid='SideNav_AccountSwitcher_Button']")).click();
        this.waitAndReturnElement(By.xpath("//a[@data-testid='AccountSwitcher_Logout_Button']")).click();
        return new TwitterLogout(this.driver);
    }

    // explore gets a seperate function because we need class model
    public TwitterExplore clickExplore() {
        this.waitAndReturnElement(By.xpath("//a[@href='/explore']")).click();
        return new TwitterExplore(this.driver);
    }

    // other anchors are simply chosen by text
    public void clickAnchor(String name) {
        this.waitAndReturnElement(By.xpath("//a[@href='/" + name + "']")).click();
    }
}
