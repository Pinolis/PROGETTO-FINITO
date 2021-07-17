package scrap;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
/**
 * This ActionListener will activate when the user will choose from one of the dinasties and it will
 * pass the information to the scraper, it then will pass the data generated from the scraper to the 
 * class PannelloTela that is in fact a JPanel that will be showed on the frame and it will
 * contains the drawed family tree
 * 
 * @author Andrea, Edoardo, Federico
 *
 */

public class Azione1 implements ActionListener {
	/**
	 * 
	 */
	public static Dinastia dinastiaSel=null;
	/**
	 * .
	 */
	public static JScrollPane scrollable;
	/**
	 * .
	 */
	public static PannelloTela res;
	private JFrame frame;
	private JComboBox lista;
	private JPanel finestra;
	private JPanel remove;
	private JButton reset;
	private JLabel label;
	private JPanel pannello;
	private Color c;
	
	/**
	 * Constructor of the class
	 * @param fr the frame
	 * @param l the list of the combobox
	 * @param fi the main panel 
	 * @param rem the panel for the reset
	 * @param rese the button for the reset
	 * @param lab panel that contains text
	 * @param pan upper panel containing the combobox
	 * @param color the background color
	 */
	public Azione1(JFrame fr,JComboBox l,JPanel fi,JPanel rem,JButton rese,JLabel lab,JPanel pan,Color color) {
		frame=fr;
		lista=l;
		finestra=fi;
		remove=rem;
		reset=rese;
		label=lab;
		pannello=pan;
		c=color;
		
	}

	
	
	public void actionPerformed(ActionEvent event) {
		finestra.repaint();
		dinastiaSel = (Dinastia) lista.getSelectedItem();
		String selected1= String.valueOf(dinastiaSel);
		label.setText(selected1);
		
		res= new PannelloTela();
		scrollable = new JScrollPane(res); //pannello scrollable che contiene il pannello contenente l'albero
		res.removeAll();
		res.paintComponents(res.getGraphics());
		remove.setBackground(c);
		remove.setOpaque(true);
		remove.add(reset);
		frame.remove(pannello);
		finestra.add(remove,BorderLayout.NORTH);
		
		finestra.add(scrollable);
		finestra.repaint();
		dinastiaSel=null;
	}
}

