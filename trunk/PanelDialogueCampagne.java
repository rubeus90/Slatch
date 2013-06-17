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
        afficheImageRedim(background, 0, 0, this.getWidth(), 3*this.getHeight()/4, g);
        g.drawImage(Slatch.aImages.get(interlocuteur),0,0,3*this.getHeight()/4,3*this.getHeight()/4,this);
        afficheImageRedim("barredialogue", 0, this.getHeight()-this.getHeight()/4, this.getWidth(), this.getHeight(), g);
        this.afficheText();
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
        
        textArea.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/4));
        this.add(textArea, BorderLayout.CENTER);
        Font font;
        try {
            font = Font.createFont(
                    Font.TRUETYPE_FONT,
                    new File(getClass().getClassLoader()
                            .getResource("Config/BlackOps.ttf").toURI()))
                    .deriveFont(Font.PLAIN, 10+this.getWidth()/80);
            textArea.setFont(font);
        } catch (FontFormatException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        
        textArea.setForeground(Color.WHITE);
        textArea.setText(dialogue);
        textArea.setMargin(new Insets(3*this.getHeight()/4+20, 40, this.getWidth()/100, this.getWidth()/100));
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
