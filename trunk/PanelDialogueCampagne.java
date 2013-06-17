import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * @author Ngoc
 * 
 */
public class PanelDialogueCampagne extends JPanel {

	private String dialogue;
	private JTextArea textArea = new JTextArea();
	private boolean dialogueFinished;
	private Scanner scanner;
	private String interlocuteur;
	private String background;
	private String barredialogue;
	private int etape;
	

	public PanelDialogueCampagne(final int pNiveau) {
		super();
		try {
			scanner = new Scanner(getClass().getClassLoader()
					.getResource("DialoguesCampagne/niveau" + (pNiveau+1))
					.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		etape = 0;
	}

	public PanelDialogueCampagne() {
		super();
		dialogue = "Voila vous avez fini la campagne, maintenant allez jouer dehors!";
	}

	@Override
	public void paintComponent(final Graphics g) {
	    //afficheImageRedim(barredialogue, 0, 0, this.getWidth(), this.getHeight(), g);
		afficheImageRedim(background, 0, 0, this.getWidth(), this.getHeight(), g);
		afficheImageRedim(interlocuteur, 0, 0, this.getWidth(), this.getHeight(), g);
	}

	private void afficheImageRedim(final String pURL,
			final int pPosHautGaucheX, final int pPosHautGaucheY,
			final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {
		Image img = Slatch.aImages.get(pURL);
		g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX
				- pPosHautGaucheX, pPosBasDroiteY - pPosHautGaucheY,
				Slatch.ihm.getPanel());
	}

	public void afficheText() {
		this.setLayout(new BorderLayout());
		
		textArea.setPreferredSize(new Dimension(800,200));
		this.add(textArea, BorderLayout.SOUTH);
		Font font;
		try {
			font = Font.createFont(
					Font.TRUETYPE_FONT,
					new File(getClass().getClassLoader()
							.getResource("Config/BlackOps.ttf").toURI()))
					.deriveFont(Font.PLAIN, 20f);
			textArea.setFont(font);
		} catch (FontFormatException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		textArea.setForeground(Color.WHITE);
		textArea.setText(dialogue);
		textArea.setMargin(new Insets(40, 40, 40, 40));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
		textArea.setFocusable(false);
		textArea.setEditable(false);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void etapeDialogue() {
		String tab[] = null;
		
		if(etape == 0){
			background = scanner.nextLine();
			etape = 1;
		}
		
		if (scanner.hasNextLine()) {
			dialogueFinished = false;
			String texte = scanner.nextLine();
			tab = texte.split(":");
			interlocuteur = tab[0];
			dialogue = interlocuteur + " : ";
			dialogue += tab[1];
			afficheText();
		} else
			dialogueFinished = true;
	}

	public boolean getDialogueFinished() {
		return dialogueFinished;
	}
}
