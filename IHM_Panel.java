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
    
    private int aLargeurMenuEnCase=3;
    private int aHauteurMenuEnCase=6;
    
    public IHM_Panel(final int pDecalageY)
    {
        NOMBRE_DE_CASE_X = Slatch.partie.getLargeur();
        NOMBRE_DE_CASE_Y = Slatch.partie.getHauteur();
        DECALAGE_PX_EN_Y = pDecalageY;
        MATRICE_TEST=Slatch.partie.getTerrain();
    }
    
    /**
     * 
     */
    @Override
    public void paintComponent (final Graphics g) 
    {
        hauteurCarte = this.getHeight()-DECALAGE_PX_EN_Y;
        largeurCarte = this.getWidth();
        aLargeurCarreau = largeurCarte/NOMBRE_DE_CASE_X;
        aHauteurCarreau = hauteurCarte/NOMBRE_DE_CASE_Y;
        
        //afficheTest(g);
        dessineMatrice(MATRICE_TEST, g);
        afficheBarreInfo(g);
    }    

    /**
     * Methode appelee lors d'un click
     */
    public void coordclick (String pcoordclick) 
    {
        String[] tabString = null;                      //tableau de chaînes
        tabString = pcoordclick.split(",");
        int clickX = Integer.parseInt(tabString[0]);
        int clickY = Integer.parseInt(tabString[1])-20;   //Decalage de 20 car la barre de la fenete fait + ou - : 20 px
        Graphics g = Slatch.ihm.getPanel().getGraphics();
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
                    //Si tu es dans menu
                    if( aMenuHautGauche_Ypx<clickY && clickY<aMenuBasDroite_Ypx && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx )
                    {
                        //Action a differencier
                        
                        //Bouton 1
                        if( aMenuHautGauche_Ypx<clickY && clickY<(aMenuHautGauche_Ypx+aHauteurCarreau) && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx ) 
                        {
                            System.out.println("Bouton 1");
                        }
                        
                        //Bouton 2
                        if( (aMenuHautGauche_Ypx+aHauteurCarreau)<clickY && clickY<(aMenuHautGauche_Ypx+2*aHauteurCarreau) && aMenuHautGauche_Xpx<clickX && clickX<aMenuBasDroite_Xpx ) 
                        {
                            System.out.println("Bouton 2");
                        }
                        
                    }
                    else {
                        //Si tu n'es pas dans menu
                        
                        //Efface Menu si present
                        if(aMenuHautGauche_Xpx!=0 ||aMenuHautGauche_Ypx!=0 ||aMenuBasDroite_Xpx!=0 || aMenuBasDroite_Ypx!=0) 
                        {
                            int aMenuHautGauche_X=aMenuHautGauche_Xpx/aLargeurCarreau;
                            int aMenuHautGauche_Y=(aMenuHautGauche_Ypx-DECALAGE_PX_EN_Y)/aMenuHautGauche_Ypx;
                            int aMenuBasDroite_X=aMenuBasDroite_Xpx/aLargeurCarreau;
                            int aMenuBasDroite_Y=(aMenuBasDroite_Ypx-DECALAGE_PX_EN_Y)/aMenuBasDroite_Ypx;
                            
                            for(int u=aMenuHautGauche_X; u<aMenuBasDroite_X; u++) {
                                for(int v=aMenuHautGauche_Y; v<aMenuBasDroite_Y; v++) {
                                    MATRICE_TEST[u][v].dessine(g);
                                }
                            }
                        }
                        
                        aMenuHautGauche_Xpx=0;
                        aMenuHautGauche_Ypx=0;
                        aMenuBasDroite_Xpx=0;
                        aMenuBasDroite_Ypx=0;

                        // Avertir Moteur
                        Slatch.moteur.caseSelectionnee(i,j);
                        Slatch.partie.getTerrain()[i][j].dessine(g);
                    }
                }
            }
        }
    }
    
    /**
     * Affiche Test
     */
    public void afficheMenu(final List<String> pList, final int pX, final int pY) 
    {
        Graphics g = Slatch.ihm.getPanel().getGraphics();
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
        g.setColor(Color.white);
        g.drawString(pList.get(0), (aMenuHautGauche_Xpx+aMenuBasDroite_Xpx)/2-3*aLargeurCarreau/2, aMenuHautGauche_Ypx+aHauteurCarreau/2);
        //g.drawString(pList.get(1), (aMenuHautGauche_Xpx+aMenuBasDroite_Xpx)/2-3*aLargeurCarreau/2, aMenuHautGauche_Ypx+3*aHauteurCarreau/2);
        //g.drawString(pList.get(2), (aMenuHautGauche_Xpx+aMenuBasDroite_Xpx)/2-3*aLargeurCarreau/2, aMenuHautGauche_Ypx+5*aHauteurCarreau/2);
        g.drawLine(aMenuHautGauche_Xpx, aMenuHautGauche_Ypx+(aHauteurMenuEnCase/aHauteurMenuEnCase)*aHauteurCarreau, aMenuBasDroite_Xpx, aMenuHautGauche_Ypx+(aHauteurMenuEnCase/aHauteurMenuEnCase)*aHauteurCarreau);
        g.drawLine(aMenuHautGauche_Xpx, aMenuHautGauche_Ypx+(2*aHauteurMenuEnCase/aHauteurMenuEnCase)*aHauteurCarreau, aMenuBasDroite_Xpx, aMenuHautGauche_Ypx+(2*aHauteurMenuEnCase/aHauteurMenuEnCase)*aHauteurCarreau);
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
        g.drawString("Tour :"+"1", this.getWidth()/8, DECALAGE_PX_EN_Y/2);
        g.drawString("Argent :"+"1000", this.getWidth()/4, DECALAGE_PX_EN_Y/2);
        g.drawString("SUIVANT", this.getWidth()-this.getWidth()/8, DECALAGE_PX_EN_Y/2);
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
    
    public int getDECALAGE_PX_EN_Y() {
        return DECALAGE_PX_EN_Y;
    }
    
    public int getaLargeurCarreau() {
        return aLargeurCarreau;
    }
    
    public int getaHauteurCarreau() {
        return aHauteurCarreau;
    }
}
