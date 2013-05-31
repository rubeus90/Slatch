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
    int barreSize;

    /**
     * Constructor for objects of class IHM_Panel_Barre
     */
    public PanelInfo()
    {
        super();
        this.setPreferredSize(new Dimension(800, 70));
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
            // Efface le menu description
            Slatch.ihm.getPanel().setMenuUniteDescription(false);
            // Efface le menu shop
            Slatch.ihm.getPanel().setMenuShop(false);
            // Efface le menu
            Slatch.ihm.getPanel().setMenu(false);
            // Avertir Moteur
            Slatch.moteur.passeTour();
            this.repaint();
            Slatch.ihm.getPanel().repaint();
            
        }
        
        // Bouton Menu fonctionnement
        if(Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).estUneIA()==false && 0<pY && pY<this.getHeight() && 0<pX && pX<menuSize+2*espaceSize) 
        {
            Slatch.ihm.getPanel().setMenu(true);
            this.repaint();
            Slatch.ihm.getPanel().repaint();
        }
    }
    
    /**
     * Affiche la barre d'informations en haut de l'ecran
     */
    public void afficheBarreInfo (final Graphics g) {
        // Fond de panel
        afficheImageRedim("barreinfo",0, 0,this.getWidth(),this.getHeight(),g);
        
        String menu = "MENU";
        String jour = "JOUR : "+Slatch.partie.getTour();
        String joueur = "JOUEUR : "+Slatch.partie.getJoueurActuel();
        String argent = "ARGENT : "+Slatch.partie.getJoueur(Slatch.partie.getJoueurActuel()).getArgent()+"¤";
        String suivant = "SUIVANT";
        String espace = "  ";
        String barre = " |  ";
        
        // Police
        Font font = new Font("Helvetica", Font.BOLD, 8+25*this.getWidth()/1500);
        g.setFont(font);
        FontMetrics fm=getFontMetrics(font); 
        
        menuSize = fm.stringWidth(menu);
        jourSize = fm.stringWidth(jour);
        joueurSize = fm.stringWidth(joueur);
        argentSize = fm.stringWidth(argent);
        suivantSize = fm.stringWidth(suivant);
        espaceSize = fm.stringWidth(espace);
        barreSize = fm.stringWidth(barre);
        
        int decaleX=3;
        int decaleY=3;
        int Y=45;
        
        // Ombre
        g.setColor(Color.black);
        g.drawString(menu, espaceSize+decaleX, Y+decaleY);
        g.drawString(barre, espaceSize+menuSize+decaleX, Y+decaleY);
        g.drawString(jour, espaceSize+menuSize+barreSize+decaleX, Y+decaleY);
        g.drawString(barre, espaceSize+menuSize+barreSize+jourSize+decaleX, Y+decaleY);
        g.drawString(joueur, espaceSize+menuSize+barreSize+jourSize+barreSize+decaleX, Y+decaleY);
        g.drawString(barre, espaceSize+menuSize+barreSize+jourSize+barreSize+joueurSize+decaleX, Y+decaleY);
        g.drawString(argent, espaceSize+menuSize+barreSize+jourSize+barreSize+joueurSize+barreSize+decaleX, Y+decaleY);
        g.drawString(suivant, this.getWidth()-suivantSize-espaceSize+decaleX, Y+decaleY);
        
        g.setColor(Color.white);
        g.drawString(menu, espaceSize, Y);
        g.drawString(barre, espaceSize+menuSize, Y);
        g.drawString(jour, espaceSize+menuSize+barreSize, Y);
        g.drawString(barre, espaceSize+menuSize+barreSize+jourSize,Y);
        g.drawString(joueur, espaceSize+menuSize+barreSize+jourSize+barreSize, Y);
        g.drawString(barre, espaceSize+menuSize+barreSize+jourSize+barreSize+joueurSize, Y);
        g.drawString(argent, espaceSize+menuSize+barreSize+jourSize+barreSize+joueurSize+barreSize, Y);
        g.drawString(suivant, this.getWidth()-suivantSize-espaceSize, Y);
    }
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {     
        Image img = Slatch.aImages.get(pURL);
        g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
    } 
}
