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
    
    private int aMenuActionHautGauche_Xpx;
    private int aMenuActionHautGauche_Ypx;
    private int aMenuActionBasDroite_Xpx;
    private int aMenuActionBasDroite_Ypx;
    
    private int aMenuDescriptionHautGauche_Xpx;
    private int aMenuDescriptionHautGauche_Ypx;
    private int aMenuDescriptionBasDroite_Xpx;
    private int aMenuDescriptionBasDroite_Ypx;
    
    private int aUniteMemMoteurCaseX;
    private int aUniteMemMoteurCaseY;
    
    private int aUniteMemMenuCaseX;
    private int aUniteMemMenuCaseY;
    
    private int aLargeurMenuActionEnCase=3;
    private int aHauteurMenuActionEnCase=6;
    
    private int aLargeurMenuDescriptionEnCase=10;
    private int aHauteurMenuDescriptionEnCase=6;
    
    private boolean menuUniteAction;
    private boolean menuUniteDescription;
    private boolean modeMenu;
    
    private boolean aAttaquePossible=false;
    private boolean aDeplacePossible=false;
    private boolean aCapturePossible=false;
    
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

        menuUniteAction=false;
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
        
        if(menuUniteAction) {
            afficheImageRedim ("noir80", aMenuActionHautGauche_Xpx, aMenuActionHautGauche_Ypx, aMenuActionBasDroite_Xpx, aMenuActionBasDroite_Ypx, g);
            
            // Ecrie les boutons en rouge
            g.setColor(Color.gray);
            g.drawString("Deplace", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3);
            g.drawString("Attaque", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);
            g.drawString("Capture", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
            g.drawString("Evolue", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
            
            // Ecrie les boutons en vert
            for(int vVar=0;vVar<aListeAction.size();vVar++) {
                g.setColor(Color.green);
                if(aListeAction.get(vVar).equals("Deplace")) {
                    g.drawString("Deplace", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3);
                    aDeplacePossible=true;
                }
                if(aListeAction.get(vVar).equals("Attaque")) {
                    g.drawString("Attaque", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);
                    aAttaquePossible=true;
                }
                if(aListeAction.get(vVar).equals("Capture")) {
                    g.drawString("Capture", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                    aCapturePossible=true;
                }
                if(aListeAction.get(vVar).equals("Evolue")) {
                    g.drawString("Evolue", aMenuActionHautGauche_Xpx+aLargeurCarreau/3, aMenuActionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
                }
            }
            
            // Trace les lignes
            for(int vVar=1;vVar<4;vVar++) {
                g.setColor(Color.white);
                g.drawLine(aMenuActionHautGauche_Xpx, aMenuActionHautGauche_Ypx+(vVar*aHauteurMenuActionEnCase/aHauteurMenuActionEnCase)*aHauteurCarreau, aMenuActionBasDroite_Xpx-1, aMenuActionHautGauche_Ypx+(vVar*aHauteurMenuActionEnCase/aHauteurMenuActionEnCase)*aHauteurCarreau);
            }
        }
        
        if(menuUniteDescription) {
            afficheImageRedim ("noir80", aMenuDescriptionHautGauche_Xpx, aMenuDescriptionHautGauche_Ypx, aMenuDescriptionBasDroite_Xpx, aMenuDescriptionBasDroite_Ypx, g);
            g.setColor(Color.white);
            Terrain t = Slatch.partie.getTerrain()[aUniteMemMenuCaseX][aUniteMemMenuCaseY];
            if(t.getUnite()!=null)
            {
                String portedep = "Portée Depl = "+t.getUnite().getPorteeDeplacement();
                String xp = "Experience = "+t.getUnite().getExperience();
                String lvl = "LVL = "+t.getUnite().getLvl();

                g.drawString("INFORMATION UNITE:", aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*0);
                g.drawString(portedep, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*1);
                g.drawString(xp, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
                g.drawString(lvl, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
            }
            else {
                String nomterrain = "NomTerrain = "+t.getType().getNom();
                String descterrain = "DescTerrain = "+t.getType().getDescription();
                String couverture = "Couv = "+ t.getType().getCouverture();
                
                g.drawString("INFORMATION TERRAIN:", aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*4);
                g.drawString(nomterrain, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*5);
                g.drawString(descterrain, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*6);
                g.drawString(couverture, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*7);
            
                if(t.getType().getDependance()) {
                    String pv = "PVTerrain = "+t.getPointDeVie();
                    g.drawString(pv, aMenuDescriptionHautGauche_Xpx+aLargeurCarreau/3, aMenuDescriptionHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*8);
                }
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
                    if(menuUniteAction)
                    {
                        if( aMenuActionHautGauche_Ypx<clickY && clickY<aMenuActionBasDroite_Ypx && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx )//Si tu es dans menu
                        {
                            //Action a differencier
                            
                            //Bouton 1 : Deplace
                            if(aDeplacePossible && (aMenuActionHautGauche_Ypx<clickY && clickY<(aMenuActionHautGauche_Ypx+aHauteurCarreau) && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx )) 
                            {
                                Slatch.moteur.modeDeplacement(aUniteMemMoteurCaseX, aUniteMemMoteurCaseY);
                                effaceMenuUniteAction();
                                effaceMenuUniteDescription();
                                aDeplacePossible=false;
                                this.repaint();
                            }
                            
                            //Bouton 2 : Attaque
                            if(aAttaquePossible && (aMenuActionHautGauche_Ypx+aHauteurCarreau)<clickY && clickY<(aMenuActionHautGauche_Ypx+2*aHauteurCarreau) && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx ) 
                            {
                                Slatch.moteur.modeAttaque(aUniteMemMoteurCaseX, aUniteMemMoteurCaseY);
                                effaceMenuUniteAction();
                                effaceMenuUniteDescription();
                                aAttaquePossible=false;
                                this.repaint();
                            }
                            
                            //Bouton 3 : Capture
                            if(aCapturePossible && (aMenuActionHautGauche_Ypx+2*aHauteurCarreau)<clickY && clickY<(aMenuActionHautGauche_Ypx+3*aHauteurCarreau) && aMenuActionHautGauche_Xpx<clickX && clickX<aMenuActionBasDroite_Xpx )
                            {
                                Slatch.moteur.capture(aUniteMemMoteurCaseX, aUniteMemMoteurCaseY);
                                effaceMenuUniteAction();
                                effaceMenuUniteDescription();
                                aCapturePossible=false;
                            }
                            
                        }
                        else {
                            aUniteMemMenuCaseX=i;
                            aUniteMemMenuCaseY=j;
                            
                            // Avertir Moteur
                            effaceMenuUniteAction();
                            effaceMenuUniteDescription();
                            menuUniteDescription = true;
                            afficheMenuDescription(i, j);
                            Slatch.moteur.caseSelectionnee(i,j);
                            
                        }
                    }
                    else
                    {
                        aUniteMemMenuCaseX=i;
                        aUniteMemMenuCaseY=j;
                        
                        menuUniteDescription = true;
                        afficheMenuDescription(i, j);
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
                Slatch.partie.getTerrain()[i][j].dessine(g, this);
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
    
    private void effaceMenuUniteAction()
    {
        aMenuActionHautGauche_Xpx=0;
        aMenuActionHautGauche_Ypx=0;
        aMenuActionBasDroite_Xpx=0;
        aMenuActionBasDroite_Ypx=0;
        menuUniteAction = false;
        this.repaint();
    }
    
    private void effaceMenuUniteDescription()
    {
        aMenuDescriptionHautGauche_Xpx=0;
        aMenuDescriptionHautGauche_Ypx=0;
        aMenuDescriptionBasDroite_Xpx=0;
        aMenuDescriptionBasDroite_Ypx=0;
        menuUniteDescription = false;
        this.repaint();
    }
    
    /**
     * Affiche les 2 menus qui s'affichent lors d'une selection d'une unite
     */
    public void afficheMenu(final List<String> pList, final int pX, final int pY) 
    {
        aUniteMemMoteurCaseX = pX;
        aUniteMemMoteurCaseY = pY;
        menuUniteAction = true;
        afficheMenuAction(pList, pX, pY);
    }
    
    /**
     * Affiche le menu avec les boutons
     */
    public void afficheMenuAction(final List<String> pList, final int pX, final int pY) 
    {
        if(pX+aLargeurMenuActionEnCase+1>Slatch.partie.getLargeur() && pY+aHauteurMenuActionEnCase+1>Slatch.partie.getHauteur()) 
        {
            //Dessine en haut à gauche
            aMenuActionHautGauche_Xpx = (pX-aLargeurMenuActionEnCase)*aLargeurCarreau;
            aMenuActionHautGauche_Ypx = (pY-aHauteurMenuActionEnCase)*aHauteurCarreau;
            aMenuActionBasDroite_Xpx = pX*aLargeurCarreau;
            aMenuActionBasDroite_Ypx = pY*aHauteurCarreau;
        }

        else if(pX+aLargeurMenuActionEnCase+1>Slatch.partie.getLargeur()) 
        {
            //Dessine en bas à gauche
            aMenuActionHautGauche_Xpx = (pX-aLargeurMenuActionEnCase)*aLargeurCarreau;
            aMenuActionHautGauche_Ypx = (pY+1)*aHauteurCarreau;
            aMenuActionBasDroite_Xpx = pX*aLargeurCarreau;
            aMenuActionBasDroite_Ypx = (pY+aHauteurMenuActionEnCase+1)*aHauteurCarreau;
        }
        
        else if(pY+aHauteurMenuActionEnCase+1>Slatch.partie.getHauteur()) {
            //Dessine en haut à droite
            aMenuActionHautGauche_Xpx = (pX+1)*aLargeurCarreau;
            aMenuActionHautGauche_Ypx = (pY-aHauteurMenuActionEnCase)*aHauteurCarreau;
            aMenuActionBasDroite_Xpx = (pX+aLargeurMenuActionEnCase+1)*aLargeurCarreau;
            aMenuActionBasDroite_Ypx = pY*aHauteurCarreau;
        }
        
        else {
            // Meun en bas a droite par default
            aMenuActionHautGauche_Xpx = (pX+1)*aLargeurCarreau;
            aMenuActionHautGauche_Ypx = (pY+1)*aHauteurCarreau;
            aMenuActionBasDroite_Xpx = (pX+aLargeurMenuActionEnCase+1)*aLargeurCarreau;
            aMenuActionBasDroite_Ypx = (pY+aHauteurMenuActionEnCase+1)*aHauteurCarreau;
        }
        aListeAction=pList;
    }
    
    /**
     * Affiche le menu avec les descriptions
     */
    public void afficheMenuDescription(final int pX, final int pY) 
    {
        if(pX<Slatch.partie.getLargeur()/2) 
        {
            
                // En bas a droite
                aMenuDescriptionHautGauche_Xpx = (Slatch.partie.getLargeur() - aLargeurMenuDescriptionEnCase)*aLargeurCarreau;
                aMenuDescriptionHautGauche_Ypx = (Slatch.partie.getHauteur() - aHauteurMenuDescriptionEnCase)*aHauteurCarreau;
                aMenuDescriptionBasDroite_Xpx = (Slatch.partie.getLargeur())*aLargeurCarreau;
                aMenuDescriptionBasDroite_Ypx = Slatch.partie.getHauteur()*aHauteurCarreau;
            
        }
        else {
            
                // En bas a gauche
                aMenuDescriptionHautGauche_Xpx = 0;
                aMenuDescriptionHautGauche_Ypx = (Slatch.partie.getHauteur() - aHauteurMenuDescriptionEnCase)*aHauteurCarreau;
                aMenuDescriptionBasDroite_Xpx = (aLargeurMenuDescriptionEnCase)*aLargeurCarreau;
                aMenuDescriptionBasDroite_Ypx = Slatch.partie.getHauteur()*aHauteurCarreau;
            
        }
    }
    
    public int getaLargeurCarreau() {return aLargeurCarreau;}
    public int getaHauteurCarreau() {return aHauteurCarreau;}
    public void setMenuUniteAction(final boolean X){menuUniteAction=X;}
    public void setMenuUniteDescription(final boolean X){menuUniteDescription=X;}
}
