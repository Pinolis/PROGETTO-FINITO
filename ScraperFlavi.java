package scrap;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

/**extends Scraper, manages the case in which the dinasty is Flavia
 * 
 * @author tobia
 *
 */
public class ScraperFlavi extends Scraper{
	
    /**takes a dinasty and a driver,
     * builds the instance
     * 
     * @param dinastia the dinasty on which you want to work
     * @param driver the driver you want to use
     */
	public ScraperFlavi(Dinastia dinastia, WebDriver driver) {
		super(dinastia, driver);
	}
	
	/**set the wives, sons, parents and other info
	 * for the input imperatore
	 * 
	 * @param imperatore the emperor you want to work on
	 */
	public void setRelatives(Imperatore imperatore) {
		super.setRelatives(imperatore);
		// tolgo dalle mogli alcuni link errati
		ArrayList<Persona> mogli = imperatore.getMogli();
		for (int n=mogli.size()-1; n>=0;n--) {
			if (anyNumber(mogli.get(n).getNome())) {
				mogli.remove(n);
			}
		}
		
	}
}
