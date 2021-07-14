package scrap;

import java.util.ArrayList;

/**the Imperatore class represents a roman emperor,
 * has wives, sons, parents, successor and other info
 * extends Persona
 * 
 * @author tobia, alberto
 */
public class Imperatore extends Persona {

	private ArrayList<Persona> mogli;
	private ArrayList<Persona> figli;
	private Imperatore successore;
	private Persona madre;
	private boolean fratelloSuccessore;
	private boolean adopted;
	private String nascitaMorte;
	private String imageLink;
	// da aggiungere poi altre informazioni
	
    /**create the instance taking the name
     * and the wikipedia's page url of the
     * emperor we want to represent
     * 
     * @param nome name of the emperor
     * @param url url of the wikipedia page
     */
	public Imperatore(String nome, String url) {
		super(nome, url);
		adopted = true;
	}
	
	/**get wives
	 * 
	 * @return the wives
	 */
	public ArrayList<Persona> getMogli() {
		return mogli;
	}
	
	/**set wives
	 * 
	 * @param mogli the wives
	 */
	public void setMogli(ArrayList<Persona> mogli) {
		this.mogli = mogli;
	}
	
	/**get sons and daughters
	 * 
	 * @return the sons and daughters
	 */
	public ArrayList<Persona> getFigli() {
		return figli;
	}

	/**set sons and daughters
	 * 
	 * @param figli the sons and daughters
	 */
	public void setFigli(ArrayList<Persona> figli) {
		this.figli = figli;
	}
	
	/**get father
	 * 
	 * @return the father
	 */

	/**get successor
	 * 
	 * @return the successor
	 */
	public Imperatore getSuccessore() {
		return successore;
	}

	/**set successor
	 * 
	 * @param successore the successor
	 */
	public void setSuccessore(Imperatore successore) {
		this.successore = successore;
	}
	
	/**true if there is a successor,
	 * false if this is the last empereor of the dinasty
	 * 
	 * @return boolean
	 */
	public boolean hasSuccessor() {
		if (successore == null) {
			return false;
		}
		else {
			return true;
		}
	}

	/**returns true if the successor is also his brother
	 * false otherwise
	 * 
	 * @return whether the successor is his brother
	 */
	public boolean hasFratelloSuccessore() {
		return fratelloSuccessore;
	}

	/**set whether the successor is also the brother of the emperor
	 * 
	 * @param fratelloSuccessore whether the successor is his brother
	 */
	public void setFratelloSuccessore(boolean fratelloSuccessore) {
		this.fratelloSuccessore = fratelloSuccessore;
	}

	/**return whether the successor is adopted
	 * 
	 * @return whether the successor is adopted
	 */
	public boolean isAdopted() {
		return adopted;
	}

	/**set whether the successor is adopted
	 * 
	 * @param adopted whether the successor is adopted
	 */
	public void setAdopted(boolean adopted) {
		this.adopted = adopted;
	}

	/**get the mother
	 * 
	 * @return madre the mother
	 */
	public Persona getMadre() {
		return madre;
	}

	/**set the mother
	 * 
	 * @param madre the mother to set
	 */
	public void setMadre(Persona madre) {
		this.madre = madre;
	}
	/** return whether the emperor has a mother
	 * 
	 * @return boolean
	 */
	public boolean hasMother() {
		if (this.madre == null) {
			return false;
		}
		else {
			return true;
		}
	}
	/** return whether the emperor has wives
	 * 
	 * @return boolean
	 */
	public boolean hasMogli() {
		if (this.mogli == null) {
			return false;
		}
		else {
			return true;
		}
	}
	/** return whether the emperor has a wife who is also
	 * a mother of their son
	 * 
	 * @return boolean
	 */
	public boolean hasMotherWife() {
		if ( this.hasMogli() && getSuccessore().hasMother() && this.getMogli().contains(getSuccessore().getMadre())) {
			return true;
		}
		else { 
			return false;
		}
	}
	/** return whether the emperor has sons
	 * 
	 * @return boolean
	 */
	public boolean hasFigli() {
		if (this.figli == null) {
			return false;
		}
		else {
			return true;
		}
	}

	/**get the date of birth and death
	 * @return the date of birth and death
	 */
	public String getNascitaMorte() {
		return nascitaMorte;
	}

	/**set the date of birth and death
	 * @param nascitaMorte the date of birth and death
	 */
	public void setNascitaMorte(String birthDeath) {
		birthDeath = birthDeath.replaceAll("a.C.", "");
		birthDeath = birthDeath.replaceAll("d.C.", "");
		this.nascitaMorte = birthDeath;
	}

	/**get the link of the profile image 
	 * @return the link of the profile image 
	 */
	public String getImageLink() {
		return imageLink;
	}

	/**set the link of the profile image 
	 * @param imageLink link of the profile image
	 */
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
}
