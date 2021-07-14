
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class Azione1 implements ActionListener {
	public static Dinastia dinastiaSel=null;
	public static JScrollPane scrollable;
	public static PannelloTela res;
	private JFrame frame;
	private JComboBox lista;
	private JPanel finestra;
	private JPanel remove;
	private JButton reset;
	private JLabel label;
	private JPanel pannello;
	private Color c;
	
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

