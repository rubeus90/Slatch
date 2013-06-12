import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Ngoc
 *
 */
public class PanelStatistique extends JPanel{
	private JTextArea text1;
	private JTextArea text2;
	private JTextArea text3;
	private JTextArea text4;
	private ArrayList<JTextArea> list;
	
	public PanelStatistique(){
		this.setPreferredSize(new Dimension(800,500));
		this.setLayout(new GridLayout(1,4));
		
		list = new ArrayList<JTextArea>();
		for(int i=1; i<=Slatch.partie.getNbrJoueur(); i++){
			JTextArea text = new JTextArea("Coucouc");
			list.add(text);
			JPanel panel = new JPanel();
			panel.add(text);
			this.add(panel);
			panel.setOpaque(false);
		}
	
		for(int i=0; i< list.size(); i++){
			list.get(i).setOpaque(false);
			list.get(i).setForeground(Color.WHITE);
			list.get(i).setEditable(false);
			list.get(i).setFocusable(false);
			afficheStat(i+1);
		}
		
		this.repaint();
	}
	
	@Override
	public void paintComponent(final Graphics g){
		afficheImageRedim ("wallpaper", 0, 0, this.getWidth(), this.getHeight(), g);
		afficheImageRedim ("noir80", 0, this.getHeight()/5, this.getWidth(), this.getHeight()*4/5, g);
		
		Font font = Slatch.fonts.get("BlackOps").deriveFont(Font.PLAIN, this.getWidth()/20);
		FontMetrics fm = getFontMetrics(font);
		g.setFont(font);
		String joueur = "Le joueur "+ Slatch.partie.getJoueurActuel() +" a gagné";
		int longueur = fm.stringWidth(joueur);
		g.drawString(joueur, this.getWidth()/2 - longueur/2, this.getHeight()/10);
		
		Font font2 = Slatch.fonts.get("Visitor").deriveFont(Font.PLAIN, this.getWidth()/90);
		for(int i=0; i< list.size(); i++){
			list.get(i).setMargin(new Insets(this.getWidth()/6,0,0,0));
			list.get(i).setFont(font2);
		}
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
}
