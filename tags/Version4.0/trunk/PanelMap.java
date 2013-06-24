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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class PanelMap extends JPanel {
     private int aLargeurCarreau;
     private int aHauteurCarreau;
     private Partie aPartie;
     
     public PanelMap(Partie pPartie){
         super();
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
                Image img;
                int pPosBasDroiteY = (j+1)*aHauteurCarreau;
                if(aPartie.getTerrain()[i][j].getType().getDependance()){
                    img = Slatch.aImages.get("TERRE"+ aPartie.getTerrain()[i][j].getType().getImage() + aPartie.getTerrain()[i][j].getJoueur());
                }
                else{
                    img = Slatch.aImages.get(""+ aPartie.getTerrain()[i][j].getType().getImage() + aPartie.getTerrain()[i][j].getJoueur());
                }   
                g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, this);
             }
         }
     }
     
     public Point coordclickUnite (int pX, int pY) 
     {
         int clickX = pX;
         int clickY = pY; 
            
         Point point = new Point();

         for(int i = 0 ; i < aPartie.getLargeur() ; i++) 
         {
             for(int j = 0 ; j < aPartie.getHauteur() ; j++) 
             {
                 // Position de la case selectionnee
                 int pPosHautGaucheX = i*aLargeurCarreau;
                 int pPosHautGaucheY = j*aHauteurCarreau;
                 int pPosBasDroiteX = (i+1)*aLargeurCarreau;
                 int pPosBasDroiteY = (j+1)*aHauteurCarreau;
                    
                 if(pPosHautGaucheY<=clickY && clickY<pPosBasDroiteY && pPosHautGaucheX<=clickX && clickX<pPosBasDroiteX) 
                 {
                    point.setLocation(i, j);
                 }
             }
         }
         return point;
     }
     
     public Partie getPartie(){
         return aPartie;
     }
     
     public int getLargeurCarreau(){
         return aLargeurCarreau;
     }
     
     public int getHauteurCarreau(){
         return aHauteurCarreau;
     }
}