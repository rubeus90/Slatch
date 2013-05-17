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

/**
 * Panneau de l'IHM (herite de JPanel)
 * 
 * @author MERCANDALLI 
 * @version 01/2013
 */
class IHM_Panel extends JPanel 
{
    private int NOMBRE_DE_CASE_X;
    private int NOMBRE_DE_CASE_Y;
    private Terrain[][] MATRICE_TEST;
    private int DECALAGE_PX_EN_Y;
    
    private int hauteurCarte;
    private int largeurCarte;
    
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
    
    private boolean modeMenu;
    private boolean aAttaquePossible=false;
    
    /**
     * Constructeur
     */
    public IHM_Panel(final int pDecalageY)
    {
        NOMBRE_DE_CASE_X = Slatch.partie.getLargeur();
        NOMBRE_DE_CASE_Y = Slatch.partie.getHauteur();
        DECALAGE_PX_EN_Y = pDecalageY;
        MATRICE_TEST=Slatch.partie.getTerrain();
        modeMenu=false;
    }
    
    /**
     * Affiche le decor (appelé lors des repaint : a eviter)
     */
    @Override
    public void paintComponent (final Graphics g) 
    {
        hauteurCarte = this.getHeight()-DECALAGE_PX_EN_Y;
        largeurCarte = this.getWidth();
        aLargeurCarreau = largeurCarte/NOMBRE_DE_CASE_X;
        aHauteurCarreau = hauteurCarte/NOMBRE_DE_CASE_Y;

        dessineMatrice(MATRICE_TEST, g);
        afficheBarreInfo(g);
    }    

    /**
     * Methode appelee lors d'un click
     */
    public void coordclickAppui (String pcoordclick) 
    {
        // Decoupe String pour récupérer les coord
        String[] tabString = null;                      //tableau de chaînes
        tabString = pcoordclick.split(",");
        int clickX = Integer.parseInt(tabString[0]);
        int clickY = Integer.parseInt(tabString[1])-20;   //Decalage de 20 car la barre de la fenete fait + ou - : 20 px
        Graphics g = Slatch.ihm.getPanel().getGraphics();
        
        // Bouton SUIVANT affichage
        if(0<clickY && clickY<DECALAGE_PX_EN_Y && this.getWidth()-6*aLargeurCarreau<clickX && clickX<this.getWidth()) 
        {
            afficheImageRedim ("suivantAppui.png", this.getWidth()-6*aLargeurCarreau, 0,this.getWidth(), DECALAGE_PX_EN_Y, g);
        }
    }
    
    
    /**
     * Methode appelee lors d'un click
     */
    public void coordclickUnite (String pcoordclick) 
    {
        // Decoupe String pour récupérer les coord
        String[] tabString = null;                      //tableau de chaînes
        tabString = pcoordclick.split(",");
        int clickX = Integer.parseInt(tabString[0]);
        int clickY = Integer.parseInt(tabString[1])-20;   //Decalage de 20 car la barre de la fenete fait + ou - : 20 px
        Graphics g = Slatch.ihm.getPanel().getGraphics();
        
        // Bouton SUIVANT affichage : Remet suivant blanc
        afficheImageRedim ("suivant.png", this.getWidth()-6*aLargeurCarreau, 0,this.getWidth(), DECALAGE_PX_EN_Y, g);
        
        // Bouton SUIVANT fonctionnement
        if(0<clickY && clickY<DECALAGE_PX_EN_Y && this.getWidth()-6*aLargeurCarreau<clickX && clickX<this.getWidth()) 
        {
            // Avertir Moteur
            Slatch.moteur.passeTour();
        }
        
        for(int i = 0 ; i < NOMBRE_DE_CASE_X ; i++) 
        {
            for(int j = 0 ; j < NOMBRE_DE_CASE_Y ; j++) 
            {
                // Position de la case selectionnee
                int pPosHautGaucheX = i*Slatch.ihm.getPanel().getaLargeurCarreau();
                int pPosHautGaucheY = j*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();
                int pPosBasDroiteX = (i+1)*Slatch.ihm.getPanel().getaLargeurCarreau();
                int pPosBasDroiteY = (j+1)*Slatch.ihm.getPanel().getaHauteurCarreau() + Slatch.ihm.getPanel().getDECALAGE_PX_EN_Y();

                if(pPosHautGaucheY<clickY && clickY<pPosBasDroiteY && pPosHautGaucheX<clickX && clickX<pPosBasDroiteX) 
                {
                	if(modeMenu)
                    {
                        if( aMenuHautGauche_Ypx<clickY && clickY<aMenuBasDroite_Ypx && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx )//Si tu es dans menu
                        {
                            //Action a differencier
                            
                            //Bouton 1 : Deplace
                            if( aMenuHautGauche_Ypx<clickY && clickY<(aMenuHautGauche_Ypx+aHauteurCarreau) && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx ) 
                            {
                                Slatch.moteur.modeDeplacement(aUniteMemCaseX, aUniteMemCaseY);
                                effaceMenu(g);
                            }
                            
                            //Bouton 2 : Attaque
                            if(aAttaquePossible && (aMenuHautGauche_Ypx+aHauteurCarreau)<clickY && clickY<(aMenuHautGauche_Ypx+2*aHauteurCarreau) && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx ) 
                            {
                                Slatch.moteur.modeAttaque(aUniteMemCaseX, aUniteMemCaseY);
                                effaceMenu(g);
                            }
                            
                        }
                        else {
                            effaceMenu(g);
                            // Avertir Moteur
                            Slatch.moteur.annulerDeplacement();
                            Slatch.moteur.caseSelectionnee(i,j);
                            Slatch.partie.getTerrain()[i][j].dessine(g);
                        }
                    }
                    else
                    {
                        Slatch.moteur.caseSelectionnee(i,j);
                        Slatch.partie.getTerrain()[i][j].dessine(g);
                    }
                }
            }
        }
    }
    
