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


/**
 * Write a description of class IHM_Panel_Matrice here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PanelMatrice extends JPanel
{     

    private int DECALAGE_PX_EN_Y;
    
    private int aLargeurCarreau;
    private int aHauteurCarreau;
    
    private int aMenuHautGauche_Xpx;
    private int aMenuHautGauche_Ypx;
    private int aMenuBasDroite_Xpx;
    private int aMenuBasDroite_Ypx;
    private int aUniteMemCaseX;
    private int aUniteMemCaseY;
    
    private int aLargeurMenuEnCase=3;
    private int aHauteurMenuEnCase=6;
    
    private boolean modeMenuUnite;
    private boolean modeMenu;
    
    private boolean aAttaquePossible=false;
    private boolean aDeplacePossible=false;
    
    
    private List<String> aListeAction;

    /**
     * Constructor for objects of class IHM_Panel_Matrice
     */
    public PanelMatrice()
    {
        super();
        this.setPreferredSize(new Dimension(800, 500));
        this.setBackground(Color.black);
        
        aLargeurCarreau = this.getWidth()/Slatch.partie.getLargeur();
        aHauteurCarreau = this.getHeight()/ Slatch.partie.getHauteur();

        modeMenuUnite=false;
        modeMenu=false;
        
        aListeAction= new ArrayList<String>();
    }

    /**
     * Affiche le decor (appelé lors des repaint : a eviter)
     */
    @Override
    public void paintComponent (final Graphics g) 
    {
        aLargeurCarreau = this.getWidth()/Slatch.partie.getLargeur();
        aHauteurCarreau = this.getHeight()/ Slatch.partie.getHauteur();
        dessineMatrice(g);
        
        if(modeMenuUnite) {
            afficheImageRedim ("noir80", aMenuHautGauche_Xpx, aMenuHautGauche_Ypx, aMenuBasDroite_Xpx, aMenuBasDroite_Ypx, g);
            
            // Ecrie les boutons en rouge
            g.setColor(Color.gray);
            g.drawString("Deplace", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3);
            g.drawString("Attaque", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);
            g.drawString("Capture", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
            g.drawString("Evolue", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
            
            // Ecrie les boutons en vert
            for(int vVar=0;vVar<aListeAction.size();vVar++) {
                g.setColor(Color.green);
                if(aListeAction.get(vVar).equals("Deplace")) {
                    g.drawString("Deplace", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3);
                    aDeplacePossible=true;
                }
                if(aListeAction.get(vVar).equals("Attaque")) {
                    g.drawString("Attaque", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);
                    aAttaquePossible=true;
                }
                if(aListeAction.get(vVar).equals("Capture")) {
                    g.drawString("Capture", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                }
                if(aListeAction.get(vVar).equals("Evolue")) {
                    g.drawString("Evolue", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
                }
            }
            
            // Trace les lignes
            for(int vVar=1;vVar<4;vVar++) {
                g.setColor(Color.white);
                g.drawLine(aMenuHautGauche_Xpx, aMenuHautGauche_Ypx+(vVar*aHauteurMenuEnCase/aHauteurMenuEnCase)*aHauteurCarreau, aMenuBasDroite_Xpx-1, aMenuHautGauche_Ypx+(vVar*aHauteurMenuEnCase/aHauteurMenuEnCase)*aHauteurCarreau);
            }
            
        }
        
    }
    
    
    
    
    /**
     * Methode appelee lors d'un click
     */
    public void coordclickUnite (int pX, int pY) 
    {
        int clickX = pX;
        int clickY = pY; 

        if(modeMenu) {
            this.repaint();modeMenu=false;
        }

        for(int i = 0 ; i < Slatch.partie.getLargeur() ; i++) 
        {
            for(int j = 0 ; j < Slatch.partie.getHauteur() ; j++) 
            {
                // Position de la case selectionnee
                int pPosHautGaucheX = i*aLargeurCarreau;
                int pPosHautGaucheY = j*aHauteurCarreau;
                int pPosBasDroiteX = (i+1)*aLargeurCarreau;
                int pPosBasDroiteY = (j+1)*aHauteurCarreau;

                if(pPosHautGaucheY<clickY && clickY<pPosBasDroiteY && pPosHautGaucheX<clickX && clickX<pPosBasDroiteX) 
                {
                    if(modeMenuUnite)
                    {
                        if( aMenuHautGauche_Ypx<clickY && clickY<aMenuBasDroite_Ypx && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx )//Si tu es dans menu
                        {
                            //Action a differencier
                            
                            //Bouton 1 : Deplace
                            if(aDeplacePossible && (aMenuHautGauche_Ypx<clickY && clickY<(aMenuHautGauche_Ypx+aHauteurCarreau) && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx )) 
                            {
                                Slatch.moteur.modeDeplacement(aUniteMemCaseX, aUniteMemCaseY);
                                effaceMenuUnite();
                                aDeplacePossible=false;
                                this.repaint();
                            }
                            
                            //Bouton 2 : Attaque
                            if(aAttaquePossible && (aMenuHautGauche_Ypx+aHauteurCarreau)<clickY && clickY<(aMenuHautGauche_Ypx+2*aHauteurCarreau) && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx ) 
                            {
                                Slatch.moteur.modeAttaque(aUniteMemCaseX, aUniteMemCaseY);
                                effaceMenuUnite();
                                aAttaquePossible=false;
                                this.repaint();
                            }
                            
                        }
                        else {
                            // Avertir Moteur
                            Slatch.moteur.annulerDeplacement();
                            Slatch.moteur.caseSelectionnee(i,j);
                            effaceMenuUnite();
                        }
                    }
                    else
                    {
                        Slatch.moteur.caseSelectionnee(i,j);
                        this.repaint();
                    }
                }
            }
        }
        
    }
    
    /**
     * Affiche une matrice d'image sur la carte
     */
    private void dessineMatrice(final Graphics g) {
        //afficheImageRedim("2.png",0, 0,this.getWidth(),this.getHeight(),g);
        
        for(int i=0; i<Slatch.partie.getLargeur(); i++) {
            for(int j=0; j<Slatch.partie.getHauteur(); j++) {
                Slatch.partie.getTerrain()[i][j].dessine(g);
            }
        }
    }
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {
                   
            Image img = Slatch.aImages.get(pURL);
            
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
        
    }
    
    private void effaceMenuUnite()
    {
        aMenuHautGauche_Xpx=0;
        aMenuHautGauche_Ypx=0;
        aMenuBasDroite_Xpx=0;
        aMenuBasDroite_Ypx=0;
        modeMenuUnite = false;
        this.repaint();
    }
    
    /**
     * Affiche Test
     */
    public void afficheMenu(final List<String> pList, final int pX, final int pY) 
    {
        aUniteMemCaseX = pX;
        aUniteMemCaseY = pY;
        modeMenuUnite = true;
        if(pX+aLargeurMenuEnCase+1>Slatch.partie.getLargeur() && pY+aHauteurMenuEnCase+1>Slatch.partie.getHauteur()) 
        {
            //Dessine en haut à gauche
            aMenuHautGauche_Xpx = (pX-aLargeurMenuEnCase)*aLargeurCarreau;
            aMenuHautGauche_Ypx = (pY-aHauteurMenuEnCase)*aHauteurCarreau;
            aMenuBasDroite_Xpx = pX*aLargeurCarreau;
            aMenuBasDroite_Ypx = pY*aHauteurCarreau;
        }

        else if(pX+aLargeurMenuEnCase+1>Slatch.partie.getLargeur()) 
        {
            //Dessine en bas à gauche
            aMenuHautGauche_Xpx = (pX-aLargeurMenuEnCase)*aLargeurCarreau;
            aMenuHautGauche_Ypx = (pY+1)*aHauteurCarreau;
            aMenuBasDroite_Xpx = pX*aLargeurCarreau;
            aMenuBasDroite_Ypx = (pY+aHauteurMenuEnCase+1)*aHauteurCarreau;
        }
        
        else if(pY+aHauteurMenuEnCase+1>Slatch.partie.getHauteur()) {
            //Dessine en haut à droite
            aMenuHautGauche_Xpx = (pX+1)*aLargeurCarreau;
            aMenuHautGauche_Ypx = (pY-aHauteurMenuEnCase)*aHauteurCarreau;
            aMenuBasDroite_Xpx = (pX+aLargeurMenuEnCase+1)*aLargeurCarreau;
            aMenuBasDroite_Ypx = pY*aHauteurCarreau;
        }
        
        else {
            // Meun en bas a droite par default
            aMenuHautGauche_Xpx = (pX+1)*aLargeurCarreau;
            aMenuHautGauche_Ypx = (pY+1)*aHauteurCarreau;
            aMenuBasDroite_Xpx = (pX+aLargeurMenuEnCase+1)*aLargeurCarreau;
            aMenuBasDroite_Ypx = (pY+aHauteurMenuEnCase+1)*aHauteurCarreau;
        }
        aListeAction=pList;
    }
    
    public int getaLargeurCarreau() {return aLargeurCarreau;}
    public int getaHauteurCarreau() {return aHauteurCarreau;}
    public void setMenuUnite(final boolean X){modeMenuUnite=X;}
    public void setMenu(final boolean X){modeMenu=X;}
}
