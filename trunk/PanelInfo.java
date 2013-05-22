import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
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


/**
 * Write a description of class IHM_Panel_Barre here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PanelInfo extends JPanel
{
    /**
     * Constructor for objects of class IHM_Panel_Barre
     */
    public PanelInfo()
    {
        super();
        this.setPreferredSize(new Dimension(800, 50));
    }
    
    /**
     * Affiche le decor (appelé lors des repaint : a eviter)
     */
    @Override
    public void paintComponent (final Graphics g) 
    {
        /*g.setColor(Color.blue);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());*/
        afficheBarreInfo(g);
    } 

    
    /**
     * Methode appelee lors d'un click
     */
    public void coordclickUnite (int pX, int pY) 
    {
        // Bouton SUIVANT fonctionnement
        if(0<pY && pY<this.getHeight() && this.getWidth()-6*this.getWidth()/32<pX && pX<this.getWidth()) 
        {
            // Efface le petit menu 
            Slatch.ihm.getPanel().setMenuUniteAction(false);
            // Efface le menu
            Slatch.ihm.getPanel().setMenuUniteDescription(false);
            // Avertir Moteur
            Slatch.moteur.passeTour();
            this.repaint();
            Slatch.ihm.getPanel().repaint();
            
            /*
            // Joueur Actuel affichage
            afficheImageRedim ("joueur.png", 18*aLargeurCarreau, 0,19*aLargeurCarreau, DECALAGE_PX_EN_Y, g);
            for(int i=0; i<10;i++) {
                if(Slatch.partie.getJoueurActuel()%10==i) {
                    afficheImageRedim ("numero"+i+".png", 19*aLargeurCarreau, DECALAGE_PX_EN_Y/4,20*aLargeurCarreau, 3*DECALAGE_PX_EN_Y/4, g);
                }
            }
            
            // Jour Actuel affichage
            afficheImageRedim ("jour.png", 3*aLargeurCarreau, 0,7*aLargeurCarreau, DECALAGE_PX_EN_Y, g);
            afficheImageRedim ("menu.png", 0, 0,4*aLargeurCarreau, DECALAGE_PX_EN_Y, g);
            // Affiche les dizaines
            for(int i=0; i<10;i++) {
                if(Slatch.partie.getTour()%10==i) {
                    afficheImageRedim ("numero"+i+".png", 8*aLargeurCarreau, DECALAGE_PX_EN_Y/4,9*aLargeurCarreau, 3*DECALAGE_PX_EN_Y/4, g);
                }
            }
            // Affiche les unites
            for(int i=0; i<10;i++) {
                if(Slatch.partie.getTour()/10%10==i) {
                    afficheImageRedim ("numero"+i+".png", 7*aLargeurCarreau,DECALAGE_PX_EN_Y/4,8*aLargeurCarreau, 3*DECALAGE_PX_EN_Y/4, g);
                }
            }
            
            
            // Argent du joueur actuel affichage
            afficheImageRedim ("argent.png", 9*aLargeurCarreau, 0,13*aLargeurCarreau, DECALAGE_PX_EN_Y, g);
            // Affiche les unites
            for(int u=0; u<10;u++) {
                if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()%10==u) {
                    afficheImageRedim ("numero"+u+".png", 16*aLargeurCarreau, DECALAGE_PX_EN_Y/4,17*aLargeurCarreau, 3*DECALAGE_PX_EN_Y/4, g);
                }
            }
            // Affiche les dizaines
            for(int v=0; v<10;v++) {
                if((Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()/10)%10==v) {
                    afficheImageRedim ("numero"+v+".png", 15*aLargeurCarreau,DECALAGE_PX_EN_Y/4,16*aLargeurCarreau, 3*DECALAGE_PX_EN_Y/4, g);
                }
            }
            // Affiche les centaines
            for(int v=0; v<10;v++) {
                if((Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()/100)%10==v) {
                    afficheImageRedim ("numero"+v+".png", 14*aLargeurCarreau,DECALAGE_PX_EN_Y/4,15*aLargeurCarreau, 3*DECALAGE_PX_EN_Y/4, g);
                }
            }
            // Affiche les milliemes
            for(int v=0; v<10;v++) {
                if((Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()/1000)%10==v) {
                    afficheImageRedim ("numero"+v+".png", 13*aLargeurCarreau,DECALAGE_PX_EN_Y/4,14*aLargeurCarreau, 3*DECALAGE_PX_EN_Y/4, g);
                }
            }*/
        }
    }
    
    /**
     * Affiche la barre d'informations en haut de l'ecran
     */
    public void afficheBarreInfo (final Graphics g) {
        afficheImageRedim("4",0, 0,this.getWidth(),this.getHeight(),g);

        
        afficheImageRedim ("jour", 3*this.getWidth()/32, this.getHeight()/4,7*this.getWidth()/32, this.getHeight(), g);
        afficheImageRedim ("numero"+Slatch.partie.getTour()%10, 8*this.getWidth()/32, this.getHeight()/4,9*this.getWidth()/32, 3*this.getHeight()/4, g);
        afficheImageRedim ("numero"+(Slatch.partie.getTour()/10)%10, 7*this.getWidth()/32, this.getHeight()/4,8*this.getWidth()/32, 3*this.getHeight()/4, g);
        
        // Argent de base du primier joueur
        afficheImageRedim ("argent", 9*this.getWidth()/32, 0,13*this.getWidth()/32, this.getHeight(), g);
        
        // Affiche les unites
        afficheImageRedim ("numero"+(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent())%10, 16*this.getWidth()/32, this.getHeight()/4,17*this.getWidth()/32, 3*this.getHeight()/4, g);
        // Affiche les dizaines
        afficheImageRedim ("numero"+(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()/10)%10, 15*this.getWidth()/32,this.getHeight()/4,16*this.getWidth()/32, 3*this.getHeight()/4, g);
        // Affiche les centaines
        afficheImageRedim ("numero"+(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()/100)%10, 14*this.getWidth()/32,this.getHeight()/4,15*this.getWidth()/32, 3*this.getHeight()/4, g);
        // Affiche les milliemes
        afficheImageRedim ("numero"+(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()/1000)%10, 13*this.getWidth()/32,this.getHeight()/4,14*this.getWidth()/32, 3*this.getHeight()/4, g);
        
        // Bouton SUIVANT
        afficheImageRedim ("suivant", this.getWidth()-6*this.getWidth()/32, 0,this.getWidth(), this.getHeight(), g);
        
        // Bouton MENU
        afficheImageRedim ("menu", 0, 0,4*this.getWidth()/32, this.getHeight(), g);
        
        // Joueur Actuel de base
        afficheImageRedim ("joueur", 18*this.getWidth()/32, 0, 19*this.getWidth()/32, this.getHeight(), g);
        afficheImageRedim ("numero"+Slatch.partie.getJoueurActuel(), 19*this.getWidth()/32, this.getHeight()/4,20*this.getWidth()/32, 3*this.getHeight()/4, g);
        
        
        
        
    }
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {
                    
            Image img = Slatch.aImages.get(pURL);
            
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
        
    } 
}
