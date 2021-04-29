import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;

class TwitterSearchResults extends PageBase {

    public TwitterSearchResults(WebDriver driver) {
        super(driver);
    }

    public String getBodyText() {
        // dont return the old body too fast (explicit wait)
        this.waitUntilURLlike("twitter.com/search?q=%23catlover");
        return this.waitAndReturnElement(By.xpath("//body")).getText();
    }

    public void switchToPhotosTab() {
        this.waitAndReturnElement(By.xpath("(//div[@role='presentation']/a[@role='tab'])[4]")).click();
    }

    // saves the first 4 images in the page
    public void saveImages() {
        for (int i = 1; i < 5; i++) {
            // grab src attribute of i-th image
            String src = this.waitAndReturnElement(By.xpath("(//div[@data-testid='tweetPhoto']/div)[" + i + "]"))
                    .getAttribute("style").split("\"")[1];
            try {
                // read image from src
                BufferedImage bufferedImage = ImageIO.read(new URL(src));
                // save to desktop
                File outputfile = new File(System.getProperty("user.home") + "/Desktop", "saved" + i + ".png");
                ImageIO.write(bufferedImage, "png", outputfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
