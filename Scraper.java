package scrap;
import java.lang.IndexOutOfBoundsException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**a Scraper instance requires a Dinastia object and a WebDriver object.
 * You can use its methods to find the emperors of the given dinasty
 * and find the attributes of the emperors you've found;
 * at the end you will have a rooted graph, the root being the first
 * emperor of the dinasty
 * 
 * @author tobia, alberto
 *
 */
public class Scraper {
	
	private static final String URL_START = "https://it.wikipedia.org/wiki/Imperatori_romani";
    private ArrayList<Imperatore> backbone;
    private Dinastia dinastia;
    private WebDriver driver;
	
    /**takes a dinasty and a driver,
     * builds the instance
     * 
     * @param dinastia the dinasty on which you want to work
     * @param driver the driver you want to use
     */
    public Scraper(Dinastia dinastia, WebDriver driver) {
    	this.dinastia = dinastia;
    	this.driver = driver;
    	this.backbone = new ArrayList<Imperatore>();
	}
    
    
    /**set the backbone manually,
     * useful if you want to modify the backbone you already set
     * 
     * @param backbone a list of Imperatore
     */
    public void setBackboneManually(ArrayList<Imperatore> backbone) {
    	this.backbone = backbone;
    }
    
    /**creates the array containing the all emperors 
     * of the input dinasty in order (Imperatore class)
     * does so by scraping information from wikipedia
     * (part1)
     */
    protected void setBackbonePart1() {
    	
		driver.get(URL_START);
		// cerco tutti gli elementi table
		List<WebElement> tables = driver.findElements(By.tagName("table"));
		// cerco l'elemento table della mia dinastia
		int indiceDinastia = dinastia.getTableIndex();
		//per test
		//int indiceDinastia = 6; 
		WebElement workingTable = tables.get(indiceDinastia);
		// cerco i tag bold, trovando nome e date di ogni imperatore
		List<WebElement> bolds = workingTable.findElements(By.tagName("b"));
		
		//ciclo solo sui nomi(link) e ignoro le date [non tiene conto dei casi particolari]
		for (int i=0; i<bolds.size();i++) {
			WebElement nameLink = bolds.get(i);
			String name = nameLink.getText();
			if (anyNumber(name)) {
				// sistemata la virgola che escludeva Claudio II
				continue;
			}
			String link = nameLink.findElement(By.tagName("a")).getAttribute("href");
			backbone.add(new Imperatore(name, link));
		}
		//prendo l'immagine profilo degli imperatori
		List<WebElement> immagini = workingTable.findElements(By.tagName("img"));
		assert immagini.size()==backbone.size();
		for (int i=0;i<backbone.size();i++) {
			backbone.get(i).setImageLink(immagini.get(i).getAttribute("src"));
		}
    }
    /**creates the array containing the all emperors 
     * of the input dinasty in order (Imperatore class)
     * does so by scraping information from wikipedia
     * (part2)
     */
    protected void setBackbonePart2() {
    	for (int n=1; n<backbone.size(); n++) {
    		backbone.get(n-1).setSuccessore(backbone.get(n));
    	}
    }
    /**creates the array containing the all emperors 
     * of the input dinasty in order (Imperatore class)
     * does so by scraping information from wikipedia
     */
    public void setBackbone() {
    	this.setBackbonePart1();
    	this.setBackbonePart2();
    }
    
    /**get the backbone
     * 
     * @return the list containing all the emperors in order
     */
	public ArrayList<Imperatore> getBackbone() {
		return backbone;
	}
	