    private void effaceMenu(Graphics g)
    {
        int aMenuHautGauche_X=aMenuHautGauche_Xpx/aLargeurCarreau;
        int aMenuHautGauche_Y=(aMenuHautGauche_Ypx-DECALAGE_PX_EN_Y)/aHauteurCarreau;
        int aMenuBasDroite_X=aMenuBasDroite_Xpx/aLargeurCarreau;
        int aMenuBasDroite_Y=(aMenuBasDroite_Ypx-DECALAGE_PX_EN_Y)/aHauteurCarreau;
        for(int u=aMenuHautGauche_X; u<aMenuBasDroite_X; u++) {
            for(int v=aMenuHautGauche_Y; v<aMenuBasDroite_Y; v++) {
                MATRICE_TEST[u][v].dessine(g);
            }
        }
        
        aMenuHautGauche_Xpx=0;
        aMenuHautGauche_Ypx=0;
        aMenuBasDroite_Xpx=0;
        aMenuBasDroite_Ypx=0;
        modeMenu = false;
    }
    
    /**
     * Affiche Test
     */
    public void afficheMenu(final List<String> pList, final int pX, final int pY) 
    {
        Graphics g = Slatch.ihm.getPanel().getGraphics();
        aUniteMemCaseX = pX;
        aUniteMemCaseY = pY;
        modeMenu = true;
        if(pX+aLargeurMenuEnCase+1>NOMBRE_DE_CASE_X && pY+aHauteurMenuEnCase+1>NOMBRE_DE_CASE_Y) 
        {
            //Dessine en haut à gauche
            aMenuHautGauche_Xpx = (pX-aLargeurMenuEnCase)*aLargeurCarreau;
            aMenuHautGauche_Ypx = (pY-aHauteurMenuEnCase)*aHauteurCarreau+DECALAGE_PX_EN_Y;
            aMenuBasDroite_Xpx = pX*aLargeurCarreau;
            aMenuBasDroite_Ypx = pY*aHauteurCarreau+DECALAGE_PX_EN_Y;
        }

        else if(pX+aLargeurMenuEnCase+1>NOMBRE_DE_CASE_X) 
        {
            //Dessine en bas à gauche
            aMenuHautGauche_Xpx = (pX-aLargeurMenuEnCase)*aLargeurCarreau;
            aMenuHautGauche_Ypx = (pY+1)*aHauteurCarreau+DECALAGE_PX_EN_Y;
            aMenuBasDroite_Xpx = pX*aLargeurCarreau;
            aMenuBasDroite_Ypx = (pY+aHauteurMenuEnCase+1)*aHauteurCarreau+DECALAGE_PX_EN_Y;
        }
        
        else if(pY+aHauteurMenuEnCase+1>NOMBRE_DE_CASE_Y) {
            //Dessine en haut à droite
            aMenuHautGauche_Xpx = (pX+1)*aLargeurCarreau;
            aMenuHautGauche_Ypx = (pY-aHauteurMenuEnCase)*aHauteurCarreau+DECALAGE_PX_EN_Y;
            aMenuBasDroite_Xpx = (pX+aLargeurMenuEnCase+1)*aLargeurCarreau;
            aMenuBasDroite_Ypx = pY*aHauteurCarreau+DECALAGE_PX_EN_Y;
        }
        
        else {
            // Meun en bas a droite par default
            aMenuHautGauche_Xpx = (pX+1)*aLargeurCarreau;
            aMenuHautGauche_Ypx = (pY+1)*aHauteurCarreau+DECALAGE_PX_EN_Y;
            aMenuBasDroite_Xpx = (pX+aLargeurMenuEnCase+1)*aLargeurCarreau;
            aMenuBasDroite_Ypx = (pY+aHauteurMenuEnCase+1)*aHauteurCarreau+DECALAGE_PX_EN_Y;
        }
            
        afficheImageRedim ("noir80.png", aMenuHautGauche_Xpx, aMenuHautGauche_Ypx, aMenuBasDroite_Xpx, aMenuBasDroite_Ypx, g);
        
        // Ecrie les boutons en rouge
        g.setColor(Color.gray);
        g.drawString("Deplace", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3);
        g.drawString("Attaque", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);
        g.drawString("Capture", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
        g.drawString("Evolue", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
        
        // Ecrie les boutons en vert
        for(int vVar=0;vVar<pList.size();vVar++) {
            g.setColor(Color.green);
            if(pList.get(vVar).equals("Deplace")) {
                g.drawString("Deplace", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3);
            }
            if(pList.get(vVar).equals("Attaque")) {
                g.drawString("Attaque", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau);
                aAttaquePossible=true;
            }
            if(pList.get(vVar).equals("Capture")) {
                g.drawString("Capture", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*2);
            }
            if(pList.get(vVar).equals("Evolue")) {
                g.drawString("Evolue", aMenuHautGauche_Xpx+aLargeurCarreau/3, aMenuHautGauche_Ypx+2*aHauteurCarreau/3+aHauteurCarreau*3);
            }
        }
        
        // Trace les lignes
        for(int vVar=1;vVar<4;vVar++) {
            g.setColor(Color.white);
            g.drawLine(aMenuHautGauche_Xpx, aMenuHautGauche_Ypx+(vVar*aHauteurMenuEnCase/aHauteurMenuEnCase)*aHauteurCarreau, aMenuBasDroite_Xpx-1, aMenuHautGauche_Ypx+(vVar*aHauteurMenuEnCase/aHauteurMenuEnCase)*aHauteurCarreau);
        }
    }

    /**
     * Affiche Test
     */
    private void afficheTest (final Graphics g) {
        for(int i = 0; i < NOMBRE_DE_CASE_X ; i++) {
            for(int j=0 ; j < NOMBRE_DE_CASE_Y ; j++) {
                afficheImageRedim(""+(i%4+1)+".png",aLargeurCarreau*i, aHauteurCarreau*j+DECALAGE_PX_EN_Y, aLargeurCarreau*(i+1), aHauteurCarreau*(j+1)+DECALAGE_PX_EN_Y,g);
                g.setColor(Color.white);
                g.drawRect(aLargeurCarreau*i, j*aHauteurCarreau+DECALAGE_PX_EN_Y, aLargeurCarreau+aLargeurCarreau*i, aHauteurCarreau+j*aHauteurCarreau+DECALAGE_PX_EN_Y);
            }
        }
    }
    
    /**
     * Affiche une matrice d'image sur la carte
     */
    private void afficheMatrice(final String[][] pStringURL, final Graphics g) {
        for(int i=0; i<NOMBRE_DE_CASE_X; i++) {
            for(int j=0; j<NOMBRE_DE_CASE_Y; j++) {
                afficheImageRedim(pStringURL[i][j], aLargeurCarreau*i, aHauteurCarreau*j+DECALAGE_PX_EN_Y, aLargeurCarreau*(i+1), aHauteurCarreau*(j+1)+DECALAGE_PX_EN_Y, g);
            }
        }
    }
    
    /**
     * Affiche une matrice d'image sur la carte
     */
    private void dessineMatrice(final Terrain[][] pMatrice, final Graphics g) {
        for(int i=0; i<NOMBRE_DE_CASE_X; i++) {
            for(int j=0; j<NOMBRE_DE_CASE_Y; j++) {
                MATRICE_TEST[i][j].dessine(g);
            }
        }
    }

    /**
     * Affiche la barre d'informations en haut de l'ecran
     */
    public void afficheBarreInfo (final Graphics g) {
        afficheImageRedim("4.png",0, 0,this.getWidth(),DECALAGE_PX_EN_Y,g);
        g.setColor(Color.white);
        g.drawString("Tour : "+Slatch.partie.getTour(), this.getWidth()/8, DECALAGE_PX_EN_Y/2);
        g.drawString("Argent :"+"1000", this.getWidth()/4, DECALAGE_PX_EN_Y/2);
        
        // Bouton SUIVANT
        afficheImageRedim ("suivant.png", this.getWidth()-6*aLargeurCarreau, 0,this.getWidth(), DECALAGE_PX_EN_Y, g);
    }
    
    
    //********************************/
    //**    Méthodes de dessin       */
    //********************************/
    public void dessineTerrain(final int i, final int j)
    {
        Graphics g = this.getGraphics();
        Slatch.partie.getTerrain()[i][j].dessine(g);
    }
    
    /**
     * Affiche une image a la position desiree
     */
    private void afficheImage (final String pURL, final int pPosX, final int pPosY, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            g.drawImage(img, pPosX, pPosY, this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImageRedim (final String pURL, final int pPosHautGaucheX, final int pPosHautGaucheY,final int pPosBasDroiteX, final int pPosBasDroiteY, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            g.drawImage(img, pPosHautGaucheX, pPosHautGaucheY, pPosBasDroiteX-pPosHautGaucheX, pPosBasDroiteY-pPosHautGaucheY, Slatch.ihm.getPanel());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    /**
     * Affiche une image en fond d'ecran
     */
    private void afficheImagePleinEcran (final String pURL, final Graphics g) {
        try {
            Image img = ImageIO.read(new File("Images/"+pURL));
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public int getDECALAGE_PX_EN_Y() {return DECALAGE_PX_EN_Y;}
    
    public int getaLargeurCarreau() {return aLargeurCarreau;}
    
    public int getaHauteurCarreau() {return aHauteurCarreau;}
}
