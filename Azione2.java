
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Azione2 implements ActionListener {
	private JFrame frame;
	
	public Azione2(JFrame f) {
		frame=f;
	}
	
	
	public void actionPerformed(ActionEvent event) {
		frame.dispose();
		new Frame();
	}
}
