import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * @author Ngoc
 *
 */
public class PanelDialogueCampagne extends JPanel{
	
	private String dialogue;
	private int etape;
	private int niveau;
	
	public PanelDialogueCampagne(final int pNiveau){
		super();
		etape = 1;
		niveau = pNiveau;
		dialogue = "Coucoucoucou on va commencer le niveau " + pNiveau;
	}
	
	@Override
	public void paintComponent(final Graphics g){
		afficheImageRedim ("noir80", 0, 0, this.getWidth(), this.getHeight(), g);
		
		Font font = new Font("Serif", Font.BOLD, this.getWidth()/75);
        g.setFont(font);
        FontMetrics fm = getFontMetrics(font); 
        g.setColor(Color.white);
        
        int hauteurSize = fm.getHeight();
        int joueurSize = fm.stringWidth(dialogue);
        g.drawString(dialogue, this.getWidth()/2 - joueurSize/2, 2*this.getHeight()/(2*hauteurSize));
	}
	
	private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {  
		Image img = Slatch.aImages.get(pURL);
		g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
	}
}
