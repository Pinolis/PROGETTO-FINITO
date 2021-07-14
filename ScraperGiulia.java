package scrap;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;

/**extends Scraper, manages the case in which the dinasty is Giulio-Claudia
 * 
 * @author tobia, alberto
 *
 */
public class ScraperGiulia extends Scraper {

    /**takes a dinasty and a driver,
     * builds the instance
     * 
     * @param dinastia the dinasty on which you want to work
     * @param driver the driver you want to use
     */
	public ScraperGiulia(Dinastia dinastia, WebDriver driver) {
		super(dinastia, driver);
	}
	
	/**creates the array containing all the emperors 
     * of the input dinasty in order (Imperatore class),
     * adding Cesare as the first member;
     * does so by scraping information from wikipedia
     */
	public void setBackbone() {
		super.setBackbonePart1();
		ArrayList<Imperatore> backbone = getBackbone();
		backbone.add(0, new Imperatore("Cesare", "https://it.wikipedia.org/wiki/Gaio_Giulio_Cesare"));
		backbone.get(0).setImageLink("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Gaius_Iulius_Caesar_%28Vatican_Museum%29.jpg/100px-Gaius_Iulius_Caesar_%28Vatican_Museum%29.jpg");
		super.setBackbonePart2();
	}
	
	/**set the wives, sons, parents and other info
	 * for the input imperatore, manages Cesare in a special way
	 * 
	 * @param imperatore the emperor you want to work on
	 */
	public void setRelatives(Imperatore imperatore) {
		super.setRelatives(imperatore);
		// se ho a che fare con cesare
		if (imperatore.getUrl().equals("https://it.wikipedia.org/wiki/Gaio_Giulio_Cesare")) {
			//tolgo sporcizia
			ArrayList<Persona> figli = imperatore.getFigli();
			figli.remove(1); figli.remove(2); figli.remove(3);
			imperatore.setFigli(figli);
			
			ArrayList<Persona> mogli = imperatore.getMogli();
			for (int i=mogli.size()-1; i>=0; i--) {
				if (anyNumber(mogli.get(i).getNome())) {
					mogli.remove(i);
				}
			}
			imperatore.setMogli(mogli);
		}
	}

}
