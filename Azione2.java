package scrap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This ActionListener will reset the frame allowing the user to choose a 
 * new dinasty to be dispayed
 * 
 * @author Andrea, Edoardo, Federico
 *
 */

public class Azione2 implements ActionListener {
	private JFrame frame;
	
	/**
	 * Constructor of the method
	 * 
	 * @param f the frame
	 */
	public Azione2(JFrame f) {
		frame=f;
	}
	

	public void actionPerformed(ActionEvent event) {
		frame.dispose();
		new Frame();
	}
}
