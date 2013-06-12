import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Ngoc
 *
 */
public class PanelStatistique extends JPanel{
	
	public PanelStatistique(){
		this.setPreferredSize(new Dimension(800,500));
		this.setLayout(new GridLayout(4,1));
		JTextArea text1 = new JTextArea("Hey I just met you");
		JTextArea text2 = new JTextArea("And this is crazy");
		JTextArea text3 = new JTextArea("But here's my number");
		JTextArea text = new JTextArea("So call me maybe");
		
	}
}
