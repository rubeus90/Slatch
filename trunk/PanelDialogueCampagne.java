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
    private String interlocuteur1 = null;
    private String interlocuteur2 = null;
    private String background;
    private int etape;
    private boolean fini = false;
    

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

    @Override
    public void paintComponent(final Graphics g) {
    	if(!fini){
    		afficheImageRedim(background, 0, 0, this.getWidth(), 3*this.getHeight()/4, g);
            
            if(interlocuteur1 != " ")
                g.drawImage(Slatch.aImages.get(interlocuteur1),0,0,3*this.getHeight()/4,3*this.getHeight()/4,this);
            if(interlocuteur2 != " ")
                g.drawImage(Slatch.aImages.get(interlocuteur2),this.getWidth()/2
                ,0,3*this.getHeight()/4,3*this.getHeight()/4,this);
            
            afficheImageRedim("barredialogue", 0, this.getHeight()-this.getHeight()/4, this.getWidth(), this.getHeight(), g);
            afficheImageRedim("skip", this.getWidth()/80, this.getHeight()/50, this.getWidth()/8, this.getHeight()/10, g);
            this.afficheText();
    	}
    	else{
    		afficheImageRedim("wallpaper", 0, 0, this.getWidth(), this.getHeight(), g);
    		afficheImageRedim("fin", 0, 0, this.getWidth(), this.getHeight(), g);
    		this.remove(textArea);
    	}
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
        textArea.setMargin(new Insets(3*this.getHeight()/4+this.getHeight()/40, 40, this.getWidth()/100, this.getWidth()/100));
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
        
        String personnes[] = null;
        
        if (scanner.hasNextLine()) {
            dialogueFinished = false;
            String texte = scanner.nextLine();
            tab = texte.split(":");
            
            personnes = tab[0].split(";");
            interlocuteur1 = personnes[0];
            interlocuteur2 = personnes[1];
            
            dialogue = interlocuteur1 + " : ";
            dialogue += tab[1];
            afficheText();
        } else
            dialogueFinished = true;
    }

    public boolean getDialogueFinished() {
        return dialogueFinished;
    }
    
    public void setFini(){
    	fini = true;
    }
}
