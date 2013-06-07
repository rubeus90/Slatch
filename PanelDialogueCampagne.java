import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JPanel;

/**
 * @author Ngoc
 * 
 */
public class PanelDialogueCampagne extends JPanel {

	private String dialogue;
	private int etape;
	private int niveau;
	private Thread t;

	public PanelDialogueCampagne(final int pNiveau) {
		super();
		etape = 1;
		niveau = pNiveau;
		dialogue = "Coucoucoucou on va commencer le niveau " + (pNiveau + 1);
	}
	
	public PanelDialogueCampagne(){
		super();
		dialogue = "Voila vous avez fini la campagne, maintenant allez jouer dehors!";
	}

	@Override
	public void paintComponent(final Graphics g) {
		afficheImageRedim("noir", 0, 0, this.getWidth(), this.getHeight(), g);

		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(getClass()
					.getClassLoader().getResource("Config/BlackOps.ttf")
					.toURI())).deriveFont(Font.PLAIN, 20f);
			g.setFont(font);
			g.setColor(Color.white);
//			FontMetrics fm = getFontMetrics(font);
//			int hauteurSize = fm.getHeight();
//			int longueur = fm.stringWidth(dialogue);
			g.drawString(dialogue, this.getWidth() / 12, this.getHeight() / 2);
		} catch (FontFormatException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		// Font font = new Font("Serif", Font.BOLD, this.getWidth()/75);

	}

	private void afficheImageRedim(final String pURL,
			final int pPosHautGaucheX, final int pPosHautGaucheY,
			final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {
		Image img = Slatch.aImages.get(pURL);
		g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX
				- pPosHautGaucheX, pPosBasDroiteY - pPosHautGaucheY,
				Slatch.ihm.getPanel());
	}
}