	/**set the wives, sons, parents and other info
	 * for the input imperatore
	 * 
	 * @param imperatore the emperor you want to work on
	 */
	public void setRelatives(Imperatore imperatore) {
		driver.get(imperatore.getUrl());
		WebElement sinottico = driver.findElement(By.className("sinottico"));
		List<WebElement> tableRows = sinottico.findElements(By.tagName("tr"));
		String dataNascita = "";
		String dataMorte = "";
		for (WebElement row : tableRows) {
			String key;
			try{
				key = row.findElement(By.tagName("th")).getText();
			}
			catch (Exception noSuchElementException) {
				continue;
			}
			if (key.equals("Coniuge") || key.equals("Consorte") || key.equals("Coniugi")) {
				ArrayList<Persona> mogli = trovaPersone(row, false);
				if (mogli.size() == 0) {
					mogli = null;
				}
				imperatore.setMogli(mogli);
			}
			else if (key.equals("Figli")) {
				ArrayList<Persona> figli = trovaPersone(row, false);
				figli = prepToSetFigli(imperatore, figli);
				if (figli.size() == 0) {
					figli = null;
				}
				imperatore.setFigli(figli);
			}
			else if (key.equals("Madre")) {
				ArrayList<Persona> madre = trovaPersone(row, false);
				if (madre.size() == 1) {
					imperatore.setMadre(madre.get(0));
				}
			}
			else if (key.equals("Nascita")) {
				try {
					dataNascita = trovaPersone(row, true).get(0).getNome();
				}
				catch (IndexOutOfBoundsException eccezione) { 
					dataNascita = "";
				}
			}
			else if (key.equals("Morte")) {
				try {
					dataMorte = trovaPersone(row, true).get(0).getNome();
				}
				catch (IndexOutOfBoundsException eccezione) {
					dataMorte = "";
				}
			}
			//si può aggiungere altro
		}
		imperatore.setNascitaMorte(dataNascita + " - " + dataMorte);

	}
	/** check whether the emperor is adopted
	 *  the emperor is not adopted if his mother is
	 *  one of the wifes of his father
	 */
	public void findMothers() {
		for (int n=0; n<backbone.size(); n++) {
			Imperatore imperatore = getBackbone().get(n);
			if (imperatore.hasSuccessor() && imperatore.getSuccessore().hasMother() && imperatore.hasMogli()) {
				for (int i=0; i<imperatore.getMogli().size();i++) {
					// se la moglie in questione è la madre del successore
					if (imperatore.getMogli().get(i).getUrl().equals(imperatore.getSuccessore().getMadre().getUrl())) {
						Madre madre = new Madre(imperatore.getMogli().get(i).getNome(), imperatore.getMogli().get(i).getUrl());
						ArrayList<Persona> mogli = imperatore.getMogli();
						// sostistuisco la persona con la madre
						mogli.set(i, madre);
						imperatore.setMogli(mogli);
						imperatore.getSuccessore().setMadre(madre);
						imperatore.getSuccessore().setAdopted(false);
						/*
						 * potremmo andare a cercare anche la madre del fratello successore,
						 * ora nemmeno entra a controllare perchè sicuramente la madre non è moglie del fratello
						 */
						}
					}
				}
			}
		}
	/**checks whether one (or two) of the sons is(are) the next emperor
	 * 
	 * @param imperatore the emperor
	 * @param figli the sons of the emperor
	 * @return the sons of the emperor changed to Imperatore if they are emperors
	 */
	private ArrayList<Persona> prepToSetFigli(Imperatore imperatore, ArrayList<Persona> figli) {
		Imperatore successore = imperatore.getSuccessore();
		Persona scambio;
		for (int i=0;i<figli.size();i++) {
			//se è il successore devo creare il collegamento
			if (successore == null) {
				break;
			}
			else if (figli.get(i).getUrl().equals(successore.getUrl())) {
				//gioco delle tre carte per metterlo in prima posizione
				scambio = figli.get(0);
				figli.set(i, scambio);
				figli.set(0, successore);
				
				if (successore.getSuccessore() == null) {
					break;
				}
				//se è il successore del successore vuol dire che i prossimi due sono fratelli
				for (int n=0; n<figli.size(); n++) {
					if (figli.get(n).getUrl().equals(successore.getSuccessore().getUrl())) {
						//lo metto in seconda posizione
						scambio = figli.get(1);
						figli.set(n, scambio);
						figli.set(1, successore.getSuccessore());
						successore.setFratelloSuccessore(true);
					}
				}
			}
		}
		return figli;
	}
	
	/**takes a table row and scrapes the needed information,
	 * then returns the found people
	 * 
	 * @param row
	 * @return an ArrayList containing the people found
	 */
	private ArrayList<Persona> trovaPersone(WebElement row, boolean cercaNumeri) {
		ArrayList<Persona> elements = new ArrayList<Persona>();
		List<WebElement> links = row.findElements(By.tagName("a"));
		for (int i=0; i<links.size();i++) {
			WebElement tagA = links.get(i);
			String name = tagA.getText();
			String link = tagA.getAttribute("href");
			if (name.contains("[")) {
				continue;
			}
			else if (anyNumber(name) && !cercaNumeri) {
				continue;
			}
			else if (cercaNumeri && anyLetter(name) && !(name.contains("a.C.") || name.contains("d.C."))) {
				continue;
			}
			elements.add(new Persona(name, link));
		}
		return elements;
	}

	
	/**control whether in the given string is present
	 * any number
	 * 
	 * @param string
	 * @return true if there is a number in the string
	 */
	protected boolean anyNumber(String string) {
		List<String> numbers = Arrays.asList("0","1","2","3","4","5","6","7","8","9");
		for (int i=0;i<string.length(); i++) {
			//if (string.charAt(i) > 0x29 && string.charAt(i) < 0x3a) 
			String z = Character.toString(string.charAt(i));
			if (numbers.contains(z))
			{
				return true;
			}
		}
		return false;
	}
	/**control whether in the given string is present
	 * any letter
	 * 
	 * @param string
	 * @return true if there is a letter in the string
	 */
	protected boolean anyLetter(String string) {
		List<String> lettere = Arrays.asList("q","Q","w","W","e","E","r","R","t","T","y","Y","u","U","i","I","o","O","p","P","a","A","s","S","d","D","f","F","g","G","h","H","j","J","k","K","l","L","z","Z","x","X","c","C","v","V","b","B","n","N","m","M");
		for (int i=0;i<string.length(); i++) {
			String z = Character.toString(string.charAt(i));
			if (lettere.contains(z))
			{
				return true;
			}
		}
		return false;
	}

}
