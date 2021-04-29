import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;

class Twitter extends PageBase {

    public Twitter(WebDriver driver) {
        super(driver);
    }

    public String getMainText() {
        // dont return the old body too fast
        this.waitUntilURLlike("twitter.com/?logout");
        return this.waitAndReturnElement(By.xpath("//body")).getText();
    }
}
