package scrap;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;


/**the class has methods that use the class Scraper
 * to get the information about the roman emperors
 * 
 * @author tobia, alberto
 *
 */
public class ScraperUser {
		
		/** uses the class Scraper to get the family tree
		 * of the dynasty requested
		 * 
		 * @param dinastia the dynaty requested
		 * @return the family tree
		 */

		public static Imperatore getAlberoGenealogico(Dinastia dinastia) {
			
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\LENOVO\\Desktop\\selenium\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			Imperatore root;
			Scraper scraper;
			String nomeDin = dinastia.getNome();
			if (nomeDin.equals("---")) {
				return null;
			}
			else if (nomeDin.equals("Dinastia giulio-claudia")){
				scraper = new ScraperGiulia(dinastia, driver);	
			}
			else if (nomeDin.equals("Dinastia dei Severi")) {
				scraper = new ScraperSeveri(dinastia, driver);
			}
			else {
				scraper = new Scraper(dinastia, driver);
			}
			try {
				scraper.setBackbone();
				Imperatore imperatore = scraper.getBackbone().get(0);
				root = imperatore;
				do {
					scraper.setRelatives(imperatore);
					imperatore = imperatore.getSuccessore();
				} while (imperatore != null);
				scraper.findMothers();
			}
			finally {
				driver.quit();
			}
			return root;
		}
		
		/** counts how many generation there are in a family tree
		 * 
		 * @param root the family tree
		 * @return the number of generation
		 */
		public static int contaGenerazioni(Imperatore root) {
		    Imperatore imperatore = root;
		    int generazioni = 1;
		    int fratelli = 0;
		    while (imperatore.hasSuccessor()) {
			if (imperatore.hasFratelloSuccessore()) {
			    fratelli += 1;
			}
			generazioni += 1;
			imperatore = imperatore.getSuccessore();
		    }
		    return (generazioni - fratelli);
		
		}
}
