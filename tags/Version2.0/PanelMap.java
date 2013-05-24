import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.* ;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.*;

public class PanelMap extends JPanel{
	 private int aLargeurCarreau;
	 private int aHauteurCarreau;
	 private Partie aPartie;
	 
	 public PanelMap(Partie pPartie){
		 super();
	     this.setPreferredSize(new Dimension(800, 500));
	     this.setBackground(Color.black);
	        
	     aLargeurCarreau = this.getWidth()/pPartie.getLargeur();
	     aHauteurCarreau = this.getHeight()/ pPartie.getHauteur();
	     aPartie = pPartie;
	 }
	 
	 
	 @Override
	 public void paintComponent (final Graphics g) 
	 {
	     aLargeurCarreau = this.getWidth() / aPartie.getLargeur();
	     aHauteurCarreau = this.getHeight() / aPartie.getHauteur();
	     dessineMatrice(g);	        
	 }
	 
	 public void dessineMatrice(final Graphics g) {
	        
	     for(int i=0; i< aPartie.getLargeur(); i++) {
	         for(int j=0; j< aPartie.getHauteur(); j++) {
	                
	         	int pPosHautGaucheX = i*aLargeurCarreau;

	            int pPosHautGaucheY = j*aHauteurCarreau;

	            int pPosBasDroiteX = (i+1)*aLargeurCarreau;

	            int pPosBasDroiteY = (j+1)*aHauteurCarreau;
	                
	            Image img = Slatch.aImages.get(""+ aPartie.getTerrain()[i][j].getType().getImage() + aPartie.getTerrain()[i][j].getJoueur());
	            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, this);
	         }
	     }
	 }
}
