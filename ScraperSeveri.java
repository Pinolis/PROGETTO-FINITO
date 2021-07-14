package scrap;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;


public class ScraperSeveri extends Scraper{

	public ScraperSeveri(Dinastia dinastia, WebDriver driver) {
		super(dinastia, driver);
	}
	public void setBackbone() {
		super.setBackbonePart1();
		// Nel caso dei Severi
		ArrayList<Imperatore> backbone = getBackbone();
		// Sostituisco Macrino e Diadumeniano con Eliogabalo e Alessandro Severo
		backbone.remove(4); //Ho invertito gli indici da eliminare perché altrimenti cancellavamo Eliogabalo
		backbone.remove(3);
		super.setBackbonePart2();
	}
}
