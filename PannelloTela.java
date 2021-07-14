package scrap;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/** The PannelloTela class create the panel that will contain the drawing of the genealogical tree
 * for the current Dinastia
 * 
 * @author Edoardo, Federico, Andrea
 *
 */
public class PannelloTela extends JPanel{
	private static final long serialVersionUID = 1L;
	private Painter painter=null;
	
/**
 * The constructor build a JPanel that contains the Root of the dinastia and 
 * the number of generations in the Dinastia.
 * It also create a Painter Object that will be use to draw.
 * @param root, the first Emperor 
 * @param generazione, the number of generations
 */
	public PannelloTela() {
		
		Dinastia dinastia=Azione1.dinastiaSel;
		if (dinastia !=null) {
			Imperatore root = ScraperUser.getAlberoGenealogico(dinastia);
			int generazioni=ScraperUser.contaGenerazioni(root);
			Painter paint= new Painter(root, generazioni);
			painter=paint;
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setPreferredSize(new Dimension(painter.getWidth(), painter.getLength()));
		}
		else {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setPreferredSize(new Dimension(0,0));
		}
	}

	/**
	 * This method overrides the JPanel method and draws the image calling 
	 * the method ricorsiva of the object Painter
	 * 
	 * @param g, a graphic component
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        // Draw 
        if (painter !=null) {
        	painter.ricorsiva(g, painter.getRadice(), painter.getWidth(), 1, true);
        }
    }
}
