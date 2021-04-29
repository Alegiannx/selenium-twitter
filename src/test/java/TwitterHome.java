import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;

class TwitterHome extends PageBase {

    public TwitterHome(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {
        return this.waitAndReturnElement(By.xpath("//main//h2[@role='heading']")).getText();
    }

    public SideBar getSideBar() {
        return new SideBar(driver);
    }
}
