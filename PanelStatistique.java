import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Ngoc
 *
 */
public class PanelStatistique extends JPanel implements MouseListener{
    private ArrayList<JTextArea> list;
    private JTextArea gagnant;
    private JPanel panelHaut;
    private JPanel panelBas;
    
    public PanelStatistique(){
        this.setPreferredSize(new Dimension(800,500));
        this.setLayout(new BorderLayout());
        
        panelHaut = new JPanel();
        panelHaut.setLayout(new BorderLayout());
        panelHaut.setOpaque(false);
        panelBas = new JPanel();
        panelBas.setLayout(new GridLayout(1,4));
        panelBas.setOpaque(false);
        
        gagnant = new JTextArea();
        gagnant.setOpaque(false);
        gagnant.setForeground(Color.WHITE);
        gagnant.setEditable(false);
        gagnant.setFocusable(false);
        gagnant.addMouseListener(this);
        
        panelHaut.add(gagnant, BorderLayout.CENTER);        
        
        list = new ArrayList<JTextArea>();
        for(int i=1; i<=Slatch.partie.getNbrJoueur(); i++){
            JTextArea text = new JTextArea();
            list.add(text);
            JPanel panel = new JPanel();
            panel.add(text);
            panelBas.add(panel);
            panel.setOpaque(false);
        }
    
        for(int i=0; i< list.size(); i++){
            list.get(i).setOpaque(false);
            list.get(i).setForeground(Color.WHITE);
            list.get(i).setEditable(false);
            list.get(i).setFocusable(false);
            list.get(i).addMouseListener(this);
            afficheStat(i+1);
        }
        
        this.add(panelHaut, BorderLayout.NORTH);
        this.add(panelBas, BorderLayout.CENTER);
        
        this.addMouseListener(this);
        
        this.repaint();
    }
    
    @Override
    public void paintComponent(final Graphics g){
        afficheImageRedim ("wallpaper", 0, 0, this.getWidth(), this.getHeight(), g);
        afficheImageRedim ("noir80", 0, 0, this.getWidth(), this.getHeight(), g);
        
        Font font2 = Slatch.fonts.get("Visitor").deriveFont(Font.PLAIN, this.getWidth()/90);
        for(int i=0; i< list.size(); i++){
            list.get(i).setMargin(new Insets(40,0,0,0));
            list.get(i).setFont(font2);
        }
        
        afficheGagnant();
        
        this.updateUI();
    }
    
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {  
        Image img = Slatch.aImages.get(pURL);
        g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
    }
    
    public void afficheStat(int i){
        String stat = "Joueur " + i + "\n" + "\n"
                    + "Score : " + Slatch.partie.ListeJoueur.get(i).getScore() + "\n"
                    + "Argent recolte : " + Slatch.partie.ListeJoueur.get(i).getArgentTotal()+"¤" + "\n"
                    + "Argent depense : " + Slatch.partie.ListeJoueur.get(i).getArgentDepense()+"¤" + "\n"
                    + "Batiments Captures: " + Slatch.partie.ListeJoueur.get(i).getCaptureTotal() + "\n"
                    + "Unites Tuees : " + Slatch.partie.ListeJoueur.get(i).getNbrUniteTue() + "\n"
                    + "Unites creees : " + Slatch.partie.ListeJoueur.get(i).getNbrUniteCree() + "\n"
                    + "Unites perdues : " + Slatch.partie.ListeJoueur.get(i).getNbrUniteMort() + "\n"
                    + "Degats infliges : " + Slatch.partie.ListeJoueur.get(i).getDegatTotal() + "\n"
                    + "Soin donne : " + Slatch.partie.ListeJoueur.get(i).getSoinTotal() + "\n"
                    + "Degats subis : " + Slatch.partie.ListeJoueur.get(i).getDegatSubit() + "\n"
                    + "Experience Totale : " + Slatch.partie.ListeJoueur.get(i).getExpTotal() + "\n"
                    + "Deplacement Total : " + Slatch.partie.ListeJoueur.get(i).getDeplacementTotal();
        list.get(i-1).setText(stat);
    }
    
    public void afficheGagnant(){
        Font font = Slatch.fonts.get("BlackOps").deriveFont(Font.PLAIN, this.getWidth()/20);
        FontMetrics fm = getFontMetrics(font);
        gagnant.setFont(font);
		String string = "";
		
		ArrayList<Joueur> equipe = Slatch.partie.getJoueurGagnant().getEquipe().getListeJoueur();
		
		for(Joueur joueur : equipe){
			string += "Le joueur " + joueur.getNumJoueur() + " a gagné" + "\n";
		}
		gagnant.setText(string);
		
		gagnant.setMargin(new Insets(0,this.getWidth()/2 - 19*fm.stringWidth(" "),0,0));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Si la partie est finie
        if(Slatch.partie.partieFinie && Slatch.partie.isCampagne()) {
            Slatch.campagne.suite();
	    }
    }    

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}

