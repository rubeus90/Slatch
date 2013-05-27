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
import java.awt.font.LineMetrics;
import java.awt.font.FontRenderContext;


/**
 * Panel de la barre info : affiche les inforamtions liees a la partie
 * 
 * @author jonathan
 * @version 1.0
 */
public class PanelInfo extends JPanel
{
    int menuSize;
    int jourSize;
    int joueurSize;
    int argentSize;
    int suivantSize;
    int espaceSize;

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
        afficheBarreInfo(g);
    } 

    /**
     * Methode appelee lors d'un click
     */
    public void coordclickUnite (int pX, int pY) 
    {
        // Bouton SUIVANT fonctionnement
        if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA()==false && 0<pY && pY<this.getHeight() && this.getWidth()-suivantSize-espaceSize<pX && pX<this.getWidth()) 
        {
            // Efface le petit menu 
            Slatch.ihm.getPanel().setMenuUniteAction(false);
            // Efface le menu
            Slatch.ihm.getPanel().setMenuUniteDescription(false);
            // Efface le menu shop
            Slatch.ihm.getPanel().setMenuShop(false);
            // Avertir Moteur
            Slatch.moteur.passeTour();
            this.repaint();
            Slatch.ihm.getPanel().repaint();
            
        }
    }
    
    /**
     * Affiche la barre d'informations en haut de l'ecran
     */
    public void afficheBarreInfo (final Graphics g) {
        // Fond de panel
        afficheImageRedim("4",0, 0,this.getWidth(),this.getHeight(),g);

        String menu = "MENU";
        String jour = "JOUR : "+Slatch.partie.getTour();
        String joueur = "JOUEUR : "+Slatch.partie.getJoueurActuel();
        String argent = "ARGENT : "+Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()+"¤";
        String suivant = "SUIVANT";
        String espace = "   ";
        
        // Police
        Font font = new Font("Serif", Font.BOLD, 25*this.getWidth()/1000);
        g.setFont(font);
        FontMetrics fm=getFontMetrics(font); 
        
        menuSize = fm.stringWidth(menu);
        jourSize = fm.stringWidth(jour);
        joueurSize = fm.stringWidth(joueur);
        argentSize = fm.stringWidth(argent);
        suivantSize = fm.stringWidth(suivant);
        espaceSize = fm.stringWidth(espace);
        
        g.setColor(Color.white);
        g.drawString(menu, espaceSize, 33);
        g.drawString(jour, espaceSize+menuSize+espaceSize, 33);
        g.drawString(joueur, espaceSize+menuSize+espaceSize+jourSize+espaceSize, 33);
        g.drawString(argent, espaceSize+menuSize+espaceSize+jourSize+espaceSize+joueurSize+espaceSize, 33);
        g.drawString(suivant, this.getWidth()-suivantSize-espaceSize, 33);
    }
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {     
        Image img = Slatch.aImages.get(pURL);
        g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
    } 
}
