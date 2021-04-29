import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;

class TwitterExplore extends PageBase {

    public TwitterExplore(WebDriver driver) {
        super(driver);
    }

    public TwitterSearchResults search(String text) {
        this.waitAndReturnElement(By.xpath("//input[@data-testid='SearchBox_Search_Input']")).sendKeys(text + "\n");
        return new TwitterSearchResults(this.driver);
    }
}
