import org.junit.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.By;
import java.util.*;

/**
 * Main Selenium Testing Class
 */
public class TwitterTest {
    public WebDriver driver;
    // Page Object Pattern instances
    TwitterLogin loginPage;
    TwitterHome homePage;
    TwitterExplore explorePage;
    TwitterSearchResults searchResultsPage;
    TwitterLogout logoutPage;
    Twitter twitterPage;
    // Sidebar instance, common amongst most pages
    SideBar sidebar;

    /**
     * Set up and configure Chrome Web Driver
     */
    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();

        // options object
        ChromeOptions options = new ChromeOptions();
        // start chrome maximized
        options.addArguments("start-maximized");
        // set default download location to desktop
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", System.getProperty("user.home") + "/Desktop");
        options.setExperimentalOption("prefs", prefs);

        // create the driver with given options
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    /**
     * Test Logging In and Logging out
     */
    @Test
    public void logTest() {
        login();

        // test many similar pages from array
        multipleStaticPagesTest(sidebar, new String[] { "Home", "Explore", "Notifications", "Messages" });

        logout();
    }

    /**
     * Test sidebar pages (they are similar so they are "automated" in a method
     * below)
     */
    @Test
    public void sidebarTest() {
        login();

        // test many similar pages from array
        multipleStaticPagesTest(sidebar, new String[] { "Home", "Explore", "Notifications", "Messages" });

        logout();
    }

    /**
     * Search Twitter for #catlover and save the first 4 images that come up (to
     * Desktop)
     */
    @Test
    public void searchAndDownloadTest() {
        login();

        // go to explore page
        explorePage = sidebar.clickExplore();
        // search #catlover and make sure we are at a relevant page
        searchResultsPage = explorePage.search("#catlover");
        Assert.assertTrue(searchResultsPage.getBodyText().contains("cat"));
        // download the first 4 photos from twitter posts (likely very cute cats!)
        searchResultsPage.switchToPhotosTab();
        searchResultsPage.saveImages();

        logout();
    }

    /**
     * Quit Web Driver
     */
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Log In (and get sidebar instance), used in all tests
     */
    public void login() {
        loginPage = new TwitterLogin(this.driver);
        // make sure the page has some familiar text
        Assert.assertTrue(loginPage.getHeadingText().contains("Log in to Twitter"));
        // close cookie bar
        loginPage.closeCookieBar();
        // fill in username & password
        loginPage.fillLoginInputs();

        homePage = loginPage.clickLogIn();
        // make sure the page has some familiar text
        Assert.assertTrue(homePage.getHeadingText().contains("Home"));
        // sidebar accessible in most twitter pages
        sidebar = homePage.getSideBar();
    }

    /**
     * Log Out, used in all tests
     */
    public void logout() {
        logoutPage = sidebar.clickLogOut();
        // make sure the page has some familiar text
        Assert.assertTrue(logoutPage.getHeadingText().contains("Log out of Twitter?"));

        twitterPage = logoutPage.clickLogout();
        // make sure the page has some familiar text
        Assert.assertTrue(twitterPage.getMainText().contains("Join Twitter today"));
    }

    /**
     * Helper function used in testing above. Automatically tests some similar pages
     * (essentially most pages accessible from the sidebar)
     * 
     * @param sidebar: the instance representing Twitter's literal NavBar on the
     *                 left side of the screen
     * @param names:   the array of names of the pages to be automatically tested
     */
    private void multipleStaticPagesTest(SideBar sidebar, String[] names) {
        // ran for each element in array
        for (String name : names) {
            // click one of the navigation options
            sidebar.clickAnchor(name.toLowerCase());
            // wait a bit and grab the body
            sidebar.waitUntilURLlike("twitter.com/" + name.toLowerCase());
            String bodyText = sidebar.waitAndReturnElement(By.xpath("//body")).getText();
            // make sure the page is loaded by checking some text
            Assert.assertTrue(bodyText.contains(name));
        }
    }
}
