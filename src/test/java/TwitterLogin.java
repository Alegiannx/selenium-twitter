import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;

class TwitterLogin extends PageBase {

    public TwitterLogin(WebDriver driver) {
        super(driver);
        this.driver.get("https://twitter.com/login");
    }

    public void fillLoginInputs() {
        this.waitAndReturnElement(By.name("session[username_or_email]")).sendKeys("SeleniumTwitty");
        this.waitAndReturnElement(By.name("session[password]")).sendKeys("s3l3niUM");
    }

    public void closeCookieBar() {
        this.waitAndReturnElement(By.xpath("//body//div[@role='button']")).click();
    }

    public String getHeadingText() {
        return this.waitAndReturnElement(By.xpath("//h1")).getText();
    }

    public TwitterHome clickLogIn() {
        this.waitAndReturnElement(By.xpath("//div[@data-testid='LoginForm_Login_Button']")).click();
        return new TwitterHome(this.driver);
    }
}
